package com.trnka.backend.service;

import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;

public class ErrorPage {

    public static ModelAndView create(String message) {
        return new ModelAndView(Templates.ERROR_PAGE.getTemplateName()).addObject("errorMessage", message);
    }
}
