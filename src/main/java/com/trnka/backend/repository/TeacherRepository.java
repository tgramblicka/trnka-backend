package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trnka.backend.domain.Teacher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUser_Username(final String username);
}
