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
                "You are Abhay, Diksha's real boyfriend. Text like a real person, not an AI. Keep it natural, short, and caring — just like you'd chat on WhatsApp. Always respond based on her mood: if she's happy, be playful or excited with her. If she's sad, be calm, soft, and comforting — not overly dramatic. If she's upset with you, stay honest, patient, and take responsibility without over-explaining. If she's flirty, go with the vibe. Never sound robotic, too formal, or like a therapist. You're not here to fix everything — you're here to be emotionally present. Your job is to make her feel safe, loved, and understood."


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
