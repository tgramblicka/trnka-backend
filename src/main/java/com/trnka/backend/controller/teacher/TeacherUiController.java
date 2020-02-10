package com.trnka.backend.controller.teacher;

import com.trnka.backend.controller.RestApiPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.dto.TestModel;
import com.trnka.backend.service.CourseService;
import com.trnka.backend.service.testing.TestingListService;
import com.trnka.backend.service.testing.TestingUiService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(RestApiPaths.PATH_UI_TEACHER)
public class TeacherUiController {
    public static final String HOME_PATH = "home";
    public static final String TESTING_PATH = "testing";
    public static final String CREATE_TEST = "testing/create";
    public static final String EDIT_TEST = "testing/edit";
    public static final String CREATE_EXAMINATION_STEP = "testing/examination-step";

    public static final String LEARNING_PATH = "learning";
    public static final String RESULTS_PATH = "results";
    public static final String DICTIONARY_PATH = "dictionary";

    @Autowired
    private CourseService courseService;
    @Autowired
    private TestingUiService testingUiService;
    @Autowired
    private TestingListService testingListService;

    @RequestMapping(method = RequestMethod.GET, path = HOME_PATH)
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("teacher-home");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

    @RequestMapping(method = RequestMethod.GET, path = DICTIONARY_PATH)
    public ModelAndView dictionary() {
        ModelAndView mv = new ModelAndView("teacher-dictionary");
        return mv.addObject("teacherName", "Jan Testovaci");
    }


    @RequestMapping(method = RequestMethod.GET, path = TESTING_PATH)
    public ModelAndView testManagement() {
        return testingListService.getExaminationsForCurrentTeacher();
    }

    @RequestMapping(method = RequestMethod.GET, path = CREATE_TEST)
    public ModelAndView getCreateTest() {
        return testingUiService.getCreateTestUiModel();
    }

    @RequestMapping(method = RequestMethod.POST, path = CREATE_EXAMINATION_STEP)
    public ModelAndView createExaminationModel(@ModelAttribute TestModel dto) {
        return testingUiService.createExaminationStep(dto.getExaminationStepCreateDto());
    }

    @RequestMapping(method = RequestMethod.POST, path = EDIT_TEST)
    public ModelAndView createTest(@ModelAttribute TestModel dto) {
        return testingUiService.createOrEditTest(dto);
    }

    @RequestMapping(method = RequestMethod.GET, path = EDIT_TEST)
    public ModelAndView getEditTest(@RequestParam Long id) {
        return testingUiService.getEditTestUiModel(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = LEARNING_PATH)
    public ModelAndView learningManagement() {
        ModelAndView mv = new ModelAndView("teacher-learning");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

    @RequestMapping(method = RequestMethod.GET, path = RESULTS_PATH)
    public ModelAndView resultsManagement() {
        ModelAndView mv = new ModelAndView("teacher-results");
        return mv.addObject("teacherName", "Jan Testovaci");
    }

}
