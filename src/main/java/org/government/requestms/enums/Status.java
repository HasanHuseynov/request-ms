package org.government.requestms.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    Gözləmədə("Gözləmədə"),
    Baxılır("Baxılır"),
    Əsassızdır("Əsassızdır"),
    Həlledildi("Həlledildi"),
    Arxivdədir("Arxivdədir");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}