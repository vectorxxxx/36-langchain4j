package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import xyz.funnyboy.service.ChatAssistant;

@RestController
@Slf4j
public class StreamingChatModelController
{
    @Resource
    private StreamingChatModel streamingChatModel;

    @Resource
    private ChatAssistant chatAssistant;

    /**
     * 聊天
     *
     * @param prompt
     *            提示
     * @return {@link Flux }<{@link String }>
     * @see {@linktourl <a href=
     *      "http://localhost:9007/chatstream/chat?prompt=天津有什么好吃的">http://localhost:9007/chatstream/chat?prompt=天津有什么好吃的</a>}
     */
    @GetMapping(value = "/chatstream/chat")
    public Flux<String> chat(@RequestParam("prompt")
    String prompt) {
        System.out.println("---come in chat");

        return Flux.create(emitter -> streamingChatModel.chat(prompt, new StreamingChatResponseHandler()
        {
            @Override
            public void onPartialResponse(String partialResponse) {
                emitter.next(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse chatResponse) {
                emitter.complete();
            }

            @Override
            public void onError(Throwable throwable) {
                emitter.error(throwable);
            }
        }));
    }

    /**
     * 聊天
     *
     * @param prompt
     *            提示
     * @return {@link Flux }<{@link String }>
     * @see {@linktourl <a href=
     *      "http://localhost:9007/chatstream/chat2?prompt=北京有什么好吃">http://localhost:9007/chatstream/chat2?prompt=北京有什么好吃</a>}
     */
    @GetMapping(value = "/chatstream/chat2")
    public void chat2(@RequestParam(value = "prompt", defaultValue = "北京有什么好吃")
    String prompt) {
        System.out.println("---come in chat2");
        streamingChatModel.chat(prompt, new StreamingChatResponseHandler()
        {
            @Override
            public void onPartialResponse(String partialResponse) {
                System.out.print(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                System.out.println("\n\n---response over: " + completeResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 聊天
     *
     * @param prompt
     *            提示
     * @return {@link Flux }<{@link String }>
     * @see {@linktourl <a href=
     *      "http://localhost:9007/chatstream/chat3?prompt=南京有什么好吃">http://localhost:9007/chatstream/chat3?prompt=南京有什么好吃</a>}
     */
    @GetMapping(value = "/chatstream/chat3")
    public Flux<String> chat3(@RequestParam(value = "prompt", defaultValue = "南京有什么好吃")
    String prompt) {
        System.out.println("---come in chat3");
        return chatAssistant.chatStream(prompt);
    }
}
