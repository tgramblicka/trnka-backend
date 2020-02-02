package com.trnka.backend.service;

import java.util.List;

import com.trnka.backend.DummyData;
import com.trnka.backend.dto.VstClassDto;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    public List<VstClassDto> getCourses(Long teacherId) {
        return new DummyData().getClasses(5);
    }

}
