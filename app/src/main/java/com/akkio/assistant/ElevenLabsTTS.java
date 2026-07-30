package com.akkio.assistant;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class ElevenLabsTTS {

    private Context context;
    private OkHttpClient client;
    private AudioTrack audioTrack;
    private static final String VOICE_ID = "pNInz6obpgDQGcFmaJgB"; // Adam voice
    private static final String API_URL = "https://api.elevenlabs.io/v1/text-to-speech/";

    public interface Callback {
        void onStart();
        void onComplete();
        void onError(String error);
    }

    public ElevenLabsTTS(Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .build();
    }

    public void speak(String text, String apiKey, Callback callback) {
        new Thread(() -> {
            try {
                if (callback != null) callback.onStart();

                JSONObject json = new JSONObject();
                json.put("text", text);
                json.put("model_id", "eleven_multilingual_v2");

                JSONObject voiceSettings = new JSONObject();
                voiceSettings.put("stability", 0.3);
                voiceSettings.put("similarity_boost", 0.75);
                voiceSettings.put("style", 0.1);
                voiceSettings.put("use_speaker_boost", true);
                json.put("voice_settings", voiceSettings);

                RequestBody body = RequestBody.create(
                        json.toString(),
                        MediaType.parse("application/json")
                );

                Request request = new Request.Builder()
                        .url(API_URL + VOICE_ID)
                        .addHeader("Accept", "audio/mpeg")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("xi-api-key", apiKey)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    byte[] audioData = response.body().bytes();
                    playAudio(audioData, callback);
                } else {
                    if (callback != null) {
                        callback.onError("API Error: " + response.code());
                    }
                }

            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e.getMessage());
                }
            }
        }).start();
    }

    private void playAudio(byte[] audioData, Callback callback) {
        try {
            int sampleRate = 44100;
            int minBufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
            );

            audioTrack = new AudioTrack(
                    new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .build(),
                    new AudioFormat.Builder()
                            .setSampleRate(sampleRate)
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build(),
                    minBufferSize,
                    AudioTrack.MODE_STREAM,
                    AudioManager.AUDIO_SESSION_ID_GENERATE
            );

            audioTrack.play();

            // Write WAV header and audio data
            byte[] wavData = createWAV(audioData, sampleRate);
            audioTrack.write(wavData, 0, wavData.length);
            audioTrack.stop();
            audioTrack.release();

            if (callback != null) callback.onComplete();

        } catch (Exception e) {
            if (callback != null) callback.onError(e.getMessage());
        }
    }

    private byte[] createWAV(byte[] mp3Data, int sampleRate) {
        // Simplified: For production, use a proper MP3 decoder
        // This is a placeholder - you'd need to convert MP3 to PCM
        return mp3Data;
    }
}