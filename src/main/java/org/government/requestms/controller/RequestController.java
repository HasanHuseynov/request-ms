package org.government.requestms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.RequestResponseForAdmin;
import org.government.requestms.dto.response.RequestResponseForUser;
import org.government.requestms.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createRequest(@Valid @RequestBody RequestDto requestDto) {
        requestService.createRequest(requestDto);
        return "Yeni müraciət yaradıldı";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RequestResponseForAdmin> getAllRequest() {
        return requestService.getAllRequest();
    }

    @GetMapping("get-user-requests")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestResponseForUser> getRequest(@RequestParam String email) {
        return requestService.getRequest(email);
    }

    @PutMapping("/{request_id}")
    public String updateRequest(@RequestBody @Valid RequestDto requestDto,
                                @PathVariable Long request_id) {
        requestService.updateRequest(request_id, requestDto);
        return "Müraciətiniz uğurla yeniləndi";

    }

    @DeleteMapping("/{request_id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteRequest(@PathVariable Long request_id) {
        requestService.deleteRequest(request_id);
        return "OK";

    }
}
