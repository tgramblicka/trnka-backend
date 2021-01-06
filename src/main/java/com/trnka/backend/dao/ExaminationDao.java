package com.trnka.backend.dao;

import com.trnka.backend.domain.Examination;
import com.trnka.backend.repository.ExaminationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExaminationDao {

    private final ExaminationRepository examinationRepository;

    public String getLettersSerquence(Examination examination) {
        return examination.getExaminationSteps()
                .stream()
                .map(examinationStep -> examinationStep.getBrailCharacter().getLetter())
                .collect(Collectors.joining(","));
    }

}
