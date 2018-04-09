package com.umeng.socialize.media;

import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.socialize.ShareContent;
import java.util.ArrayList;
import java.util.Iterator;

public class QZoneShareContent extends QQShareContent {
    private boolean isPublish = false;
    private UMediaObject uMedia;

    public QZoneShareContent(ShareContent shareContent) {
        super(shareContent);
        this.uMedia = shareContent.mMedia;
    }

    public UMediaObject getMedia() {
        return this.uMedia;
    }

    public boolean getisPublish() {
        return this.isPublish;
    }

    public Bundle buildParamsQzone() {
        Bundle bundle = new Bundle();
        String shareContent = getText();
        int shareType = 1;
        this.isPublish = false;
        UMediaObject uMediaObject = this.uMedia;
        if ((uMediaObject instanceof UMImage) && TextUtils.isEmpty(getText())) {
            if (getTargeturl() == null) {
                this.isPublish = true;
                shareType = 3;
                setShareToImage(bundle);
            } else {
                setShareToImage(bundle);
            }
        } else if ((uMediaObject instanceof UMVideo) || (uMediaObject instanceof UMusic)) {
            shareType = 2;
            setShareToAudio(bundle);
        } else if (TextUtils.isEmpty(getTargeturl())) {
            this.isPublish = true;
            shareType = 3;
            setShareToTextAndImage(bundle);
        } else {
            setShareToTextAndImage(bundle);
        }
        bundle.putString("summary", shareContent);
        ArrayList<String> paths = new ArrayList();
        if (QQExtra.QzoneList.size() > 1) {
            Iterator it = QQExtra.QzoneList.iterator();
            while (it.hasNext()) {
                paths.add((String) it.next());
            }
            bundle.remove("imageLocalUrl");
            bundle.putStringArrayList("imageLocalUrl", paths);
            QQExtra.QzoneList.clear();
        } else {
            String imagePath = bundle.getString("imageUrl");
            bundle.remove("imageUrl");
            if (!TextUtils.isEmpty(imagePath)) {
                paths.add(imagePath);
            }
            bundle.putStringArrayList("imageUrl", paths);
            String imagePath2 = bundle.getString("imageLocalUrl");
            bundle.remove("imageLocalUrl");
            if (!TextUtils.isEmpty(imagePath2)) {
                paths.add(imagePath2);
            }
            bundle.putStringArrayList("imageLocalUrl", paths);
        }
        bundle.putInt("req_type", shareType);
        if (TextUtils.isEmpty(bundle.getString("title"))) {
            bundle.putString("title", "分享到QQ空间");
        }
        if (TextUtils.isEmpty(bundle.getString("targetUrl"))) {
            bundle.putString("targetUrl", "http://wsq.umeng.com/");
        }
        return bundle;
    }

    private void setShareToTextAndImage(Bundle bundle) {
        setShareToImage(bundle);
    }

    private void setShareToAudio(Bundle bundle) {
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
            } else {
                bundle.putString("imageLocalUrl", thumb.asFileImage().toString());
            }
        } else if (getImage() != null) {
            if (getImage().isUrlMedia()) {
                bundle.putString("imageUrl", getImage().toUrl());
            } else {
                bundle.putString("imageLocalUrl", getImage().asFileImage().toString());
            }
        }
        bundle.putString("targetUrl", getTargeturl());
        bundle.putString("audio_url", TEMP.toUrl());
        bundle.putString("title", getTitle());
    }

    private void setShareToImage(Bundle bundle) {
        if (this.isPublish) {
            if (!(getImage() == null || getImage().asFileImage() == null)) {
                bundle.putString("imageUrl", getImage().asFileImage().toString());
            }
        } else if (getImage() != null) {
            if (getImage().isUrlMedia()) {
                bundle.putString("imageUrl", getImage().toUrl());
            } else {
                bundle.putString("imageLocalUrl", getImage().asFileImage().toString());
            }
        }
        if (TextUtils.isEmpty(getTargeturl())) {
            setTargeturl("http://wsq.umeng.com/");
        }
        if (!this.isPublish) {
            bundle.putString("targetUrl", getTargeturl());
            bundle.putString("title", getTitle());
        }
    }
}
