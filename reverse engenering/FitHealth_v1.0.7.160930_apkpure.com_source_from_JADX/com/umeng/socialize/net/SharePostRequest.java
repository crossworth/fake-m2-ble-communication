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
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.Map;

public class SharePostRequest extends SocializeRequest {
    private static final String f5524a = "/share/add/";
    private static final int f5525b = 9;
    private String f5526c;
    private String f5527d;
    private ShareContent f5528e;

    public SharePostRequest(Context context, String str, String str2, ShareContent shareContent) {
        super(context, "", SocializeReseponse.class, 9, RequestMethod.POST);
        this.mContext = context;
        this.f5526c = str;
        this.f5527d = str2;
        this.f5528e = shareContent;
        Log.m3250e("xxxx content=" + shareContent.mMedia);
    }

    public void onPrepareRequest() {
        addStringParams("to", this.f5526c);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_COMMENT_TEXT, this.f5528e.mText);
        addStringParams("usid", this.f5527d);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_AK, SocializeUtils.getAppkey(this.mContext));
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_KEY, Config.EntityKey);
        if (this.f5528e.mLocation != null) {
            addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_LOCATION, this.f5528e.mLocation.toString());
        }
        addMediaParams(this.f5528e.mMedia);
    }

    protected String getPath() {
        return f5524a + SocializeUtils.getAppkey(this.mContext) + "/" + Config.EntityKey + "/";
    }

    public Map<String, FilePair> getFilePair() {
        if (this.f5528e == null || this.f5528e.mMedia == null || this.f5528e.mMedia.isUrlMedia()) {
            return super.getFilePair();
        }
        Map<String, FilePair> filePair = super.getFilePair();
        if (this.f5528e.mMedia instanceof UMImage) {
            UMImage uMImage = (UMImage) this.f5528e.mMedia;
            uMImage.asFileImage().getPath();
            Object asBinImage = uMImage.asBinImage();
            String checkFormat = ImageFormat.checkFormat(asBinImage);
            if (TextUtils.isEmpty(checkFormat)) {
                checkFormat = "png";
            }
            String str = System.currentTimeMillis() + "";
            Log.m3250e("xxxx filedata=" + asBinImage);
            filePair.put(SocializeProtocolConstants.PROTOCOL_KEY_IMAGE, new FilePair(str + "." + checkFormat, asBinImage));
        }
        return filePair;
    }
}
