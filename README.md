## 2025-7-23

### feat

1. 会话记忆：@MemoryId、@UserMessage、chatMemoryProvider
   1) MessageWindowChatMemory
   2) TokenWindowChatMemory
2. 提示词工程：
   1) @SystemMessage➕@UserMessage➕@V
   2) @StructuredPrompt
   3) PromptTemplate➕Prompt

## 2025-7-22

### feat

1. 多模态：ImageContent、WanxImageModel、ImageSynthesis➕ImageSynthesisParam；
2. 流式输出：StreamingChatModel、Flux\<String\>、StreamingChatResponseHandler

## 2025-7-21

### feat

1. 初识：ChatModel、UserMessage、TokenUsage
2. springboot整合：@AiService
3. 高阶API：AiServices
4. 参数：
   1) 日志配置：logRequests、logResponses
   2) 监听配置：listeners
   3) 重试配置：maxRetries
   4) 请求超时：timeout
