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

import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private static final String MSG_COURSE_CREATER = "Kurz bol úspešne vytvorený.";
    private static final String MSG_COURSE_UPDATED = "Kurz bol úspešne upravený.";
    private static final String MSG_COURSE_DOES_NOT_EXIST = "Trieda s tymto ID neexistuje!";
    private static final String MSG_COURSE_EXISTS = "Trieda s tymto nazvoms uz existuje!";
    private static final String MSG_COURSE_NAME_WRONG = "Nazov triedy musi mat aspon 4 znaky a najviac 50 znakov!";

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
        return courses.stream()
                .map(c -> new CourseSelectDto(c.getName(),
                                              c.getId(),
                                              c.getStudents().size()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ModelAndView createOrEdit(CourseModel dto) {
        Optional<Teacher> currentTeacher = teacherService.getCurrentTeacher();
        if (!currentTeacher.isPresent()) {
            log.error("Non teacher cannot create a courseDto !");
            return new ModelAndView(Templates.ERROR_PAGE.getTemplateName());
        }
        Course courseDto = dto.getCourse();
        // creation
        if (courseDto.getId() == null) {
            return createCourse(currentTeacher, courseDto);
            // update
        } else {
            return editCourse(courseDto);
        }
    }

    private ModelAndView editCourse(final Course courseDto) {
        Course course = courseRepository.findById(courseDto.getId()).get();
        if (course == null) {
            log.error("Course with {} does not exits!", courseDto.getId());
            return initUi(initModel(createInitialCourse(), null, MSG_COURSE_DOES_NOT_EXIST));
        }
        String errorMsg = validateCourseName(courseDto.getName(), null);
        if (errorMsg != null) {
            return initUi(initModel(course, null, errorMsg));
        }
        return initUi(initModel(course, MSG_COURSE_UPDATED, null));
    }

    private ModelAndView createCourse(final Optional<Teacher> currentTeacher,
                                      final Course courseDto) {
        Course courseEntity;
        String errorMsg = validateCourseName(courseDto.getName(), null);
        if (errorMsg != null) {
            return initUi(initModel(createInitialCourse(), null, errorMsg));
        }
        currentTeacher.get().getCourseList().add(courseDto);
        courseEntity = courseRepository.save(courseDto);
        return initUi(initModel(courseEntity, MSG_COURSE_CREATER, null));
    }

    public ModelAndView getCreateOrEditUi(final Long id) {
        if (id == null) {
            return initUi(initModel(createInitialCourse(), null, null));
        }
        Course course = courseRepository.findById(id).get();
        return initUi(initModel(course, null, null));
    }

    private String validateCourseName(String newCourseName,
                                      String oldCourseName) {

        if (StringUtils.isEmpty(newCourseName)) {
            return MSG_COURSE_NAME_WRONG;
        }
        if (newCourseName.length() < 4 || newCourseName.length() > 50) {
            return MSG_COURSE_NAME_WRONG;
        }

        // when username changed - check whether does not exist user with same username
        if (!newCourseName.equals(oldCourseName)) {
            Course foundByNewCourseName = courseRepository.findByName(newCourseName);
            if (foundByNewCourseName != null) {
                return MSG_COURSE_EXISTS;
            }
        }
        return null;
    }

    private ModelAndView initUi(CourseModel courseModel) {
        ModelAndView mv = new ModelAndView(Templates.COURSE_EDIT_PAGE.getTemplateName());
        mv.addObject("model", courseModel);
        return mv;
    }

    private CourseModel initModel(Course course,
                                  String infoMessage,
                                  String errorMessage) {
        CourseModel courseModel = new CourseModel();
        courseModel.setCourse(course);
        courseModel.setErrorMessage(errorMessage);
        courseModel.setInfoMessage(infoMessage);
        return courseModel;
    }

    private Course createInitialCourse() {
        Course course = new Course();
        course.setName("");
        return course;
    }

}
