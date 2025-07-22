package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import xyz.funnyboy.service.ChatAssistant;

@RestController
@Slf4j
public class DeclarativeAIServiceController
{
    @Resource
    private ChatAssistant chatAssistantQwen;

    /**
     * 聊天
     *
     * @param prompt
     *            提示
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9003/lc4j/boot/declarative">http://localhost:9003/lc4j/boot/declarative</a>}
     */
    @GetMapping(value = "/lc4j/boot/declarative")
    public String declarative(@RequestParam(value = "prompt", defaultValue = "你是谁")
    String prompt) {
        return chatAssistantQwen.chat(prompt);
    }
}
