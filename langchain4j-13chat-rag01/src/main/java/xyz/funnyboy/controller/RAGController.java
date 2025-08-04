package xyz.funnyboy.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import xyz.funnyboy.service.ChatAssistant;

/**
 * ragcontroller
 *
 * @author uxiah
 * @date 2025/08/04
 * @see {@linktourl <a href=
 *      "https://docs.langchain4j.dev/tutorials/rag#easy-rag">https://docs.langchain4j.dev/tutorials/rag#easy-rag</a>}
 */
@RestController
@Slf4j
public class RAGController
{

    @Resource
    private ChatAssistant chatAssistant;

    @Resource
    private InMemoryEmbeddingStore<TextSegment> embeddingStore;

    /**
     * 测试添加
     *
     * @return {@link String }
     * @throws FileNotFoundException
     *             文件未找到异常
     * @see {@linktourl <a href=
     *      "http://localhost:9013/rag/add">http://localhost:9013/rag/add</a>}
     */
    @GetMapping(value = "/rag/add")
    public String testAdd() throws FileNotFoundException {
        final String filePath = "D:\\workspace-mine\\36-langchain4j\\langchain4j-13chat-rag01\\src\\main\\resources\\doc\\alibaba-java.pdf";

        // https://docs.langchain4j.dev/tutorials/rag#document-parser
        // final Document document = FileSystemDocumentLoader.loadDocument(filePath);
        final FileInputStream fileInputStream = new FileInputStream(filePath);
        Document document = new ApacheTikaDocumentParser().parse(fileInputStream);

        EmbeddingStoreIngestor.ingest(document, embeddingStore);

        String result = chatAssistant.chat("错误码00000和A0001分别是什么");
        log.info("result: {}", result);

        return result;
    }
}
