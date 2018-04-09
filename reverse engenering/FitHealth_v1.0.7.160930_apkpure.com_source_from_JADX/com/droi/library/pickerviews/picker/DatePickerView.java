package com.droi.library.pickerviews.picker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.amap.api.services.core.AMapException;
import com.droi.library.pickerviews.C0545R;
import com.droi.library.pickerviews.adapter.NumericWheelAdapter;
import com.droi.library.pickerviews.lib.WheelView;
import com.droi.library.pickerviews.listener.OnItemSelectedListener;
import com.umeng.socialize.common.SocializeConstants;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatePickerView extends LinearLayout implements IPickerView {
    private static Format intformat = new DecimalFormat("00");
    private static final List<String> list_big = Arrays.asList(months_big);
    private static final List<String> list_little = Arrays.asList(months_little);
    private static String[] months_big = new String[]{"1", "3", "5", "7", "8", "10", "12"};
    private static String[] months_little = new String[]{"4", "6", "9", "11"};
    public static String space = SocializeConstants.OP_DIVIDER_MINUS;
    private static Format yearformat = new DecimalFormat("0000");
    private NumericWheelAdapter dayAdapter;
    private int dayIndex;
    private WheelView dayView;
    private OnDatePickedListener listener;
    private int maxYear = AMapException.CODE_AMAP_NEARBY_INVALID_USERID;
    private int minYear = AMapException.CODE_AMAP_ID_NOT_EXIST;
    private NumericWheelAdapter monthAdapter;
    private int monthIndex;
    private WheelView monthView;
    private boolean todayLimit;
    private NumericWheelAdapter yearAdapter;
    private int yearIndex;
    private WheelView yearView;

    public interface OnDatePickedListener {
        void onDatePicked(int i, int i2, int i3, String str);
    }

    class C16241 implements OnItemSelectedListener {
        C16241() {
        }

        public void onItemSelected(int index) {
            DatePickerView.this.yearIndex = index;
            DatePickerView.this.initDayView();
            DatePickerView.this.doListener();
        }
    }

    class C16252 implements OnItemSelectedListener {
        C16252() {
        }

        public void onItemSelected(int index) {
            DatePickerView.this.monthIndex = index;
            DatePickerView.this.initDayView();
            DatePickerView.this.doListener();
        }
    }

    class C16263 implements OnItemSelectedListener {
        C16263() {
        }

        public void onItemSelected(int index) {
            DatePickerView.this.dayIndex = index;
            DatePickerView.this.doListener();
        }
    }

    public DatePickerView(Context context) {
        super(context);
        initView(context);
    }

    public DatePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DatePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(C0545R.layout.date_picker_layout, this, true);
        this.yearView = (WheelView) contentView.findViewById(C0545R.id.year_view);
        this.monthView = (WheelView) contentView.findViewById(C0545R.id.month_view);
        this.dayView = (WheelView) contentView.findViewById(C0545R.id.day_view);
        this.yearView.setOnItemSelectedListener(new C16241());
        this.monthView.setOnItemSelectedListener(new C16252());
        this.dayView.setOnItemSelectedListener(new C16263());
    }

    private void doListener() {
        if (this.todayLimit) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(1);
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            if (this.yearIndex + this.minYear > year) {
                setInitDatePicked(year, month, day);
                this.yearView.setCurrentItem(this.yearIndex);
                this.monthView.setCurrentItem(this.monthIndex);
                initDayView();
            } else if (this.yearIndex + this.minYear == year && this.monthIndex + 1 > month) {
                setInitDatePicked(year, month, day);
                this.monthView.setCurrentItem(this.monthIndex);
                initDayView();
            } else if (this.yearIndex + this.minYear == year && this.monthIndex + 1 == month && this.dayIndex + 1 > day) {
                setInitDatePicked(year, month, day);
                initDayView();
            }
        }
        if (this.listener != null) {
            this.listener.onDatePicked(this.yearIndex, this.monthIndex, this.dayIndex, getSelectedDate());
        }
    }

    public String getSelectedDate() {
        String year = yearformat.format(this.yearAdapter.getItem(this.yearIndex));
        String month = intformat.format(this.monthAdapter.getItem(this.monthIndex));
        return year + space + month + space + intformat.format(this.dayAdapter.getItem(this.dayIndex));
    }

    public void showLabel(boolean show) {
        if (show) {
            this.yearView.setLabel(C0545R.string.pickerview_year);
            this.monthView.setLabel(C0545R.string.pickerview_month);
            this.dayView.setLabel(C0545R.string.pickerview_day);
            return;
        }
        this.yearView.setLabel(null);
        this.monthView.setLabel(null);
        this.dayView.setLabel(null);
    }

    public void setTextSize(int size) {
        this.yearView.setTextSize((float) size);
        this.monthView.setTextSize((float) size);
        this.dayView.setTextSize((float) size);
    }

    public void setTextColorOut(int textColorOut) {
        this.yearView.setTextColorOut(textColorOut);
        this.monthView.setTextColorOut(textColorOut);
        this.dayView.setTextColorOut(textColorOut);
    }

    public void setTextColorCenter(int textColorCenter) {
        this.yearView.setTextColorCenter(textColorCenter);
        this.monthView.setTextColorCenter(textColorCenter);
        this.dayView.setTextColorCenter(textColorCenter);
    }

    public void setDividerColor(int dividerColor) {
        this.yearView.setDividerColor(dividerColor);
        this.monthView.setDividerColor(dividerColor);
        this.dayView.setDividerColor(dividerColor);
    }

    public void setLabelColor(int labelColor) {
        this.yearView.setLabelColor(labelColor);
        this.monthView.setLabelColor(labelColor);
        this.dayView.setLabelColor(labelColor);
    }

    public void setLineSpacingMultiplier(float space) {
        this.yearView.setLineSpacingMultiplier(space);
        this.monthView.setLineSpacingMultiplier(space);
        this.dayView.setLineSpacingMultiplier(space);
    }

    public void setTypeface(Typeface type) {
        this.yearView.setTypeface(type);
        this.monthView.setTypeface(type);
        this.dayView.setTypeface(type);
    }

    public void setDividerWidth(float width) {
        this.yearView.setDividerWidth(width);
        this.monthView.setDividerWidth(width);
        this.dayView.setDividerWidth(width);
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        space = space;
    }

    public void setInitDatePicked(int year, int month, int day) {
        if (year > this.maxYear || year < this.minYear) {
            year = this.minYear;
        }
        this.yearIndex = year - this.minYear;
        this.monthIndex = month - 1;
        this.dayIndex = day - 1;
    }

    public void setInitDatePicked(int[] date) {
        if (date.length == 3) {
            setInitDatePicked(date[0], date[1], date[2]);
        }
    }

    public void setInitDatePicked(String date) {
        String[] dateArray = date.split(space);
        if (dateArray.length == 3) {
            setInitDatePicked(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]));
        }
    }

    public void setDatePicked(int year, int month, int day) {
        if (year > this.maxYear || year < this.minYear) {
            year = this.minYear;
        }
        this.yearIndex = year - this.minYear;
        this.monthIndex = month - 1;
        this.dayIndex = day - 1;
        this.yearView.setCurrentItem(this.yearIndex);
        this.monthView.setCurrentItem(this.monthIndex);
        this.dayView.setCurrentItem(this.dayIndex);
        doListener();
    }

    public void setDatePicked(int[] date) {
        if (date.length == 3) {
            setDatePicked(date[0], date[1], date[2]);
        }
    }

    public void setDatePicked(String date) {
        String[] dateArray = date.split(space);
        if (dateArray.length == 3) {
            setDatePicked(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]));
        }
    }

    private void setDateIndex(int yearIndex, int monthIndex, int dayIndex) {
        this.yearIndex = yearIndex;
        this.monthIndex = monthIndex;
        this.dayIndex = dayIndex;
    }

    private void initYearView() {
        this.yearAdapter = new NumericWheelAdapter(this.minYear, this.maxYear);
        this.yearView.setAdapter(this.yearAdapter);
        this.yearView.setCurrentItem(this.yearIndex);
    }

    private void initMonthView() {
        this.monthAdapter = new NumericWheelAdapter(1, 12);
        this.monthView.setAdapter(this.monthAdapter);
        this.monthView.setCurrentItem(this.monthIndex);
    }

    private void initDayView() {
        if (list_big.contains(String.valueOf(this.monthIndex + 1))) {
            this.dayAdapter = new NumericWheelAdapter(1, 31);
        } else if (list_little.contains(String.valueOf(this.monthIndex + 1))) {
            this.dayAdapter = new NumericWheelAdapter(1, 30);
            if (this.dayIndex > 29) {
                this.dayIndex = 29;
            }
        } else if (((this.yearIndex + this.minYear) % 4 != 0 || (this.yearIndex + this.minYear) % 100 == 0) && (this.yearIndex + this.minYear) % 400 != 0) {
            this.dayAdapter = new NumericWheelAdapter(1, 28);
            if (this.dayIndex > 27) {
                this.dayIndex = 27;
            }
        } else {
            this.dayAdapter = new NumericWheelAdapter(1, 29);
            if (this.dayIndex > 28) {
                this.dayIndex = 28;
            }
        }
        this.dayView.setAdapter(this.dayAdapter);
        this.dayView.setCurrentItem(this.dayIndex);
    }

    public void show() {
        initYearView();
        initMonthView();
        initDayView();
    }

    public void setYearRange(int minYear, int maxYear) {
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    public void setTodayLimit(boolean limit) {
        this.todayLimit = limit;
    }

    public void setOnDatePickedListener(OnDatePickedListener listener) {
        this.listener = listener;
    }
}
