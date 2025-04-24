package com.esprit.eventservice.request;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class EventRequest {
    private String name;
    private String description;
    private LocalDateTime date;
    private String location;

}
