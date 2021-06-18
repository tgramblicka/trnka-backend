package com.trnka.backend.controller;

import com.trnka.backend.config.Templates;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(RestApiPaths.PATH_UI_MANUAL)
@RequiredArgsConstructor
public class ManualController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
        ModelAndView mv = new ModelAndView(Templates.MANUAL.getTemplateName());
        return mv;
    }

}
