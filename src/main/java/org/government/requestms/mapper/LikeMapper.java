package org.government.requestms.mapper;
import java.util.List;
import org.government.requestms.dto.request.LikeRequest;
import org.government.requestms.dto.response.LikeResponse;
import org.government.requestms.entity.Like;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(
    builder = @Builder(
    disableBuilder = true
)
)
public interface LikeMapper {
    Like fromDTO(LikeRequest likeRequest);

    LikeResponse toDTO(Like like);

    List<LikeResponse> toDTOs(List<Like> likes);

    void mapUpdateRequestToEntity(@MappingTarget Like like, LikeRequest likeRequest);
}
