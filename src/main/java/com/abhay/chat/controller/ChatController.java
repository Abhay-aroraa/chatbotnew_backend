package com.abhay.chat.controller;

import com.abhay.chat.model.ChatRequest;
import com.abhay.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public String getChatResponse(@RequestBody ChatRequest request) {
        return chatService.getReplyFromAI(request.getMessage());
    }
}
