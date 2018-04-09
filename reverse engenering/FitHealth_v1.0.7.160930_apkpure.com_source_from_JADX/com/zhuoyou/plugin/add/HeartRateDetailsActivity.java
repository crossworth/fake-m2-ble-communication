package com.zhuoyou.plugin.add;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.fithealth.running.R;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.view.AnimeUtils;

public class HeartRateDetailsActivity extends Activity implements OnClickListener {
    public static final int ANIMATION_MOVE = 289;
    private String count;
    private String date;
    private Button delete_heartreat_details;
    private ImageView details;
    private TextView heartreat_count;
    private TextView heartreat_date;
    private TextView heartreat_time;
    private long id;
    private ImageView imageview_sportline;
    private ImageView mBack;
    private RelativeLayout relativeLayout;
    private ImageView sport_image;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private Typeface tf;
    private String time;
    private ImageView title_share;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heartrate_details);
        this.tf = Typeface.createFromAsset(getAssets(), "font/cmtattoodragon.ttf");
        this.id = getIntent().getLongExtra("heart_rate_id", 0);
        this.count = getIntent().getStringExtra(DataBaseContants.HEART_RATE_COUNT);
        this.date = getIntent().getStringExtra("heart_rate_date");
        this.time = getIntent().getStringExtra(DataBaseContants.HEART_RATE_TIME);
        initView();
        setData();
        MotionCalculations();
    }

    private void initView() {
        this.relativeLayout = (RelativeLayout) findViewById(R.id.back);
        this.relativeLayout.setOnClickListener(this);
        this.mBack = (ImageView) findViewById(R.id.back_m);
        this.mBack.setOnClickListener(this);
        this.title_share = (ImageView) findViewById(R.id.title_share);
        this.title_share.setOnClickListener(this);
        this.heartreat_count = (TextView) findViewById(R.id.heartreat_count);
        this.heartreat_count.setTypeface(this.tf);
        this.heartreat_date = (TextView) findViewById(R.id.heartreat_date);
        this.heartreat_time = (TextView) findViewById(R.id.heartreat_time);
        this.details = (ImageView) findViewById(R.id.details);
        this.details.setOnClickListener(this);
        this.delete_heartreat_details = (Button) findViewById(R.id.delete_heartreat_details);
        this.delete_heartreat_details.setOnClickListener(this);
        this.imageview_sportline = (ImageView) findViewById(R.id.imageview_sportline);
        this.text1 = (TextView) findViewById(R.id.text1);
        this.text2 = (TextView) findViewById(R.id.text2);
        this.text3 = (TextView) findViewById(R.id.text3);
        this.text4 = (TextView) findViewById(R.id.text4);
        this.sport_image = (ImageView) findViewById(R.id.sport_image);
    }

    private void setData() {
        if (this.count != null && this.date != null && this.time != null) {
            this.heartreat_count.setText(this.count);
            this.heartreat_date.setText(this.date);
            this.heartreat_time.setText(this.time);
        }
    }

    private void MotionCalculations() {
        int UserAge = Tools.getUserAge(this);
        int Slight_Movement = (int) (((double) (220 - UserAge)) * 0.7d);
        int Aerobic_Exercise = (int) (((double) (220 - UserAge)) * 0.85d);
        this.text1.setText(((int) (((double) (220 - UserAge)) * HeatmapTileProvider.DEFAULT_OPACITY)) + "");
        this.text2.setText(Slight_Movement + "");
        this.text3.setText(Aerobic_Exercise + "");
        this.text4.setText((Aerobic_Exercise + 44) + "");
        int text1Data = Integer.parseInt(this.text1.getText().toString());
        int text2Data = Integer.parseInt(this.text2.getText().toString());
        int text3Data = Integer.parseInt(this.text3.getText().toString());
        int text4Data = Integer.parseInt(this.text4.getText().toString());
        float totalWidth = (float) (this.imageview_sportline.getDrawable().getIntrinsicWidth() / 4);
        float imgWidth = (float) this.sport_image.getDrawable().getIntrinsicWidth();
        try {
            if (this.count != null) {
                float offset;
                int heart_count = Integer.parseInt(this.count);
                if (heart_count <= text1Data) {
                    this.sport_image.setImageResource(R.drawable.sport_1);
                    offset = (((float) heart_count) * totalWidth) / ((float) text1Data);
                } else if (heart_count <= text2Data) {
                    this.sport_image.setImageResource(R.drawable.sport_2);
                    offset = ((2.0f * totalWidth) * ((float) heart_count)) / ((float) text2Data);
                } else if (heart_count <= text3Data) {
                    this.sport_image.setImageResource(R.drawable.sport_3);
                    offset = ((3.0f * totalWidth) * ((float) heart_count)) / ((float) text3Data);
                } else {
                    this.sport_image.setImageResource(R.drawable.sport_4);
                    offset = ((4.0f * totalWidth) * ((float) heart_count)) / ((float) text4Data);
                }
                AnimeUtils.playTranstionAnime(this.sport_image, offset - imgWidth, 3000);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                finish();
                return;
            case R.id.back_m:
                finish();
                return;
            case R.id.details:
                intent = new Intent();
                intent.setClass(this, DetailsDescriptionActivity.class);
                startActivity(intent);
                return;
            case R.id.title_share:
                intent = new Intent();
                intent.putExtra(DataBaseContants.HEART_RATE_COUNT, this.count);
                intent.putExtra("heart_rate_date", this.date);
                intent.putExtra(DataBaseContants.HEART_RATE_TIME, this.time);
                intent.setClass(this, HeartRateShareActivity.class);
                startActivity(intent);
                return;
            case R.id.delete_heartreat_details:
                deleteDateBaseHeartRate();
                return;
            default:
                return;
        }
    }

    private void deleteDateBaseHeartRate() {
        ContentResolver cr = getContentResolver();
        cr.delete(DataBaseContants.CONTENT_URI, "_id = ?", new String[]{String.valueOf(this.id)});
        ContentValues values = new ContentValues();
        values.put(DataBaseContants.DELETE_VALUE, Long.valueOf(this.id));
        cr.insert(DataBaseContants.CONTENT_DELETE_URI, values);
        finish();
    }

    protected void onResume() {
        super.onResume();
    }
}
