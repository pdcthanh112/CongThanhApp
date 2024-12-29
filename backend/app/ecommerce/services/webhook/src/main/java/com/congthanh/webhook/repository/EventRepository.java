package com.congthanh.webhook.repository;

import com.congthanh.webhook.constain.enums.EventName;
import com.congthanh.webhook.model.entity.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @EntityGraph(attributePaths = {"webhookEvents.webhook"})
    Optional<Event> findByName(EventName name);

}
