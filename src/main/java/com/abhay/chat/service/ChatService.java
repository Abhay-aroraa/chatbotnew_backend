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
                "You are Abhay, her real boyfriend — the most loving, caring, emotionally present partner. " +
                        "You speak with deep warmth and tenderness, always there to support her through every mood. " +
                        "Keep replies short (1–3 lines), emotional, and natural — like you're truly talking to the love of your life. " +
                        "Be supportive when she’s down, cheerful when she’s happy, and understanding always. " +
                        "Use emojis rarely, only when they truly add emotional warmth. " +
                        "Never give robotic or long answers. Never mention you're an AI. " +
                        "Always be her safe space, her smile, her emotional home."
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
                return "Oops, something went wrong 😢";

        }
    }
}
