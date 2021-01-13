package com.trnka.backend.dao;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trnka.backend.domain.statistic.ExaminationStatistic;
import com.trnka.backend.domain.statistic.ExaminationStepStatistic;

@Service
public class ExaminationStatisticDao {

    public Long getTotalExaminationDurationInMs(ExaminationStatistic statistic) {
        Optional<Long> reduced = statistic.getExaminationStepStatistics()
                .stream()
                .map(ExaminationStepStatistic::getTook)
                .reduce((a,
                         b) -> a + b);
        return reduced.isPresent() ? reduced.get()
                                   : 0L;
    }
}
