package com.ivy.ai.robot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Ivy
 * @Date: 2025/6/21 13:57
 * @Version 1.00
 * @Description AI 对话响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIResponse {

    // 流式响应内容
    private String v;
}
