package org.government.requestms.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.BaseResponse;
import org.government.requestms.dto.response.RequestResponse;
import org.government.requestms.enums.Status;
import org.government.requestms.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public BaseResponse<String> createRequest(@Valid @RequestBody RequestDto requestDto,
                                              @RequestParam String categoryName) {
        requestService.createRequest(requestDto, categoryName);
        return BaseResponse.message("Yeni müraciət yaradıldı");
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public BaseResponse<List<RequestResponse>> getAllRequest() {
        return BaseResponse.OK(requestService.getAllRequest());
    }

    @GetMapping("user-requests")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<RequestResponse>> getRequest() {
        return BaseResponse.OK(requestService.getRequest());
    }

    @GetMapping("organization-requests")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','GOVERMENT','SUPER_STAFF')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<RequestResponse>> getOrganizationRequest(@RequestParam String organizationName) {
        return BaseResponse.OK(requestService.getOrganizationRequest(organizationName));
    }


    @PutMapping("/{request_id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<String> updateRequest(@RequestBody @Valid RequestDto requestDto,
                                              @PathVariable Long request_id, @RequestParam String categoryName) {
        requestService.updateRequest(request_id, requestDto, categoryName);
        return BaseResponse.message("Müraciətiniz uğurla yeniləndi");

    }

    @DeleteMapping("/{request_id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<String> deleteRequest(@PathVariable Long request_id) {
        requestService.deleteRequest(request_id);
        return BaseResponse.message("Müraciətiniz silindi");

    }


    @GetMapping("/filter")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','GOVERMENT')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<RequestResponse>> getRequestByFilter(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String organizationName,
            @Parameter(description = "Parameter types: LastDay, LastWeek, LastMonth")
            @RequestParam(required = false) String days) {
        return BaseResponse.OK(requestService.getRequestByFilter(status, categoryName, organizationName, days));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','GOVERMENT')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<RequestResponse>> searchRequests(@RequestParam String keyword) {
        return BaseResponse.OK(requestService.searchRequests(keyword));
    }

    @PatchMapping("/update-status/{requestId}")
    @PreAuthorize("hasAnyAuthority('SUPER_STAFF','STAFF')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<String> updateStatus(@RequestParam(required = false) Status status, @PathVariable Long requestId) {
        requestService.updateStatus(status, requestId);
        return BaseResponse.message("Status yeniləndi");
    }
}
