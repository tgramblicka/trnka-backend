package com.trnka.backend.service;

import org.springframework.stereotype.Service;

import com.trnka.backend.domain.User;
import com.trnka.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public User getCurrentUser() {
//        UserDetails session = UserSession.currentUserDetails();
        return userRepository.findByUsername("a").get(); // todo revert
        //        return userRepository.findByUser_Username(session.getUsername());
    }
}
