package org.government.requestms.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    @NotBlank(message = "Şərh boş ola bilməz")
    @Size(max = 500, message = "Şərh maksimum 500 simvoldan ibarət ola bilər")
    private String commentText;
}