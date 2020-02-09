package com.trnka.backend.controller;

import com.trnka.backend.controller.teacher.TeacherUiController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
@RequestMapping(RestApiPaths.PATH_UI)
public class LoginController {

    public static final String LOGIN = "login";
    public static final String LOGIN_ERROR = "login-error";
    public static final String LOGOUT = "logout";
    // https://www.thymeleaf.org/doc/articles/springsecurity.html

    @RequestMapping(method = RequestMethod.GET, path = LOGIN)
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");
        return mv.addObject("teacherName", "Jan Testovaci").addObject("homePath", RestApiPaths.PATH_UI_TEACHER + TeacherUiController.HOME_PATH);
    }

    @RequestMapping(method = RequestMethod.GET, path = LOGIN_ERROR)
    public ModelAndView loginError() {
        ModelAndView mv = new ModelAndView("login");
        return mv.addObject("loginError", true);
    }

    @RequestMapping(method = RequestMethod.POST, path = LOGOUT)
    public ModelAndView logout() {
        ModelAndView mv = new ModelAndView("login");
        return mv.addObject("logout", true);
    }

}
