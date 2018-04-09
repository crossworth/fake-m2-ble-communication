package com.zhuoyou.plugin.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.fithealth.running.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.zhuoyou.plugin.share.ShareToWeixin;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareToWeixin.api.handleIntent(getIntent(), this);
    }

    public void onReq(BaseReq arg0) {
    }

    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case -4:
            case -2:
                break;
            case 0:
                Toast.makeText(this, R.string.share_success, 0).show();
                break;
            default:
                Toast.makeText(this, R.string.share_fail, 0).show();
                break;
        }
        finish();
    }
}
