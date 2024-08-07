package org.government.requestms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.government.requestms.dto.request.CommentRequest;
import org.government.requestms.dto.response.BaseResponse;
import org.government.requestms.dto.response.CommentResponse;
import org.government.requestms.service.CommentService;
import org.government.requestms.service.JWTService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/v1/comments"})
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final JWTService jwtService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','SUPER_STAFF')")
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getAllComments(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());

        return ResponseEntity.ok(BaseResponse.OK(commentService.getAllComment(pageable)));
    }

    @GetMapping("organization-comments")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','GOVERMENT','SUPER_STAFF')")
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getOrganizationComment(@RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size,
                                                                                      HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());

        return ResponseEntity.ok(BaseResponse.OK(commentService.getOrganizationComment(pageable, token)));
    }


    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','SUPER_STAFF')")
    public ResponseEntity<BaseResponse<String>> updateComment(@RequestParam Long id, @RequestBody CommentRequest commentRequest) {
        commentService.updateComment(id, commentRequest);
        return ResponseEntity.ok(BaseResponse.message("Comment updated successfully!"));
    }

    @DeleteMapping("/{commentİd}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','SUPER_STAFF')")
    public ResponseEntity<BaseResponse<String>> deleteComment(@PathVariable Long commentİd,
                                                              HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        commentService.deleteComment(commentİd, token);
        return ResponseEntity.ok(BaseResponse.message("Comment deleted successfully!"));
    }

    @GetMapping("/request/{requestId}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','SUPER_STAFF')")
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getCommentById(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size,
                                                                              @PathVariable Long requestId,
                                                                              HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        var fullName = jwtService.extractFullName(token);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        List<CommentResponse> comments = commentService.getCommentByRequest(requestId, pageable);

        return ResponseEntity.ok(BaseResponse.OKComment(comments, fullName));
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','STAFF','SUPER_STAFF')")
    public ResponseEntity<BaseResponse<CommentResponse>> postComment(@RequestBody @Valid CommentRequest commentRequest,
                                                                     @RequestParam Long requestId,
                                                                     HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        var response = commentService.assignCommentToRequest(requestId, commentRequest, token);
        return ResponseEntity.ok(BaseResponse.OK(response));
    }


}
