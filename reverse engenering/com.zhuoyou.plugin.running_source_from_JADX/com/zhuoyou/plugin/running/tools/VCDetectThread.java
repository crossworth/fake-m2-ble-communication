package com.zhuoyou.plugin.running.tools;

import android.media.AudioRecord;
import android.os.Handler;
import android.util.Log;

public class VCDetectThread {
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, 1, 2);
    private static final int SAMPLE_RATE_IN_HZ = 8000;
    private static final String TAG = "renjing";
    private int count;
    private long endTime;
    private Handler handler;
    private int f5007i;
    private boolean isGetVoiceRun;
    private int f5008k;
    private AudioRecord mAudioRecord;
    private final Object mLock;
    private long startTime;
    private double totalVolume;

    class C19291 implements Runnable {
        C19291() {
        }

        public void run() {
            try {
                VCDetectThread.this.mAudioRecord.startRecording();
                VCDetectThread.this.startTime = System.currentTimeMillis();
                VCDetectThread.this.endTime = VCDetectThread.this.startTime;
                short[] buffer = new short[VCDetectThread.BUFFER_SIZE];
                int totalValue = 0;
                while (VCDetectThread.this.isGetVoiceRun) {
                    Log.d(VCDetectThread.TAG, "endTime" + VCDetectThread.this.endTime);
                    Log.d(VCDetectThread.TAG, "startTime" + VCDetectThread.this.startTime);
                    Log.d(VCDetectThread.TAG, LogColumns.TIME + (VCDetectThread.this.endTime - VCDetectThread.this.startTime));
                    int r = VCDetectThread.this.mAudioRecord.read(buffer, 0, VCDetectThread.BUFFER_SIZE);
                    long v = 0;
                    for (short aBuffer : buffer) {
                        v += (long) (aBuffer * aBuffer);
                    }
                    double volume = 10.0d * Math.log10(((double) v) / ((double) r));
                    if (volume > 0.0d) {
                        VCDetectThread.this.count = VCDetectThread.this.count + 1;
                        VCDetectThread.this.totalVolume = VCDetectThread.this.totalVolume + volume;
                    }
                    if (VCDetectThread.this.count > 20) {
                        VCDetectThread.this.count = 1;
                        VCDetectThread.this.totalVolume = volume;
                    }
                    Log.d(VCDetectThread.TAG, "<,54:" + VCDetectThread.this.totalVolume + "/" + VCDetectThread.this.count + "=" + (VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count)));
                    if (VCDetectThread.this.count == 0 || VCDetectThread.this.totalVolume / ((double) (VCDetectThread.this.count + 1)) < 54.0d) {
                        if (VCDetectThread.this.f5008k > 0 && VCDetectThread.this.f5008k < 6) {
                            VCDetectThread.this.f5007i = VCDetectThread.this.f5007i + 1;
                            if (VCDetectThread.this.f5007i > 12) {
                                VCDetectThread.this.isGetVoiceRun = false;
                            }
                        }
                        VCDetectThread.this.startTime = System.currentTimeMillis();
                    } else {
                        VCDetectThread.this.f5008k = VCDetectThread.this.f5008k + 1;
                    }
                    Log.d(VCDetectThread.TAG, "k++:" + VCDetectThread.this.f5008k);
                    if (volume < 74.5d && VCDetectThread.this.f5008k > 5) {
                        VCDetectThread.this.isGetVoiceRun = false;
                    }
                    synchronized (VCDetectThread.this.mLock) {
                        try {
                            VCDetectThread.this.mLock.wait(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    VCDetectThread.this.endTime = System.currentTimeMillis();
                    if (VCDetectThread.this.endTime - VCDetectThread.this.startTime > 150 && VCDetectThread.this.endTime - VCDetectThread.this.startTime < 500) {
                        if (VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count) > 54.0d) {
                            totalValue += (int) (VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count));
                        }
                        Log.d(VCDetectThread.TAG, "totalValue1:" + totalValue);
                        SPUtils.setVitalCapacity(totalValue);
                        VCDetectThread.this.handler.sendEmptyMessage(0);
                    } else if (VCDetectThread.this.endTime - VCDetectThread.this.startTime > 500 && VCDetectThread.this.endTime - VCDetectThread.this.startTime < 1000) {
                        if (VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count) > 54.0d) {
                            totalValue += (int) ((VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count)) - 66.0d);
                        }
                        Log.d(VCDetectThread.TAG, "totalValue2:" + totalValue);
                        SPUtils.setVitalCapacity(totalValue);
                        VCDetectThread.this.handler.sendEmptyMessage(0);
                    } else if (VCDetectThread.this.endTime - VCDetectThread.this.startTime > 1300 && VCDetectThread.this.endTime - VCDetectThread.this.startTime < 3000) {
                        if (VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count) > 54.0d) {
                            totalValue += (int) ((VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count)) - 62.0d);
                        }
                        Log.d(VCDetectThread.TAG, "totalValue3:" + totalValue);
                        SPUtils.setVitalCapacity(totalValue);
                        VCDetectThread.this.handler.sendEmptyMessage(0);
                    } else if (VCDetectThread.this.endTime - VCDetectThread.this.startTime > 3000) {
                        Log.d(VCDetectThread.TAG, "otalVolume:" + VCDetectThread.this.totalVolume);
                        Log.d(VCDetectThread.TAG, "count:" + VCDetectThread.this.count);
                        Log.d(VCDetectThread.TAG, "otalVolume/count:" + (VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count)));
                        if (VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count) > 54.0d) {
                            totalValue += (int) ((VCDetectThread.this.totalVolume / ((double) VCDetectThread.this.count)) - 62.0d);
                        }
                        Log.d(VCDetectThread.TAG, "totalValue4:" + totalValue);
                        SPUtils.setVitalCapacity(totalValue);
                        VCDetectThread.this.handler.sendEmptyMessage(0);
                    }
                }
                VCDetectThread.this.mAudioRecord.stop();
                VCDetectThread.this.mAudioRecord.release();
                VCDetectThread.this.mAudioRecord = null;
                VCDetectThread.this.handler.sendEmptyMessage(1);
            } catch (Exception e2) {
                VCDetectThread.this.handler.sendEmptyMessage(4);
            }
        }
    }

    public VCDetectThread() {
        this.mLock = new Object();
        this.totalVolume = 0.0d;
        this.count = 0;
        this.f5007i = 0;
        this.f5008k = 0;
    }

    public VCDetectThread(Handler myHandler) {
        this.mLock = new Object();
        this.totalVolume = 0.0d;
        this.count = 0;
        this.f5007i = 0;
        this.f5008k = 0;
        this.mAudioRecord = new AudioRecord(1, SAMPLE_RATE_IN_HZ, 1, 2, BUFFER_SIZE);
        this.handler = myHandler;
    }

    public void getNoiseLevel() {
        if (this.isGetVoiceRun) {
            Log.e(TAG, "还在录着呢");
            return;
        }
        if (this.mAudioRecord == null) {
            Log.e("sound", "mAudioRecord初始化失败");
        }
        this.isGetVoiceRun = true;
        new Thread(new C19291()).start();
    }

    public void stopThread() {
        this.isGetVoiceRun = false;
    }
}
