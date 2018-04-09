package com.zhuoyou.plugin.running;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.view.CalView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint({"SdCardPath"})
public class CalendarView extends PopupWindow implements OnTouchListener {
    private static final int CAL_LAYOUT_ID = 55;
    private Calendar calSelected = null;
    private Calendar calStartDate = null;
    private Calendar calToday = Calendar.getInstance();
    private String currDate = null;
    private Calendar currentCalendar = null;
    private CalendarGridViewAdapter currentGridAdapter;
    private GridView currentGridView;
    private List<String> days = null;
    private Calendar firstCalendar = null;
    private CalendarGridViewAdapter firstGridAdapter;
    private GridView firstGridView;
    private int iFirstDayOfWeek = 2;
    private int index;
    private Calendar lastCalendar = null;
    private CalendarGridViewAdapter lastGridAdapter;
    private GridView lastGridView;
    private RelativeLayout mBgDis;
    private RelativeLayout mCalendarMainLayout;
    private Context mContext;
    private TextView mDayMessage;
    GestureDetector mGesture = null;
    private int[] mHDate = null;
    private int mMonthViewCurrentMonth = 0;
    private int mMonthViewCurrentYear = 0;
    private ImageView mNextMonthImg;
    private ImageView mPreMonthImg;
    private Button mTodayBtn;
    private DisplayMetrics metric;
    private OnClickListener onDisClickListener = new C13461();
    private OnClickListener onNextMonthClickListener = new C13494();
    private OnClickListener onPreMonthClickListener = new C13483();
    private OnClickListener onTodayClickListener = new C13472();
    private View view;
    private ViewPager viewPager;

    class C13461 implements OnClickListener {
        C13461() {
        }

        public void onClick(View view) {
            CalendarView.this.dismiss();
        }
    }

    class C13472 implements OnClickListener {
        C13472() {
        }

        public void onClick(View view) {
            if (HomePageFragment.mInstance != null) {
                HomePageFragment.mInstance.onViewPagerCurrent();
            }
            CalendarView.this.dismiss();
        }
    }

    class C13483 implements OnClickListener {
        C13483() {
        }

        public void onClick(View view) {
            CalendarView.this.setPrevViewItem();
            CalendarView.this.CreateGirdView();
        }
    }

    class C13494 implements OnClickListener {
        C13494() {
        }

        public void onClick(View view) {
            CalendarView.this.setNextViewItem();
            CalendarView.this.CreateGirdView();
        }
    }

    class GestureListener extends SimpleOnGestureListener {
        GestureListener() {
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            CalView txtDay = (CalView) CalendarView.this.currentGridView.findViewById(CalendarView.this.currentGridView.pointToPosition((int) e.getX(), (int) e.getY()) + 5000);
            if (!(txtDay == null || txtDay.getTag() == null)) {
                Date date = (Date) txtDay.getTag();
                CalendarView.this.calSelected.setTime(date);
                int calSeletedDay = Tools.DataToInteger(date).intValue();
                int calSelectIndex = CalendarView.getCurrentDate() - calSeletedDay;
                if (CalendarView.this.mHDate.length > 0 && CalendarView.this.mHDate[0] <= calSeletedDay && calSelectIndex >= 0) {
                    CalendarView.this.currentGridAdapter.setSelectedDate(CalendarView.this.calSelected);
                    CalendarView.this.currentGridAdapter.notifyDataSetChanged();
                    CalendarView.this.index = Tools.getDayCount(CalendarView.this.mHDate[0] + "", calSeletedDay + "", "yyyyMMdd") - 1;
                    if (HomePageFragment.mInstance != null) {
                        HomePageFragment.mInstance.onViewPagerIndex(CalendarView.this.index);
                    }
                    CalendarView.this.dismiss();
                }
            }
            return false;
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {
        int positionState = -1;

        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                if (this.positionState == 0) {
                    CalendarView.this.setPrevViewItem();
                    CalendarView.this.CreateGirdView();
                }
                if (this.positionState == 2) {
                    CalendarView.this.setNextViewItem();
                    CalendarView.this.CreateGirdView();
                }
            }
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            this.positionState = position;
        }
    }

