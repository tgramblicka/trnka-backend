package com.trnka.backend.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.trnka.backend.domain.Teacher;
import com.trnka.backend.repository.TeacherRepository;
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
    private final TeacherRepository teacherRepository;

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

        createTeacherWhenNeeded(type, savedUser);
        return savedUser;
    }

    private void createTeacherWhenNeeded(final UserType type, final User savedUser) {
        Set<UserType> userTypesWithTeacherRole = Stream.of(UserType.TEACHER, UserType.ADMIN).collect(Collectors.toSet());
        if (userTypesWithTeacherRole.contains(type)){
            Teacher teacher = new Teacher();
            teacher.setUser(savedUser);
            teacherRepository.save(teacher);
        }
    }

    public User updateUserFromDetachedEntity(User detachedUser) {
        // clear and re-add all the authorities
        detachedUser.getAuthorities().clear();
        detachedUser.addAuthority(detachedUser.getType());
        return userRepository.save(detachedUser);
    }

}
