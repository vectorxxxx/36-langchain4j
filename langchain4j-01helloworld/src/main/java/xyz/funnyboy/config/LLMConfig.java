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

    protected static final String API_KEY = "Alibaba-APIKey";
    protected static final String MODEL_NAME = "qwen-plus";
    protected static final String BASE_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1";

    @Bean
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder().apiKey(System.getenv(API_KEY)).modelName(MODEL_NAME).baseUrl(BASE_URL).build();
    }
}
