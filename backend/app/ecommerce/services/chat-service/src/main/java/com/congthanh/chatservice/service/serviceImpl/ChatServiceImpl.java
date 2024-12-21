package com.congthanh.chatservice.service.serviceImpl;

import com.congthanh.chatservice.model.entity.ChatMessage;
import com.congthanh.chatservice.model.entity.Conversation;
import com.congthanh.chatservice.repository.conversation.ConversationRepository;
import com.congthanh.chatservice.service.ChatService;
import com.congthanh.chatservice.service.OnlineOfflineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    private final ConversationRepository conversationRepository;

    private final OnlineOfflineService onlineOfflineService;

    public void sendMessageToConvId(
            ChatMessage chatMessage, String conversationId, SimpMessageHeaderAccessor headerAccessor) {
        UserDetailsImpl userDetails = getUser();
        String fromUserId = userDetails.getId();
        String toUserId = chatMessage.getReceiverId();
        populateContext(chatMessage, userDetails);
        boolean isTargetOnline = onlineOfflineService.isUserOnline(toUserId);
        boolean isTargetSubscribed =
                onlineOfflineService.isUserSubscribed(toUserId, "/topic/" + conversationId);
        chatMessage.setId(UUID.randomUUID());

        Conversation.ConversationEntityBuilder conversationEntityBuilder =
                ConversationEntity.builder();

        conversationEntityBuilder
                .id(chatMessage.getId())
                .fromUser(fromUserId)
                .toUser(toUserId)
                .content(chatMessage.getContent())
                .convId(conversationId);
        if (!isTargetOnline) {
            log.info(
                    "{} is not online. Content saved in unseen messages", chatMessage.getReceiverUsername());
            conversationEntityBuilder.deliveryStatus(MessageDeliveryStatusEnum.NOT_DELIVERED.toString());
            chatMessage.setMessageDeliveryStatusEnum(MessageDeliveryStatusEnum.NOT_DELIVERED);

        } else if (!isTargetSubscribed) {
            log.info(
                    "{} is online but not subscribed. sending to their private subscription",
                    chatMessage.getReceiverUsername());
            conversationEntityBuilder.deliveryStatus(MessageDeliveryStatusEnum.DELIVERED.toString());
            chatMessage.setMessageDeliveryStatusEnum(MessageDeliveryStatusEnum.DELIVERED);
            simpMessageSendingOperations.convertAndSend("/topic/" + toUserId.toString(), chatMessage);

        } else {
            conversationEntityBuilder.deliveryStatus(MessageDeliveryStatusEnum.SEEN.toString());
            chatMessage.setMessageDeliveryStatusEnum(MessageDeliveryStatusEnum.SEEN);
        }
        conversationRepository.save(conversationEntityBuilder.build());
        simpMessageSendingOperations.convertAndSend("/topic/" + conversationId, chatMessage);
    }

    private void populateContext(ChatMessage chatMessage, UserDetailsImpl userDetails) {
        chatMessage.setSenderUsername(userDetails.getUsername());
        chatMessage.setSenderId(userDetails.getId());
    }

    public UserDetailsImpl getUser() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserDetailsImpl) object;
    }


}
