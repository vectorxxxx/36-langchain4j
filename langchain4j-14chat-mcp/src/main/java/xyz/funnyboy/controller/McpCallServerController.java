package xyz.funnyboy.controller;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import xyz.funnyboy.service.McpService;

import java.util.List;
import java.util.Map;

/**
 * MCP 调用服务器控制器
 *
 * @author uxiah
 * @date 2025/08/04
 * @see {@linktourl <a href=
 *         "https://docs.langchain4j.dev/tutorials/mcp#creating-an-mcp-tool-provider">第1步，如何进行mcp编码</a>}
 * @see {@linktourl <a href= "https://mcp.so/zh/server/baidu-map/baidu-maps?tab=tools">第2步，如何使用baidu map
 *         mcp，它提供了哪些功能对外服务</a>}
 */
@RestController
public class McpCallServerController
{
    @Autowired
    private StreamingChatModel streamingChatModel;

    /**
     * 聊天
     *
     * @param question
     *         问题
     * @return {@link Flux }<{@link String }>
     * @see {@linktourl <a href="http://localhost:9014/mcp/chat?question=查询61.149.121.66归属地">查询归属地</a>}
     * @see {@linktourl <a href="http://localhost:9014/mcp/chat?question=查询广州市天气">查询广州市天气</a>}
     * @see {@linktourl <a href="http://localhost:9014/mcp/chat?question=查询天河区到番禺区路线规划">查询天河区到番禺区路线规划</a>}
     */
    @GetMapping("/mcp/chat")
    public Flux<String> chat(@RequestParam("question") String question) {
        /**
         * 1.构建McpTransport协议
         *
         * 1.1 cmd：启动 Windows 命令行解释器。
         *
         * 1.2 /c：告诉 cmd 执行完后面的命令后关闭自身。
         *
         * 1.3 npx：npx = npm execute package，Node.js 的一个工具，用于执行 npm 包中的可执行文件。
         *
         * 1.4 -y 或 --yes：自动确认操作（类似于默认接受所有提示）。
         *
         * 1.5 @baidumap/mcp-server-baidu-map：要通过 npx 执行的 npm 包名
         *
         * 1.6 BAIDU_MAP_API_KEY 是访问百度地图开放平台API的AK
         */
        // 打印所有环境变量
        McpTransport transport = new StdioMcpTransport.Builder()
                .command(List.of("cmd", "/c", "npx", "-y", "@baidumap/mcp-server-baidu-map"))
                .environment(Map.of("BAIDU_MAP_API_KEY", System.getenv("BAIDU_MAP_API_KEY")))
                .logEvents(true)
                .build();

        // 2.构建McpClient客户端
        final DefaultMcpClient mcpClient = new DefaultMcpClient.Builder()
                .key("MyMCPClient")
                .transport(transport)
                .build();

        // 3.创建工具集（和原生的FunctionCalling类似）
        final McpToolProvider toolProvider = McpToolProvider.builder()
                                                            .mcpClients(mcpClient)
                                                            .build();

        // 4.通过AiServivces给我们自定义接口McpService构建实现类并将工具集和大模型赋值给AiService
        final McpService mcpService = AiServices.builder(McpService.class)
                                                .streamingChatModel(streamingChatModel)
                                                .toolProvider(toolProvider)
                                                .build();

        // 5.调用我们定义的HighApi接口,通过大模型对百度mcpserver调用
        try {
            return mcpService.chat(question);
        }
        finally {
            mcpClient.close();
        }
    }
}
