package org.government.requestms.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private String message;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeStamp;
    private Integer statusCode;

}
