package com.trnka.backend.controller.teacher;

import com.trnka.backend.service.results.StudentExamStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.controller.RestApiPaths;
import com.trnka.backend.dto.student.StudentModel;
import com.trnka.backend.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(RestApiPaths.PATH_UI_STUDENT)
public class StudentUiController {
    public static final String PATH_ALL = "/all"; // todo rename
    public static final String PATH_MY_COURSES = "/all/course/{courseId}";
    public static final String PATH_DELETE = "/delete";
    public static final String PATH_STATISTICS = "/{id}/statistics";

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentExamStatisticService studentExamStatisticService;

    @RequestMapping(method = RequestMethod.GET, path = PATH_MY_COURSES)
    public ModelAndView courseStudents(@PathVariable Long courseId) {
        return studentService.getCourseStudentListUi(courseId);
    }

    @RequestMapping(method = RequestMethod.GET, path = PATH_ALL)
    public ModelAndView allStudents() {
        return studentService.getAllStudentsUi();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get(@RequestParam(name = "id", required = false) Long id) {
        return studentService.getCreateOrEditUi(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(StudentModel model) {
        return studentService.createStudent(model);
    }

    @RequestMapping(method = RequestMethod.GET, path = PATH_DELETE)
    public ModelAndView delete(@RequestParam(name = "id") Long id) {
        return studentService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = PATH_STATISTICS)
    public ModelAndView studentStatistics(@PathVariable Long id) {
        return studentExamStatisticService.getStudentStatistic(id);
    }

}
