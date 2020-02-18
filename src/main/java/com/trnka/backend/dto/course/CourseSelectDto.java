package com.trnka.backend.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSelectDto {
    private String name;
    private Long id;
    private Integer studentCount;
}
