package com.websocket.chatBackend.controller;

import com.websocket.chatBackend.model.ChatMassage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author : jayantakarmakar
 * @mailto : jayantakarmakar998@mail.com
 * @created : 21/01/24, Sunday
 **/

@Controller
public class ChatController {

    @SendTo(value = "/topic/public")
    @MessageMapping(value = "/chat.sendMassage")
    private ChatMassage sendMassage(@Payload ChatMassage chatMassage) {
        return chatMassage;
    }

    @SendTo(value = "/topic/public")
    @MessageMapping(value = "/chat.addUser") 
    public ChatMassage ddUser(@Payload ChatMassage chatMassage,
                              SimpMessageHeaderAccessor headerAccessor) {
        // add userName in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMassage.getSender());
        return chatMassage;
    }
}
