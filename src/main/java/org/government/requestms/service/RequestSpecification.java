package org.government.requestms.service;

import org.government.requestms.entity.Request;
import org.government.requestms.enums.Status;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class RequestSpecification {

    public static Specification<Request> hasStatus(Status status) {
        return (root, query, criteriaBuilder) -> status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Request> hasCategory(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if (categoryName == null || categoryName.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.join("category").get("categoryName"), categoryName);
        };
    }

    public static Specification<Request> hasOrganization(String organizationName) {
        return (root, query, criteriaBuilder) -> organizationName == null ? null : criteriaBuilder.like(root.get("organizationName"), "%" + organizationName + "%");
    }

    public static Specification<Request> isCreatedWithinLastDays(int days) {
        return (root, query, criteriaBuilder) -> {
            if (days <= 0) {
                return null;
            }
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime date = now.minusDays(days);
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), date);
        };
    }

    public static Specification<Request> isCreatedWithinLast1Day() {
        return isCreatedWithinLastDays(1);
    }

    public static Specification<Request> isCreatedWithinLast7Days() {
        return isCreatedWithinLastDays(7);
    }

    public static Specification<Request> isCreatedWithinLast30Days() {
        return isCreatedWithinLastDays(30);
    }
}
