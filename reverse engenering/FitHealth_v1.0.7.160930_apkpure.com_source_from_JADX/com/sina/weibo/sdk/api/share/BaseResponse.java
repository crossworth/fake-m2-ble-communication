package com.sina.weibo.sdk.api.share;

import android.os.Bundle;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBConstants.Base;
import com.sina.weibo.sdk.constant.WBConstants.Response;

public abstract class BaseResponse extends Base {
    public int errCode;
    public String errMsg;
    public String reqPackageName;

    public void toBundle(Bundle bundle) {
        bundle.putInt(WBConstants.COMMAND_TYPE_KEY, getType());
        bundle.putInt(Response.ERRCODE, this.errCode);
        bundle.putString(Response.ERRMSG, this.errMsg);
        bundle.putString(WBConstants.TRAN, this.transaction);
    }

    public void fromBundle(Bundle bundle) {
        this.errCode = bundle.getInt(Response.ERRCODE);
        this.errMsg = bundle.getString(Response.ERRMSG);
        this.transaction = bundle.getString(WBConstants.TRAN);
        this.reqPackageName = bundle.getString(Base.APP_PKG);
    }
}
