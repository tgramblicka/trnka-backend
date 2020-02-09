package com.trnka.backend.controller.teacher;

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
@RequestMapping(RestApiPaths.PATH_UI_TEACHER)
public class CourseUiController {
    public static final String PATH_COURSE = "course";

    private CourseService courseService;

    public CourseUiController(final CourseService courseService) {
        this.courseService = courseService;
    }


    @RequestMapping(method = RequestMethod.GET, path = PATH_COURSE)
    public ModelAndView editTestOpen(@RequestParam(required = false) Long id) {
        return courseService.getCreateOrEditUi(id);
    }

    @RequestMapping(method = RequestMethod.POST, path = PATH_COURSE)
    public ModelAndView createTest(@ModelAttribute CourseModel dto) {
        return courseService.create(dto);
    }
}
