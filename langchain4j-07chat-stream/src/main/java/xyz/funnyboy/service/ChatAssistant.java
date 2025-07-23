package xyz.funnyboy.service;

import reactor.core.publisher.Flux;

public interface ChatAssistant
{
    String chat(String prompt);

    Flux<String> chatStream(String prompt);
}
