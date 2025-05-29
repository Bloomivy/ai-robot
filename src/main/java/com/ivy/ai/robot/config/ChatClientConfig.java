package com.ivy.ai.robot.config;

import com.ivy.ai.robot.advisor.MyLoggerAdvisor;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: Ivy
 * @Date: 2025/5/28 20:03
 * @Version 1.00
 * @Description
 */
@Configuration
public class ChatClientConfig {

    @Resource
    private ChatMemory chatMemory;
    
    /**
    * @Description: 初始化ChatClient客户端
    * @Param: [deepSeekChatModel]
    * @return: org.springframework.ai.chat.client.ChatClient
    * @Author: Ivy
    * @Date: 2025/5/28
    */
    @Bean
    public ChatClient chatClient(DeepSeekChatModel deepSeekChatModel) {
        return ChatClient.builder(deepSeekChatModel)
                .defaultSystem("你是一名教育网站的客服人员，你需要扮演好客服的角色")
                // Spring AI 内置的日志记录功能，解决流式对话的日志记录
                .defaultAdvisors(new SimpleLoggerAdvisor(),
                        new MyLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }
}
