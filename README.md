# Ivy AI Robot

基于Spring Boot和Spring AI框架的人工智能聊天机器人项目，集成了DeepSeek大模型，提供普通对话和流式对话能力。

## 项目介绍

本项目使用Spring Boot 3.4.5和Spring AI 1.0.0构建，通过REST API提供AI聊天服务。项目支持：
- 普通对话模式（同步返回完整结果）
- 流式对话模式（异步流式返回结果）

## 技术栈

- JDK 21
- Spring Boot 3.4.5
- Spring AI 1.0.0
- DeepSeek 大语言模型

## 项目结构

```
ivy-ai-robot-springboot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ivy/ai/robot/
│   │   │       ├── controller/
│   │   │       │   └── DeepSeekChatController.java  # AI聊天控制器
│   │   │       └── IvyAiRobotSpringbootApplication.java  # 应用启动类
│   │   └── resources/
│   │       └── application.yml  # 应用配置文件
├── .gitignore
├── pom.xml  # Maven依赖配置
└── README.md  # 项目说明文档
```

## 快速开始

### 前置条件

- JDK 21+
- Maven 3.6+
- DeepSeek API密钥（在官网申请）

### 配置

1. 修改`application.yml`文件，设置您的DeepSeek API密钥：

```yaml
spring:
  ai:
    deepseek:
      api-key: your-api-key-here  # 替换为您的DeepSeek API密钥
```

2. 可选：调整模型参数

```yaml
spring:
  ai:
    deepseek:
      chat:
        options:
          model: deepseek-chat  # 选择模型
          temperature: 0.8  # 调整温度参数
```

### 启动项目

```bash
mvn spring-boot:run
```

服务将在本地8080端口启动。

## API接口

### 普通对话接口

```
GET /ai/generate?message=你的问题
```

同步返回完整的AI回答结果。

### 流式对话接口

```
GET /ai/generateStream?message=你的问题
```

异步流式返回AI回答，适用于需要实时显示回答过程的场景。

#version 1.0.0
