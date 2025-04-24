package com.esprit.eventservice.repository;

import com.esprit.eventservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
