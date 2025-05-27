package com.ivy.ai.robot.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Version 1.00
 * @Author: Ivy
 * @Date: 2025/5/27 21:28
 * @Description: AI 机器人
 */

@RestController
@RequestMapping("/ai")
public class DeepSeekChatController {

    @Resource
    private DeepSeekChatModel chatModel;

    /**
    * @Description: 普通对话模式
    * @Param: [message]
    * @return: java.lang.String
    * @Author: Ivy
    * @Date: 2025/5/27
    */
    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "你是谁？") String message) {
        return chatModel.call(message);
    }
    
    /**
    * @Description: 流式返回对话
    * @Param: [message]
    * @return: reactor.core.publisher.Flux<java.lang.String>
    * @Author: Ivy
    * @Date: 2025/5/27
    */
    @GetMapping(value = "/generateStream", produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "你是谁？") String message) {

        // 构建提示词
        Prompt prompt = new Prompt(message);

        return chatModel.stream(prompt)
                .mapNotNull(chatResponse -> chatResponse.getResult().getOutput().getText());
    }
}
