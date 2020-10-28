package com.trnka.backend.controller.teacher;

import com.trnka.backend.dto.course.ExaminationStepReorderDto;
import com.trnka.backend.service.ExaminationStepService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.controller.RestApiPaths;
import com.trnka.backend.dto.course.CourseModel;
import com.trnka.backend.service.CourseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(RestApiPaths.PATH_UI_COURSE)
public class CourseUiController {
    public static final String PATH_MY_COURSES = "/all";
    public static final String PATH_BRAIL_UP = "/brail/up";
    public static final String PATH_BRAIL_DOWN = "/brail/down";

    private CourseService courseService;
    private ExaminationStepService examinationStepService;

    public CourseUiController(final CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.GET, path = PATH_MY_COURSES)
    public ModelAndView classManagement() {
        return courseService.getMyCoursesList();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView editTestOpen(@RequestParam(required = false) Long id) {
        return courseService.getCreateOrEditUi(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView createTest(@ModelAttribute CourseModel dto) {
        return courseService.create(dto);
    }

    @RequestMapping(method = RequestMethod.PUT, path = PATH_BRAIL_UP)
    public ModelAndView moveBrailUp(@ModelAttribute ExaminationStepReorderDto dto) {
        return examinationStepService.moveExaminationStepUpAndGetTemplate(dto);
    }

    @RequestMapping(method = RequestMethod.PUT, path = PATH_BRAIL_DOWN)
    public ModelAndView moveBrailDown(@ModelAttribute ExaminationStepReorderDto dto) {
        return examinationStepService.moveExaminationStepDownAndGetTemplate(dto);
    }
}
