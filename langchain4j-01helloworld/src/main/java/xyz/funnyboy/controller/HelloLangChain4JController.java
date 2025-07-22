package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloLangChain4JController
{

    @Resource
    private ChatModel chatModel;

    /**
     * 你好
     *
     * @param question
     *            问题
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9001/langchain4j/hello?question=如何学习java">http://localhost:9001/langchain4j/hello?question=如何学习java</a>}
     */
    @GetMapping(value = "/langchain4j/hello")
    public String hello(@RequestParam(value = "question", defaultValue = "你是谁")
    String question) {
        log.info("question: {}", question);
        final String result = chatModel.chat(question);
        log.info("result: {}", result);
        return result;
    }
}
