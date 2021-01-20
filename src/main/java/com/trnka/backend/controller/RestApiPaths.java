package com.trnka.backend.controller;

public class RestApiPaths {
    public static final String PATH = "/vst/";
    public static final String PATH_MONITORING = PATH + "monitoring/";
    public static final String PATH_UI = PATH + "ui/";
    public static final String PATH_UI_TEACHER = PATH_UI + "teacher/";
    public static final String PATH_UI_ADMIN = PATH_UI + "admin/";
    public static final String PATH_UI_EXAMINATION = PATH_UI + "examination/";
    public static final String PATH_UI_COURSE = PATH_UI + "course";
    public static final String PATH_UI_ADMIN_USER_MNGMT = PATH_UI_ADMIN + "users";
    public static final String PATH_UI_STUDENT = PATH_UI + "student";

    private RestApiPaths() {
        super();
    }
}
