package com.trnka.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trnka.backend.DummyData;
import com.trnka.backend.dto.VstTestDto;

@Service
public class VstTestService {

    public List<VstTestDto> getTests(Long teacherId) {
        return new DummyData().getTests(10);
    }

}
