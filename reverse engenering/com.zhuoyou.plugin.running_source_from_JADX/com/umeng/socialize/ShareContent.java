package com.umeng.socialize;

import android.text.TextUtils;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.media.UMusic;
import java.io.File;

public class ShareContent {
    public File app;
    public File file;
    public UMediaObject mExtra;
    public String mFollow;
    public UMediaObject mMedia;
    public String mTargetUrl;
    public String mText;
    public String mTitle;

    public int getShareType() {
        if (this.mMedia == null && this.mExtra == null && this.file == null) {
            if (TextUtils.isEmpty(this.mText)) {
                return 0;
            }
            return 1;
        } else if (this.file != null) {
            return 32;
        } else {
            if (this.mExtra != null) {
                return 64;
            }
            if (this.mTargetUrl != null) {
                return 16;
            }
            if (this.mMedia == null) {
                return 0;
            }
            if (this.mMedia instanceof UMImage) {
                if (TextUtils.isEmpty(this.mText)) {
                    return 2;
                }
                return 3;
            } else if (this.mMedia instanceof UMusic) {
                return 4;
            } else {
                if (this.mMedia instanceof UMVideo) {
                    return 8;
                }
                return 0;
            }
        }
    }
}
