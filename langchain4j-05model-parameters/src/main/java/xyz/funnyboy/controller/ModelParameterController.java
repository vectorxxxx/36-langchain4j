package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ModelParameterController
{
    @Resource
    private ChatModel chatModelQwen;

    /**
     * 配置
     *
     * @param prompt
     *            提示
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9005/modelparam/config">http://localhost:9005/modelparam/config</a>}
     */
    @GetMapping(value = "/modelparam/config")
    public String config(@RequestParam(value = "prompt", defaultValue = "你是谁")
    String prompt) {
        String result = chatModelQwen.chat(prompt);

        System.out.println("通过langchain4j调用模型返回结果：" + result);

        return result;
    }
}
