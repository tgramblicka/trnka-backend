package com.trnka.backend.service.testing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.Course;
import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import com.trnka.backend.dto.ExaminationModel;
import com.trnka.backend.dto.ExaminationStepCreateDto;
import com.trnka.backend.repository.BrailRepository;
import com.trnka.backend.repository.CourseRepository;
import com.trnka.backend.repository.ExaminationRepository;
import com.trnka.backend.repository.ExaminationStepRepository;
import com.trnka.backend.service.CourseService;
import com.trnka.backend.service.ErrorPage;
import com.trnka.restapi.dto.SequenceType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExaminationService {

    private static final String TEST_SUCC_CREATED = "Test bol úspešne vytvotený.";
    private static final String TEST_SUCC_UPDATED = "Test bol úspešne upravený.";

    private final ExaminationRepository examinationRepository;
    private final ExaminationStepRepository examinationStepRepository;
    private final BrailRepository brailRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final ExaminationListService examinationListService;


    @Transactional
    public ModelAndView createOrEditTest(ExaminationModel model) {
        if (model.getExamination().getId() == null) {
            return createTest(model);
        }
        return editTest(model);
    }

    private ModelAndView editTest(final ExaminationModel model) {
        List<String> errors = validate(model);

        Examination detachedEntity = model.getExamination();
        if (!errors.isEmpty()) {
            return getEditExaminationUiModel(detachedEntity, null, errors);
        }

        Optional<Examination> found = examinationRepository.findById(Long.valueOf(detachedEntity.getId()));
        if (found.isPresent()) {
            Examination entity = found.get();
            updateExamination(detachedEntity, entity);
            examinationRepository.save(entity);
            return getEditExaminationUiModel(entity, TEST_SUCC_UPDATED, Collections.EMPTY_LIST);
        }
        return examinationNotExistErrorPage(detachedEntity.getId());
    }

    private List<String> validate(ExaminationModel model) {
        List<String> errors = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(model.getExamination()).forEach(v -> errors.add(v.getMessage()));
        return errors;
    }

    private ModelAndView createTest(ExaminationModel model) {

        List<String> errors = validate(model);
        if (!errors.isEmpty()) {
            return getCreateExaminationUiModel(model, null, errors);
        }

        addExaminationToCourse(model.getExamination(), model.getSelectedCourseId());
        Examination saved = examinationRepository.save(model.getExamination());
        return getEditExaminationUiModel(saved, TEST_SUCC_CREATED, Collections.EMPTY_LIST);
    }

    private void addExaminationToCourse(final Examination examination,
                                        final Long selectedCourseId) {
        Course course = courseRepository.findById(selectedCourseId).get();
        course.addExamination(examination);
    }

    private void updateExamination(final Examination model,
                                   final Examination examination) {
        examination.setName(model.getName());
        examination.setAllowedRetries(Integer.valueOf(model.getAllowedRetries()));
        examination.setType(model.getType());
    }

    private Examination createInitialExamination() {
        Examination examination = new Examination();
        examination.setAudio(null);
        examination.setPassingRate(BigDecimal.valueOf(80));
        examination.setTimeout(100L);
        examination.setType(SequenceType.TESTING);
        return examination;
    }

    public ModelAndView getEditExaminationUiModel(Long examinationId) {
        return getEditExaminationUiModel(examinationRepository.findById(examinationId).get(), null, Collections.EMPTY_LIST);
    }

    private ModelAndView getEditExaminationUiModel(Examination examination,
                                                   String infoMessage,
                                                   List<String> errors) {
        ModelAndView mv = new ModelAndView(Templates.EDIT_EXAMINATION.getTemplateName());
        ExaminationModel model = new ExaminationModel();
        model.setExamination(examination);
        model.setExaminationStepCreateDto(getExaminationStepCreateDto(examination));
        model.setSelectedCourseId(Optional.ofNullable(examination.getCourse()).map(Course::getId).orElse(null));
        model.setCourses(courseService.getMyCoursesSelection());
        model.setInfoMessage(infoMessage);
        model.setErrors(errors);

        return mv.addObject("model", model);
    }

    private ExaminationStepCreateDto getExaminationStepCreateDto(final Examination examination) {
        ExaminationStepCreateDto dto = new ExaminationStepCreateDto();
        dto.setExaminationId(examination.getId());
        dto.setBrails(brailRepository.findAll());
        return dto;
    }

    public ModelAndView getCreateExaminationUiModel(ExaminationModel model,
                                                    String infoMessage,
                                                    List<String> errors) {
        ModelAndView mv = new ModelAndView(Templates.CREATE_EXAMINATION.getTemplateName());
        model.setExamination(createInitialExamination());
        model.setCourses(courseService.getMyCoursesSelection());
        model.setErrors(errors);
        model.setInfoMessage(infoMessage);
        return mv.addObject("model", model);
    }

    public ModelAndView getCreateExaminationUiModel() {
        ExaminationModel model = new ExaminationModel();
        return getCreateExaminationUiModel(model, null, Collections.EMPTY_LIST);
    }

    @Transactional
    public ModelAndView createExaminationStep(final ExaminationStepCreateDto dto) {
        Optional<Examination> examination = examinationRepository.findById(dto.getExaminationId());
        if (examination == null) {
            return examinationNotExistErrorPage(dto.getExaminationId());
        }
        ExaminationStep step = new ExaminationStep();
        step.setBrailCharacter(brailRepository.findById(dto.getSelectedBrailId()).get());
        step.setPreserveOrder(dto.getPreserveOrder());

        Examination realExamination = examination.get();
        realExamination.addExaminationStep(step);

        return getEditExaminationUiModel(realExamination, null, Collections.EMPTY_LIST);
    }

    private ModelAndView examinationNotExistErrorPage(final Long examinationId) {
        String errorMsg = String.format("Examination with id %s does not exist!", examinationId);
        log.error(errorMsg);
        return ErrorPage.create(errorMsg);
    }

    public ModelAndView deleteTestAndGetUi(final Long id) {
        Optional<Examination> examinationOptional = examinationRepository.findById(id);
        if (!examinationOptional.isPresent()) {
            examinationNotExistErrorPage(id);
        }
        deleteExaminationTransactional(examinationOptional.get());
        return examinationListService.getExaminationsForCurrentTeacher();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteExaminationTransactional(Examination examination){
        examinationStepRepository.deleteAll(examination.getExaminationSteps());

        examination.getExaminationSteps().clear();
        examinationRepository.delete(examination);
    }
}
