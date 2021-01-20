package com.trnka.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(RestApiPaths.PATH_UI_ADMIN_USER_MNGMT)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getUsersList() {
        return adminService.getUsersList();
    }

}
