package org.government.requestms.service;

import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.CategoryRequest;
import org.government.requestms.dto.response.CategoryResponse;
import org.government.requestms.entity.Category;
import org.government.requestms.exception.ExistCategoryException;
import org.government.requestms.exception.RequestNotFoundException;
import org.government.requestms.mapper.CategoryMapper;
import org.government.requestms.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void createCategory(CategoryRequest categoryRequest) throws ExistCategoryException {
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new ExistCategoryException("Bu kateqoriya artıq mövcuddur");
        }
        Category category = categoryMapper.mapToEntity(categoryRequest);
        categoryRepository.save(category);
    }

    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.mapToListDto(categories);
    }

    public CategoryResponse getAllCategoryName(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RequestNotFoundException("Belə bir kateqoriya mövcud deyil"));
        return categoryMapper.mapToDto(category);
    }


    public void updateCategory(CategoryRequest categoryRequest, Long categoryId) throws ExistCategoryException {
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new ExistCategoryException("Bu kateqoriya artıq mövcuddur");
        }
        Category oldCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RequestNotFoundException("Belə bir kateqoriya mövcud deyil"));

            Category updateCategory = categoryMapper.mapToUpdateEntity(categoryRequest, oldCategory);
            categoryRepository.save(updateCategory);
        }


    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RequestNotFoundException("Belə bir kateqoriya mövcud deyil"));
        categoryRepository.delete(category);
    }
}
