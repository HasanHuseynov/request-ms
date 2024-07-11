package org.government.requestms.enums;

public enum Status {
    Gözləmədə("Gözləmədə"),
    Baxılır("Baxılır"),
    Əssasızdır("Əssasızdır"),
    Həlledildi("Həll edildi"),
    Arxivdədir("Arxivdədir");

    public final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}