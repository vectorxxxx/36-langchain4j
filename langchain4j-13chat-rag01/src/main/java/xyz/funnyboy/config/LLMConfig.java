package xyz.funnyboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import xyz.funnyboy.service.ChatAssistant;

@Configuration
public class LLMConfig
{
    protected static final String API_KEY = "Alibaba-APIKey";
    protected static final String MODEL_NAME = "qwen-plus";
    protected static final String BASE_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1";

    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder().apiKey(System.getenv(API_KEY)).modelName(MODEL_NAME).baseUrl(BASE_URL).build();
    }

    /**
     * 需要预处理文档并将其存储在专门的嵌入存储（也称为矢量数据库）中。<br/>
     * 当用户提出问题时，这对于快速找到相关信息是必要的。 <br/>
     * 我们可以使用我们支持的 15 多个嵌入存储中的任何一个，但为了简单起见，我们将使用内存中的嵌入存储：
     *
     * @return {@link InMemoryEmbeddingStore }<{@link TextSegment }>
     * @see {@linktourl <a href=
     *      "https://docs.langchain4j.dev/integrations/embedding-stores/in-memory">https://docs.langchain4j.dev/integrations/embedding-stores/in-memory</a>}
     */
    @Bean
    public InMemoryEmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    public ChatAssistant chatAssistant(ChatModel chatModel, EmbeddingStore<TextSegment> embeddingStore) {
        return AiServices.builder(ChatAssistant.class).chatModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(50))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore)).build();
    }
}
