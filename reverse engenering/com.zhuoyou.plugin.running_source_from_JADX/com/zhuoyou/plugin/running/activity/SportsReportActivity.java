package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.SportsReport.Response;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.ReportBean;
import com.zhuoyou.plugin.running.fragment.SportsReportFragment1;
import com.zhuoyou.plugin.running.fragment.SportsReportFragment2;
import com.zhuoyou.plugin.running.fragment.SportsReportFragment3;
import com.zhuoyou.plugin.running.tools.JsonUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyActionBar;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.VerticalViewPager;
import java.util.ArrayList;

public class SportsReportActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!SportsReportActivity.class.desiredAssertionStatus());
    public static final String ACTION_DATA_HAS_CHANGED = "com.droi.droihealth.ACTION_DATA_HAS_CHANGED";
    public static ReportBean reportBean;
    private ImageView btnTop;
    private ProgressDialog dialog;
    private ArrayList<Fragment> fragments = new ArrayList();
    private OnPageChangeListener onPageChangeListener = new C18256();
    private TextView tvTop;
    private VerticalViewPager viewPager;

    class C18201 implements OnClickListener {
        C18201() {
        }

        public void onClick(View v) {
            SportsReportActivity.this.getSportReport();
        }
    }

    class C18212 implements DroiCallback<Response> {
        C18212() {
        }

        public void result(Response response, DroiError droiError) {
            Tools.hideProgressDialog(SportsReportActivity.this.dialog);
            Log.i("zhuqichao", "code=" + response.code + ", time=" + response.time + ", result=" + JsonUtils.formatJson(response.result));
            if (!droiError.isOk()) {
                SportsReportActivity.this.showFailDialog(SportsReportActivity.this.getString(C1680R.string.sport_report_get_fail) + "[" + droiError.getCode() + "]");
            } else if (response.code == 1001) {
                SportsReportActivity.this.showNoDataDialog();
            } else if (response.code == -1) {
                SportsReportActivity.this.showFailDialog(SportsReportActivity.this.getString(C1680R.string.sport_report_get_fail));
            } else {
                ReportBean bean = JsonUtils.getSportReport(response.result);
                if (bean == null) {
                    SportsReportActivity.this.showFailDialog(SportsReportActivity.this.getString(C1680R.string.sport_report_get_fail));
                    return;
                }
                SportsReportActivity.reportBean = bean;
                SportsReportActivity.this.sendBroadcast(new Intent(SportsReportActivity.ACTION_DATA_HAS_CHANGED));
                Tools.makeToast((int) C1680R.string.sport_report_updated);
            }
        }
    }

    class C18223 implements MyAlertDialog.OnClickListener {
        C18223() {
        }

        public void onClick(int witch) {
            SportsReportActivity.this.finish();
        }
    }

    class C18234 implements MyAlertDialog.OnClickListener {
        C18234() {
        }

        public void onClick(int witch) {
            if (SportsReportActivity.reportBean == null) {
                SportsReportActivity.this.finish();
            }
        }
    }

    class C18245 implements MyAlertDialog.OnClickListener {
        C18245() {
        }

        public void onClick(int witch) {
            SportsReportActivity.this.getSportReport();
        }
    }

    class C18256 implements OnPageChangeListener {
        C18256() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            if (position == SportsReportActivity.this.fragments.size() - 1) {
                SportsReportActivity.this.tvTop.setVisibility(0);
                SportsReportActivity.this.btnTop.setImageResource(C1680R.drawable.ic_top);
                return;
            }
            SportsReportActivity.this.tvTop.setVisibility(8);
            SportsReportActivity.this.btnTop.setImageResource(C1680R.drawable.ic_nextpage);
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        FragmentAdapter(FragmentManager fm) {
            super(fm);
            SportsReportActivity.this.fragments.clear();
            SportsReportActivity.this.fragments.add(new SportsReportFragment1());
            SportsReportActivity.this.fragments.add(new SportsReportFragment2());
            SportsReportActivity.this.fragments.add(new SportsReportFragment3());
        }

        public Fragment getItem(int position) {
            return (Fragment) SportsReportActivity.this.fragments.get(position);
        }

        public int getCount() {
            return SportsReportActivity.this.fragments.size();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_sports_report);
        MyActionBar actionBar = (MyActionBar) findViewById(C1680R.id.action_bar);
        this.dialog = Tools.getProgressDialog(this);
        this.viewPager = (VerticalViewPager) findViewById(C1680R.id.view_pager);
        this.btnTop = (ImageView) findViewById(C1680R.id.btn_top);
        this.tvTop = (TextView) findViewById(C1680R.id.tv_top);
        this.viewPager.setOnPageChangeListener(this.onPageChangeListener);
        this.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        this.viewPager.setOffscreenPageLimit(3);
        if (reportBean == null) {
            getSportReport();
        }
        if ($assertionsDisabled || actionBar != null) {
            actionBar.setOnRightTitleClickListener(new C18201());
            return;
        }
        throw new AssertionError();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C1680R.id.btn_top:
                if (this.viewPager.getCurrentItem() < this.fragments.size() - 1) {
                    this.viewPager.setCurrentItem(this.viewPager.getCurrentItem() + 1, true);
                    return;
                } else {
                    this.viewPager.setCurrentItem(0, true);
                    return;
                }
            default:
                return;
        }
    }

    private void getSportReport() {
        Tools.showProgressDialog(this.dialog, getString(C1680R.string.sport_report_getting));
        BaasHelper.getSportsReportTotal(new C18212());
    }

    private void showNoDataDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.sport_report_no_data);
        dialog.setLeftButton((int) C1680R.string.string_ok, new C18223());
        dialog.show();
    }

    private void showFailDialog(String message) {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage(message);
        dialog.setLeftButton((int) C1680R.string.string_cancel, new C18234());
        dialog.setRightButton((int) C1680R.string.string_retry, new C18245());
        dialog.show();
    }
}
