package com.trnka.backend.service;

import com.trnka.backend.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.dto.UsersListDto;
import com.trnka.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

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

    public ModelAndView getUserEditUI(final Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            String msg = String.format("User with id %s not found!", id);
            log.error(msg);
            return ErrorPage.create(msg);
        }
        ModelAndView model = new ModelAndView(Templates.ADMIN_USER_EDIT.getTemplateName());
        return model.addObject("model", user.get());
    }

    public ModelAndView getUserCreateUI() {
        ModelAndView model = new ModelAndView(Templates.ADMIN_USER_CREATE.getTemplateName());
        User user = new User();
        return model.addObject("model", user);
    }
}
