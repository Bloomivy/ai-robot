package com.ivy.ai.robot.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Author: Ivy
 * @Date: 2025/5/30 20:12
 * @Version 1.00
 * @Description 对接智谱 AI
 */
@RestController
@RequestMapping("/v4/ai")
public class ZhiPuController {

    @Resource
    private ZhiPuAiChatModel zhiPuAiChatModel;
    
    /**
    * @Description: 普通对话
    * @Param: [message]
    * @return: java.lang.String
    * @Author: Ivy
    * @Date: 2025/5/30
    */
    @GetMapping("/generrate")
    public String generate(@RequestParam(value = "message", defaultValue = "你是谁？") String message) {
        return zhiPuAiChatModel.call(message);
    }
    
    /**
    * @Description:  流式对话
    * @Param: [message]
    * @return: reactor.core.publisher.Flux<java.lang.String>
    * @Author: Ivy
    * @Date: 2025/5/30
    */
    @GetMapping(value = "/generateStream", produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "你是谁？") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));

        return zhiPuAiChatModel.stream(prompt)
                .mapNotNull(chatResponse -> chatResponse.getResult().getOutput().getText());
    }
}
