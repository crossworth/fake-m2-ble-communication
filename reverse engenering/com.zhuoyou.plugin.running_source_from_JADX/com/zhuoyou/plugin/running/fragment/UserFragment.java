package com.zhuoyou.plugin.running.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtDevice.ConnectCallback;
import com.droi.btlib.service.BtManagerService;
import com.droi.btlib.service.BtManagerService.CONNECT_STATE;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.feedback.DroiFeedback;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.AboutActivity;
import com.zhuoyou.plugin.running.activity.AppSettingActivity;
import com.zhuoyou.plugin.running.activity.CropPictureActivity;
import com.zhuoyou.plugin.running.activity.DeviceAddActivity;
import com.zhuoyou.plugin.running.activity.DeviceMineActivity;
import com.zhuoyou.plugin.running.activity.DeviceSettingActivity;
import com.zhuoyou.plugin.running.activity.QQBandActivity;
import com.zhuoyou.plugin.running.activity.QQHealthActivity;
import com.zhuoyou.plugin.running.activity.SportHistoryActivity;
import com.zhuoyou.plugin.running.activity.SportTargetActivity;
import com.zhuoyou.plugin.running.activity.UserInfoActivity;
import com.zhuoyou.plugin.running.activity.VCTestActivity;
import com.zhuoyou.plugin.running.activity.WXGuideActivity;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseFragment;
import com.zhuoyou.plugin.running.bean.EventGetStatis;
import com.zhuoyou.plugin.running.bean.EventUserFetch;
import com.zhuoyou.plugin.running.database.SportHelper;
import com.zhuoyou.plugin.running.tools.AnimUtils;
import com.zhuoyou.plugin.running.tools.FileUtils;
import com.zhuoyou.plugin.running.tools.FirmwareUtils;
import com.zhuoyou.plugin.running.tools.Fonts;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.AnimTextView;
import com.zhuoyou.plugin.running.view.FullShareDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.ProgressWheel;
import com.zhuoyou.plugin.running.view.SetPhotoDialog;
import java.io.File;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

public class UserFragment extends BaseFragment implements OnClickListener {
    private static final int REQUEST_CODE_ADD_DEVICE = 4100;
    private static final String SPORT_MUSIC = "com.droi.sportmusic";
    public static final String TAG = "UserFragment";
    private BtDevice btDevice;
    private LinearLayout btnAddDevice;
    private ConnectCallback callback = new C19052();
    private ImageView connectState;
    private TextView deviceBattery;
    private ImageView deviceBatteryImg;
    private LinearLayout deviceLayout;
    private ImageView deviceLogo;
    private TextView deviceName;
    private TextView deviceSetting;
    private IUiListener iUiListener = new C19084();
    private ImageView imgBack;
    private ImageView imgPhoto;
    private ImageView imgUpdatePoint;
    private ScrollView mScrollView;
    private DroiFile oldPhoto;
    private DroiFile photo;
    private AnimTextView tvAchieve;
    private AnimTextView tvDaily;
    private TextView tvHint;
    private TextView tvName;
    private TextView tvTarget;
    private TextView tvTargetStep;
    private AnimTextView tvTotal;
    private TextView tvWXGuide;
    private User user = ((User) DroiUser.getCurrentUser(User.class));
    private ProgressWheel wheel;

    class C19041 implements DroiCallback<Boolean> {
        C19041() {
        }

        public void result(Boolean aBoolean, DroiError droiError) {
            if (droiError.isOk()) {
                EventBus.getDefault().post(new EventUserFetch());
            }
        }
    }

    class C19052 implements ConnectCallback {
        C19052() {
        }

        public void success(BtDevice device) {
            UserFragment.this.btDevice = device;
            UserFragment.this.updateDeviceLayout();
            if (UserFragment.this.isVisibled) {
                FirmwareUtils.checkDeviceUpdate(UserFragment.this.btDevice, UserFragment.this.getContext(), UserFragment.this.imgUpdatePoint, true, false);
            }
        }

        public void fail(int state) {
            switch (state) {
                case 0:
                    UserFragment.this.connectState.setImageResource(C1680R.drawable.bt_state_disconnect);
                    UserFragment.this.deviceBattery.setText(C1680R.string.connect_state_disconnect);
                    return;
                case 2:
                    UserFragment.this.connectState.setImageResource(C1680R.drawable.bt_state_disconnect);
                    UserFragment.this.deviceBattery.setText(C1680R.string.connect_state_disconnect);
                    return;
                default:
                    return;
            }
        }

