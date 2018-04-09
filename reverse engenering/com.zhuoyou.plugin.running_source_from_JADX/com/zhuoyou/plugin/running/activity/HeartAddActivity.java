package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtManagerService;
import com.droi.btlib.service.BtManagerService.CONNECT_STATE;
import com.droi.btlib.service.BtManagerService.END_STATE;
import com.droi.btlib.service.BtManagerService.GetHeartCallback;
import com.droi.greendao.bean.HeartBean;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.EventGetHeart;
import com.zhuoyou.plugin.running.database.HeartHelper;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.HeartAnimView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;

public class HeartAddActivity extends BaseActivity {
    public static final String KEY_CLICK_ITEM = "key_click_item";
    private static final int MSG_UPDATE_TIME = 4098;
    private static final int REQUEST_CODE_HEART_DETAIL = 4098;
    private static final int REQUEST_CODE_HEART_HINT = 4097;
    private static int TEST_TIME = 20;
    private BaseAdapter adapter = new C17774();
    private BtDevice btDevice = BtManagerService.getConnectDevice(null);
    private List<HeartBean> dataList = new ArrayList();
    private String format;
    private HeartAnimView heartView;
    private boolean isFirstHint = true;
    private boolean isTesting;
    private LinearLayout layoutResult;
    private Handler mHandler = new C17763();
    private ArrayList<Integer> resultList = new ArrayList();
    private int testTime = TEST_TIME;
    private TextView tvCount;
    private TextView tvHint;
    private TextView tvOxygen;
    private TextView tvStart;
    private ListView viewList;

