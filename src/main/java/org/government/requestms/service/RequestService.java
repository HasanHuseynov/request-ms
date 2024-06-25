package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.RequestResponseForAdmin;
import org.government.requestms.dto.response.RequestResponseForUser;
import org.government.requestms.entity.Category;
import org.government.requestms.entity.Request;
import org.government.requestms.exception.RequestNotFoundException;
import org.government.requestms.mapper.RequestMapper;
import org.government.requestms.repository.CategoryRepository;
import org.government.requestms.repository.RequestRepository;
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

    public void createRequest(RequestDto requestDto, String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RequestNotFoundException("Belə bir kateqoriya mövcud deyil"));

        Request requestEntity = requestMapper.mapToEntity(requestDto, categoryName);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        requestEntity.setEmail(email);
        requestEntity.setCategory(category);
        requestRepository.save(requestEntity);
    }

    public List<RequestResponseForAdmin> getAllRequest() {
        List<Request> requestList = requestRepository.findAll();
        if (requestList.isEmpty()) {
            return Collections.emptyList();
        }
        return requestMapper.mapToDtoListAdmin(requestList);
    }

    public List<RequestResponseForUser> getRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Request> requestList = requestRepository.findByEmail(email)
                .orElse(null);
        return requestMapper.mapToDtoListUser(requestList);

    }

    public void updateRequest(Long requestId, RequestDto requestDto) {
        Request oldRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("request not found"));
        if (oldRequest != null) {
            Request updateRequest = requestMapper.mapToUpdateEntity(oldRequest, requestDto);
            requestRepository.save(updateRequest);
        }
    }

    public void deleteRequest(Long requestId) {
        Request requestEntity = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Request not found"));
        requestRepository.delete(requestEntity);
    }
}
