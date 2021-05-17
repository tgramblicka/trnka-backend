package com.trnka.backend.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.controller.RestApiPaths;
import com.trnka.backend.service.testing.ExaminationListService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(RestApiPaths.PATH_UI_TEACHER)
public class TeacherUiController {
    public static final String HOME_PATH = "home";
    public static final String TESTING_PATH = "testing";
    public static final String RESULTS_PATH = "results";
    public static final String DICTIONARY_PATH = "dictionary";


    @Autowired
    private ExaminationListService examinationListService;

    @RequestMapping(method = RequestMethod.GET, path = HOME_PATH)
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("teacher-home");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

    @RequestMapping(method = RequestMethod.GET, path = DICTIONARY_PATH)
    public ModelAndView dictionary() {
        ModelAndView mv = new ModelAndView("dictionary");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

    @RequestMapping(method = RequestMethod.GET, path = TESTING_PATH)
    public ModelAndView testManagement() {
        return examinationListService.getExaminationsForCurrentTeacher();
    }

    @RequestMapping(method = RequestMethod.GET, path = RESULTS_PATH)
    public ModelAndView resultsManagement() {
        ModelAndView mv = new ModelAndView("teacher-results");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

}
