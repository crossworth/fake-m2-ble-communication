package com.zhuoyou.plugin.mainFrame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.umeng.analytics.MobclickAgent;
import com.zhuoyou.plugin.fitness.FitnessMain;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.view.BadgeView;

public class FinderFragment extends Fragment {
    private static final String TAG = "FinderFragment";
    private RelativeLayout actionLayout;
    private TextView actionText;
    BadgeView badge3;
    private BadgeView badgeView;
    private RelativeLayout circleLayout;
    private RelativeLayout eameyLayout;
    private RelativeLayout fitnessLayout;
    private Context mContext = RunningApp.getInstance().getApplicationContext();
    private View mView;
    private RelativeLayout qumeLayout;
    private RelativeLayout watchLayout;

    class C13021 implements OnClickListener {
        C13021() {
        }

        public void onClick(View v) {
            FinderFragment.this.startActivity(new Intent(FinderFragment.this.mContext, FitnessMain.class));
        }
    }

    class C13032 implements OnClickListener {
        C13032() {
        }

        public void onClick(View v) {
            Toast.makeText(FinderFragment.this.mContext, FinderFragment.this.getResources().getString(R.string.discover_show), 0).show();
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.finder_fragment, container, false);
        initView();
        return this.mView;
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        if (Tools.getActState(this.mContext)) {
            drawCircle();
        } else {
            cancleDrawCircle();
        }
        MobclickAgent.onPageStart(TAG);
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        this.fitnessLayout = (RelativeLayout) this.mView.findViewById(R.id.discover_fitness);
        this.circleLayout = (RelativeLayout) this.mView.findViewById(R.id.discover_circle);
        this.fitnessLayout.setOnClickListener(new C13021());
        this.circleLayout.setOnClickListener(new C13032());
        this.badgeView = new BadgeView(this.mContext, this.actionText);
    }

    public void drawCircle() {
        this.badgeView.setBackgroundResource(R.drawable.remind_circle);
        this.badgeView.setBadgeMargin(0, 0);
        this.badgeView.setWidth(8);
        this.badgeView.setHeight(8);
        this.badgeView.toggle(false);
    }

    public void cancleDrawCircle() {
        if (this.badgeView != null) {
            this.badgeView.toggle(true);
            this.badgeView = null;
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }
}
