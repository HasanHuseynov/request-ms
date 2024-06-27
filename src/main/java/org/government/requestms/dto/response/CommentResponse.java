package org.government.requestms.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.government.requestms.entity.Request;

import java.time.LocalDateTime;
@Getter
@Setter
public class CommentResponse {
    private String email;
    private String commentText;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
}