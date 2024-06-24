package org.government.requestms.service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.government.requestms.dto.request.LikeRequest;
import org.government.requestms.dto.response.LikeResponse;
import org.government.requestms.entity.Like;
import org.government.requestms.exception.LikeNotFoundException;
import org.government.requestms.mapper.LikeMapper;
import org.government.requestms.repository.LikeRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    public List<LikeResponse> getAllLike() {
        List<Like> likeEntities = this.likeRepository.findAll();
        return this.likeMapper.toDTOs(likeEntities);
    }

    public LikeResponse createNewLike(LikeRequest likeRequest) {
        Like likeEntity = this.likeMapper.fromDTO(likeRequest);
        likeEntity = (Like)this.likeRepository.save(likeEntity);
        return this.likeMapper.toDTO(likeEntity);
    }

    public void deleteLike(Long id) {
        Like likeEntity = (Like)this.likeRepository.findById(id).orElseThrow(() -> {
            return new LikeNotFoundException("Like not found with id: " + id);
        });
        log.info("Deleted the like with details:" + likeEntity.toString());
        this.likeRepository.delete(likeEntity);
    }

    public void updateLike(Long id, LikeRequest likeRequest) {
        Like likeEntity = (Like)this.likeRepository.findById(id).orElseThrow(() -> {
            return new LikeNotFoundException("Like not found with id:" + id);
        });
        this.likeMapper.mapUpdateRequestToEntity(likeEntity, likeRequest);
        this.likeRepository.save(likeEntity);
    }

}