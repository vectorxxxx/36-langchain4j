package xyz.funnyboy.service;

public interface ChatAssistant
{

    /**
     * 聊天
     *
     * @param message
     *            消息
     * @return {@link String }
     */
    String chat(String message);
}
