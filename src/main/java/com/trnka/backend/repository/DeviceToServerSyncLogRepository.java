package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trnka.backend.domain.sync.DeviceToServerSyncLog;

@Repository
public interface DeviceToServerSyncLogRepository extends JpaRepository<DeviceToServerSyncLog, Long> {

}
