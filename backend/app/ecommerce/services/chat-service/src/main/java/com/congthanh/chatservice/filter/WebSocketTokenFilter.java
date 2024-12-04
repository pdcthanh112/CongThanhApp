package com.congthanh.chatservice.filter;

import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketTokenFilter  implements ChannelInterceptor {

    private final JWTUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public WebSocketTokenFilter(JWTUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT == accessor.getCommand()) {

            String jwt = jwtUtils.parseJwt(accessor);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                accessor.setUser(authentication);
            }
        }
        return message;
    }

}
