package org.website.thienan.ricewaterthienan.controller.apiv1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.website.thienan.ricewaterthienan.controller.UrlApi;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
@Tag(name = "Chat GPT API")
public class ChatGPTController {

//    private final OpenAiChatModel chatClient;
//
//    @Autowired
//    public ChatOpenAIController(OpenAiChatModel chatClient) {
//        this.chatClient = chatClient;
//    }
//
//    @GetMapping("/")
//    public String hello(){
//        return "Hello World";
//    }
//
//    @GetMapping("/ai/generate")
//    public Map<String, Object> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        return Map.of("generation", chatClient.call(message));
//    }
//
//    @GetMapping("/ai/generateStream")
//    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        Prompt prompt = new Prompt(new UserMessage(message));
//        return chatClient.stream(prompt);
//    }
}
