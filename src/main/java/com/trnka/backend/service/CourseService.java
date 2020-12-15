package com.trnka.backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.Course;
import com.trnka.backend.domain.Teacher;
import com.trnka.backend.dto.course.CourseModel;
import com.trnka.backend.dto.course.CourseSelectDto;
import com.trnka.backend.repository.CourseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final TeacherService teacherService;
    private final CourseRepository courseRepository;

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
        return courses.stream().map(c -> new CourseSelectDto(c.getName(), c.getId(), c.getStudents().size())).collect(Collectors.toList());
    }

    @Transactional public ModelAndView create(CourseModel dto) {
        Optional<Teacher> currentTeacher = teacherService.getCurrentTeacher();
        if (!currentTeacher.isPresent()) {
            log.error("Non teacher cannot create a courseDto !");
            return new ModelAndView(Templates.ERROR_PAGE.getTemplateName());
        }
        Course courseDto = dto.getCourse();

        Course courseEntity;
        if (courseDto.getId() == null) {
            currentTeacher.get().getCourseList().add(courseDto);
            courseEntity = courseRepository.save(courseDto);
        } else {
            courseEntity = updateCourse(courseDto);
        }

        CourseModel courseModel = new CourseModel();
        courseModel.setCourse(courseEntity);
        courseModel.setInfoMessage("Kurz bol uspesne ulozeny.");

        return getMyCoursesList();
    }

    private Course updateCourse(Course dto) {
        Course course = courseRepository.findById(dto.getId()).get();
        course.setName(dto.getName());
        return course;
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
