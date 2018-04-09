package com.baidu.platform.comjni.map.cloud;

import android.os.Bundle;

public class JniCloud {
    public native void cloudDetailSearch(long j, Bundle bundle);

    public native void cloudRgcSearch(long j, Bundle bundle);

    public native void cloudSearch(long j, Bundle bundle);

    public native long create();

    public native byte[] getSearchResult(long j, int i);

    public native int release(long j);
}
