package io.github.jamielu.deepseeknative.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author jamieLu
 * @create 2025-02-17
 */
@RestController
@RequestMapping("/ai")
public class AiChatController {
    private final ChatClient chatClient;
    @Autowired
    public AiChatController(ChatClient.Builder chatModel) {
        this.chatClient = chatModel.build();
    }
    @GetMapping("/stream/chat")
    public ResponseEntity<Flux<String>> streamChat(@RequestParam("message") String message) {
        try {
            Flux<String> response = chatClient.prompt(message).stream().content();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt(message).call().content();
    }
}
