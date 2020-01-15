package com.trnka.backend.service;

import java.util.List;

import com.trnka.backend.DummyData;
import com.trnka.backend.dto.VstClassDto;
import org.springframework.stereotype.Service;

@Service
public class VstClassService {

    public List<VstClassDto> getClasses(Long teacherId) {
        return new DummyData().getClasses(5);
    }

}
