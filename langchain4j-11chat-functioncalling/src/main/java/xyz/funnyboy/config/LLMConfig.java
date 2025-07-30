package xyz.funnyboy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import xyz.funnyboy.service.FunctionAssistant;
import xyz.funnyboy.service.InvoiceHandler;

@Configuration
public class LLMConfig
{
    @Bean
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder().apiKey(System.getenv("Alibaba-APIKey")).modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").build();
    }

    /**
     * 功能助手
     *
     * @param chatModelQwen
     *            聊天模型 Qwen
     * @return {@link FunctionAssistant }
     * @see {@linktourl <a href=
     *      "https://docs.langchain4j.dev/tutorials/tools#low-level-tool-api">https://docs.langchain4j.dev/tutorials/tools#low-level-tool-api</a>}
     */
    /*
     * @Bean public FunctionAssistant functionAssistant(ChatModel chatModelQwen) {
     * final JsonObjectSchema parameters =
     * JsonObjectSchema.builder().addStringProperty("companyName", "公司名称")
     * .addStringProperty("dutyNumber", "税号序列").addStringProperty("amount",
     * "开票金额，保留两位有效数字").build();
     * 
     * final ToolSpecification toolSpecification =
     * ToolSpecification.builder().name("开具发票助手")
     * .description("根据用户提交的开票信息，开具发票").parameters(parameters).build();
     * 
     * final ToolExecutor toolExecutor = (toolExecutionRequest, memoryId) -> {
     * System.out.println(toolExecutionRequest.id());
     * System.out.println(toolExecutionRequest.name()); final String arguments =
     * toolExecutionRequest.arguments(); System.out.println("arguments1****》 " +
     * arguments); return "开票成功"; };
     * 
     * return AiServices.builder(FunctionAssistant.class).chatModel(chatModelQwen)
     * .tools(Map.of(toolSpecification, toolExecutor)).build(); }
     */

    /**
     * 功能助手
     *
     * @param chatModelQwen
     *            聊天模型 Qwen
     * @return {@link FunctionAssistant }
     * @see {@linktourl <a href=
     *      "https://docs.langchain4j.dev/tutorials/tools/#high-level-tool-api">https://docs.langchain4j.dev/tutorials/tools/#high-level-tool-api</a>}
     */
    @Bean
    public FunctionAssistant functionAssistant(ChatModel chatModelQwen, InvoiceHandler invoiceHandler) {
        return AiServices.builder(FunctionAssistant.class).chatModel(chatModelQwen).tools(invoiceHandler).build();
    }
}
