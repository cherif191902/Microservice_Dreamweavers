package com.esprit.eventservice.mapper;

import com.esprit.eventservice.model.Event;
import com.esprit.eventservice.request.EventRequest;
import com.esprit.eventservice.response.EventResponse;
import org.springframework.stereotype.Service;

@Service

public class EventMapper {
    public Event toEvent(EventRequest request) {
        return Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .date(request.getDate())
                .location(request.getLocation())
                .build();
    }

    public EventResponse toEventResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getLocation(),
                event.getParticipantUserIds()
        );
    }
}
