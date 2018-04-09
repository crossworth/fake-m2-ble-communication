package com.umeng.socialize.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.utils.Utility;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.utils.BitmapUtils;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.Log;

public class SinaShareContent extends SimpleShareContent {
    private final int IMAGE_LIMIT = 153600;
    private final int THUMB_LIMIT = 24576;
    private UMImage mExtra;

    public SinaShareContent(ShareContent shareContent) {
        super(shareContent);
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMVideo)) {
            setVideo((UMVideo) shareContent.mMedia);
        }
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMusic)) {
            setMusic((UMusic) shareContent.mMedia);
        }
        if (shareContent.mExtra != null) {
            this.mExtra = (UMImage) shareContent.mExtra;
        }
    }

    public WeiboMultiMessage getMessage() {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj();
        if (getImage() != null) {
            weiboMessage.imageObject = getImageObj();
        }
        if (!TextUtils.isEmpty(getTargeturl())) {
            weiboMessage.mediaObject = getWebpageObj();
        }
        if (getMusic() != null) {
            weiboMessage.mediaObject = getMusicObj();
        }
        if (getVideo() != null) {
            weiboMessage.mediaObject = getVideoObj();
        }
        return weiboMessage;
    }

    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getText();
        return textObject;
    }

    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        if (getImage().asBitmap() != null) {
            byte[] datas = getImage().asBinImage();
            imageObject.setImageObject(BitmapFactory.decodeByteArray(datas, 0, datas.length));
        }
        return imageObject;
    }

    private WebpageObject getWebpageObj() {
        Bitmap bitmapThumb;
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        if (TextUtils.isEmpty(getTitle())) {
            mediaObject.title = "分享链接";
        } else {
            mediaObject.title = getTitle();
        }
        mediaObject.description = getText();
        if (this.mExtra != null) {
            byte[] datas = this.mExtra.asBinImage();
            if (this.mExtra.asBinImage().length > 24576) {
                bitmapThumb = BitmapFactory.decodeByteArray(BitmapUtils.compressBitmap(datas, 24576), 0, BitmapUtils.compressBitmap(datas, 24576).length);
            } else {
                bitmapThumb = this.mExtra.asBitmap();
            }
        } else {
            bitmapThumb = BitmapFactory.decodeResource(ContextUtil.getContext().getResources(), ResContainer.getResourceId(ContextUtil.getContext(), "drawable", "umeng_socialize_sina"));
        }
        mediaObject.setThumbImage(bitmapThumb);
        mediaObject.actionUrl = getTargeturl();
        mediaObject.defaultText = getText();
        Log.m4546d("share", "args check:" + mediaObject.checkArgs());
        return mediaObject;
    }

    private MusicObject getMusicObj() {
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        if (TextUtils.isEmpty(getTitle())) {
            musicObject.title = "分享音乐";
        } else {
            musicObject.title = getTitle();
        }
        musicObject.description = getMusic().mText;
        Bitmap bitmapThumb = null;
        byte[] images;
        if (getMusic().getThumbImage() != null) {
            images = BitmapUtils.compressBitmap(getMusic().getThumbImage().asBinImage(), 24576);
            if (images != null) {
                bitmapThumb = BitmapFactory.decodeByteArray(images, 0, images.length);
            }
        } else if (TextUtils.isEmpty(getMusic().getThumb())) {
            bitmapThumb = BitmapFactory.decodeResource(ContextUtil.getContext().getResources(), ResContainer.getResourceId(ContextUtil.getContext(), "drawable", "ic_logo"));
        } else {
            images = BitmapUtils.compressBitmap(new UMImage(ContextUtil.getContext(), getMusic().getThumb()).asBinImage(), 24576);
            if (images != null) {
                bitmapThumb = BitmapFactory.decodeByteArray(images, 0, images.length);
                Log.m4546d("UM", "get thumb bitmap");
            }
        }
        musicObject.setThumbImage(bitmapThumb);
        musicObject.actionUrl = getMusic().toUrl();
        if (!TextUtils.isEmpty(getMusic().getLowBandDataUrl())) {
            musicObject.dataUrl = getMusic().getLowBandDataUrl();
        }
        if (!TextUtils.isEmpty(getMusic().getHighBandDataUrl())) {
            musicObject.dataHdUrl = getMusic().getHighBandDataUrl();
        }
        if (!TextUtils.isEmpty(getMusic().getH5Url())) {
            musicObject.h5Url = getMusic().getH5Url();
        }
        if (getMusic().getDuration() > 0) {
            musicObject.duration = getMusic().getDuration();
        } else {
            musicObject.duration = 10;
        }
        if (!TextUtils.isEmpty(getMusic().getDescription())) {
            musicObject.description = getMusic().getDescription();
        }
        if (!TextUtils.isEmpty(getText())) {
            musicObject.defaultText = getText();
        }
        return musicObject;
    }

    private VideoObject getVideoObj() {
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        if (TextUtils.isEmpty(getTitle())) {
            videoObject.title = "分享视频";
        } else {
            videoObject.title = getTitle();
        }
        videoObject.description = getText();
        Bitmap bitmapThumb = null;
        byte[] images;
        if (getVideo().getThumbImage() != null) {
            images = BitmapUtils.compressBitmap(getVideo().getThumbImage().asBinImage(), 24576);
            if (images != null) {
                bitmapThumb = BitmapFactory.decodeByteArray(images, 0, images.length);
            }
        } else if (TextUtils.isEmpty(getVideo().getThumb())) {
            bitmapThumb = BitmapFactory.decodeResource(ContextUtil.getContext().getResources(), ResContainer.getResourceId(ContextUtil.getContext(), "drawable", "ic_logo"));
        } else {
            images = new UMImage(ContextUtil.getContext(), getVideo().getThumb()).asBinImage();
            if (images != null) {
                bitmapThumb = BitmapFactory.decodeByteArray(images, 0, images.length);
            }
        }
        videoObject.setThumbImage(bitmapThumb);
        videoObject.actionUrl = getVideo().toUrl();
        if (!TextUtils.isEmpty(getVideo().getLowBandDataUrl())) {
            videoObject.dataUrl = getVideo().getLowBandDataUrl();
        }
        if (!TextUtils.isEmpty(getVideo().getHighBandDataUrl())) {
            videoObject.dataHdUrl = getVideo().getHighBandDataUrl();
        }
        if (!TextUtils.isEmpty(getVideo().getH5Url())) {
            videoObject.h5Url = getVideo().getH5Url();
        }
        if (getVideo().getDuration() > 0) {
            videoObject.duration = getVideo().getDuration();
        } else {
            videoObject.duration = 10;
        }
        if (!TextUtils.isEmpty(getVideo().getDescription())) {
            videoObject.description = getVideo().getDescription();
        }
        videoObject.defaultText = "Video 分享视频";
        return videoObject;
    }

    private VoiceObject getVoiceObj() {
        return null;
    }
}
