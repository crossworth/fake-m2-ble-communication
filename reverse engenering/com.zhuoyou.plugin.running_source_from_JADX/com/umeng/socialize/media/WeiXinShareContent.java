package com.umeng.socialize.media;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXEmojiObject;
import com.tencent.mm.sdk.modelmsg.WXFileObject;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.utils.BitmapUtils;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import java.io.File;

public class WeiXinShareContent {
    public static final String TYPE_EMOJI = "emoji";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_MUSIC = "music";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_TEXT_IMAGE = "text_image";
    public static final String TYPE_VIDEO = "video";
    private final String DEFAULT_TITLE = "分享到微信";
    private final int DESCRIPTION_LIMIT = 1024;
    private final int IMAGE_LIMIT = 65536;
    private final int NETTHUMB_LIMIT = 18432;
    private final int SHOW_COMPRESS_TOAST = 1;
    private final int SHOW_TITLE_TOAST = 2;
    private final int THUMB_LIMIT = 24576;
    private final int THUMB_SIZE = 150;
    private final int TITLE_LIMIT = 512;
    private ShareContent mShareContent;
    public String mShareType;
    private String mTargetUrl;
    private String mText;
    private String mTitle;
    private WXMediaMessage mWxMediaMessage = null;
    private UMediaObject uMediaObject;

    public WeiXinShareContent(ShareContent shareContent) {
        this.mShareContent = shareContent;
        this.mTitle = shareContent.mTitle;
        this.mText = shareContent.mText;
        this.uMediaObject = shareContent.mMedia;
        this.mTargetUrl = shareContent.mTargetUrl;
    }

    public void parseMediaType() {
        if (!TextUtils.isEmpty(this.mText) && this.uMediaObject == null) {
            this.mShareType = "text";
        } else if (this.uMediaObject != null && (this.uMediaObject instanceof UMEmoji)) {
            this.mShareType = TYPE_EMOJI;
        } else if (TextUtils.isEmpty(this.mText) && this.uMediaObject != null && (this.uMediaObject instanceof UMImage)) {
            this.mShareType = "image";
        } else if (this.uMediaObject != null && (this.uMediaObject instanceof UMusic)) {
            this.mShareType = TYPE_MUSIC;
        } else if (this.uMediaObject != null && (this.uMediaObject instanceof UMVideo)) {
            this.mShareType = "video";
        } else if (!TextUtils.isEmpty(this.mText) && this.uMediaObject != null && (this.uMediaObject instanceof UMImage)) {
            this.mShareType = TYPE_TEXT_IMAGE;
        }
    }

    public WXMediaMessage getWxMediaMessage() {
        WXMediaMessage wxMessage = null;
        if (this.mShareContent.file != null) {
            wxMessage = buildFileParams();
        } else if (this.mShareContent.app != null) {
            wxMessage = buildAppParams();
        } else if (this.mShareContent.mMedia == null) {
            if (!TextUtils.isEmpty(this.mShareContent.mText)) {
                wxMessage = TextUtils.isEmpty(this.mShareContent.mTargetUrl) ? buildTextParams() : buildUrlParams();
            }
        } else if (this.mShareContent.mMedia instanceof UMEmoji) {
            wxMessage = buildEmojiParams();
        } else if (TextUtils.isEmpty(this.mShareContent.mText) && (this.mShareContent.mMedia instanceof UMImage)) {
            wxMessage = buildImageParams();
        } else if (this.mShareContent.mMedia instanceof UMusic) {
            wxMessage = buildMusicParams();
        } else if (this.mShareContent.mMedia instanceof UMVideo) {
            wxMessage = buildVideoParams();
        } else if (!TextUtils.isEmpty(this.mShareContent.mText) && (this.mShareContent.mMedia instanceof UMImage)) {
            wxMessage = TextUtils.isEmpty(this.mShareContent.mTargetUrl) ? buildImageParams() : buildUrlParams();
        }
        if (wxMessage != null) {
            byte[] thumbData = wxMessage.thumbData;
            if (thumbData != null && thumbData.length > 24576) {
                wxMessage.thumbData = BitmapUtils.compressBitmap(thumbData, 24576);
                Log.m4545d("压缩之后缩略图大小 : " + (wxMessage.thumbData.length / 1024) + " KB.");
            }
            if (TextUtils.isEmpty(wxMessage.title) || wxMessage.title.getBytes().length < 512) {
                this.mTitle = "分享到微信";
            } else {
                wxMessage.title = new String(wxMessage.title.getBytes(), 0, 512);
            }
            if (!TextUtils.isEmpty(wxMessage.description) && wxMessage.description.getBytes().length >= 1024) {
                wxMessage.description = new String(wxMessage.description.getBytes(), 0, 1024);
            }
        }
        return wxMessage;
    }

