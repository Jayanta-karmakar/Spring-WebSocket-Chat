package com.websocket.chatBackend.config;

import com.websocket.chatBackend.model.ChatMassage;
import com.websocket.chatBackend.model.MassageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * @author : jayantakarmakar
 * @mailto : jayantakarmakar998@mail.com
 * @created : 21/01/24, Sunday
 **/

//@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    @Autowired
    private final SimpMessageSendingOperations messageTemplate;
    public final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userName = headerAccessor.getSessionAttributes().get("userName").toString();
        if (userName != null) {
            log.info("User Disconnected : {}", userName);
            var chatMassage = ChatMassage.builder()
                    .sender(userName)
                    .massageType(MassageType.LEAVE)
                    .build();
            messageTemplate.convertAndSend("/topic/public", chatMassage);
        }
    }
}
