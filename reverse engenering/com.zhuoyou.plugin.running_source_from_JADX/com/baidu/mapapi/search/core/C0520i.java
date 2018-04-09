package com.baidu.mapapi.search.core;

import com.baidu.mapapi.VersionInfo;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.baidu.platform.comapi.NativeLoader;

public class C0520i {
    static {
        if (VersionInfo.getApiVersion().equals(C0536o.m1463a())) {
            NativeLoader.getInstance().loadLibrary(C0536o.m1464b());
            return;
        }
        throw new BaiduMapSDKException("the version of search is not match with base");
    }
}
