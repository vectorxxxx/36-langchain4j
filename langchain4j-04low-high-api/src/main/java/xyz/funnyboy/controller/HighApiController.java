package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import xyz.funnyboy.service.ChatAssistant;

@RestController
@Slf4j
public class HighApiController
{
    @Resource
    private ChatAssistant chatAssistant;

    /**
     * 高阶 API
     *
     * @param prompt
     *            提示
     * @return {@link String } highapi
     * @see {@linktourl <a href=
     *      "http://localhost:9004/highapi/highapi">http://localhost:9004/highapi/highapi</a>}
     */
    @GetMapping(value = "/highapi/highapi")
    public String highApi(@RequestParam(value = "prompt", defaultValue = "你是谁")
    String prompt) {
        return chatAssistant.chat(prompt);
    }
}
