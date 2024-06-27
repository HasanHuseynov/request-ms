package org.government.requestms.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.government.requestms.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestResponseForAdmin {
    private Long requestId;
    private String email;
    private Long categoryId;
    private String categoryName;
    private String address;
    private String description;
    private Status status;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;

    private Integer commentCount;

    private Integer likeCount;

    private List<LikeResponse> like;

    private List<CommentResponse> comment;
}
