package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trnka.backend.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findByUser_Username(final String username);
}
