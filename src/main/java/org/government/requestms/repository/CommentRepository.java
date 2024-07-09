package org.government.requestms.repository;

import org.government.requestms.entity.Comment;
import org.government.requestms.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByRequest(Request request);
    Page<Comment> findByFullName(String organizationName, Pageable pageable);

}
