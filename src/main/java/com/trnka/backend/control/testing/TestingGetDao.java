package com.trnka.backend.control.testing;

import com.trnka.restapi.dto.TestContentDto;
import com.trnka.restapi.dto.TestDto;

public class TestingGetDao {

    public TestDto get(final Long studentId){
        TestDto dto = new TestDto();
        dto.setContent(new TestContentDto());
        dto.setStudentId(10L);
        dto.setTestId(1L);
        return dto;
    }
}
