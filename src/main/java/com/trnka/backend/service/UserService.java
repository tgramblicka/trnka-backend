package com.trnka.backend.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.backend.domain.User;
import com.trnka.backend.domain.UserType;
import com.trnka.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User getCurrentUser() {
        UserDetails session = UserSession.currentUserDetails();
        Optional<User> sessionUser = userRepository.findByUsername(session.getUsername());
        if (!sessionUser.isPresent()) {
            log.error("User from session with username {} not found!", session.getUsername());
            throw new SessionAuthenticationException("User not in session!");
        }
        return sessionUser.get();
        // return userRepository.findByUsername("a").get(); //
    }

    public User createNewUser(String username,
                              String encodedPwd,
                              UserType type) {
        // set default password, this will be manual set then by admin
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPwd);
        user.setType(type);
        user.setEnabled(true);
        User savedUser = userRepository.save(user);
        savedUser.addAuthority(user.getType());
        return savedUser;
    }

    public User updateUserFromDetachedEntity(User detachedUser) {
        // clear and re-add all the authorities
        detachedUser.getAuthorities().clear();
        detachedUser.addAuthority(detachedUser.getType());
        return userRepository.save(detachedUser);
    }

}
