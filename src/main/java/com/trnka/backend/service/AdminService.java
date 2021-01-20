package com.trnka.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.dto.UsersListDto;
import com.trnka.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public ModelAndView getUsersList() {
        ModelAndView model = new ModelAndView(Templates.ADMIN_USER_LIST.getTemplateName());
        UsersListDto dto = new UsersListDto();
        dto.setUsers(userRepository.findAll());
        return model.addObject("model", dto);
    }
}
