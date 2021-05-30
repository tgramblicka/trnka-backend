package com.trnka.backend.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.dto.user.PasswordModel;
import com.trnka.backend.dto.user.UserModel;
import com.trnka.backend.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(RestApiPaths.PATH_UI_ADMIN_USER_MNGMT)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public ModelAndView getUsersList() {
        return adminService.getUsersList();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/edit/{id}")
    public ModelAndView getUpdateUi(@PathVariable Long id){
        return adminService.getCreateOrUpdateUI(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/create")
    public ModelAndView getCreateUi(){
        return adminService.getCreateOrUpdateUI(null);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/edit")
    public ModelAndView createOrUpdateUser(@ModelAttribute UserModel model) {
        return adminService.createOrUpdateUser(model);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/pwd")
    public ModelAndView updateUserPwd(@ModelAttribute PasswordModel model) {
        return adminService.updatePassword(model);
    }





}
