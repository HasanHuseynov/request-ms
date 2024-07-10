
package org.government.requestms.repository;

import org.government.requestms.entity.Like;
import org.government.requestms.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByRequest(Request request);
    List<Like> findByEmail(String email);


    boolean existsByRequest_RequestIdAndEmail(Long requestId, String email);
}
