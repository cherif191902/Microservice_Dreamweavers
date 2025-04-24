package com.esprit.eventservice.controller;

import com.esprit.eventservice.Service.EventService;
import com.esprit.eventservice.request.EventRequest;
import com.esprit.eventservice.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService service;

    @PostMapping
    public ResponseEntity<String> createEvent(
            @RequestBody EventRequest request
    ) {
        return ResponseEntity.ok(service.createEvent(request));
    }
    @GetMapping
    public ResponseEntity<List<EventResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable String eventId,
            @RequestBody EventRequest request
    ) {
        EventResponse updatedEvent = service.updateEvent(eventId, request);
        if (updatedEvent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEvent);
    }


    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable String eventId) {
        boolean isDeleted = service.deleteEvent(eventId);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Event deleted successfully.");
    }
    @PostMapping("/{eventId}/participate")
    public ResponseEntity<String> participateInEvent(@PathVariable String eventId, @RequestBody String userId) {
        boolean isParticipated = service.participateInEvent(eventId, userId);
        if (!isParticipated) {
            return ResponseEntity.badRequest().body("User already participating or event not found.");
        }
        return ResponseEntity.ok("User added to the event successfully.");
    }
}
