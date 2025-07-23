package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import xyz.funnyboy.service.ChatAssistant;
import xyz.funnyboy.service.ChatMemoryAssistant;

@RestController
@Slf4j
public class ChatMemoryController
{
    @Resource(name = "chat")
    private ChatAssistant chatAssistant;

    @Resource(name = "chatMessageWindowChatMemory")
    private ChatMemoryAssistant chatMessageWindowChatMemory;

    @Resource(name = "chatTokenWindowChatMemory")
    private ChatMemoryAssistant chatTokenWindowChatMemory;

    /**
     * 聊天
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9008/chatmemory/test1">http://localhost:9008/chatmemory/test1</a>}
     */
    @GetMapping(value = "/chatmemory/test1")
    public String chat() {
        String answer01 = chatAssistant.chat("你好，我的名字叫张三");
        System.out.println("answer01返回结果：" + answer01);

        String answer02 = chatAssistant.chat("我的名字是什么");
        System.out.println("answer02返回结果：" + answer02);

        return "success : " + DateUtil.now() + "<br> \n\n answer01: " + answer01 + "<br> \n\n answer02: " + answer02;
    }

    /**
     * 聊天
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9008/chatmemory/test2">http://localhost:9008/chatmemory/test2</a>}
     */
    @GetMapping(value = "/chatmemory/test2")
    public String chatMessageWindowChatMemory() {
        chatMessageWindowChatMemory.chatWithChatMemory(1L, "你好！我的名字是Java.");
        String answer01 = chatMessageWindowChatMemory.chatWithChatMemory(1L, "我的名字是什么");
        System.out.println("answer01返回结果：" + answer01);

        chatMessageWindowChatMemory.chatWithChatMemory(3L, "你好！我的名字是C++");
        String answer02 = chatMessageWindowChatMemory.chatWithChatMemory(3L, "我的名字是什么");
        System.out.println("answer02返回结果：" + answer02);

        return "chatMessageWindowChatMemory success : " + DateUtil.now() + "<br> \n\n answer01: " + answer01
                + "<br> \n\n answer02: " + answer02;
    }

    /**
     * 聊天
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9008/chatmemory/test3">http://localhost:9008/chatmemory/test3</a>}
     */
    @GetMapping(value = "/chatmemory/test3")
    public String chatTokenWindowChatMemory() {
        chatTokenWindowChatMemory.chatWithChatMemory(1L, "你好！我的名字是mysql");
        String answer01 = chatTokenWindowChatMemory.chatWithChatMemory(1L, "我的名字是什么");
        System.out.println("answer01返回结果：" + answer01);

        chatTokenWindowChatMemory.chatWithChatMemory(3L, "你好！我的名字是oracle");
        String answer02 = chatTokenWindowChatMemory.chatWithChatMemory(3L, "我的名字是什么");
        System.out.println("answer02返回结果：" + answer02);

        return "chatTokenWindowChatMemory success : " + DateUtil.now() + "<br> \n\n answer01: " + answer01
                + "<br> \n\n answer02: " + answer02;
    }
}
