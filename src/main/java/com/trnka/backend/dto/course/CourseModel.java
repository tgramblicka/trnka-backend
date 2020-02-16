package com.trnka.backend.dto.course;

import com.trnka.backend.domain.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseModel {
    private Course course;
    private String infoMessage;
    private String errorMessage;

}
