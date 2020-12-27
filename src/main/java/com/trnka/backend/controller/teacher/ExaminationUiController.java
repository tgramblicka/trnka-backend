package com.trnka.backend.controller.teacher;

import com.trnka.backend.controller.RestApiPaths;
import com.trnka.backend.dto.TestModel;
import com.trnka.backend.service.testing.ExaminationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
@RequestMapping(RestApiPaths.PATH_UI_EXAMINATION)
public class ExaminationUiController {
    @Autowired
    private ExaminationService examinationService;

    public static final String CREATE_TEST = "create";
    public static final String EDIT_TEST = "edit";
    public static final String DELETE_TEST = "delete";
    public static final String CREATE_EXAMINATION_STEP = "examination-step";

    @RequestMapping(method = RequestMethod.GET, path = CREATE_TEST)
    public ModelAndView getCreateTest() {
        return examinationService.getCreateTestUiModel();
    }

    @RequestMapping(method = RequestMethod.POST, path = CREATE_EXAMINATION_STEP)
    public ModelAndView createExaminationModel(@ModelAttribute TestModel dto) {
        return examinationService.createExaminationStep(dto.getExaminationStepCreateDto());
    }

    @RequestMapping(method = RequestMethod.POST, path = EDIT_TEST)
    public ModelAndView createTest(@ModelAttribute TestModel dto) {
        return examinationService.createOrEditTest(dto);
    }

    @RequestMapping(method = RequestMethod.GET, path = EDIT_TEST)
    public ModelAndView getEditTest(@RequestParam Long id) {
        return examinationService.getEditTestUiModel(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = DELETE_TEST)
    public ModelAndView deleteTest(@RequestParam Long id) {
        return examinationService.deleteTest(id);
    }

}
