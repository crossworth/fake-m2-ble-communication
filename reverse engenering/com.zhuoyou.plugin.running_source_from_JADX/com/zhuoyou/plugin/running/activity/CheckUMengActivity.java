package com.zhuoyou.plugin.running.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.umeng.socialize.UmengTool;
import com.zhuoyou.plugin.running.C1680R;

public class CheckUMengActivity extends Activity {
    Button checkAlipayBtn;
    Button checkSinaBtn;
    Button checkWXBtn;
    Button checkpermissionBtn;
    Button checksignBtn;
    Button checkurlBtn;
    TextView contentTv;
    private OnClickListener listener = new C16951();

    class C16951 implements OnClickListener {
        C16951() {
        }

        public void onClick(View v) {
            if (v.getId() == C1680R.id.umeng_sign) {
                UmengTool.getSignature(CheckUMengActivity.this);
            } else if (v.getId() == C1680R.id.umeng_redirecturl) {
                UmengTool.getREDICRECT_URL(CheckUMengActivity.this);
            } else if (v.getId() == C1680R.id.umeng_wx) {
                UmengTool.checkWx(CheckUMengActivity.this);
            } else if (v.getId() == C1680R.id.umeng_sina) {
                UmengTool.checkQQ(CheckUMengActivity.this);
            } else if (v.getId() == C1680R.id.umeng_alipay) {
                UmengTool.checkAlipay(CheckUMengActivity.this);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1680R.layout.activity_check_umeng);
        this.contentTv = (TextView) findViewById(C1680R.id.umeng_text);
        this.checksignBtn = (Button) findViewById(C1680R.id.umeng_sign);
        this.checkurlBtn = (Button) findViewById(C1680R.id.umeng_redirecturl);
        this.checkWXBtn = (Button) findViewById(C1680R.id.umeng_wx);
        this.checkSinaBtn = (Button) findViewById(C1680R.id.umeng_sina);
        this.checkAlipayBtn = (Button) findViewById(C1680R.id.umeng_alipay);
        this.checkpermissionBtn = (Button) findViewById(C1680R.id.umeng_permission);
        this.contentTv.setText("自检工具只是提供给开发者调试使用，上线app不需要该功能");
        this.checksignBtn.setOnClickListener(this.listener);
        this.checkurlBtn.setOnClickListener(this.listener);
        this.checkWXBtn.setOnClickListener(this.listener);
        this.checkSinaBtn.setOnClickListener(this.listener);
        this.checkAlipayBtn.setOnClickListener(this.listener);
    }
}
