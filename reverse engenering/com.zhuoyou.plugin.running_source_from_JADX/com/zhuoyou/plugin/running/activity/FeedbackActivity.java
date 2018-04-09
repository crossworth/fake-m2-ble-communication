package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;

public class FeedbackActivity extends BaseActivity {
    private ImageView imgPhoto;
    private TextView tvName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_feedback);
        this.imgPhoto = (ImageView) findViewById(C1680R.id.img_photo);
        this.tvName = (TextView) findViewById(C1680R.id.tv_nickname);
        updateView();
    }

    private void updateView() {
    }

    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
