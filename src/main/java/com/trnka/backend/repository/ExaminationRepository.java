package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trnka.backend.domain.Examination;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    Examination findByName(String name);

}
