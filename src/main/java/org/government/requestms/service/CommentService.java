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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final RequestRepository requestRepository;
    private final JWTService jwtService;

    public List<CommentResponse> getAllComment() {
        List<Comment> commentEntities = commentRepository.findAll();
        return commentMapper.toDTOs(commentEntities);
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

        Long commentCount = commentRepository.countByRequest(requestRepository.getReferenceById(request.getRequestId()));


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
        CommentResponse response = commentMapper.toDTO(commentEntity);
        response.setCommentCount(Math.toIntExact(commentCount + 1));
        return response;

    }


    public void deleteComment(Long id) {
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> {
            return new DataNotFoundException("Comment not found with id: " + id);
        });
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
