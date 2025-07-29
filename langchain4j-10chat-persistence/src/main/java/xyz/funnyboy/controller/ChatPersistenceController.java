package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import xyz.funnyboy.service.ChatPersistenceAssistant;

@RestController
@Slf4j
public class ChatPersistenceController
{
    @Resource
    private ChatPersistenceAssistant chatPersistenceAssistant;

    /**
     * 聊天
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9010/chatpersistence/redis">http://localhost:9010/chatpersistence/redis</a>}
     */
    @GetMapping(value = "/chatpersistence/redis")
    public String testChatPersistence() {
        chatPersistenceAssistant.chat(1L, "你好！我的名字是redis");
        chatPersistenceAssistant.chat(2L, "你好！我的名字是nacos");

        String chat = chatPersistenceAssistant.chat(1L, "我的名字是什么");
        System.out.println(chat);

        chat = chatPersistenceAssistant.chat(2L, "我的名字是什么");
        System.out.println(chat);

        return "testChatPersistence success : " + DateUtil.now();
    }

}
