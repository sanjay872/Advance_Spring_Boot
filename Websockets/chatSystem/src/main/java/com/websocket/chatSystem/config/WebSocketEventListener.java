package com.websocket.chatSystem.config;

import com.websocket.chatSystem.dto.ChatMessage;
import com.websocket.chatSystem.dto.MessageType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@AllArgsConstructor
@Component
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebSocketDisConnect(SessionDisconnectEvent sessionDisconnectEvent){
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage()); // get the disconnected session
        String username=(String)headerAccessor.getSessionAttributes().get("username"); // get username from the session
        if (username!=null){ // if username exist
            log.info("Username : {}",username);
            var chatMessage= ChatMessage.builder() // create chat message that user left
                    .sender(username)
                    .type(MessageType.LEAVE)
                    .build();
            sendingOperations.convertAndSend("/topic/public",chatMessage); // sent the message to the group
        }
    }

}
