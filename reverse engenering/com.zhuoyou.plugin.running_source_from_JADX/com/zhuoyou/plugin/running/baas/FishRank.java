package com.zhuoyou.plugin.running.baas;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import java.util.ArrayList;
import java.util.List;

public class FishRank {

    @SuppressLint({"ParcelCreator"})
    public static class RankInfo extends DroiObject {
        @DroiExpose
        public String headUrl;
        @DroiExpose
        public int rank;
        @DroiExpose
        public int score;
        @DroiExpose
        public User user;
    }

    @SuppressLint({"ParcelCreator"})
    public static class Request extends DroiObject {
        @DroiExpose
        public String account;
    }

    @SuppressLint({"ParcelCreator"})
    public static class Response extends DroiObject {
        @DroiExpose
        public RankInfo mUser;
        @DroiExpose
        public List<RankInfo> rankList = new ArrayList();
    }
}
