package org.government.requestms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestDto {

    @NotBlank(message = "address boş ola bilməz")
    private String address;

    @NotBlank(message = "müraciət boş ola bilməz")
    @Size(min = 10, message = "Minimum 10 simvol istifadə olunmalıdır")
    @Size(max = 500, message = "Maksimum 500 simvol istifadə oluna bilər")
    private String description;

    @NotBlank(message = "organizationName boş ola bilməz")
    private String organizationName;

}
