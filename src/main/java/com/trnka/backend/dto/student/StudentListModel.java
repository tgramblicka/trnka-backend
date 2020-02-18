package com.trnka.backend.dto.student;

import com.trnka.backend.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentListModel {
    private List<Student> students;
    private Long courseId;
    private String errorMessage;
}
