package xyz.funnyboy.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import xyz.funnyboy.entities.LawPrompt;
import xyz.funnyboy.service.LawAssistant;

@RestController
@Slf4j
public class ChatPromptController
{
    @Resource
    private LawAssistant lawAssistant;

    @Resource
    private ChatModel chatModel;

    /**
     * 测试1
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9009/chatprompt/test1">http://localhost:9009/chatprompt/test1</a>}
     */
    @GetMapping(value = "/chatprompt/test1")
    public String test1() {
        String chat = lawAssistant.chat("什么是知识产权？", 2000);
        System.out.println(chat);

        String chat2 = lawAssistant.chat("什么是java？", 2000);
        System.out.println(chat2);

        String chat3 = lawAssistant.chat("介绍下西瓜和芒果", 2000);
        System.out.println(chat3);

        String chat4 = lawAssistant.chat("飞机发动机原理", 2000);
        System.out.println(chat4);

        return "success : " + DateUtil.now() + "<br> \n\n chat: " + chat + "<br> \n\n chat2: " + chat2
                + "<br> \n\n chat3: " + chat3 + "<br> \n\n chat4: " + chat4;
    }

    /**
     * 测试2
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9009/chatprompt/test2">http://localhost:9009/chatprompt/test2</a>}
     */
    @GetMapping(value = "/chatprompt/test2")
    public String test2() {
        LawPrompt prompt = new LawPrompt();
        prompt.setLegal("知识产权");
        prompt.setQuestion("TRIPS协议?");

        String chat = lawAssistant.chat(prompt);
        System.out.println(chat);
        return "success : " + DateUtil.now() + "<br> \n\n chat: " + chat;
    }

    /**
     * 测试3
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9009/chatprompt/test3">http://localhost:9009/chatprompt/test3</a>}
     */
    @GetMapping(value = "/chatprompt/test3")
    public String test3() {
        // String role = "外科医生";
        // String question = "牙疼";
        String role = "财务会计";
        String question = "人民币大写";

        final PromptTemplate promptTemplate = PromptTemplate.from("你是一个{{role}}助手，{{question}}怎么办");
        final Prompt prompt = promptTemplate.apply(Map.of("role", role, "question", question));
        final UserMessage userMessage = prompt.toUserMessage();
        final ChatResponse chatResponse = chatModel.chat(userMessage);

        System.out.println(chatResponse.aiMessage().text());
        return "success : " + DateUtil.now() + "<br> \n\n chat: " + chatResponse.aiMessage().text();
    }
}
