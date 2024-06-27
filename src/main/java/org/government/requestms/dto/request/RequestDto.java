package org.government.requestms.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestDto {
    private String categoryId;
    private String email;
    private String address;
    private String description;

    private Integer countOfLike;
    private Integer countOFComment;
}
