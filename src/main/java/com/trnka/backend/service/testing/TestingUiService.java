package com.trnka.backend.service.testing;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import com.trnka.backend.domain.ExaminationType;
import com.trnka.backend.dto.ExaminationStepCreateDto;
import com.trnka.backend.dto.TestModel;
import com.trnka.backend.dto.TestingPageModel;
import com.trnka.backend.repository.BrailRepository;
import com.trnka.backend.repository.ExaminationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestingUiService {

    private ExaminationRepository examinationRepository;
    private BrailRepository brailRepository;

    public TestingUiService(final ExaminationRepository examinationRepository,
                            final BrailRepository brailRepository) {
        this.examinationRepository = examinationRepository;
        this.brailRepository = brailRepository;
    }

    public ModelAndView getTestingListUi() {
        ModelAndView mv = new ModelAndView(Templates.TESTING_PAGE.getTemplateName());
        return mv.addObject("model", new TestingPageModel());
    }

    @Transactional
    public ModelAndView createOrEditTest(TestModel dto) {
        if (dto.getExamination().getId() == null) {
            return createTest(dto.getExamination());
        }
        return editTest(dto.getExamination());
    }

    private ModelAndView editTest(final Examination model) {
        Optional<Examination> found = examinationRepository.findById(Long.valueOf(model.getId()));
        if (found.isPresent()) {
            Examination entity = found.get();
            updateExamination(model, entity);
            examinationRepository.save(entity);
            return getEditTestUiModel(entity);
        }
        log.error("Test with id {} not found!", model.getId());
        return errorPage();
    }

    private ModelAndView createTest(final Examination examination) {
        Examination saved = examinationRepository.save(examination);
        return getEditTestUiModel(saved);
    }

    private void updateExamination(final Examination model,
                                   final Examination examination) {
        examination.setName(model.getName());
        examination.setAllowedRetries(Integer.valueOf(model.getAllowedRetries()));
    }

    private Examination createInitialExamination() {
        Examination examination = new Examination();
        examination.setAudio(null);
        examination.setComplexity(1L);
        examination.setTimeout(100L);
        examination.setType(ExaminationType.TESTING);
        doplnit course id; - test sa da vytvorit len z KURZU
        return examination;
    }

    public ModelAndView getEditTestUiModel(Long examinationId) {
        return getEditTestUiModel(examinationRepository.findById(examinationId).get());
    }

    private ModelAndView getEditTestUiModel(Examination examination) {
        ModelAndView mv = new ModelAndView(Templates.EDIT_TEST.getTemplateName());
        TestModel model = new TestModel();
        model.setExamination(examination);
        model.setExaminationStepCreateDto(getExaminationStepCreateDto(examination));
        return mv.addObject("model", model);
    }

    private ExaminationStepCreateDto getExaminationStepCreateDto(final Examination examination) {
        ExaminationStepCreateDto dto = new ExaminationStepCreateDto();
        dto.setExaminationId(examination.getId());
        dto.setBrails(brailRepository.findAll());
        return dto;
    }

    public ModelAndView getCreateTestUiModel() {
        ModelAndView mv = new ModelAndView(Templates.CREATE_TEST.getTemplateName());
        TestModel model = new TestModel();
        model.setExamination(createInitialExamination());
        return mv.addObject("model", model);
    }

    @Transactional
    public ModelAndView createExaminationStep(final ExaminationStepCreateDto dto) {
        Optional<Examination> examination = examinationRepository.findById(dto.getExaminationId());
        if (examination == null) {
            log.error("Examination with id {} does not exist!", dto.getExaminationId());
            return errorPage();
        }
        ExaminationStep step = new ExaminationStep();
        step.setBrailCharacter(brailRepository.findById(dto.getSelectedBrailId()).get());
        step.setPreserveOrder(dto.getPreserveOrder());

        Examination realExamination = examination.get();
        realExamination.addExaminationStep(step);

        return getEditTestUiModel(realExamination);
    }

    private ModelAndView errorPage() {
        return new ModelAndView(Templates.ERROR_PAGE.getTemplateName());
    }
}
