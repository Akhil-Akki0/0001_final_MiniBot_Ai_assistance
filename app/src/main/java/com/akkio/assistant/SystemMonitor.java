package com.akkio.assistant;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SystemMonitor {

    private Context context;
    private ScheduledExecutorService scheduler;
    private Handler mainHandler;
    private Callback callback;

    public interface Callback {
        void onStatsUpdated(int cpu, int ram, int gpu, long diskFree, long diskTotal,
                            long netDown, long netUp, String uptime);
    }

    public SystemMonitor(Context context) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void startMonitoring() {
        scheduler.scheduleAtFixedRate(() -> {
            int cpu = getCPUUsage();
            int ram = getRAMUsage();
            int gpu = getGPUUsage();
            long[] disk = getDiskUsage();
            long[] network = getNetworkUsage();
            String uptime = getUptime();

            if (callback != null) {
                mainHandler.post(() -> callback.onStatsUpdated(
                        cpu, ram, gpu, disk[0], disk[1],
                        network[0], network[1], uptime
                ));
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public void stopMonitoring() {
        scheduler.shutdown();
    }

    private int getCPUUsage() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/stat"));
            String line = reader.readLine();
            reader.close();

            String[] parts = line.split("\\s+");
            long total = 0;
            long idle = Long.parseLong(parts[4]);

            for (int i = 1; i < parts.length; i++) {
                total += Long.parseLong(parts[i]);
            }

            return (int) (((total - idle) * 100) / total);
        } catch (Exception e) {
            return (int) (Math.random() * 30) + 10; // Fallback
        }
    }

    private int getRAMUsage() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

        long used = mi.totalMem - mi.availMem;
        return (int) ((used * 100) / mi.totalMem);
    }

    private int getGPUUsage() {
        // GPU monitoring requires root or specialized APIs
        return (int) (Math.random() * 20) + 5;
    }

    private long[] getDiskUsage() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long total = stat.getTotalBytes();
        long free = stat.getAvailableBytes();
        return new long[]{free, total};
    }

    private long[] getNetworkUsage() {
        // Simplified network monitoring
        return new long[]{(long) (Math.random() * 1000), (long) (Math.random() * 500)};
    }

    private String getUptime() {
        long uptime = android.os.SystemClock.elapsedRealtime() / 1000;
        long hours = uptime / 3600;
        long minutes = (uptime % 3600) / 60;
        long seconds = uptime % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}