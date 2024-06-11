package org.government.requestms.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.government.requestms.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RequestResponseForUser {
    private String email;
    private Long categoryId;
    private String categoryName;
    private String address;
    private String description;
    private Status status;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
}
