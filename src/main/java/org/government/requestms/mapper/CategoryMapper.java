package org.government.requestms.mapper;

import org.government.requestms.dto.request.CategoryRequest;
import org.government.requestms.dto.response.CategoryResponse;
import org.government.requestms.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {
    Category mapToEntity(CategoryRequest categoryRequest);

    List<CategoryResponse> mapToListDto(List<Category> categories);

    CategoryResponse mapToDto(Category category);

    @Mapping(target = "categoryId", ignore = true)
    Category mapToUpdateEntity(CategoryRequest categoryRequest, @MappingTarget Category oldCategory);
}
