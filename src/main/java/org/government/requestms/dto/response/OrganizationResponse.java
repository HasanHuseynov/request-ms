package org.government.requestms.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrganizationResponse {
    private OrganizationData data;
    private String message;
}
