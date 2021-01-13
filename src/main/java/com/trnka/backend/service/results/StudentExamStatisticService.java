package com.trnka.backend.service.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.trnka.backend.domain.statistic.MethodicalLearningStatistic;
import com.trnka.backend.domain.statistic.SequeceStatistic;
import com.trnka.backend.dto.results.StudentStatisticListItemDto;
import com.trnka.restapi.dto.statistics.ExaminationStepStatisticDto;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.dao.ExaminationStatisticDao;
import com.trnka.backend.domain.statistic.ExaminationStatistic;
import com.trnka.backend.domain.statistic.ExaminationStepStatistic;
import com.trnka.backend.domain.Student;
import com.trnka.backend.dto.results.StudentStatisticListDto;
import com.trnka.backend.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentExamStatisticService {

    private final StudentRepository studentRepository;
    private final ExaminationStatisticDao examinationStatisticDao;

    public ModelAndView getStudentStatistic(@NotNull Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) {
            log.error("Student with id {} not found!", studentId);
            ModelAndView mv = new ModelAndView(Templates.ERROR_PAGE.getTemplateName());
            mv.addObject("errorMessage", "Student not found!");
            return mv;
        }
        StudentStatisticListDto modelDto = mapToUiModel(student.get().getSequenceStatistics());
        return new ModelAndView(Templates.STUDENT_EXAM_STATISTICS.getTemplateName()).addObject("model", modelDto);
    }

    private StudentStatisticListDto mapToUiModel(List<SequeceStatistic> statistics) {

        List<StudentStatisticListItemDto> listRows = new ArrayList<>();
        for (SequeceStatistic sequenceStatistic : statistics) {
            StudentStatisticListItemDto examStatsDto = new StudentStatisticListItemDto();

            examStatsDto.setFinishedOn(sequenceStatistic.getFinishedOn());
            examStatsDto.setPassed(sequenceStatistic.getPassed());
            examStatsDto.setSequenceType(sequenceStatistic.getSequenceType());

            switch (sequenceStatistic.getSequenceType()) {
            case LEARNING:
            case TESTING:
                ExaminationStatistic examStats = (ExaminationStatistic) sequenceStatistic;
                examStatsDto.setExaminationId(examStats.getExamination().getId());
                examStatsDto.setTotalTimeInMs(examinationStatisticDao.getTotalExaminationDurationInMs(examStats));
                List<ExaminationStepStatisticDto> stepStatsList = mapStepStatsToDtos(examStats.getExaminationStepStatistics());
                examStatsDto.setStepStatistics(stepStatsList);
                examStatsDto.setLetterSequence(extractLettersSerquence(stepStatsList));
                break;
            case METHODICAL:
                MethodicalLearningStatistic methodicalLearningStats = (MethodicalLearningStatistic) sequenceStatistic;
                examStatsDto.setLetterSequence(methodicalLearningStats.getLetterSequence());
                examStatsDto.setTotalTimeInMs(methodicalLearningStats.getTotalTimeInMs());

                break;
            default:
            }
            listRows.add(examStatsDto);
        }

        StudentStatisticListDto listDto = new StudentStatisticListDto();
        listDto.setStatistics(listRows);
        return listDto;
    }

    private List<ExaminationStepStatisticDto> mapStepStatsToDtos(List<ExaminationStepStatistic> list) {
        List<ExaminationStepStatisticDto> stepStatsList = new ArrayList<>();
        for (ExaminationStepStatistic stepStatistic : list) {
            ExaminationStepStatisticDto stepStatDto = new ExaminationStepStatisticDto();
            stepStatDto.setLetter(stepStatistic.getStep().getBrailCharacter().getLetter());
            stepStatDto.setCorrect(stepStatistic.getCorrect());
            stepStatDto.setRetries(stepStatistic.getRetries());
            stepStatDto.setDurationInMs(stepStatistic.getTook());
            stepStatDto.setDisplayOrder(stepStatistic.getStep().getDisplayOrder());
            stepStatsList.add(stepStatDto);
        }
        Collections.sort(stepStatsList, Comparator.comparing(ExaminationStepStatisticDto::getDisplayOrder));
        return stepStatsList;
    }

    public String extractLettersSerquence(List<ExaminationStepStatisticDto> list) {
        return list.stream().map(entry -> entry.getLetter()).collect(Collectors.joining(","));
    }

}
