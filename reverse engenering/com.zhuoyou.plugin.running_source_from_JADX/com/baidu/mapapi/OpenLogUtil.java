package com.baidu.mapapi;

import com.baidu.platform.comjni.tools.C0680a;

public class OpenLogUtil {
    private static ModuleName f925a;

    public static void setModuleLogEnable(ModuleName moduleName, boolean z) {
        f925a = moduleName;
        C0680a.m2305a(z, f925a.ordinal());
    }
}
