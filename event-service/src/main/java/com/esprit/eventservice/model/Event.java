package com.esprit.eventservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String description;
    private LocalDateTime date;
    private String location;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "event_participants", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "user_id")
    private Set<String> participantUserIds = new HashSet<>();
}