    private WXMediaMessage buildEmojiParams() {
        UMEmoji emoji = this.mShareContent.mMedia;
        UMImage image = emoji.mSrcImage;
        String path = image.asFileImage().toString();
        WXEmojiObject wxEmojiObject = new WXEmojiObject();
        if (emoji.mSrcImage.isUrlMedia()) {
            path = BitmapUtils.getFileName(image.toUrl());
            if (!new File(path).exists()) {
                BitmapUtils.loadImage(image.toUrl(), 150, 150);
            }
        }
        wxEmojiObject.emojiPath = path;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wxEmojiObject;
        if (emoji.getThumbImage() != null) {
            msg.thumbData = emoji.mThumb.toByte();
        } else if (TextUtils.isEmpty(emoji.getThumb())) {
            msg.thumbData = emoji.mSrcImage.toByte();
        } else {
            Bitmap bitmap = BitmapUtils.loadImage(emoji.getThumb(), 150, 150);
            msg.thumbData = BitmapUtils.bitmap2Bytes(bitmap);
            bitmap.recycle();
        }
        msg.title = this.mTitle;
        msg.description = this.mShareContent.mText;
        return msg;
    }

    private WXMediaMessage buildMusicParams() {
        UMusic umusic = this.mShareContent.mMedia;
        WXMusicObject music = new WXMusicObject();
        if (!TextUtils.isEmpty(umusic.getTargetUrl())) {
            music.musicUrl = umusic.getTargetUrl();
        } else if (TextUtils.isEmpty(this.mShareContent.mTargetUrl)) {
            music.musicUrl = "http://wsq.umeng.com";
        } else {
            music.musicUrl = this.mShareContent.mTargetUrl;
        }
        music.musicDataUrl = umusic.toUrl();
        if (!TextUtils.isEmpty(umusic.getLowBandDataUrl())) {
            music.musicLowBandDataUrl = umusic.getLowBandDataUrl();
        }
        if (!TextUtils.isEmpty(umusic.getLowBandUrl())) {
            music.musicLowBandUrl = umusic.getLowBandUrl();
        }
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        if (!TextUtils.isEmpty(umusic.getTitle())) {
            msg.title = umusic.getTitle();
        } else if (TextUtils.isEmpty(this.mShareContent.mTitle)) {
            msg.title = "分享音频";
        } else {
            msg.title = this.mShareContent.mTitle;
        }
        msg.description = this.mShareContent.mText;
        msg.mediaObject = music;
        if (!(umusic.getThumb() == null || ("".equals(umusic.getThumb()) && umusic.getThumb() == null))) {
            byte[] data = null;
            if (umusic.getThumbImage() != null) {
                data = umusic.getThumbImage().asBinImage();
            } else if (!TextUtils.isEmpty(umusic.getThumb())) {
                data = new UMImage(ContextUtil.getContext(), umusic.getThumb()).asBinImage();
            }
            if (data != null) {
                Log.m4545d("share with thumb");
                msg.thumbData = data;
            }
        }
        return msg;
    }

