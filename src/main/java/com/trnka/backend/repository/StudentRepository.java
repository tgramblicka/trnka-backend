package com.trnka.backend.repository;

import java.util.List;
import java.util.Optional;

import com.trnka.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trnka.backend.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByDeviceIdentificationCode(String code);

    List<Student> findAllByCreatedBy(User user);
}
