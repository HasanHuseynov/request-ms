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

public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {
    Page<Request> findByEmail(String email, Pageable pageable);

    @Query("SELECT r FROM Request r WHERE LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Request> findByDescriptionContaining(@Param("keyword") String keyword, Pageable pageable);

    Page<Request> findByOrganizationName(String organizationName, Pageable pageable);

    List<Request> findByStatusInAndLastModifiedBefore(List<Status> status, LocalDateTime lastModified);

}
