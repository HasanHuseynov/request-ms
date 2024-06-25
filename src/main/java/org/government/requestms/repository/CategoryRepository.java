package org.government.requestms.repository;

import org.government.requestms.dto.response.CategoryResponse;
import org.government.requestms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);

    Optional<Category> findByCategoryName(String categoryName);
}
