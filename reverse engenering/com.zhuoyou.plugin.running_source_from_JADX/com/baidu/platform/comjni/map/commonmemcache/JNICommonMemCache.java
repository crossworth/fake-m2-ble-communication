package com.baidu.platform.comjni.map.commonmemcache;

import android.os.Bundle;

public class JNICommonMemCache {
    public native long Create();

    public native String GetPhoneInfoUrl(long j);

    public native void Init(long j, Bundle bundle);
}
