package org.government.requestms.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
}
