package com.trnka.backend.service.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.trnka.backend.domain.sync.DeviceToServerSyncLog;
import com.trnka.backend.domain.sync.DeviceToServerSyncStatus;
import com.trnka.backend.domain.sync.DeviceToServerSyncType;
import com.trnka.backend.repository.DeviceToServerSyncLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.trnka.backend.domain.Course;
import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import com.trnka.backend.domain.Student;
import com.trnka.backend.repository.CourseRepository;
import com.trnka.restapi.dto.BrailCharacterDto;
import com.trnka.restapi.dto.ExaminationDto;
import com.trnka.restapi.dto.ExaminationStepDto;
import com.trnka.restapi.dto.StudentDTO;
import com.trnka.restapi.dto.SyncDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentSyncService {

    private final CourseRepository courseRepository;
    private final DeviceToServerSyncLogRepository deviceToServerSyncLogRepository;

    public SyncDto getSyncDtoAndLogDeviceIntegration(String deviceId) {
        try {
            SyncDto syncDto = getSyncDto();
            log.info("Returning sync DTO for deviceId: {}", deviceId);
            deviceToServerSyncLogRepository.save(new DeviceToServerSyncLog(deviceId, DeviceToServerSyncStatus.SUCCESS, DeviceToServerSyncType.DEVICE_DOWNLOAD_FROM_SERVER));
            return syncDto;
        } catch (Exception e) {
            log.error("Exception occurred while downloading sync data from server for deviceId: {}", deviceId);
            deviceToServerSyncLogRepository.save(new DeviceToServerSyncLog(deviceId, DeviceToServerSyncStatus.FAILED, DeviceToServerSyncType.DEVICE_DOWNLOAD_FROM_SERVER));
            throw new RuntimeException(e);
        }

    }

    private SyncDto getSyncDto() {
        List<Course> courses = courseRepository.findAll();

        List<StudentDTO> studentDtos = new ArrayList<>();
        ArrayList<ExaminationDto> examinationDtos = new ArrayList<>();

        courses.forEach(course -> {
            // prepare examinations
            List<Examination> courseExaminations = course.getExaminations();
            courseExaminations.stream().forEach(examination -> examinationDtos.add(mapExamination(examination)));

            // prepare students
            List<StudentDTO> dtos = mapToStudents(courseExaminations.stream().map(Examination::getId).collect(Collectors.toSet()), course.getStudents());
            studentDtos.addAll(dtos);

        });
        return new SyncDto(studentDtos,
                           examinationDtos);
    }

    private List<StudentDTO> mapToStudents(Set<Long> examinationIds,
                                           List<Student> students) {
        List<StudentDTO> studentDtos = students.stream().map(s -> mapStudent(s, examinationIds)).collect(Collectors.toList());
        return studentDtos;
    }

    private ExaminationDto mapExamination(Examination examination) {
        ExaminationDto dto = new ExaminationDto();
        dto.setId(examination.getId());
        dto.setAllowedRetries(examination.getAllowedRetries());
        dto.setAudio(examination.getAudio());
        dto.setName(examination.getName());
        dto.setTimeout(examination.getTimeout());
        dto.setType(examination.getType());
        dto.setPassingRate(examination.getPassingRate());
        dto.setSteps(examination.getExaminationSteps().stream().map(this::mapExaminationStep).collect(Collectors.toList()));
        return dto;
    }

    private ExaminationStepDto mapExaminationStep(ExaminationStep step) {
        ExaminationStepDto dto = new ExaminationStepDto();
        BrailCharacterDto bc = new BrailCharacterDto(step.getBrailCharacter().getLetter());
        bc.setId(step.getBrailCharacter().getId());
        dto.setId(step.getId());
        dto.setBrailCharacter(bc);
        dto.setPreserveOrder(step.getPreserveOrder());
        return dto;
    }

    private StudentDTO mapStudent(Student student,
                                  Set<Long> examinationIds) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setCode(student.getDeviceIdentificationCode());
        dto.setExaminationIds(examinationIds);
        return dto;
    }
}