        public void disconnect(BtDevice device) {
            UserFragment.this.updateDeviceLayout();
        }

        public void connecting(BtDevice device) {
            UserFragment.this.updateDeviceLayout();
        }

        public void battery(int battery) {
            UserFragment.this.updateDeviceLayout();
            Log.i("hph", "battery===" + battery);
        }
    }

    class C19073 implements DroiCallback<Boolean> {

        class C19061 implements DroiCallback<Boolean> {
            C19061() {
            }

            public void result(Boolean aBoolean, DroiError droiError) {
                UserFragment.this.wheel.setVisibility(8);
                if (droiError.isOk()) {
                    Tools.makeToast((int) C1680R.string.userinfo_backphoto_save_success);
                    EventBus.getDefault().post(new EventUserFetch());
                    return;
                }
                Tools.makeToast(Tools.getDroiError(droiError));
                UserFragment.this.user.setBack(UserFragment.this.oldPhoto);
                Tools.displayBack(UserFragment.this.imgBack, UserFragment.this.user.getBack());
            }
        }

        C19073() {
        }

        public void result(Boolean aBoolean, DroiError droiError) {
            if (droiError.isOk()) {
                UserFragment.this.user.setBack(UserFragment.this.photo);
                UserFragment.this.user.saveInBackground(new C19061());
                return;
            }
            Tools.makeToast(Tools.getDroiError(droiError));
            UserFragment.this.wheel.setVisibility(8);
            UserFragment.this.user.setBack(UserFragment.this.oldPhoto);
            Tools.displayBack(UserFragment.this.imgBack, UserFragment.this.user.getBack());
        }
    }

    class C19084 implements IUiListener {
        C19084() {
        }

        public void onComplete(Object o) {
            Log.i("zhuqichao", "登陆成功=" + o);
            if (o instanceof JSONObject) {
                String userid = DroiUser.getCurrentUser().getUserId();
                JSONObject JSON = (JSONObject) o;
                SPUtils.setQQOpenId(JSON.optString("openid"), userid);
                SPUtils.setQQAccessToken(JSON.optString("access_token"), userid);
                SPUtils.setQQExpiresIn(JSON.optString("expires_in"), userid);
                UserFragment.this.startActivity(new Intent(UserFragment.this.getContext(), QQHealthActivity.class));
                return;
            }
            Tools.makeToast("登录失败！");
        }

        public void onError(UiError uiError) {
            Tools.makeToast("登录失败！");
            Log.i("zhuqichao", "登陆失败=" + uiError);
        }

        public void onCancel() {
        }
    }

    class C19095 implements MyAlertDialog.OnClickListener {
        C19095() {
        }

        public void onClick(int witch) {
            BtManagerService.openBluetooth();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1680R.layout.fragment_user, container, false);
    }

