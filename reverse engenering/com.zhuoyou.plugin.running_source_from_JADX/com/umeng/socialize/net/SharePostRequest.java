package com.umeng.socialize.net;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.common.ImageFormat;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.URequest.FilePair;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.Map;

public class SharePostRequest extends SocializeRequest {
    private static final String f4966a = "/share/add/";
    private static final int f4967b = 9;
    private String f4968c;
    private String f4969d;
    private ShareContent f4970e;

    public SharePostRequest(Context context, String str, String str2, ShareContent shareContent) {
        super(context, "", SocializeReseponse.class, 9, RequestMethod.POST);
        this.mContext = context;
        this.f4968c = str;
        this.f4969d = str2;
        this.f4970e = shareContent;
    }

    public void onPrepareRequest() {
        addStringParams("to", this.f4968c);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_COMMENT_TEXT, this.f4970e.mText);
        addStringParams("usid", this.f4969d);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_TITLE, this.f4970e.mTitle);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_AK, SocializeUtils.getAppkey(this.mContext));
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_KEY, Config.EntityKey);
        addMediaParams(this.f4970e.mMedia);
    }

    protected String getPath() {
        return f4966a + SocializeUtils.getAppkey(this.mContext) + "/" + Config.EntityKey + "/";
    }

    public Map<String, FilePair> getFilePair() {
        if (this.f4970e == null || this.f4970e.mMedia == null || this.f4970e.mMedia.isUrlMedia()) {
            return super.getFilePair();
        }
        Map<String, FilePair> filePair = super.getFilePair();
        if (this.f4970e.mMedia instanceof UMImage) {
            UMImage uMImage = (UMImage) this.f4970e.mMedia;
            uMImage.asFileImage().getPath();
            byte[] asBinImage = uMImage.asBinImage();
            String checkFormat = ImageFormat.checkFormat(asBinImage);
            if (TextUtils.isEmpty(checkFormat)) {
                checkFormat = "png";
            }
            filePair.put(SocializeProtocolConstants.PROTOCOL_KEY_IMAGE, new FilePair((System.currentTimeMillis() + "") + "." + checkFormat, asBinImage));
        }
        return filePair;
    }
}
