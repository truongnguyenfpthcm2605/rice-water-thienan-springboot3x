package org.website.thienan.ricewaterthienan.openAI;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiService openAiService;

    @PostMapping
    public String chat(@RequestBody ChatRequest chatRequest) {
        return openAiService.getChatResponse(chatRequest.getPrompt());
    }
}