    private WXMediaMessage buildFileParams() {
        WXFileObject textObj = new WXFileObject();
        textObj.fileData = SocializeUtils.File2byte(this.mShareContent.file);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = this.mShareContent.mText;
        msg.title = this.mTitle;
        return msg;
    }

    private WXMediaMessage buildAppParams() {
        WXAppExtendObject apObj = new WXAppExtendObject();
        apObj.extInfo = "sssssss";
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = apObj;
        msg.description = this.mShareContent.mText;
        msg.title = this.mTitle;
        return msg;
    }

    private WXMediaMessage buildTextParams() {
        WXTextObject textObj = new WXTextObject();
        textObj.text = this.mShareContent.mText;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = this.mShareContent.mText;
        msg.title = this.mTitle;
        return msg;
    }

    private WXMediaMessage buildImageParams() {
        UMImage img = this.mShareContent.mMedia;
        WXImageObject imgObj = new WXImageObject();
        WXMediaMessage msg = new WXMediaMessage();
        imgObj.imageData = img.asBinImage();
        byte[] thumbData;
        if (img.getThumbImage() != null) {
            msg.thumbData = img.getThumbImage().asBinImage();
            thumbData = msg.thumbData;
            if (thumbData != null && thumbData.length > 24576) {
                msg.thumbData = BitmapUtils.compressBitmap(thumbData, 24576);
            }
        } else {
            msg.thumbData = img.asBinImage();
            thumbData = msg.thumbData;
            if (thumbData != null && thumbData.length > 24576) {
                msg.thumbData = BitmapUtils.compressBitmap(thumbData, 24576);
            }
        }
        msg.mediaObject = imgObj;
        return msg;
    }

    private WXMediaMessage buildVideoParams() {
        UMVideo uvideo = this.mShareContent.mMedia;
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = uvideo.toUrl();
        if (!TextUtils.isEmpty(uvideo.getLowBandUrl())) {
            video.videoLowBandUrl = uvideo.getLowBandUrl();
        }
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = video;
        if (!TextUtils.isEmpty(uvideo.getTitle())) {
            msg.title = uvideo.getTitle();
        } else if (TextUtils.isEmpty(this.mShareContent.mTargetUrl)) {
            msg.title = "分享视频";
        } else {
            msg.title = this.mShareContent.mTargetUrl;
        }
        msg.description = this.mShareContent.mText;
        byte[] thumbData = null;
        if (!TextUtils.isEmpty(uvideo.getThumb())) {
            thumbData = new UMImage(ContextUtil.getContext(), uvideo.getThumb()).asBinImage();
        } else if (uvideo.getThumbImage() != null) {
            thumbData = uvideo.getThumbImage().asBinImage();
        }
        if (thumbData != null && thumbData.length > 0) {
            msg.thumbData = thumbData;
        }
        return msg;
    }

    private WXMediaMessage buildTextImageParams() {
        UMImage img = this.mShareContent.mMedia;
        if (TextUtils.isEmpty(this.mTargetUrl)) {
            this.mTargetUrl = "http://www.umeng.com";
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = this.mTargetUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.title = this.mTitle;
        msg.description = this.mShareContent.mText;
        msg.mediaObject = webpage;
        msg.thumbData = img.asBinImage();
        return msg;
    }

    private WXMediaMessage buildUrlParams() {
        UMImage img = this.mShareContent.mMedia;
        if (TextUtils.isEmpty(this.mTargetUrl)) {
            this.mTargetUrl = "http://www.umeng.com";
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = this.mTargetUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.title = this.mTitle;
        msg.description = this.mShareContent.mText;
        msg.mediaObject = webpage;
        if (img != null) {
            msg.thumbData = img.asBinImage();
            byte[] thumbData = msg.thumbData;
            if (thumbData != null && thumbData.length > 18432) {
                msg.thumbData = BitmapUtils.compressBitmap(thumbData, 18432);
            }
        }
        return msg;
    }
}
