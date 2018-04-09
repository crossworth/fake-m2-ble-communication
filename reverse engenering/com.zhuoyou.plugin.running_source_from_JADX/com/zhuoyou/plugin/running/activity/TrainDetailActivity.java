package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.view.MyActionBar;

public class TrainDetailActivity extends BaseActivity {
    private MyActionBar actionBar;
    private String[] first_week_plan = new String[7];
    private String[] fourth_week_plan = new String[7];
    private ListView lv_eighth_week;
    private ListView lv_fifth_week;
    private ListView lv_first_week;
    private ListView lv_fourth_week;
    private ListView lv_second_week;
    private ListView lv_seventh_week;
    private ListView lv_sixth_week;
    private ListView lv_third_week;
    private String[] second_week_plan = new String[7];
    private String[] third_week_plan = new String[7];
    private TextView tv_eighth_week;
    private TextView tv_fifth_week;
    private TextView tv_first_week;
    private TextView tv_fourth_week;
    private TextView tv_second_week;
    private TextView tv_seventh_week;
    private TextView tv_sixth_week;
    private TextView tv_third_week;

    private class MyAdapter extends BaseAdapter {
        String[] arr = new String[7];

        public boolean isEnabled(int position) {
            return false;
        }

        public MyAdapter(String[] arr) {
            this.arr = arr;
        }

        public int getCount() {
            return this.arr.length;
        }

        public Object getItem(int position) {
            return this.arr[position];
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(TrainDetailActivity.this).inflate(C1680R.layout.item_train_week_list, null);
            TextView tv = (TextView) view.findViewById(C1680R.id.textView1);
            if (this.arr[position].equals(TrainDetailActivity.this.getString(C1680R.string.train_week_day_of_rest))) {
                tv.setGravity(17);
                tv.setTextColor(-1);
                tv.setBackgroundResource(C1680R.drawable.train_rest_bg);
            }
            tv.setText(this.arr[position]);
            return view;
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int tmp = getIntent().getIntExtra("tmp", -1);
        changeThem(tmp);
        setContentView((int) C1680R.layout.activity_train_detail);
        this.lv_first_week = (ListView) findViewById(C1680R.id.lv_first_week);
        this.lv_first_week.setFocusable(false);
        this.lv_second_week = (ListView) findViewById(C1680R.id.lv_second_week);
        this.lv_second_week.setFocusable(false);
        this.lv_third_week = (ListView) findViewById(C1680R.id.lv_third_week);
        this.lv_fourth_week = (ListView) findViewById(C1680R.id.lv_fourth_week);
        this.lv_fifth_week = (ListView) findViewById(C1680R.id.lv_fifth_week);
        this.lv_sixth_week = (ListView) findViewById(C1680R.id.lv_sixth_week);
        this.lv_seventh_week = (ListView) findViewById(C1680R.id.lv_seventh_week);
        this.lv_eighth_week = (ListView) findViewById(C1680R.id.lv_eighth_week);
        this.lv_third_week.setFocusable(false);
        this.lv_fourth_week.setFocusable(false);
        this.lv_fifth_week.setFocusable(false);
        this.lv_sixth_week.setFocusable(false);
        this.lv_seventh_week.setFocusable(false);
        this.lv_eighth_week.setFocusable(false);
        this.tv_first_week = (TextView) findViewById(C1680R.id.tv_first_week);
        this.tv_second_week = (TextView) findViewById(C1680R.id.tv_second_week);
        this.tv_third_week = (TextView) findViewById(C1680R.id.tv_third_week);
        this.tv_fourth_week = (TextView) findViewById(C1680R.id.tv_fourth_week);
        this.tv_fifth_week = (TextView) findViewById(C1680R.id.tv_fifth_week);
        this.tv_sixth_week = (TextView) findViewById(C1680R.id.tv_sixth_week);
        this.tv_seventh_week = (TextView) findViewById(C1680R.id.tv_seventh_week);
        this.tv_eighth_week = (TextView) findViewById(C1680R.id.tv_eighth_week);
        this.actionBar = (MyActionBar) findViewById(C1680R.id.actionbar);
        getWeekPlan(tmp);
        this.lv_first_week.setAdapter(new MyAdapter(this.first_week_plan));
        this.lv_second_week.setAdapter(new MyAdapter(this.second_week_plan));
        this.lv_third_week.setAdapter(new MyAdapter(this.third_week_plan));
        this.lv_fourth_week.setAdapter(new MyAdapter(this.fourth_week_plan));
    }

