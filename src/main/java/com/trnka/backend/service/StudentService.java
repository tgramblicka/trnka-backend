package com.trnka.backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Validation;
import javax.validation.Validator;

import com.trnka.backend.dto.course.CourseSelectDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.Course;
import com.trnka.backend.domain.Student;
import com.trnka.backend.domain.User;
import com.trnka.backend.domain.UserType;
import com.trnka.backend.dto.student.StudentListModel;
import com.trnka.backend.dto.student.StudentModel;
import com.trnka.backend.repository.CourseRepository;
import com.trnka.backend.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final UserService userService;
    private final CourseService courseService;

    @PersistenceContext
    private EntityManager em;

    public ModelAndView getAllStudentsUi() {
        User currentUser = userService.getCurrentUser();

        List<Student> students;
        if (currentUser.getType().equals(UserType.ADMIN)) {
            students = studentRepository.findAll();
        } else {
            students = studentRepository.findAllByCreatedBy(currentUser);
        }
        return new ModelAndView(Templates.STUDENTS_LIST.getTemplateName()).addObject("model", students);
    }

    public ModelAndView getCourseStudentListUi(final Long courseId) {
        Optional<Course> coursById = courseRepository.findById(courseId);
        if (!coursById.isPresent()) {
            log.error(courseNotFoundErrorMsg(courseId));
            StudentListModel model = new StudentListModel(Collections.EMPTY_LIST,
                                                          courseId,
                                                          "",
                                                          courseNotFoundErrorMsg(courseId));
            return new ModelAndView(Templates.COURSE_STUDENTS_LIST.getTemplateName()).addObject("model", model);
        }
        Course course = coursById.get();
        StudentListModel model = new StudentListModel(course.getStudents(),
                                                      courseId,
                                                      course.getName(),
                                                      null);

        return new ModelAndView(Templates.COURSE_STUDENTS_LIST.getTemplateName()).addObject("model", model);
    }

    public ModelAndView getCreateOrEditUi(final Long id) {
        if (id == null) {
            return initCreateUi();
        }
        return initEditUi(id);
    }

    private String courseNotFoundErrorMsg(final Long courseId) {
        return String.format("Course with id: %s not found!", courseId);
    }

    private ModelAndView initEditUi(final Long studentId) {
        Optional<Student> studentById = studentRepository.findById(studentId);
        if (!studentById.isPresent()) {
            String msg = String.format("Student with id: %s not found", studentId);
            log.error(msg);
            StudentModel model = new StudentModel();
            model.getErrors().add(msg);
            return new ModelAndView().addObject("model", model);
        }

        StudentModel model = new StudentModel();
        model.setStudent(studentById.get());
        model.setCoursesToSelect(courseService.getMyCoursesSelection());

        return new ModelAndView(Templates.STUDENT_EDIT_PAGE.getTemplateName()).addObject("model", model);
    }

    private ModelAndView initCreateUi() {
        Student student = new Student();
        StudentModel model = new StudentModel();
        model.setStudent(student);
        model.setCoursesToSelect(courseService.getMyCoursesSelection());
        return new ModelAndView(Templates.STUDENT_EDIT_PAGE.getTemplateName()).addObject("model", model);
    }



    private boolean existsAnotherStudentWithSameIdentificationCode(final Student dto){
        Optional<Student> optionalStudent = studentRepository.findByDeviceIdentificationCode(dto.getDeviceIdentificationCode());
        if (optionalStudent.isPresent()){
            return !optionalStudent.get().getId().equals(dto.getId());
        }
        return false;
    }

    private ModelAndView getEditPage(StudentModel model, List<CourseSelectDto> coursesToSelect){
        model.setCoursesToSelect(coursesToSelect);
        return new ModelAndView(Templates.STUDENT_EDIT_PAGE.getTemplateName()).addObject("model", model);
    }

    @Transactional
    public ModelAndView createOrUpdateStudent(final StudentModel model) {
        Student studentDto = model.getStudent();
        model.getErrors().clear();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(model.getStudent()).forEach(v -> model.getErrors().add(v.getMessage()));
        if (!CollectionUtils.isEmpty(model.getErrors())) {
            return getEditPage(model, courseService.getMyCoursesSelection());
        }


        if (existsAnotherStudentWithSameIdentificationCode(model.getStudent())) {
            model.getErrors().add("Student s rovnakym prihlasovacím kodom uz existuje !");
            return getEditPage(model, courseService.getMyCoursesSelection());
        }


        Student studentEntity;
        if (studentDto.getId() == null) {
            studentDto.setCreatedBy(userService.getCurrentUser());
            studentDto.getCourse().getStudents().add(studentDto);
            studentEntity = studentRepository.save(studentDto);
        } else {
            studentEntity = studentRepository.findById(studentDto.getId()).get();
            boolean changedCourse = studentEntity.getCourse().getId() != studentDto.getCourse().getId();
            if (changedCourse){
                Course oldCourse = courseRepository.findById(studentEntity.getCourse().getId()).get();
                oldCourse.getStudents().remove(studentDto);

                Course newCourse = courseRepository.findById(studentDto.getCourse().getId()).get();
                newCourse.getStudents().add(studentEntity);
                studentEntity.setCourse(newCourse);
            }

            studentEntity.setDeviceIdentificationCode(studentDto.getDeviceIdentificationCode());
        }

        model.setStudent(studentEntity);
        model.setInfoMessage("Student bol vytvoreny !");


        // below flush just because the returned UI should be already refreshed
        em.flush();
        em.clear();
        return getAllStudentsUi();
    }

    public ModelAndView delete(final Long id) {
        studentRepository.deleteById(id);
        return getAllStudentsUi();
    }
}
