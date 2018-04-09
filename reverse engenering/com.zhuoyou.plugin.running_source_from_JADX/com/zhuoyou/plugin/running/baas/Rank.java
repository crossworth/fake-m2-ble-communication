package com.zhuoyou.plugin.running.baas;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import java.util.ArrayList;
import java.util.List;

public class Rank {
    public static final int LIMIT = 30;
    public static final int MIN = 100000;

    @SuppressLint({"ParcelCreator"})
    public static class RankInfo extends DroiObject {
        @DroiExpose
        public String headUrl;
        @DroiExpose
        public int rank;
        @DroiExpose
        public RankZan rankZan;
        @DroiExpose
        public int step;
        @DroiExpose
        public User user;
        @DroiExpose
        public int zanNum;
        @DroiExpose
        public int zanSum;
    }

    @SuppressLint({"ParcelCreator"})
    public static class Request extends DroiObject {
        @DroiExpose
        public String account;
        @DroiExpose
        public int limit = 30;
        @DroiExpose
        public int min = Rank.MIN;
    }

    @SuppressLint({"ParcelCreator"})
    public static class Response extends DroiObject {
        @DroiExpose
        public RankInfo mUser;
        @DroiExpose
        public List<RankInfo> rankList = new ArrayList();
    }
}
