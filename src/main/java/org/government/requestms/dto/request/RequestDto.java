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

    @NotBlank(message = "Address boş ola bilməz")
    @Size(min = 5, message = "Ünvan minimum 5 simvoldan ibarət olmalıdır")
    @Size(max = 150, message = "Ünvan maksimum 150 simvoldan ibarət olmalıdır")
    private String address;

    @NotBlank(message = "Müraciət boş ola bilməz")
    @Size(min = 10, message = "Müraciət mətni minimum 10 simvoldan ibarət olmalıdır")
    @Size(max = 500, message ="Müraciət mətni maksimum 500 simvoldan ibarət olmalıdır")
    private String description;

    @NotBlank(message = "OrganizationName boş ola bilməz")
    private String organizationName;

}
