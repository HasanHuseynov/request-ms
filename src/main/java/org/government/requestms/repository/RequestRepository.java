package org.government.requestms.repository;

import org.government.requestms.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<List<Request>> findByEmail(String email);
}
