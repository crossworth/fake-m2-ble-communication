package com.baidu.mapapi.p014a.p015a;

import com.baidu.mapapi.VersionInfo;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.baidu.platform.comapi.NativeLoader;

public class C0469a {
    static {
        if (VersionInfo.getApiVersion().equals(C0470b.m1030a())) {
            NativeLoader.getInstance().loadLibrary(C0470b.m1031b());
            return;
        }
        throw new BaiduMapSDKException("the version of util is not match with base");
    }
}
