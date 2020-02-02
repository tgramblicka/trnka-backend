package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trnka.backend.domain.BrailCharacter;

@Repository
public interface BrailRepository extends JpaRepository<BrailCharacter, Long> {
}
