package com.zhuoyou.plugin.mainFrame;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.tencent.mm.sdk.modelbiz.JumpToBizProfile.Req;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.zhuoyi.account.IAccountListener;
import com.zhuoyi.account.ZyAccount;
import com.zhuoyi.account.util.MD5Util;
import com.zhuoyou.plugin.album.SportsAlbum;
import com.zhuoyou.plugin.ble.BindBleDeviceActivity;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.ble.BluetoothLeService;
import com.zhuoyou.plugin.bluetooth.attach.BTPluginActivity;
import com.zhuoyou.plugin.bluetooth.attach.PluginManager;
import com.zhuoyou.plugin.bluetooth.connection.BtProfileReceiver;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.bluetooth.product.ProductManager;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.cloud.NetUtils;
import com.zhuoyou.plugin.cloud.OpenUrlGetStyle;
import com.zhuoyou.plugin.component.AlarmMainActivity;
import com.zhuoyou.plugin.firmware.FirmwareService;
import com.zhuoyou.plugin.info.PersonalInformation;
import com.zhuoyou.plugin.resideMenu.EquipManagerListActivity;
import com.zhuoyou.plugin.resideMenu.HelpActivity;
import com.zhuoyou.plugin.resideMenu.SettingActivity;
import com.zhuoyou.plugin.running.BuildConfig;
import com.zhuoyou.plugin.running.DayPedometerActivity;
import com.zhuoyou.plugin.running.HomePageFragment;
import com.zhuoyou.plugin.running.Main;
import com.zhuoyou.plugin.running.MotionDataActivity;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.running.WaterIntakeActivity;
import com.zhuoyou.plugin.selfupdate.Constants;
import com.zhuoyou.plugin.selfupdate.MyHandler;
import com.zhuoyou.plugin.selfupdate.RequestAsyncTask;
import com.zhuoyou.plugin.selfupdate.SelfUpdateMain;
import com.zhuoyou.plugin.share.WeiboConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MineFragment extends Fragment implements OnClickListener {
    public static final int BATTERY = 1;
    public static final int MSG_UNREAD = 4;
    public static final int MSG_UPDATE_START = 1000;
    public static final int MSG_UPDATE_VIEW = 1001;
    private static final String TAG = "MineFragment";
    public static final int UPDATA_BLE_DISCONNECT = 5;
    public static final int UPDATA_RED_POINT = 8080;
    public static final int UPDATE_BATTERY = 2;
    public static Handler mHandler;
    private int battery = 0;
    private int batteryNumber = 0;
    private HashMap<String, String> bleBondMap;
    private Bitmap bmp = null;
    private List<BluetoothDevice> bondList;
    private BluetoothAdapter btAdapt;
    private int connectState;
    private LinearLayout connect_device;
    private ImageView connect_state;
    private LinearLayout connected_device;
    private int count = 0;
    private BluetoothDevice currentDevice = null;
    private TextView device_battery;
    private ImageView device_batteryImageView;
    private RelativeLayout device_layout;
    private ImageView device_logo;
    private TextView device_name;
    private ImageView drawer_top_face;
    private TextView enjoy_day;
    private int headIndex;
    private boolean isManager;
    private boolean isUpdate;
    private Button log_in;
    private IWXAPI mApi;
    private BroadcastReceiver mBTConnectReceiver = new C13052();
    private BluetoothAdapter mBluetoothAdapter;
    private View mRootView;
    private List<WidgetItem> mWidgetItems = new ArrayList();
    private ZyAccount mZyAccount;
    private PluginManager manager;
    private LinearLayout manager_layout;
    private String nickname;
    private String preDeviceAddress;
    private boolean preDeviceType = false;
    private String productName = "";
    private ProgressBar progressCircle;
    private TextView signature;
    private RequestAsyncTask tast;
    private ImageView title_setting;
    private int totalNumber = 0;
    private LinearLayout user_layout;
    private TextView usrname;
    private WidgetAdapter widgetAdapter;
    private int[] widgetIcon = new int[]{R.drawable.my_device, R.drawable.firmware_upgrade, R.drawable.data_center, R.drawable.water_intakes, R.drawable.day_pedometer, R.drawable.alarm_brain, R.drawable.wechat, R.drawable.software_update, R.drawable.help};
    private int[] widgetName = new int[]{R.string.equip_manager, R.string.firmware_upgrade, R.string.data_center, R.string.water_intake, R.string.day_pedometer, R.string.brain_alarm, R.string.wechat_rank, R.string.software_updates, R.string.help};
    private ListView widget_listView;

    class C13041 extends Handler {
        C13041() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (MineFragment.this.isAdded()) {
                        int status = msg.arg1;
                        MineFragment.this.battery = msg.arg2 - 32;
                        Bundle bundle = msg.getData();
                        MineFragment.this.totalNumber = bundle.getInt("total_number");
                        MineFragment.this.batteryNumber = bundle.getInt("battery_number");
                        Log.i("hph", "status=spp" + status);
                        Log.i("hph", "totalNumber=" + MineFragment.this.totalNumber + "batteryNumber=" + MineFragment.this.batteryNumber);
                        Log.i("hph", "battery===spp" + MineFragment.this.battery);
                        if (MineFragment.this.battery == 255 || MineFragment.this.batteryNumber == 0) {
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_0);
                            MineFragment.this.device_battery.setTextColor(-65494);
                        } else if (status == 1) {
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_connecting);
                            MineFragment.this.device_battery.setTextColor(-65494);
                            MineFragment.this.battery = -1;
                            Log.i("hph", "battery_connecting  BATTERY classic");
                        } else if (status == 2) {
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_completed);
                        } else if (MineFragment.this.totalNumber == 3) {
                            if (MineFragment.this.batteryNumber == 1) {
                                MineFragment.this.device_battery.setTextColor(-6709081);
                                MineFragment.this.device_batteryImageView.setVisibility(0);
                                MineFragment.this.device_battery.setVisibility(8);
                                MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_one);
                            } else if (MineFragment.this.batteryNumber == 2) {
                                MineFragment.this.device_battery.setTextColor(-6709081);
                                MineFragment.this.device_batteryImageView.setVisibility(0);
                                MineFragment.this.device_battery.setVisibility(8);
                                MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_two);
                            } else if (MineFragment.this.batteryNumber == 3) {
                                MineFragment.this.device_battery.setTextColor(-6709081);
                                MineFragment.this.device_batteryImageView.setVisibility(0);
                                MineFragment.this.device_battery.setVisibility(8);
                                MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_4);
                            }
                        } else if (MineFragment.this.battery > 0 && MineFragment.this.battery <= 25) {
                            MineFragment.this.device_battery.setTextColor(-6709081);
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_1);
                        } else if (25 < MineFragment.this.battery && MineFragment.this.battery <= 50) {
                            MineFragment.this.device_battery.setTextColor(-6709081);
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_2);
                        } else if (50 < MineFragment.this.battery && MineFragment.this.battery <= 75) {
                            MineFragment.this.device_battery.setTextColor(-6709081);
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_3);
                        } else if (75 >= MineFragment.this.battery || MineFragment.this.battery > 100) {
                            MineFragment.this.device_battery.setTextColor(-65494);
                        } else {
                            MineFragment.this.device_battery.setTextColor(-6709081);
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_4);
                            Log.i("hph", "75...100");
                        }
                        MineFragment.this.progressCircle.setVisibility(8);
                        return;
                    }
                    return;
                case 2:
                    MineFragment.this.battery = msg.arg1;
                    if (Util.getDeviceName().equals("M2")) {
                        MineFragment.this.updateM2Battery();
                    } else {
                        if (MineFragment.this.battery > 0 && MineFragment.this.battery <= 25) {
                            MineFragment.this.device_battery.setTextColor(-6709081);
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_1);
                        } else if (25 < MineFragment.this.battery && MineFragment.this.battery <= 50) {
                            MineFragment.this.device_battery.setTextColor(-6709081);
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_2);
                        } else if (50 < MineFragment.this.battery && MineFragment.this.battery <= 75) {
                            MineFragment.this.device_battery.setTextColor(-6709081);
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_3);
                        } else if (75 < MineFragment.this.battery && MineFragment.this.battery <= 100) {
                            MineFragment.this.device_battery.setTextColor(-6709081);
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_4);
                            Log.i("hph", "75...100");
                        } else if (MineFragment.this.battery == 101) {
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_connecting);
                            Log.i("hph", "connecting_battery gone");
                        } else if (MineFragment.this.battery == 102) {
                            MineFragment.this.device_batteryImageView.setVisibility(0);
                            MineFragment.this.device_battery.setVisibility(8);
                            MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_completed);
                        } else {
                            MineFragment.this.device_battery.setTextColor(-65494);
                        }
                        MineFragment.this.progressCircle.setVisibility(8);
                    }
                    Log.i("hph", "battery===" + MineFragment.this.battery);
                    return;
                case 4:
                    MineFragment.this.widgetAdapter.notifyDataSetChanged();
                    return;
                case 5:
                    MineFragment.this.connectState = 2;
                    MineFragment.this.device_batteryImageView.setVisibility(8);
                    MineFragment.this.device_battery.setVisibility(0);
                    MineFragment.this.updateView(Util.getConnectDevice(), R.string.not_connected);
                    MineFragment.this.connect_state.setBackgroundResource(R.drawable.state_disconnect);
                    return;
                case 9:
                    MineFragment.this.device_battery.setText(R.string.connecting);
                    return;
                case 1001:
                    if (msg.obj != null) {
                        MineFragment.this.isUpdate = true;
                        MineFragment.this.widgetAdapter.notifyDataSetChanged();
                        return;
                    }
                    return;
                case MineFragment.UPDATA_RED_POINT /*8080*/:
                    MineFragment.this.updateUserInfo();
                    MineFragment.this.updateMyDeviceInfo();
                    MineFragment.this.initWidgets();
                    return;
                default:
                    return;
            }
        }
    }

    class C13052 extends BroadcastReceiver {
        C13052() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                int status = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                Log.d(MineFragment.TAG, "ACTION_STATE_CHANGED :" + status);
                if (status == 10) {
                    MineFragment.this.updateView(Util.getConnectDevice(), R.string.not_connected);
                    MineFragment.this.connect_state.setBackgroundResource(R.drawable.state_disconnect);
                } else if (status == 12) {
                    Intent intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
                    intent1.putExtra("plugin_cmd", 3);
                    intent1.putExtra("plugin_content", "");
                    MineFragment.this.getContext().sendBroadcast(intent1);
                    MineFragment.this.updateView(Util.getConnectDevice(), R.string.getting_electricity1);
                    MineFragment.this.connect_state.setBackgroundResource(R.drawable.state_connect);
                    Log.i("hph", "getting_electricity000");
                }
            }
            if (BluetoothLeService.relinkCount == 5) {
                MineFragment.this.connectState = 2;
                MineFragment.this.device_batteryImageView.setVisibility(8);
                MineFragment.this.device_battery.setVisibility(0);
                MineFragment.this.connect_state.setBackgroundResource(R.drawable.state_disconnect);
                BluetoothLeService.relinkCount = 0;
            } else if (action.equals("com.zhuoyou.running.connect.success")) {
                MineFragment.this.connectState = 3;
                intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
                intent1.putExtra("plugin_cmd", 3);
                intent1.putExtra("plugin_content", "");
                MineFragment.this.getContext().sendBroadcast(intent1);
                MineFragment.this.updateView(Util.getConnectDevice(), R.string.getting_electricity1);
                MineFragment.this.device_batteryImageView.setImageResource(R.drawable.battery_4);
                MineFragment.this.device_battery.setText(R.string.getting_electricity);
                Log.i("hph", "getting_electricity111");
                MineFragment.this.connect_state.setBackgroundResource(R.drawable.state_connect);
            } else if (action.equals("com.zhuoyou.running.connect.failed")) {
                MineFragment.this.connectState = 2;
                MineFragment.this.device_batteryImageView.setVisibility(8);
                MineFragment.this.device_battery.setVisibility(0);
                MineFragment.this.updateView(Util.getConnectDevice(), R.string.not_connected);
                MineFragment.this.connect_state.setBackgroundResource(R.drawable.state_disconnect);
            }
        }
    }

    private class WidgetAdapter extends BaseAdapter {
        private static final int MAX_H_NUM = 4;
        private Context mContext;
        private List<WidgetItem> mWidgetList = new ArrayList();

        public WidgetAdapter(Context context, List<WidgetItem> list) {
            this.mContext = context;
            this.mWidgetList = list;
        }

        public int getCount() {
            return ((this.mWidgetList.size() + 4) - 1) / 4;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View root = LayoutInflater.from(this.mContext).inflate(R.layout.widget_item, null);
            RelativeLayout[] roots = new RelativeLayout[4];
            ImageView[] icons = new ImageView[4];
            ImageView[] states = new ImageView[4];
            TextView[] names = new TextView[4];
            findViews(root, roots, icons, states, names);
            int max = 4;
            if (getCount() == position + 1) {
                max = this.mWidgetList.size() % 4;
                if (max == 0) {
                    max = 4;
                }
            }
            for (int i = 0; i < max; i++) {
                final int index = (position * 4) + i;
                WidgetItem item = (WidgetItem) this.mWidgetList.get(index);
                icons[i].setImageResource(item.getIcon());
                names[i].setText(item.getName());
                if (item.getName().equals(MineFragment.this.getResources().getString(R.string.software_updates))) {
                    if (MineFragment.this.isUpdate) {
                        states[i].setVisibility(0);
                        states[i].setImageResource(R.drawable.remind_circle);
                    }
                } else if (item.getName().equals(MineFragment.this.getResources().getString(R.string.firmware_upgrade))) {
                    if (Tools.getFirmwear()) {
                        states[i].setVisibility(0);
                        states[i].setImageResource(R.drawable.remind_circle);
                    } else {
                        states[i].setVisibility(8);
                        states[i].setImageResource(R.drawable.remind_circle);
                    }
                } else if (item.getName().equals(MineFragment.this.getResources().getString(R.string.message)) && Tools.getMsgState(this.mContext)) {
                    states[i].setVisibility(0);
                    states[i].setImageResource(R.drawable.remind_circle);
                }
                roots[i].setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        MineFragment.this.widgetClick(index);
                    }
                });
            }
            return root;
        }

        private void findViews(View root, RelativeLayout[] roots, ImageView[] icons, ImageView[] states, TextView[] names) {
            roots[0] = (RelativeLayout) root.findViewById(R.id.gv_item_root1);
            icons[0] = (ImageView) root.findViewById(R.id.gv_item_icon1);
            states[0] = (ImageView) root.findViewById(R.id.gv_item_state1);
            states[0].setVisibility(8);
            int i = 0 + 1;
            names[0] = (TextView) root.findViewById(R.id.gv_item_name1);
            roots[i] = (RelativeLayout) root.findViewById(R.id.gv_item_root2);
            icons[i] = (ImageView) root.findViewById(R.id.gv_item_icon2);
            states[i] = (ImageView) root.findViewById(R.id.gv_item_state2);
            states[i].setVisibility(8);
            int i2 = i + 1;
            names[i] = (TextView) root.findViewById(R.id.gv_item_name2);
            roots[i2] = (RelativeLayout) root.findViewById(R.id.gv_item_root3);
            icons[i2] = (ImageView) root.findViewById(R.id.gv_item_icon3);
            states[i2] = (ImageView) root.findViewById(R.id.gv_item_state3);
            states[i2].setVisibility(8);
            i = i2 + 1;
            names[i2] = (TextView) root.findViewById(R.id.gv_item_name3);
            roots[i] = (RelativeLayout) root.findViewById(R.id.gv_item_root4);
            icons[i] = (ImageView) root.findViewById(R.id.gv_item_icon4);
            states[i] = (ImageView) root.findViewById(R.id.gv_item_state4);
            states[i].setVisibility(8);
            i2 = i + 1;
            names[i] = (TextView) root.findViewById(R.id.gv_item_name4);
        }

        public void notify(List<WidgetItem> lists) {
            this.mWidgetList = lists;
            notifyDataSetChanged();
        }
    }

    private class WidgetItem {
        private int icon;
        private String name;

        private WidgetItem() {
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public int getIcon() {
            return this.icon;
        }
    }

    class C18903 implements IAccountListener {
        C18903() {
        }

        public void onCancel() {
        }

        public void onSuccess(String userInfo) {
            Tools.saveInfoToSharePreference(MineFragment.this.getContext(), userInfo);
            Tools.setLogin(MineFragment.this.getContext(), true);
            if (Tools.getUsrName(MineFragment.this.getContext()).equals("")) {
                MineFragment.this.usrname.setText(Tools.getLoginName(MineFragment.this.getContext()));
            } else {
                MineFragment.this.usrname.setText(Tools.getUsrName(MineFragment.this.getContext()));
            }
            Main.mHandler.sendEmptyMessage(2);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.mine_fragment, container, false);
        this.btAdapt = BluetoothAdapter.getDefaultAdapter();
        this.mZyAccount = new ZyAccount(getContext(), "1102927580", WeiboConstant.CONSUMER_KEY);
        this.manager = PluginManager.getInstance();
        this.mApi = WXAPIFactory.createWXAPI(getActivity(), "wx4d148671075be61c", false);
        initViews();
        mHandler = new C13041();
        IntentFilter intent = new IntentFilter();
        intent.addAction("com.zhuoyou.running.connect.success");
        intent.addAction("com.zhuoyou.running.connect.failed");
        intent.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        getContext().registerReceiver(this.mBTConnectReceiver, intent);
        return this.mRootView;
    }

    private void updateM2Battery() {
        if (this.battery > 0 && this.battery <= 10) {
            this.device_battery.setTextColor(-6709081);
            this.device_batteryImageView.setVisibility(0);
            this.device_battery.setVisibility(8);
            this.device_batteryImageView.setImageResource(R.drawable.battery_0);
        } else if (10 < this.battery && this.battery <= 30) {
            this.device_battery.setTextColor(-6709081);
            this.device_batteryImageView.setVisibility(0);
            this.device_battery.setVisibility(8);
            this.device_batteryImageView.setImageResource(R.drawable.battery_1);
        } else if (30 < this.battery && this.battery <= 60) {
            this.device_battery.setTextColor(-6709081);
            this.device_batteryImageView.setVisibility(0);
            this.device_battery.setVisibility(8);
            this.device_batteryImageView.setImageResource(R.drawable.battery_2);
        } else if (60 < this.battery && this.battery <= 90) {
            this.device_battery.setTextColor(-6709081);
            this.device_batteryImageView.setVisibility(0);
            this.device_battery.setVisibility(8);
            this.device_batteryImageView.setImageResource(R.drawable.battery_3);
            Log.i("hph", "75...100");
        } else if (90 < this.battery && this.battery <= 100) {
            this.device_battery.setTextColor(-6709081);
            this.device_batteryImageView.setVisibility(0);
            this.device_battery.setVisibility(8);
            this.device_batteryImageView.setImageResource(R.drawable.battery_4);
            Log.i("hph", "75...100");
        } else if (this.battery == 101) {
            this.device_batteryImageView.setVisibility(0);
            this.device_battery.setVisibility(8);
            this.device_batteryImageView.setImageResource(R.drawable.battery_connecting);
            Log.i("hph", "connecting_battery gone");
        } else if (this.battery == 102) {
            this.device_batteryImageView.setVisibility(0);
            this.device_battery.setVisibility(8);
            this.device_batteryImageView.setImageResource(R.drawable.battery_completed);
        } else {
            this.device_battery.setTextColor(-65494);
        }
        this.progressCircle.setVisibility(8);
    }

    private void initBluetoothView() {
        if (!this.mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getContext(), R.string.ensure_bluetooth_isenable, 0).show();
        }
    }

    private void updateView(BluetoothDevice connectDevice, int state) {
        this.device_battery.setTextColor(-6709081);
        this.device_battery.setCompoundDrawables(null, null, null, null);
        this.device_battery.setText(state);
        this.progressCircle.setVisibility(8);
        initWidgets();
    }

    private void initViews() {
        this.title_setting = (ImageView) this.mRootView.findViewById(R.id.title_setting);
        this.title_setting.setOnClickListener(this);
        this.user_layout = (LinearLayout) this.mRootView.findViewById(R.id.user_layout);
        this.user_layout.setOnClickListener(this);
        this.drawer_top_face = (ImageView) this.mRootView.findViewById(R.id.drawer_top_face);
        this.usrname = (TextView) this.mRootView.findViewById(R.id.usrname);
        this.signature = (TextView) this.mRootView.findViewById(R.id.signature);
        this.log_in = (Button) this.mRootView.findViewById(R.id.log_in);
        this.log_in.setOnClickListener(this);
        this.device_layout = (RelativeLayout) this.mRootView.findViewById(R.id.device_layout);
        this.device_layout.setOnClickListener(this);
        this.device_logo = (ImageView) this.mRootView.findViewById(R.id.device_logo);
        this.connected_device = (LinearLayout) this.mRootView.findViewById(R.id.connected_device);
        this.device_name = (TextView) this.mRootView.findViewById(R.id.device_name);
        this.device_battery = (TextView) this.mRootView.findViewById(R.id.device_battery);
        this.device_batteryImageView = (ImageView) this.mRootView.findViewById(R.id.device_battery_img);
        this.connect_state = (ImageView) this.mRootView.findViewById(R.id.connect_state);
        this.connect_device = (LinearLayout) this.mRootView.findViewById(R.id.connect_device);
        this.manager_layout = (LinearLayout) this.mRootView.findViewById(R.id.manager_layout);
        this.manager_layout.setOnClickListener(this);
        this.widget_listView = (ListView) this.mRootView.findViewById(R.id.widget_list);
        this.widgetAdapter = new WidgetAdapter(getContext(), this.mWidgetItems);
        this.widget_listView.setAdapter(this.widgetAdapter);
        this.enjoy_day = (TextView) this.mRootView.findViewById(R.id.enjoy_day);
        this.progressCircle = (ProgressBar) this.mRootView.findViewById(R.id.progress_circle);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            LayoutParams params = listView.getLayoutParams();
            params.height = (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + totalHeight;
            listView.setLayoutParams(params);
        }
    }

    private void getEnjoyDays() {
        String today = Tools.getDate(0);
        String enter_day = HomePageFragment.mInstance.firstDay;
        if (enter_day.equals("")) {
            enter_day = today;
        }
        this.count = Tools.getDayCount(enter_day, today, "yyyy-MM-dd");
        if (this.count == 1) {
            this.count = Tools.getAccountRegistDay();
            Log.i("hhp", "count==1" + this.count);
        }
        if (!Tools.getLogin(getContext())) {
            this.count = 1;
        }
        this.enjoy_day.setText(" " + this.count + " ");
        Tools.saveAccountRegistDay(this.count);
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        updateUserInfo();
        updateMyDeviceInfo();
        getDeviceHardware();
        initWidgets();
        getEnjoyDays();
        request();
        Log.i("hph333", "minefragment onResume");
        MobclickAgent.onPageStart(TAG);
    }

    private void updateUserInfo() {
        this.headIndex = Tools.getHead(getContext());
        if (this.headIndex == 10000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/custom");
            this.drawer_top_face.setImageBitmap(this.bmp);
        } else if (this.headIndex == 1000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/logo");
            this.drawer_top_face.setImageBitmap(this.bmp);
        } else {
            this.drawer_top_face.setImageResource(Tools.selectByIndex(this.headIndex));
        }
        if (Tools.getLogin(getContext())) {
            this.log_in.setVisibility(8);
        } else {
            this.log_in.setVisibility(0);
        }
        Log.i("hph333", "getUsrName=" + Tools.getUsrName(getContext()));
        if (Tools.getUsrName(getContext()).equals("")) {
            if (Tools.getLoginName(getContext()).equals("")) {
                this.usrname.setText(R.string.username);
            } else if (Tools.getLoginName(getContext()).contains("@")) {
                this.usrname.setText(Tools.getLoginName(getContext()).split("@")[0]);
            } else {
                this.usrname.setText(Tools.getLoginName(getContext()));
            }
        } else if (Tools.getUsrName(getContext()).contains("@")) {
            this.usrname.setText(Tools.getUsrName(getContext()).split("@")[0]);
        } else {
            this.usrname.setText(Tools.getUsrName(getContext()));
        }
        if (Tools.getSignature(getContext()).equals("")) {
            this.signature.setText(R.string.no_signature);
        } else {
            this.signature.setText(Tools.getSignature(getContext()));
        }
    }

    private void updateMyDeviceInfo() {
        this.bondList = Util.getBondDevice();
        Log.i("hph", "updateMyDeviceInfo");
        this.bleBondMap = Tools.getBleBindDevice(getContext());
        this.preDeviceType = Util.getLatestDeviceType(getContext());
        this.preDeviceAddress = Util.getLatestConnectDeviceAddress(getContext());
        Log.d("TAG", "preDeviceType" + this.preDeviceType);
        Log.d("TAG", "preDeviceAddress" + this.preDeviceAddress);
        if (this.preDeviceType) {
            List<BluetoothDevice> gattConnectedDeviceList = BleManagerService.getInstance() == null ? null : BleManagerService.getInstance().getGattCurrentDevice();
            Log.d("TAG", "gattConnectedDeviceList" + gattConnectedDeviceList);
            if (gattConnectedDeviceList == null) {
                this.currentDevice = null;
            } else if (!TextUtils.isEmpty(this.preDeviceAddress)) {
                for (int i = 0; i < gattConnectedDeviceList.size(); i++) {
                    if (this.preDeviceAddress.equals(((BluetoothDevice) gattConnectedDeviceList.get(i)).getAddress())) {
                        this.currentDevice = (BluetoothDevice) gattConnectedDeviceList.get(i);
                    }
                }
            }
            Log.d("TAG", "currentDevice" + this.currentDevice);
            if (this.currentDevice != null) {
                this.connectState = 3;
                this.productName = Util.getProductName(Tools.keyString(this.bleBondMap, this.preDeviceAddress));
            } else if (TextUtils.isEmpty(this.preDeviceAddress)) {
                this.connectState = 1;
            } else if (this.bleBondMap == null || this.bleBondMap.size() <= 0) {
                this.connectState = 1;
            } else if (this.bleBondMap.containsValue(this.preDeviceAddress)) {
                this.connectState = 2;
            } else {
                this.connectState = 1;
            }
            Log.d("TAG", "connectState" + this.connectState);
            if (this.connectState == 2) {
                this.productName = Util.getProductName(Tools.keyString(this.bleBondMap, this.preDeviceAddress));
            }
            Log.d("TAG", "productName2" + this.productName);
            if (this.connectState == 1) {
                this.device_logo.setVisibility(8);
                this.connected_device.setVisibility(8);
                this.connect_state.setVisibility(8);
                this.manager_layout.setVisibility(8);
                this.connect_device.setVisibility(0);
                this.isManager = false;
                return;
            }
            this.manager.processPlugList(this.productName);
            this.nickname = ProductManager.getInstance().getProductCategory(this.productName);
            this.device_logo.setVisibility(0);
            this.device_logo.setImageResource(Util.getProductIcon(this.productName, true));
            this.connected_device.setVisibility(0);
            this.connect_device.setVisibility(8);
            this.connect_state.setVisibility(0);
            if (this.manager.getPlugBeans().size() > 0) {
                this.manager_layout.setVisibility(0);
            } else {
                this.manager_layout.setVisibility(4);
            }
            this.isManager = true;
            this.device_name.setText(this.productName);
            this.device_battery.setTextColor(-6709081);
            if (this.connectState == 2) {
                this.device_batteryImageView.setVisibility(8);
                this.device_battery.setVisibility(0);
                this.device_battery.setText(R.string.not_connected);
                this.connect_state.setBackgroundResource(R.drawable.state_disconnect);
                Log.i("hph", "BLE state_disconnect connectState == 2 ");
                return;
            }
            Message msg = new Message();
            msg.what = 2;
            msg.arg1 = Tools.getBatteryLevel();
            mHandler.sendMessage(msg);
            Log.d(TAG, "batteryValue=" + this.battery);
            this.progressCircle.setVisibility(8);
            this.device_battery.setText(R.string.getting_electricity);
            this.connect_state.setBackgroundResource(R.drawable.state_connect);
            Log.i("hph", " BLE connect_state.setBackgroundResource(R.drawable.state_connect)");
            return;
        }
        this.currentDevice = BtProfileReceiver.getRemoteDevice();
        if (this.currentDevice != null) {
            this.connectState = 3;
            Tools.setDeviceVersion("");
        } else if (this.bondList == null || this.bondList.size() <= 0) {
            this.connectState = 1;
        } else {
            this.connectState = 2;
            if (this.preDeviceAddress.equals("")) {
                this.currentDevice = (BluetoothDevice) this.bondList.get(0);
            } else {
                this.currentDevice = this.btAdapt.getRemoteDevice(this.preDeviceAddress);
            }
        }
        if (this.currentDevice != null) {
            this.productName = Util.getProductName(this.currentDevice.getName());
        }
        Tools.setCurrentDeviceName(this.productName);
        Log.i("hph012", "productName1=" + this.productName);
        if (this.connectState == 1) {
            this.device_logo.setVisibility(8);
            this.connected_device.setVisibility(8);
            this.connect_state.setVisibility(8);
            this.manager_layout.setVisibility(8);
            this.connect_device.setVisibility(0);
            this.isManager = false;
            return;
        }
        Log.i("hph", "manager.processPlugList(productName);");
        this.manager.processPlugList(this.productName);
        this.nickname = ProductManager.getInstance().getProductCategory(this.productName);
        this.device_logo.setVisibility(0);
        this.device_logo.setImageResource(Util.getProductIcon(this.productName, true));
        Log.i("hph012", "device_logo=" + this.productName);
        Log.i("hph012", "getName=" + this.currentDevice.getName());
        this.connected_device.setVisibility(0);
        this.connect_device.setVisibility(8);
        this.connect_state.setVisibility(0);
        if (this.manager.getPlugBeans().size() > 0) {
            this.manager_layout.setVisibility(0);
        } else {
            this.manager_layout.setVisibility(4);
        }
        this.isManager = true;
        this.device_name.setText(this.currentDevice.getName());
        this.device_battery.setTextColor(-6709081);
        if (this.connectState == 2) {
            this.device_battery.setText(R.string.not_connected);
            this.connect_state.setBackgroundResource(R.drawable.state_disconnect);
            Log.i("hph", "Classic state_disconnect connectState == 2 ");
            return;
        }
        Log.i("hph", "new Intent(com.tyd.plugin.receiver.sendmsg ");
        Intent intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
        intent1.putExtra("plugin_cmd", 3);
        intent1.putExtra("plugin_content", "");
        getContext().sendBroadcast(intent1);
        if (this.battery == 0) {
            this.device_battery.setText(R.string.getting_electricity);
            this.device_batteryImageView.setImageResource(R.drawable.battery_0);
            Log.i("hph", "battery == 0");
        } else if (this.battery == -1) {
            this.device_battery.setText(R.string.getting_electricity);
            this.device_batteryImageView.setImageResource(R.drawable.battery_connecting);
            this.device_battery.setTextColor(-6709081);
            Log.i("hph", "battery == -1");
        } else if (this.battery == 255) {
            this.device_batteryImageView.setImageResource(R.drawable.battery_0);
            this.device_battery.setTextColor(-65494);
            Log.i("hph", "battery == 0xff)");
        } else {
            this.device_battery.setText(R.string.getting_electricity);
            Log.i("hph", "progressCircle.setVisibility(View.VISIBLE)");
        }
        this.connect_state.setBackgroundResource(R.drawable.state_connect);
        Log.i("hph", "connect_state.setBackgroundResource(R.drawable.state_connect)");
    }

    private void initWidgets() {
        this.mWidgetItems.clear();
        int i = 0;
        while (i < this.widgetIcon.length) {
            WidgetItem item = new WidgetItem();
            if (!BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR) || this.widgetName[i] != R.string.wechat_rank) {
                if (i == 0) {
                    if (this.isManager) {
                        item.setIcon(this.widgetIcon[i]);
                        item.setName(getResources().getString(this.widgetName[i]));
                        this.mWidgetItems.add(item);
                    }
                } else if (i != 1) {
                    item.setIcon(this.widgetIcon[i]);
                    item.setName(getResources().getString(this.widgetName[i]));
                    this.mWidgetItems.add(item);
                } else if (this.connectState == 3) {
                    item.setIcon(this.widgetIcon[i]);
                    item.setName(getResources().getString(this.widgetName[i]));
                    this.mWidgetItems.add(item);
                }
            }
            i++;
        }
        if (this.widgetAdapter == null) {
            this.widgetAdapter = new WidgetAdapter(getContext(), this.mWidgetItems);
        }
        this.widgetAdapter.notify(this.mWidgetItems);
        setListViewHeightBasedOnChildren(this.widget_listView);
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.device_layout:
                if (this.connectState == 1) {
                    intent = new Intent(getContext(), BindBleDeviceActivity.class);
                    intent.setFlags(268435456);
                    getContext().startActivity(intent);
                    return;
                } else if (this.connectState == 2) {
                    if (this.mBluetoothAdapter.isEnabled()) {
                        this.device_battery.setText(R.string.connecting);
                        if (!this.preDeviceType) {
                            Util.connect(this.currentDevice);
                            return;
                        } else if (this.productName != null) {
                            Tools.setConnectNotVibtation(false);
                            intent = new Intent(BleManagerService.ACTION_CONNECT_BINDED_DEVICE);
                            intent.putExtra("deviceName", this.productName);
                            getContext().sendBroadcast(intent);
                            return;
                        } else {
                            return;
                        }
                    }
                    Tools.makeToast(getString(R.string.tip_open_bt));
                    return;
                } else if (this.connectState == 3 && this.manager.getPlugBeans().size() > 0) {
                    intent = new Intent(getContext(), BTPluginActivity.class);
                    intent.putExtra("nick_name", this.nickname);
                    intent.putExtra("remote_name", this.productName);
                    intent.putExtra("enable_state", true);
                    intent.putExtra("connect_state", this.connectState);
                    intent.setFlags(268435456);
                    getContext().startActivity(intent);
                    return;
                } else {
                    return;
                }
            case R.id.log_in:
                this.mZyAccount.login(new C18903());
                return;
            case R.id.title_setting:
                intent.setClass(getContext(), SettingActivity.class);
                startActivity(intent);
                return;
            case R.id.user_layout:
                intent.setClass(getContext(), PersonalInformation.class);
                startActivityForResult(intent, 100);
                return;
            case R.id.manager_layout:
                if (this.manager.getPlugBeans().size() > 0) {
                    Log.i("hph", "productName 111=" + this.productName);
                    intent = new Intent(getContext(), BTPluginActivity.class);
                    intent.putExtra("nick_name", this.nickname);
                    intent.putExtra("remote_name", this.productName);
                    intent.putExtra("enable_state", true);
                    intent.putExtra("connect_state", this.connectState);
                    intent.setFlags(268435456);
                    getContext().startActivity(intent);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroyView() {
        super.onDestroy();
        getContext().unregisterReceiver(this.mBTConnectReceiver);
        if (this.bmp != null) {
            this.bmp.recycle();
            this.bmp = null;
            System.gc();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1) {
            if (Tools.getUsrName(getContext()).equals("")) {
                this.usrname.setText(Tools.getLoginName(getContext()));
            } else {
                this.usrname.setText(Tools.getUsrName(getContext()));
            }
            Main.mHandler.sendEmptyMessage(2);
        }
    }

    public void request() {
        this.isUpdate = false;
        if (!SelfUpdateMain.isDownloading) {
            new RequestAsyncTask(getContext(), mHandler, 1001, Constants.APPID, Constants.CHNID).startRun();
        }
    }

    private void widgetClick(int index) {
        String name = ((WidgetItem) this.mWidgetItems.get(index)).getName();
        Intent intent = new Intent();
        if (name.equals(getResources().getString(R.string.equip_manager))) {
            intent.setClass(getContext(), EquipManagerListActivity.class);
            intent.putExtra("battery", this.device_battery.getText());
            startActivity(intent);
        } else if (name.equals(getResources().getString(R.string.firmware_upgrade))) {
            Toast.makeText(getContext(), R.string.isgoing_check_update, 0).show();
            getContext().startService(new Intent(getContext(), FirmwareService.class));
            this.preDeviceType = Util.getLatestDeviceType(getContext());
            if (this.preDeviceType) {
                getContext().sendBroadcast(new Intent(BleManagerService.ACTION_GET_FIRMWARE_VERSION));
                Log.i("hph", "BLE ACTION_GET_FIRMWARE_VERSION");
                return;
            }
            Intent intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
            intent1.putExtra("plugin_cmd", 5);
            intent1.putExtra("plugin_content", "1");
            getContext().sendBroadcast(intent1);
            if (Util.getProductName(this.currentDevice.getName()).equals("Unik 3") || Util.getProductName(this.currentDevice.getName()).equals("UNIK 3SE")) {
                Tools.setFirmwear(false);
                Toast.makeText(getContext(), R.string.firmware_upgrade_none, 0).show();
            }
        } else if (name.equals(getResources().getString(R.string.data_center))) {
            intent.setClass(getContext(), MotionDataActivity.class);
            startActivity(intent);
        } else if (name.equals(getResources().getString(R.string.sports_photo))) {
            intent.setClass(getContext(), SportsAlbum.class);
            startActivity(intent);
        } else if (name.equals(getResources().getString(R.string.software_updates))) {
            if (NetUtils.getAPNType(getContext()) == -1) {
                Toast.makeText(getContext(), R.string.check_network, 0).show();
            } else if (!SelfUpdateMain.isDownloading) {
                Toast.makeText(getContext(), R.string.isgoing_check_update, 0).show();
                MyHandler h = new MyHandler(getContext(), true);
                if (this.tast == null || this.tast.getStatus() == Status.FINISHED) {
                    this.tast = new RequestAsyncTask(getContext(), h, 1001, Constants.APPID, Constants.CHNID);
                    this.tast.startRun();
                }
            }
        } else if (name.equals(getResources().getString(R.string.help))) {
            intent.setClass(getContext(), HelpActivity.class);
            startActivity(intent);
        } else if (name.equals(getResources().getString(R.string.wechat_rank))) {
            if (Tools.getLogin(getContext()) && this.mApi.isWXAppInstalled() && this.mApi.isWXAppSupportAPI()) {
                Req req = new Req();
                req.toUserName = "gh_5fd67da1b3b7";
                req.profileType = 1;
                req.extMsg = "http://we.qq.com/d/AQDZxL24c6Og1v0vrWv6PN1KKxu57I_kcPJlxwnD#" + Tools.getOpenId(getContext());
                this.mApi.sendReq(req);
            } else if (this.mApi.isWXAppInstalled() && this.mApi.isWXAppSupportAPI()) {
                Toast.makeText(getContext(), R.string.login_before_wechat, 0).show();
            } else {
                Toast.makeText(getContext(), R.string.have_no_wechat, 0).show();
            }
        } else if (name.equals(getResources().getString(R.string.brain_alarm))) {
            if (BluetoothService.getInstance().isConnected() || (RunningApp.isBLESupport && BleManagerService.getInstance().GetBleConnectState())) {
                startActivity(new Intent(getContext(), AlarmMainActivity.class));
            } else {
                Toast.makeText(getContext(), R.string.alarm_toast, 0).show();
            }
        } else if (name.equals(getResources().getString(R.string.water_intake))) {
            startActivity(new Intent(getContext(), WaterIntakeActivity.class));
        } else if (name.equals(getResources().getString(R.string.day_pedometer))) {
            startActivity(new Intent(getContext(), DayPedometerActivity.class));
        }
    }

    public String getAccessToken() {
        String WATCH_APP_ID = "wxa551e6c5a57445a5";
        Log.i("zhaojunhui", "jsonStr is :" + OpenUrlGetStyle.getAccessToken("http://open.zhuoyoutech.com/DroiWechatSport/phone/index.php/DroiWechatApi/getWechatToken", "wechat_app_id=" + WATCH_APP_ID + "&request_platform=" + "1" + "&sign=" + MD5Util.md5(WATCH_APP_ID + "1" + "82beef362d1539ecd1e82830a7eb95d3") + "&test=test"));
        return null;
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    private void getDeviceHardware() {
        Intent intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
        intent1.putExtra("plugin_cmd", 97);
        intent1.putExtra("plugin_content", "");
        getContext().sendBroadcast(intent1);
    }
}
