package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;
import java.util.ArrayList;
import java.util.List;

public class PushMessageActivity extends BaseActivity {
    private static String PHONE_PKG_NAME = "com.android.dialer";
    private static final String PUSH_SHARE_LIST_NAME = "droi_share_push_list";
    private static String SMS_PKG_NAME = "com.android.mms";
    private static String SYSTEMUI_PKG_NAME = "com.android.systemui";
    private static final String TAG = "PushMessageActivity";
    private MyAppAdapter adapter;
    private List<AppInfo> appList;
    private ListView lvAppList;
    PackageManager pManager;
    private boolean selectAll = true;
    private TextView tvSelectAll;
    private TextView tvSelectApp;

    class C17931 implements OnItemClickListener {
        C17931() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            ((AppInfo) PushMessageActivity.this.appList.get(position)).setSelect(!((AppInfo) PushMessageActivity.this.appList.get(position)).isSelect());
            PushMessageActivity.this.adapter.notifyDataSetChanged();
            PushMessageActivity.this.updateSelectNum();
        }
    }

    class AppInfo {
        ResolveInfo info;
        boolean select;

        AppInfo() {
        }

        public ResolveInfo getInfo() {
            return this.info;
        }

        public void setInfo(ResolveInfo info) {
            this.info = info;
        }

        public boolean isSelect() {
            return this.select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }

    class MyAppAdapter extends BaseAdapter {

        class ViewHolder {
            ImageView appIcon;
            TextView appName;
            ImageView select;

            ViewHolder() {
            }
        }

        MyAppAdapter() {
        }

        public int getCount() {
            return PushMessageActivity.this.appList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holer;
            if (convertView == null) {
                holer = new ViewHolder();
                convertView = View.inflate(PushMessageActivity.this, C1680R.layout.item_push_message, null);
                holer.appIcon = (ImageView) convertView.findViewById(C1680R.id.img_app_icon);
                holer.appName = (TextView) convertView.findViewById(C1680R.id.tv_app_name);
                holer.select = (ImageView) convertView.findViewById(C1680R.id.img_app_select);
                convertView.setTag(holer);
            } else {
                holer = (ViewHolder) convertView.getTag();
            }
            AppInfo info = (AppInfo) PushMessageActivity.this.appList.get(position);
            holer.appIcon.setImageDrawable(info.getInfo().loadIcon(PushMessageActivity.this.pManager));
            holer.appName.setText(info.getInfo().loadLabel(PushMessageActivity.this.pManager).toString());
            holer.select.setSelected(info.isSelect());
            return convertView;
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_push_message);
        initView();
        initData();
    }

    private void initView() {
        this.tvSelectApp = (TextView) findViewById(C1680R.id.tv_select_app);
        this.tvSelectAll = (TextView) findViewById(C1680R.id.tv_all_select);
        this.lvAppList = (ListView) findViewById(C1680R.id.lv_app_list);
        this.lvAppList.setOnItemClickListener(new C17931());
    }

    private void initData() {
        this.pManager = getPackageManager();
        this.appList = getAppList();
        updateSelectNum();
        this.adapter = new MyAppAdapter();
        this.lvAppList.setAdapter(this.adapter);
    }

    private void updateSelectNum() {
        int count = 0;
        for (AppInfo info : this.appList) {
            if (info.isSelect()) {
                count++;
            }
        }
        this.tvSelectApp.setText(String.format(getString(C1680R.string.push_message_select_app), new Object[]{Integer.valueOf(count), Integer.valueOf(this.appList.size())}));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.tv_all_select:
                for (AppInfo info : this.appList) {
                    info.setSelect(this.selectAll);
                }
                this.adapter.notifyDataSetChanged();
                this.selectAll = !this.selectAll;
                updateSelectNum();
                return;
            default:
                return;
        }
    }

    private List<AppInfo> getAppList() {
        Intent it = new Intent("android.intent.action.MAIN");
        it.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(it, 0);
        ArrayList<AppInfo> infoList = new ArrayList();
        List<String> selectList = SPUtils.getList(PUSH_SHARE_LIST_NAME);
        for (ResolveInfo info : resInfo) {
            if (!(info.activityInfo.packageName.contains(SMS_PKG_NAME) || info.activityInfo.packageName.contains(PHONE_PKG_NAME) || info.activityInfo.packageName.contains(SYSTEMUI_PKG_NAME))) {
                AppInfo appInfo = new AppInfo();
                appInfo.setInfo(info);
                infoList.add(appInfo);
                appInfo.setSelect(false);
                for (String appName : selectList) {
                    String[] activityInfo = appName.split("\\|");
                    if (activityInfo[0].equals(info.activityInfo.packageName) && activityInfo[1].equals(info.activityInfo.name)) {
                        appInfo.setSelect(true);
                    }
                }
            }
        }
        return infoList;
    }

    protected void onDestroy() {
        ArrayList<String> shareList = new ArrayList();
        for (int i = 0; i < this.appList.size(); i++) {
            if (((AppInfo) this.appList.get(i)).isSelect()) {
                Log.i("yuanzz", "select Name:" + ((AppInfo) this.appList.get(i)).getInfo().activityInfo.packageName);
                shareList.add(((AppInfo) this.appList.get(i)).getInfo().activityInfo.packageName + "|" + ((AppInfo) this.appList.get(i)).getInfo().activityInfo.name);
            }
        }
        SPUtils.saveList(PUSH_SHARE_LIST_NAME, shareList);
        super.onDestroy();
    }
}
