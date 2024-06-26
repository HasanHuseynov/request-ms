package org.government.requestms.client;

import org.government.requestms.dto.response.OrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "organization-ms", url = "localhost:8080/customer")
public interface OrganizationServiceClient {
    @GetMapping()
    ResponseEntity<OrganizationResponse> getOrganizationByName(@RequestParam String organizationName);

}
