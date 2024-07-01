package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import org.government.requestms.client.OrganizationServiceClient;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.OrganizationResponse;
import org.government.requestms.dto.response.RequestResponse;
import org.government.requestms.entity.Category;
import org.government.requestms.entity.Request;
import org.government.requestms.enums.Status;
import org.government.requestms.exception.RequestNotFoundException;
import org.government.requestms.mapper.RequestMapper;
import org.government.requestms.repository.CategoryRepository;
import org.government.requestms.repository.CommentRepository;
import org.government.requestms.repository.LikeRepository;
import org.government.requestms.repository.RequestRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final CategoryRepository categoryRepository;
    private final OrganizationServiceClient organizationServiceClient;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public void createRequest(RequestDto requestDto, String categoryName, String token) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RequestNotFoundException("Belə bir kateqoriya mövcud deyil"));
        OrganizationResponse organizationResponse =
                organizationServiceClient.getOrganizationByName(requestDto, token).getBody();


        Request requestEntity = requestMapper.mapToEntity(requestDto, categoryName);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        requestEntity.setEmail(email);
        requestEntity.setCategory(category);
        assert organizationResponse != null;
        requestEntity.setOrganizationName(organizationResponse.getData().getName());
        requestRepository.save(requestEntity);
    }

    public List<RequestResponse> getAllRequest() {
        List<Request> requestList = requestRepository.findAll();
        if (requestList.isEmpty()) {
            return Collections.emptyList();
        }
        return getRequestResponses(requestList);
    }

    public List<RequestResponse> getRequestsFilter(Status status, Long categoryId, String organizationName,LocalDateTime createDate) {
        Specification<Request> spec = Specification.where(null);
        if (status != null) {
            spec = spec.and(RequestSpecification.hasStatus(status));
        }
        if (categoryId != null) {
            spec = spec.and(RequestSpecification.hasCategory(categoryId));
        }
        if (organizationName != null && !organizationName.isEmpty()) {
            spec = spec.and(RequestSpecification.hasOrganization(organizationName));
        }

        if (createDate != null) {
            spec = spec.and(RequestSpecification.isCreatedBefore(createDate));
        }

        return requestMapper.mapToDtoList(requestRepository.findAll(spec));
    }

    public List<RequestResponse> getRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Request> requestList = requestRepository.findByEmail(email)
                .orElse(Collections.emptyList());
        return getRequestResponses(requestList);
    }

    private List<RequestResponse> getRequestResponses(List<Request> requestList) {
        List<RequestResponse> responseList = requestMapper.mapToDtoList(requestList);
        responseList.forEach(response -> {
            Long commentCount = commentRepository.countByRequest(requestRepository.getReferenceById(response.getRequestId()));
            Long likeCount = likeRepository.countByRequest(requestRepository.getReferenceById(response.getRequestId()));
            response.setCommentCount(Math.toIntExact(commentCount));
            response.setLikeCount(Math.toIntExact(likeCount));
        });
        return responseList;
    }

    public void updateRequest(Long requestId, RequestDto requestDto, String categoryName) {
        Request oldRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Müraciət tapılmadı"));

        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RequestNotFoundException("Belə bir kateqoriya mövcud deyil"));

        if (oldRequest != null) {
            Request updateRequest = requestMapper.mapToUpdateEntity(oldRequest, requestDto, categoryName);
            updateRequest.setCategory(category);
            requestRepository.save(updateRequest);
        }
    }

    public void deleteRequest(Long requestId) {
        Request requestEntity = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Müraciət tapılmadı"));
        requestRepository.delete(requestEntity);
    }
}
