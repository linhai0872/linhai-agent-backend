package com.linhai.linhai_agent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Spring AI 框架调用 AI 大模型（本地ollama运行）
 * @see <a href="https://java2ai.com/docs/1.0.0-M6.1/models/ollama/?spm=4347728f.1073474f.0.0.49347982J8Pkpq">...</a>
 */
// 取消注释后，项目启动时会执行
//@Component
public class OllamaAiInvoke implements CommandLineRunner {

    @Resource
    private ChatModel ollamaChatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage assistantMessage = ollamaChatModel.call(new Prompt("你好，我是林海，这是我的一个测试demo"))
                .getResult()
                .getOutput();
        System.out.println(assistantMessage.getText());
    }
}
