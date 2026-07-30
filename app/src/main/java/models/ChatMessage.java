package com.akkio.assistant.models;

import java.util.Date;

public class ChatMessage {
    private String speaker;
    private String text;
    private boolean isAI;
    private long timestamp;

    public ChatMessage(String speaker, String text, boolean isAI) {
        this.speaker = speaker;
        this.text = text;
        this.isAI = isAI;
        this.timestamp = System.currentTimeMillis();
    }

    public String getSpeaker() { return speaker; }
    public String getText() { return text; }
    public boolean isAI() { return isAI; }
    public long getTimestamp() { return timestamp; }

    public String getFormattedTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}