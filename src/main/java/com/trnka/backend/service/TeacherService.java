package com.trnka.backend.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.trnka.backend.domain.Teacher;
import com.trnka.backend.repository.TeacherRepository;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;

    public TeacherService(final TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Optional<Teacher> getCurrentTeacher() {
        UserDetails session = UserSession.currentUserDetails();
        return teacherRepository.findByUser_Username(session.getUsername());
    }

}
