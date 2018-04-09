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

public class DurationPopupWindow extends PopupWindow implements OnEndFlingListener {
    private int choice_hour;
    private int choice_minute;
    private MyWheelView hourAdapter;
    private String[] lastHourData = new String[3];
    private String[] lastMinuteData = new String[61];
    private MyWheelView minuteAdapter;
    private int selectPostion;
    private int sport_hour;
    private int sport_minute;
    private TextView tv_ok;
    private View view;
    private WheelView wView_hour;
    private WheelView wView_minute;

    class C11261 implements OnClickListener {
        C11261() {
        }

        public void onClick(View v) {
            DurationPopupWindow.this.choice_hour = DurationPopupWindow.this.sport_hour;
            DurationPopupWindow.this.choice_minute = DurationPopupWindow.this.sport_minute;
            DurationPopupWindow.this.dismiss();
        }
    }

    class C18682 implements OnItemSelectedListener {
        C18682() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            DurationPopupWindow.this.selectPostion = DurationPopupWindow.this.wView_hour.getSelectedItemPosition();
            DurationPopupWindow.this.hourAdapter.setSelectPos(DurationPopupWindow.this.selectPostion);
            DurationPopupWindow.this.hourAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    class C18693 implements OnItemSelectedListener {
        C18693() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            DurationPopupWindow.this.selectPostion = DurationPopupWindow.this.wView_minute.getSelectedItemPosition();
            DurationPopupWindow.this.minuteAdapter.setSelectPos(DurationPopupWindow.this.selectPostion);
            DurationPopupWindow.this.minuteAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    public DurationPopupWindow(Context context, int hourSelection, int minuteSelection) {
        this.view = LayoutInflater.from(context).inflate(R.layout.add_duration, null);
        this.tv_ok = (TextView) this.view.findViewById(R.id.tv_ok);
        this.wView_hour = (WheelView) this.view.findViewById(R.id.wView_hour);
        this.wView_minute = (WheelView) this.view.findViewById(R.id.wView_minute);
        this.sport_hour = hourSelection;
        this.sport_minute = minuteSelection;
        this.choice_hour = hourSelection;
        this.choice_minute = minuteSelection;
        this.tv_ok.setOnClickListener(new C11261());
        this.lastMinuteData = context.getResources().getStringArray(R.array.last_minute);
        this.lastHourData = context.getResources().getStringArray(R.array.duration_hour);
        this.hourAdapter = new MyWheelView(this.lastHourData, context);
        this.minuteAdapter = new MyWheelView(this.lastMinuteData, context);
        this.wView_hour.setAdapter(this.hourAdapter);
        this.wView_minute.setAdapter(this.minuteAdapter);
        this.wView_hour.setSelection(hourSelection);
        this.wView_minute.setSelection(minuteSelection);
        this.hourAdapter.setSelectPos(hourSelection);
        this.minuteAdapter.setSelectPos(minuteSelection);
        this.wView_hour.setOnEndFlingListener(this);
        this.wView_minute.setOnEndFlingListener(this);
        this.wView_hour.setOnItemSelectedListener(new C18682());
        this.wView_minute.setOnItemSelectedListener(new C18693());
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

    public int getLastTime() {
        return (this.choice_hour * 60) + this.choice_minute;
    }
}
