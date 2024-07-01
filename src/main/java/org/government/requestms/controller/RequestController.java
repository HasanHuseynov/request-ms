package org.government.requestms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.RequestResponse;
import org.government.requestms.entity.Request;
import org.government.requestms.enums.Status;
import org.government.requestms.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public String createRequest(@Valid @RequestBody RequestDto requestDto,
                                @RequestParam String categoryName, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        requestService.createRequest(requestDto, categoryName, token);
        return "Yeni müraciət yaradıldı";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RequestResponse> getAllRequest() {
        return requestService.getAllRequest();
    }

    @GetMapping("user-requests")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestResponse> getRequest() {
        return requestService.getRequest();
    }

    @PutMapping("/{request_id}")
    @PreAuthorize("hasAuthority('USER')")
    public String updateRequest(@RequestBody @Valid RequestDto requestDto,
                                @PathVariable Long request_id, @RequestParam String categoryName) {
        requestService.updateRequest(request_id, requestDto, categoryName);
        return "Müraciətiniz uğurla yeniləndi";

    }

    @DeleteMapping("/{request_id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public String deleteRequest(@PathVariable Long request_id) {
        requestService.deleteRequest(request_id);
        return "Müraciətiniz silindi";

    }

    @GetMapping("/filter")
    public List<RequestResponse> getRequests(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String organizationName,
            @RequestParam(required = false) LocalDateTime createDate) {
        return requestService.getRequestsFilter(status, categoryId, organizationName, createDate);
    }


}
