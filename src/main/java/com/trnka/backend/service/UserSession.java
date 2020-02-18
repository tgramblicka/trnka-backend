package com.trnka.backend.service;

import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSession {

    public static UserDetails currentUserDetails() {
        User user = new User("a",
                             "a",
                             Collections.emptyList());
        return user;

    }

    public static UserDetails currentUserDetails1() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof UserDetails ? (UserDetails) principal
                                                    : null;
        }
        return null;
    }
}
