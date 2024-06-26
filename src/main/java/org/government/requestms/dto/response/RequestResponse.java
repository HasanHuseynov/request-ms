package org.government.requestms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.government.requestms.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RequestResponse {
    private Long requestId;
    private String email;
    private String address;
    private String description;
    private Status status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastModified;
}
