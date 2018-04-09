package com.zhuoyou.plugin.bluetooth.attach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.running.PersonalGoal;
import com.zhuoyou.plugin.running.Tools;
import java.text.DecimalFormat;

public class SportTargetReminderActivity extends Activity {
    private boolean isOpen;
    private RelativeLayout mActionBarBackRl;
    private TextView mActionBarLeftTitleTv;
    private PersonalGoal mPersonalGoal;
    private ImageView mSportTargetRemindImg;
    private String sportTargetRemindInfo = "";

    class C11891 implements OnClickListener {
        C11891() {
        }

        public void onClick(View v) {
            SportTargetReminderActivity.this.broadSportTargetInfo(SportTargetReminderActivity.this.sportTargetRemindInfo);
            SportTargetReminderActivity.this.finish();
        }
    }

    class C11902 implements OnClickListener {
        C11902() {
        }

        public void onClick(View v) {
            int i = 1;
            if (SportTargetReminderActivity.this.isOpen) {
                SportTargetReminderActivity.this.mSportTargetRemindImg.setImageResource(R.drawable.warn_on);
                SportTargetReminderActivity.this.isOpen = false;
                Tools.setSportTargetReminderSwitch(true);
            } else {
                SportTargetReminderActivity.this.mSportTargetRemindImg.setImageResource(R.drawable.warn_off);
                SportTargetReminderActivity.this.isOpen = true;
                Tools.setSportTargetReminderSwitch(false);
            }
            SportTargetReminderActivity sportTargetReminderActivity = SportTargetReminderActivity.this;
            StringBuilder append = new StringBuilder().append("2|");
            if (!Tools.getSportTargetReminderSwitch()) {
                i = 0;
            }
            sportTargetReminderActivity.sportTargetRemindInfo = append.append(i).append("|").append(SportTargetReminderActivity.this.decimalFormat(SportTargetReminderActivity.this.mPersonalGoal.getStep())).append("||||||").toString();
            Log.i("hph", "sportTargetRemindInfo=" + SportTargetReminderActivity.this.sportTargetRemindInfo);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_target_reminder);
        initView();
        initData();
    }

    private void initView() {
        this.mActionBarLeftTitleTv = (TextView) findViewById(R.id.title);
        this.mActionBarBackRl = (RelativeLayout) findViewById(R.id.back);
        this.mSportTargetRemindImg = (ImageView) findViewById(R.id.sport_target_remind_img);
    }

    private void initData() {
        this.isOpen = Tools.getSportTargetReminderSwitch();
        this.mPersonalGoal = Tools.getPersonalGoal();
        this.mActionBarLeftTitleTv.setText(R.string.sport_target_reminder);
        this.mActionBarBackRl.setOnClickListener(new C11891());
        if (this.isOpen) {
            this.mSportTargetRemindImg.setImageResource(R.drawable.warn_on);
        } else {
            this.mSportTargetRemindImg.setImageResource(R.drawable.warn_off);
        }
        this.mSportTargetRemindImg.setOnClickListener(new C11902());
        this.sportTargetRemindInfo = "2|" + (Tools.getSportTargetReminderSwitch() ? 1 : 0) + "|" + decimalFormat(this.mPersonalGoal.getStep()) + "||||||";
    }

    public void onBackPressed() {
        super.onBackPressed();
        broadSportTargetInfo(this.sportTargetRemindInfo);
        Log.i("hph", "onBackPressed sportTargetRemindInfo=" + this.sportTargetRemindInfo);
    }

    private void broadSportTargetInfo(String info) {
        Intent intent = new Intent(BleManagerService.ACTION_UPDATE_SEDENTARY_INFO);
        intent.putExtra("sedentary_info", info);
        sendBroadcast(intent);
    }

    private String decimalFormat(int step) {
        return new DecimalFormat("#00000").format((long) step);
    }
}
