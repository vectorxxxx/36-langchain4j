package xyz.funnyboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

@Configuration
public class LLMConfig
{
    @Bean
    public ChatModel ImageModel() {
        return OpenAiChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-vl-max")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").build();
    }

    /**
     * 
     *
     * @return {@link WanxImageModel }
     * @see {@linktourl <a href=
     *      "https://help.aliyun.com/zh/model-studio/text-to-image">https://help.aliyun.com/zh/model-studio/text-to-image</a>}
     */
    @Bean
    public WanxImageModel WanxImageModel() {
        return WanxImageModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("wanx2.1-t2i-turbo").build();
    }
}
