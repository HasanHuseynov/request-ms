package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.RequestResponse;
import org.government.requestms.entity.Category;
import org.government.requestms.entity.Request;
import org.government.requestms.enums.Status;
import org.government.requestms.exception.DataNotFoundException;
import org.government.requestms.mapper.RequestMapper;
import org.government.requestms.repository.CategoryRepository;
import org.government.requestms.repository.CommentRepository;
import org.government.requestms.repository.LikeRepository;
import org.government.requestms.repository.RequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final JWTService jwtService;

    public void createRequest(RequestDto requestDto, String categoryName, String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new DataNotFoundException("Belə bir kateqoriya mövcud deyil"));

        Request requestEntity = requestMapper.mapToEntity(requestDto, categoryName);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var fullName = jwtService.extractFullName(token);
        requestEntity.setEmail(email);
        requestEntity.setFullName(fullName);
        requestEntity.setCategory(category);
        requestRepository.save(requestEntity);
    }

    public List<RequestResponse> getAllRequest(Pageable pageable) {
        var requestPage = requestRepository.findAll(pageable);
        if (requestPage.isEmpty()) {
            return Collections.emptyList();
        }
        List<Request> requestList = requestPage.getContent();
        return getRequestResponses(requestList);
    }

    public List<RequestResponse> searchRequests(Pageable pageable,String keyword) {
        var requestPage = requestRepository.findByDescriptionContaining(keyword,pageable);
        List<Request> requestList  = requestPage.getContent();
        return requestMapper.mapToDtoList(requestList);
    }

    public List<RequestResponse> getRequestByFilter(Status status, String categoryName, String organizationName, String days) {

        {
            Specification<Request> spec = Specification.where(null);
            if (status != null) {
                spec = spec.and(RequestSpecification.hasStatus(status));
            }

            if (categoryName != null && !categoryName.isEmpty()) {
                spec = spec.and(RequestSpecification.hasCategory(categoryName));
            }

            if (organizationName != null && !organizationName.isEmpty()) {
                spec = spec.and(RequestSpecification.hasOrganization(organizationName));
            }

            if ("LastDay".equalsIgnoreCase(days)) {
                spec = RequestSpecification.isCreatedWithinLast1Day();
            } else if ("LastWeek".equalsIgnoreCase(days)) {
                spec = RequestSpecification.isCreatedWithinLast7Days();
            } else if ("LastMonth".equalsIgnoreCase(days)) {
                spec = RequestSpecification.isCreatedWithinLast30Days();
            }

            return requestMapper.mapToDtoList(requestRepository.findAll(spec));
        }
    }

    public List<RequestResponse> getRequest(Pageable pageable) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var requestPage = requestRepository.findByEmail(email,pageable);
        List<Request> requestList = requestPage.getContent();
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
                .orElseThrow(() -> new DataNotFoundException("Müraciət tapılmadı"));

        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new DataNotFoundException("Belə bir kateqoriya mövcud deyil"));

        Request updateRequest = requestMapper.mapToUpdateEntity(oldRequest, requestDto, categoryName);
        updateRequest.setCategory(category);
        requestRepository.save(updateRequest);
    }

    public void deleteRequest(Long requestId) {
        Request requestEntity = requestRepository.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException("Müraciət tapılmadı"));
        requestRepository.delete(requestEntity);
    }

    public void updateStatus(Status status, Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException("Status tapılmadı"));
        request.setStatus(status);
        requestRepository.save(request);

    }

    public List<RequestResponse> getOrganizationRequest(String organizationName,Pageable pageable) {
        Page<Request> requestPage = requestRepository.findByOrganizationName(organizationName,pageable);
        List<Request> requestList = requestPage.getContent();
    public List<RequestResponse> getOrganizationRequest(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        var organizationName = jwtService.extractOrganizationName(token);

        List<Request> requestList = requestRepository.findByOrganizationName(organizationName)
                .orElse(Collections.emptyList());
        return requestMapper.mapToDtoList(requestList);
    }

}

