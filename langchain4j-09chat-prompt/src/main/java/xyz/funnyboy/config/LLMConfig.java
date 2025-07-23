package xyz.funnyboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import xyz.funnyboy.service.LawAssistant;

/**
 * LLM配置
 * 
 * @author uxiah
 * @date 2025/07/23
 * @see {@linktourl <a href=
 *      "https://docs.langchain4j.dev/tutorials/chat-memory">https://docs.langchain4j.dev/tutorials/chat-memory</a>}
 */
@Configuration
public class LLMConfig
{

    @Bean
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-long")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").build();
    }

    @Bean
    public LawAssistant lawAssistant(ChatModel chatModel) {
        return AiServices.create(LawAssistant.class, chatModel);
    }
}
