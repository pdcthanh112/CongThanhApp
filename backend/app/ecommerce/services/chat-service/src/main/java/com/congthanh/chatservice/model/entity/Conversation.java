package com.congthanh.chatservice.model.entity;

import com.congthanh.chatservice.constant.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "conversation")
public class Conversation extends AbstractAuditEntity {

    @Id
    private Long id;

    @Column(name = "conversation_id")
    private String conversationId;

    @Column(name = "from_user")
    private String fromUser;

    @Column(name = "to_user")
    private String toUser;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
