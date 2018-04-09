package com.baidu.platform.comjni.map.radar;

import android.os.Bundle;

public class JNIRadar {
    public native long Create();

    public native String GetRadarResult(long j, int i);

    public native int Release(long j);

    public native boolean SendClearLocationInfoRequest(long j, Bundle bundle);

    public native boolean SendGetLocationInfosNearbyRequest(long j, Bundle bundle);

    public native boolean SendUploadLocationInfoRequest(long j, Bundle bundle);
}
