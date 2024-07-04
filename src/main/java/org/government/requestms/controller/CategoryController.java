package org.government.requestms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.CategoryRequest;
import org.government.requestms.dto.response.BaseResponse;
import org.government.requestms.dto.response.CategoryResponse;
import org.government.requestms.exception.DataExistException;
import org.government.requestms.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<String> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) throws DataExistException {
        categoryService.createCategory(categoryRequest);
        return BaseResponse.message("Yeni kateqoriya yaradıldı");
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<CategoryResponse>> getAllCategory() {
        return BaseResponse.OK(categoryService.getAllCategory());
    }

    @GetMapping("category-name")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CategoryResponse> getCategoryName(@RequestParam String categoryName) {
        return BaseResponse.OK(categoryService.getAllCategoryName(categoryName));
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<String> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest,
                                               @PathVariable Long categoryId) throws DataExistException {
        categoryService.updateCategory(categoryRequest, categoryId);
        return BaseResponse.message("Kateqoriya yeniləndi");
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return BaseResponse.message("Kateqoriya silindi");
    }

}
