package com.trnka.backend.controller;

import com.trnka.backend.service.VstClassService;
import com.trnka.backend.service.VstTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(RestApiPaths.PATH_UI_TEACHER)
public class TeacherUiController {

    @Autowired
    private VstClassService vstClassService;
    @Autowired
    private VstTestService  vstTestService;

    @RequestMapping(method = RequestMethod.GET, path = "/home")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("teacher-home");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/dictionary")
    public ModelAndView dictionary() {
        ModelAndView mv = new ModelAndView("teacher-dictionary");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/class")
    public ModelAndView classManagement() {
        ModelAndView mv = new ModelAndView("teacher-class");
        return mv.addObject("teacherName", "Jan Testovaci").addObject("classes", vstClassService.getClasses(1L));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/testing")
    public ModelAndView testManagement() {
        ModelAndView mv = new ModelAndView("teacher-tests");
        return mv.addObject("teacherName", "Jan Testovaci").addObject("tests", vstTestService.getTests(1L));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/learning")
    public ModelAndView learningManagement() {
        ModelAndView mv = new ModelAndView("teacher-learning");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/results")
    public ModelAndView resultsManagement() {
        ModelAndView mv = new ModelAndView("teacher-results");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

}
