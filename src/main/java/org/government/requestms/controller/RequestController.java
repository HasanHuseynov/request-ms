package org.government.requestms.controller;

import io.swagger.v3.oas.annotations.Parameter;
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
        return requestService.getAllRequest();
    }

    @PutMapping("/{request_id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
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
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','GOVERMENT')")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestResponse> getRequestByFilter(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String organizationName,
            @Parameter(description = "Parameter types: LastDay, LastWeek, LastMonth")
            @RequestParam(required = false) String days) {
        return requestService.getRequestByFilter(status, categoryName, organizationName, days);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','GOVERMENT')")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestResponse> searchRequests(@RequestParam String keyword) {
        return requestService.searchRequests(keyword);
    }

    @PatchMapping("/update-status/{requestId}")
    @PreAuthorize("hasAnyAuthority('SUPER_STAFF','STAFF')")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(@RequestParam(required = false) Status status, @PathVariable Long requestId) {
        requestService.updateStatus(status,requestId);
    }
}
