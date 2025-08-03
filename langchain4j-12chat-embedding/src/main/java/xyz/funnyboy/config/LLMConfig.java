package xyz.funnyboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;

/**
 * LLM配置
 *
 * @author uxiah
 * @date 2025/08/03
 * @see {@linktourl <a href=
 *      "https://github.com/langchain4j/langchain4j-examples/blob/main/qdrant-example/src/main/java/QdrantEmbeddingStoreExample.java">https://github.com/langchain4j/langchain4j-examples/blob/main/qdrant-example/src/main/java/QdrantEmbeddingStoreExample.java</a>}
 */
@Configuration
public class LLMConfig
{
    protected static final String API_KEY = "Alibaba-APIKey";
    protected static final String MODEL_NAME = "text-embedding-v3";
    protected static final String BASE_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1";
    protected static final String HOST = "localhost";
    protected static final int PORT = 6334;
    public static final String COLLECTION_NAME = "test-qdrant";

    @Bean
    public EmbeddingModel embeddingModel() {
        return OpenAiEmbeddingModel.builder().apiKey(System.getenv(API_KEY)).modelName(MODEL_NAME).baseUrl(BASE_URL)
                .build();
    }

    /**
     * qdrant 客户端
     *
     * @return {@link QdrantClient }
     * @see {@linktourl <a href=
     *      "http://localhost:6333/dashboard">http://localhost:6333/dashboard</a>}
     */
    @Bean
    public QdrantClient qdrantClient() {
        return new QdrantClient(QdrantGrpcClient.newBuilder(HOST, PORT, false).build());
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return QdrantEmbeddingStore.builder().host(HOST).port(PORT).collectionName(COLLECTION_NAME).build();
    }
}
