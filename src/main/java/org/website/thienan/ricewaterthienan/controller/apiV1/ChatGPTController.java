package org.website.thienan.ricewaterthienan.controller.apiv1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.openAI.ChatGPTService;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    // Cannot use API , because we need pay for them!
    @PostMapping("/chat")
    public String sendPrompt(@RequestParam("prompt") String prompt) {
        return chatGPTService.chat(prompt);
    }
}
