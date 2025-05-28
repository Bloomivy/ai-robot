package com.ivy.ai.robot.controller;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: Ivy
 * @Date: 2025/5/27 21:44
 * @Version 1.00
 * @Description: DeepSeek 聊天
 */
@RestController
@RequestMapping("/v1/ai")
public class DeepSeekR1ChatController {

    @Resource
    private DeepSeekChatModel chatModel;

    @GetMapping(value = "/generateStream", produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "你是谁？") String message) {

        // 构建提示词
        Prompt prompt = new Prompt(message);

        // 原子类标记是否需要分割线 每个请求独立
        AtomicBoolean needSeparator = new AtomicBoolean(true);

        return chatModel.stream(prompt)
                .mapNotNull(chatResponse -> {
                    // 获取响应内容
                    DeepSeekAssistantMessage deepSeekAssistantMessage = (DeepSeekAssistantMessage) chatResponse.getResult().getOutput();
                    // 推理内容
                    String reasoningContent = deepSeekAssistantMessage.getReasoningContent();
                    // 推理结束后的正式回答
                    String text = deepSeekAssistantMessage.getText();

                    // 判断是否为正式回答
                    boolean isContext = false;

                    // 推理有误，则响应推理内容 否则响应回答
                    String rowContent = null;
                    if (Objects.isNull(text)) {
                        rowContent = reasoningContent;
                    } else {
                        rowContent = text;
                        // 标记为正式回答
                        isContext = true;
                    }

                    // 处理换行
                    String process = StringUtils.isNotBlank(rowContent) ? rowContent.replace("\n", "<br>") : rowContent;

                    // 正式回答前添加分割线
                    if (isContext
                    && needSeparator.compareAndSet(true, false)) {
                        process = "<hr>" + process;
                    }
                    return process;
                });
    }

}
