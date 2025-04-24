package com.esprit.eventservice.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EventResponse {
    private String id;
    private String name;
    private String description;
    private LocalDateTime date;
    private String location;
    private Set<String> participantUserIds;
}
