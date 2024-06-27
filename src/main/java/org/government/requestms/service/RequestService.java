package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.RequestResponseForAdmin;
import org.government.requestms.dto.response.RequestResponseForUser;
import org.government.requestms.entity.Request;
import org.government.requestms.exception.AllException;
import org.government.requestms.mapper.RequestMapper;
import org.government.requestms.repository.CommentRepository;
import org.government.requestms.repository.LikeRepository;
import org.government.requestms.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public void createRequest(RequestDto requestDto) {
        Request requestEntity = requestMapper.mapToEntity(requestDto);
        requestRepository.save(requestEntity);
    }

    public List<RequestResponseForAdmin> getAllRequest() {
        List<Request> requestList = requestRepository.findAll();
        if (requestList.isEmpty()) {
            return Collections.emptyList();
        }
        List<RequestResponseForAdmin> responseList = requestMapper.mapToDtoListAdmin(requestList);
        responseList.forEach(response -> {
            Long commentCount = commentRepository.countByRequest(requestRepository.getOne(response.getRequestId()));
            Long likeCount = likeRepository.countByRequest(requestRepository.getOne(response.getRequestId()));
            response.setCommentCount(Math.toIntExact(commentCount));
            response.setLikeCount(Math.toIntExact(likeCount));
        });
        return responseList;
    }


    public List<RequestResponseForUser> getRequest(String email) {
        List<Request> requestList = requestRepository.findByEmail(email)
                .orElse(null);
        return requestMapper.mapToDtoListUser(requestList);

    }

    public void updateRequest(Long requestId, RequestDto requestDto) {
        Request oldRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new AllException("request not found"));
        if (oldRequest != null) {
            Request updateRequest = requestMapper.mapToUpdateEntity(oldRequest, requestDto);
            requestRepository.save(updateRequest);
        }
    }

    public void deleteRequest(Long requestId) {
        Request requestEntity = requestRepository.findById(requestId)
                .orElseThrow(() -> new AllException("Request not found"));
        requestRepository.delete(requestEntity);
    }
}
