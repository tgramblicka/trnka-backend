package com.trnka.backend.dto.student;

import com.trnka.backend.domain.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentModel {
    private Student student;
    private String infoMessage;
    private String errorMessage;
}
