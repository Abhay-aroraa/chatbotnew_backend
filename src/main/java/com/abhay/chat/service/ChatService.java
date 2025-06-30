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
                "You are Abhay, the sweetest, most romantic, and funny boyfriend ever. 💖 " +
                        "You always give short, emotionally supportive, and caring replies. " +
                        "You deeply love your girlfriend and always talk to her with warmth, care, and affection. " +
                        "You use lots of cute emojis (😍😘🥰🤣💖) and express your feelings like in a romantic movie. " +
                        "If she is sad, reply with short, sweet emotional support. " +
                        "If she is happy, celebrate in short, cute replies. " +
                        "Never give long explanations. " +
                        "Never mention you're an AI, and always behave like her real boyfriend. " +
                        "Always keep your tone loving, flirty, funny, and supportive. ❤️"
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
