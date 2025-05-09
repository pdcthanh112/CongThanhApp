package com.congthanh.messageservice.listener;

import com.congthanh.messageservice.service.OnlineOfflineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final OnlineOfflineService onlineOfflineService;

    private final Map<String, String> simpSessionIdToSubscriptionId;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        onlineOfflineService.removeOnlineUser(event.getUser());
    }

    @EventListener
    @SendToUser
    public void handleSubscribeEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        String subscribedChannel =
                (String) sessionSubscribeEvent.getMessage().getHeaders().get("simpDestination");
        String simpSessionId =
                (String) sessionSubscribeEvent.getMessage().getHeaders().get("simpSessionId");
        if (subscribedChannel == null) {
            log.error("SUBSCRIBED TO NULL?? WAT?!");
            return;
        }
        simpSessionIdToSubscriptionId.put(simpSessionId, subscribedChannel);
        onlineOfflineService.addUserSubscribed(sessionSubscribeEvent.getUser(), subscribedChannel);
    }

    @EventListener
    public void handleUnSubscribeEvent(SessionUnsubscribeEvent unsubscribeEvent) {
        String simpSessionId = (String) unsubscribeEvent.getMessage().getHeaders().get("simpSessionId");
        String unSubscribedChannel = simpSessionIdToSubscriptionId.get(simpSessionId);
        onlineOfflineService.removeUserSubscribed(unsubscribeEvent.getUser(), unSubscribedChannel);
    }

    @EventListener
    public void handleConnectedEvent(SessionConnectedEvent sessionConnectedEvent) {
        onlineOfflineService.addOnlineUser(sessionConnectedEvent.getUser());
    }
}
