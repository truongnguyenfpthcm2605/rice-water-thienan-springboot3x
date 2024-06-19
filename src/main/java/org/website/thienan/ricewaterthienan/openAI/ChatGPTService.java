package org.website.thienan.ricewaterthienan.openAI;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@RequiredArgsConstructor
public class ChatGPTService {

    @Value("${chat.model}")
    private String model;

    @Value("${chat.api.url}")
    private String apiURL;


    private final RestTemplate template;


    public String chat(String prompt){
        ChatGPTRequest request=new ChatGPTRequest(model, prompt.isEmpty() ? "Hello" : prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        assert chatGptResponse != null;
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}
