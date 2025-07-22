package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LowApiController
{
    @Resource(name = "qwen")
    private ChatModel chatModelQwen;

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepSeek;

    /**
     *
     * @param prompt
     *            提示
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9004/lowapi/api01">http://localhost:9004/lowapi/api01</a>}
     */
    @GetMapping(value = "/lowapi/api01")
    public String api01(@RequestParam(value = "prompt", defaultValue = "你是谁")
    String prompt) {
        return chatModelQwen.chat(prompt);
    }

    /**
     *
     * @param prompt
     *            提示
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9004/lowapi/api02">http://localhost:9004/lowapi/api02</a>}
     */
    @GetMapping(value = "/lowapi/api02")
    public String api02(@RequestParam(value = "prompt", defaultValue = "你是谁")
    String prompt) {
        final ChatResponse chatResponse = chatModelDeepSeek.chat(UserMessage.from(prompt));
        final String result = chatResponse.aiMessage().text();
        log.info("result: {}", result);

        final TokenUsage tokenUsage = chatResponse.tokenUsage();
        log.info("tokenUsage: {}", tokenUsage);

        return result + "\t\n" + tokenUsage;
    }
}
