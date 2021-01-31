package com.trnka.backend.dto.student;

import java.util.ArrayList;
import java.util.List;

import com.trnka.backend.domain.Student;
import com.trnka.backend.dto.course.CourseSelectDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentModel {
    private Student student;
    private String infoMessage;
    private List<String> errors = new ArrayList<>();
    private List<CourseSelectDto> coursesToSelect;
}
