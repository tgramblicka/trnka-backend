package com.trnka.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import com.trnka.backend.dto.course.ExaminationStepReorderDto;
import com.trnka.backend.repository.ExaminationRepository;
import com.trnka.backend.repository.ExaminationStepRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExaminationStepService {

    private final ExaminationRepository examinationRepository;
    private final ExaminationStepRepository examinationStepRepository;

    public ModelAndView moveExaminationStepDownAndGetTemplate(final ExaminationStepReorderDto dto) {
        return moveExaminationStepAndGetTemplate(dto, this::moveDown);
    }

    public ModelAndView moveExaminationStepUpAndGetTemplate(final ExaminationStepReorderDto dto) {
        return moveExaminationStepAndGetTemplate(dto, this::moveUp);
    }

    private ModelAndView moveExaminationStepAndGetTemplate(final ExaminationStepReorderDto dto,
                                                           final BiConsumer<Examination, Long> moveConsumer) {
        Optional<Examination> foundExamination = examinationRepository.findById(dto.getExaminationId());
        handleInvalidID(foundExamination);
        Examination examination = foundExamination.get();
        moveConsumer.accept(examination, dto.getExaminationStepId());

        ModelAndView mv = new ModelAndView(Templates.EXAMINATION_STEP_LIST.getTemplateName() +  " :: #examination-step-list");
        mv.addObject("examinationSteps", examination.getExaminationSteps());


        return mv;
    }

    @Transactional
    public void moveUp(final Examination examination,
                       final Long examinationStepId) {
        if (examination.getExaminationSteps().size() < 2) {
            return;
        }
        ExaminationStep step1 = examination.getExaminationSteps().stream().filter(s -> s.getId().equals(examinationStepId)).findFirst().get();
        if (step1.getDisplayOrder() <=1){
            return;
        }
        int step1DisplayOrder = step1.getDisplayOrder();
        int step2DisplayOrder = step1.getDisplayOrder() - 1 > 0 ? step1.getDisplayOrder() - 1
                                                                : examination.getExaminationSteps().size();
        ExaminationStep step2 = examination.getExaminationSteps().stream().filter(s -> s.getDisplayOrder().equals(step2DisplayOrder)).findFirst().get();

        step1.setDisplayOrder(step2DisplayOrder);
        step2.setDisplayOrder(step1DisplayOrder);
        examinationStepRepository.save(step1);
        examinationStepRepository.save(step2);

    }

    @Transactional
    public void moveDown(final Examination examination,
                         final Long examinationStepId) {
        if (examination.getExaminationSteps().size() < 2) {
            return;
        }
        ExaminationStep step1 = examination.getExaminationSteps().stream().filter(s -> s.getId().equals(examinationStepId)).findFirst().get();
        if ( step1.getDisplayOrder() == examination.getExaminationSteps().size()){
            return;
        }

        int step1DisplayOrder = step1.getDisplayOrder();
        int step2DisplayOrder = step1.getDisplayOrder() + 1 > examination.getExaminationSteps().size() ? step1.getDisplayOrder() + 1
                                                                                                       : examination.getExaminationSteps().size();
        ExaminationStep step2 = examination.getExaminationSteps().stream().filter(s -> s.getDisplayOrder().equals(step2DisplayOrder)).findFirst().get();

        step1.setDisplayOrder(step2DisplayOrder);
        step2.setDisplayOrder(step1DisplayOrder);
        examinationStepRepository.save(step1);
        examinationStepRepository.save(step2);
    }

    private void switchOrder(ExaminationStep step1,
                             ExaminationStep step2) {

    }

    private void handleInvalidID(final Optional<Examination> examination) {
        if (!examination.isPresent()) {
            log.error("Move examination step down, Invalid examinationID!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                              "Examination not found by provided ID!");
        }
    }

    public ModelAndView moveExaminationStepUp(final ExaminationStepReorderDto dto) {
        return null;
    }

    private ModelAndView getExaminationStepListFragment(List<Examination> examinationList) {
        ModelAndView mv = new ModelAndView(Templates.EXAMINATION_STEP_LIST.getTemplateName());
        mv.addObject("examinationSteps", examinationList);
        return mv;
    }
}
