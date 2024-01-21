package com.websocket.chatBackend.model;

import lombok.*;

/**
 * @author : jayantakarmakar
 * @mailto : jayantakarmakar998@mail.com
 * @created : 21/01/24, Sunday
 **/
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMassage {
    private String content;
    private String sender; 
    private MassageType massageType;
}
