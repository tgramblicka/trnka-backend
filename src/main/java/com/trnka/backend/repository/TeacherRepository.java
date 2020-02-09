package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trnka.backend.domain.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUser_Username(final String username);
}
