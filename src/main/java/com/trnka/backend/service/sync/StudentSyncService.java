package com.trnka.backend.service.sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class StudentSyncService {

    private CourseRepository courseRepository;

    public StudentSyncService(final CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public SyncDto getSyncDto() {
        List<Course> courses = courseRepository.findAll();

        List<StudentDTO> studentDtos = new ArrayList<>();

        courses.forEach(course -> {
            List<StudentDTO> dtos = mapToStudents(course.getExaminations().stream().map(Examination::getId).collect(Collectors.toSet()), course.getStudents());
            studentDtos.addAll(dtos);
        });
        // todo fix examinations sync
        return new SyncDto(studentDtos,
                           Collections.emptyList());
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
        dto.setComplexity(examination.getComplexity());
        dto.setSteps(examination.getExaminationSteps().stream().map(this::mapExaminationStep).collect(Collectors.toList()));
        return dto;
    }

    private ExaminationStepDto mapExaminationStep(ExaminationStep step) {
        ExaminationStepDto dto = new ExaminationStepDto();
        BrailCharacterDto bc = new BrailCharacterDto(step.getBrailCharacter().getLetter());
        bc.setId(step.getBrailCharacter().getId());
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