    protected void initView(View view) {
        this.wheel = (ProgressWheel) view.findViewById(C1680R.id.progress_wheel);
        this.mScrollView = (ScrollView) view.findViewById(C1680R.id.user_scroll_view);
        this.deviceLayout = (LinearLayout) view.findViewById(C1680R.id.device_layout);
        this.deviceSetting = (TextView) view.findViewById(C1680R.id.device_setting);
        this.deviceLogo = (ImageView) view.findViewById(C1680R.id.device_logo);
        this.btnAddDevice = (LinearLayout) view.findViewById(C1680R.id.btn_add_device);
        this.deviceName = (TextView) view.findViewById(C1680R.id.device_name);
        this.deviceBattery = (TextView) view.findViewById(C1680R.id.device_battery);
        this.deviceBatteryImg = (ImageView) view.findViewById(C1680R.id.device_battery_img);
        this.connectState = (ImageView) view.findViewById(C1680R.id.connect_state);
        this.tvDaily = (AnimTextView) view.findViewById(C1680R.id.tv_daily_steps);
        this.tvTotal = (AnimTextView) view.findViewById(C1680R.id.tv_total_distance);
        this.tvAchieve = (AnimTextView) view.findViewById(C1680R.id.tv_achieve_days);
        this.imgUpdatePoint = (ImageView) view.findViewById(C1680R.id.img_update_point);
        this.tvDaily.setTypeface(Fonts.number);
        this.tvTotal.setTypeface(Fonts.number);
        this.tvAchieve.setTypeface(Fonts.number);
        this.imgPhoto = (ImageView) view.findViewById(C1680R.id.img_photo);
        this.imgBack = (ImageView) view.findViewById(C1680R.id.img_back);
        this.tvName = (TextView) view.findViewById(C1680R.id.tv_usrname);
        this.tvHint = (TextView) view.findViewById(C1680R.id.tv_hint);
        this.tvTarget = (TextView) view.findViewById(C1680R.id.menu_target);
        this.tvTargetStep = (TextView) view.findViewById(C1680R.id.tv_target_step);
        if (getLanguage().equals("es")) {
            this.tvTarget.setText(getString(C1680R.string.main_menu_target1, Integer.valueOf(0)));
        } else {
            this.tvTarget.setText(getString(C1680R.string.main_menu_target, Integer.valueOf(0)));
        }
        this.tvTargetStep.setText(getString(C1680R.string.mine_goal_unit, Integer.valueOf(SPUtils.getTargetStep())));
        this.tvWXGuide = (TextView) view.findViewById(C1680R.id.third_wechat_guide);
        if (getResources().getConfiguration().locale.getLanguage().equals("zh")) {
            this.tvWXGuide.setVisibility(0);
        } else {
            this.tvWXGuide.setVisibility(8);
        }
        this.deviceSetting.setOnClickListener(this);
        this.deviceBattery.setOnClickListener(this);
        this.deviceBatteryImg.setOnClickListener(this);
        this.connectState.setOnClickListener(this);
        this.imgPhoto.setOnClickListener(this);
        this.imgBack.setOnClickListener(this);
        this.tvName.setOnClickListener(this);
        this.tvHint.setOnClickListener(this);
        this.tvTarget.setOnClickListener(this);
        view.findViewById(C1680R.id.device_info).setOnClickListener(this);
        view.findViewById(C1680R.id.mine_item_preference).setOnClickListener(this);
        view.findViewById(C1680R.id.mine_vc_test).setOnClickListener(this);
        view.findViewById(C1680R.id.mine_item_feedback).setOnClickListener(this);
        view.findViewById(C1680R.id.btn_about).setOnClickListener(this);
        view.findViewById(C1680R.id.btn_history).setOnClickListener(this);
        view.findViewById(C1680R.id.mygoal_setting).setOnClickListener(this);
        view.findViewById(C1680R.id.btn_add_device).setOnClickListener(this);
        view.findViewById(C1680R.id.third_wechat).setOnClickListener(this);
        view.findViewById(C1680R.id.third_wechat_guide).setOnClickListener(this);
        view.findViewById(C1680R.id.third_qq).setOnClickListener(this);
    }

    protected void onVisible() {
        updateData();
        showUserData();
        this.btDevice = BtManagerService.getConnectDevice(this.callback);
        Log.i("zhuqichao", "UserFragment.onVisible");
        if (this.btDevice == null) {
            this.deviceLayout.setVisibility(8);
            this.btnAddDevice.setVisibility(0);
            this.tvHint.setVisibility(8);
        } else {
            this.deviceLayout.setVisibility(0);
            this.btnAddDevice.setVisibility(8);
            if (this.btDevice.getName().contains("Primo 5")) {
                String strHint = "";
                this.tvHint.setVisibility(0);
                if (Tools.isAppInstalled(SPORT_MUSIC)) {
                    strHint = getString(C1680R.string.mars5_hint1);
                } else {
                    strHint = getString(C1680R.string.mars5_hint);
                }
                this.tvHint.setText(Html.fromHtml("<font color='#a9a9a9'>" + strHint + " </font><font color='#3366cc'><U>" + getString(C1680R.string.mars5_hint_app) + "</U></font>"));
            } else {
                this.tvHint.setVisibility(8);
            }
            if (this.btDevice.getName().contains("M2") || this.btDevice.getName().equals("U3") || this.btDevice.getName().equals("TERRA HR2")) {
                int i;
                StringBuilder append = new StringBuilder().append("5|");
                if (SPUtils.getM2WristBrightScreenSwitch()) {
                    i = 1;
                } else {
                    i = 0;
                }
                this.btDevice.setSedentary(append.append(i).append("||||||||||||").toString());
            }
            if (this.btDevice.getType() == 1) {
                this.btDevice.refreshClassicBattery();
            }
            updateDeviceLayout();
        }
        this.user.fetchInBackground(new C19041());
        FirmwareUtils.checkDeviceUpdate(this.btDevice, getContext(), this.imgUpdatePoint, false, false);
    }

