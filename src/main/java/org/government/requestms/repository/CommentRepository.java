package org.government.requestms.repository;

import org.government.requestms.entity.Comment;
import org.government.requestms.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByRequest(Request request);

    List<Comment> findByRequest_RequestId(Long id);
    Page<Comment> findByFullName(String organizationName, Pageable pageable);

}
