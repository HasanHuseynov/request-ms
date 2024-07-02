package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.government.requestms.dto.request.LikeRequest;
import org.government.requestms.dto.response.LikeResponse;
import org.government.requestms.entity.Like;
import org.government.requestms.exception.ExistCategoryException;
import org.government.requestms.exception.LikeNotFoundException;
import org.government.requestms.exception.RequestNotFoundException;
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


    public LikeResponse assignLikeToRequest(Long id) throws ExistCategoryException {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        var request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Request not found with username: " + id));

        if (likeRepository.existsByRequest_RequestIdAndEmail(id, name)) {
            throw new ExistCategoryException("Bu müraciətə artıq like vermisiz");
        }

        var likeEntity = new Like();
        likeEntity.setEmail(name);
        likeEntity.setRequest(request);
        likeRepository.save(likeEntity);
        return likeMapper.toDTO(likeEntity);

    }


    public void deleteLike(Long id) {
        Like likeEntity = (Like) this.likeRepository.findById(id).orElseThrow(() -> {
            return new RequestNotFoundException("Like not found with id: " + id);
        });
        log.info("Deleted the like with details:" + likeEntity.toString());
        this.likeRepository.delete(likeEntity);
    }

    public void updateLike(Long id, LikeRequest likeRequest) {
        Like likeEntity = (Like) this.likeRepository.findById(id).orElseThrow(() -> {
            return new LikeNotFoundException("Like not found with id:" + id);
        });
        this.likeMapper.mapUpdateRequestToEntity(likeEntity, likeRequest);
        this.likeRepository.save(likeEntity);
    }

}
