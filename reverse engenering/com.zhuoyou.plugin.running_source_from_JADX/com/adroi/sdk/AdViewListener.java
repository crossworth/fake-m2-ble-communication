package com.adroi.sdk;

public interface AdViewListener {
    void onAdClick();

    void onAdDismissed();

    void onAdFailed(String str);

    void onAdReady();

    void onAdShow();

    void onAdSwitch();
}
