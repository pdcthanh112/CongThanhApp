package com.congthanh.webhook.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "webhook")
public class Webhook extends AbstractAuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payload_url")
    private String payloadUrl;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "secret")
    private String secret;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "webhook")
    List<WebhookEvent> webhookEvents;
}