    private void showUserData() {
        Tools.displayFace(this.imgPhoto, this.user.getHead());
        Tools.displayBack(this.imgBack, this.user.getBack());
        this.tvName.setText(TextUtils.isEmpty(this.user.getNickName()) ? this.user.getUserId() : this.user.getNickName());
    }

    private String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    private void updateData() {
        this.tvDaily.withNumber(SportHelper.getAverageStep());
        this.tvTotal.withNumber(GpsUtils.getDistance(SportHelper.getTotalDis()));
        this.tvAchieve.withNumber(SportHelper.getComplete());
        if (getLanguage().equals("es")) {
            if (SportHelper.getCompleteSer() > 1) {
                this.tvTarget.setText(getString(C1680R.string.main_menu_target, Integer.valueOf(days)));
            } else {
                this.tvTarget.setText(getString(C1680R.string.main_menu_target1, Integer.valueOf(days)));
            }
        } else {
            this.tvTarget.setText(getString(C1680R.string.main_menu_target, Integer.valueOf(SportHelper.getCompleteSer())));
        }
        this.tvTargetStep.setText(getString(C1680R.string.mine_goal_unit, Integer.valueOf(SPUtils.getTargetStep())));
    }

    @Subscribe
    public void onEventMainThread(EventGetStatis event) {
        updateData();
    }

    @Subscribe
    public void onEventMainThread(EventUserFetch event) {
        showUserData();
    }

    private void updateDeviceLayout() {
        if (this.btDevice != null) {
            Log.i(TAG, "updateDeviceLayout:" + this.btDevice.getConnectState());
            if (this.btDevice.getConnectState() == CONNECT_STATE.CONNECTED) {
                this.connectState.setImageResource(C1680R.drawable.bt_state_connect);
                this.deviceSetting.setVisibility(0);
                if (this.btDevice.getName().contains("Primo 5") || this.btDevice.getName().contains("Primo 3") || this.btDevice.getName().contains("EAMEY P3")) {
                    this.deviceSetting.setVisibility(8);
                }
                updateDeviceInfo();
            } else if (this.btDevice.getConnectState() == CONNECT_STATE.CONNECTING) {
                this.deviceName.setText(this.btDevice.getName());
                this.connectState.setImageResource(C1680R.drawable.bt_connecting);
                AnimUtils.playAnimList(this.connectState.getDrawable());
                this.deviceBattery.setText(C1680R.string.connect_state_connecting);
                this.deviceSetting.setVisibility(8);
            } else {
                this.deviceName.setText(this.btDevice.getName());
                this.connectState.setImageResource(C1680R.drawable.bt_state_disconnect);
                this.deviceBattery.setText(C1680R.string.connect_state_disconnect);
                this.deviceSetting.setVisibility(8);
            }
        }
    }

    private void updateDeviceInfo() {
        updateDeviceBattery();
        this.deviceName.setText(this.btDevice.getName());
    }

    private void updateDeviceBattery() {
        if (this.btDevice.getBattery() == 101) {
            this.deviceBattery.setText(C1680R.string.battery_charging);
        } else if (this.btDevice.getBattery() == 102) {
            this.deviceBattery.setText(C1680R.string.battery_complete);
        } else {
            this.deviceBattery.setText(this.btDevice.getBattery() + "%");
        }
    }

