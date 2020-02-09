package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trnka.backend.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
