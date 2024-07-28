package org.government.requestms.repository;

import org.government.requestms.entity.Request;
import org.government.requestms.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {
    Page<Request> findByEmail(String email, Pageable pageable);

    @Query("SELECT r FROM Request r WHERE r.organizationName = :organizationName AND (LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR CAST(r.requestId AS string) LIKE CONCAT(:id, '%'))")
    Page<Request> findByOrganizationNameAndDescriptionContainingOrId(@Param("organizationName") String organizationName, @Param("keyword") String keyword, @Param("id") String id, Pageable pageable);

    @Query("SELECT r FROM Request r WHERE r.organizationName = :organizationName AND r.requestId = :id AND LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Request> findByOrganizationNameAndDescriptionContainingAndId(@Param("organizationName") String organizationName, @Param("keyword") String keyword, @Param("id") Long id, Pageable pageable);
    Page<Request> findByOrganizationName(String organizationName, Pageable pageable);

    List<Request> findByStatusInAndLastModifiedBefore(List<Status> status, LocalDateTime lastModified);

    Optional<Request> findByRequestIdAndEmail(Long requestId, String email);

    List<Request> findByCategory_CategoryId(Long categoryId);


}
