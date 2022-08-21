package com.example.restservice;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WSController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/publish")
    public void PublishMessage(WSMessage message) {
        simpMessageSendingOperations.convertAndSend("/subcribe/" + message.getChannel(), message);
    }
}
