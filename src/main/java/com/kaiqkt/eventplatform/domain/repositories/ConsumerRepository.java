package com.kaiqkt.eventplatform.domain.repositories;

import com.kaiqkt.eventplatform.domain.models.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, String> {
    @Query("SELECT c FROM Consumer c JOIN c.version v WHERE v.producer.service = :service AND v.producer.action = :action AND v.value = :versionValue")
    List<Consumer> find(@Param("service") String service, @Param("action") String action, @Param("versionValue") Integer versionValue);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Consumer c JOIN c.version v WHERE c.service = :service AND v.producer.service = :producerService AND v.producer.action = :action AND v.value = :versionValue")
    boolean exists(@Param("service") String service, @Param("producerService") String producerService, @Param("action") String action, @Param("versionValue") Integer versionValue);

    @Query("SELECT c FROM Consumer c JOIN c.version v WHERE v.producer.service = :service")
    List<Consumer> findAllByService(@Param("service") String service);
}
