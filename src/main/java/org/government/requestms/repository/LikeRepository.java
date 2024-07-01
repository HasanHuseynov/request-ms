
package org.government.requestms.repository;

import org.government.requestms.entity.Like;
import org.government.requestms.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByRequest(Request request);

    boolean existsByRequest_RequestIdAndEmail(Long requestId, String email);
}
