package com.zhuoyou.plugin.add;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.Tools;

public class AddSports extends Activity implements OnClickListener {
    private String date;
    private DateSelectPopupWindow datePopuWindow;
    private int durationHour;
    private int durationOther;
    private String durationTime;
    private String endTime;
    private boolean hasChanged = false;
    private long id;
    private RelativeLayout im_back;
    private Intent intent;
    private boolean isEdit;
    private int lastTime;
    private Button mButton;
    private LinearLayout rLayout;
    private RelativeLayout rlayout_choseSport;
    private RelativeLayout rlayout_lastTime;
    private RelativeLayout rlayout_startDate;
    private RelativeLayout rlayout_startTime;
    private String[] sportArray = new String[40];
    private ChooseSportPopoWindow sportChose;
    private int sportIndex;
    private int sportNum;
    private String sportStartTime;
    private String sportType;
    private int startHour;
    private int startOther;
    private SportTimePopupWindow startPopuWindow;
    private int startTime;
    private DurationPopupWindow stopPopuWindow;
    private TextView tv_add_sport;
    private TextView tv_last_time;
    private TextView tv_run;
    private TextView tv_start_date;
    private TextView tv_start_time;
    private TextView tv_waste_kll;
    private String wasteCalories;
    private int wasteKll;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sport);
        this.rLayout = (LinearLayout) findViewById(R.id.rlayout);
        this.im_back = (RelativeLayout) findViewById(R.id.back);
        this.im_back.setOnClickListener(this);
        this.mButton = (Button) findViewById(R.id.save);
        this.mButton.setOnClickListener(this);
        this.tv_waste_kll = (TextView) findViewById(R.id.kll_count);
        this.tv_run = (TextView) findViewById(R.id.tv_run);
        this.tv_start_date = (TextView) findViewById(R.id.tv_start_date);
        this.tv_start_time = (TextView) findViewById(R.id.tv_chose_start);
        this.tv_last_time = (TextView) findViewById(R.id.tv_chose_close);
        this.tv_add_sport = (TextView) findViewById(R.id.title);
        this.tv_add_sport.setText(R.string.add_sport);
        this.rlayout_choseSport = (RelativeLayout) findViewById(R.id.rlayout_choseSport);
        this.rlayout_startDate = (RelativeLayout) findViewById(R.id.rlayout_startDate);
        this.rlayout_startTime = (RelativeLayout) findViewById(R.id.rlayout_startTime);
        this.rlayout_lastTime = (RelativeLayout) findViewById(R.id.rlayout_lastTime);
        this.rlayout_choseSport.setOnClickListener(this);
        this.rlayout_startDate.setOnClickListener(this);
        this.rlayout_startTime.setOnClickListener(this);
        this.rlayout_lastTime.setOnClickListener(this);
        this.sportArray = getResources().getStringArray(R.array.whole_sport_type);
        this.intent = getIntent();
        this.sportStartTime = this.intent.getStringExtra("sportStartTime");
        this.date = this.intent.getStringExtra("date");
        this.id = this.intent.getLongExtra("id", 0);
        if (this.date.equals(Tools.getDate(0))) {
            this.tv_start_date.setText(R.string.today);
        } else {
            this.tv_start_date.setText(this.date);
        }
        if (this.sportStartTime != null) {
            this.isEdit = true;
            this.sportNum = this.intent.getIntExtra("sportType", 0);
            this.sportIndex = this.sportNum - 1;
            this.sportType = this.sportArray[this.sportNum - 1];
            this.durationTime = this.intent.getStringExtra("sportDuration");
            this.wasteCalories = this.intent.getStringExtra("wasteCalories");
            getStartIntTime(this.sportStartTime);
            this.wasteKll = Integer.parseInt(this.wasteCalories);
            this.startTime = (this.startHour * 60) + this.startOther;
            this.lastTime = Integer.parseInt(this.durationTime);
            if (this.lastTime >= 60) {
                this.durationHour = this.lastTime / 60;
                this.durationOther = this.lastTime % 60;
                this.tv_last_time.setText(this.durationHour + getResources().getString(R.string.hour) + this.durationOther + getResources().getString(R.string.minute));
            } else {
                this.durationHour = 0;
                this.durationOther = this.lastTime;
                this.tv_last_time.setText(this.durationOther + getResources().getString(R.string.minute));
            }
            this.mButton.setText(R.string.gpsdata_delete);
            this.tv_add_sport.setText(R.string.edit_sport);
            this.tv_run.setText(this.sportType);
            this.tv_start_time.setText(this.sportStartTime);
            this.tv_waste_kll.setText(this.wasteCalories);
            return;
        }
        this.isEdit = false;
        this.sportIndex = 28;
        this.mButton.setText(R.string.ok);
    }

    public void onClick(View v) {
        LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        final LayoutParams layoutParams;
        switch (v.getId()) {
            case R.id.back:
                finish();
                return;
            case R.id.save:
                if (!this.isEdit) {
                    String start = this.tv_start_time.getText().toString();
                    String last = this.tv_last_time.getText().toString();
                    if (start.equals(getResources().getString(R.string.choice))) {
                        Toast.makeText(this, R.string.ps_starttime, 0).show();
                        return;
                    } else if (last.equals(getResources().getString(R.string.choice))) {
                        Toast.makeText(this, R.string.ps_duration, 0).show();
                        return;
                    } else {
                        this.endTime = getEndTime(this.startTime, this.lastTime);
                        insertDataBaseSportType(this.date, this.sportStartTime, this.sportIndex + 1, this.lastTime + "", this.endTime, this.wasteKll + "", 2, 0);
                        finish();
                        return;
                    }
                } else if (this.hasChanged) {
                    String start_edit = this.tv_start_time.getText().toString();
                    String last_edit = this.tv_last_time.getText().toString();
                    if (start_edit.equals(getResources().getString(R.string.choice))) {
                        Toast.makeText(this, R.string.ps_starttime, 0).show();
                        return;
                    }
                    if (last_edit.equals(getResources().getString(R.string.choice))) {
                        Toast.makeText(this, R.string.ps_duration, 0).show();
                        return;
                    }
                    this.endTime = getEndTime(this.startTime, this.lastTime);
                    updateDateBaseSport(this.sportStartTime, this.sportIndex + 1, this.lastTime + "", this.endTime, this.wasteKll + "", this.date);
                    finish();
                    return;
                } else {
                    deleteDateBaseSport();
                    finish();
                    return;
                }
            case R.id.rlayout_startDate:
                final String finalDate = this.date;
                this.datePopuWindow = new DateSelectPopupWindow(this, finalDate);
                this.datePopuWindow.setColor(-10311101);
                this.datePopuWindow.showAtLocation(this.rLayout, 81, 0, 0);
                getWindow().setAttributes(lp);
                layoutParams = lp;
                this.datePopuWindow.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss() {
                        layoutParams.alpha = 1.0f;
                        AddSports.this.getWindow().setAttributes(layoutParams);
                        AddSports.this.date = AddSports.this.datePopuWindow.getStartDate();
                        if (!AddSports.this.date.equals(finalDate)) {
                            AddSports.this.hasChanged = true;
                            AddSports.this.mButton.setText(R.string.ok);
                        }
                        if (AddSports.this.date.equals(Tools.getDate(0))) {
                            AddSports.this.tv_start_date.setText(R.string.today);
                        } else {
                            AddSports.this.tv_start_date.setText(AddSports.this.date);
                        }
                    }
                });
                return;
            case R.id.rlayout_startTime:
                final int finalStartTime = this.startTime;
                this.startHour = finalStartTime / 60;
                this.startOther = finalStartTime % 60;
                this.startPopuWindow = new SportTimePopupWindow(this, this.startHour, this.startOther);
                this.startPopuWindow.setColor(-10311101);
                this.startPopuWindow.showAtLocation(this.rLayout, 81, 0, 0);
                getWindow().setAttributes(lp);
                layoutParams = lp;
                this.startPopuWindow.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss() {
                        layoutParams.alpha = 1.0f;
                        AddSports.this.getWindow().setAttributes(layoutParams);
                        AddSports.this.startTime = AddSports.this.startPopuWindow.getStartTime();
                        if (finalStartTime != AddSports.this.startTime) {
                            AddSports.this.hasChanged = true;
                            AddSports.this.mButton.setText(R.string.ok);
                        }
                        if (AddSports.this.startTime >= 60) {
                            if (AddSports.this.startTime % 60 < 10) {
                                AddSports.this.sportStartTime = (AddSports.this.startTime / 60) + ":0" + (AddSports.this.startTime % 60);
                            } else {
                                AddSports.this.sportStartTime = (AddSports.this.startTime / 60) + ":" + (AddSports.this.startTime % 60);
                            }
                        } else if (AddSports.this.startTime == 0) {
                            AddSports.this.sportStartTime = "00:00";
                        } else if (AddSports.this.startTime < 10) {
                            AddSports.this.sportStartTime = "00:0" + AddSports.this.startTime;
                        } else {
                            AddSports.this.sportStartTime = "00:" + AddSports.this.startTime;
                        }
                        AddSports.this.tv_start_time.setText(AddSports.this.sportStartTime);
                    }
                });
                return;
            case R.id.rlayout_choseSport:
                final int finalIndex = this.sportIndex;
                this.sportChose = new ChooseSportPopoWindow(this, this.sportArray[finalIndex]);
                this.sportChose.showAtLocation(this.rLayout, 81, 0, 0);
                getWindow().setAttributes(lp);
                layoutParams = lp;
                this.sportChose.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss() {
                        layoutParams.alpha = 1.0f;
                        AddSports.this.getWindow().setAttributes(layoutParams);
                        AddSports.this.sportType = AddSports.this.sportChose.getSport();
                        AddSports.this.sportIndex = Tools.getSportIndex(AddSports.this.sportArray, AddSports.this.sportType);
                        if (finalIndex != AddSports.this.sportIndex) {
                            AddSports.this.hasChanged = true;
                            AddSports.this.mButton.setText(R.string.ok);
                        }
                        AddSports.this.tv_run.setText(AddSports.this.sportType);
                        if (AddSports.this.sportType == null) {
                            AddSports.this.wasteKll = AddSports.this.lastTime * 9;
                            AddSports.this.tv_waste_kll.setText(AddSports.this.wasteKll + "");
                            return;
                        }
                        AddSports.this.wasteKll = Tools.getSportKll(AddSports.this.sportIndex, AddSports.this.lastTime);
                        AddSports.this.tv_waste_kll.setText(AddSports.this.wasteKll + "");
                    }
                });
                return;
            case R.id.rlayout_lastTime:
                final int finalLastTime = this.lastTime;
                this.durationHour = finalLastTime / 60;
                this.durationOther = finalLastTime % 60;
                this.stopPopuWindow = new DurationPopupWindow(this, this.durationHour, this.durationOther);
                this.stopPopuWindow.showAtLocation(this.rLayout, 81, 0, 0);
                getWindow().setAttributes(lp);
                layoutParams = lp;
                this.stopPopuWindow.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss() {
                        layoutParams.alpha = 1.0f;
                        AddSports.this.getWindow().setAttributes(layoutParams);
                        AddSports.this.lastTime = AddSports.this.stopPopuWindow.getLastTime();
                        if (finalLastTime != AddSports.this.lastTime) {
                            AddSports.this.hasChanged = true;
                            AddSports.this.mButton.setText(R.string.ok);
                        }
                        if (AddSports.this.lastTime >= 60) {
                            AddSports.this.tv_last_time.setText((AddSports.this.lastTime / 60) + AddSports.this.getResources().getString(R.string.hour) + (AddSports.this.lastTime % 60) + AddSports.this.getResources().getString(R.string.minute));
                        } else if (AddSports.this.lastTime == 0) {
                            AddSports.this.tv_last_time.setText(R.string.choice);
                        } else {
                            AddSports.this.tv_last_time.setText(AddSports.this.lastTime + AddSports.this.getResources().getString(R.string.minute));
                        }
                        if (AddSports.this.sportType == null) {
                            AddSports.this.wasteKll = AddSports.this.lastTime * 9;
                            AddSports.this.tv_waste_kll.setText(AddSports.this.wasteKll + "");
                            return;
                        }
                        AddSports.this.wasteKll = Tools.getSportKll(AddSports.this.sportIndex, AddSports.this.lastTime);
                        AddSports.this.tv_waste_kll.setText(AddSports.this.wasteKll + "");
                    }
                });
                return;
            default:
                return;
        }
    }

    private void insertDataBaseSportType(String date, String startTime, int sportType, String duration, String endTime, String calories, int type, int statistics) {
        ContentValues runningItem = new ContentValues();
        runningItem.put("_id", Long.valueOf(Tools.getPKL()));
        runningItem.put("date", date);
        runningItem.put(DataBaseContants.TIME_START, startTime);
        runningItem.put(DataBaseContants.TIME_DURATION, duration);
        runningItem.put(DataBaseContants.TIME_END, endTime);
        runningItem.put(DataBaseContants.SPORTS_TYPE, Integer.valueOf(sportType));
        runningItem.put(DataBaseContants.CALORIES, calories);
        runningItem.put("type", Integer.valueOf(type));
        runningItem.put(DataBaseContants.STATISTICS, Integer.valueOf(statistics));
        getContentResolver().insert(DataBaseContants.CONTENT_URI, runningItem);
    }

    private void updateDateBaseSport(String startTime, int sportType, String duration, String endTime, String calories, String date) {
        ContentResolver cr = getContentResolver();
        ContentValues updateValues = new ContentValues();
        Cursor c = cr.query(DataBaseContants.CONTENT_URI, new String[]{DataBaseContants.SYNC_STATE}, "_id = ? ", new String[]{String.valueOf(this.id)}, null);
        if (c.getCount() > 0 && c.moveToFirst()) {
            if (c.getInt(c.getColumnIndex(DataBaseContants.SYNC_STATE)) == 0) {
                updateValues.put("date", date);
                updateValues.put(DataBaseContants.TIME_START, startTime);
                updateValues.put(DataBaseContants.TIME_DURATION, duration);
                updateValues.put(DataBaseContants.TIME_END, endTime);
                updateValues.put(DataBaseContants.SPORTS_TYPE, Integer.valueOf(sportType));
                updateValues.put(DataBaseContants.CALORIES, calories);
                updateValues.put(DataBaseContants.SYNC_STATE, Integer.valueOf(0));
            } else {
                updateValues.put("date", date);
                updateValues.put(DataBaseContants.TIME_START, startTime);
                updateValues.put(DataBaseContants.TIME_DURATION, duration);
                updateValues.put(DataBaseContants.TIME_END, endTime);
                updateValues.put(DataBaseContants.SPORTS_TYPE, Integer.valueOf(sportType));
                updateValues.put(DataBaseContants.CALORIES, calories);
                updateValues.put(DataBaseContants.SYNC_STATE, Integer.valueOf(2));
            }
        }
        c.close();
        cr.update(DataBaseContants.CONTENT_URI, updateValues, "_id = ?", new String[]{String.valueOf(this.id)});
    }

    private void deleteDateBaseSport() {
        ContentResolver cr = getContentResolver();
        cr.delete(DataBaseContants.CONTENT_URI, "_id = ?", new String[]{String.valueOf(this.id)});
        ContentValues values = new ContentValues();
        values.put(DataBaseContants.DELETE_VALUE, Long.valueOf(this.id));
        cr.insert(DataBaseContants.CONTENT_DELETE_URI, values);
    }

    private String getEndTime(int startTime, int lastTime) {
        if (startTime + lastTime >= 60) {
            if ((startTime + lastTime) / 60 >= 24) {
                if ((startTime + lastTime) % 60 < 10) {
                    return (((startTime + lastTime) / 60) - 24) + ":0" + ((startTime + lastTime) % 60);
                }
                return (((startTime + lastTime) / 60) - 24) + ":" + ((startTime + lastTime) % 60);
            } else if ((startTime + lastTime) % 60 < 10) {
                return ((startTime + lastTime) / 60) + ":0" + ((startTime + lastTime) % 60);
            } else {
                return ((startTime + lastTime) / 60) + ":" + ((startTime + lastTime) % 60);
            }
        } else if (startTime + lastTime < 10) {
            return "00:0" + (startTime + lastTime);
        } else {
            return "00:" + (startTime + lastTime);
        }
    }

    private void getStartIntTime(String sportStartTime) {
        String[] time = sportStartTime.split(":");
        String hour = time[0];
        String other = time[1];
        if (other.startsWith("0")) {
            this.startHour = Integer.parseInt(hour.toString());
            this.startOther = Integer.parseInt(other.substring(1).toString());
            return;
        }
        this.startHour = Integer.parseInt(hour.toString());
        this.startOther = Integer.parseInt(other.toString());
    }
}
