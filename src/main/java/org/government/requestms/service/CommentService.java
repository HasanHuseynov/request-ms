package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.government.requestms.dto.request.CommentRequest;
import org.government.requestms.dto.response.CommentResponse;
import org.government.requestms.entity.Comment;
import org.government.requestms.exception.CommentNotFoundException;
import org.government.requestms.exception.RequestNotFoundException;
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

    public List<CommentResponse> getAllComment() {
        List<Comment> commentEntities = commentRepository.findAll();
        return commentMapper.toDTOs(commentEntities);
    }

    public CommentResponse createNewComment(CommentRequest commentRequest) {

        Comment commentEntity = commentMapper.fromDTO(commentRequest);
        commentEntity = commentRepository.save(commentEntity);
        return commentMapper.toDTO(commentEntity);
    }

    public CommentResponse assignCommentToRequest(Long id, CommentRequest commentRequest) {
        var request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Request not found with username: " + id));
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        var commentEntity = commentMapper.fromDTO(commentRequest);
        commentEntity.setRequest(request);
        commentEntity.setEmail(email);
        commentEntity = commentRepository.save(commentEntity);
        return commentMapper.toDTO(commentEntity);

    }

    public void deleteComment(Long id) {
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> {
            return new RequestNotFoundException("Comment not found with id: " + id);
        });
        log.info("Deleted the comment with details:" + commentEntity.toString());
        commentRepository.delete(commentEntity);
    }

    public void updateComment(Long id, CommentRequest commentRequest) {
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> {
            return new CommentNotFoundException("Comment not found with id:" + id);
        });
        commentMapper.mapUpdateRequestToEntity(commentEntity, commentRequest);
        commentRepository.save(commentEntity);
    }
}
