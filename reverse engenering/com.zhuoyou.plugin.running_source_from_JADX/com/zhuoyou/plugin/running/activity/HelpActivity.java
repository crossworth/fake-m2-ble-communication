package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.view.View;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;

public class HelpActivity extends BaseActivity {
    private View mItemBody1 = null;
    private View mItemBody2 = null;
    private boolean mItemExpeanded1 = false;
    private boolean mItemExpeanded2 = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_help);
        initView();
    }

    protected void initView() {
        this.mItemBody1 = findViewById(C1680R.id.mine_item_help1_body);
        this.mItemBody2 = findViewById(C1680R.id.mine_item_help2_body);
    }

    public void onClick(View v) {
        boolean z = true;
        int i = 0;
        View view;
        switch (v.getId()) {
            case C1680R.id.mine_item_help1:
                if (this.mItemExpeanded1) {
                    z = false;
                }
                this.mItemExpeanded1 = z;
                view = this.mItemBody1;
                if (!this.mItemExpeanded1) {
                    i = 8;
                }
                view.setVisibility(i);
                return;
            case C1680R.id.mine_item_help2:
                if (this.mItemExpeanded2) {
                    z = false;
                }
                this.mItemExpeanded2 = z;
                view = this.mItemBody2;
                if (!this.mItemExpeanded2) {
                    i = 8;
                }
                view.setVisibility(i);
                return;
            default:
                return;
        }
    }
}
