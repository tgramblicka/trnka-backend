package com.trnka.backend.controller;

import com.trnka.backend.service.sync.SyncConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.restapi.dto.SyncConfigDto;

@RestController
@RequestMapping(RestApiPaths.PATH_UI_SYNC)
@RequiredArgsConstructor
public class UiSyncController {

    private final SyncConfigService service;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
        return service.getConfigForUi();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute SyncConfigDto dto) {
        return service.updateConfigFromUi(dto);
    }
}
