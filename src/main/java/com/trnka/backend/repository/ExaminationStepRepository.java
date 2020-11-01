package com.trnka.backend.repository;

import com.trnka.backend.domain.ExaminationStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationStepRepository extends JpaRepository<ExaminationStep, Long> {
}
