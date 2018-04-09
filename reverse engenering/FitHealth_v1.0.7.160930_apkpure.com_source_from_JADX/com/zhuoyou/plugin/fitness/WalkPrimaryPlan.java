package com.zhuoyou.plugin.fitness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class WalkPrimaryPlan extends Activity {
    private String[] eighth_week_plan = new String[7];
    private String[] fifth_week_plan = new String[7];
    private String[] first_week_plan = new String[7];
    private String[] fourth_week_plan = new String[7];
    private RelativeLayout im_back;
    private Intent intent;
    private ListView lv_eighth_week;
    private ListView lv_fifth_week;
    private ListView lv_first_week;
    private ListView lv_fourth_week;
    private ListView lv_second_week;
    private ListView lv_seventh_week;
    private ListView lv_sixth_week;
    private ListView lv_third_week;
    private String[] second_week_plan = new String[7];
    private String[] seventh_week_plan = new String[7];
    private String[] sixth_week_plan = new String[7];
    private String[] third_week_plan = new String[7];
    private int tmp = 0;
    private TextView tv_eighth_week;
    private TextView tv_fifth_week;
    private TextView tv_first_week;
    private TextView tv_fourth_week;
    private TextView tv_second_week;
    private TextView tv_seventh_week;
    private TextView tv_sixth_week;
    private TextView tv_third_week;
    private TextView tv_title;

    class C12511 implements OnClickListener {
        C12511() {
        }

        public void onClick(View v) {
            WalkPrimaryPlan.this.finish();
        }
    }

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
            View view = LayoutInflater.from(WalkPrimaryPlan.this).inflate(R.layout.week_list_item, null);
            TextView tv = (TextView) view.findViewById(R.id.textView1);
            if (this.arr[position].equals(WalkPrimaryPlan.this.getResources().getString(R.string.day_of_rest))) {
                tv.setGravity(17);
                tv.setTextColor(-1);
                tv.setBackgroundResource(R.drawable.rest_bg);
            }
            tv.setText(this.arr[position]);
            return view;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_primary_plan);
        this.intent = getIntent();
        this.tmp = this.intent.getIntExtra("tmp", -1);
        this.lv_first_week = (ListView) findViewById(R.id.lv_first_week);
        this.lv_second_week = (ListView) findViewById(R.id.lv_second_week);
        this.lv_third_week = (ListView) findViewById(R.id.lv_third_week);
        this.lv_fourth_week = (ListView) findViewById(R.id.lv_fourth_week);
        this.lv_fifth_week = (ListView) findViewById(R.id.lv_fifth_week);
        this.lv_sixth_week = (ListView) findViewById(R.id.lv_sixth_week);
        this.lv_seventh_week = (ListView) findViewById(R.id.lv_seventh_week);
        this.lv_eighth_week = (ListView) findViewById(R.id.lv_eighth_week);
        this.tv_first_week = (TextView) findViewById(R.id.tv_first_week);
        this.tv_second_week = (TextView) findViewById(R.id.tv_second_week);
        this.tv_third_week = (TextView) findViewById(R.id.tv_third_week);
        this.tv_fourth_week = (TextView) findViewById(R.id.tv_fourth_week);
        this.tv_fifth_week = (TextView) findViewById(R.id.tv_fifth_week);
        this.tv_sixth_week = (TextView) findViewById(R.id.tv_sixth_week);
        this.tv_seventh_week = (TextView) findViewById(R.id.tv_seventh_week);
        this.tv_eighth_week = (TextView) findViewById(R.id.tv_eighth_week);
        this.tv_title = (TextView) findViewById(R.id.title);
        this.tv_title.setText(R.string.walk_primary_plan);
        this.im_back = (RelativeLayout) findViewById(R.id.back);
        this.im_back.setOnClickListener(new C12511());
        getWeekPlan(this.tmp);
        this.lv_first_week.setAdapter(new MyAdapter(this.first_week_plan));
        this.lv_second_week.setAdapter(new MyAdapter(this.second_week_plan));
        this.lv_third_week.setAdapter(new MyAdapter(this.third_week_plan));
        this.lv_fourth_week.setAdapter(new MyAdapter(this.fourth_week_plan));
    }

    public void getWeekPlan(int tmp) {
        switch (tmp) {
            case 1:
                this.tv_first_week.setBackgroundResource(R.drawable.week_bg_1);
                this.tv_second_week.setBackgroundResource(R.drawable.week_bg_1);
                this.tv_third_week.setBackgroundResource(R.drawable.week_bg_1);
                this.tv_fourth_week.setBackgroundResource(R.drawable.week_bg_1);
                this.tv_fifth_week.setVisibility(8);
                this.tv_sixth_week.setVisibility(8);
                this.tv_seventh_week.setVisibility(8);
                this.tv_eighth_week.setVisibility(8);
                this.lv_fifth_week.setVisibility(8);
                this.lv_sixth_week.setVisibility(8);
                this.lv_seventh_week.setVisibility(8);
                this.lv_eighth_week.setVisibility(8);
                this.first_week_plan = getResources().getStringArray(R.array.walk_first_week_plan);
                this.second_week_plan = getResources().getStringArray(R.array.walk_second_week_plan);
                this.third_week_plan = getResources().getStringArray(R.array.walk_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(R.array.walk_fourth_week_plan);
                return;
            case 2:
                this.tv_first_week.setBackgroundResource(R.drawable.week_bg_2);
                this.tv_second_week.setBackgroundResource(R.drawable.week_bg_2);
                this.tv_third_week.setBackgroundResource(R.drawable.week_bg_2);
                this.tv_fourth_week.setBackgroundResource(R.drawable.week_bg_2);
                this.tv_fifth_week.setVisibility(8);
                this.tv_sixth_week.setVisibility(8);
                this.tv_seventh_week.setVisibility(8);
                this.tv_eighth_week.setVisibility(8);
                this.lv_fifth_week.setVisibility(8);
                this.lv_sixth_week.setVisibility(8);
                this.lv_seventh_week.setVisibility(8);
                this.lv_eighth_week.setVisibility(8);
                this.first_week_plan = getResources().getStringArray(R.array.primary_run_first_week_plan);
                this.second_week_plan = getResources().getStringArray(R.array.primary_run_second_week_plan);
                this.third_week_plan = getResources().getStringArray(R.array.primary_run_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(R.array.primary_run_fourth_week_plan);
                this.tv_title.setText(R.string.run_primary_plan);
                return;
            case 3:
                this.tv_first_week.setBackgroundResource(R.drawable.week_bg_3);
                this.tv_second_week.setBackgroundResource(R.drawable.week_bg_3);
                this.tv_third_week.setBackgroundResource(R.drawable.week_bg_3);
                this.tv_fourth_week.setBackgroundResource(R.drawable.week_bg_3);
                this.tv_fifth_week.setBackgroundResource(R.drawable.week_bg_3);
                this.tv_sixth_week.setBackgroundResource(R.drawable.week_bg_3);
                this.tv_fifth_week.setVisibility(0);
                this.tv_sixth_week.setVisibility(0);
                this.tv_seventh_week.setVisibility(8);
                this.tv_eighth_week.setVisibility(8);
                this.lv_fifth_week.setVisibility(0);
                this.lv_sixth_week.setVisibility(0);
                this.lv_seventh_week.setVisibility(8);
                this.lv_eighth_week.setVisibility(8);
                this.first_week_plan = getResources().getStringArray(R.array.advance_run_first_week_plan);
                this.second_week_plan = getResources().getStringArray(R.array.advance_run_second_week_plan);
                this.third_week_plan = getResources().getStringArray(R.array.advance_run_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(R.array.advance_run_fourth_week_plan);
                this.fifth_week_plan = getResources().getStringArray(R.array.advance_run_fifth_week_plan);
                this.sixth_week_plan = getResources().getStringArray(R.array.advance_run_sixth_week_plan);
                this.lv_fifth_week.setAdapter(new MyAdapter(this.fifth_week_plan));
                this.lv_sixth_week.setAdapter(new MyAdapter(this.sixth_week_plan));
                this.tv_title.setText(R.string.run_advance_plan);
                return;
            case 4:
                this.tv_first_week.setBackgroundResource(R.drawable.week_bg_4);
                this.tv_second_week.setBackgroundResource(R.drawable.week_bg_4);
                this.tv_third_week.setBackgroundResource(R.drawable.week_bg_4);
                this.tv_fourth_week.setBackgroundResource(R.drawable.week_bg_4);
                this.tv_fifth_week.setBackgroundResource(R.drawable.week_bg_4);
                this.tv_sixth_week.setBackgroundResource(R.drawable.week_bg_4);
                this.tv_seventh_week.setBackgroundResource(R.drawable.week_bg_4);
                this.tv_eighth_week.setBackgroundResource(R.drawable.week_bg_4);
                this.tv_fifth_week.setVisibility(0);
                this.tv_sixth_week.setVisibility(0);
                this.tv_seventh_week.setVisibility(0);
                this.tv_eighth_week.setVisibility(0);
                this.lv_fifth_week.setVisibility(0);
                this.lv_sixth_week.setVisibility(0);
                this.lv_seventh_week.setVisibility(0);
                this.lv_eighth_week.setVisibility(0);
                this.first_week_plan = getResources().getStringArray(R.array.first_marathon_first_week_plan);
                this.second_week_plan = getResources().getStringArray(R.array.first_marathon_second_week_plan);
                this.third_week_plan = getResources().getStringArray(R.array.first_marathon_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(R.array.first_marathon_fourth_week_plan);
                this.fifth_week_plan = getResources().getStringArray(R.array.first_marathon_fifth_week_plan);
                this.sixth_week_plan = getResources().getStringArray(R.array.first_marathon_sixth_week_plan);
                this.seventh_week_plan = getResources().getStringArray(R.array.first_marathon_seventh_week_plan);
                this.eighth_week_plan = getResources().getStringArray(R.array.first_marathon_eighth_week_plan);
                this.lv_fifth_week.setAdapter(new MyAdapter(this.fifth_week_plan));
                this.lv_sixth_week.setAdapter(new MyAdapter(this.sixth_week_plan));
                this.lv_seventh_week.setAdapter(new MyAdapter(this.seventh_week_plan));
                this.lv_eighth_week.setAdapter(new MyAdapter(this.eighth_week_plan));
                this.tv_title.setText(R.string.first_five_km);
                return;
            case 5:
                this.tv_first_week.setBackgroundResource(R.drawable.week_bg_5);
                this.tv_second_week.setBackgroundResource(R.drawable.week_bg_5);
                this.tv_third_week.setBackgroundResource(R.drawable.week_bg_5);
                this.tv_fourth_week.setBackgroundResource(R.drawable.week_bg_5);
                this.tv_fifth_week.setBackgroundResource(R.drawable.week_bg_5);
                this.tv_sixth_week.setBackgroundResource(R.drawable.week_bg_5);
                this.tv_seventh_week.setBackgroundResource(R.drawable.week_bg_5);
                this.tv_eighth_week.setBackgroundResource(R.drawable.week_bg_5);
                this.tv_fifth_week.setVisibility(0);
                this.tv_sixth_week.setVisibility(0);
                this.tv_seventh_week.setVisibility(0);
                this.tv_eighth_week.setVisibility(0);
                this.lv_fifth_week.setVisibility(0);
                this.lv_sixth_week.setVisibility(0);
                this.lv_seventh_week.setVisibility(0);
                this.lv_eighth_week.setVisibility(0);
                this.first_week_plan = getResources().getStringArray(R.array.beyond_marathon_first_week_plan);
                this.second_week_plan = getResources().getStringArray(R.array.beyond_marathon_second_week_plan);
                this.third_week_plan = getResources().getStringArray(R.array.beyond_marathon_third_week_plan);
                this.fourth_week_plan = getResources().getStringArray(R.array.beyond_marathon_fourth_week_plan);
                this.fifth_week_plan = getResources().getStringArray(R.array.beyond_marathon_fifth_week_plan);
                this.sixth_week_plan = getResources().getStringArray(R.array.beyond_marathon_sixth_week_plan);
                this.seventh_week_plan = getResources().getStringArray(R.array.beyond_marathon_seventh_week_plan);
                this.eighth_week_plan = getResources().getStringArray(R.array.beyond_marathon_eighth_week_plan);
                this.lv_fifth_week.setAdapter(new MyAdapter(this.fifth_week_plan));
                this.lv_sixth_week.setAdapter(new MyAdapter(this.sixth_week_plan));
                this.lv_seventh_week.setAdapter(new MyAdapter(this.seventh_week_plan));
                this.lv_eighth_week.setAdapter(new MyAdapter(this.eighth_week_plan));
                this.tv_title.setText(R.string.beyond_marathon);
                return;
            default:
                return;
        }
    }
}
