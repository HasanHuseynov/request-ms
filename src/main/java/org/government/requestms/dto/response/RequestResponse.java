package org.government.requestms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.government.requestms.enums.Status;
import org.government.requestms.enums.StatusSerializer;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestResponse {
    private Long requestId;
    private String fullName;
    private String address;
    private String description;

    @JsonSerialize(using = StatusSerializer.class)
    private Status status;
    private String organizationName;

    @JsonFormat(pattern = "dd.MM.yyyy, HH:mm")
    private LocalDateTime createDate;

    private Integer commentCount;

    private Integer likeCount;
    private boolean likeSuccess;

    private CategoryResponse category;
}
