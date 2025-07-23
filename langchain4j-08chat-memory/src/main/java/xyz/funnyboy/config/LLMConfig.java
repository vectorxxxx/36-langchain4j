package xyz.funnyboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.TokenCountEstimator;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.service.AiServices;
import xyz.funnyboy.service.ChatAssistant;
import xyz.funnyboy.service.ChatMemoryAssistant;

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

    @Bean(name = "qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-long")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").build();
    }

    @Bean(name = "chat")
    public ChatAssistant chatAssistant(ChatModel chatModel) {
        return AiServices.create(ChatAssistant.class, chatModel);
    }

    @Bean(name = "chatMessageWindowChatMemory")
    public ChatMemoryAssistant chatMessageWindowChatMemory(ChatModel chatModel) {
        return AiServices.builder(ChatMemoryAssistant.class).chatModel(chatModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(100)).build();
    }

    @Bean(name = "chatTokenWindowChatMemory")
    public ChatMemoryAssistant chatTokenWindowChatMemory(ChatModel chatModel) {
        final TokenCountEstimator tokenCountEstimator = new OpenAiTokenCountEstimator("gpt-4");
        return AiServices.builder(ChatMemoryAssistant.class).chatModel(chatModel)
                .chatMemoryProvider(memoryId -> TokenWindowChatMemory.withMaxTokens(1000, tokenCountEstimator)).build();
    }
}
