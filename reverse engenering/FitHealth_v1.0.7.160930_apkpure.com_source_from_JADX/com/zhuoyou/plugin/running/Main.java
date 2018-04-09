package com.zhuoyou.plugin.running;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.tencent.open.yyb.TitleBar;
import com.umeng.analytics.MobclickAgent;
import com.zhuoyi.system.promotion.listener.ZyPromSDK;
import com.zhuoyou.plugin.add.AddHeartRate;
import com.zhuoyou.plugin.add.AddSports;
import com.zhuoyou.plugin.add.AddWeight;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.cloud.AlarmUtils;
import com.zhuoyou.plugin.cloud.CloudSync;
import com.zhuoyou.plugin.cloud.NetMsgCode;
import com.zhuoyou.plugin.cloud.RankInfoSync;
import com.zhuoyou.plugin.cloud.SportDataSync;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.gps.FirstGpsActivity;
import com.zhuoyou.plugin.gps.GaoDeMapActivity;
import com.zhuoyou.plugin.gps.GaodeService;
import com.zhuoyou.plugin.info.PersonalInformation;
import com.zhuoyou.plugin.mainFrame.BottomViewItem;
import com.zhuoyou.plugin.mainFrame.FinderFragment;
import com.zhuoyou.plugin.mainFrame.MineFragment;
import com.zhuoyou.plugin.mainFrame.RankFragment;
import com.zhuoyou.plugin.rank.RankInfo;
import com.zhuoyou.plugin.selfupdate.Constants;
import com.zhuoyou.plugin.selfupdate.SelfUpdateMain;
import com.zhuoyou.plugin.view.BadgeView;
import com.zhuoyou.plugin.weather.WeatherTools;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Main extends FragmentActivity implements OnClickListener {
    public static final int ACT_STATE = 5;
    public static final int MSG_UNREAD = 4;
    static final String[] PERMISSIONS = new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    private static final int REQUEST_CODE = 7;
    public static final int SELECT_FRAGMENT_HOME = 2;
    public static final int SHOW_CLOUD_SYNC_PROGRESS = 3;
    public static final int SHUT_DOWN_APP = 1;
    public static final int SLEEP_FRAGMENT_HOME = 6;
    private static final String TAG = "MainActivity";
    public static Handler mHandler;
    public static boolean vFragment = true;
    private final int ANIM_DURATION = 400;
    private List<RankInfo> accountHighestData = new ArrayList();
    private List<RankInfo> accountMouthData = new ArrayList();
    private List<RankInfo> accountServenData = new ArrayList();
    private AnimatorListener animationListener = new C18996();
    private BadgeView badgeView2;
    private BadgeView badgeView3;
    private boolean config_dialog = false;
    private int currentTab = -1;
    private int current_tab_id = 0;
    private boolean first_gps;
    private FragmentManager fragmentManager;
    private List<RankInfo> highestStepList = new ArrayList();
    public boolean isAutoSync = true;
    private boolean isClick = false;
    private BottomViewItem item;
    private int lastTab = -1;
    private ImageView mAdd_card_background;
    private RelativeLayout mAdd_card_layout;
    private LinearLayout mAdd_gps;
    private LinearLayout mAdd_heartrate;
    private ImageView mAdd_image;
    private ImageView mAdd_image_bg;
    private ImageView mAdd_image_circle;
    private RelativeLayout mAdd_layout;
    private LinearLayout mAdd_mood;
    private LinearLayout mAdd_sport;
    private LinearLayout mAdd_weight;
    private Context mContext = RunningApp.getInstance().getApplicationContext();
    private boolean mExpanded = false;
    private PermissionsChecker mPermissionsChecker;
    private int mWidth;
    private List<RankInfo> mouthDalysList = new ArrayList();
    OnClickListener onClickListener = new C13775();
    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList(10);
    private int overlapping = Tools.dip2px(this.mContext, TitleBar.SHAREBTN_RIGHT_MARGIN);
    private int perHeightShow = Tools.dip2px(this.mContext, TitleBar.TITLEBAR_HEIGHT);
    public Handler rank_handler = new C13797();
    private List<RankInfo> sevenDalysList = new ArrayList();
    private SharedPreferences sharepreference;
    private long synctime = 0;
    private int tabHeight = Tools.dip2px(this.mContext, 71.0f);
    private FragmentTransaction transaction;

    class C13731 extends Handler {
        C13731() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Main.this.finish();
                    return;
                case 2:
                    Main.this.setTabSelection(0);
                    Main.mHandler.sendEmptyMessageDelayed(3, 1000);
                    return;
                case 3:
                    AlarmUtils.setAutoSyncAlarm(Main.this.mContext);
                    CloudSync.autoSyncType = 1;
                    CloudSync.syncAfterLogin(0);
                    return;
                case 4:
                    if (Tools.getMsgState(Main.this.mContext)) {
                        Main.this.drawCircle(Main.this.badgeView3);
                        return;
                    } else {
                        Main.this.cancleDrawCircle(Main.this.badgeView3);
                        return;
                    }
                case 5:
                    if (Tools.getActState(Main.this.mContext)) {
                        Main.this.drawCircle(Main.this.badgeView2);
                        return;
                    } else {
                        Main.this.cancleDrawCircle(Main.this.badgeView2);
                        return;
                    }
                case 6:
                    if (Main.vFragment) {
                        Main.this.setTabSelection(0);
                        return;
                    }
                    Tools.setSleepCurrPageIndex(0);
                    Main.this.setTabSelection(4);
                    return;
                default:
                    return;
            }
        }
    }

    class C13742 implements OnClickListener {
        C13742() {
        }

        public void onClick(View v) {
            if (!Main.this.isClick) {
                Main.this.isClick = true;
                if (Main.this.mExpanded) {
                    Main.this.closeAddAnimation();
                } else {
                    Main.this.openAddAnimation();
                }
            }
        }
    }

    class C13753 implements DialogInterface.OnClickListener {
        C13753() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            Main.this.startActivity(new Intent(Main.this, PersonalInformation.class));
        }
    }

    class C13764 implements DialogInterface.OnClickListener {
        C13764() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C13775 implements OnClickListener {
        C13775() {
        }

        public void onClick(View v) {
            String curr_date = Tools.getDate(0);
            switch (v.getId()) {
                case R.id.add_card_layout:
                    if (Main.this.mExpanded) {
                        Main.this.closeAddAnimation();
                        return;
                    }
                    return;
                case R.id.add_heartrate:
                    Log.i("lsj", "isStartHeart=" + GaoDeMapActivity.isStartHeart);
                    String name = Util.getDeviceName();
                    if (GaoDeMapActivity.isStartHeart) {
                        Log.i("lsj", "GaoDeMapActivity.isStartHeart=" + GaoDeMapActivity.isStartHeart);
                        Tools.makeToast(Main.this.getResources().getString(R.string.prohibited));
                        return;
                    } else if (!BluetoothService.getInstance().isConnected() && (!RunningApp.isBLESupport || !BleManagerService.getInstance().GetBleConnectState())) {
                        Tools.makeToast(Main.this.getResources().getString(R.string.alarm_toast));
                        return;
                    } else if (name.equals("Rumor-2") || name.equals("M2")) {
                        Intent heartrateIntent = new Intent();
                        heartrateIntent.setClass(Main.this.mContext, AddHeartRate.class);
                        Main.this.startActivity(heartrateIntent);
                        Main.this.closeAddAnimation();
                        return;
                    } else {
                        Tools.makeToast(Main.this.getResources().getString(R.string.no_support_heart_rate));
                        return;
                    }
                case R.id.add_weight:
                    Intent weightIntent = new Intent();
                    weightIntent.putExtra("date", curr_date);
                    weightIntent.setClass(Main.this.mContext, AddWeight.class);
                    Main.this.startActivity(weightIntent);
                    Main.this.closeAddAnimation();
                    return;
                case R.id.add_gps:
                    Intent gpsIntent = new Intent();
                    gpsIntent.putExtra("date", curr_date);
                    gpsIntent.putExtra("from", "Main");
                    Main.this.first_gps = Main.this.sharepreference.contains("is_first_gps");
                    if (Main.this.first_gps) {
                        gpsIntent.setClass(Main.this.mContext, GaoDeMapActivity.class);
                    } else {
                        gpsIntent.setClass(Main.this.mContext, FirstGpsActivity.class);
                    }
                    Main.this.startActivity(gpsIntent);
                    Main.this.closeAddAnimation();
                    return;
                case R.id.add_sport:
                    Intent sportIntent = new Intent();
                    sportIntent.putExtra("date", curr_date);
                    sportIntent.setClass(Main.this.mContext, AddSports.class);
                    Main.this.startActivity(sportIntent);
                    Main.this.closeAddAnimation();
                    return;
                default:
                    return;
            }
        }
    }

    class C13797 extends Handler {

        class C13781 implements Runnable {
            C13781() {
            }

            public void run() {
                RankInfoSync rankInfo = new RankInfoSync(Main.this.mContext);
                if (CloudSync.isUpdateTodayRank) {
                    rankInfo.getTodayRankInfo(Main.this.rank_handler);
                    CloudSync.isUpdateTodayRank = false;
                    Log.i("hph", "CloudSync.isUpdateTodayRank = false;");
                }
            }
        }

        C13797() {
        }

        public void handleMessage(Message msg) {
            Log.d("zzb1", "msg:" + msg);
            RankFragment curFragment;
            switch (msg.what) {
                case 200:
                    switch (msg.arg1) {
                        case NetMsgCode.postSportInfo /*104001*/:
                            new Thread(new C13781()).start();
                            return;
                        case NetMsgCode.getTodayRankInfo /*301013*/:
                            HashMap<String, List<RankInfo>> resRankList = msg.obj;
                            if (resRankList.size() > 0) {
                                if (resRankList.get("sevenDaysStepList") != null) {
                                    Main.this.sevenDalysList = (List) resRankList.get("sevenDaysStepList");
                                }
                                if (resRankList.get("monthStepList") != null) {
                                    Main.this.mouthDalysList = (List) resRankList.get("monthStepList");
                                }
                                if (resRankList.get("todayStepList") != null) {
                                    Main.this.highestStepList = (List) resRankList.get("todayStepList");
                                }
                                if (resRankList.get("accountSevenData") != null) {
                                    Main.this.accountServenData = (List) resRankList.get("accountSevenData");
                                }
                                if (resRankList.get("accountMonthData") != null) {
                                    Main.this.accountMouthData = (List) resRankList.get("accountMonthData");
                                }
                                if (resRankList.get("accountTodayStep") != null) {
                                    Main.this.accountHighestData = (List) resRankList.get("accountTodayStep");
                                }
                            }
                            Main.this.synctime = System.currentTimeMillis();
                            if (Main.this.currentTab == 1) {
                                curFragment = (RankFragment) Main.this.fragmentManager.findFragmentByTag(String.valueOf(Main.this.currentTab));
                                if (curFragment != null && curFragment.isVisible()) {
                                    curFragment.UpdateRankView(Main.this.sevenDalysList, Main.this.mouthDalysList, Main.this.highestStepList, Main.this.accountServenData, Main.this.accountMouthData, Main.this.accountHighestData);
                                    return;
                                }
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                case 404:
                    Toast.makeText(Main.this.mContext, R.string.network_link_failure, 0).show();
                    Log.i("hph", "network_link_failure333");
                    return;
                case 110011:
                    if (Main.this.currentTab == 1) {
                        curFragment = (RankFragment) Main.this.fragmentManager.findFragmentByTag(String.valueOf(Main.this.currentTab));
                        if (curFragment != null && curFragment.isVisible()) {
                            curFragment.UpdateRankView(Main.this.sevenDalysList, Main.this.mouthDalysList, Main.this.highestStepList, Main.this.accountServenData, Main.this.accountMouthData, Main.this.accountHighestData);
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public interface MyOnTouchListener {
        boolean onTouch(MotionEvent motionEvent);
    }

    class C18996 implements AnimatorListener {
        C18996() {
        }

        public void onAnimationCancel(Animator arg0) {
        }

        public void onAnimationEnd(Animator arg0) {
            Main.this.isClick = false;
            if (Main.this.mExpanded) {
                LayoutParams params = new LayoutParams(Tools.dip2px(Main.this.mContext, 44.0f), -1);
                params.addRule(14);
                Main.this.mAdd_layout.setLayoutParams(params);
                Main.this.mAdd_image_circle.setVisibility(8);
                Main.this.mAdd_card_layout.setVisibility(8);
                Main.this.mExpanded = false;
                return;
            }
            Main.this.mExpanded = true;
        }

        public void onAnimationRepeat(Animator arg0) {
        }

        public void onAnimationStart(Animator arg0) {
            if (!Main.this.mExpanded) {
                Animation animation = AnimationUtils.loadAnimation(Main.this.mContext, R.anim.add_card_backgroud_image);
                animation.setFillAfter(true);
                Main.this.mAdd_card_background.setAnimation(animation);
                LayoutParams params = new LayoutParams(-1, -1);
                Main.this.mAdd_layout.setLayoutParams(params);
                Main.this.mAdd_image_circle.setVisibility(0);
                Main.this.mAdd_card_layout.setVisibility(0);
                Main.this.mAdd_card_layout.setLayoutParams(params);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WeatherTools.newInstance().getCurrWeather();
        DisplayMetrics outMetrics = getResources().getDisplayMetrics();
        this.sharepreference = getSharedPreferences("gaode_location_info", 0);
        this.mWidth = outMetrics.widthPixels;
        this.item = BottomViewItem.getInstance();
        this.fragmentManager = getSupportFragmentManager();
        initViews();
        setTabSelection(0);
        initOC();
        if (getIntent() != null) {
            this.config_dialog = getIntent().getBooleanExtra("config_dialog", false);
            if (getIntent().getBooleanExtra("firmwear", false)) {
                sendBroadcast(new Intent("com.zhuoyou.running.notification.firmwear"));
            }
        }
        if (Tools.checkIsFirstEntry(this.mContext)) {
            if (this.config_dialog) {
                showConfigDialog();
            }
            Tools.setFirstEntry(this.mContext);
        }
        if (!SelfUpdateMain.isDownloading) {
            SelfUpdateMain.execApkSelfUpdateRequest(this, Constants.APPID, Constants.CHNID);
        }
        mHandler = new C13731();
        File fd = new File(Tools.getSDPath() + "/Running/.thumbnail/");
        if (fd.exists()) {
            Tools.deleteSDCardFolder(fd);
        }
        MainService.getInstance();
    }

    private void initViews() {
        this.mAdd_card_layout = (RelativeLayout) findViewById(R.id.add_card_layout);
        this.mAdd_card_layout.setOnClickListener(this.onClickListener);
        this.mAdd_card_background = (ImageView) findViewById(R.id.add_card_background);
        this.mAdd_heartrate = (LinearLayout) findViewById(R.id.add_heartrate);
        this.mAdd_heartrate.setOnClickListener(this.onClickListener);
        this.mAdd_weight = (LinearLayout) findViewById(R.id.add_weight);
        this.mAdd_weight.setOnClickListener(this.onClickListener);
        this.mAdd_gps = (LinearLayout) findViewById(R.id.add_gps);
        if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
            this.mAdd_gps.setVisibility(8);
        }
        this.mAdd_gps.setOnClickListener(this.onClickListener);
        this.mAdd_sport = (LinearLayout) findViewById(R.id.add_sport);
        this.mAdd_sport.setOnClickListener(this.onClickListener);
        this.mAdd_layout = (RelativeLayout) findViewById(R.id.add_layout);
        this.mAdd_image_bg = (ImageView) findViewById(R.id.add_image_bg);
        this.mAdd_image_circle = (ImageView) findViewById(R.id.add_image_circle);
        this.mAdd_image = (ImageView) findViewById(R.id.add_image);
        this.mAdd_layout.setOnClickListener(new C13742());
        for (int i = 0; i < this.item.viewNum; i++) {
            this.item.linears[i] = (LinearLayout) findViewById(this.item.linears_id[i]);
            this.item.linears[i].setOnClickListener(this);
            this.item.images[i] = (ImageView) findViewById(this.item.images_id[i]);
            this.item.texts[i] = (TextView) findViewById(this.item.texts_id[i]);
        }
        this.badgeView2 = new BadgeView((Context) this, this.item.images[2]);
        this.badgeView3 = new BadgeView((Context) this, this.item.images[3]);
        if (Tools.getMsgState(this.mContext)) {
            drawCircle(this.badgeView3);
        }
        if (Tools.getActState(this.mContext)) {
            drawCircle(this.badgeView2);
        }
        this.mPermissionsChecker = new PermissionsChecker(this);
    }

    public void drawCircle(BadgeView bv) {
        bv.setBackgroundResource(R.drawable.remind_circle);
        bv.setBadgeMargin(3, 3);
        bv.setWidth(8);
        bv.setHeight(8);
        bv.toggle(false);
    }

    public void cancleDrawCircle(BadgeView bv) {
        if (bv != null) {
            bv.toggle(true);
        }
    }

    private void showConfigDialog() {
        Builder builder = new Builder(this);
        builder.setTitle((int) R.string.alert_title);
        builder.setMessage((int) R.string.complete_personal_config);
        builder.setPositiveButton((int) R.string.perfect, new C13753());
        builder.setNegativeButton((int) R.string.no_thanks, new C13764());
        builder.setCancelable(Boolean.valueOf(true));
    }

    public void onClick(View v) {
        if (this.current_tab_id != v.getId()) {
            for (int i = 0; i < this.item.linears_id.length; i++) {
                if (v.getId() == this.item.linears_id[i]) {
                    setTabSelection(i);
                }
            }
        }
    }

    private void setTabSelection(int index) {
        if (index != this.currentTab) {
            this.lastTab = this.currentTab;
            this.currentTab = index;
            this.transaction = this.fragmentManager.beginTransaction();
            if (!(this.currentTab == this.lastTab || this.lastTab == -1)) {
                Fragment mlastFragment = this.fragmentManager.findFragmentByTag(String.valueOf(this.lastTab));
                if (mlastFragment != null && mlastFragment.isAdded()) {
                    this.transaction.detach(mlastFragment);
                    Log.d("zzb11", "setTabSelection detach  mlastFragment =" + mlastFragment);
                }
            }
            Fragment curFragment = this.fragmentManager.findFragmentByTag(String.valueOf(index));
            switch (index) {
                case 0:
                    if (curFragment != null) {
                        this.transaction.detach(curFragment);
                        this.transaction.add(R.id.main_fragment, new HomePageFragment(), String.valueOf(index));
                        break;
                    }
                    this.transaction.add(R.id.main_fragment, new HomePageFragment(), String.valueOf(index));
                    break;
                case 1:
                    if (curFragment == null) {
                        this.transaction.add(R.id.main_fragment, new RankFragment(), String.valueOf(index));
                    } else {
                        this.transaction.attach(curFragment);
                    }
                    GetRankListDate();
                    Log.i("hph", "GetRankListDate=case 1");
                    break;
                case 2:
                    if (curFragment != null) {
                        this.transaction.attach(curFragment);
                        break;
                    }
                    this.transaction.add(R.id.main_fragment, new FinderFragment(), String.valueOf(index));
                    break;
                case 3:
                    if (curFragment != null) {
                        this.transaction.attach(curFragment);
                        break;
                    }
                    this.transaction.add(R.id.main_fragment, new MineFragment(), String.valueOf(index));
                    break;
                case 4:
                    if (curFragment != null) {
                        this.transaction.attach(curFragment);
                        break;
                    }
                    this.transaction.add(R.id.main_fragment, new SleepPageFragment(), String.valueOf(index));
                    break;
            }
            this.transaction.commit();
            if (index != 4) {
                this.current_tab_id = this.item.linears_id[index];
            } else {
                this.current_tab_id = this.item.linears_id[0];
            }
            clearSelection();
            if (index != 4) {
                this.item.images[index].setImageResource(this.item.images_selected[index]);
                this.item.texts[index].setTextColor(getResources().getColor(R.color.bottom_text_selected));
                return;
            }
            this.item.images[0].setImageResource(this.item.images_selected[0]);
            this.item.texts[0].setTextColor(getResources().getColor(R.color.bottom_text_selected));
        }
    }

    private void clearSelection() {
        for (int i = 0; i < this.item.viewNum; i++) {
            this.item.images[i].setImageResource(this.item.images_unselected[i]);
            this.item.texts[i].setTextColor(getResources().getColor(R.color.bottom_text_unselected));
        }
    }

    public void onResume() {
        super.onResume();
        if (!(BluetoothService.getInstance() == null && BleManagerService.getInstance() == null) && ((BluetoothService.getInstance().isConnected() || (RunningApp.isBLESupport && BleManagerService.getInstance().GetBleConnectState())) && !this.isAutoSync)) {
            this.isAutoSync = true;
            if (MainService.getInstance() != null) {
                MainService.getInstance().syncWithDevice();
                Message message = new Message();
                BleManagerService.getInstance().setTimeToRemote(0);
                message.what = 1;
                message.arg1 = 1;
                HomePageFragment.mHandler.sendMessageDelayed(message, 500);
            }
        }
        if (this.mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            Log.i("zhangweinan", "mPermissionsChecker");
            startPermissionsActivity();
        }
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
        Log.i(TAG, "MobclickAgent.onPageStart(TAG)");
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, 7, PERMISSIONS);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == 1) {
            finish();
        }
    }

    public void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            this.isAutoSync = false;
            Log.i("1111", "eeeeee");
        }
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, GaodeService.class));
        if (HomePageFragment.mRunningDays != null) {
            HomePageFragment.mRunningDays.clear();
        }
        if (HomePageFragment.weight != null) {
            HomePageFragment.weight.clear();
        }
        if (HomePageFragment.steps != null) {
            HomePageFragment.steps.clear();
        }
    }

    public void onBackPressed() {
        if (this.mExpanded) {
            closeAddAnimation();
        } else {
            moveTaskToBack(true);
        }
    }

    private void initOC() {
        ZyPromSDK.getInstance().init(getApplicationContext(), false);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.currentTab == 1) {
            Iterator it = this.onTouchListeners.iterator();
            while (it.hasNext()) {
                if (!((MyOnTouchListener) it.next()).onTouch(ev)) {
                    return false;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        this.onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        this.onTouchListeners.remove(myOnTouchListener);
    }

    private void openAddAnimation() {
        AnimatorSet add_gps;
        AnimatorSet add_heartrate;
        AnimatorSet add_weight;
        AnimatorSet add_image = buildRoateAnimation(this.mAdd_image, false);
        AnimatorSet add_image_bg = buildScaleAnimation(this.mAdd_image_bg, false);
        AnimatorSet add_sport = buildTranslateAnimation(this.mAdd_sport, false, 100, 0 - this.overlapping);
        if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
            add_gps = buildTranslateAnimation(this.mAdd_gps, false, 400, (this.perHeightShow * 3) - this.overlapping);
            add_heartrate = buildTranslateAnimation(this.mAdd_heartrate, false, 300, (this.perHeightShow * 2) - this.overlapping);
            add_weight = buildTranslateAnimation(this.mAdd_weight, false, 200, this.perHeightShow - this.overlapping);
        } else {
            add_gps = buildTranslateAnimation(this.mAdd_gps, false, 200, this.perHeightShow - this.overlapping);
            add_heartrate = buildTranslateAnimation(this.mAdd_heartrate, false, 400, (this.perHeightShow * 3) - this.overlapping);
            add_weight = buildTranslateAnimation(this.mAdd_weight, false, 300, (this.perHeightShow * 2) - this.overlapping);
        }
        add_image.playTogether(add_image_bg);
        add_image.playTogether(add_sport);
        add_image.playTogether(add_gps);
        add_image.playTogether(add_weight);
        add_image.playTogether(add_heartrate);
        add_image.addListener(this.animationListener);
        add_image.start();
    }

    private void closeAddAnimation() {
        AnimatorSet add_gps;
        AnimatorSet add_heartrate;
        AnimatorSet add_weight;
        AnimatorSet add_image = buildRoateAnimation(this.mAdd_image, true);
        AnimatorSet add_image_bg = buildScaleAnimation(this.mAdd_image_bg, true);
        AnimatorSet add_sport = buildTranslateAnimation(this.mAdd_sport, true, 100, 0 - this.overlapping);
        if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
            add_gps = buildTranslateAnimation(this.mAdd_gps, true, 400, (this.perHeightShow * 3) - this.overlapping);
            add_heartrate = buildTranslateAnimation(this.mAdd_heartrate, true, 300, (this.perHeightShow * 2) - this.overlapping);
            add_weight = buildTranslateAnimation(this.mAdd_weight, true, 200, this.perHeightShow - this.overlapping);
        } else {
            add_gps = buildTranslateAnimation(this.mAdd_gps, true, 200, this.perHeightShow - this.overlapping);
            add_heartrate = buildTranslateAnimation(this.mAdd_heartrate, true, 400, (this.perHeightShow * 3) - this.overlapping);
            add_weight = buildTranslateAnimation(this.mAdd_weight, true, 300, (this.perHeightShow * 2) - this.overlapping);
        }
        add_image.playTogether(add_image_bg);
        add_image.playTogether(add_sport);
        add_image.playTogether(add_gps);
        add_image.playTogether(add_weight);
        add_image.playTogether(add_heartrate);
        add_image.addListener(this.animationListener);
        add_image.start();
    }

    private AnimatorSet buildRoateAnimation(View target, boolean flag) {
        float f;
        float f2 = 0.0f;
        AnimatorSet roate = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        String str = "Rotation";
        float[] fArr = new float[2];
        if (flag) {
            f = 45.0f;
        } else {
            f = 0.0f;
        }
        fArr[0] = f;
        if (!flag) {
            f2 = 45.0f;
        }
        fArr[1] = f2;
        animatorArr[0] = ObjectAnimator.ofFloat((Object) target, str, fArr);
        roate.playTogether(animatorArr);
        roate.setInterpolator(new DecelerateInterpolator());
        roate.setDuration(400);
        return roate;
    }

    private AnimatorSet buildScaleAnimation(View target, boolean flag) {
        float f = 1.0f;
        int scale = (this.mWidth / Tools.dip2px(this.mContext, 44.0f)) + 1;
        AnimatorSet scaleAni = new AnimatorSet();
        Animator[] animatorArr = new Animator[3];
        String str = "scaleX";
        float[] fArr = new float[1];
        fArr[0] = flag ? 1.0f : (float) scale;
        animatorArr[0] = ObjectAnimator.ofFloat((Object) target, str, fArr);
        str = "scaleY";
        fArr = new float[1];
        fArr[0] = flag ? 1.0f : (float) scale;
        animatorArr[1] = ObjectAnimator.ofFloat((Object) target, str, fArr);
        str = "alpha";
        fArr = new float[1];
        if (!flag) {
            f = 0.8f;
        }
        fArr[0] = f;
        animatorArr[2] = ObjectAnimator.ofFloat((Object) target, str, fArr);
        scaleAni.playTogether(animatorArr);
        scaleAni.setInterpolator(new DecelerateInterpolator());
        scaleAni.setDuration(400);
        return scaleAni;
    }

    private AnimatorSet buildTranslateAnimation(View target, boolean flag, int duration, int distance) {
        AnimatorSet translation = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        String str = "translationY";
        float[] fArr = new float[2];
        fArr[0] = flag ? (float) (0 - distance) : (float) this.tabHeight;
        fArr[1] = flag ? (float) this.tabHeight : (float) (0 - distance);
        animatorArr[0] = ObjectAnimator.ofFloat((Object) target, str, fArr);
        translation.playTogether(animatorArr);
        translation.setInterpolator(new OvershootInterpolator(0.6f));
        translation.setDuration((long) duration);
        return translation;
    }

    public void GetRankListDate() {
        if (Tools.getLogin(this.mContext)) {
            boolean isSync = CloudSync.isSync;
            long current_time = System.currentTimeMillis();
            if (this.synctime != 0 && (current_time - this.synctime <= 300000 || this.synctime == 0)) {
                this.rank_handler.sendEmptyMessageDelayed(110011, 500);
            } else if (Tools.getLogin(this.mContext) && !MainService.syncnow.booleanValue() && !isSync) {
                new SportDataSync(this.mContext, 2).postSportData(this.rank_handler);
                Tools.loginStateChange = true;
            }
        }
    }

    public List<RankInfo> GetRankDate(int type) {
        List<RankInfo> result = new ArrayList();
        switch (type) {
            case 1:
                return this.sevenDalysList;
            case 2:
                return this.mouthDalysList;
            case 3:
                return this.highestStepList;
            case 4:
                return this.accountServenData;
            case 5:
                return this.accountMouthData;
            case 6:
                return this.accountHighestData;
            default:
                return result;
        }
    }

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService("activity");
        String packageName = getApplicationContext().getPackageName();
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == 100) {
                return true;
            }
        }
        return false;
    }
}