    public void onDestroy() {
        super.onDestroy();
        BtManagerService.removeConnectCallback(this.callback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("zhuqichao", "UserFragment.onActivityResult");
        if (resultCode == -1) {
            switch (requestCode) {
                case 4097:
                    SetPhotoDialog.startPhotoZoom((Fragment) this, data.getData(), 360, 260);
                    return;
                case 4098:
                    if (FileUtils.isSDAvailable()) {
                        SetPhotoDialog.startPhotoZoom((Fragment) this, Uri.fromFile(new File(SetPhotoDialog.filePath)), 360, 260);
                        return;
                    } else {
                        Tools.makeToast((int) C1680R.string.string_sd_not_exist);
                        return;
                    }
                case 4099:
                    Tools.makeToast((int) C1680R.string.userinfo_backphoto_saving);
                    Uri uri = (Uri) data.getParcelableExtra(CropPictureActivity.KEY_RESULT);
                    this.oldPhoto = this.user.getBack();
                    this.photo = new DroiFile(new File(uri.getPath()));
                    saveBackPhoto();
                    return;
                case 4100:
                    this.btDevice = BtManagerService.getConnectDevice(this.callback);
                    return;
                default:
                    return;
            }
        }
    }

    private void saveBackPhoto() {
        this.wheel.setVisibility(0);
        this.photo.saveInBackground(new C19073());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.tv_hint:
                if (Tools.isAppInstalled(SPORT_MUSIC)) {
                    startActivity(TheApp.getContext().getPackageManager().getLaunchIntentForPackage(SPORT_MUSIC));
                    return;
                } else {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://market.droi.com/appDetail-1302947.html")));
                    return;
                }
            case C1680R.id.img_back:
                new SetPhotoDialog((Fragment) this).show((int) C1680R.string.userinfo_set_back);
                return;
            case C1680R.id.btn_history:
                startActivity(new Intent(getContext(), SportHistoryActivity.class));
                return;
            case C1680R.id.img_photo:
            case C1680R.id.tv_usrname:
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                return;
            case C1680R.id.btn_add_device:
                startActivityForResult(new Intent(getContext(), DeviceAddActivity.class), 4100);
                return;
            case C1680R.id.device_info:
                Intent intent1 = new Intent(getContext(), DeviceMineActivity.class);
                intent1.putExtra(DeviceMineActivity.KEY_HAS_UPDATE, this.imgUpdatePoint.getVisibility() == 0);
                startActivity(intent1);
                return;
            case C1680R.id.device_battery:
            case C1680R.id.connect_state:
                Log.i("zhuqichao", "btDevice.getConnectState()=" + this.btDevice.getConnectState());
                if (!BtManagerService.isBluetoothOpen()) {
                    showOpenBtDialog();
                    return;
                } else if (this.btDevice.getConnectState() == CONNECT_STATE.CONNECTED) {
                    startActivity(new Intent(getContext(), DeviceMineActivity.class));
                    return;
                } else {
                    this.connectState.setImageResource(C1680R.drawable.bt_connecting);
                    AnimUtils.playAnimList(this.connectState.getDrawable());
                    this.deviceBattery.setText(C1680R.string.connect_state_connecting);
                    this.btDevice.connect(this.callback);
                    return;
                }
            case C1680R.id.device_setting:
                startActivity(new Intent(getContext(), DeviceSettingActivity.class));
                return;
            case C1680R.id.third_wechat:
                Tools.jumpToWinXin(getContext());
                return;
            case C1680R.id.third_wechat_guide:
                startActivity(new Intent(getContext(), WXGuideActivity.class));
                return;
            case C1680R.id.third_qq:
                if (Tools.isQQLogin()) {
                    startActivity(new Intent(getContext(), QQHealthActivity.class));
                    return;
                } else {
                    startActivity(new Intent(getContext(), QQBandActivity.class));
                    return;
                }
            case C1680R.id.mygoal_setting:
                startActivity(new Intent(getContext(), SportTargetActivity.class));
                return;
            case C1680R.id.menu_target:
                showShareDialog();
                return;
            case C1680R.id.mine_vc_test:
                startActivity(new Intent(getContext(), VCTestActivity.class));
                return;
            case C1680R.id.mine_item_preference:
                startActivity(new Intent(getContext(), AppSettingActivity.class));
                return;
            case C1680R.id.mine_item_feedback:
                DroiFeedback.callFeedback(getContext());
                return;
            case C1680R.id.btn_about:
                startActivity(new Intent(getContext(), AboutActivity.class));
                return;
            default:
                return;
        }
    }

    private void showShareDialog() {
        FullShareDialog dialog = new FullShareDialog(getActivity());
        View convertView = getActivity().getLayoutInflater().inflate(C1680R.layout.layout_share_achieve, null);
        ((TextView) convertView.findViewById(C1680R.id.tv_day_count)).setText(String.valueOf(SportHelper.getCompleteSer()));
        dialog.setContentView(convertView);
        dialog.setMessage(getString(C1680R.string.share_achieve_message, Integer.valueOf(achieve)) + getString(C1680R.string.share_suffix));
        dialog.show();
    }

    private void showOpenBtDialog() {
        MyAlertDialog dialog = new MyAlertDialog(getContext());
        dialog.setTitle((int) C1680R.string.bt_tip_bt_not_open);
        dialog.setMessage((int) C1680R.string.bt_tip_open_bt);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C19095());
        dialog.show();
    }
}
