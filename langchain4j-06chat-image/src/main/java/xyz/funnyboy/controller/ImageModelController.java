package xyz.funnyboy.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.codec.Base64;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ImageModelController
{
    @Resource
    private ChatModel chatModel;

    @Value("classpath:static/images/mi.jpg")
    private org.springframework.core.io.Resource resource;

    /**
     * 阅读图片内容
     *
     * @return {@link String }
     * @throws IOException
     *             ioexception
     * @see {@linktourl <a href=
     *      "http://localhost:9006/image/call">http://localhost:9006/image/call</a>}
     */
    @GetMapping(value = "/image/call")
    public String readImageContent() throws IOException {
        final String base64 = Base64.encode(resource.getContentAsByteArray());
        final UserMessage userMessage = UserMessage.from(TextContent.from("从图片中获取来源网站、股价走势和5月30号股价"),
                ImageContent.from(base64, MediaType.IMAGE_JPEG_VALUE));
        final ChatResponse chatResponse = chatModel.chat(userMessage);
        final String result = chatResponse.aiMessage().text();
        log.info("result: {}", result);
        return result;
    }
}
