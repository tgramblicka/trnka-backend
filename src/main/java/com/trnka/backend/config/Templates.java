package com.trnka.backend.config;

public enum Templates {
    CREATE_TEST("create-test"),
    EDIT_TEST("edit-test"),
    ERROR_PAGE("error"),
    TESTING_PAGE("teacher-tests"),
    COURSE_EDIT_PAGE("course-edit"),
    COURSES_PAGE("teacher-class"),
    STUDENTS_LIST("students-list"),
    STUDENT_EDIT_PAGE("student-edit"),
    EXAMINATION_STEP_LIST("fragments/examination-step-list"),
    STUDENT_EXAM_STATISTICS("student-examination-statistics");




    private final String templateName;

    Templates(final String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
