package com.akkio.assistant.models;

public class APIConfig {
    // AI Provider
    private String aiProvider; // "gemini", "openai", "custom"
    private String aiApiKey;
    private String aiApiUrl;
    private String aiModel;

    // Voice Provider
    private String voiceProvider; // "elevenlabs", "device"
    private String voiceApiKey;
    private String voiceId;

    // Search Provider
    private String searchProvider; // "serper", "direct"
    private String searchApiKey;

    // Constructors
    public APIConfig() {
        this.aiProvider = "none";
        this.voiceProvider = "device";
        this.searchProvider = "direct";
        this.voiceId = "pNInz6obpgDQGcFmaJgB";
        this.aiModel = "gemini-2.0-flash";
    }

    // Getters and Setters
    public String getAiProvider() { return aiProvider; }
    public void setAiProvider(String aiProvider) { this.aiProvider = aiProvider; }

    public String getAiApiKey() { return aiApiKey; }
    public void setAiApiKey(String aiApiKey) { this.aiApiKey = aiApiKey; }

    public String getAiApiUrl() { return aiApiUrl; }
    public void setAiApiUrl(String aiApiUrl) { this.aiApiUrl = aiApiUrl; }

    public String getAiModel() { return aiModel; }
    public void setAiModel(String aiModel) { this.aiModel = aiModel; }

    public String getVoiceProvider() { return voiceProvider; }
    public void setVoiceProvider(String voiceProvider) { this.voiceProvider = voiceProvider; }

    public String getVoiceApiKey() { return voiceApiKey; }
    public void setVoiceApiKey(String voiceApiKey) { this.voiceApiKey = voiceApiKey; }

    public String getVoiceId() { return voiceId; }
    public void setVoiceId(String voiceId) { this.voiceId = voiceId; }

    public String getSearchProvider() { return searchProvider; }
    public void setSearchProvider(String searchProvider) { this.searchProvider = searchProvider; }

    public String getSearchApiKey() { return searchApiKey; }
    public void setSearchApiKey(String searchApiKey) { this.searchApiKey = searchApiKey; }

    public boolean hasAI() { return aiApiKey != null && !aiApiKey.isEmpty(); }
    public boolean hasVoice() { return voiceApiKey != null && !voiceApiKey.isEmpty(); }
    public boolean hasSearch() { return searchApiKey != null && !searchApiKey.isEmpty(); }
}