    class C17741 implements OnItemClickListener {
        C17741() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            HeartAddActivity.this.startActivityForResult(new Intent(HeartAddActivity.this, HeartDetailActivity.class).putExtra(HeartAddActivity.KEY_CLICK_ITEM, ((HeartBean) HeartAddActivity.this.dataList.get(position)).getDate()), 4098);
        }
    }

    class C17752 implements GetHeartCallback {
        C17752() {
        }

        public void success(int heart) {
            if (heart > 0) {
                HeartAddActivity.this.resultList.add(Integer.valueOf(heart));
            }
        }

        public void end(END_STATE state) {
            switch (state) {
                case COMPLETE:
                    HeartAddActivity.this.onTestFinish();
                    return;
                default:
                    Tools.makeToast((int) C1680R.string.heart_measure_failed);
                    HeartAddActivity.this.reset();
                    return;
            }
        }
    }

    class C17763 extends Handler {
        C17763() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 4098:
                    HeartAddActivity.this.tvStart.setText(HeartAddActivity.this.getString(C1680R.string.heart_measuring, new Object[]{Integer.valueOf(HeartAddActivity.this.testTime)}));
                    HeartAddActivity.this.testTime = HeartAddActivity.this.testTime - 1;
                    if (HeartAddActivity.this.testTime > 0) {
                        HeartAddActivity.this.mHandler.sendEmptyMessageDelayed(4098, 1000);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    class C17774 extends BaseAdapter {

        class ViewHolder {
            TextView tvCount;
            TextView tvTime;

            ViewHolder() {
            }
        }

        C17774() {
        }

        public int getCount() {
            return HeartAddActivity.this.dataList.size();
        }

        public HeartBean getItem(int position) {
            return (HeartBean) HeartAddActivity.this.dataList.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(HeartAddActivity.this).inflate(C1680R.layout.item_heart_count_list, parent, false);
                holder.tvTime = (TextView) convertView.findViewById(C1680R.id.tv_time);
                holder.tvCount = (TextView) convertView.findViewById(C1680R.id.tv_heart_count);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            HeartBean bean = (HeartBean) HeartAddActivity.this.dataList.get(position);
            holder.tvTime.setText(Tools.formatDate(bean.getDate(), Tools.DEFAULT_FORMAT_TIME, HeartAddActivity.this.format));
            holder.tvCount.setText(String.valueOf(bean.getCount()));
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_heart_add);
        if (this.btDevice == null || !this.btDevice.getName().equals("U3")) {
            TEST_TIME = 20;
        } else {
            TEST_TIME = 30;
        }
        this.testTime = TEST_TIME;
        this.format = getString(C1680R.string.heart_time_format);
        initView();
        initData(false, true);
    }

    private void initView() {
        this.tvStart = (TextView) findViewById(C1680R.id.btn_start);
        this.tvHint = (TextView) findViewById(C1680R.id.tv_hint);
        this.viewList = (ListView) findViewById(C1680R.id.heart_count_list);
        this.layoutResult = (LinearLayout) findViewById(C1680R.id.layout_result);
        this.heartView = (HeartAnimView) findViewById(C1680R.id.heart_testing);
        this.tvCount = (TextView) findViewById(C1680R.id.tv_heart_count);
        this.tvOxygen = (TextView) findViewById(C1680R.id.heart_oxygen);
        this.viewList.setAdapter(this.adapter);
        this.viewList.setOnItemClickListener(new C17741());
    }

    private void initData(boolean result, boolean success) {
        this.tvOxygen.setVisibility(4);
        this.dataList = HeartHelper.getHeartList();
        if (this.dataList.size() > 0) {
            HeartBean bean = (HeartBean) this.dataList.get(0);
            this.tvCount.setText(String.valueOf(bean.getCount()));
            if (!result) {
                this.tvHint.setText(Tools.formatDate(bean.getDate(), Tools.DEFAULT_FORMAT_TIME, this.format));
            } else if (success) {
                this.tvHint.setText(C1680R.string.heart_count_result);
                if (this.btDevice != null && "U3".equals(this.btDevice.getName())) {
                    this.tvOxygen.setVisibility(0);
                    this.tvOxygen.setText(getString(C1680R.string.heart_oxygen_value, new Object[]{Tools.getHeartOxygen(bean.getCount()) + "%"}));
                }
            } else {
                this.tvCount.setText(String.valueOf(0));
                this.tvHint.setText(C1680R.string.heart_measure_fail);
            }
        } else {
            this.tvCount.setText(String.valueOf(0));
            this.tvHint.setText(C1680R.string.heart_count_no_data);
        }
        this.adapter.notifyDataSetChanged();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_start:
                if (this.btDevice == null) {
                    Tools.makeToast((int) C1680R.string.heart_no_device);
                    return;
                } else if (!this.btDevice.supportHeart()) {
                    Tools.makeToast((int) C1680R.string.heart_device_not_support);
                    return;
                } else if (this.btDevice.getConnectState() != CONNECT_STATE.CONNECTED) {
                    Tools.makeToast((int) C1680R.string.heart_device_not_connect);
                    return;
                } else if (!this.isTesting) {
                    if (this.isFirstHint && SPUtils.isShowHeartHint()) {
                        startActivityForResult(new Intent(this, HeartHintActivity.class), 4097);
                        this.isFirstHint = false;
                        return;
                    }
                    startMeasure();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    private void startMeasure() {
        this.isTesting = true;
        this.testTime = TEST_TIME;
        this.resultList.clear();
        this.heartView.setVisibility(0);
        this.layoutResult.setVisibility(8);
        this.mHandler.sendEmptyMessage(4098);
        this.btDevice.getHeartInfo(TEST_TIME, new C17752());
    }

    private void onTestFinish() {
        int heart = getAverage();
        if (heart > 0) {
            HeartBean bean = new HeartBean();
            bean.setAccount(((User) DroiUser.getCurrentUser(User.class)).getUserId());
            bean.setDate(Tools.getCurrentTime());
            bean.setCount(heart);
            HeartHelper.getBeanDao().insertOrReplace(bean);
            initData(true, true);
        } else {
            Tools.makeToast((int) C1680R.string.heart_measure_nodata);
            startActivity(new Intent(this, HeartHintActivity.class).putExtra(HeartHintActivity.KEY_SHOW_NOT_SHOW, false));
            initData(true, false);
        }
        reset();
    }

    private void reset() {
        this.mHandler.removeMessages(4098);
        this.tvStart.setText(C1680R.string.heart_count_measure);
        this.resultList.clear();
        this.heartView.setVisibility(8);
        this.layoutResult.setVisibility(0);
        this.isTesting = false;
        this.testTime = TEST_TIME;
    }

    private int getAverage() {
        int result = 0;
        Iterator it = this.resultList.iterator();
        while (it.hasNext()) {
            result += ((Integer) it.next()).intValue();
        }
        if (this.resultList.size() > 0) {
            return result / this.resultList.size();
        }
        return result;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 4097:
                startMeasure();
                return;
            case 4098:
                if (resultCode == -1) {
                    initData(false, true);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        if (this.isTesting) {
            Tools.makeToast((int) C1680R.string.heart_measuring_wait);
            return;
        }
        super.onBackPressed();
        BaasHelper.uploadHeartInBackground();
    }

    @Subscribe
    public void onEventMainThread(EventGetHeart event) {
        initData(false, true);
    }
}
