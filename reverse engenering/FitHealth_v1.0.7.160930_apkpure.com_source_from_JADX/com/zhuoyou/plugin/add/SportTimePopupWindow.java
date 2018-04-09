package com.zhuoyou.plugin.add;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.add.TosAdapterView.OnItemSelectedListener;
import com.zhuoyou.plugin.add.TosGallery.OnEndFlingListener;
import com.zhuoyou.plugin.view.WheelView;

public class SportTimePopupWindow extends PopupWindow implements OnEndFlingListener {
    private int choice_hour;
    private int choice_minute;
    private MyWheelView hourAdapter;
    private String[] hourData = new String[25];
    private MyWheelView minuteAdapter;
    private String[] minuteData = new String[61];
    private int selectPostion;
    private int sport_hour;
    private int sport_minute;
    private TextView tv_ok;
    private View view;
    private WheelView wView_hour;
    private WheelView wView_minute;

    class C11321 implements OnClickListener {
        C11321() {
        }

        public void onClick(View v) {
            SportTimePopupWindow.this.choice_hour = SportTimePopupWindow.this.sport_hour;
            SportTimePopupWindow.this.choice_minute = SportTimePopupWindow.this.sport_minute;
            SportTimePopupWindow.this.dismiss();
        }
    }

    class C18722 implements OnItemSelectedListener {
        C18722() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            SportTimePopupWindow.this.selectPostion = SportTimePopupWindow.this.wView_hour.getSelectedItemPosition();
            SportTimePopupWindow.this.hourAdapter.setSelectPos(SportTimePopupWindow.this.selectPostion);
            SportTimePopupWindow.this.hourAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    class C18733 implements OnItemSelectedListener {
        C18733() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            SportTimePopupWindow.this.selectPostion = SportTimePopupWindow.this.wView_minute.getSelectedItemPosition();
            SportTimePopupWindow.this.minuteAdapter.setSelectPos(SportTimePopupWindow.this.selectPostion);
            SportTimePopupWindow.this.minuteAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    public SportTimePopupWindow(Context context, int hourSelection, int minuteSelection) {
        this.view = LayoutInflater.from(context).inflate(R.layout.add_time, null);
        this.tv_ok = (TextView) this.view.findViewById(R.id.tv_ok);
        this.wView_hour = (WheelView) this.view.findViewById(R.id.wView_hour);
        this.wView_minute = (WheelView) this.view.findViewById(R.id.wView_minute);
        this.sport_hour = hourSelection;
        this.sport_minute = minuteSelection;
        this.choice_hour = hourSelection;
        this.choice_minute = minuteSelection;
        this.tv_ok.setOnClickListener(new C11321());
        this.hourData = context.getResources().getStringArray(R.array.hour);
        this.minuteData = context.getResources().getStringArray(R.array.minute);
        this.hourAdapter = new MyWheelView(this.hourData, context);
        this.minuteAdapter = new MyWheelView(this.minuteData, context);
        this.wView_hour.setAdapter(this.hourAdapter);
        this.wView_minute.setAdapter(this.minuteAdapter);
        this.wView_hour.setSelection(hourSelection);
        this.wView_minute.setSelection(minuteSelection);
        this.hourAdapter.setSelectPos(hourSelection);
        this.minuteAdapter.setSelectPos(minuteSelection);
        this.wView_hour.setOnEndFlingListener(this);
        this.wView_minute.setOnEndFlingListener(this);
        this.wView_hour.setOnItemSelectedListener(new C18722());
        this.wView_minute.setOnItemSelectedListener(new C18733());
        setContentView(this.view);
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
        setBackgroundDrawable(new PaintDrawable());
        setOutsideTouchable(true);
    }

    public void onEndFling(TosGallery v) {
        switch (v.getId()) {
            case R.id.wView_hour:
                this.sport_hour = this.wView_hour.getSelectedItemPosition();
                return;
            case R.id.wView_minute:
                this.sport_minute = this.wView_minute.getSelectedItemPosition();
                return;
            default:
                return;
        }
    }

    public int getStartTime() {
        return (this.choice_hour * 60) + this.choice_minute;
    }

    public int getLastTime() {
        return (this.sport_hour * 60) + this.sport_minute;
    }

    public void setColor(int color) {
        this.tv_ok.setTextColor(color);
    }
}
