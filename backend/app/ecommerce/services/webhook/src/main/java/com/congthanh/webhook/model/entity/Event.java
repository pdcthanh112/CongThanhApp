package com.congthanh.webhook.model.entity;

import com.congthanh.webhook.constain.enums.EventName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EventName name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    private List<WebhookEvent> webhookEvents;
}
