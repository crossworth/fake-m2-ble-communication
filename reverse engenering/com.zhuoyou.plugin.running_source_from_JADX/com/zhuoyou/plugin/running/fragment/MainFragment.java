package com.zhuoyou.plugin.running.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtDevice.ConnectCallback;
import com.droi.btlib.service.BtManagerService;
import com.droi.btlib.service.BtManagerService.CONNECT_STATE;
import com.droi.btlib.service.BtManagerService.END_STATE;
import com.droi.btlib.service.BtManagerService.GetSleepInfoCallback;
import com.droi.btlib.service.BtManagerService.GetSubStepsCallback;
import com.droi.btlib.service.BtManagerService.GetTotalStepCallback;
import com.droi.btlib.service.SleepInfo;
import com.droi.btlib.service.SubStep;
import com.droi.greendao.bean.HeartBean;
import com.droi.greendao.bean.SleepBean;
import com.droi.greendao.bean.SportBean;
import com.droi.pedometer.sdk.PedometerService;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiPreference;
import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnHeaderSizeListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.GPSFirstActivity;
import com.zhuoyou.plugin.running.activity.HeartAddActivity;
import com.zhuoyou.plugin.running.activity.SleepOneActivity;
import com.zhuoyou.plugin.running.activity.SportOneActivity;
import com.zhuoyou.plugin.running.activity.SportsReportActivity;
import com.zhuoyou.plugin.running.activity.StepActivityActivity;
import com.zhuoyou.plugin.running.activity.TopicActivity;
import com.zhuoyou.plugin.running.activity.WaterReminderActivity;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.base.BaseFragment;
import com.zhuoyou.plugin.running.bean.EventGetHeart;
import com.zhuoyou.plugin.running.bean.EventGetSleep;
import com.zhuoyou.plugin.running.bean.EventGetStatis;
import com.zhuoyou.plugin.running.bean.EventStep;
import com.zhuoyou.plugin.running.database.HeartHelper;
import com.zhuoyou.plugin.running.database.SleepHelper;
import com.zhuoyou.plugin.running.database.SportHelper;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.SystemBarConfig;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.StepView;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainFragment extends BaseFragment implements OnClickListener {
    private LayoutParams backParams;
    private BtDevice btDeivce;
    private ConnectCallback callback = new C18989();
    private String dataSync;
    private ImageView imgBack;
    private ImageView imgEvents;
    private LoadingLayoutProxy loading;
    private int menuHeight;
    private LinearLayout menuLayuout;
    private LayoutParams menuParams;
    private PullToRefreshScrollView scrollView;
    private GetSleepInfoCallback sleepInfoCallback = new C18978();
    private StepView stepView;
    private StringBuilder subStepStr = new StringBuilder();
    private GetSubStepsCallback subStepsCallback = new C18967();
    private boolean syncing;
    private GetTotalStepCallback totalStepCallback = new C18956();
    private TextView tvHeart;
    private TextView tvRun;
    private TextView tvSleep;
    private TextView tvVitalCap;
    private TextView tvWater;

    class C18901 implements OnHeaderSizeListener {
        C18901() {
        }

        public void onHeaderSize(int size) {
            MainFragment.this.updateMenuLayout(size);
        }
    }

    class C18912 implements OnRefreshListener<ScrollView> {
        C18912() {
        }

        public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
            Log.i("zhuqichao", "下拉刷新");
            if (MainFragment.this.syncing) {
                Tools.makeToast((int) C1680R.string.main_data_is_syncing);
                MainFragment.this.scrollView.onRefreshComplete();
                return;
            }
            MainFragment.this.btDeivce = BtManagerService.getConnectDevice(MainFragment.this.callback);
            if (MainFragment.this.btDeivce != null) {
                MainFragment.this.updateDevice();
            } else if (SPUtils.isPhonePed()) {
                Tools.makeToast((int) C1680R.string.main_data_sync_not_need);
                MainFragment.this.scrollView.onRefreshComplete();
                MainFragment.this.syncing = false;
            } else {
                MainFragment.this.scrollView.onRefreshComplete();
                MainFragment.this.syncing = false;
                MainFragment.this.showOpenPhoneStepDialog();
            }
        }
    }

    class C18923 implements DroiCallback<Boolean> {
        C18923() {
        }

        public void result(Boolean aBoolean, DroiError droiError) {
            if (droiError.isOk()) {
                MainFragment.this.acitiviyCheck();
            }
        }
    }

    class C18934 implements MyAlertDialog.OnClickListener {
        C18934() {
        }

        public void onClick(int witch) {
            BtManagerService.openBluetooth();
        }
    }

    class C18945 implements MyAlertDialog.OnClickListener {
        C18945() {
        }

        public void onClick(int witch) {
            SPUtils.setPhonePed(true);
            MainFragment.this.getContext().startService(new Intent(MainFragment.this.getContext(), PedometerService.class));
        }
    }

    class C18956 implements GetTotalStepCallback {
        C18956() {
        }

        public void success(int step, String date) {
            Log.i("zhuqichao", "getTotalStep success:step=" + step + ", date=" + date);
            SportHelper.saveStepData(Tools.formatDefDate(date, Tools.BIRTH_FORMAT), step, MainFragment.this.btDeivce.getType());
        }

        public void end(END_STATE state) {
            Log.i("zhuqichao", "getTotalStep end=" + state);
            MainFragment.this.loading.setProgressLabel(String.format(MainFragment.this.dataSync, new Object[]{"40%"}));
            MainFragment.this.subStepStr.delete(0, MainFragment.this.subStepStr.length());
            MainFragment.this.btDeivce.getSubSteps(MainFragment.this.subStepsCallback);
            EventBus.getDefault().post(new EventGetStatis());
            MainFragment.this.updateStepData(false);
        }
    }

    class C18967 implements GetSubStepsCallback {
        C18967() {
        }

        public void success(SubStep info) {
            Log.i("zhuqichao", "getSubSteps success:info=" + info);
            if (Tools.getToday().equals(Tools.formatDefDate(info.getStartTime().split(" +")[0], Tools.BIRTH_FORMAT))) {
                MainFragment.this.subStepStr.append(MainFragment.this.subStepStr.length() == 0 ? "" : "&");
                MainFragment.this.subStepStr.append(SportHelper.subStepToString(info));
            }
        }

        public void end(END_STATE state) {
            Log.i("zhuqichao", "getSubSteps end=" + state);
            MainFragment.this.loading.setProgressLabel(String.format(MainFragment.this.dataSync, new Object[]{"70%"}));
            if (MainFragment.this.btDeivce.supportSleep()) {
                MainFragment.this.btDeivce.getSleepInfo(MainFragment.this.sleepInfoCallback);
            } else {
                MainFragment.this.loading.setProgressLabel(String.format(MainFragment.this.dataSync, new Object[]{"100%"}));
                MainFragment.this.scrollView.onRefreshComplete();
                MainFragment.this.syncing = false;
            }
            SportHelper.saveSubData(MainFragment.this.subStepStr.toString(), MainFragment.this.btDeivce.getType());
            MainFragment.this.subStepStr.delete(0, MainFragment.this.subStepStr.length());
            BaasHelper.uploadSportInBackground();
        }
    }

    class C18978 implements GetSleepInfoCallback {
        C18978() {
        }

        public void success(SleepInfo info) {
            Log.i("zhuqichao", "getSleepInfo success:info=" + info);
            SleepHelper.saveData(info, MainFragment.this.btDeivce.getType());
            MainFragment.this.loading.setProgressLabel(String.format(MainFragment.this.dataSync, new Object[]{"80%"}));
        }

        public void end(END_STATE state) {
            Log.i("zhuqichao", "getSleepInfo end=" + state);
            MainFragment.this.loading.setProgressLabel(String.format(MainFragment.this.dataSync, new Object[]{"100%"}));
            MainFragment.this.scrollView.onRefreshComplete();
            MainFragment.this.syncing = false;
            MainFragment.this.updateSleepData();
            BaasHelper.uploadSleepInBackground();
        }
    }

    class C18989 implements ConnectCallback {
        C18989() {
        }

        public void success(BtDevice device) {
            MainFragment.this.btDeivce = device;
            MainFragment.this.btDeivce.getTotalStep(MainFragment.this.totalStepCallback);
        }

        public void fail(int state) {
            MainFragment.this.scrollView.onRefreshComplete();
            MainFragment.this.syncing = false;
        }

        public void disconnect(BtDevice device) {
            MainFragment.this.scrollView.onRefreshComplete();
            MainFragment.this.syncing = false;
        }

        public void connecting(BtDevice device) {
        }

        public void battery(int battery) {
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1680R.layout.fragment_main, container, false);
    }

    protected void initView(View view) {
        this.stepView = (StepView) view.findViewById(C1680R.id.step_view);
        this.stepView.setOnClickListener(this);
        this.tvRun = (TextView) view.findViewById(C1680R.id.goto_run);
        this.tvRun.setOnClickListener(this);
        this.imgBack = (ImageView) view.findViewById(C1680R.id.img_background);
        this.menuLayuout = (LinearLayout) view.findViewById(C1680R.id.menu_layout);
        this.menuParams = (LayoutParams) this.menuLayuout.getLayoutParams();
        this.backParams = (LayoutParams) this.imgBack.getLayoutParams();
        this.tvWater = (TextView) view.findViewById(C1680R.id.tv_water_num);
        this.tvSleep = (TextView) view.findViewById(C1680R.id.tv_sleep_data);
        this.tvHeart = (TextView) view.findViewById(C1680R.id.tv_heart_data);
        this.scrollView = (PullToRefreshScrollView) view.findViewById(C1680R.id.main_scroll_view);
        this.imgEvents = (ImageView) view.findViewById(C1680R.id.img_events);
        this.imgEvents.setOnClickListener(this);
        view.findViewById(C1680R.id.menu_sleep).setOnClickListener(this);
        view.findViewById(C1680R.id.menu_heart).setOnClickListener(this);
        view.findViewById(C1680R.id.menu_water_alarm).setOnClickListener(this);
        view.findViewById(C1680R.id.menu_vc_test).setOnClickListener(this);
        view.findViewById(C1680R.id.menu_sport_report).setOnClickListener(this);
        if (getResources().getConfiguration().locale.getLanguage().equals("zh")) {
            view.findViewById(C1680R.id.menu_vc_test).setVisibility(0);
        } else {
            view.findViewById(C1680R.id.menu_vc_test).setVisibility(8);
        }
        this.menuHeight = getResources().getDimensionPixelSize(C1680R.dimen.main_menu_height) - ((SystemBarConfig.getNavigationBarHeight() / 3) * 2);
        initMenuLayoutHeight(this.menuHeight);
        updateStepData(false);
        initRefreshView();
        initDevice();
        initStr();
    }

    private void initStr() {
        this.dataSync = getString(C1680R.string.main_data_syncing);
    }

    private void initRefreshView() {
        this.loading = (LoadingLayoutProxy) this.scrollView.getLoadingLayoutProxy();
        this.loading.setPullLabel(getString(C1680R.string.main_data_sync_pull));
        this.loading.setReleaseLabel(getString(C1680R.string.main_data_sync_release));
        this.loading.setRefreshingLabel(getString(C1680R.string.main_data_sync_start));
        this.scrollView.setOnHeaderSizeListener(new C18901());
        this.scrollView.setOnRefreshListener(new C18912());
    }

    private void updateStepData(boolean noAnimation) {
        SportBean bean = SportHelper.getBeanByDate(Tools.getToday());
        this.stepView.setCalString(GpsUtils.getCalStr(bean.getCal()));
        this.stepView.setDistance(GpsUtils.getDisStr(bean.getDistance()));
        if ((this.stepView.getTargetStep() == bean.getStepTarget() && this.stepView.getTotalStep() == bean.getStepPhone()) || noAnimation) {
            this.stepView.setStep(bean.getStepTarget(), bean.getStepPhone(), false);
            return;
        }
        this.stepView.setStep(bean.getStepTarget(), bean.getStepPhone());
        this.stepView.startAnim(1000, 0);
    }

    private void updateSleepData() {
        List<SleepBean> src = SleepHelper.getTodaySleepList();
        if (src.size() > 0) {
            String timeFormat = "HH:mm";
            String start = Tools.formatDate(((SleepBean) src.get(0)).getStartTime(), Tools.DEFAULT_FORMAT_TIME, timeFormat);
            this.tvSleep.setText(start + "~" + Tools.formatDate(((SleepBean) src.get(src.size() - 1)).getEndTime(), Tools.DEFAULT_FORMAT_TIME, timeFormat));
            return;
        }
        this.tvSleep.setText(C1680R.string.main_data_nodata);
    }

    private void updateHeartData() {
        HeartBean bean = HeartHelper.getNewestHeart();
        if (bean != null) {
            this.tvHeart.setText(bean.getCount() + getString(C1680R.string.heart_count_unit));
        } else {
            this.tvHeart.setText(C1680R.string.main_data_nodata);
        }
    }

    private void initDevice() {
        this.btDeivce = BtManagerService.getConnectDevice(this.callback);
        if (this.btDeivce != null) {
            updateDevice();
        }
    }

    private void updateDevice() {
        if (BtManagerService.isBluetoothOpen()) {
            this.syncing = true;
            if (this.btDeivce.getConnectState() == CONNECT_STATE.CONNECTED) {
                this.loading.setProgressLabel(getString(C1680R.string.main_data_syncing, "10%"));
                this.btDeivce.getTotalStep(this.totalStepCallback);
                return;
            }
            this.loading.setProgressLabel(getString(C1680R.string.main_data_sync_connecting));
            this.btDeivce.connect(this.callback);
            return;
        }
        this.scrollView.onRefreshComplete();
        this.syncing = false;
        showOpenBtDialog();
    }

    protected void onVisible() {
        updateHeartData();
        updateStepData(true);
        updateSleepData();
        refreshActivity();
        this.tvWater.setText(getString(C1680R.string.drink_alarm_unit2, Integer.valueOf(SPUtils.getWaterNumber())));
    }

    private void refreshActivity() {
        if (!DroiPreference.instance().isRefreshing()) {
            DroiPreference.instance().refreshInBackground(new C18923());
        }
    }

    private void acitiviyCheck() {
        boolean ifStart;
        String startTime = DroiPreference.instance().getString("activityStartTime");
        String endTime = DroiPreference.instance().getString("activityEndTime");
        long today = Long.parseLong(Tools.getTodayTime());
        if (TextUtils.isEmpty(startTime) || today < Long.parseLong(startTime)) {
            ifStart = false;
        } else {
            ifStart = true;
        }
        boolean ifEnd;
        if (TextUtils.isEmpty(endTime) || today < Long.parseLong(endTime)) {
            ifEnd = false;
        } else {
            ifEnd = true;
        }
        if (!ifStart || ifEnd) {
            this.imgEvents.setVisibility(8);
        } else if (getResources().getConfiguration().locale.getCountry().equals("CN")) {
            this.imgEvents.setVisibility(0);
        } else {
            this.imgEvents.setVisibility(8);
        }
    }

    private void showOpenBtDialog() {
        MyAlertDialog dialog = new MyAlertDialog(getContext());
        dialog.setTitle((int) C1680R.string.bt_tip_bt_not_open);
        dialog.setMessage((int) C1680R.string.bt_tip_open_bt);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C18934());
        dialog.show();
    }

    private void showOpenPhoneStepDialog() {
        MyAlertDialog dialog = new MyAlertDialog(getContext());
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.main_data_open_phone_step);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C18945());
        dialog.show();
    }

    public void onDestroy() {
        super.onDestroy();
        BtManagerService.removeConnectCallback(this.callback);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.menu_sleep:
                startActivity(new Intent(getContext(), SleepOneActivity.class));
                return;
            case C1680R.id.menu_heart:
                startActivity(new Intent(getContext(), HeartAddActivity.class));
                return;
            case C1680R.id.menu_water_alarm:
                startActivity(new Intent(getContext(), WaterReminderActivity.class));
                return;
            case C1680R.id.menu_sport_report:
                startActivity(new Intent(getContext(), SportsReportActivity.class));
                return;
            case C1680R.id.menu_vc_test:
                startActivity(new Intent(getContext(), TopicActivity.class));
                return;
            case C1680R.id.goto_run:
                startActivity(new Intent(getContext(), GPSFirstActivity.class));
                return;
            case C1680R.id.img_events:
                startActivity(new Intent(getContext(), StepActivityActivity.class));
                this.imgEvents.setImageResource(C1680R.drawable.ic_activity);
                return;
            case C1680R.id.arc_step:
                startActivity(new Intent(getContext(), SportOneActivity.class));
                return;
            default:
                return;
        }
    }

    private void initMenuLayoutHeight(int height) {
        this.menuParams.height = height;
        this.menuLayuout.setLayoutParams(this.menuParams);
        this.backParams.bottomMargin = (-height) / 16;
        this.imgBack.setLayoutParams(this.backParams);
    }

    private void updateMenuLayout(int size) {
        this.menuParams.height = this.menuHeight + size;
        this.menuParams.bottomMargin = -size;
        this.menuLayuout.setLayoutParams(this.menuParams);
        this.backParams.bottomMargin = (-this.menuParams.height) / 16;
        this.imgBack.setLayoutParams(this.backParams);
    }

    @Subscribe
    public void onEventMainThread(EventStep event) {
        updateStepData(event.isNoAnim());
    }

    @Subscribe
    public void onEventMainThread(EventGetHeart event) {
        updateHeartData();
    }

    @Subscribe
    public void onEventMainThread(EventGetSleep event) {
        updateSleepData();
    }
}
