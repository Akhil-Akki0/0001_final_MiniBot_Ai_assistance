package com.akkio.assistant;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {

    private static final String PREFS_NAME = "AKKIO_PREFS";
    private static final String KEY_AI_API = "ai_api_key";
    private static final String KEY_VOICE_API = "voice_api_key";
    private static final String KEY_SEARCH_API = "search_api_key";
    private static final String KEY_VOICE_ACTIVATION = "voice_activation";
    private static final String KEY_SENSITIVITY = "sensitivity";
    private static final String KEY_VOICE_ID = "voice_id";
    private static final String KEY_OPACITY = "opacity";

    private SharedPreferences prefs;

    public SettingsManager(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveKeys(String aiKey, String voiceKey, String searchKey) {
        prefs.edit()
                .putString(KEY_AI_API, aiKey)
                .putString(KEY_VOICE_API, voiceKey)
                .putString(KEY_SEARCH_API, searchKey)
                .apply();
    }

    public String getAIKey() {
        return prefs.getString(KEY_AI_API, "");
    }

    public String getVoiceKey() {
        return prefs.getString(KEY_VOICE_API, "");
    }

    public String getSearchKey() {
        return prefs.getString(KEY_SEARCH_API, "");
    }

    public boolean isVoiceActivationEnabled() {
        return prefs.getBoolean(KEY_VOICE_ACTIVATION, true);
    }

    public int getSensitivity() {
        return prefs.getInt(KEY_SENSITIVITY, 50);
    }

    public String getVoiceId() {
        return prefs.getString(KEY_VOICE_ID, "pNInz6obpgDQGcFmaJgB");
    }

    public int getOpacity() {
        return prefs.getInt(KEY_OPACITY, 90);
    }

    public void saveSetting(String key, Object value) {
        SharedPreferences.Editor editor = prefs.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        editor.apply();
    }
}