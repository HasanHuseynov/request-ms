package org.government.requestms.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.government.requestms.dto.request.CategoryRequest;
import org.government.requestms.dto.response.CategoryResponse;
import org.government.requestms.exception.ExistCategoryException;
import org.government.requestms.service.CategoryService;
import org.government.requestms.service.LikeService;
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
    public String createCategory(@Valid @RequestBody CategoryRequest categoryRequest) throws ExistCategoryException {
        categoryService.createCategory(categoryRequest);
        return "Yeni kateqoriya yaradıldı";
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("categoryName")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse getCategoryName(@RequestParam String categoryName) {
        return categoryService.getAllCategoryName(categoryName);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String updateCategory(@Valid @RequestBody CategoryRequest categoryRequest,
                                 @PathVariable Long categoryId) throws ExistCategoryException {
        categoryService.updateCategory(categoryRequest, categoryId);
        return "Kateqoriya yeniləndi";
    }

}
