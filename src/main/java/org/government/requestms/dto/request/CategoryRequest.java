package org.government.requestms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryRequest {
    @NotBlank(message = "kateqoriya adı boş ola bilməz")
    private String categoryName;
}
