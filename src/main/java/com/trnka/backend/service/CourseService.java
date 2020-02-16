package com.trnka.backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.DummyData;
import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.Course;
import com.trnka.backend.domain.Teacher;
import com.trnka.backend.dto.course.CourseModel;
import com.trnka.backend.dto.course.CourseSelectDto;
import com.trnka.backend.repository.CourseRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseService {

    private TeacherService teacherService;
    private CourseRepository courseRepository;

    public CourseService(final TeacherService teacherService,
                         final CourseRepository courseRepository) {
        this.teacherService = teacherService;
        this.courseRepository = courseRepository;
    }

    public ModelAndView getMyCoursesList() {
        ModelAndView mv = new ModelAndView(Templates.COURSES_PAGE.getTemplateName());
        return mv.addObject("teacherName", "Jan Testovaci").addObject("courses", getMyCoursesSelection());
    }

    public List<CourseSelectDto> getMyCoursesSelection() {
        Optional<Teacher> currentTeacher = teacherService.getCurrentTeacher();
        if (!currentTeacher.isPresent()) {
            log.error("Non teacher cannot create a course !");
            return Collections.EMPTY_LIST;
        }
        List<Course> courses = currentTeacher.get().getCourseList();
        return courses.stream()
                .map(c -> new CourseSelectDto(c.getName(),
                                              c.getId(),
                                              c.getStudents().size()))
                .collect(Collectors.toList());
    }

    public ModelAndView create(CourseModel dto) {
        Optional<Teacher> currentTeacher = teacherService.getCurrentTeacher();
        if (!currentTeacher.isPresent()) {
            log.error("Non teacher cannot create a course !");
            return new ModelAndView(Templates.ERROR_PAGE.getTemplateName());
        }
        Course course = dto.getCourse();
        if (course.getId() == null) {
            currentTeacher.get().getCourseList().add(course);
        }

        Course savedCourse = courseRepository.save(course);

        CourseModel courseModel = new CourseModel();
        courseModel.setCourse(savedCourse);
        courseModel.setInfoMessage("Kurz bol uspesne ulozeny.");

        return getMyCoursesList();
    }

    public ModelAndView getCreateOrEditUi(final Long id) {
        if (id == null) {
            return initCreateUi();
        }
        return initEditUi(id);
    }

    private ModelAndView initEditUi(Long id) {
        Course course = courseRepository.findById(id).get();

        CourseModel courseModel = new CourseModel();
        courseModel.setCourse(course);
        ModelAndView model = new ModelAndView(Templates.COURSE_EDIT_PAGE.getTemplateName());
        model.addObject("model", courseModel);
        return model;
    }

    private ModelAndView initCreateUi() {
        CourseModel courseModel = new CourseModel();
        courseModel.setCourse(createInitialCourse());
        ModelAndView model = new ModelAndView(Templates.COURSE_EDIT_PAGE.getTemplateName());
        model.addObject("model", courseModel);
        return model;
    }

    private Course createInitialCourse() {
        Course course = new Course();
        course.setName("");
        return course;
    }
}
