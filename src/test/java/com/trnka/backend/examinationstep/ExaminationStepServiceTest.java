package com.trnka.backend.examinationstep;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trnka.backend.BaseTest;
import com.trnka.backend.domain.BrailCharacter;
import com.trnka.backend.domain.Course;
import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import com.trnka.backend.dto.course.ExaminationStepReorderDto;
import com.trnka.backend.repository.CourseRepository;
import com.trnka.backend.repository.ExaminationStepRepository;
import com.trnka.backend.service.ExaminationStepService;

public class ExaminationStepServiceTest extends BaseTest {

    @Autowired
    private ExaminationStepRepository examinationStepRepository;
    @Autowired
    private ExaminationStepService examinationStepService;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void testMoveDown() {
        ExaminationStep step1 = createExaminationStep("a", 1);
        ExaminationStep step2 = createExaminationStep("b", 2);
        Examination examination = prepareExaminationWithSteps(step1, step2);

        ExaminationStepReorderDto reorderDto = new ExaminationStepReorderDto(examination.getId(),
                                                                             step1.getId());
        examinationStepService.moveDown(examination, step1.getId());

        Integer step1DisplayOrder = examinationStepRepository.findById(step1.getId()).get().getDisplayOrder();
        Integer step2DisplayOrder = examinationStepRepository.findById(step2.getId()).get().getDisplayOrder();

        Assertions.assertEquals(2, step1DisplayOrder);
        Assertions.assertEquals(1, step2DisplayOrder);
    }

    @Test
    public void testMoveUp() {
        ExaminationStep step1 = createExaminationStep("a", 1);
        ExaminationStep step2 = createExaminationStep("b", 2);
        Examination examination = prepareExaminationWithSteps(step1, step2);

        ExaminationStepReorderDto reorderDto = new ExaminationStepReorderDto(examination.getId(),
                step1.getId());
        examinationStepService.moveUp(examination, step2.getId());

        Integer step1DisplayOrder = examinationStepRepository.findById(step1.getId()).get().getDisplayOrder();
        Integer step2DisplayOrder = examinationStepRepository.findById(step2.getId()).get().getDisplayOrder();

        Assertions.assertEquals(2, step1DisplayOrder);
        Assertions.assertEquals(1, step2DisplayOrder);
    }


    @Test
    public void changeDisplayOfSecondAndThird() {
        ExaminationStep step1 = createExaminationStep("a", 1);
        ExaminationStep step2 = createExaminationStep("b", 2);
        ExaminationStep step3 = createExaminationStep("c", 3);

        Examination examination = prepareExaminationWithSteps(step1, step2, step3);

        ExaminationStepReorderDto reorderDto = new ExaminationStepReorderDto(examination.getId(),
                step1.getId());
        examinationStepService.moveDown(examination, step2.getId());

        Integer step1DisplayOrder = examinationStepRepository.findById(step1.getId()).get().getDisplayOrder();
        Integer step2DisplayOrder = examinationStepRepository.findById(step2.getId()).get().getDisplayOrder();
        Integer step3DisplayOrder = examinationStepRepository.findById(step3.getId()).get().getDisplayOrder();


        Assertions.assertEquals(1, step1DisplayOrder);
        Assertions.assertEquals(3, step2DisplayOrder);
        Assertions.assertEquals(2, step3DisplayOrder);
    }

    @Test
    public void moveLastDownShouldDoNothing() {
        ExaminationStep step1 = createExaminationStep("a", 1);
        ExaminationStep step2 = createExaminationStep("b", 2);
        Examination examination = prepareExaminationWithSteps(step1, step2);

        ExaminationStepReorderDto reorderDto = new ExaminationStepReorderDto(examination.getId(),
                step1.getId());
        examinationStepService.moveDown(examination, step2.getId());

        Integer step2DisplayOrder = examinationStepRepository.findById(step2.getId()).get().getDisplayOrder();

        Assertions.assertEquals(2, step2DisplayOrder);
    }

    @Test
    public void moveFirstUpShouldDoNothing() {
        ExaminationStep step1 = createExaminationStep("a", 1);
        ExaminationStep step2 = createExaminationStep("b", 2);
        Examination examination = prepareExaminationWithSteps(step1, step2);

        ExaminationStepReorderDto reorderDto = new ExaminationStepReorderDto(examination.getId(),
                step1.getId());
        examinationStepService.moveUp(examination, step1.getId());

        Integer step1DisplayOrder = examinationStepRepository.findById(step1.getId()).get().getDisplayOrder();

        Assertions.assertEquals(1, step1DisplayOrder);
    }



    private Examination prepareExaminationWithSteps(ExaminationStep... steps){
        Examination examination = new Examination();

        for (ExaminationStep step: steps){
            examination.addExaminationStep(step);
        }
        Course course = new Course();
        course.addExamination(examination);
        courseRepository.save(course);
        return examination;
    }




    private ExaminationStep createExaminationStep(final String a,
                                                  final int i) {
        return new ExaminationStep(new BrailCharacter(a,
                                                      Collections.emptyList(),
                                                      null),
                                   true,
                                   i);
    }

}
