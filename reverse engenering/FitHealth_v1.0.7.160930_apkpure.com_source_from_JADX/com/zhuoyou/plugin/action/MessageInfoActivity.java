package com.zhuoyou.plugin.action;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class MessageInfoActivity extends Activity {
    private String content;
    private TextView msgContent;

    class C10941 implements OnClickListener {
        C10941() {
        }

        public void onClick(View v) {
            MessageInfoActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_info_layout);
        this.content = getIntent().getStringExtra("msg_content");
        ((TextView) findViewById(R.id.title)).setText(R.string.sys_info);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C10941());
        this.msgContent = (TextView) findViewById(R.id.content);
        this.msgContent.setText(this.content);
    }

    protected void onResume() {
        super.onResume();
    }
}
