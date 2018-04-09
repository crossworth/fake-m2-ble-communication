package com.zhuoyou.plugin.running.baas;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

public class SportsReport {

    @SuppressLint({"ParcelCreator"})
    public static class Request extends DroiObject {
        @DroiExpose
        public String account;
        @DroiExpose
        public String end;
        @DroiExpose
        public String start;
        @DroiExpose
        public int type;
    }

    @SuppressLint({"ParcelCreator"})
    public static class Response extends DroiObject {
        @DroiExpose
        public int code;
        @DroiExpose
        public String result;
        @DroiExpose
        public long time;
    }
}
