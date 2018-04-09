package com.zhuoyou.plugin.add;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.fithealth.running.R;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.add.TosAdapterView.OnItemSelectedListener;
import com.zhuoyou.plugin.add.TosGallery.LayoutParams;
import com.zhuoyou.plugin.add.TosGallery.OnEndFlingListener;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.PersonalConfig;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.view.WheelView;

public class AddWeight extends Activity implements OnClickListener, OnEndFlingListener {
    private String date;
    private DateSelectPopupWindow datePopuWindow;
    private long id;
    private RelativeLayout imView;
    private float initWeightCount = 0.0f;
    private Intent intent;
    private boolean isModify = false;
    private int kg = 50;
    private String[] kgData = new String[300];
    private Button mButton;
    private int other = 0;
    private String[] otherData = new String[10];
    private LinearLayout rLayout;
    private RelativeLayout rlayout_startDate;
    private int selectPostion;
    private TextView tv_add_weight;
    private TextView tv_start_date;
    private String updateDate = "";
    private String weightCount;
    private WheelView wheelViewKg;
    private WheelView wheelViewOther;

    class C11175 implements DialogInterface.OnClickListener {
        C11175() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C11197 implements DialogInterface.OnClickListener {
        C11197() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    public class MyWheelView extends BaseAdapter {
        String[] data;
        int mHeight = 50;
        private Typeface mNumberTP = RunningApp.getCustomNumberFont();
        int selectPos;

        public MyWheelView(String[] String) {
            this.data = String;
            this.mHeight = Tools.dip2px(AddWeight.this, (float) this.mHeight);
        }

        public void setSelectPos(int pos) {
            this.selectPos = pos;
        }

        public int getCount() {
            return this.data != null ? this.data.length : 0;
        }

        public Object getItem(int position) {
            return this.data[position];
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txtView = null;
            if (convertView == null) {
                convertView = new TextView(AddWeight.this);
                convertView.setLayoutParams(new LayoutParams(-1, this.mHeight));
                txtView = (TextView) convertView;
                txtView.setGravity(17);
            }
            if (txtView == null) {
                txtView = (TextView) convertView;
            }
            if (position == this.selectPos) {
                txtView.setTextSize(1, 36.0f);
                txtView.setTextColor(-968822);
            } else if (position == this.selectPos - 1 || position == this.selectPos + 1) {
                txtView.setTextSize(1, 32.0f);
                txtView.setTextColor(-1712244854);
            } else if (position == this.selectPos - 2 || position == this.selectPos + 2) {
                txtView.setTextSize(1, 28.0f);
                txtView.setTextColor(1441871754);
            } else {
                txtView.setTextSize(1, 24.0f);
                txtView.setTextColor(301021066);
            }
            txtView.setText(this.data[position]);
            txtView.setTypeface(this.mNumberTP);
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_weight);
        this.imView = (RelativeLayout) findViewById(R.id.back);
        this.wheelViewKg = (WheelView) findViewById(R.id.weight_kg);
        this.wheelViewOther = (WheelView) findViewById(R.id.weight_other);
        this.tv_add_weight = (TextView) findViewById(R.id.title);
        this.tv_add_weight.setText(R.string.add_weight);
        this.mButton = (Button) findViewById(R.id.save);
        this.mButton.setOnClickListener(this);
        this.tv_start_date = (TextView) findViewById(R.id.tv_start_date);
        this.rLayout = (LinearLayout) findViewById(R.id.rlayout);
        this.rlayout_startDate = (RelativeLayout) findViewById(R.id.rlayout_startDate);
        this.rlayout_startDate.setOnClickListener(this);
        this.imView.setOnClickListener(this);
        this.kgData = getResources().getStringArray(R.array.weight_kg);
        this.otherData = getResources().getStringArray(R.array.weight_point);
        final MyWheelView kgAdapter = new MyWheelView(this.kgData);
        final MyWheelView otherAdapter = new MyWheelView(this.otherData);
        this.wheelViewKg.setAdapter((SpinnerAdapter) kgAdapter);
        this.wheelViewOther.setAdapter((SpinnerAdapter) otherAdapter);
        this.wheelViewKg.setOnEndFlingListener(this);
        this.wheelViewOther.setOnEndFlingListener(this);
        this.wheelViewKg.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
                AddWeight.this.selectPostion = AddWeight.this.wheelViewKg.getSelectedItemPosition();
                kgAdapter.setSelectPos(AddWeight.this.selectPostion);
                kgAdapter.notifyDataSetChanged();
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
        this.wheelViewOther.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
                AddWeight.this.selectPostion = AddWeight.this.wheelViewOther.getSelectedItemPosition();
                otherAdapter.setSelectPos(AddWeight.this.selectPostion);
                otherAdapter.notifyDataSetChanged();
            }

            public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
            }
        });
        this.intent = getIntent();
        this.weightCount = this.intent.getStringExtra("weightCount");
        this.date = this.intent.getStringExtra("date");
        this.id = this.intent.getLongExtra("id", 0);
        if (this.date.equals(Tools.getDate(0))) {
            this.tv_start_date.setText(R.string.today);
        } else {
            this.tv_start_date.setText(this.date);
        }
        if (this.weightCount != null) {
            this.updateDate = this.date;
            this.initWeightCount = Float.parseFloat(this.intent.getStringExtra("weightCount"));
            this.kg = (int) (this.initWeightCount / 1.0f);
            this.other = (int) ((this.initWeightCount * TitleBar.SHAREBTN_RIGHT_MARGIN) % TitleBar.SHAREBTN_RIGHT_MARGIN);
            this.wheelViewKg.setSelection(this.kg);
            this.wheelViewOther.setSelection(this.other);
            this.tv_add_weight.setText(R.string.edit_weight);
            this.mButton.setText(R.string.gpsdata_delete);
            return;
        }
        this.wheelViewKg.setSelection(50);
        this.wheelViewOther.setSelection(0);
        this.mButton.setText(R.string.ok);
    }

    @SuppressLint({"DefaultLocale"})
    public void onClick(View v) {
        PersonalConfig personalConfig = Tools.getPersonalConfig();
        Object[] aobj = new Object[1];
        switch (v.getId()) {
            case R.id.back:
                finish();
                return;
            case R.id.save:
                if (this.weightCount == null) {
                    String curTime = Tools.getStartTime();
                    personalConfig.setWeight(this.kg + "." + this.other);
                    aobj[0] = Double.valueOf(Tools.getBMI(personalConfig));
                    String bmi = String.format("%.1f", aobj);
                    insertDataBaseWeight(this.date, curTime, this.kg + "." + this.other, bmi + "", 1, 0);
                } else if (this.isModify) {
                    personalConfig.setWeight(this.kg + "." + this.other);
                    aobj[0] = Double.valueOf(Tools.getBMI(personalConfig));
                    updateDateBaseWeight(this.kg + "." + this.other, this.date, String.format("%.1f", aobj));
                } else {
                    deleteDateBaseWeight();
                    finish();
                }
                Tools.loginStateChange = true;
                return;
            case R.id.rlayout_startDate:
                final WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                final String finalDate = this.date;
                this.datePopuWindow = new DateSelectPopupWindow(this, finalDate);
                this.datePopuWindow.showAtLocation(this.rLayout, 81, 0, 0);
                getWindow().setAttributes(lp);
                this.datePopuWindow.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss() {
                        lp.alpha = 1.0f;
                        AddWeight.this.getWindow().setAttributes(lp);
                        AddWeight.this.date = AddWeight.this.datePopuWindow.getStartDate();
                        if (!AddWeight.this.date.equals(finalDate)) {
                            AddWeight.this.isModify = true;
                            AddWeight.this.mButton.setText(R.string.ok);
                        }
                        if (AddWeight.this.date.equals(Tools.getDate(0))) {
                            AddWeight.this.tv_start_date.setText(R.string.today);
                        } else {
                            AddWeight.this.tv_start_date.setText(AddWeight.this.date);
                        }
                    }
                });
                return;
            default:
                return;
        }
    }

    private void insertDataBaseWeight(String date, String time, final String weight, final String bmi, int type, int statistics) {
        Cursor c = getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{"_id"}, "date = ?  and type = ?  and statistics = ? ", new String[]{date, "1", "0"}, null);
        if (c.getCount() <= 0 || !c.moveToFirst()) {
            ContentValues runningItem = new ContentValues();
            runningItem.put("_id", Long.valueOf(Tools.getPKL()));
            runningItem.put("date", date);
            runningItem.put(DataBaseContants.TIME_START, time);
            runningItem.put(DataBaseContants.CONF_WEIGHT, weight);
            runningItem.put(DataBaseContants.BMI, bmi);
            runningItem.put("type", Integer.valueOf(type));
            runningItem.put(DataBaseContants.STATISTICS, Integer.valueOf(statistics));
            getContentResolver().insert(DataBaseContants.CONTENT_URI, runningItem);
            c.close();
            finish();
            return;
        }
        final Long mId = Long.valueOf(c.getLong(c.getColumnIndex("_id")));
        Builder builder = new Builder(this);
        builder.setTitle((int) R.string.alert_title);
        builder.setMessage((int) R.string.date_have_weight);
        builder.setPositiveButton((int) R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AddWeight.this.updateDateBaseWeight(mId, weight, bmi);
                dialog.dismiss();
                AddWeight.this.finish();
            }
        });
        builder.setNegativeButton((int) R.string.cancle, new C11175());
        builder.setCancelable(Boolean.valueOf(false));
        builder.create().show();
        c.close();
    }

    private void updateDateBaseWeight(Long id, String weight, String bmi) {
        ContentResolver cr = getContentResolver();
        ContentValues updateValues = new ContentValues();
        Cursor c = cr.query(DataBaseContants.CONTENT_URI, new String[]{DataBaseContants.SYNC_STATE}, "_id = ? ", new String[]{String.valueOf(id)}, null);
        if (c.getCount() > 0 && c.moveToFirst()) {
            if (c.getInt(c.getColumnIndex(DataBaseContants.SYNC_STATE)) == 0) {
                updateValues.put(DataBaseContants.CONF_WEIGHT, weight);
                updateValues.put(DataBaseContants.BMI, bmi);
                updateValues.put(DataBaseContants.SYNC_STATE, Integer.valueOf(0));
            } else {
                updateValues.put(DataBaseContants.CONF_WEIGHT, weight);
                updateValues.put(DataBaseContants.BMI, bmi);
                updateValues.put(DataBaseContants.SYNC_STATE, Integer.valueOf(2));
            }
        }
        c.close();
        cr.update(DataBaseContants.CONTENT_URI, updateValues, "_id = ? ", new String[]{String.valueOf(id)});
    }

    private void updateDateBaseWeight(String weight, String date, String bmi) {
        ContentResolver cr = getContentResolver();
        ContentValues updateValues = new ContentValues();
        Cursor c;
        if (this.updateDate.equals(date)) {
            c = cr.query(DataBaseContants.CONTENT_URI, new String[]{DataBaseContants.SYNC_STATE}, "_id = ? ", new String[]{String.valueOf(this.id)}, null);
            if (c.getCount() > 0 && c.moveToFirst()) {
                if (c.getInt(c.getColumnIndex(DataBaseContants.SYNC_STATE)) == 0) {
                    updateValues.put("date", date);
                    updateValues.put(DataBaseContants.CONF_WEIGHT, weight);
                    updateValues.put(DataBaseContants.BMI, bmi);
                    updateValues.put(DataBaseContants.SYNC_STATE, Integer.valueOf(0));
                } else {
                    updateValues.put("date", date);
                    updateValues.put(DataBaseContants.CONF_WEIGHT, weight);
                    updateValues.put(DataBaseContants.BMI, bmi);
                    updateValues.put(DataBaseContants.SYNC_STATE, Integer.valueOf(2));
                }
            }
            c.close();
            cr.update(DataBaseContants.CONTENT_URI, updateValues, "_id = ? ", new String[]{String.valueOf(this.id)});
            finish();
            return;
        }
        c = cr.query(DataBaseContants.CONTENT_URI, new String[]{"_id"}, "date = ?  and type = ?  and statistics = ? ", new String[]{date, "1", "0"}, null);
        if (c.getCount() <= 0 || !c.moveToFirst()) {
            ContentValues runningItem = new ContentValues();
            runningItem.put("_id", Long.valueOf(Tools.getPKL()));
            runningItem.put("date", date);
            runningItem.put(DataBaseContants.TIME_START, Tools.getStartTime());
            runningItem.put(DataBaseContants.CONF_WEIGHT, weight);
            runningItem.put(DataBaseContants.BMI, bmi);
            runningItem.put("type", Integer.valueOf(1));
            runningItem.put(DataBaseContants.STATISTICS, Integer.valueOf(0));
            cr.insert(DataBaseContants.CONTENT_URI, runningItem);
            c.close();
            finish();
            return;
        }
        final Long mId = Long.valueOf(c.getLong(c.getColumnIndex("_id")));
        Builder builder = new Builder(this);
        builder.setTitle((int) R.string.alert_title);
        builder.setMessage((int) R.string.date_have_weight);
        final String str = weight;
        final String str2 = bmi;
        builder.setPositiveButton((int) R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AddWeight.this.updateDateBaseWeight(mId, str, str2);
                AddWeight.this.finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton((int) R.string.cancle, new C11197());
        builder.setCancelable(Boolean.valueOf(false));
        builder.create().show();
        c.close();
    }

    private void deleteDateBaseWeight() {
        ContentResolver cr = getContentResolver();
        cr.delete(DataBaseContants.CONTENT_URI, "_id = ?", new String[]{String.valueOf(this.id)});
        ContentValues values = new ContentValues();
        values.put(DataBaseContants.DELETE_VALUE, Long.valueOf(this.id));
        cr.insert(DataBaseContants.CONTENT_DELETE_URI, values);
    }

    public void onEndFling(TosGallery v) {
        this.isModify = true;
        this.mButton.setText(R.string.ok);
        switch (v.getId()) {
            case R.id.weight_kg:
                this.kg = this.wheelViewKg.getSelectedItemPosition();
                return;
            case R.id.weight_other:
                this.other = this.wheelViewOther.getSelectedItemPosition();
                return;
            default:
                return;
        }
    }
}
