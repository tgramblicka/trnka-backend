package com.trnka.backend.service.sync;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.trnka.backend.domain.sync.DeviceToServerSyncLog;
import com.trnka.backend.domain.sync.DeviceToServerSyncStatus;
import com.trnka.backend.repository.DeviceToServerSyncLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import com.trnka.backend.domain.Student;
import com.trnka.backend.domain.statistic.ExaminationStatistic;
import com.trnka.backend.domain.statistic.MethodicalLearningStatistic;
import com.trnka.backend.repository.ExaminationRepository;
import com.trnka.backend.repository.ExaminationStepRepository;
import com.trnka.backend.repository.StudentRepository;
import com.trnka.restapi.dto.statistics.DeviceStatisticsSyncDto;
import com.trnka.restapi.dto.statistics.Evaluate;
import com.trnka.restapi.dto.statistics.ExaminationStatisticDto;
import com.trnka.restapi.dto.statistics.ExaminationStepStatisticDto;
import com.trnka.restapi.dto.statistics.StudentDeviceStatisticsDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExaminationStatisticSyncService {

    private final ExaminationRepository examinationRepository;
    private final ExaminationStepRepository examinationStepRepository;
    private final StudentRepository studentRepository;
    private final DeviceToServerSyncLogRepository deviceToServerSyncLogRepository;

    public Boolean uploadStatsFromDeviceToServer(final DeviceStatisticsSyncDto dto) {
        try {
            updateSequenceStatisticsToAllStudents(dto);
            deviceToServerSyncLogRepository.save(new DeviceToServerSyncLog(dto.getDeviceId(), DeviceToServerSyncStatus.SUCCESS));
            return true;
        } catch (Exception e) {
            log.error("Exception occurred during syncing");
            deviceToServerSyncLogRepository.save(new DeviceToServerSyncLog(dto.getDeviceId(), DeviceToServerSyncStatus.FAILED));
            return false;
        }
    }

    private void updateSequenceStatisticsToAllStudents(final DeviceStatisticsSyncDto dto){
        for (StudentDeviceStatisticsDto studentSatDto : dto.getStatistics()) {
            Optional<Student> foundStudent = studentRepository.findByDeviceIdentificationCode(studentSatDto.getStudentCode());
            if (!foundStudent.isPresent()) {
                log.error("Student with code: {} does not exist. Will ignore the examination statistics update!", studentSatDto.getStudentCode());
                break;
            }
            Student student = foundStudent.get();
            student.setDeviceLoginCount(studentSatDto.getLoginCount());
            updateStudentStats(student, studentSatDto.getStatistics());
        }
    }


    private void updateStudentStats(Student student,
                                    List<ExaminationStatisticDto> newStats) {
        for (ExaminationStatisticDto newExaminationStat : newStats) {
            if (hasStudentAlreadySequenceStatistic(student, newExaminationStat.getFinishedOn())) {
                log.info("Examination statistic with examination finished on timestamp: {} already exists. Skipping this update.",
                        newExaminationStat.getFinishedOn());
                break;
            }
            saveSequenceStatistic(student, newExaminationStat);
        }
    }

    private void saveSequenceStatistic(final Student student,
                                       final ExaminationStatisticDto newExaminationStatDto) {

        switch (newExaminationStatDto.getSequenceType()) {
        case TESTING:
        case LEARNING:
            Optional<Examination> optionalExamination = examinationRepository.findById(newExaminationStatDto.getExaminationId());
            if (!optionalExamination.isPresent()) {
                log.error("Examination with ID: {} not found, skipping this statistic update!", newExaminationStatDto.getExaminationId());
                return;
            }
            ExaminationStatistic examinationStatistic = ExaminationStatistic.create(optionalExamination.get(), student, newExaminationStatDto.getSequenceType(),
                    newExaminationStatDto.getFinishedOn());
            examinationStatistic.setPassed(newExaminationStatDto.getPassed());
            for (ExaminationStepStatisticDto stepStatDto : newExaminationStatDto.getStepStatistics()) {
                Optional<ExaminationStep> optionalExaminationStep = examinationStepRepository.findById(stepStatDto.getExaminationStepId());
                if (!optionalExaminationStep.isPresent()) {
                    log.error("Examination step with ID: {} not found, this step won't be updated to statistics!", stepStatDto.getExaminationStepId());
                    continue;
                }
                examinationStatistic.addStepStatistic(optionalExaminationStep.get(), stepStatDto.getDurationInMs(), new Evaluate(stepStatDto.getCorrect(),
                                                                                                                                 stepStatDto.getRetries()));
            }
            break;
        case METHODICAL:
            MethodicalLearningStatistic stat = MethodicalLearningStatistic.create(student);
            stat.setLetterSequence(newExaminationStatDto.getLetterSequence());
            stat.setPassed(newExaminationStatDto.getPassed());
            stat.setTotalTimeInMs(newExaminationStatDto.getTotalTimeInMs());
            stat.setFinishedOn(newExaminationStatDto.getFinishedOn());
            break;
        default:
            break;
        }

    }

    // could be optimized with a named query
    private boolean hasStudentAlreadySequenceStatistic(final Student student,
                                                       final LocalDateTime sequenceFinishedOn) {
        return student.getSequenceStatistics()
                .stream()
                .filter(sequeceStatistic -> sequeceStatistic.getFinishedOn().equals(sequenceFinishedOn))
                .findAny()
                .isPresent();
    }

}
