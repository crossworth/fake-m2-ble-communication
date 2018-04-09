package com.zhuoyou.plugin.bluetooth.attach;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.data.IgnoreList;
import com.zhuoyou.plugin.bluetooth.data.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SelectNotifiActivity extends Activity {
    private static final String TAB_TAG_PERSONAL_APP = "personal_app";
    private static final String TAB_TAG_SYSTEM_APP = "system_app";
    private static final String VIEW_ITEM_CHECKBOX = "package_checkbox";
    private static final String VIEW_ITEM_ICON = "package_icon";
    private static final String VIEW_ITEM_NAME = "package_name";
    private static final String VIEW_ITEM_TEXT = "package_text";
    private static final int[] VIEW_RES_ID_ARRAY = new int[]{R.id.package_icon, R.id.package_text, R.id.package_switch};
    private static final String[] VIEW_TEXT_ARRAY = new String[]{VIEW_ITEM_ICON, VIEW_ITEM_TEXT, VIEW_ITEM_CHECKBOX};
    private SimpleAdapter mPersonalAppAdapter = null;
    private List<Map<String, Object>> mPersonalAppList = null;
    private int mPersonalAppSelectedCount = 0;
    private Button mSelectAllPersonalAppButton = null;
    private Button mSelectAllSystemAppButton = null;
    private SimpleAdapter mSystemAppAdapter = null;
    private List<Map<String, Object>> mSystemAppList = null;
    private int mSystemAppSelectedCount = 0;
    private TabHost mTabHost = null;
    private View mView1;
    private View mView2;

    class C11821 implements OnClickListener {
        C11821() {
        }

        public void onClick(View v) {
            SelectNotifiActivity.this.finish();
        }
    }

    class C11832 implements OnTabChangeListener {
        C11832() {
        }

        public void onTabChanged(String tabId) {
            SelectNotifiActivity.this.mTabHost.setCurrentTabByTag(tabId);
            SelectNotifiActivity.this.updateTab(SelectNotifiActivity.this.mTabHost);
            switch (SelectNotifiActivity.this.mTabHost.getCurrentTab()) {
                case 0:
                    SelectNotifiActivity.this.mView1.setVisibility(0);
                    SelectNotifiActivity.this.mView2.setVisibility(4);
                    return;
                case 1:
                    SelectNotifiActivity.this.mView1.setVisibility(4);
                    SelectNotifiActivity.this.mView2.setVisibility(0);
                    return;
                default:
                    return;
            }
        }
    }

    class C11843 implements OnItemClickListener {
        C11843() {
        }

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Map<String, Object> item = (Map) arg0.getItemAtPosition(arg2);
            if (item != null) {
                int countVariation;
                boolean isSelected = ((Boolean) item.get(SelectNotifiActivity.VIEW_ITEM_CHECKBOX)).booleanValue();
                item.remove(SelectNotifiActivity.VIEW_ITEM_CHECKBOX);
                item.put(SelectNotifiActivity.VIEW_ITEM_CHECKBOX, Boolean.valueOf(!isSelected));
                if (isSelected) {
                    countVariation = -1;
                } else {
                    countVariation = 1;
                }
                if (SelectNotifiActivity.this.isPersonalAppTabSelected()) {
                    SelectNotifiActivity.this.mPersonalAppSelectedCount = SelectNotifiActivity.this.mPersonalAppSelectedCount + countVariation;
                    SelectNotifiActivity.this.mPersonalAppAdapter.notifyDataSetChanged();
                    SelectNotifiActivity.this.updateSelectAllButtonText(SelectNotifiActivity.TAB_TAG_PERSONAL_APP);
                    return;
                }
                SelectNotifiActivity.this.mSystemAppSelectedCount = SelectNotifiActivity.this.mSystemAppSelectedCount + countVariation;
                SelectNotifiActivity.this.mSystemAppAdapter.notifyDataSetChanged();
                SelectNotifiActivity.this.updateSelectAllButtonText(SelectNotifiActivity.TAB_TAG_SYSTEM_APP);
            }
        }
    }

    class C11854 implements ViewBinder {
        C11854() {
        }

        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if (!(view instanceof ImageView) || !(data instanceof Drawable)) {
                return false;
            }
            ((ImageView) view).setImageDrawable((Drawable) data);
            return true;
        }
    }

    class C11865 implements OnClickListener {
        C11865() {
        }

        public void onClick(View v) {
            SelectNotifiActivity.this.saveIgnoreList();
        }
    }

    class C11876 implements OnClickListener {
        C11876() {
        }

        public void onClick(View v) {
            SelectNotifiActivity.this.toggleAllListItemSelection();
        }
    }

    private class LoadPackageTask extends AsyncTask<String, Integer, Boolean> {
        private final Context mContext;
        private ProgressDialog mProgressDialog = null;

        public LoadPackageTask(Context context) {
            this.mContext = context;
            createProgressDialog();
        }

        private void createProgressDialog() {
            if (this.mProgressDialog == null) {
                this.mProgressDialog = new ProgressDialog(this.mContext);
                this.mProgressDialog.setMessage(this.mContext.getString(R.string.progress_dialog_message));
            }
            this.mProgressDialog.show();
        }

        protected Boolean doInBackground(String... arg0) {
            loadPackageList();
            sortPackageList();
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean result) {
            SelectNotifiActivity.this.initUiComponents();
            if (this.mProgressDialog != null) {
                this.mProgressDialog.dismiss();
                this.mProgressDialog = null;
            }
        }

        private void loadPackageList() {
            SelectNotifiActivity.this.mPersonalAppList = new ArrayList();
            SelectNotifiActivity.this.mSystemAppList = new ArrayList();
            HashSet<String> ignoreList = IgnoreList.getInstance().getIgnoreList();
            HashSet<String> exclusionList = IgnoreList.getInstance().getExclusionList();
            for (PackageInfo packageInfo : SelectNotifiActivity.this.getPackageManager().getInstalledPackages(0)) {
                if (!(packageInfo == null || exclusionList.contains(packageInfo.packageName))) {
                    int countVariable;
                    Map<String, Object> packageItem = new HashMap();
                    packageItem.put(SelectNotifiActivity.VIEW_ITEM_ICON, this.mContext.getPackageManager().getApplicationIcon(packageInfo.applicationInfo));
                    packageItem.put(SelectNotifiActivity.VIEW_ITEM_TEXT, this.mContext.getPackageManager().getApplicationLabel(packageInfo.applicationInfo).toString());
                    packageItem.put("package_name", packageInfo.packageName);
                    boolean isChecked = ignoreList.contains(packageInfo.packageName);
                    packageItem.put(SelectNotifiActivity.VIEW_ITEM_CHECKBOX, Boolean.valueOf(isChecked));
                    if (isChecked) {
                        countVariable = 1;
                    } else {
                        countVariable = 0;
                    }
                    if (Util.isSystemApp(packageInfo.applicationInfo)) {
                        SelectNotifiActivity.this.mSystemAppList.add(packageItem);
                        SelectNotifiActivity.this.mSystemAppSelectedCount = SelectNotifiActivity.this.mSystemAppSelectedCount + countVariable;
                    } else {
                        SelectNotifiActivity.this.mPersonalAppList.add(packageItem);
                        SelectNotifiActivity.this.mPersonalAppSelectedCount = SelectNotifiActivity.this.mPersonalAppSelectedCount + countVariable;
                    }
                }
            }
        }

        private void sortPackageList() {
            PackageItemComparator comparator = new PackageItemComparator();
            if (SelectNotifiActivity.this.mPersonalAppList != null) {
                Collections.sort(SelectNotifiActivity.this.mPersonalAppList, comparator);
            }
            if (SelectNotifiActivity.this.mSystemAppList != null) {
                Collections.sort(SelectNotifiActivity.this.mSystemAppList, comparator);
            }
        }
    }

    private class PackageItemComparator implements Comparator<Map<String, Object>> {
        private final String mKey = SelectNotifiActivity.VIEW_ITEM_TEXT;

        public int compare(Map<String, Object> packageItem1, Map<String, Object> packageItem2) {
            return ((String) packageItem1.get(this.mKey)).compareToIgnoreCase((String) packageItem2.get(this.mKey));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_notifi_activity_layout);
        ((TextView) findViewById(R.id.title)).setText(R.string.select_notifi_activity);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C11821());
        initTabHost();
        new LoadPackageTask(this).execute(new String[]{""});
    }

    private View getTabIndicatorView(Context context, int textViewId) {
        View tabIndicatorView = LayoutInflater.from(context).inflate(R.layout.tab_indicator, null);
        ((TextView) tabIndicatorView.findViewById(R.id.txt_indicator)).setText(textViewId);
        return tabIndicatorView;
    }

    private void initTabHost() {
        this.mView1 = findViewById(R.id.view1);
        this.mView2 = findViewById(R.id.view2);
        this.mTabHost = (TabHost) findViewById(16908306);
        this.mTabHost.setup();
        this.mTabHost.addTab(this.mTabHost.newTabSpec(TAB_TAG_PERSONAL_APP).setContent(R.id.LinearLayout001).setIndicator(getTabIndicatorView(this, R.string.personal_apps_title)));
        this.mTabHost.addTab(this.mTabHost.newTabSpec(TAB_TAG_SYSTEM_APP).setContent(R.id.LinearLayout002).setIndicator(getTabIndicatorView(this, R.string.system_apps_title)));
        updateTab(this.mTabHost);
        this.mTabHost.setOnTabChangedListener(new C11832());
    }

    private void updateTab(TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) this.mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.txt_indicator);
            if (tabHost.getCurrentTab() == i) {
                tv.setTextColor(-15563825);
            } else {
                tv.setTextColor(-9929337);
            }
        }
    }

    private void initUiComponents() {
        OnItemClickListener listener = new C11843();
        ListView mPersonalAppListView = (ListView) findViewById(R.id.list_personal_app);
        this.mPersonalAppAdapter = createAdapter(this.mPersonalAppList);
        mPersonalAppListView.setAdapter(this.mPersonalAppAdapter);
        mPersonalAppListView.setOnItemClickListener(listener);
        ListView mSystemAppListView = (ListView) findViewById(R.id.list_system_app);
        this.mSystemAppAdapter = createAdapter(this.mSystemAppList);
        mSystemAppListView.setAdapter(this.mSystemAppAdapter);
        mSystemAppListView.setOnItemClickListener(listener);
        initCmdBtns();
    }

    private SimpleAdapter createAdapter(List<Map<String, Object>> dataList) {
        SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.package_list_layout, VIEW_TEXT_ARRAY, VIEW_RES_ID_ARRAY);
        adapter.setViewBinder(new C11854());
        return adapter;
    }

    private void saveIgnoreList() {
        HashSet<String> ignoreList = new HashSet();
        for (Map<String, Object> personalAppItem : this.mPersonalAppList) {
            if (((Boolean) personalAppItem.get(VIEW_ITEM_CHECKBOX)).booleanValue()) {
                ignoreList.add((String) personalAppItem.get("package_name"));
            }
        }
        for (Map<String, Object> systemAppItem : this.mSystemAppList) {
            if (((Boolean) systemAppItem.get(VIEW_ITEM_CHECKBOX)).booleanValue()) {
                ignoreList.add((String) systemAppItem.get("package_name"));
            }
        }
        IgnoreList.getInstance().saveIgnoreList(ignoreList);
        Toast.makeText(this, R.string.save_successfully, 0).show();
    }

    private boolean isPersonalAppTabSelected() {
        return this.mTabHost.getCurrentTabTag() == TAB_TAG_PERSONAL_APP;
    }

    private void initCmdBtns() {
        OnClickListener saveButtonListener = new C11865();
        Button savePersonalAppButton = (Button) findViewById(R.id.button_save_personal_app);
        savePersonalAppButton.setVisibility(0);
        savePersonalAppButton.setOnClickListener(saveButtonListener);
        Button saveSystemAppButton = (Button) findViewById(R.id.button_save_system_app);
        saveSystemAppButton.setVisibility(0);
        saveSystemAppButton.setOnClickListener(saveButtonListener);
        OnClickListener selectButtonListener = new C11876();
        this.mSelectAllPersonalAppButton = (Button) findViewById(R.id.button_select_all_personal_app);
        this.mSelectAllPersonalAppButton.setVisibility(0);
        this.mSelectAllPersonalAppButton.setOnClickListener(selectButtonListener);
        updateSelectAllButtonText(TAB_TAG_PERSONAL_APP);
        this.mSelectAllSystemAppButton = (Button) findViewById(R.id.button_select_all_system_app);
        this.mSelectAllSystemAppButton.setVisibility(0);
        this.mSelectAllSystemAppButton.setOnClickListener(selectButtonListener);
        updateSelectAllButtonText(TAB_TAG_SYSTEM_APP);
    }

    private void toggleAllListItemSelection() {
        int i = 0;
        boolean isAllSelected;
        boolean z;
        if (isPersonalAppTabSelected()) {
            if (this.mPersonalAppSelectedCount == this.mPersonalAppList.size()) {
                isAllSelected = true;
            } else {
                isAllSelected = false;
            }
            for (Map<String, Object> personalAppItem : this.mPersonalAppList) {
                personalAppItem.remove(VIEW_ITEM_CHECKBOX);
                String str = VIEW_ITEM_CHECKBOX;
                if (isAllSelected) {
                    z = false;
                } else {
                    z = true;
                }
                personalAppItem.put(str, Boolean.valueOf(z));
            }
            if (!isAllSelected) {
                i = this.mPersonalAppList.size();
            }
            this.mPersonalAppSelectedCount = i;
            this.mPersonalAppAdapter.notifyDataSetChanged();
            updateSelectAllButtonText(TAB_TAG_PERSONAL_APP);
            return;
        }
        if (this.mSystemAppSelectedCount == this.mSystemAppList.size()) {
            isAllSelected = true;
        } else {
            isAllSelected = false;
        }
        for (Map<String, Object> systemAppItem : this.mSystemAppList) {
            systemAppItem.remove(VIEW_ITEM_CHECKBOX);
            str = VIEW_ITEM_CHECKBOX;
            if (isAllSelected) {
                z = false;
            } else {
                z = true;
            }
            systemAppItem.put(str, Boolean.valueOf(z));
        }
        if (!isAllSelected) {
            i = this.mSystemAppList.size();
        }
        this.mSystemAppSelectedCount = i;
        this.mSystemAppAdapter.notifyDataSetChanged();
        updateSelectAllButtonText(TAB_TAG_SYSTEM_APP);
    }

    private void updateSelectAllButtonText(String tabTag) {
        Button selectAllButton;
        boolean isAllSelected = true;
        if (tabTag.equals(TAB_TAG_PERSONAL_APP)) {
            if (this.mPersonalAppSelectedCount != this.mPersonalAppList.size()) {
                isAllSelected = false;
            }
            selectAllButton = this.mSelectAllPersonalAppButton;
        } else {
            if (this.mSystemAppSelectedCount != this.mSystemAppList.size()) {
                isAllSelected = false;
            }
            selectAllButton = this.mSelectAllSystemAppButton;
        }
        if (isAllSelected) {
            selectAllButton.setText(R.string.button_deselect_all);
        } else {
            selectAllButton.setText(R.string.button_select_all);
        }
    }
}
