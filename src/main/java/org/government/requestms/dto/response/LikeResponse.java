package org.government.requestms.dto.response;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class LikeResponse {
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;

}
