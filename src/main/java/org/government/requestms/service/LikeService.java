package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.government.requestms.dto.request.LikeRequest;
import org.government.requestms.dto.response.LikeResponse;
import org.government.requestms.entity.Like;
import org.government.requestms.exception.DataExistException;
import org.government.requestms.exception.DataNotFoundException;
import org.government.requestms.mapper.LikeMapper;
import org.government.requestms.repository.LikeRepository;
import org.government.requestms.repository.RequestRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final RequestRepository requestRepository;

    public List<LikeResponse> getAllLike() {
        List<Like> likeEntities = this.likeRepository.findAll();
        return this.likeMapper.toDTOs(likeEntities);
    }


    public LikeResponse createNewLike() {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();

        var likeEntity = new Like();
        likeEntity.setEmail(name);
        likeEntity = (Like) likeRepository.save(likeEntity);
        return this.likeMapper.toDTO(likeEntity);
    }


    public void assignLikeToRequest(Long requestId) throws DataExistException {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        var request = requestRepository.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException("Request not found with username: " + requestId));

        if (likeRepository.existsByRequest_RequestIdAndEmail(requestId, name)) {
            throw new DataExistException("Bu müraciətə artıq like vermisiz");
        }
        var likeEntity = new Like();
        likeEntity.setEmail(name);
        likeEntity.setRequest(request);
        likeRepository.save(likeEntity);

    }


    public void deleteLike(Long likeId) {
        Like likeEntity = (Like) this.likeRepository.findById(likeId).orElseThrow(() -> {
            return new DataNotFoundException("Like not found with likeId: " + likeId);
        });
        log.info("Deleted the like with details:" + likeEntity.toString());
        this.likeRepository.delete(likeEntity);
    }

    public void updateLike(Long id, LikeRequest likeRequest) {
        Like likeEntity = (Like) this.likeRepository.findById(id).orElseThrow(() -> {
            return new DataNotFoundException("Like not found with id:" + id);
        });
        this.likeMapper.mapUpdateRequestToEntity(likeEntity, likeRequest);
        this.likeRepository.save(likeEntity);
    }

}
