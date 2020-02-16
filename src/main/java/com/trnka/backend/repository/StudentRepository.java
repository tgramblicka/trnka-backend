package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trnka.backend.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
