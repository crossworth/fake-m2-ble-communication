package com.zhuoyou.plugin.bluetooth.attach;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.data.PreferenceData;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.running.Tools;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BTPluginActivity extends Activity {
    private static final String PLUG_PACKAGENAME_PREX = "com.zhuoyou.plugin.";
    public static boolean isEditMode;
    public static BTPluginActivity this_;
    private int connectState;
    private String filePath = null;
    private Button mEdit;
    private boolean mEnable;
    private List<String> mFileNames;
    private List<String> mInstalledPlugs = new ArrayList();
    private String mNickName = null;
    private ProgressDialog mPb = null;
    private PlugInAdapter mPlugInAdapter;
    private List<PlugBean> mPlugLists = new ArrayList();
    private refreshBroadcast mRefreshBroadcast = null;
    private String mRemoteName = "";

    class C11671 implements OnClickListener {
        C11671() {
        }

        public void onClick(View v) {
            BTPluginActivity.this.finish();
        }
    }

    class C11682 implements OnClickListener {
        C11682() {
        }

        public void onClick(View v) {
            if (BTPluginActivity.isEditMode) {
                BTPluginActivity.isEditMode = false;
                BTPluginActivity.this.mPlugInAdapter.notifyDataSetChanged();
                BTPluginActivity.this.mEdit.setText(R.string.bt_edit);
            } else if (BTPluginActivity.this.mInstalledPlugs == null || BTPluginActivity.this.mInstalledPlugs.size() <= 0) {
                Toast.makeText(BTPluginActivity.this_, R.string.not_uninstall, 0).show();
            } else {
                BTPluginActivity.isEditMode = true;
                BTPluginActivity.this.mPlugInAdapter.notifyDataSetChanged();
                BTPluginActivity.this.mEdit.setText(R.string.it_is_ok);
            }
        }
    }

    class C11693 implements DialogInterface.OnClickListener {
        C11693() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C11704 implements DialogInterface.OnClickListener {
        C11704() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            BTPluginActivity.this.startActivity(NoticationActivity.ACCESSIBILITY_INTENT);
        }
    }

    private class InstallAsynTask extends AsyncTask<String, String, Integer> {
        private String path;

        private InstallAsynTask() {
            this.path = "";
        }

        protected void onPreExecute() {
            if (BTPluginActivity.this.mPb == null) {
                BTPluginActivity.this.mPb = new ProgressDialog(BTPluginActivity.this_);
            }
            BTPluginActivity.this.mPb.setCancelable(false);
            BTPluginActivity.this.mPb.setMessage(BTPluginActivity.this.getString(R.string.progress_plugin_wait));
            BTPluginActivity.this.mPb.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(String... arg0) {
            String packageName = arg0[0];
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return Integer.valueOf(-5);
            }
            try {
                InputStream stream = BTPluginActivity.this.getAssets().open("preinstall/plug/" + packageName + ".plg");
                if (stream == null) {
                    return Integer.valueOf(-1);
                }
                String sdcard = Environment.getExternalStorageDirectory().getPath();
                if (Environment.getExternalStorageState().equals("mounted")) {
                    sdcard = Environment.getExternalStorageDirectory().getPath();
                } else {
                    sdcard = Environment.getDataDirectory().getPath();
                }
                if (!PlugUtils.canCopy(sdcard)) {
                    return Integer.valueOf(-2);
                }
                this.path = sdcard + "/" + packageName + ".apk";
                File file = new File(this.path);
                try {
                    file.createNewFile();
                    if (PlugUtils.writeStreamToFile(stream, file)) {
                        return Integer.valueOf(0);
                    }
                    return Integer.valueOf(-4);
                } catch (IOException e) {
                    e.printStackTrace();
                    return Integer.valueOf(-3);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                return Integer.valueOf(-1);
            }
        }

        protected void onPostExecute(Integer result) {
            if (BTPluginActivity.this.mPb.isShowing()) {
                BTPluginActivity.this.mPb.dismiss();
            }
            switch (result.intValue()) {
                case -5:
                    Toast.makeText(BTPluginActivity.this_, R.string.toast_close_usb_mass_mode, 0).show();
                    break;
                case -4:
                    Toast.makeText(BTPluginActivity.this_, R.string.toast_cannot_copy_file, 0).show();
                    break;
                case -3:
                    Toast.makeText(BTPluginActivity.this_, R.string.toast_cannot_create_new_file, 0).show();
                    break;
                case -2:
                    Toast.makeText(BTPluginActivity.this_, R.string.toast_not_enough_space, 0).show();
                    break;
                case -1:
                    Toast.makeText(BTPluginActivity.this_, R.string.toast_cannot_find_plugin, 0).show();
                    break;
                case 0:
                    Uri uri = Uri.fromFile(new File(this.path));
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    BTPluginActivity.this.startActivity(intent);
                    break;
            }
            super.onPostExecute(result);
        }
    }

    private class refreshBroadcast extends BroadcastReceiver {
        private refreshBroadcast() {
        }

        public void onReceive(Context context, Intent intent) {
            BTPluginActivity.this.buildData();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_plugin);
        this_ = this;
        Intent intent = getIntent();
        if (intent != null) {
            this.mNickName = intent.getStringExtra("nick_name");
            this.mRemoteName = intent.getStringExtra("remote_name");
            this.mEnable = intent.getBooleanExtra("enable_state", true);
            this.connectState = intent.getIntExtra("connect_state", 0);
        }
        initViews();
        if (this.mRefreshBroadcast == null) {
            this.mRefreshBroadcast = new refreshBroadcast();
            IntentFilter intentfilter = new IntentFilter();
            intentfilter.addAction("com.tyd.plugin.PLUGIN_LIST_REFRESH");
            registerReceiver(this.mRefreshBroadcast, intentfilter);
        }
        if (PreferenceData.isNotificationServiceEnable() && !BluetoothService.isNotificationServiceActived() && PluginManager.getInstance().hasNotication) {
            showAccessibilityPrompt();
        }
    }

    public void onResume() {
        super.onResume();
        isEditMode = false;
        if (!isEditMode) {
            this.mEdit.setText(R.string.bt_edit);
        }
        buildData();
        checkPlug();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mRefreshBroadcast != null) {
            unregisterReceiver(this.mRefreshBroadcast);
        }
    }

    private void initViews() {
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C11671());
        ((TextView) findViewById(R.id.title_plug_name)).setText(this.mRemoteName);
        this.mEdit = (Button) findViewById(R.id.edit);
        this.mEdit.setOnClickListener(new C11682());
        ListView gv = (ListView) findViewById(R.id.plug_gv);
        this.mPlugInAdapter = new PlugInAdapter(this, this.mPlugLists);
        gv.setAdapter(this.mPlugInAdapter);
    }

    private void checkPlug() {
        getFilesList();
        if (this.mFileNames != null && this.mFileNames.size() > 0) {
            List<String> temp = new ArrayList();
            temp.addAll(this.mFileNames);
            for (int i = 0; i < this.mFileNames.size(); i++) {
                String fileName = (String) this.mFileNames.get(i);
                if (this.mInstalledPlugs != null && this.mInstalledPlugs.size() > 0) {
                    for (int j = 0; j < this.mInstalledPlugs.size(); j++) {
                        if (((String) this.mInstalledPlugs.get(j)).endsWith(fileName)) {
                            temp.remove(fileName);
                        }
                    }
                }
            }
            if (temp != null && temp.size() > 0) {
                for (int n = 0; n < temp.size(); n++) {
                    clear(new File(this.filePath + "/" + ((String) temp.get(n)) + "/data"));
                }
            }
        }
    }

    private void getInstalledPlugs() {
        this.mInstalledPlugs.clear();
        for (PackageInfo pkg : getPackageManager().getInstalledPackages(8192)) {
            if (pkg.packageName.startsWith(PLUG_PACKAGENAME_PREX) && !pkg.packageName.equals(getPackageName())) {
                this.mInstalledPlugs.add(pkg.packageName);
            }
        }
    }

    private void getFilesList() {
        this.mFileNames = new ArrayList();
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.filePath = Environment.getExternalStorageDirectory().toString() + "/Running";
            File file = new File(this.filePath);
            if (file.exists()) {
                for (File f : file.listFiles()) {
                    if (f.isDirectory()) {
                        this.mFileNames.add(f.getName());
                    }
                }
            }
        }
    }

    private void clear(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File clear : files) {
                    clear(clear);
                }
            }
            file.delete();
        }
    }

    private void buildData() {
        Log.i("chenxin", "buildData in");
        getInstalledPlugs();
        PluginManager.getInstance().createSystemPlug();
        PluginManager.getInstance().processPlugList(this.mRemoteName);
        this.mPlugLists = PluginManager.getInstance().getPlugBeans();
        if (this.mPlugInAdapter == null) {
            this.mPlugInAdapter = new PlugInAdapter(this, this.mPlugLists);
        }
        this.mPlugInAdapter.notify(this.mPlugLists);
    }

    public static void onClick(int index) {
        this_.__onClick(index);
    }

    public static void onUninstallClick(int index) {
        this_.uninstall(index);
    }

    public void __onClick(int index) {
        if (!isEditMode) {
            PlugBean bean = (PlugBean) this.mPlugLists.get(index);
            if (bean.isSystem) {
                if (!this.mEnable) {
                    Toast.makeText(this_, R.string.device_not_connected, 1).show();
                } else if (!this.mRemoteName.equals("M2")) {
                    intent = new Intent(bean.mMethod_Entry);
                    intent.putExtra("product_name", this.mNickName);
                    startActivity(intent);
                } else if (index < 3) {
                    intent = new Intent(bean.mMethod_Entry);
                    intent.putExtra("product_name", this.mNickName);
                    startActivity(intent);
                } else if (this.connectState == 3) {
                    intent = new Intent(bean.mMethod_Entry);
                    intent.putExtra("product_name", this.mNickName);
                    startActivity(intent);
                } else {
                    Tools.makeToast("请连接设备后使用该功能");
                }
            } else if (bean.isInstalled) {
                if (!this.mEnable) {
                    Toast.makeText(this_, R.string.device_not_connected, 1).show();
                } else if (bean.mMethod_Entry != null && bean.mMethod_Entry.length() != 0) {
                    startActivity(new Intent(bean.mMethod_Entry));
                }
            } else if (bean.isPreInstall) {
                new InstallAsynTask().execute(new String[]{bean.mPackageName});
            }
        }
    }

    public void uninstall(int index) {
        if (!((PlugBean) this.mPlugLists.get(index)).isSystem && ((PlugBean) this.mPlugLists.get(index)).isInstalled) {
            PlugUtils.uninstallUseIntent(((PlugBean) this.mPlugLists.get(index)).mPackageName, this_);
        }
    }

    private void showAccessibilityPrompt() {
        Builder builder = new Builder(this);
        builder.setTitle((int) R.string.accessibility_prompt_title);
        builder.setMessage((int) R.string.accessibility_prompt_content);
        builder.setNegativeButton((int) R.string.cancle, new C11693());
        builder.setPositiveButton((int) R.string.ok, new C11704());
        builder.create().show();
    }
}
