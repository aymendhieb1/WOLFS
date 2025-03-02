package com.wolfs.models;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeepSeekService {

    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String OPENROUTER_API_KEY = "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";

    public static String generateComment(String postContent) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(OPENROUTER_API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + OPENROUTER_API_KEY);

            String jsonBody = String.format(
                    "{\"model\": \"openai/gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"Generate a comment for this post: %s\"}]}",
                    postContent
            );
            httpPost.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(responseBody);

                if (rootNode.has("error")) {
                    return "API Error: " + rootNode.path("error").path("message").asText();
                }

                JsonNode choicesNode = rootNode.path("choices");
                if (choicesNode.isArray() && choicesNode.size() > 0) {
                    JsonNode messageNode = choicesNode.get(0).path("message");
                    return messageNode.path("content").asText();
                } else {
                    return "No comment generated: 'choices' array is empty.";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating comment: " + e.getMessage();
        }
    }
}