package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.RequestResponse;
import org.government.requestms.entity.Category;
import org.government.requestms.entity.Request;
import org.government.requestms.enums.Status;
import org.government.requestms.exception.DataExistException;
import org.government.requestms.exception.DataNotFoundException;
import org.government.requestms.mapper.RequestMapper;
import org.government.requestms.repository.CategoryRepository;
import org.government.requestms.repository.CommentRepository;
import org.government.requestms.repository.LikeRepository;
import org.government.requestms.repository.RequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    public List<RequestResponse> searchRequests(String keyword, String id, Pageable pageable) {
        var requestPage = requestRepository.findByDescriptionContainingOrId(keyword, id, pageable);
        List<Request> requestList = requestPage.getContent();
        return getRequestResponses(requestList);
    }

    public List<RequestResponse> getRequestByFilter(Status status, String categoryName, String organizationName, String days,
                                                    Pageable pageable) {

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

            if ("Son bir gün".equalsIgnoreCase(days)) {
                spec = RequestSpecification.isCreatedWithinLast1Day();
            } else if ("Son bir həftə".equalsIgnoreCase(days)) {
                spec = RequestSpecification.isCreatedWithinLast7Days();
            } else if ("Son bir ay".equalsIgnoreCase(days)) {
                spec = RequestSpecification.isCreatedWithinLast30Days();
            }

            return getRequestResponses(requestRepository.findAll(spec, pageable).getContent());
        }
    }

    public List<RequestResponse> getUserRequest(Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var requestPage = requestRepository.findByEmail(email, pageable);
        List<Request> requestList = requestPage.getContent();
        return getRequestResponses(requestList);
    }

    private List<RequestResponse> getRequestResponses(List<Request> requestList) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<RequestResponse> responseList = requestMapper.mapToDtoList(requestList);
        responseList.forEach(response -> {
            Long commentCount = commentRepository.countByRequest(requestRepository.getReferenceById(response.getRequestId()));
            Long likeCount = likeRepository.countByRequest(requestRepository.getReferenceById(response.getRequestId()));
            boolean isLiked = likeRepository.findByEmailAndRequest_RequestId(email, response.getRequestId()) != null;
            response.setCommentCount(Math.toIntExact(commentCount));
            response.setLikeCount(Math.toIntExact(likeCount));
            response.setLikeSuccess(isLiked);

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

    public void deleteRequest(Long requestId, String token) throws DataExistException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        var email = jwtService.extractUsername(token);
        Request requestEntity = requestRepository.findByRequestIdAndEmail(requestId, email)
                .orElseThrow(() -> new DataNotFoundException("Müraciətiniz tapılmadı"));
        if (requestEntity.getStatus() == Status.Gözləmədə) {
            requestRepository.delete(requestEntity);
        } else
            throw new DataExistException("Müraciətin statusu dəyişdiyi üçün silmə əməliiyyatı etmək mümkün olmayacaq");

    }

    public void updateStatus(Status status, Long requestId) throws DataExistException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException("Status tapılmadı"));
        if (!request.getStatus().equals(Status.Arxivdədir)) {
            request.setStatus(status);
            requestRepository.save(request);
        } else throw new DataExistException("Arxiv statusu dəyişilə bilməz");

    }

    @Scheduled(fixedRate = 86400000)
    public void processChangeStatusForTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = now.minusDays(30);

        List<Status> statusList = Arrays.asList(Status.Əssasızdır, Status.Həlledildi);

        List<Request> requests = requestRepository.findByStatusInAndLastModifiedBefore(statusList, dateTime);

        requests.forEach(request -> {
            LocalDateTime lastModified = request.getLastModified();
            if (lastModified.isBefore(dateTime)) {
                request.setStatus(Status.Arxivdədir);
                requestRepository.save(request);
            }
        });
    }


    public List<RequestResponse> getOrganizationRequest(String token, Pageable pageable) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        var organizationName = jwtService.extractOrganizationName(token);

        Page<Request> requestPage = requestRepository.findByOrganizationName(organizationName, pageable);
        List<Request> requestList = requestPage.getContent();
        return getRequestResponses(requestList);
    }


    public RequestResponse getRequestById(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException("Müraciət tapılmadı"));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean isLiked = likeRepository.findByEmailAndRequest_RequestId(email, requestId) != null;
        RequestResponse response = requestMapper.mapToDto(request);

        Long commentCount = commentRepository.countByRequest(request);
        Long likeCount = likeRepository.countByRequest(request);

        response.setCommentCount(Math.toIntExact(commentCount));
        response.setLikeCount(Math.toIntExact(likeCount));
        response.setLikeSuccess(isLiked);
        return response;
    }
}


