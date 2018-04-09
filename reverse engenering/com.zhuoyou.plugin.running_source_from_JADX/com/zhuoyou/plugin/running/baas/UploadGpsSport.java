package com.zhuoyou.plugin.running.baas;

import android.annotation.SuppressLint;
import com.droi.greendao.bean.GpsPointBean;
import com.droi.greendao.bean.GpsSportBean;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import java.util.List;

public class UploadGpsSport {

    @SuppressLint({"ParcelCreator"})
    public static class Request extends DroiObject {
        @DroiExpose
        public List<GpsPointBean> pointList;
        @DroiExpose
        public GpsSportBean sportBean;
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
