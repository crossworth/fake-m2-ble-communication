package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.droi.sdk.selfupdate.DroiUpdateListener;
import com.droi.sdk.selfupdate.DroiUpdateResponse;
import com.umeng.socialize.UMShareAPI;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.ShareAppDialog;

public class AboutActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!AboutActivity.class.desiredAssertionStatus());
    private ImageView imgUpdatePoint;
    private Handler mHandler = new Handler();
    private DroiUpdateListener updateListener = new C16861();

    class C16861 implements DroiUpdateListener {
        C16861() {
        }

        public void onUpdateReturned(final int i, DroiUpdateResponse droiUpdateResponse) {
            AboutActivity.this.mHandler.post(new Runnable() {
                public void run() {
                    AboutActivity.this.handlerUpdateResult(i);
                }
            });
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_about);
        TextView tvVersion = (TextView) findViewById(C1680R.id.tv_version_name);
        this.imgUpdatePoint = (ImageView) findViewById(C1680R.id.img_update_point);
        if ($assertionsDisabled || tvVersion != null) {
            tvVersion.setText("V" + Tools.getVersionName());
            DroiUpdate.setUpdateAutoPopup(false);
            DroiUpdate.setUpdateListener(this.updateListener);
            DroiUpdate.update(this);
            return;
        }
        throw new AssertionError();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.mine_item_help:
                startActivity(new Intent(this, HelpActivity.class));
                return;
            case C1680R.id.mine_item_upgrate:
                Tools.makeToast((int) C1680R.string.about_checking_update);
                DroiUpdate.setUpdateAutoPopup(true);
                DroiUpdate.manualUpdate(this);
                return;
            case C1680R.id.share_to_other:
                new ShareAppDialog(this).show();
                return;
            case C1680R.id.mine_item_droi_wechat:
                Tools.jumpToWinXin(this);
                return;
            default:
                return;
        }
    }

    private void handlerUpdateResult(int i) {
        switch (i) {
            case 0:
            case 2:
            case 3:
                this.imgUpdatePoint.setVisibility(8);
                return;
            case 1:
                this.imgUpdatePoint.setVisibility(0);
                return;
            default:
                return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
