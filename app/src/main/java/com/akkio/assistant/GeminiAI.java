package com.akkio.assistant;

import android.content.Context;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class GeminiAI {

    private Context context;
    private OkHttpClient client;
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions";

    public GeminiAI(Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .build();
    }

    public String getResponse(String userMessage) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gemini-2.0-flash");

            JSONArray messages = new JSONArray();

            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are AKKIO, a JARVIS-style AI assistant. " +
                    "You are helpful, professional, and concise. " +
                    "Keep responses under 50 words unless asked for more detail.");
            messages.put(systemMessage);

            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);
            messages.put(userMsg);

            requestBody.put("messages", messages);
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 150);

            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + getApiKey())
                    .post(RequestBody.create(
                            requestBody.toString(),
                            MediaType.parse("application/json")
                    ))
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                JSONArray choices = jsonResponse.getJSONArray("choices");
                JSONObject firstChoice = choices.getJSONObject(0);
                JSONObject message = firstChoice.getJSONObject("message");
                return message.getString("content");
            } else {
                return "❌ Error connecting to AI. Please check your API key.";
            }

        } catch (Exception e) {
            return "❌ Sorry, I'm having trouble connecting to my AI brain.";
        }
    }

    private String getApiKey() {
        return context.getSharedPreferences("AKKIO_PREFS", Context.MODE_PRIVATE)
                .getString("ai_api_key", "");
    }
}