    class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) this.mListViews.get(position));
        }

        public Object instantiateItem(ViewGroup container, int position) {
            container.addView((View) this.mListViews.get(position), 0);
            return this.mListViews.get(position);
        }

        public int getCount() {
            return this.mListViews.size();
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

    public CalendarView(Context con, String date) {
        this.mContext = con;
        this.currDate = date;
        this.metric = new DisplayMetrics();
        ((Activity) con).getWindowManager().getDefaultDisplay().getMetrics(this.metric);
        this.view = ((Activity) this.mContext).getLayoutInflater().inflate(R.layout.calendar_main, null, false);
        initView(this.view);
        this.calStartDate = stringToCal(this.currDate);
        this.calSelected = stringToCal(this.currDate);
        this.days = HomePageFragment.mInstance.getDateList();
        this.mHDate = getCurrentMouthDate(this.days);
        Arrays.sort(this.mHDate);
        updateStartDateForMonth();
        generateContetView(this.mCalendarMainLayout);
        this.mGesture = new GestureDetector(this.mContext, new GestureListener());
        this.view.setLayoutParams(new LayoutParams(-1, -2));
        this.view.measure(0, 0);
        setContentView(this.view);
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
        setOutsideTouchable(true);
    }

    public boolean onTouch(View v, MotionEvent event) {
        return this.mGesture.onTouchEvent(event);
    }

    private void initView(View v) {
        this.mTodayBtn = (Button) v.findViewById(R.id.today_btn);
        this.mDayMessage = (TextView) v.findViewById(R.id.day_message);
        this.mCalendarMainLayout = (RelativeLayout) v.findViewById(R.id.calendar_main);
        this.mPreMonthImg = (ImageView) v.findViewById(R.id.left_img);
        this.mNextMonthImg = (ImageView) v.findViewById(R.id.right_img);
        this.mTodayBtn.setOnClickListener(this.onTodayClickListener);
        this.mPreMonthImg.setOnClickListener(this.onPreMonthClickListener);
        this.mNextMonthImg.setOnClickListener(this.onNextMonthClickListener);
        this.mBgDis = (RelativeLayout) v.findViewById(R.id.bg_dis);
        this.mBgDis.setOnClickListener(this.onDisClickListener);
    }

    private void generateContetView(RelativeLayout layout) {
        int i = this.metric.widthPixels / 7;
        this.viewPager = new ViewPager(this.mContext);
        this.viewPager.setId(55);
        this.calStartDate = getCalendarStartDate();
        CreateGirdView();
        layout.addView(this.viewPager, new RelativeLayout.LayoutParams(-1, (i * 6) + 5));
    }

    private void CreateGirdView() {
        this.mDayMessage.setText(generateTitle());
        this.firstCalendar = Calendar.getInstance();
        this.currentCalendar = Calendar.getInstance();
        this.lastCalendar = Calendar.getInstance();
        this.firstCalendar.setTime(this.calStartDate.getTime());
        this.currentCalendar.setTime(this.calStartDate.getTime());
        this.lastCalendar.setTime(this.calStartDate.getTime());
        this.firstGridView = new CalendarGridView(this.mContext);
        this.firstCalendar.add(2, -1);
        this.firstCalendar.get(2);
        this.currentGridView = new CalendarGridView(this.mContext);
        this.lastGridView = new CalendarGridView(this.mContext);
        this.lastCalendar.add(2, 1);
        setInitAdapter();
        if (this.viewPager.getChildCount() != 0) {
            this.viewPager.removeAllViews();
        }
        List<View> views = new ArrayList();
        views.add(this.firstGridView);
        views.add(this.currentGridView);
        views.add(this.lastGridView);
        this.viewPager.setAdapter(new MyViewPagerAdapter(views));
        this.viewPager.setCurrentItem(1);
        this.viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public void setInitAdapter() {
        this.firstGridAdapter = new CalendarGridViewAdapter(this.mContext, this.firstCalendar, this.mHDate, this.currDate, this.metric);
        this.firstGridView.setAdapter(this.firstGridAdapter);
        this.firstGridView.setId(55);
        this.currentGridAdapter = new CalendarGridViewAdapter(this.mContext, this.currentCalendar, this.mHDate, this.currDate, this.metric);
        this.currentGridView.setAdapter(this.currentGridAdapter);
        this.currentGridView.setId(55);
        this.lastGridAdapter = new CalendarGridViewAdapter(this.mContext, this.lastCalendar, this.mHDate, this.currDate, this.metric);
        this.lastGridView.setAdapter(this.lastGridAdapter);
        this.lastGridView.setId(55);
        this.currentGridView.setOnTouchListener(this);
        this.firstGridView.setOnTouchListener(this);
        this.lastGridView.setOnTouchListener(this);
    }

    private void setPrevViewItem() {
        this.mMonthViewCurrentMonth--;
        if (this.mMonthViewCurrentMonth == -1) {
            this.mMonthViewCurrentMonth = 11;
            this.mMonthViewCurrentYear--;
        }
        this.calStartDate.set(5, 1);
        this.calStartDate.set(2, this.mMonthViewCurrentMonth);
        this.calStartDate.set(1, this.mMonthViewCurrentYear);
    }

    private void setNextViewItem() {
        this.mMonthViewCurrentMonth++;
        if (this.mMonthViewCurrentMonth == 12) {
            this.mMonthViewCurrentMonth = 0;
            this.mMonthViewCurrentYear++;
        }
        this.calStartDate.set(5, 1);
        this.calStartDate.set(2, this.mMonthViewCurrentMonth);
        this.calStartDate.set(1, this.mMonthViewCurrentYear);
        this.mDayMessage.setText(generateTitle());
    }

    private void updateStartDateForMonth() {
        this.calStartDate.set(5, 1);
        this.mMonthViewCurrentMonth = this.calStartDate.get(2);
        this.mMonthViewCurrentYear = this.calStartDate.get(1);
        this.mDayMessage.setText(generateTitle());
        int iDay = 0;
        int iStartDay = 2;
        if (iStartDay == 2) {
            iDay = this.calStartDate.get(7) - 2;
            if (iDay < 0) {
                iDay = 6;
            }
        }
        if (iStartDay == 1) {
            iDay = this.calStartDate.get(7) - 1;
            if (iDay < 0) {
                iDay = 6;
            }
        }
        this.calStartDate.add(7, -iDay);
    }

    private Calendar getCalendarStartDate() {
        this.calToday.setTimeInMillis(System.currentTimeMillis());
        this.calToday.setFirstDayOfWeek(this.iFirstDayOfWeek);
        if (this.calSelected.getTimeInMillis() == 0) {
            this.calStartDate.setTimeInMillis(System.currentTimeMillis());
            this.calStartDate.setFirstDayOfWeek(this.iFirstDayOfWeek);
        } else {
            this.calStartDate.setTimeInMillis(this.calSelected.getTimeInMillis());
            this.calStartDate.setFirstDayOfWeek(this.iFirstDayOfWeek);
        }
        return this.calStartDate;
    }

    int getIndex() {
        return this.index;
    }

    private String leftPad_Tow_Zero(int str) {
        return new DecimalFormat("00").format((long) str);
    }

    private int[] getCurrentMouthDate(List<String> days) {
        int[] mDays = new int[days.size()];
        for (int i = 0; i < days.size(); i++) {
            mDays[i] = Tools.DataToInteger(Tools.stringToDate((String) days.get(i))).intValue();
        }
        return mDays;
    }

    public static int getCurrentDate() {
        Calendar mcalendar = Calendar.getInstance();
        return ((mcalendar.get(1) * 10000) + ((mcalendar.get(2) + 1) * 100)) + mcalendar.get(5);
    }

    public static Calendar stringToCal(String str) {
        Calendar calendar = Calendar.getInstance();
        String[] a = str.split(SocializeConstants.OP_DIVIDER_MINUS);
        calendar.set(Integer.valueOf(a[0]).intValue(), Integer.valueOf(a[1]).intValue() - 1, Integer.valueOf(a[2]).intValue());
        return calendar;
    }

    private String translateToEn(int month) {
        String monthEn = "";
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return monthEn;
        }
    }

    private String generateTitle() {
        String title = "";
        if (this.mContext.getResources().getConfiguration().locale.getLanguage().endsWith("en")) {
            return translateToEn(this.calStartDate.get(2) + 1) + " " + this.calStartDate.get(1);
        }
        return this.calStartDate.get(1) + this.mContext.getResources().getString(R.string.year) + leftPad_Tow_Zero(this.calStartDate.get(2) + 1) + this.mContext.getResources().getString(R.string.mouth);
    }
}
