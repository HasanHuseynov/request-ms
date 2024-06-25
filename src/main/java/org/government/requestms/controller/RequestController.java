package org.government.requestms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.RequestResponseForAdmin;
import org.government.requestms.dto.response.RequestResponseForUser;
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
    public String createRequest(@Valid @RequestBody RequestDto requestDto,
                                @RequestParam String categoryName) {
        requestService.createRequest(requestDto, categoryName);
        return "Yeni müraciət yaradıldı";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RequestResponseForAdmin> getAllRequest() {
        return requestService.getAllRequest();
    }

    @GetMapping("user-requests")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestResponseForUser> getRequest() {
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
}
