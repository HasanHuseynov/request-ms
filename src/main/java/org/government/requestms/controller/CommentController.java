package org.government.requestms.controller;

import java.util.Base64;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.government.requestms.dto.request.CommentRequest;
import org.government.requestms.dto.request.CommentRequest;
import org.government.requestms.dto.response.BaseResponse;
import org.government.requestms.dto.response.CommentResponse;
import org.government.requestms.dto.response.CommentResponse;
import org.government.requestms.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1/comments"})
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getAllComments() {
        return ResponseEntity.ok(BaseResponse.OK(commentService.getAllComment()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseResponse<String>> createComment(@RequestBody CommentRequest commentRequest) {
        this.commentService.createNewComment(commentRequest);
        return ResponseEntity.ok(BaseResponse.message("Comment has been created!"));
    }

    @PutMapping
    public ResponseEntity<BaseResponse<String>> updateComment(@RequestParam Long id, @RequestBody CommentRequest commentRequest) {
        this.commentService.updateComment(id, commentRequest);
        return ResponseEntity.ok(BaseResponse.message("Comment updated successfully!"));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<String>> deleteComment(Long id) {
        this.commentService.deleteComment(id);
        return ResponseEntity.ok(BaseResponse.message("Comment deleted successfully!"));
    }

    @PostMapping("/post")
    public ResponseEntity<BaseResponse<CommentResponse>> postComment(@RequestBody @Valid CommentRequest commentRequest,
                                                                     @RequestParam Long id,
                                                                     HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        var response = commentService.assignCommentToRequest(id, commentRequest,token);
        return ResponseEntity.ok(BaseResponse.OK(response));
    }

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
}
