package com.ivy.ai.robot.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Ivy
 * @Date: 2025/5/28 20:36
 * @Version 1.00
 * @Description
 */
@Configuration
public class ChatMemoryConfig {
    
    /**
    * @Description:  记忆存储
    * @Author: Ivy
    */
    @Resource
    private ChatMemoryRepository chatMemoryRepository;
    
    /**
    * @Description: 初始化一个 ChatMemory 实例，并注入到 Spring 容器中
    * @Author: Ivy
    * @Date: 2025/5/28
    */
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                // 最大消息数
                .maxMessages(50)
                // 记忆存储
                .chatMemoryRepository(chatMemoryRepository)
                .build();
    }
}
