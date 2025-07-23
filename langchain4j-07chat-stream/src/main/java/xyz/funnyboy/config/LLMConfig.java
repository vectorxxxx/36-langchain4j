package xyz.funnyboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import xyz.funnyboy.service.ChatAssistant;

@Configuration
public class LLMConfig
{

    @Bean(name = "qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").build();
    }

    /**
     *
     *
     * @return {@link StreamingChatModel }
     * @see {@linktourl <a href=
     *      "https://docs.langchain4j.dev/tutorials/response-streaming">https://docs.langchain4j.dev/tutorials/response-streaming</a>}
     */
    @Bean
    public StreamingChatModel streamingChatModelQwen() {
        return OpenAiStreamingChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").build();
    }

    @Bean
    public ChatAssistant chatAssistant(StreamingChatModel streamingChatModel) {
        return AiServices.create(ChatAssistant.class, streamingChatModel);
    }
}
