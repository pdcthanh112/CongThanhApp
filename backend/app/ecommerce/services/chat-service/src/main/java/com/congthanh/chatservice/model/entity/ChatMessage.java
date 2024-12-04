package com.congthanh.chatservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long conversationId;

    private String sender;

    private String recipient;

    private String content;

    private Instant timestamp;

}
