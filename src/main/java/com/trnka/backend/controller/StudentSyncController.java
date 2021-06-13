package com.trnka.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.trnka.backend.service.sync.ExaminationStatisticSyncService;
import com.trnka.backend.service.sync.StudentSyncService;
import com.trnka.backend.service.sync.SyncConfigService;
import com.trnka.restapi.dto.StudentDTO;
import com.trnka.restapi.dto.SyncConfigDto;
import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.dto.statistics.DeviceStatisticsSyncDto;
import com.trnka.restapi.endpoint.SyncEndpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StudentSyncController implements SyncEndpoint {

    private final StudentSyncService studentSyncService;
    private final ExaminationStatisticSyncService examinationStatisticSyncService;
    private final SyncConfigService syncConfigService;

    //TODO delete
    @Override
    public StudentDTO getStudent(final String s) {
        return null;
    }

    @Override
    public SyncDto getSyncDto() {
        return studentSyncService.getSyncDto();
    }

    @Override
    public Boolean updateExaminationStatisticsToAllStudents(final DeviceStatisticsSyncDto deviceStatisticsSyncDto) {
        return examinationStatisticSyncService.uploadStatsFromDeviceToServer(deviceStatisticsSyncDto);
    }

    @Override public SyncConfigDto getConfig() {
        return syncConfigService.getConfig();
    }
}
