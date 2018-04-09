package com.zhuoyou.plugin.component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.fithealth.running.R;

public class AlarmDateActivity extends Activity implements OnClickListener {
    private boolean friday;
    private ImageView imageFriday;
    private ImageView imageMonday;
    private ImageView imageSaturday;
    private ImageView imageSunday;
    private ImageView imageThursday;
    private ImageView imageTuesday;
    private ImageView imageWednesday;
    private RelativeLayout layoutFriday;
    private RelativeLayout layoutMonday;
    private RelativeLayout layoutSaturday;
    private RelativeLayout layoutSunday;
    private RelativeLayout layoutThursday;
    private RelativeLayout layoutTuesday;
    private RelativeLayout layoutWednesday;
    private AlarmBean mBean;
    private boolean monday;
    private boolean saturday;
    private boolean sunday;
    private boolean thursday;
    private boolean tuesday;
    private boolean wednesday;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_date_activity);
        this.mBean = (AlarmBean) getIntent().getSerializableExtra("alarmbean");
        if (this.mBean == null) {
            finish();
        }
        findView();
        initData();
        setImageView();
    }

    private void findView() {
        this.layoutMonday = (RelativeLayout) findViewById(R.id.alarm_layout_monday);
        this.layoutTuesday = (RelativeLayout) findViewById(R.id.alarm_layout_tuesday);
        this.layoutWednesday = (RelativeLayout) findViewById(R.id.alarm_layout_wednesday);
        this.layoutThursday = (RelativeLayout) findViewById(R.id.alarm_layout_thursday);
        this.layoutFriday = (RelativeLayout) findViewById(R.id.alarm_layout_friday);
        this.layoutSaturday = (RelativeLayout) findViewById(R.id.alarm_layout_saturday);
        this.layoutSunday = (RelativeLayout) findViewById(R.id.alarm_layout_sunday);
        this.imageMonday = (ImageView) findViewById(R.id.alarm_imageview_monday);
        this.imageTuesday = (ImageView) findViewById(R.id.alarm_imageview_tuesday);
        this.imageWednesday = (ImageView) findViewById(R.id.alarm_imageview_wednesday);
        this.imageThursday = (ImageView) findViewById(R.id.alarm_imageview_thursday);
        this.imageFriday = (ImageView) findViewById(R.id.alarm_imageview_friday);
        this.imageSaturday = (ImageView) findViewById(R.id.alarm_imageview_saturday);
        this.imageSunday = (ImageView) findViewById(R.id.alarm_imageview_sunday);
        this.layoutMonday.setOnClickListener(this);
        this.layoutTuesday.setOnClickListener(this);
        this.layoutWednesday.setOnClickListener(this);
        this.layoutThursday.setOnClickListener(this);
        this.layoutFriday.setOnClickListener(this);
        this.layoutSaturday.setOnClickListener(this);
        this.layoutSunday.setOnClickListener(this);
    }

    private void initData() {
        boolean z;
        boolean z2 = true;
        int customs = this.mBean.getCustomData();
        this.monday = (customs % 10) / 1 == 1;
        if ((customs % 100) / 10 == 1) {
            z = true;
        } else {
            z = false;
        }
        this.tuesday = z;
        if ((customs % 1000) / 100 == 1) {
            z = true;
        } else {
            z = false;
        }
        this.wednesday = z;
        if ((customs % 10000) / 1000 == 1) {
            z = true;
        } else {
            z = false;
        }
        this.thursday = z;
        if ((customs % 100000) / 10000 == 1) {
            z = true;
        } else {
            z = false;
        }
        this.friday = z;
        if ((customs % 1000000) / 100000 == 1) {
            z = true;
        } else {
            z = false;
        }
        this.saturday = z;
        if ((customs % 10000000) / 1000000 != 1) {
            z2 = false;
        }
        this.sunday = z2;
    }

    private void setImageView() {
        if (this.monday) {
            this.imageMonday.setVisibility(0);
        } else {
            this.imageMonday.setVisibility(4);
        }
        if (this.tuesday) {
            this.imageTuesday.setVisibility(0);
        } else {
            this.imageTuesday.setVisibility(4);
        }
        if (this.wednesday) {
            this.imageWednesday.setVisibility(0);
        } else {
            this.imageWednesday.setVisibility(4);
        }
        if (this.thursday) {
            this.imageThursday.setVisibility(0);
        } else {
            this.imageThursday.setVisibility(4);
        }
        if (this.friday) {
            this.imageFriday.setVisibility(0);
        } else {
            this.imageFriday.setVisibility(4);
        }
        if (this.saturday) {
            this.imageSaturday.setVisibility(0);
        } else {
            this.imageSaturday.setVisibility(4);
        }
        if (this.sunday) {
            this.imageSunday.setVisibility(0);
        } else {
            this.imageSunday.setVisibility(4);
        }
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

    public void onClick(View view) {
        boolean z = true;
        switch (view.getId()) {
            case R.id.back:
                finishActivity();
                break;
            case R.id.alarm_layout_monday:
                if (this.monday) {
                    z = false;
                }
                this.monday = z;
                break;
            case R.id.alarm_layout_tuesday:
                if (this.tuesday) {
                    z = false;
                }
                this.tuesday = z;
                break;
            case R.id.alarm_layout_wednesday:
                if (this.wednesday) {
                    z = false;
                }
                this.wednesday = z;
                break;
            case R.id.alarm_layout_thursday:
                if (this.thursday) {
                    z = false;
                }
                this.thursday = z;
                break;
            case R.id.alarm_layout_friday:
                if (this.friday) {
                    z = false;
                }
                this.friday = z;
                break;
            case R.id.alarm_layout_saturday:
                if (this.saturday) {
                    z = false;
                }
                this.saturday = z;
                break;
            case R.id.alarm_layout_sunday:
                if (this.sunday) {
                    z = false;
                }
                this.sunday = z;
                break;
        }
        setImageView();
    }

    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity() {
        int res = 0;
        if (this.monday) {
            res = 0 + 1;
        }
        if (this.tuesday) {
            res += 10;
        }
        if (this.wednesday) {
            res += 100;
        }
        if (this.thursday) {
            res += 1000;
        }
        if (this.friday) {
            res += 10000;
        }
        if (this.saturday) {
            res += 100000;
        }
        if (this.sunday) {
            res += 1000000;
        }
        this.mBean.setCustomData(res);
        Intent intent = new Intent();
        intent.putExtra("alarmReturn", this.mBean);
        setResult(100, intent);
        finish();
    }
}
