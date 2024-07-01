package org.government.requestms.service;

import org.government.requestms.entity.Request;
import org.government.requestms.enums.Status;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class RequestSpecification {

    public static Specification<Request> hasStatus(Status status) {
        return (root, query, criteriaBuilder) -> status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Request> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) -> categoryId == null ? null : criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Request> hasOrganization(String organizationName) {
        return (root, query, criteriaBuilder) -> organizationName == null ? null : criteriaBuilder.like(root.get("organizationName"), "%" + organizationName + "%");
    }

    public static Specification<Request> isCreatedBefore(LocalDateTime date) {
        return (root, query, criteriaBuilder) -> date == null ? null : criteriaBuilder.equal(root.get("createDate"), date);
    }
}
