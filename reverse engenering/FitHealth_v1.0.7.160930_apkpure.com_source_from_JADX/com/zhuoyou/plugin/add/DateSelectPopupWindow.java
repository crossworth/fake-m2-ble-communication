package com.zhuoyou.plugin.add;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.fithealth.running.R;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.add.TosAdapterView.OnItemSelectedListener;
import com.zhuoyou.plugin.add.TosGallery.OnEndFlingListener;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.view.WheelView;
import java.util.ArrayList;
import java.util.List;

public class DateSelectPopupWindow extends PopupWindow implements OnEndFlingListener {
    private static final int[] DAYS_PER_MONTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] MONTH_NAME = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String curr_date = Tools.getDate(0);
    private MyWheelView dateAdapter;
    private Context mContext;
    private String[] mDates;
    private Handler mHandler;
    private String[] mMonths;
    private String[] mYears;
    private MyWheelView monthAdapter;
    private int positonDate;
    private int positonMonth;
    private int positonYear;
    private int selectDate;
    private int selectMonth;
    private int selectPostion;
    private int selectYear;
    private TextView today;
    private TextView tv_ok;
    private WheelView wView_date;
    private WheelView wView_month;
    private WheelView wView_year;
    private MyWheelView yearAdapter;

    class C11221 implements OnClickListener {
        C11221() {
        }

        public void onClick(View v) {
            DateSelectPopupWindow.this.selectYear = DateSelectPopupWindow.this.positonYear;
            DateSelectPopupWindow.this.selectMonth = DateSelectPopupWindow.this.positonMonth;
            DateSelectPopupWindow.this.selectDate = DateSelectPopupWindow.this.positonDate;
            DateSelectPopupWindow.this.dismiss();
        }
    }

    class C11235 implements Runnable {
        C11235() {
        }

        public void run() {
            DateSelectPopupWindow.this.prepareData();
            DateSelectPopupWindow.this.yearAdapter.setData(DateSelectPopupWindow.this.mYears);
            DateSelectPopupWindow.this.yearAdapter.notifyDataSetChanged();
            DateSelectPopupWindow.this.monthAdapter.setData(DateSelectPopupWindow.this.mMonths);
            DateSelectPopupWindow.this.monthAdapter.notifyDataSetChanged();
            DateSelectPopupWindow.this.dateAdapter.setData(DateSelectPopupWindow.this.mDates);
            DateSelectPopupWindow.this.dateAdapter.notifyDataSetChanged();
        }
    }

    class C11246 implements Runnable {
        C11246() {
        }

        public void run() {
            DateSelectPopupWindow.this.prepareData();
            DateSelectPopupWindow.this.yearAdapter.setData(DateSelectPopupWindow.this.mYears);
            DateSelectPopupWindow.this.yearAdapter.notifyDataSetChanged();
            DateSelectPopupWindow.this.monthAdapter.setData(DateSelectPopupWindow.this.mMonths);
            DateSelectPopupWindow.this.monthAdapter.notifyDataSetChanged();
            DateSelectPopupWindow.this.dateAdapter.setData(DateSelectPopupWindow.this.mDates);
            DateSelectPopupWindow.this.dateAdapter.notifyDataSetChanged();
        }
    }

    class C18652 implements OnItemSelectedListener {
        C18652() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            DateSelectPopupWindow.this.selectPostion = DateSelectPopupWindow.this.wView_year.getSelectedItemPosition();
            DateSelectPopupWindow.this.yearAdapter.setSelectPos(DateSelectPopupWindow.this.selectPostion);
            DateSelectPopupWindow.this.yearAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    class C18663 implements OnItemSelectedListener {
        C18663() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            DateSelectPopupWindow.this.selectPostion = DateSelectPopupWindow.this.wView_month.getSelectedItemPosition();
            DateSelectPopupWindow.this.monthAdapter.setSelectPos(DateSelectPopupWindow.this.selectPostion);
            DateSelectPopupWindow.this.monthAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    class C18674 implements OnItemSelectedListener {
        C18674() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            DateSelectPopupWindow.this.selectPostion = DateSelectPopupWindow.this.wView_date.getSelectedItemPosition();
            DateSelectPopupWindow.this.dateAdapter.setSelectPos(DateSelectPopupWindow.this.selectPostion);
            DateSelectPopupWindow.this.dateAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    public DateSelectPopupWindow(Context context, String date) {
        int intValue;
        this.mContext = context;
        this.mHandler = new Handler();
        View view = LayoutInflater.from(context).inflate(R.layout.date_select, null);
        this.tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        this.tv_ok.setOnClickListener(new C11221());
        this.wView_year = (WheelView) view.findViewById(R.id.wView_year);
        this.wView_year.setOnEndFlingListener(this);
        this.wView_year.setOnItemSelectedListener(new C18652());
        this.wView_month = (WheelView) view.findViewById(R.id.wView_month);
        this.wView_month.setOnEndFlingListener(this);
        this.wView_month.setOnItemSelectedListener(new C18663());
        this.wView_date = (WheelView) view.findViewById(R.id.wView_date);
        this.wView_date.setOnEndFlingListener(this);
        this.wView_date.setOnItemSelectedListener(new C18674());
        String[] temp = date.split(SocializeConstants.OP_DIVIDER_MINUS);
        if (Integer.valueOf(temp[0]).intValue() < 2014) {
            this.positonYear = 2014;
            this.selectYear = 2014;
        } else {
            intValue = Integer.valueOf(temp[0]).intValue();
            this.positonYear = intValue;
            this.selectYear = intValue;
        }
        intValue = Integer.valueOf(temp[1]).intValue();
        this.positonMonth = intValue;
        this.selectMonth = intValue;
        intValue = Integer.valueOf(temp[2]).intValue();
        this.positonDate = intValue;
        this.selectDate = intValue;
        prepareData();
        this.yearAdapter = new MyWheelView(this.mYears, context);
        this.monthAdapter = new MyWheelView(this.mMonths, context);
        this.dateAdapter = new MyWheelView(this.mDates, context);
        this.wView_year.setAdapter(this.yearAdapter);
        this.wView_month.setAdapter(this.monthAdapter);
        this.wView_date.setAdapter(this.dateAdapter);
        this.wView_year.setSelection(this.selectYear - 2014);
        this.wView_month.setSelection(this.selectMonth - 1);
        this.wView_date.setSelection(this.selectDate - 1);
        this.yearAdapter.setSelectPos(this.selectYear - 2014);
        this.monthAdapter.setSelectPos(this.selectMonth - 1);
        this.dateAdapter.setSelectPos(this.selectDate - 1);
        setContentView(view);
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
        setBackgroundDrawable(new PaintDrawable());
        setOutsideTouchable(true);
    }

    private void prepareData() {
        int i;
        String[] temp = this.curr_date.split(SocializeConstants.OP_DIVIDER_MINUS);
        int curr_year = Integer.valueOf(temp[0]).intValue();
        int curr_month = Integer.valueOf(temp[1]).intValue();
        int curr_date = Integer.valueOf(temp[2]).intValue();
        List<String> list = new ArrayList();
        if (curr_year < 2014) {
            list.add("2014" + this.mContext.getResources().getString(R.string.pop_year));
        } else {
            for (i = 2014; i <= curr_year; i++) {
                list.add(String.valueOf(i) + this.mContext.getResources().getString(R.string.pop_year));
            }
        }
        this.mYears = (String[]) list.toArray(new String[list.size()]);
        list.clear();
        if (this.positonYear == curr_year) {
            for (i = 0; i < curr_month; i++) {
                list.add(MONTH_NAME[i] + this.mContext.getResources().getString(R.string.pop_mouth));
            }
        } else {
            for (i = 0; i < 12; i++) {
                list.add(MONTH_NAME[i] + this.mContext.getResources().getString(R.string.pop_mouth));
            }
        }
        this.mMonths = (String[]) list.toArray(new String[list.size()]);
        if (this.positonMonth > this.mMonths.length) {
            this.positonMonth = this.mMonths.length;
            this.monthAdapter.setSelectPos(this.positonMonth - 1);
        }
        int days = DAYS_PER_MONTH[this.positonMonth - 1];
        if (2 == this.positonMonth) {
            days = isLeapYear(this.positonYear) ? 29 : 28;
        }
        list.clear();
        if (this.positonYear == curr_year && this.positonMonth == curr_month) {
            for (i = 1; i <= curr_date; i++) {
                list.add(String.valueOf(i) + this.mContext.getResources().getString(R.string.pop_date));
            }
        } else {
            for (i = 1; i <= days; i++) {
                list.add(String.valueOf(i) + this.mContext.getResources().getString(R.string.pop_date));
            }
        }
        this.mDates = (String[]) list.toArray(new String[list.size()]);
        if (this.positonDate > this.mDates.length) {
            this.positonDate = this.mDates.length;
            this.dateAdapter.setSelectPos(this.positonDate - 1);
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public void onEndFling(TosGallery v) {
        int pos = v.getSelectedItemPosition();
        switch (v.getId()) {
            case R.id.wView_year:
                this.positonYear = pos + 2014;
                this.mHandler.postDelayed(new C11235(), 1000);
                return;
            case R.id.wView_month:
                this.positonMonth = pos + 1;
                this.mHandler.postDelayed(new C11246(), 1000);
                return;
            case R.id.wView_date:
                this.positonDate = pos + 1;
                return;
            default:
                return;
        }
    }

    public String getStartDate() {
        String month = "" + this.selectMonth;
        String date = "" + this.selectDate;
        if (this.selectMonth < 10) {
            month = "0" + this.selectMonth;
        }
        if (this.selectDate < 10) {
            date = "0" + this.selectDate;
        }
        return this.selectYear + SocializeConstants.OP_DIVIDER_MINUS + month + SocializeConstants.OP_DIVIDER_MINUS + date;
    }

    public void setColor(int color) {
        this.tv_ok.setTextColor(color);
    }
}
