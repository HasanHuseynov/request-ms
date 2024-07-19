package org.government.requestms.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public enum Status {
    Gözləmədə("Gözləmədə"),
    Baxılır("Baxılır"),
    Əssasızdır("Əssasızdır"),
    Həlledildi("Həlledildi"),
    Arxivdədir("Arxivdədir");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}