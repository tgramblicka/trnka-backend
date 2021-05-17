package com.trnka.backend.domain;

public enum UserType {
    ADMIN("administrátor"),
    TEACHER("učiteľ");

    private final String translation;

    UserType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
