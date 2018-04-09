package com.zhuoyou.plugin.running.baas;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

public class DeleteGpsSport {

    @SuppressLint({"ParcelCreator"})
    public static class Request extends DroiObject {
        @DroiExpose
        public String account;
        @DroiExpose
        public String sportid;
    }

    @SuppressLint({"ParcelCreator"})
    public static class Response extends DroiObject {
        @DroiExpose
        public int count;
        @DroiExpose
        public String result;

        public String toString() {
            return "Response{result='" + this.result + '\'' + ", count=" + this.count + '}';
        }
    }
}
