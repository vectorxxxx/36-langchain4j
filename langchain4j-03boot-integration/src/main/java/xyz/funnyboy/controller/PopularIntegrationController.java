package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PopularIntegrationController
{
    @Resource
    private ChatModel chatModel;

    /**
     * 聊天
     *
     * @param prompt
     *            提示
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9003/lc4j/boot/chat">http://localhost:9003/lc4j/boot/chat</a>}
     */
    @GetMapping(value = "/lc4j/boot/chat")
    public String chat(@RequestParam(value = "prompt", defaultValue = "你是谁")
    String prompt) {
        return chatModel.chat(prompt);
    }
}
