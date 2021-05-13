package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trnka.backend.domain.sync.SyncConfig;

@Repository
public interface SyncConfigRepository extends JpaRepository<SyncConfig, Long> {

}
