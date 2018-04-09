package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;

public class HeartHintActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!HeartHintActivity.class.desiredAssertionStatus());
    public static final String KEY_SHOW_NOT_SHOW = "key_show_not_show";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_heart_hint);
        boolean showNotShowButton = getIntent().getBooleanExtra(KEY_SHOW_NOT_SHOW, true);
        TextView tvNotShow = (TextView) findViewById(C1680R.id.btn_no_remind);
        if ($assertionsDisabled || tvNotShow != null) {
            tvNotShow.setVisibility(showNotShowButton ? 0 : 8);
            return;
        }
        throw new AssertionError();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_know:
                finish();
                return;
            case C1680R.id.btn_no_remind:
                SPUtils.setShowHeartHint(false);
                finish();
                return;
            default:
                return;
        }
    }
}