    public void getWeekPlan(int tmp) {
        String[] fifth_week_plan;
        String[] sixth_week_plan;
        String[] seventh_week_plan;
        String[] eighth_week_plan;
        switch (tmp) {
            case 1:
                this.tv_first_week.setBackgroundResource(C1680R.drawable.train_week_bg_1);
                this.tv_second_week.setBackgroundResource(C1680R.drawable.train_week_bg_1);
                this.tv_third_week.setBackgroundResource(C1680R.drawable.train_week_bg_1);
                this.tv_fourth_week.setBackgroundResource(C1680R.drawable.train_week_bg_1);
                this.tv_fifth_week.setVisibility(8);
                this.tv_sixth_week.setVisibility(8);
                this.tv_seventh_week.setVisibility(8);
                this.tv_eighth_week.setVisibility(8);
                this.lv_fifth_week.setVisibility(8);
                this.lv_sixth_week.setVisibility(8);
                this.lv_seventh_week.setVisibility(8);
                this.lv_eighth_week.setVisibility(8);
                this.first_week_plan = getResources().getStringArray(C1680R.array.walk_first_week_plan);
                this.second_week_plan = getResources().getStringArray(C1680R.array.walk_second_week_plan);
                this.third_week_plan = getResources().getStringArray(C1680R.array.walk_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(C1680R.array.walk_fourth_week_plan);
                this.actionBar.setTitle((int) C1680R.string.train_plan_walk_primary);
                this.actionBar.setBackgroundResource(C1680R.color.train_detail_style_color1);
                return;
            case 2:
                this.tv_first_week.setBackgroundResource(C1680R.drawable.train_week_bg_2);
                this.tv_second_week.setBackgroundResource(C1680R.drawable.train_week_bg_2);
                this.tv_third_week.setBackgroundResource(C1680R.drawable.train_week_bg_2);
                this.tv_fourth_week.setBackgroundResource(C1680R.drawable.train_week_bg_2);
                this.tv_fifth_week.setVisibility(8);
                this.tv_sixth_week.setVisibility(8);
                this.tv_seventh_week.setVisibility(8);
                this.tv_eighth_week.setVisibility(8);
                this.lv_fifth_week.setVisibility(8);
                this.lv_sixth_week.setVisibility(8);
                this.lv_seventh_week.setVisibility(8);
                this.lv_eighth_week.setVisibility(8);
                this.first_week_plan = getResources().getStringArray(C1680R.array.primary_run_first_week_plan);
                this.second_week_plan = getResources().getStringArray(C1680R.array.primary_run_second_week_plan);
                this.third_week_plan = getResources().getStringArray(C1680R.array.primary_run_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(C1680R.array.primary_run_fourth_week_plan);
                this.actionBar.setTitle((int) C1680R.string.train_plan_run_primary);
                this.actionBar.setBackgroundResource(C1680R.color.train_detail_style_color2);
                return;
            case 3:
                this.tv_first_week.setBackgroundResource(C1680R.drawable.train_week_bg_3);
                this.tv_second_week.setBackgroundResource(C1680R.drawable.train_week_bg_3);
                this.tv_third_week.setBackgroundResource(C1680R.drawable.train_week_bg_3);
                this.tv_fourth_week.setBackgroundResource(C1680R.drawable.train_week_bg_3);
                this.tv_fifth_week.setBackgroundResource(C1680R.drawable.train_week_bg_3);
                this.tv_sixth_week.setBackgroundResource(C1680R.drawable.train_week_bg_3);
                this.tv_fifth_week.setVisibility(0);
                this.tv_sixth_week.setVisibility(0);
                this.tv_seventh_week.setVisibility(8);
                this.tv_eighth_week.setVisibility(8);
                this.lv_fifth_week.setVisibility(0);
                this.lv_sixth_week.setVisibility(0);
                this.lv_seventh_week.setVisibility(8);
                this.lv_eighth_week.setVisibility(8);
                this.first_week_plan = getResources().getStringArray(C1680R.array.advance_run_first_week_plan);
                this.second_week_plan = getResources().getStringArray(C1680R.array.advance_run_second_week_plan);
                this.third_week_plan = getResources().getStringArray(C1680R.array.advance_run_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(C1680R.array.advance_run_fourth_week_plan);
                fifth_week_plan = getResources().getStringArray(C1680R.array.advance_run_fifth_week_plan);
                sixth_week_plan = getResources().getStringArray(C1680R.array.advance_run_sixth_week_plan);
                this.lv_fifth_week.setAdapter(new MyAdapter(fifth_week_plan));
                this.lv_sixth_week.setAdapter(new MyAdapter(sixth_week_plan));
                this.actionBar.setTitle((int) C1680R.string.train_plan_run_advance);
                this.actionBar.setBackgroundResource(C1680R.color.train_detail_style_color3);
                return;
            case 4:
                this.tv_first_week.setBackgroundResource(C1680R.drawable.train_week_bg_4);
                this.tv_second_week.setBackgroundResource(C1680R.drawable.train_week_bg_4);
                this.tv_third_week.setBackgroundResource(C1680R.drawable.train_week_bg_4);
                this.tv_fourth_week.setBackgroundResource(C1680R.drawable.train_week_bg_4);
                this.tv_fifth_week.setBackgroundResource(C1680R.drawable.train_week_bg_4);
                this.tv_sixth_week.setBackgroundResource(C1680R.drawable.train_week_bg_4);
                this.tv_seventh_week.setBackgroundResource(C1680R.drawable.train_week_bg_4);
                this.tv_eighth_week.setBackgroundResource(C1680R.drawable.train_week_bg_4);
                this.tv_fifth_week.setVisibility(0);
                this.tv_sixth_week.setVisibility(0);
                this.tv_seventh_week.setVisibility(0);
                this.tv_eighth_week.setVisibility(0);
                this.lv_fifth_week.setVisibility(0);
                this.lv_sixth_week.setVisibility(0);
                this.lv_seventh_week.setVisibility(0);
                this.lv_eighth_week.setVisibility(0);
                this.first_week_plan = getResources().getStringArray(C1680R.array.first_marathon_first_week_plan);
                this.second_week_plan = getResources().getStringArray(C1680R.array.first_marathon_second_week_plan);
                this.third_week_plan = getResources().getStringArray(C1680R.array.first_marathon_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(C1680R.array.first_marathon_fourth_week_plan);
                fifth_week_plan = getResources().getStringArray(C1680R.array.first_marathon_fifth_week_plan);
                sixth_week_plan = getResources().getStringArray(C1680R.array.first_marathon_sixth_week_plan);
                seventh_week_plan = getResources().getStringArray(C1680R.array.first_marathon_seventh_week_plan);
                eighth_week_plan = getResources().getStringArray(C1680R.array.first_marathon_eighth_week_plan);
                this.lv_fifth_week.setAdapter(new MyAdapter(fifth_week_plan));
                this.lv_sixth_week.setAdapter(new MyAdapter(sixth_week_plan));
                this.lv_seventh_week.setAdapter(new MyAdapter(seventh_week_plan));
                this.lv_eighth_week.setAdapter(new MyAdapter(eighth_week_plan));
                this.actionBar.setTitle((int) C1680R.string.train_plan_first_5km);
                this.actionBar.setBackgroundResource(C1680R.color.train_detail_style_color4);
                return;
            case 5:
                this.tv_first_week.setBackgroundResource(C1680R.drawable.train_week_bg_5);
                this.tv_second_week.setBackgroundResource(C1680R.drawable.train_week_bg_5);
                this.tv_third_week.setBackgroundResource(C1680R.drawable.train_week_bg_5);
                this.tv_fourth_week.setBackgroundResource(C1680R.drawable.train_week_bg_5);
                this.tv_fifth_week.setBackgroundResource(C1680R.drawable.train_week_bg_5);
                this.tv_sixth_week.setBackgroundResource(C1680R.drawable.train_week_bg_5);
                this.tv_seventh_week.setBackgroundResource(C1680R.drawable.train_week_bg_5);
                this.tv_eighth_week.setBackgroundResource(C1680R.drawable.train_week_bg_5);
                this.tv_fifth_week.setVisibility(0);
                this.tv_sixth_week.setVisibility(0);
                this.tv_seventh_week.setVisibility(0);
                this.tv_eighth_week.setVisibility(0);
                this.lv_fifth_week.setVisibility(0);
                this.lv_sixth_week.setVisibility(0);
                this.lv_seventh_week.setVisibility(0);
                this.lv_eighth_week.setVisibility(0);
                this.first_week_plan = getResources().getStringArray(C1680R.array.beyond_marathon_first_week_plan);
                this.second_week_plan = getResources().getStringArray(C1680R.array.beyond_marathon_second_week_plan);
                this.third_week_plan = getResources().getStringArray(C1680R.array.beyond_marathon_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(C1680R.array.beyond_marathon_fourth_week_plan);
                fifth_week_plan = getResources().getStringArray(C1680R.array.beyond_marathon_fifth_week_plan);
                sixth_week_plan = getResources().getStringArray(C1680R.array.beyond_marathon_sixth_week_plan);
                seventh_week_plan = getResources().getStringArray(C1680R.array.beyond_marathon_seventh_week_plan);
                eighth_week_plan = getResources().getStringArray(C1680R.array.beyond_marathon_eighth_week_plan);
                this.lv_fifth_week.setAdapter(new MyAdapter(fifth_week_plan));
                this.lv_sixth_week.setAdapter(new MyAdapter(sixth_week_plan));
                this.lv_seventh_week.setAdapter(new MyAdapter(seventh_week_plan));
                this.lv_eighth_week.setAdapter(new MyAdapter(eighth_week_plan));
                this.actionBar.setTitle((int) C1680R.string.train_plan_marathon);
                this.actionBar.setBackgroundResource(C1680R.color.train_detail_style_color5);
                return;
            default:
                return;
        }
    }

    private void changeThem(int tmp) {
        switch (tmp) {
            case 1:
                setTheme(C1680R.style.AppTheme.TrainDetail1);
                return;
            case 2:
                setTheme(C1680R.style.AppTheme.TrainDetail2);
                return;
            case 3:
                setTheme(C1680R.style.AppTheme.TrainDetail3);
                return;
            case 4:
                setTheme(C1680R.style.AppTheme.TrainDetail4);
                return;
            case 5:
                setTheme(C1680R.style.AppTheme.TrainDetail5);
                return;
            default:
                return;
        }
    }
}
