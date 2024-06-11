package com.kaiqkt.eventplatform.domain.repositories;

import com.kaiqkt.eventplatform.domain.models.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, String> {
    @Query("SELECT p FROM Producer p JOIN p.versions v WHERE p.service = :service AND p.action = :action AND v.value = :versionValue")
    Optional<Producer> find(@Param("service") String service, @Param("action") String action, @Param("versionValue") Integer versionValue);

    Optional<Producer> findByServiceAndAction(String service, String action);

    @Query("SELECT p FROM Producer p WHERE (:service IS NULL OR p.service = :service) AND (:action IS NULL OR p.action = :action)")
    Page<Producer> findAll(String service, String action, Pageable pageable);
}
