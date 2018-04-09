package com.droi.sdk.selfupdate;

import java.io.File;

public interface DroiDownloadListener {
    void onFailed(int i);

    void onFinished(File file);

    void onPatching();

    void onProgress(float f);

    void onStart(long j);
}
