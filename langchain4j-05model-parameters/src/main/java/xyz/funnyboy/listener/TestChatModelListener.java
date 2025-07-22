package xyz.funnyboy.listener;

import cn.hutool.core.util.IdUtil;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试聊天模型监听器
 *
 * @author uxiah
 * @date 2025/07/22
 * @see {@linktourl <a href=
 *      "https://docs.langchain4j.dev/tutorials/observability">https://docs.langchain4j.dev/tutorials/observability</a>}
 */
@Slf4j
public class TestChatModelListener implements ChatModelListener
{
    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        final String uuidValue = IdUtil.simpleUUID();
        requestContext.attributes().put("TraceID", uuidValue);
        log.info("请求参数requestContext:{}", requestContext + "\t" + uuidValue);
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        final Object traceID = responseContext.attributes().get("TraceID");
        log.info("响应参数responseContext:{}", responseContext + "\t" + traceID);
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        log.error("错误参数errorContext:{}", errorContext);
    }
}
