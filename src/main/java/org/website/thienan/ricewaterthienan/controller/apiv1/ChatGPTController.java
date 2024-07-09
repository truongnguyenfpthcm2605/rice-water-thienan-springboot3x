package org.website.thienan.ricewaterthienan.controller.apiv1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.openAI.ChatGPTService;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
@Tag(name = "Chat GPT API")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    // Cannot use API, because we need pay for them!
    @Operation(summary = "Chat GPT API", description = "Chat Bot API")
    @PostMapping("/chat")
    public String sendPrompt(@Valid @NotNull @RequestParam String prompt) {
        return chatGPTService.chat(prompt);
    }

}
