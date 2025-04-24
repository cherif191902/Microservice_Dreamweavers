package com.esprit.eventservice.Service;

import com.esprit.eventservice.mapper.EventMapper;
import com.esprit.eventservice.model.Event;
import com.esprit.eventservice.repository.EventRepository;
import com.esprit.eventservice.request.EventRequest;
import com.esprit.eventservice.response.EventResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class EventService {
    private final EventRepository repository;
    private final EventMapper mapper;
    public String createEvent(EventRequest request) {
        var event = this.repository.save(mapper.toEvent(request));
        return event.getId();
    }

    public List<EventResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toEventResponse)
                .collect(Collectors.toList());
    }

    public EventResponse updateEvent(String eventId, EventRequest request) {
        Optional<Event> existingEventOpt = repository.findById(eventId);
        if (existingEventOpt.isEmpty()) {
            return null;
        }

        Event existingEvent = existingEventOpt.get();

        existingEvent.setName(request.getName());
        existingEvent.setDescription(request.getDescription());
        existingEvent.setDate(request.getDate());
        existingEvent.setLocation(request.getLocation());


        Event updatedEvent = repository.save(existingEvent);
        return mapper.toEventResponse(updatedEvent);
    }

    public boolean deleteEvent(String eventId) {
        if (!repository.existsById(eventId)) {
            return false;  
        }
        repository.deleteById(eventId);
        return true;
    }

    public boolean participateInEvent(String eventId, String userId) {
        Optional<Event> eventOpt = repository.findById(eventId);
        if (eventOpt.isEmpty()) {
            return false;
        }

        Event event = eventOpt.get();

        if (event.getParticipantUserIds().contains(userId)) {
            return false;
        }


        event.getParticipantUserIds().add(userId);
        repository.save(event);
        return true;
    }
}
