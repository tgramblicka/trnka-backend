package com.trnka.backend.service.testing;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.trnka.backend.domain.UserType;
import com.trnka.backend.repository.ExaminationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    private final ExaminationRepository examinationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ModelAndView getExaminationsForCurrentTeacher() {
        Optional<Teacher> teacherOptional = teacherRepository.getCurrentTeacher();
        if (!teacherOptional.isPresent()) {
            log.error("None teacherOptional cannot see courses!");
            return null;
        }
        Teacher teacher = teacherOptional.get();
        List<Examination> examinations;
        if (teacher.getUser().getType().equals(UserType.ADMIN)) {
            examinations = examinationRepository.findAll();
        } else {
            examinations = teacher.getCourseList().stream().flatMap(c -> c.getExaminations().stream()).collect(Collectors.toList());
        }
        ExaminationsPageModel model = new ExaminationsPageModel();
        model.setExams(examinations);
        ModelAndView mav = new ModelAndView(Templates.EXAMINATIONS_PAGE.getTemplateName());
        return mav.addObject("model", model);
    }
}
