package com.umeng.socialize.media;

import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareContent;

public class QQShareContent extends SimpleShareContent {
    protected static final String DEFAULT_TARGET_URL = "http://wsq.umeng.com/";
    public int mShareType = 1;

    public QQShareContent(ShareContent shareContent) {
        super(shareContent);
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMusic)) {
            setMusic((UMusic) shareContent.mMedia);
        }
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMVideo)) {
            setVideo((UMVideo) shareContent.mMedia);
        }
    }

    public Bundle buildParams() {
        Bundle mParams = new Bundle();
        if (getImage() != null && TextUtils.isEmpty(getText())) {
            this.mShareType = 5;
            buildImageParams(mParams);
        } else if (getVideo() != null || getMusic() != null) {
            this.mShareType = 2;
            buildAudioParams(mParams);
        } else if (!(getImage() == null || TextUtils.isEmpty(getText()))) {
            buildTextImageParams(mParams);
        }
        mParams.putString("summary", getText());
        mParams.putInt("req_type", this.mShareType);
        if (TextUtils.isEmpty(getTargeturl())) {
            setTargeturl(DEFAULT_TARGET_URL);
        }
        mParams.putString("targetUrl", getTargeturl());
        if (TextUtils.isEmpty(getTitle())) {
            mParams.putString("title", " ");
        } else {
            mParams.putString("title", getTitle());
        }
        if (Config.QQWITHQZONE == 1) {
            mParams.putInt("cflag", 1);
        } else if (Config.QQWITHQZONE == 2) {
            mParams.putInt("cflag", 2);
        }
        if (!TextUtils.isEmpty(Config.appName)) {
            mParams.putString("appName", Config.appName);
        }
        return mParams;
    }

    private void buildImageParams(Bundle bundle) {
        if (getImage() == null) {
            return;
        }
        if (getImage().asFileImage() != null) {
            bundle.putString("imageLocalUrl", getImage().asFileImage().toString());
        } else {
            bundle.putString("error", "请检查是否添加了读写文件的权限，或检查是否有sd卡");
        }
    }

    private void buildTextImageParams(Bundle bundle) {
        if (getImage() == null) {
            return;
        }
        if (getImage().isUrlMedia()) {
            bundle.putString("imageUrl", getImage().toUrl());
        } else if (getImage().asFileImage() != null) {
            bundle.putString("imageLocalUrl", getImage().asFileImage().toString());
        } else {
            bundle.putString("error", "请检查是否添加了读写文件的权限，或检查是否有sd卡");
        }
    }

    private void buildAudioParams(Bundle bundle) {
        UMediaObject TEMP = null;
        UMImage thumb = null;
        String thumbpath = null;
        if (getMusic() != null) {
            TEMP = getMusic();
            thumb = getMusic().getThumbImage();
            thumbpath = getMusic().getThumb();
        } else if (getVideo() != null) {
            TEMP = getVideo();
            thumb = getVideo().getThumbImage();
            thumbpath = getVideo().getThumb();
        }
        if (thumbpath != null) {
            bundle.putString("imageUrl", thumbpath);
        } else if (thumb != null) {
            if (thumb.isUrlMedia()) {
                bundle.putString("imageUrl", thumb.toUrl());
            } else if (getImage().asFileImage() != null) {
                bundle.putString("imageLocalUrl", thumb.asFileImage().toString());
            } else {
                bundle.putString("error", "请检查是否添加了读写文件的权限，或检查是否有sd卡");
            }
        } else if (getImage() != null) {
            if (getImage().isUrlMedia()) {
                bundle.putString("imageUrl", getImage().toUrl());
            } else if (getImage().asFileImage() != null) {
                bundle.putString("imageLocalUrl", thumb.asFileImage().toString());
            } else {
                bundle.putString("error", "请检查是否添加了读写文件的权限，或检查是否有sd卡");
            }
        }
        if (TextUtils.isEmpty(getTargeturl())) {
            setTargeturl(TEMP.toUrl());
        }
        bundle.putString("audio_url", TEMP.toUrl());
    }
}
