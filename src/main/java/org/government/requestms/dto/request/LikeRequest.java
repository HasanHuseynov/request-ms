package org.government.requestms.dto.request;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.government.requestms.entity.Request;


@Getter
@Setter
public class LikeRequest {
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;

}
