package org.government.requestms.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StatusSerializer extends JsonSerializer<Status> {

    @Override
    public void serialize(Status status, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String value;
        switch (status) {
            case Həlledildi:
                value = "Həll edildi";
                break;
            default:
                value = status.name();
        }
        gen.writeString(value);
    }
}