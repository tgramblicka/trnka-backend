package com.trnka.backend.dto.student;

import com.trnka.backend.domain.Student;
import com.trnka.backend.dto.course.CourseSelectDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentModel {
    private Student student;
    private String infoMessage;
    private String errorMessage;
    private List<CourseSelectDto> coursesToSelect;
}
