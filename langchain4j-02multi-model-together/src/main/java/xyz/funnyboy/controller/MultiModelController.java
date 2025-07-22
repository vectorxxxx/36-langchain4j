package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MultiModelController
{

    @Resource(name = "qwen")
    private ChatModel chatModelQwen;

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepSeek;

    /***
     * @param prompt
     *            提示词
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9002/multimodel/qwen?prompt=如何学习java">http://localhost:9002/multimodel/qwen?prompt=如何学习java</a>}
     */
    @GetMapping(value = "/multimodel/qwen")
    public String qwenCall(@RequestParam(value = "prompt", defaultValue = "你是谁")
    String prompt) {
        log.info("prompt: {}", prompt);
        final String result = chatModelQwen.chat(prompt);
        log.info("result: {}", result);
        return result;
    }

    /**
     *
     * @param prompt
     *            提示词
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9002/multimodel/deepseek?prompt=如何学习java">http://localhost:9002/multimodel/deepseek?prompt=如何学习java</a>}
     */
    @GetMapping(value = "/multimodel/deepseek")
    public String deepseekCall(@RequestParam(value = "prompt", defaultValue = "你是谁")
    String prompt) {
        log.info("prompt: {}", prompt);
        final String result = chatModelDeepSeek.chat(prompt);
        log.info("result: {}", result);
        return result;
    }
}
