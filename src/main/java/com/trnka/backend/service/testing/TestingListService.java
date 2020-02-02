package com.trnka.backend.service.testing;

import java.util.List;
import java.util.stream.Collectors;

import com.trnka.backend.config.Templates;
import com.trnka.backend.dto.TestingPageModel;
import org.hibernate.sql.Template;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.Teacher;
import com.trnka.backend.repository.ExaminationRepository;
import com.trnka.backend.repository.TeacherRepository;
import com.trnka.backend.service.UserSession;
import org.springframework.web.servlet.ModelAndView;

@Service
public class TestingListService {

    private ExaminationRepository examinationRepository;
    private TeacherRepository teacherRepository;

    public TestingListService(final ExaminationRepository examinationRepository,
                              final TeacherRepository teacherRepository) {
        this.examinationRepository = examinationRepository;
        this.teacherRepository = teacherRepository;
    }

    public ModelAndView getExaminationsForCurrentTeacher() {
        UserDetails session = UserSession.currentUserDetails();
        Teacher teacher = teacherRepository.findByUser_Username(session.getUsername());
        List<Examination> examinations = teacher.getCourseList().stream().flatMap(c -> c.getExaminations().stream()).collect(Collectors.toList());
        TestingPageModel model = new TestingPageModel();
        model.setExams(examinations);
        ModelAndView mav = new ModelAndView(Templates.TESTING_PAGE.getTemplateName());
        return mav.addObject("model", model);
    }
}
