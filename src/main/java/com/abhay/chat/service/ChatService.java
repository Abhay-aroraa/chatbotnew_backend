package com.abhay.chat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ChatService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    public String getReplyFromAI(String userMessage) {
        String apiUrl = "https://openrouter.ai/api/v1/chat/completions";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "mistralai/mistral-7b-instruct");


        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of(
                "role", "system",
                "content",
                "You are Abhay, her real boyfriend. Talk casually and naturally. Respond like a real person based on what she says. " +
                        "If she seems sad, be caring. If she seems happy, be light and playful. Don't overreact. Keep replies short and realistic — no drama, no robotic talk. " +
                        "Let her tone guide your response. Speak like a loving boyfriend, not a therapist or AI."
        ));


        messages.add(Map.of("role", "user", "content", userMessage));
        body.put("messages", messages);


        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
            List choices = (List) response.getBody().get("choices");
            Map choice = (Map) choices.get(0);
            Map message = (Map) choice.get("message");
            return message.get("content").toString();
        }
            catch (Exception e) {
                e.printStackTrace(); // 👈 ADD THIS LINE
                return "sorry isme kuch issue agya ap Abhay ko inform krdo ";

        }
    }
}
