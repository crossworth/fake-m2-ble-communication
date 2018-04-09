package com.zhuoyou.plugin.bluetooth.attach;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.product.ProductManager;
import com.zhuoyou.plugin.running.RunningApp;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class PluginManager {
    private static final String PLUG_PACKAGENAME_PREX = "com.zhuoyou.plugin.";
    private static PluginManager mPluginManager = null;
    public boolean hasNotication;
    private Context mCtx = RunningApp.getInstance().getApplicationContext();
    private List<String> mInstalledPlugs = null;
    private PackageManager mPackageManager = this.mCtx.getPackageManager();
    private List<PlugBean> mPlugBeans;
    private List<PreInstallBean> mPreInstallBeans = null;
    private List<PlugBean> mSystemPlugBeans = null;

    public static PluginManager getInstance() {
        if (mPluginManager != null) {
            return mPluginManager;
        }
        mPluginManager = new PluginManager();
        return mPluginManager;
    }

    public PluginManager() {
        getInstalledPlugs();
        createSystemPlug();
        loadPreInstallPlug();
    }

    public void getInstalledPlugs() {
        if (this.mInstalledPlugs != null && this.mInstalledPlugs.size() > 0) {
            this.mInstalledPlugs.clear();
            this.mInstalledPlugs = null;
        }
        this.mInstalledPlugs = new ArrayList();
        for (PackageInfo pkg : this.mPackageManager.getInstalledPackages(8192)) {
            if (pkg.packageName.startsWith(PLUG_PACKAGENAME_PREX) && !pkg.packageName.equals(this.mCtx.getPackageName())) {
                this.mInstalledPlugs.add(pkg.packageName);
            }
        }
    }

    protected void createSystemPlug() {
        if (this.mSystemPlugBeans != null && this.mSystemPlugBeans.size() > 0) {
            this.mSystemPlugBeans.clear();
            this.mSystemPlugBeans = null;
        }
        this.mSystemPlugBeans = new ArrayList();
        PlugBean bean = new PlugBean();
        bean.isInstalled = false;
        bean.isPreInstall = true;
        bean.isSystem = true;
        bean.mLogoResId = R.drawable.plug_system_notify;
        bean.mTitle = this.mCtx.getString(R.string.notification_preference_title);
        bean.mMethod_Entry = "com.tyd.bt.NoticationActivity";
        this.mSystemPlugBeans.add(bean);
        bean = new PlugBean();
        bean.isInstalled = false;
        bean.isPreInstall = true;
        bean.isSystem = true;
        bean.mLogoResId = R.drawable.plug_system_call;
        bean.mTitle = this.mCtx.getString(R.string.phone_preference_category);
        bean.mMethod_Entry = "com.tyd.bt.CallServiceActivity";
        this.mSystemPlugBeans.add(bean);
        bean = new PlugBean();
        bean.isInstalled = false;
        bean.isPreInstall = true;
        bean.isSystem = true;
        bean.mLogoResId = R.drawable.plug_system_msg;
        bean.mTitle = this.mCtx.getString(R.string.sms_preference_title);
        bean.mMethod_Entry = "com.tyd.bt.SmsServiceActivity";
        this.mSystemPlugBeans.add(bean);
        bean = new PlugBean();
        bean.isInstalled = false;
        bean.isPreInstall = true;
        bean.isSystem = true;
        bean.mLogoResId = R.drawable.plug_system_remind_switch;
        bean.mTitle = this.mCtx.getString(R.string.remind_setting_title);
        bean.mMethod_Entry = "com.tyd.bt.RemindSettingActivity";
        this.mSystemPlugBeans.add(bean);
        bean = new PlugBean();
        bean.isInstalled = false;
        bean.isPreInstall = true;
        bean.isSystem = true;
        bean.mLogoResId = R.drawable.sport_target;
        bean.mTitle = this.mCtx.getString(R.string.sport_target_reminder);
        bean.mMethod_Entry = "com.tyd.bt.SportTargetReminderActivity";
        this.mSystemPlugBeans.add(bean);
        bean = new PlugBean();
        bean.isInstalled = false;
        bean.isPreInstall = true;
        bean.isSystem = true;
        bean.mLogoResId = R.drawable.disturbance_mode;
        bean.mTitle = this.mCtx.getString(R.string.disturbance_mode);
        bean.mMethod_Entry = "com.tyd.bt.AaronLiModelActivity";
        this.mSystemPlugBeans.add(bean);
        bean = new PlugBean();
        bean.isInstalled = false;
        bean.isPreInstall = true;
        bean.isSystem = true;
        bean.mLogoResId = R.drawable.sedentary_remind;
        bean.mTitle = this.mCtx.getString(R.string.sedentary_remind);
        bean.mMethod_Entry = "com.tyd.bt.SedentaryReminderM2Activity";
        this.mSystemPlugBeans.add(bean);
        bean = new PlugBean();
        bean.isInstalled = false;
        bean.isPreInstall = true;
        bean.isSystem = true;
        bean.mLogoResId = R.drawable.m2_ui_display;
        bean.mTitle = this.mCtx.getString(R.string.m2_ui_display);
        bean.mMethod_Entry = "com.tyd.bt.M2UiDisplaySetting";
        this.mSystemPlugBeans.add(bean);
        bean = new PlugBean();
        bean.isInstalled = false;
        bean.isPreInstall = true;
        bean.isSystem = true;
        bean.mLogoResId = R.drawable.m2_wrist_light_screen;
        bean.mTitle = this.mCtx.getString(R.string.wrist_bright_screen);
        bean.mMethod_Entry = "com.tyd.bt.WristBrightScreenActivity";
        this.mSystemPlugBeans.add(bean);
    }

    private void loadPreInstallPlug() {
        InputStream is = null;
        String loc = Locale.getDefault().getCountry();
        try {
            is = this.mCtx.getAssets().open("preinstall/xml/preinstall_" + loc + ".xml");
            Log.i("gchk", "open preinstall file successed ,file =preinstall/xml/preinstall_" + loc + ".xml");
        } catch (IOException e) {
            Log.i("gchk", "open preinstall file failed ,file =preinstall/xml/preinstall_" + loc + ".xml");
            e.printStackTrace();
        }
        if (is == null) {
            try {
                is = this.mCtx.getAssets().open("preinstall/xml/preinstall_US.xml");
                Log.i("gchk", "open assert profile LAN = US force");
            } catch (IOException e2) {
                Log.e("gchk", "open assert profile LAN = US force ERROR " + e2.getMessage());
                e2.printStackTrace();
            }
        }
        if (is == null) {
            Log.e("gchk", "didn't find en profile , can not happen!");
            return;
        }
        if (this.mPreInstallBeans != null && this.mPreInstallBeans.size() > 0) {
            this.mPreInstallBeans.clear();
            this.mPreInstallBeans = null;
        }
        PreInstallHandler handler = new PreInstallHandler();
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(is, handler);
            is.close();
            this.mPreInstallBeans = handler.getRoot();
        } catch (ParserConfigurationException e3) {
            e3.printStackTrace();
        } catch (SAXException e4) {
            e4.printStackTrace();
        } catch (IOException e22) {
            e22.printStackTrace();
        }
        if (this.mPreInstallBeans == null || this.mPreInstallBeans.size() == 0) {
            Log.e("gchk", "parser preinstall xml failed or current product didn't contain preinstall information , can not happen!");
        }
    }

    public void processPlugList(String curr_nickname) {
        int i;
        List<PlugBean> temps;
        PlugBean bean;
        if (this.mPlugBeans != null && this.mPlugBeans.size() > 0) {
            this.mPlugBeans.clear();
            this.mPlugBeans = null;
        }
        this.mPlugBeans = new ArrayList();
        this.hasNotication = false;
        for (int k = 0; k < this.mSystemPlugBeans.size(); k++) {
            if (ProductManager.getInstance().isSupportPlugin(curr_nickname, ((PlugBean) this.mSystemPlugBeans.get(k)).mMethod_Entry)) {
                this.mPlugBeans.add(this.mSystemPlugBeans.get(k));
                if (k == 0) {
                    this.hasNotication = true;
                }
            }
        }
        if (this.mPreInstallBeans != null && this.mPreInstallBeans.size() > 0) {
            for (i = 0; i < this.mPreInstallBeans.size(); i++) {
                PreInstallBean preinstallbean = (PreInstallBean) this.mPreInstallBeans.get(i);
                if (preinstallbean.getName().equals(curr_nickname)) {
                    List<String> packagenames = preinstallbean.getPlugPackageNames();
                    List<String> names = preinstallbean.getPlugNames();
                    if (packagenames != null && packagenames.size() > 0) {
                        for (int j = 0; j < packagenames.size(); j++) {
                            String j_packagename = (String) packagenames.get(j);
                            String j_name = (String) names.get(j);
                            PlugBean plugbean = new PlugBean();
                            if (this.mInstalledPlugs.contains(j_packagename)) {
                                plugbean.isInstalled = true;
                            } else {
                                plugbean.isInstalled = false;
                            }
                            plugbean.isPreInstall = true;
                            plugbean.isSystem = false;
                            plugbean.mPackageName = j_packagename;
                            plugbean.mTitle = j_name;
                            String imageName = j_packagename.replaceAll("\\.", "_");
                            Resources resource = this.mCtx.getResources();
                            int resID = resource.getIdentifier(imageName, "drawable", this.mCtx.getPackageName());
                            if (resID == 0) {
                                resID = R.drawable.com_zhuoyou_plugin_antilost;
                            }
                            plugbean.mLogoBitmap = resource.getDrawable(resID);
                            this.mPlugBeans.add(plugbean);
                        }
                    }
                }
            }
        }
        if (this.mInstalledPlugs != null && this.mInstalledPlugs.size() > 0) {
            temps = new ArrayList();
            for (i = 0; i < this.mInstalledPlugs.size(); i++) {
                String install_packageName = (String) this.mInstalledPlugs.get(i);
                boolean need_add = true;
                for (PlugBean bean2 : this.mPlugBeans) {
                    if (bean2.mPackageName.equals(install_packageName)) {
                        need_add = false;
                        break;
                    }
                }
                if (need_add) {
                    PlugBean plugin = new PlugBean();
                    plugin.mPackageName = install_packageName;
                    plugin.isInstalled = true;
                    plugin.isSystem = false;
                    plugin.isPreInstall = false;
                    temps.add(plugin);
                }
            }
            this.mPlugBeans.addAll(this.mPlugBeans.size(), temps);
        }
        temps = new ArrayList();
        for (i = 0; i < this.mPlugBeans.size(); i++) {
            bean2 = (PlugBean) this.mPlugBeans.get(i);
            if (bean2.isSystem) {
                temps.add(bean2);
            } else {
                if (ProductManager.getInstance().isSupportPlugin(curr_nickname, bean2.mPackageName)) {
                    temps.add(bean2);
                }
            }
        }
        if (this.mPlugBeans != null && this.mPlugBeans.size() > 0) {
            this.mPlugBeans.clear();
            this.mPlugBeans = null;
        }
        this.mPlugBeans = new ArrayList();
        this.mPlugBeans.addAll(temps);
        for (i = 0; i < this.mPlugBeans.size(); i++) {
            bean2 = (PlugBean) this.mPlugBeans.get(i);
            if (bean2.isInstalled) {
                registerPlug(bean2);
            }
        }
    }

    private void registerPlug(PlugBean bean) {
        try {
            Context targetContext = this.mCtx.createPackageContext(bean.mPackageName, 3);
            bean.mCtx = targetContext;
            Log.e("gchk", bean.mPackageName + " CONTEXT = " + bean.mCtx.toString());
            try {
                Class<?> c = targetContext.getClassLoader().loadClass(bean.mPackageName + "." + "PlugMain");
                bean.mClasses = c;
                try {
                    Constructor<?> constructor = c.getConstructor(new Class[]{Context.class, Context.class});
                    bean.mConstructor = constructor;
                    try {
                        bean.mInstance = constructor.newInstance(new Object[]{targetContext, this.mCtx});
                        Object obj = PlugUtils.invoke(bean, "getIcon");
                        if (obj != null) {
                            bean.mLogoBitmap = (Drawable) obj;
                        }
                        obj = PlugUtils.invoke(bean, "getName");
                        if (obj != null) {
                            bean.mTitle = (String) obj;
                        }
                        obj = PlugUtils.invoke(bean, "getSupportCmd");
                        if (obj != null) {
                            bean.mSupportCmd = (String) obj;
                        }
                        obj = PlugUtils.invoke(bean, "getEntryMethodName");
                        if (obj != null) {
                            bean.mMethod_Entry = (String) obj;
                        }
                        obj = PlugUtils.invoke(bean, "getWorkMethodName");
                        if (obj != null) {
                            bean.mWorkMethod = (Map) obj;
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        Log.i("gchk", "IllegalArgumentException" + e.getMessage());
                    } catch (InstantiationException e2) {
                        e2.printStackTrace();
                        Log.i("gchk", "InstantiationException" + e2.getMessage());
                    } catch (IllegalAccessException e3) {
                        e3.printStackTrace();
                        Log.i("gchk", "IllegalAccessException" + e3.getMessage());
                    } catch (InvocationTargetException e4) {
                        e4.printStackTrace();
                        Log.i("gchk", "InvocationTargetException" + e4.getMessage());
                    }
                } catch (NoSuchMethodException e5) {
                    e5.printStackTrace();
                    Log.i("gchk", "NoSuchMethodException" + e5.getMessage());
                }
            } catch (ClassNotFoundException e6) {
                e6.printStackTrace();
                Log.i("gchk", "ClassNotFoundException" + e6.getMessage());
            }
        } catch (NameNotFoundException e7) {
            e7.printStackTrace();
            Log.i("gchk", "NameNotFoundException" + e7.getMessage());
        }
    }

    public void updatePlugList(String packagename, boolean add) {
        if (packagename.startsWith(PLUG_PACKAGENAME_PREX)) {
            getInstalledPlugs();
            if (this.mPlugBeans != null && this.mPlugBeans.size() > 0) {
                int i;
                PlugBean bean;
                if (add) {
                    for (i = 0; i < this.mPlugBeans.size(); i++) {
                        bean = (PlugBean) this.mPlugBeans.get(i);
                        if (bean.mPackageName.equals(packagename)) {
                            bean.isInstalled = true;
                            registerPlug(bean);
                            return;
                        }
                    }
                    if (!false) {
                        PlugBean t = new PlugBean();
                        t.isPreInstall = false;
                        t.isInstalled = true;
                        t.isSystem = false;
                        t.mPackageName = packagename;
                        registerPlug(t);
                        this.mPlugBeans.add(this.mPlugBeans.size() - 1, t);
                        return;
                    }
                    return;
                }
                i = 0;
                while (i < this.mPlugBeans.size()) {
                    bean = (PlugBean) this.mPlugBeans.get(i);
                    if (!bean.mPackageName.equals(packagename)) {
                        i++;
                    } else if (bean.isPreInstall) {
                        bean.isInstalled = false;
                        String imageName = bean.mPackageName.replaceAll("\\.", "_");
                        Resources resource = this.mCtx.getResources();
                        int resID = resource.getIdentifier(imageName, "drawable", this.mCtx.getPackageName());
                        if (resID == 0) {
                            resID = R.drawable.com_zhuoyou_plugin_antilost;
                        }
                        bean.mLogoBitmap = resource.getDrawable(resID);
                        return;
                    } else {
                        this.mPlugBeans.remove(i);
                        return;
                    }
                }
                return;
            }
            return;
        }
        Log.i("gchk", "not plugin , don't care~haha");
    }

    public List<PlugBean> getPlugBeans() {
        return this.mPlugBeans;
    }

    public static void release() {
        mPluginManager = null;
    }
}
