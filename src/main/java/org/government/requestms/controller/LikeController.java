package org.government.requestms.controller;

import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.LikeRequest;
import org.government.requestms.dto.response.LikeResponse;
import org.government.requestms.exception.ExistCategoryException;
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
    public ResponseEntity<List<LikeResponse>> getAllLikes() {
        return ResponseEntity.ok(this.likeService.getAllLike());
    }

    @PostMapping
    public ResponseEntity<String> createLike() {
        this.likeService.createNewLike();
        return ResponseEntity.ok("Like has been created!");
    }

    @PutMapping
    public ResponseEntity<String> updateLike(@RequestParam Long id, @RequestBody LikeRequest likeRequest) {
        this.likeService.updateLike(id, likeRequest);
        return ResponseEntity.ok("Like updated successfully!");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLike(Long id) {
        this.likeService.deleteLike(id);
        return ResponseEntity.ok("Like deleted successfully!");
    }

    @PostMapping("/post")
    public ResponseEntity<LikeResponse> postLike(@RequestParam Long id) throws ExistCategoryException {
        var response  = likeService.assignLikeToRequest(id);
        return ResponseEntity.ok(response);
    }


}
