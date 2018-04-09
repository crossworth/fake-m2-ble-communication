package com.zhuoyou.plugin.mainFrame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.add.SportTimePopupWindow;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.SleepBean;
import com.zhuoyou.plugin.running.SleepItem;
import com.zhuoyou.plugin.view.BarChartSleep;
import java.text.DecimalFormat;
import java.util.List;

public class SleepDetailActivity extends Activity implements OnClickListener {
    private Handler handler = new C13103();
    private SleepItem item;
    private LinearLayout mBarchartLayout;
    private Context mCtx = RunningApp.getInstance().getApplicationContext();
    private ImageView mSleepLine;
    private RelativeLayout rLayout;
    private SportTimePopupWindow startPopuWindow;
    private List<SleepBean> turnData;
    private TextView tv_DeepSleepTime;
    private TextView tv_GoSleep;
    private TextView tv_GoSleepTime;
    private TextView tv_LightSleepTime;
    private TextView tv_SleepRanger;
    private TextView tv_SleepText;
    private TextView tv_SleepTime;
    private TextView tv_WakeUp;
    private TextView tv_WakeUpTime;

    class C13103 extends Handler {
        C13103() {
        }

        public void handleMessage(Message msg) {
            Log.i("hello", "msg:" + msg.what);
            switch (msg.what) {
                case 1:
                    SleepBean bean = msg.obj;
                    if (bean.isDeep()) {
                        SleepDetailActivity.this.tv_SleepText.setText(R.string.deep_sleep);
                        SleepDetailActivity.this.tv_SleepRanger.setText(bean.getStartTime() + " - " + bean.getEndTime());
                        SleepDetailActivity.this.mSleepLine.setVisibility(0);
                        return;
                    }
                    SleepDetailActivity.this.tv_SleepText.setText(R.string.light_sleep);
                    SleepDetailActivity.this.tv_SleepRanger.setText(bean.getStartTime() + " - " + bean.getEndTime());
                    SleepDetailActivity.this.mSleepLine.setVisibility(0);
                    return;
                case 2:
                    SleepDetailActivity.this.tv_SleepText.setText("");
                    SleepDetailActivity.this.tv_SleepRanger.setText("");
                    SleepDetailActivity.this.mSleepLine.setVisibility(8);
                    return;
                default:
                    return;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_detail_activity);
        findView();
        initData();
        setBarView();
        this.tv_SleepText.setText("");
        this.tv_SleepRanger.setText("");
    }

    private void findView() {
        this.rLayout = (RelativeLayout) findViewById(R.id.layout_sleep);
        this.tv_SleepText = (TextView) findViewById(R.id.sleep_text);
        this.tv_SleepRanger = (TextView) findViewById(R.id.tv_SleepRanger);
        this.mSleepLine = (ImageView) findViewById(R.id.sleep_line);
        this.tv_GoSleep = (TextView) findViewById(R.id.tv_GoSleep);
        this.tv_WakeUp = (TextView) findViewById(R.id.tv_WakeUp);
        this.tv_SleepTime = (TextView) findViewById(R.id.tv_SleepTime);
        this.tv_DeepSleepTime = (TextView) findViewById(R.id.tv_DeepSleepTime);
        this.tv_LightSleepTime = (TextView) findViewById(R.id.tv_LightSleepTime);
        this.tv_GoSleepTime = (TextView) findViewById(R.id.tv_GoSleepTime);
        this.tv_WakeUpTime = (TextView) findViewById(R.id.tv_WakeUpTime);
        this.mBarchartLayout = (LinearLayout) findViewById(R.id.layout_sleep_barchart_draw);
    }

    private void initData() {
        this.item = (SleepItem) getIntent().getSerializableExtra("item");
        this.turnData = this.item.getData();
        int sleepTime = this.item.getmSleepT() / 60;
        this.tv_SleepTime.setText((sleepTime / 60) + this.mCtx.getString(R.string.hour) + (sleepTime % 60) + this.mCtx.getString(R.string.minute));
        int deepTime = this.item.getmDSleepT() / 60;
        this.tv_DeepSleepTime.setText((deepTime / 60) + this.mCtx.getString(R.string.hour) + (deepTime % 60) + this.mCtx.getString(R.string.minute));
        int lightTime = sleepTime - deepTime;
        this.tv_LightSleepTime.setText((lightTime / 60) + this.mCtx.getString(R.string.hour) + (lightTime % 60) + this.mCtx.getString(R.string.minute));
        String startTime = this.item.getmStartT();
        String endTime = this.item.getmEndT();
        this.tv_SleepRanger.setText(startTime + SocializeConstants.OP_DIVIDER_MINUS + endTime);
        this.tv_GoSleep.setText(startTime);
        this.tv_WakeUp.setText(endTime);
        this.tv_GoSleepTime.setText(startTime);
        this.tv_WakeUpTime.setText(endTime);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_m:
                finish();
                return;
            case R.id.title_share:
                Intent intent = new Intent(this, SleepShareActivity.class);
                intent.putExtra("item", this.item);
                startActivity(intent);
                return;
            default:
                return;
        }
    }

    private void showPopupWindow1() {
        final DecimalFormat mFormat = new DecimalFormat("#00");
        String[] time = ((String) this.tv_GoSleepTime.getText()).split(":");
        final LayoutParams lp = getWindow().getAttributes();
        this.startPopuWindow = new SportTimePopupWindow(this, Integer.valueOf(time[0]).intValue(), Integer.valueOf(time[1]).intValue());
        this.startPopuWindow.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.startPopuWindow.showAtLocation(this.rLayout, 81, 0, 0);
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        this.startPopuWindow.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                lp.alpha = 1.0f;
                SleepDetailActivity.this.getWindow().setAttributes(lp);
                int time = SleepDetailActivity.this.startPopuWindow.getStartTime();
                SleepDetailActivity.this.tv_GoSleepTime.setText(mFormat.format((long) (time / 60)) + ":" + mFormat.format((long) (time % 60)));
            }
        });
    }

    private void showPopupWindow2() {
        final DecimalFormat mFormat = new DecimalFormat("#00");
        String[] time = ((String) this.tv_WakeUpTime.getText()).split(":");
        final LayoutParams lp = getWindow().getAttributes();
        this.startPopuWindow = new SportTimePopupWindow(this, Integer.valueOf(time[0]).intValue(), Integer.valueOf(time[1]).intValue());
        this.startPopuWindow.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.startPopuWindow.showAtLocation(this.rLayout, 81, 0, 0);
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        this.startPopuWindow.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                lp.alpha = 1.0f;
                SleepDetailActivity.this.getWindow().setAttributes(lp);
                int time = SleepDetailActivity.this.startPopuWindow.getStartTime();
                SleepDetailActivity.this.tv_WakeUpTime.setText(mFormat.format((long) (time / 60)) + ":" + mFormat.format((long) (time % 60)));
            }
        });
    }

    private void setBarView() {
        if (this.turnData != null && this.turnData.size() > 0) {
            BarChartSleep bar = new BarChartSleep(this, this.item, this.handler);
            bar.setLayoutParams(this.mBarchartLayout.getLayoutParams());
            this.mBarchartLayout.removeAllViews();
            this.mBarchartLayout.addView(bar);
            bar.invalidate();
        }
    }
}
