package com.zhuoyou.plugin.fitness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class FitnessMain extends Activity implements OnClickListener {
    private LinearLayout llayout_advance_run;
    private LinearLayout llayout_beyond_marathon;
    private LinearLayout llayout_first_5km;
    private LinearLayout llayout_primary_run;
    private LinearLayout llayout_walk;

    class C12501 implements OnClickListener {
        C12501() {
        }

        public void onClick(View v) {
            FitnessMain.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_main);
        ((TextView) findViewById(R.id.title)).setText(R.string.fitness_plan);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C12501());
        this.llayout_walk = (LinearLayout) findViewById(R.id.llayout_walk);
        this.llayout_primary_run = (LinearLayout) findViewById(R.id.llayout_primary_run);
        this.llayout_advance_run = (LinearLayout) findViewById(R.id.llayout_advance_run);
        this.llayout_first_5km = (LinearLayout) findViewById(R.id.llayout_first_5km);
        this.llayout_beyond_marathon = (LinearLayout) findViewById(R.id.llayout_beyond_marathon);
        this.llayout_walk.setOnClickListener(this);
        this.llayout_primary_run.setOnClickListener(this);
        this.llayout_advance_run.setOnClickListener(this);
        this.llayout_first_5km.setOnClickListener(this);
        this.llayout_beyond_marathon.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llayout_walk:
                Intent walk_intent = new Intent();
                walk_intent.putExtra("tmp", 1);
                walk_intent.setClass(this, WalkPrimaryPlan.class);
                startActivity(walk_intent);
                return;
            case R.id.llayout_primary_run:
                Intent primary_run_intent = new Intent();
                primary_run_intent.putExtra("tmp", 2);
                primary_run_intent.setClass(this, WalkPrimaryPlan.class);
                startActivity(primary_run_intent);
                return;
            case R.id.llayout_advance_run:
                Intent advance_run_intent = new Intent();
                advance_run_intent.putExtra("tmp", 3);
                advance_run_intent.setClass(this, WalkPrimaryPlan.class);
                startActivity(advance_run_intent);
                return;
            case R.id.llayout_first_5km:
                Intent first_marathon_intent = new Intent();
                first_marathon_intent.putExtra("tmp", 4);
                first_marathon_intent.setClass(this, WalkPrimaryPlan.class);
                startActivity(first_marathon_intent);
                return;
            case R.id.llayout_beyond_marathon:
                Intent beyond_marathon_intent = new Intent();
                beyond_marathon_intent.putExtra("tmp", 5);
                beyond_marathon_intent.setClass(this, WalkPrimaryPlan.class);
                startActivity(beyond_marathon_intent);
                return;
            default:
                return;
        }
    }
}
