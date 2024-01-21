package com.websocket.chatBackend.config;

import com.websocket.chatBackend.model.ChatMessage;
import com.websocket.chatBackend.model.MassageType;
import lombok.RequiredArgsConstructor;
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
        String userName = headerAccessor.getSessionAttributes().get("username").toString();
        if (userName != null) {
            log.info("User Disconnected : {}", userName);
            var chatMassage = ChatMessage.builder()
                    .sender(userName)
                    .massageType(MassageType.LEAVE)
                    .build();
            messageTemplate.convertAndSend("/topic/public", chatMassage);
        }
    }
}
