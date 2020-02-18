package com.trnka.backend.service;

import java.util.Collections;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.Course;
import com.trnka.backend.domain.Student;
import com.trnka.backend.dto.student.StudentListModel;
import com.trnka.backend.dto.student.StudentModel;
import com.trnka.backend.repository.CourseRepository;
import com.trnka.backend.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService {

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    public StudentService(final CourseRepository courseRepository,
                          final StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public ModelAndView getStudentsListUi(final Long courseId) {
        Optional<Course> coursById = courseRepository.findById(courseId);
        if (!coursById.isPresent()) {
            log.error(courseNotFoundErrorMsg(courseId));
            StudentListModel model = new StudentListModel(Collections.EMPTY_LIST,
                                                          courseId,
                                                          StringUtils.EMPTY,
                                                          courseNotFoundErrorMsg(courseId));
            return new ModelAndView(Templates.STUDENTS_LIST.getTemplateName()).addObject("model", model);
        }
        Course course = coursById.get();
        StudentListModel model = new StudentListModel(course.getStudents(),
                                                      courseId,
                                                      course.getName(),
                                                      null);

        return new ModelAndView(Templates.STUDENTS_LIST.getTemplateName()).addObject("model", model);
    }

    public ModelAndView getCreateOrEditUi(final Long courseId,
                                          final Long studentId) {
        Optional<Course> courseById = courseRepository.findById(courseId);
        if (!courseById.isPresent()) {
            log.error(courseNotFoundErrorMsg(courseId));
            StudentModel model = new StudentModel();
            model.setErrorMessage(courseNotFoundErrorMsg(courseId));
            return new ModelAndView(Templates.STUDENT_EDIT_PAGE.getTemplateName()).addObject("model", model);
        }

        if (studentId == null) {
            return initCreateUi(courseById.get());
        }
        return initEditUi(studentId);
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
            model.setErrorMessage(msg);
            return new ModelAndView().addObject("model", model);
        }

        StudentModel model = new StudentModel();
        model.setStudent(studentById.get());

        return new ModelAndView(Templates.STUDENT_EDIT_PAGE.getTemplateName()).addObject("model", model);
    }

    private ModelAndView initCreateUi(final Course course) {

        Student student = new Student();
        student.setCourse(course);

        StudentModel model = new StudentModel();
        model.setStudent(student);

        return new ModelAndView(Templates.STUDENT_EDIT_PAGE.getTemplateName()).addObject("model", model);
    }

    @Transactional
    public ModelAndView createStudent(final StudentModel model) {
        Student student = model.getStudent();

        if (student.getId() == null) {
            student.getCourse().getStudents().add(student);
        }

        Student savedStudent = studentRepository.save(student);

        model.setStudent(savedStudent);
        model.setInfoMessage("Student bol vytvoreny !");
        return getStudentsListUi(student.getCourse().getId());
    }

    public ModelAndView delete(final Long courseId,
                               final Long studentId) {
        studentRepository.deleteById(studentId);
        return getStudentsListUi(courseId);
    }
}
