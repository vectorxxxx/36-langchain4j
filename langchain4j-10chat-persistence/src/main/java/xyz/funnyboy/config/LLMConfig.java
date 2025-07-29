package xyz.funnyboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import xyz.funnyboy.service.ChatPersistenceAssistant;

@Configuration
public class LLMConfig
{
    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    @Bean
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").build();
    }

    /**
     * 聊天记忆助手
     *
     * @param chatModel
     *            聊天模式
     * @return {@link ChatPersistenceAssistant }
     * @see {@linktourl <a href=
     *      "https://docs.langchain4j.dev/tutorials/chat-memory#persistence">https://docs.langchain4j.dev/tutorials/chat-memory#persistence</a>}
     */
    @Bean
    public ChatPersistenceAssistant chatMemoryAssistant(ChatModel chatModel) {
        final ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder().id(memoryId)
                .maxMessages(1000).chatMemoryStore(redisChatMemoryStore).build();
        return AiServices.builder(ChatPersistenceAssistant.class).chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider).build();
    }
}
