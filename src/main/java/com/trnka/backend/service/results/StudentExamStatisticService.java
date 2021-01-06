package com.trnka.backend.service.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.dao.ExaminationStatisticDao;
import com.trnka.backend.domain.ExaminationStatistic;
import com.trnka.backend.domain.ExaminationStepStatistic;
import com.trnka.backend.domain.Student;
import com.trnka.backend.dto.results.ExaminationStatisticDto;
import com.trnka.backend.dto.results.ExaminationStepStatisticDto;
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
        StudentStatisticListDto modelDto = mapToUiModel(student.get().getExaminationStatistics());
        return new ModelAndView(Templates.STUDENT_EXAM_STATISTICS.getTemplateName()).addObject("model", modelDto);
    }

    private StudentStatisticListDto mapToUiModel(List<ExaminationStatistic> statistics) {

        List<ExaminationStatisticDto> examStatsList = new ArrayList<>();
        for (ExaminationStatistic examinationStatistic : statistics) {
            ExaminationStatisticDto examStatsDto = new ExaminationStatisticDto();

            examStatsDto.setFinishedOn(examinationStatistic.getFinishedOn());
            examStatsDto.setExaminationId(examinationStatistic.getExamination().getId());
            examStatsDto.setPassed(examinationStatistic.getPassed());
            examStatsDto.setTotalTimeInMs(examinationStatisticDao.getTotalExaminationDurationInMs(examinationStatistic));

            List<ExaminationStepStatisticDto> stepStatsList = mapStepStatsToDtos(examinationStatistic.getExaminationStepStatistics());

            examStatsDto.setLetterSequence(extractLettersSerquence(stepStatsList));
            examStatsDto.setStepStatistics(stepStatsList);

            examStatsList.add(examStatsDto);
        }

        StudentStatisticListDto studentStatsListDto = new StudentStatisticListDto();
        studentStatsListDto.setStatistics(examStatsList);
        return studentStatsListDto;
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
        }
        Collections.sort(stepStatsList, Comparator.comparing(ExaminationStepStatisticDto::getDisplayOrder));
        return stepStatsList;
    }

    public String extractLettersSerquence(List<ExaminationStepStatisticDto> list) {
        return list.stream()
                .map(entry -> entry.getLetter())
                .collect(Collectors.joining(","));
    }


}
