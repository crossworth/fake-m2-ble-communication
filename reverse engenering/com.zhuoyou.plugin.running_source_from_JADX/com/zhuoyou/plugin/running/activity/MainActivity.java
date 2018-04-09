package com.zhuoyou.plugin.running.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.droi.greendao.bean.SportBean;
import com.droi.pedometer.sdk.PedometerService;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.umeng.socialize.UMShareAPI;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.BaasHelper.QQCallback;
import com.zhuoyou.plugin.running.baas.UploadCallBack;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.fragment.GroupFragment;
import com.zhuoyou.plugin.running.fragment.MainFragment;
import com.zhuoyou.plugin.running.fragment.TrainFragment;
import com.zhuoyou.plugin.running.fragment.UserFragment;
import com.zhuoyou.plugin.running.service.NotificationServiceNew;
import com.zhuoyou.plugin.running.tools.AnimUtils;
import com.zhuoyou.plugin.running.tools.DisplayUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyViewPager;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int UPDATE_TIME_DELAY = 3600000;
    private static final Handler mHandler = new Handler();
    private TextView[] mRadioButtons = new TextView[this.tabIds.length];
    private TextView[] mRadioButtons1 = new TextView[this.tab1Ids.length];
    private MyViewPager mViewPager;
    private OnPageChangeListener onPageChangeListener = new C17841();
    private Runnable rUpdateSportData = new C17873();
    private int[] tab1Ids = new int[]{C1680R.id.tab1_radio0, C1680R.id.tab1_radio1, C1680R.id.tab1_radio2, C1680R.id.tab1_radio3};
    private int[] tabIds = new int[]{C1680R.id.tab_radio0, C1680R.id.tab_radio1, C1680R.id.tab_radio2, C1680R.id.tab_radio3};

    class C17841 implements OnPageChangeListener {
        C17841() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            MainActivity.this.mRadioButtons[position].setAlpha(1.0f - positionOffset);
            MainActivity.this.mRadioButtons1[position].setAlpha(positionOffset);
            if (position < MainActivity.this.mRadioButtons.length - 1) {
                MainActivity.this.mRadioButtons[position + 1].setAlpha(positionOffset);
                MainActivity.this.mRadioButtons1[position + 1].setAlpha(1.0f - positionOffset);
            }
        }

        public void onPageSelected(int position) {
            for (int i = 0; i < MainActivity.this.tabIds.length; i++) {
                MainActivity.this.mRadioButtons[i].setAlpha(0.0f);
                MainActivity.this.mRadioButtons1[i].setAlpha(1.0f);
            }
            MainActivity.this.mRadioButtons[position].setAlpha(1.0f);
            MainActivity.this.mRadioButtons1[position].setAlpha(0.0f);
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    class C17852 implements QQCallback {
        C17852() {
        }

        public void callback(boolean success, int code, JSONObject obj) {
            if (success) {
                Tools.makeToast(MainActivity.this, (int) C1680R.string.qq_health_update_success);
            } else {
                Tools.makeToast(MainActivity.this, (int) C1680R.string.qq_health_update_fail);
            }
        }
    }

    class C17873 implements Runnable {

        class C17861 implements UploadCallBack<SportBean> {
            C17861() {
            }

            public void result(List<SportBean> list, DroiError error) {
                MainActivity.mHandler.removeCallbacks(MainActivity.this.rUpdateSportData);
                MainActivity.mHandler.postDelayed(MainActivity.this.rUpdateSportData, 3600000);
                Log.i("chenxin", "update QQ data run");
            }
        }

        C17873() {
        }

        public void run() {
            BaasHelper.uploadSportInBackground(new C17861());
        }
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments = new ArrayList();

        FragmentAdapter(FragmentManager fm) {
            super(fm);
            this.fragments.add(new MainFragment());
            this.fragments.add(new GroupFragment());
            this.fragments.add(new TrainFragment());
            this.fragments.add(new UserFragment());
        }

        public Fragment getItem(int position) {
            return (Fragment) this.fragments.get(position);
        }

        public int getCount() {
            return this.fragments.size();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_main);
        this.mViewPager = (MyViewPager) findViewById(C1680R.id.view_pager);
        this.mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        this.mViewPager.setOffscreenPageLimit(3);
        this.mViewPager.addOnPageChangeListener(this.onPageChangeListener);
        initTabButton();
        this.mViewPager.setCurrentItem(0);
        DroiUpdate.setUpdateAutoPopup(true);
        DroiUpdate.update(this);
        if (!SPUtils.isShowedTips() && getResources().getConfiguration().locale.getLanguage().equals("zh")) {
            showTips();
            SPUtils.setShowedTips(true);
        }
        startService(new Intent(this, NotificationServiceNew.class));
        NotificationServiceNew.toggleNotificationListenerService(this);
    }

    private void initTabButton() {
        for (int i = 0; i < this.tabIds.length; i++) {
            this.mRadioButtons[i] = (TextView) findViewById(this.tabIds[i]);
            this.mRadioButtons1[i] = (TextView) findViewById(this.tab1Ids[i]);
        }
    }

    public void onClick(View v) {
        for (int i = 0; i < this.tabIds.length; i++) {
            if (v.getId() == this.tabIds[i]) {
                this.mViewPager.setCurrentItem(i);
            }
        }
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        if (SPUtils.isPhonePed()) {
            startService(new Intent(this, PedometerService.class));
        } else {
            stopService(new Intent(this, PedometerService.class));
        }
        updateSportData();
    }

    private void updateSportData() {
        String from = getIntent().getStringExtra("from");
        if (TextUtils.isEmpty(from) || !"qqhealth".equals(from)) {
            mHandler.removeCallbacks(this.rUpdateSportData);
            mHandler.post(this.rUpdateSportData);
        } else {
            String accesstoken = getIntent().getStringExtra("accesstoken");
            String openid = getIntent().getStringExtra("openid");
            Log.i("chenxin", "accesstoken:" + accesstoken + " openid:" + openid + " from:" + from);
            String userId = DroiUser.getCurrentUser().getUserId();
            if (TextUtils.isEmpty(SPUtils.getQQOpenId(userId))) {
                Tools.makeToast((Context) this, (int) C1680R.string.qq_upate_with_no_account);
                getIntent().removeExtra("accesstoken");
                getIntent().removeExtra("openid");
                getIntent().removeExtra("from");
                return;
            } else if (SPUtils.getQQOpenId(userId).equals(openid)) {
                BaasHelper.updateQQHealthStep(new C17852());
            } else {
                Tools.makeToast((Context) this, (int) C1680R.string.qq_bind_other);
                getIntent().removeExtra("accesstoken");
                getIntent().removeExtra("openid");
                getIntent().removeExtra("from");
                return;
            }
        }
        getIntent().removeExtra("accesstoken");
        getIntent().removeExtra("openid");
        getIntent().removeExtra("from");
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mViewPager.removeOnPageChangeListener(this.onPageChangeListener);
        Log.i("zhuqichao", "MainActivity.onDestroy()");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void showTips() {
        final Dialog dialog = new Dialog(this, C1680R.style.FullScreenDialog);
        View convertView1 = getLayoutInflater().inflate(C1680R.layout.layout_tips1, null);
        final View convertView2 = getLayoutInflater().inflate(C1680R.layout.layout_tips2, null);
        dialog.setContentView(convertView1);
        setParams(dialog);
        final ImageView anim1 = (ImageView) convertView1.findViewById(C1680R.id.img_anim);
        final ImageView anim2 = (ImageView) convertView2.findViewById(C1680R.id.img_anim);
        ((ImageView) convertView2.findViewById(C1680R.id.btn_known)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                anim1.setImageResource(0);
                anim2.setImageResource(0);
                System.gc();
            }
        });
        AnimUtils.playAnimList(anim1.getDrawable());
        convertView1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.setContentView(convertView2);
                AnimUtils.playAnimList(anim2.getDrawable());
            }
        });
        dialog.show();
    }

    private void setParams(Dialog dialog) {
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setGravity(17);
        window.setWindowAnimations(0);
        LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(this).x;
        lp.height = DisplayUtils.getScreenMetrics(this).y;
        window.setAttributes(lp);
    }
}
