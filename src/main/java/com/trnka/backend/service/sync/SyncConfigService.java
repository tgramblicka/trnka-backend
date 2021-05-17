package com.trnka.backend.service.sync;

import com.trnka.backend.config.Templates;
import org.springframework.stereotype.Service;

import com.trnka.backend.domain.sync.SyncConfig;
import com.trnka.backend.repository.SyncConfigRepository;
import com.trnka.restapi.dto.SyncConfigDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class SyncConfigService {

    private final SyncConfigRepository syncConfigRepository;


    public ModelAndView getConfigForUi() {
        SyncConfigDto dto = getConfig();
        ModelAndView mv = new ModelAndView(Templates.SYNC_MANAGEMENT.getTemplateName());
        return mv.addObject("model", dto);
    }

    public ModelAndView updateConfigFromUi(SyncConfigDto dto) {
        SyncConfigDto updatedDto = updateConfig(dto);
        ModelAndView mv = new ModelAndView(Templates.SYNC_MANAGEMENT.getTemplateName());
        return mv.addObject("model", updatedDto);
    }

    public SyncConfigDto getConfig() {
        SyncConfig config = syncConfigRepository.findAll().stream().findFirst().orElse(new SyncConfig());
        return toDto(config);
    }

    private SyncConfigDto updateConfig(SyncConfigDto dto) {
        SyncConfig config = syncConfigRepository.findAll().stream().findFirst().orElse(new SyncConfig());
        config.setEnableDownloadFromServerToDevice(dto.getEnableDownloadFromServerToDevice());
        config.setEnableUploadFromDeviceToServer(dto.getEnableUploadFromDeviceToServer());
        SyncConfig saved = syncConfigRepository.save(config);
        return toDto(saved);
    }


    private SyncConfigDto toDto(SyncConfig config) {
        SyncConfigDto dto = new SyncConfigDto();
        dto.setEnableDownloadFromServerToDevice(config.getEnableDownloadFromServerToDevice());
        dto.setEnableUploadFromDeviceToServer(config.getEnableUploadFromDeviceToServer());
        dto.setCreatedOn(config.getCreatedOn());
        dto.setUpdatedOn(config.getUpdatedOn());
        return dto;
    }

}
