package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WaterIntakeActivity extends Activity implements OnClickListener {
    private LinearLayout IntakeInfomation;
    private int f3491i = 0;
    private RelativeLayout im_back;
    private ImageView mAddButton;
    private ColorLineView mColorLine;
    private Boolean mEnable;
    private ImageView mImage;
    private ImageView mMinusButton;
    private TextView mOpenWaterIntakeTextView;
    private SharedPreferences mSharedPres;
    private TextView mTitle;
    private WaterIntakeLinearLayout mWaterLayout;
    private WavesAnimView mWavesVies;

    class C14221 implements OnClickListener {
        C14221() {
        }

        public void onClick(View v) {
            WaterIntakeActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.mSharedPres = getSharedPreferences("TestResult", 2);
        this.f3491i = this.mSharedPres.getInt("water_num", 0);
        this.mEnable = Boolean.valueOf(this.mSharedPres.getBoolean("warn_enable", false));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_intake_view);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mTitle.setText(R.string.water_intake_title);
        this.mColorLine = (ColorLineView) findViewById(R.id.water_intake_view);
        this.mColorLine.setNumber(this.f3491i);
        this.mWavesVies = (WavesAnimView) findViewById(R.id.mVideoView);
        this.mWavesVies.setStartPointY(this.f3491i);
        this.mWaterLayout = (WaterIntakeLinearLayout) findViewById(R.id.water_intake_number);
        this.mWaterLayout.setWaterNumber(this.f3491i);
        this.mAddButton = (ImageView) findViewById(R.id.add_water_intake);
        this.mMinusButton = (ImageView) findViewById(R.id.minus_water_intake);
        this.mAddButton.setOnClickListener(this);
        this.mMinusButton.setOnClickListener(this);
        this.mOpenWaterIntakeTextView = (TextView) findViewById(R.id.open_water_intake);
        this.mImage = (ImageView) findViewById(R.id.warn_enable);
        if (this.mEnable.booleanValue()) {
            this.mOpenWaterIntakeTextView.setText(R.string.open_water_intake);
            this.mImage.setImageResource(R.drawable.warn_on);
        } else {
            this.mImage.setImageResource(R.drawable.warn_off);
            this.mOpenWaterIntakeTextView.setText(R.string.close_water_intake);
        }
        this.IntakeInfomation = (LinearLayout) findViewById(R.id.intake_infomation);
        this.IntakeInfomation.setOnClickListener(this);
        this.mImage.setOnClickListener(this);
        this.im_back = (RelativeLayout) findViewById(R.id.back);
        this.im_back.setOnClickListener(new C14221());
    }

    protected void onResume() {
        super.onResume();
        this.mSharedPres = getSharedPreferences("TestResult", 2);
        this.f3491i = this.mSharedPres.getInt("water_num", 0);
        this.mEnable = Boolean.valueOf(this.mSharedPres.getBoolean("warn_enable", false));
        updateOnOffImageView(this.mImage, this.mEnable);
        openWater_Warn(this.mEnable);
    }

    public void onClick(View v) {
        boolean z = true;
        switch (v.getId()) {
            case R.id.minus_water_intake:
                if (this.f3491i > 0) {
                    this.f3491i--;
                }
                this.mColorLine.setNumber(this.f3491i);
                this.mWaterLayout.setWaterNumber(this.f3491i);
                this.mWavesVies.setStartPointY(this.f3491i);
                break;
            case R.id.add_water_intake:
                if (this.f3491i >= 0 && this.f3491i < 99) {
                    this.f3491i++;
                }
                this.mColorLine.setNumber(this.f3491i);
                this.mWaterLayout.setWaterNumber(this.f3491i);
                this.mWavesVies.setStartPointY(this.f3491i);
                break;
            case R.id.intake_infomation:
                startActivity(new Intent(this, WaterIntakeInfoActivity.class));
                break;
            case R.id.warn_enable:
                if (WaterIntakeUtils.isWarnTime(getApplicationContext(), System.currentTimeMillis())) {
                    if (this.mEnable.booleanValue()) {
                        z = false;
                    }
                    this.mEnable = Boolean.valueOf(z);
                    openWater_Warn(this.mEnable);
                    updateOnOffImageView(this.mImage, this.mEnable);
                } else {
                    if (!this.mEnable.booleanValue()) {
                        Toast.makeText(this, R.string.go_to_sleep, 1).show();
                    }
                    this.mEnable = Boolean.valueOf(false);
                }
                openWater_Warn(this.mEnable);
                updateOnOffImageView(this.mImage, this.mEnable);
                break;
        }
        Editor editor = this.mSharedPres.edit();
        editor.putInt("water_num", this.f3491i);
        editor.putBoolean("warn_enable", this.mEnable.booleanValue());
        editor.commit();
    }

    private void openWater_Warn(Boolean mEnable2) {
        Intent[] intents = new Intent[8];
        PendingIntent[] pendIntents = new PendingIntent[8];
        for (int i = 0; i < pendIntents.length; i++) {
            intents[i] = new Intent("Drink_Water_Warn");
            Bundle bundle = new Bundle();
            bundle.putInt("key", i);
            intents[i].putExtras(bundle);
            pendIntents[i] = PendingIntent.getBroadcast(getApplicationContext(), i, intents[i], 134217728);
        }
        long current = System.currentTimeMillis();
        Log.v("renjing", "firstime" + current);
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        String[] times = new String[]{"0700", "0840", "1000", "1130", "1430", "1600", "1800", "2200"};
        if (mEnable2.booleanValue()) {
            startRepeat(WaterIntakeUtils.warnTime(getApplicationContext(), TimetoMini(times), Long.valueOf(current)), pendIntents);
            return;
        }
        for (PendingIntent cancel : pendIntents) {
            am.cancel(cancel);
        }
    }

    public Long[] TimetoMini(String[] times) {
        Long[] temp = new Long[8];
        SimpleDateFormat dd = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < times.length; i++) {
            String today = dd.format(new Date()) + times[i];
            Log.i("zhaojunhui", "time string is " + today);
            long millionSeconds = 0;
            try {
                millionSeconds = new SimpleDateFormat("yyyyMMddhhmm").parse(today).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            temp[i] = Long.valueOf(millionSeconds);
        }
        return temp;
    }

    public void startRepeat(Long[] time, PendingIntent[] pendIntents) {
        Calendar mCalendar = Calendar.getInstance();
        for (int i = 0; i < time.length; i++) {
            AlarmManager am = (AlarmManager) getSystemService("alarm");
            mCalendar.setTimeInMillis(time[i].longValue());
            am.set(0, time[i].longValue(), pendIntents[i]);
        }
    }

    private void updateOnOffImageView(ImageView mImage2, Boolean mEnable2) {
        if (mEnable2.booleanValue()) {
            mImage2.setImageResource(R.drawable.warn_on);
            this.mOpenWaterIntakeTextView.setText(R.string.open_water_intake);
            return;
        }
        mImage2.setImageResource(R.drawable.warn_off);
        this.mOpenWaterIntakeTextView.setText(R.string.close_water_intake);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
