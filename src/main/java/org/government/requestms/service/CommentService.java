package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.government.requestms.dto.request.CommentRequest;
import org.government.requestms.dto.response.CommentResponse;
import org.government.requestms.entity.Comment;
import org.government.requestms.exception.DataNotFoundException;
import org.government.requestms.mapper.CommentMapper;
import org.government.requestms.repository.CommentRepository;
import org.government.requestms.repository.RequestRepository;
import org.hibernate.annotations.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final RequestRepository requestRepository;
    private final JWTService jwtService;

    public List<CommentResponse> getAllComment(Pageable pageable) {
        var commentEntities = commentRepository.findAll(pageable);
        if (commentEntities.isEmpty()) {
            return Collections.emptyList();
        }
        List<Comment> commentList = commentEntities.getContent();
        return commentMapper.toDTOs(commentList);
    }

    public List<CommentResponse> getOrganizationComment(Pageable pageable, String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        var organizationName = jwtService.extractOrganizationName(token);
        Page<Comment> commentPage = commentRepository.findByFullName(organizationName, pageable);
        List<Comment> commentList = commentPage.getContent();
        return commentMapper.toDTOs(commentList);

    }

    public CommentResponse createNewComment(CommentRequest commentRequest) {

        Comment commentEntity = commentMapper.fromDTO(commentRequest);
        commentEntity = commentRepository.save(commentEntity);
        return commentMapper.toDTO(commentEntity);
    }

    public CommentResponse assignCommentToRequest(Long requestId, CommentRequest commentRequest, String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        var request = requestRepository.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException("Request not found with username: " + requestId));
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        var commentEntity = commentMapper.fromDTO(commentRequest);
        var fullName = jwtService.extractFullName(token);

        var organizationName = jwtService.extractOrganizationName(token);

        List<String> authorities = jwtService.extractAuthorities(token);

        if (authorities.contains("STAFF") || authorities.contains("SUPER_STAFF")) {
            commentEntity.setFullName(organizationName);
            commentEntity.setAuthority("STAFF");

        } else {
            commentEntity.setAuthority("USER");
            commentEntity.setFullName(fullName);
        }
        commentEntity.setEmail(email);
        commentEntity.setRequest(request);
        commentEntity = commentRepository.save(commentEntity);
        return commentMapper.toDTO(commentEntity);

    }

    public List<CommentResponse> getCommentByRequest(Long id) {
        var commentsEntity = commentRepository.findByRequest_RequestId(id);
        return commentMapper.toDTOs(commentsEntity);
    }


    public void deleteComment(Long id, String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        var email = jwtService.extractUsername(token);
        Comment commentEntity = commentRepository.findByEmailAndRequest_RequestId(email, id)
                .orElseThrow(() -> new DataNotFoundException("Comment not found with id: " + id));

        log.info("Deleted the comment with details:" + commentEntity.toString());
        commentRepository.delete(commentEntity);
    }

    public void updateComment(Long id, CommentRequest commentRequest) {
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> {
            return new DataNotFoundException("Comment not found with id:" + id);
        });
        commentMapper.mapUpdateRequestToEntity(commentEntity, commentRequest);
        commentRepository.save(commentEntity);
    }

}
