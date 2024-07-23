package org.government.requestms.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.LikeRequest;
import org.government.requestms.dto.response.BaseResponse;
import org.government.requestms.dto.response.LikeResponse;
import org.government.requestms.exception.DataExistException;
import org.government.requestms.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/v1/likes"})
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','SUPER_STAFF')")
    public ResponseEntity<BaseResponse<List<LikeResponse>>> getAllLikes() {
        return ResponseEntity.ok(BaseResponse.OK(likeService.getAllLike()));
    }


    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','SUPER_STAFF')")
    public ResponseEntity<BaseResponse<String>> deleteLike(Long requestId,
                                                           HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        likeService.deleteLike(requestId, token);
        return ResponseEntity.ok(BaseResponse.message("Like deleted successfully!"));
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','SUPER_STAFF')")
    public ResponseEntity<BaseResponse<String>> postLike(@RequestParam Long requestId) throws DataExistException {
        likeService.assignLikeToRequest(requestId);
        return ResponseEntity.ok(BaseResponse.message("OK"));
    }


}
