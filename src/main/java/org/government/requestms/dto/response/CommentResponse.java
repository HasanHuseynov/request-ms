package org.government.requestms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.government.requestms.entity.Request;

import java.time.LocalDateTime;
@Getter
@Setter
public class CommentResponse {
    private String email;
    private String commentText;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastModified;
    private Request request;
}