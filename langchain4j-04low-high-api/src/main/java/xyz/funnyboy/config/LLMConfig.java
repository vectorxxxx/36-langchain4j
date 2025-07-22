package xyz.funnyboy.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import xyz.funnyboy.service.ChatAssistant;

@Configuration
public class LLMConfig
{

    /**
     * @see {@linktourl <a href=
     *      "https://bailian.console.aliyun.com/console?tab=api#/doc/?type=model&url=https%3A%2F%2Fhelp.aliyun.com%2Fdocument_detail%2F2712576.html">https://bailian.console.aliyun.com/console?tab=api#/doc/?type=model&url=https%3A%2F%2Fhelp.aliyun.com%2Fdocument_detail%2F2712576.html</a>}
     */
    @Bean(name = "qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").build();
    }

    /**
     * @see {@linktourl <a href=
     *      "https://api-docs.deepseek.com/zh-cn/">https://api-docs.deepseek.com/zh-cn/</a>}
     */
    @Bean(name = "deepseek")
    public ChatModel chatModelDeepSeek() {
        return OpenAiChatModel.builder().apiKey(System.getenv("DeepSeek-APIKey")).modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com/v1").build();
    }

    /**
     * 聊天助手
     *
     * @param chatModel
     *            聊天模式
     * @return {@link ChatAssistant }
     * @see {@linktourl <a href=
     *      "https://docs.langchain4j.dev/tutorials/ai-services#simplest-ai-service">https://docs.langchain4j.dev/tutorials/ai-services#simplest-ai-service</a>}
     */
    @Bean
    public ChatAssistant chatAssistant(@Qualifier("qwen")
    ChatModel chatModel) {
        return AiServices.create(ChatAssistant.class, chatModel);
    }
}
