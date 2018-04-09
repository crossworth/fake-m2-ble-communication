package com.zhuoyou.plugin.resideMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class FrequentlyQuestionsActivity extends Activity implements OnClickListener {
    private TextView tvDropdownTriangle1;
    private TextView tvDropdownTriangle2;
    private TextView tvDropdownTriangle3;
    private TextView tvDropdownTriangle4;
    private TextView tvDropdownTriangle5;
    private TextView tvNewbieGuide;
    private TextView tvNewbieGuide3;
    private TextView tvNewbieGuide4;
    private TextView tvNewbieGuide5;
    private TextView tvSolvep1;
    private TextView tvSolvep2;
    private TextView tvSolvep3;
    private TextView tvSolvep4;
    private TextView tvSolvep5;
    private TextView tvStandbyTime;

    class C13411 implements OnClickListener {
        C13411() {
        }

        public void onClick(View v) {
            FrequentlyQuestionsActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequently_questions);
        initView();
        ((TextView) findViewById(R.id.title)).setText(R.string.asked_questions);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C13411());
    }

    private void initView() {
    }

    public void onClick(View v) {
    }
}
