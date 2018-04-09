package com.droi.sdk.selfupdate;

public interface DroiInappDownloadListener {
    void onFailed(int i);

    void onFinished(String str);

    void onProgress(float f);

    void onStart(long j);
}
