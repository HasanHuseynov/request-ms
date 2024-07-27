package org.government.requestms.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.BaseResponse;
import org.government.requestms.dto.response.RequestResponse;
import org.government.requestms.enums.Status;
import org.government.requestms.exception.DataExistException;
import org.government.requestms.service.RequestService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                                              @RequestParam String categoryName, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        requestService.createRequest(requestDto, categoryName, token);
        return BaseResponse.message("Təşəkkürlər! Sorğunuz uğurla yönləndirildi");
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public BaseResponse<List<RequestResponse>> getAllRequest(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return BaseResponse.OK(requestService.getAllRequest(pageable));
    }

    @GetMapping("user-requests")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<RequestResponse>> getUserRequest(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return BaseResponse.OK(requestService.getUserRequest(pageable));
    }

    @GetMapping("by-id/{requestId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','GOVERMENT','SUPER_STAFF','USER')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<RequestResponse> getRequestById(@PathVariable Long requestId) {
        return BaseResponse.OK(requestService.getRequestById(requestId));
    }

    @GetMapping("organization-requests")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','GOVERMENT','SUPER_STAFF')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<RequestResponse>> getOrganizationRequest(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return BaseResponse.OK(requestService.getOrganizationRequest(token, pageable));
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
    public BaseResponse<String> deleteRequest(@PathVariable Long request_id,
                                              HttpServletRequest request) throws DataExistException {
        String token = request.getHeader("Authorization");
        requestService.deleteRequest(request_id, token);
        return BaseResponse.message("Müraciətiniz silindi");

    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','GOVERMENT')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<RequestResponse>> getRequestByFilter(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String organizationName,
            @Parameter(description = "Parameter types: Son bir gün, Son bir həftə, Son bir ay")
            @RequestParam(required = false) String days,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());

        return BaseResponse.OK(requestService.getRequestByFilter(status, categoryName, organizationName, days, pageable));
    }

    @GetMapping("/organization-filter")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','GOVERMENT')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<RequestResponse>> getRequestOrganizationByFilter(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String categoryName,
            @Parameter(description = "Parameter types: Son bir gün, Son bir həftə, Son bir ay")
            @RequestParam(required = false) String days,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());

        return BaseResponse.OK(requestService.getRequestOrganizationByFilter(status, categoryName, token, days, pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','GOVERMENT')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<RequestResponse>> searchRequests(@RequestParam(required = false) String keyword,
                                                              @RequestParam(required = false) String id,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return BaseResponse.OK(requestService.searchRequests(keyword, id, pageable));
    }

    @PatchMapping("/update-status/{requestId}")
    @PreAuthorize("hasAnyAuthority('SUPER_STAFF','STAFF')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<String> updateStatus(@RequestParam(required = false) Status status, @PathVariable Long requestId) throws DataExistException {
        requestService.updateStatus(status, requestId);
        return BaseResponse.message("Status yeniləndi");
    }
}
