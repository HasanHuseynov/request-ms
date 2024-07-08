package org.government.requestms.controller;

import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.LikeRequest;
import org.government.requestms.dto.response.BaseResponse;
import org.government.requestms.dto.response.LikeResponse;
import org.government.requestms.exception.DataExistException;
import org.government.requestms.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/v1/likes"})
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<LikeResponse>>> getAllLikes() {
        return ResponseEntity.ok(BaseResponse.OK(likeService.getAllLike()));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<String>> createLike() {
        this.likeService.createNewLike();
        return ResponseEntity.ok(BaseResponse.message("Like has been created!"));
    }

    @PutMapping
    public ResponseEntity<BaseResponse<String>> updateLike(@RequestParam Long id, @RequestBody LikeRequest likeRequest) {
        this.likeService.updateLike(id, likeRequest);
        return ResponseEntity.ok(BaseResponse.message("Like updated successfully!"));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<String>> deleteLike(Long likeId) {
        this.likeService.deleteLike(likeId);
        return ResponseEntity.ok(BaseResponse.message("Like deleted successfully!"));
    }

    @PostMapping("/post")
    public ResponseEntity<BaseResponse<LikeResponse>> postLike(@RequestParam Long requestId) throws DataExistException {
        var response = likeService.assignLikeToRequest(requestId);
        return ResponseEntity.ok(BaseResponse.OK(response));
    }


}
