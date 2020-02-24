package com.trnka.backend.service.testing;

import java.util.Optional;

import com.trnka.restapi.dto.ExaminationType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.Course;
import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import com.trnka.backend.dto.ExaminationStepCreateDto;
import com.trnka.backend.dto.TestModel;
import com.trnka.backend.dto.TestingPageModel;
import com.trnka.backend.repository.BrailRepository;
import com.trnka.backend.repository.CourseRepository;
import com.trnka.backend.repository.ExaminationRepository;
import com.trnka.backend.service.CourseService;
import com.trnka.backend.service.ErrorPage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestingUiService {

    private ExaminationRepository examinationRepository;
    private BrailRepository brailRepository;
    private CourseRepository courseRepository;
    private CourseService courseService;
    private TestingListService testingListService;

    public TestingUiService(final ExaminationRepository examinationRepository,
                            final BrailRepository brailRepository,
                            final CourseRepository courseRepository,
                            final CourseService courseService,
                            final TestingListService testingListService) {
        this.examinationRepository = examinationRepository;
        this.brailRepository = brailRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.testingListService = testingListService;
    }

    public ModelAndView getTestingListUi() {
        ModelAndView mv = new ModelAndView(Templates.TESTING_PAGE.getTemplateName());
        return mv.addObject("model", new TestingPageModel());
    }

    @Transactional
    public ModelAndView createOrEditTest(TestModel dto) {
        if (dto.getExamination().getId() == null) {
            return createTest(dto.getExamination(), dto.getSelectedCourseId());
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
        String msg = String.format("Test with id %s not found!", model.getId());
        log.error(msg);
        return ErrorPage.create(msg);
    }

    private ModelAndView createTest(final Examination examination,
                                    final Long selectedCourseId) {
        addExaminationToCourse(examination, selectedCourseId);
        Examination saved = examinationRepository.save(examination);
        return getEditTestUiModel(saved);
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
    }

    private Examination createInitialExamination() {
        Examination examination = new Examination();
        examination.setAudio(null);
        examination.setComplexity(1L);
        examination.setTimeout(100L);
        examination.setType(ExaminationType.TESTING);
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
        model.setSelectedCourseId(examination.getCourse().getId());
        model.setCourses(courseService.getMyCoursesSelection());
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
        model.setCourses(courseService.getMyCoursesSelection());
        return mv.addObject("model", model);
    }

    @Transactional
    public ModelAndView createExaminationStep(final ExaminationStepCreateDto dto) {
        Optional<Examination> examination = examinationRepository.findById(dto.getExaminationId());
        if (examination == null) {
            String errorMsg = String.format("Examination with id %s does not exist!", dto.getExaminationId());
            log.error(errorMsg);
            return ErrorPage.create(errorMsg);
        }
        ExaminationStep step = new ExaminationStep();
        step.setBrailCharacter(brailRepository.findById(dto.getSelectedBrailId()).get());
        step.setPreserveOrder(dto.getPreserveOrder());

        Examination realExamination = examination.get();
        realExamination.addExaminationStep(step);

        return getEditTestUiModel(realExamination);
    }

    public ModelAndView deleteTest(final Long id) {
        examinationRepository.deleteById(id);
        return testingListService.getExaminationsForCurrentTeacher();
    }
}
