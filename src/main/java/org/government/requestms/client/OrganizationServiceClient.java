//package org.government.requestms.client;
//
//import org.government.requestms.dto.request.RequestDto;
//import org.government.requestms.dto.response.OrganizationResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//@FeignClient(name = "organization-ms", url = "https://adminms.azurewebsites.net/api/Organozations/")
//public interface OrganizationServiceClient {
//    @GetMapping("/get")
//    ResponseEntity<OrganizationResponse> getOrganizationByName(@RequestBody RequestDto requestDto,
//                                                               @RequestHeader("Authorization") String token);
//
//}
