package com.zhuoyou.plugin.running.baas;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;
import java.util.ArrayList;
import java.util.List;

public class CommentCloud {

    @SuppressLint({"ParcelCreator"})
    public static class CommentInfo extends DroiObject implements Comparable<CommentInfo> {
        @DroiExpose
        public String comment;
        @DroiExpose
        public String commentAdd;
        @DroiExpose
        public String commentId;
        @DroiExpose
        public String date;
        @DroiExpose
        public String headUrl;
        @DroiReference
        public User toUser;
        @DroiReference
        public User user;
        @DroiExpose
        public CommentZan zanBean;
        @DroiExpose
        public int zanSum;

        public boolean equals(Object o) {
            if (!(o instanceof CommentInfo)) {
                return super.equals(o);
            }
            return this.commentId.equals(((CommentInfo) o).commentId);
        }

        public int compareTo(CommentInfo another) {
            return this.date.compareTo(another.date);
        }
    }

    @SuppressLint({"ParcelCreator"})
    public static class CommentRequest extends DroiObject {
        @DroiExpose
        public String accountId;
        @DroiExpose
        public String date;
        @DroiExpose
        public String topicId;
    }

    @SuppressLint({"ParcelCreator"})
    public static class CommentResponse extends DroiObject {
        @DroiExpose
        public List<CommentInfo> commentInfoList = new ArrayList();
        @DroiExpose
        public List<CommentInfo> selectedCommentInfoList = new ArrayList();
    }
}
