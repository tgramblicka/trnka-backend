package com.trnka.backend.dto;

import com.trnka.backend.domain.Examination;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExaminationsPageModel {
    private List<Examination> exams = new ArrayList<>();
}