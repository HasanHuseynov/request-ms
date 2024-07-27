package org.government.requestms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentResponse {
    private Long commentId;

    private String fullName;

    private String authority;

    private String commentText;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createDate;


}