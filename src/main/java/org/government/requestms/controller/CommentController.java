package org.government.requestms.controller;

import java.util.List;
import org.government.requestms.dto.request.CommentRequest;
import org.government.requestms.dto.response.CommentResponse;
import org.government.requestms.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        return ResponseEntity.ok(this.commentService.getAllComment());
    }

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentRequest commentRequest) {
        this.commentService.createNewComment(commentRequest);
        return ResponseEntity.ok("Comment has been created!");
    }

    @PutMapping
    public ResponseEntity<String> updateComment(@RequestParam Long id, @RequestBody CommentRequest commentRequest) {
        this.commentService.updateComment(id, commentRequest);
        return ResponseEntity.ok("Comment updated successfully!");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComment(Long id) {
        this.commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully!");
    }

    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }
}
