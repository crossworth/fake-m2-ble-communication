package com.tencent.wxop.stat;

import android.app.ListActivity;

public class EasyListActivity extends ListActivity {
    protected void onPause() {
        super.onPause();
        C0896e.m2995m(this);
    }

    protected void onResume() {
        super.onResume();
        C0896e.m2994l(this);
    }
}
