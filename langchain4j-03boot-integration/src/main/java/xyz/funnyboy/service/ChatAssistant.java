package xyz.funnyboy.service;

import dev.langchain4j.service.spring.AiService;
// import dev.langchain4j.service.spring.AiServiceWiringMode;

// @AiService(wiringMode = AiServiceWiringMode.EXPLICIT, chatModel =
// "qwen-plus", streamingChatModel = "deepseek")
@AiService
public interface ChatAssistant
{
    String chat(String prompt);
}
