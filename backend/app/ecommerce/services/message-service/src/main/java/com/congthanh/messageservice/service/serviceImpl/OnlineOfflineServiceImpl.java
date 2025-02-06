package com.congthanh.messageservice.service.serviceImpl;

import com.congthanh.messageservice.model.entity.ChatMessage;
import com.congthanh.messageservice.service.OnlineOfflineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineOfflineServiceImpl implements OnlineOfflineService {

    private final Set<UUID> onlineUsers;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @Override
    public void addOnlineUser(Principal user) {
        if (user == null) return;
//        UserDetailsImpl userDetails = getUserDetails(user);
//        log.info("{} is online", userDetails.getUsername());
        for (UUID id : onlineUsers) {
            simpMessageSendingOperations.convertAndSend(
                    "/topic/" + id,
                    ChatMessage.builder()
                            .messageType(MessageType.FRIEND_ONLINE)
                            .userConnection(UserConnection.builder().connectionId(userDetails.getId()).build())
                            .build());
        }
        onlineUsers.add(userDetails.getId());
    }

    @Override
    public void removeOnlineUser(Principal user) {
        if (user != null) {
            UserDetailsImpl userDetails = getUserDetails(user);
            log.info("{} went offline", userDetails.getUsername());
            onlineUsers.remove(userDetails.getId());
            userSubscribed.remove(userDetails.getId());
            for (UUID id : onlineUsers) {
                simpMessageSendingOperations.convertAndSend(
                        "/topic/" + id,
                        ChatMessage.builder()
                                .messageType(MessageType.FRIEND_OFFLINE)
                                .userConnection(UserConnection.builder().connectionId(userDetails.getId()).build())
                                .build());
            }
        }
    }
}
