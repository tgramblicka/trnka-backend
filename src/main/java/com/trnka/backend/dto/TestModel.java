package com.trnka.backend.dto;

import java.util.List;

import com.trnka.backend.domain.Examination;
import com.trnka.backend.dto.course.CourseSelectDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestModel {
    private Examination examination;
    private ExaminationStepCreateDto examinationStepCreateDto;
    private List<CourseSelectDto> courses;
    private Long selectedCourseId;

}
