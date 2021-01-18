package com.trnka.backend.service.testing;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.Teacher;
import com.trnka.backend.dto.ExaminationsPageModel;
import com.trnka.backend.service.TeacherService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExaminationListService {

    private final TeacherService teacherRepository;

    public ModelAndView getExaminationsForCurrentTeacher() {
        Optional<Teacher> teacher = teacherRepository.getCurrentTeacher();
        if (!teacher.isPresent()) {
            log.error("None teacher cannot see courses!");
            return null;
        }
        List<Examination> examinations = teacher.get().getCourseList().stream().flatMap(c -> c.getExaminations().stream()).collect(Collectors.toList());
        ExaminationsPageModel model = new ExaminationsPageModel();
        model.setExams(examinations);
        ModelAndView mav = new ModelAndView(Templates.EXAMINATIONS_PAGE.getTemplateName());
        return mav.addObject("model", model);
    }
}
