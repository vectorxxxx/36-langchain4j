package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import xyz.funnyboy.service.FunctionAssistant;

@RestController
@Slf4j
public class ChatFunctionCallingController
{
    @Resource
    private FunctionAssistant functionAssistant;

    /**
     * 测试1
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9011/chatfunction/test1">http://localhost:9011/chatfunction/test1</a>}
     */
    @GetMapping(value = "/chatfunction/test1")
    public String test1() {
        String chat = functionAssistant.chat("开张发票,公司：尚硅谷教育科技有限公司 税号：atguigu533 金额：668.12");

        System.out.println(chat);

        return "success : " + DateUtil.now() + "\t" + chat;
    }
}
