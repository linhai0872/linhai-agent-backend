package com.linhai.linhai_agent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * HTTP 方式调用 AI
 * @see <a href="https://help.aliyun.com/zh/model-studio/use-qwen-by-calling-api#9141263b961cc">...</a>
 */
public class QwenHttpTest {

    private static final String API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
    private static final String MODEL_NAME = "qwen-plus";

    public static String callQwenPlus(String userContent) {
        // 1. 从同包下的 ApiConfig 类获取 API Key
        String apiKey = TestApiKey.apiKey;

        JSONObject payload = new JSONObject();
        payload.set("model", MODEL_NAME);

        JSONObject input = new JSONObject();
        JSONArray messages = new JSONArray();

        JSONObject systemMessage = new JSONObject();
        systemMessage.set("role", "system");
        systemMessage.set("content", "You are a helpful assistant.");
        messages.add(systemMessage);

        JSONObject userMessage = new JSONObject();
        userMessage.set("role", "user");
        userMessage.set("content", userContent); // 使用传入的用户内容
        messages.add(userMessage);

        input.set("messages", messages);
        payload.set("input", input);

        JSONObject parameters = new JSONObject();
        parameters.set("result_format", "message");
        payload.set("parameters", parameters);

        String jsonBody = payload.toString();

        // 3. 发送 HTTP POST 请求
        try {
            HttpResponse response = HttpRequest.post(API_URL)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .timeout(20000) // 设置超时时间，单位毫秒
                    .execute();

            if (response.isOk()) {
                return response.body();
            } else {
                System.err.println("请求失败，状态码: " + response.getStatus());
                System.err.println("响应体: " + response.body());
                return "{\"error\": \"Request failed with status " + response.getStatus() + "\", \"details\": " + JSONUtil.quote(response.body()) + "}";
            }
        } catch (Exception e) {
            System.err.println("请求过程中发生异常: " + e.getMessage());
            e.printStackTrace();
            return "{\"error\": \"Exception during request\", \"details\": " + JSONUtil.quote(e.getMessage()) + "}";
        }
    }
    //第一次测试
    public static void main(String[] args) {
        // 测试调用
        String userQuery = "你是谁？";
        System.out.println("发送请求给模型: " + userQuery);
        String responseJson = callQwenPlus(userQuery);
        System.out.println("\n模型响应:");

        // 美化输出 JSON (可选)
        try {
            JSONObject responseObject = JSONUtil.parseObj(responseJson);
            System.out.println(JSONUtil.toJsonPrettyStr(responseObject));

            // 如果想直接提取 content
            if (responseObject.containsKey("output")) {
                JSONObject output = responseObject.getJSONObject("output");
                if (output.containsKey("choices")) {
                    JSONArray choices = output.getJSONArray("choices");
                    if (!choices.isEmpty()) {
                        JSONObject firstChoice = choices.getJSONObject(0);
                        if (firstChoice.containsKey("message")) {
                            JSONObject message = firstChoice.getJSONObject("message");
                            if (message.containsKey("content")) {
                                System.out.println("\n提取到的回答内容: " + message.getStr("content"));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(responseJson); // 如果不是标准JSON，直接打印
        }
//第二次测试
        System.out.println("\n--- 第二次测试 ---");
        userQuery = "用 Java 写一个 Hello World";
        System.out.println("发送请求给模型: " + userQuery);
        responseJson = callQwenPlus(userQuery);
        System.out.println("\n模型响应:");
        try {
            JSONObject responseObject = JSONUtil.parseObj(responseJson);
            System.out.println(JSONUtil.toJsonPrettyStr(responseObject));
        } catch (Exception e) {
            System.out.println(responseJson); // 如果不是标准JSON，直接打印
        }
    }
}