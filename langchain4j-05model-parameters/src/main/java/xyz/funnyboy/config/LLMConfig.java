package xyz.funnyboy.config;

import java.time.Duration;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import xyz.funnyboy.listener.TestChatModelListener;

@Configuration
public class LLMConfig
{

    /**
     * 聊天模型 Qwen
     *
     * @return {@link ChatModel }
     * @see {@linktourl <a href=
     *      "https://docs.langchain4j.dev/tutorials/model-parameters">https://docs.langchain4j.dev/tutorials/model-parameters</a>}
     */
    @Bean(name = "qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").logRequests(true).logResponses(true)
                .listeners(List.of(new TestChatModelListener())).maxRetries(2).timeout(Duration.ofSeconds(2)).build();
    }
}
