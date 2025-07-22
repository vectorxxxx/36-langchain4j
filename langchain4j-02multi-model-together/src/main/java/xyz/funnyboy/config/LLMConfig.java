package xyz.funnyboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 * LLM配置
 *
 * @author uxiah
 * @date 2025/07/21
 * @see {@linktourl <a href=
 *      "https://docs.langchain4j.dev/get-started">https://docs.langchain4j.dev/get-started</a>}
 */
@Configuration
public class LLMConfig
{

    @Bean(name = "qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").build();
    }

    @Bean(name = "deepseek")
    public ChatModel chatModelDeepSeek() {
        return OpenAiChatModel.builder().apiKey(System.getenv("DeepSeek-APIKey")).modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com/v1").build();
    }
}
