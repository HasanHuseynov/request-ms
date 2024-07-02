package org.government.requestms.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.government.requestms.entity.Request;

@Getter
@Setter
public class CommentRequest {
    @NotBlank(message = "mətn boş ola bilməz")
    @Size(min = 10, message = "Minimum 10 simvol istifadə olunmalıdır")
    @Size(max = 500, message = "Maksimum 500 simvol istifadə oluna bilər")
    private String commentText;
}