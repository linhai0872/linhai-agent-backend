package com.linhai.linhai_agent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * Langchain4j 框架调用 AI 大模型
 * @see <a href="https://docs.langchain4j.info/integrations/language-models/dashscope">...</a>
 */
public class LangChainAiInvoke {

    public static void main(String[] args) {
        ChatLanguageModel qwenChatModel = QwenChatModel.builder()
                .apiKey(TestApiKey.apiKey)
                .modelName("qwen-plus")
                .build();
        String answer = qwenChatModel.chat("我是林海，这是我的一个AI项目的测试demo");
        System.out.println(answer);
    }
}