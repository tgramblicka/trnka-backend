package com.trnka.backend;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import com.trnka.backend.repository.BrailRepository;
import com.trnka.backend.repository.ExaminationRepository;
import com.trnka.restapi.dto.SequenceType;

public class ExaminationCrudTest extends BaseTest {

    @Autowired
    private ExaminationRepository examinationRepository;
    @Autowired
    private BrailRepository brailRepository;

    // @Test
    public void testCreate() {
        String name = "test1";
        createExaminationTest(name);

        Examination found = examinationRepository.findByName(name);

        Assert.assertNotNull(found);
        Assert.assertNotNull(found.getId());
        Assert.assertEquals(2, found.getExaminationSteps().size());
    }

    private void createExaminationTest(String testName) {
        Examination examination = new Examination();
        examination.setType(SequenceType.TESTING);
        examination.setTimeout(1000L);
        examination.setComplexity(1L);
        examination.setAudio("none");
        examination.setName(testName);
        examination.getExaminationSteps().add(step("a"));
        examination.getExaminationSteps().add(step("b"));
        examinationRepository.save(examination);
    }

    private ExaminationStep step(String letter) {
        ExaminationStep step = new ExaminationStep();
        step.setPreserveOrder(true);
        step.setBrailCharacter(brailRepository.findByLetter(letter));
        return step;
    }
}
