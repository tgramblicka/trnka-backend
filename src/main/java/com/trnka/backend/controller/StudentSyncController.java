package com.trnka.backend.controller;

import com.trnka.backend.service.sync.StudentSyncService;
import com.trnka.restapi.dto.StudentDTO;
import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.dto.statistics.DeviceStatisticsSyncDto;
import com.trnka.restapi.endpoint.SyncEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StudentSyncController implements SyncEndpoint {

    @Autowired
    private StudentSyncService studentSyncService;

    @Override
    public StudentDTO getStudent(final String s) {
        return null;
    }

    @Override
    public SyncDto syncAll() {
        return studentSyncService.getSyncDto();
    }

    @Override
    public Void updateExaminationStatisticsToAllStudents(final DeviceStatisticsSyncDto deviceStatisticsSyncDto) {
        // todo implement
        return null;
    }
}
