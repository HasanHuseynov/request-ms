package org.government.requestms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LikeResponse {
    private Long likeId;

    private String fullName;
    private String email;


    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastModified;

}
