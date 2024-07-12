package org.government.requestms.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    Gözləmədə("Gözləmədə"),
    Baxılır("Baxılır"),
    Əssasızdır("Əssasızdır"),
    Həlledildi("Həlledildi"),
    Arxivdədir("Arxivdədir");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}