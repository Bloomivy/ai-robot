package com.ivy.ai.robot.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Author: Ivy
 * @Date: 2025/5/28 20:06
 * @Version 1.00
 * @Description
 */

@RestController
@RequestMapping("/v2/ai")
public class ChatClientController {

    @Resource
    private ChatClient chatClient;

    /**
    * @Description: 普通对话
    * @Param: [message]
    * @return: java.lang.String
    * @Author: Ivy
    * @Date: 2025/5/28
    */
    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "你是谁？") String message) {

        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
    
    /**
    * @Description: 流式对话功能
    * @Param: [message]
    * @return: reactor.core.publisher.Flux<java.lang.String>
    * @Author: Ivy
    * @Date: 2025/5/28
    */
    @GetMapping(value = "/generateStream", produces = "text/html;charset=utf-8")
    public Flux<String> generateString(@RequestParam(value = "message", defaultValue = "你是谁？") String message, @RequestParam(value = "chatId") String chatId) {

        return chatClient.prompt()
                .user(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }
}
