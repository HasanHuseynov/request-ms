package org.government.requestms.mapper;
import java.util.List;
import org.government.requestms.dto.request.CommentRequest;
import org.government.requestms.dto.response.CommentResponse;
import org.government.requestms.entity.Comment;
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
public interface CommentMapper {
    Comment fromDTO(CommentRequest commentRequest);

    CommentResponse toDTO(Comment comment);

    List<CommentResponse> toDTOs(List<Comment> comments);

    void mapUpdateRequestToEntity(@MappingTarget Comment comment, CommentRequest commentRequest);
}
