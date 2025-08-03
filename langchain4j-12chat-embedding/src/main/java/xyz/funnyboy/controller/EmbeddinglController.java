package xyz.funnyboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.MetadataFilterBuilder;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import xyz.funnyboy.config.LLMConfig;

/**
 * EmbeddinglController
 *
 * @author uxiah
 * @date 2025/08/03
 * @see {@linktourl <a href=
 *      "https://github.com/langchain4j/langchain4j-examples/blob/main/qdrant-example/src/main/java/QdrantEmbeddingStoreExample.java">https://github.com/langchain4j/langchain4j-examples/blob/main/qdrant-example/src/main/java/QdrantEmbeddingStoreExample.java</a>}
 */
@RestController
@Slf4j
public class EmbeddinglController
{
    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private QdrantClient qdrantClient;

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    /**
     * 文本向量化测试，看看形成向量后的文本
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9012/embedding/embed">http://localhost:9012/embedding/embed</a>}
     */
    @GetMapping(value = "/embedding/embed")
    public String embed() {
        String prompt = """
                   咏鸡
                鸡鸣破晓光，
                红冠映朝阳。
                金羽披霞彩，
                昂首步高岗。
                """;
        final Response<Embedding> response = embeddingModel.embed(prompt);
        System.out.println(response);
        return response.content().toString();
    }

    /**
     * 新建向量数据库实例和创建索引：test-qdrant <br/>
     * 类似mysql create database test-qdrant
     *
     * @see {@linktourl <a href=
     *      "http://localhost:9012/embedding/createCollection">http://localhost:9012/embedding/createCollection</a>}
     */
    @GetMapping(value = "/embedding/createCollection")
    public void createCollection() {
        final Collections.VectorParams vectorParams = Collections.VectorParams.newBuilder()
                .setDistance(Collections.Distance.Cosine).setSize(1024).build();
        qdrantClient.createCollectionAsync(LLMConfig.COLLECTION_NAME, vectorParams);
    }

    /**
     * 往向量数据库新增文本记录
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9012/embedding/add">http://localhost:9012/embedding/add</a>}
     */
    @GetMapping(value = "/embedding/add")
    public String add() {
        String prompt = """
                咏鸡
                鸡鸣破晓光，
                红冠映朝阳。
                金羽披霞彩，
                昂首步高岗。
                """;
        final TextSegment segment = TextSegment.from(prompt);
        segment.metadata().put("author", "VectorX");
        final Embedding embedding = embeddingModel.embed(segment).content();
        final String result = embeddingStore.add(embedding, segment);
        System.out.println(result);
        return result;
    }

    /**
     * 查询1
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9012/embedding/query1">http://localhost:9012/embedding/query1</a>}
     */
    @GetMapping(value = "/embedding/query1")
    public String query1() {
        final Embedding embedding = embeddingModel.embed("咏鸡说的是什么").content();
        final EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder().queryEmbedding(embedding)
                .maxResults(1).build();
        final EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(searchRequest);

        final TextSegment textSegment = searchResult.matches().get(0).embedded();
        System.out.println(textSegment);
        return textSegment.text();
    }

    /**
     * 查询2
     *
     * @return {@link String }
     * @see {@linktourl <a href=
     *      "http://localhost:9012/embedding/query2">http://localhost:9012/embedding/qquery2uery1</a>}
     */
    @GetMapping(value = "/embedding/query2")
    public String query2() {
        final Embedding embedding = embeddingModel.embed("咏鸡").content();
        final EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder().queryEmbedding(embedding)
                .filter(MetadataFilterBuilder.metadataKey("author").isEqualTo("VectorX")).maxResults(1).build();
        final EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(searchRequest);

        final TextSegment textSegment = searchResult.matches().get(0).embedded();
        System.out.println(textSegment);
        return textSegment.text();
    }
}
