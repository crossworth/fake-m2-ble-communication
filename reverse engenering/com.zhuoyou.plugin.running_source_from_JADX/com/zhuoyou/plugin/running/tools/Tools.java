package com.zhuoyou.plugin.running.tools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtManagerService;
import com.droi.greendao.bean.SleepBean;
import com.droi.greendao.bean.SportBean;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.tencent.connect.UserInfo;
import com.tencent.mm.sdk.modelbiz.JumpToBizProfile.Req;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tyd.aidlservice.internal.Constants;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.MainActivity;
import com.zhuoyou.plugin.running.activity.SplashActivity;
import com.zhuoyou.plugin.running.activity.SportsReportActivity;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.bean.EventLogout;
import com.zhuoyou.plugin.running.database.HeartHelper;
import com.zhuoyou.plugin.running.database.SleepHelper;
import com.zhuoyou.plugin.running.database.SportHelper;
import com.zhuoyou.plugin.running.database.WeightHelper;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog.OnClickListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class Tools {
    public static final String BIRTH_FORMAT = "yyyy-MM-dd";
    public static final String CACHE_PATH = (TheApp.getContext().getCacheDir().getPath() + File.separator);
    public static final String DEFAULT_FORMAT_DATE = "yyyyMMdd";
    public static final String DEFAULT_FORMAT_TIME = "yyyyMMddHHmmss";
    public static final int DEF_BACK_ID = 2131623952;
    public static final int DEF_PHOTO_ID = 2130837642;
    public static final String DOWNLOAD_SITE = "http://market.droi.com/appDetail-1087848.html";
    public static final String FILE_PATH = (TheApp.getContext().getFilesDir().getPath() + File.separator);
    public static final int QQ_FAIL_CANCEL = -3;
    public static final int QQ_FAIL_DEFAULT = -1;
    public static final int QQ_FAIL_ERROR = -2;
    public static final int QQ_FAIL_GET_USER_INFO = -4;
    public static final int QQ_PASSWORD_CHANGE = -73;
    public static final int QQ_SUCCESSFULL = 0;
    private static final String TEMP_PATH = (FileUtils.getSDRoot() + Constants.DROI_TAG + File.separator + "DroiHealth" + File.separator + "temp" + File.separator);
    private static DisplayImageOptions defImgOptions;
    private static float scale = getScale();

    public interface QQLoginCallback {
        void callback(boolean z, int i);
    }

    public static class MyIUListener implements IUiListener {
        Activity activity;
        QQLoginCallback callback;
        Tencent mTencent;

        public MyIUListener(QQLoginCallback callback, Tencent mTencent, Activity activity) {
            this.callback = callback;
            this.mTencent = mTencent;
            this.activity = activity;
        }

        public void onComplete(Object o) {
            Log.i("chenxinX", "onComplete:" + o);
            if (o instanceof JSONObject) {
                JSONObject loginObject = (JSONObject) o;
                int code = Tools.dealQQjson((JSONObject) o);
                if (code == 0) {
                    final String userId = DroiUser.getCurrentUser().getUserId();
                    SPUtils.setQQOpenId(loginObject.optString("openid"), userId);
                    SPUtils.setQQAccessToken(loginObject.optString("access_token"), userId);
                    SPUtils.setQQExpiresIn(loginObject.optString("expires_in"), userId);
                    SPUtils.setQQExpiresDate(System.currentTimeMillis(), userId);
                    this.mTencent.setOpenId(loginObject.optString("openid"));
                    this.mTencent.setAccessToken(loginObject.optString("access_token"), (SPUtils.getQQExpiresIn(userId) - ((System.currentTimeMillis() - SPUtils.getQQExpiresDate(userId)) / 1000)) + "");
                    new UserInfo(this.activity, this.mTencent.getQQToken()).getUserInfo(new IUiListener() {
                        public void onComplete(Object o) {
                            Log.i("chenxinX", "get user info:" + o);
                            if (o instanceof JSONObject) {
                                JSONObject userJson = (JSONObject) o;
                                int code = Tools.dealQQjson(userJson);
                                if (code == 0) {
                                    String userName = "unKnow";
                                    try {
                                        userName = userJson.getString("nickname");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    SPUtils.setQQUserName(userName, userId);
                                    MyIUListener.this.callback.callback(true, 0);
                                    return;
                                }
                                MyIUListener.this.callback.callback(false, code);
                                return;
                            }
                            MyIUListener.this.callback.callback(false, -4);
                        }

                        public void onError(UiError uiError) {
                            MyIUListener.this.callback.callback(false, -4);
                        }

                        public void onCancel() {
                            MyIUListener.this.callback.callback(false, -4);
                        }
                    });
                    return;
                }
                this.callback.callback(false, code);
                return;
            }
            this.callback.callback(false, -1);
        }

        public void onError(UiError uiError) {
            Log.i("chenxinX", "onError:" + uiError.errorDetail);
            this.callback.callback(false, -2);
        }

        public void onCancel() {
            Log.i("chenxinX", "onCancel:");
            this.callback.callback(false, -3);
        }
    }

    public static int dip2px(float dipValue) {
        return (int) ((scale * dipValue) + 0.5f);
    }

    public static float px2dip(float pxValue) {
        return (pxValue / scale) + 0.5f;
    }

    public static float getScale() {
        return TheApp.getInstance().getResources().getDisplayMetrics().density;
    }

    public static String getTempPath() {
        File file = new File(TEMP_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return TEMP_PATH;
    }

    public static DisplayImageOptions getImageOptions() {
        return new Builder().cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).build();
    }

    public static DisplayImageOptions getImageOptionsAnim() {
        return new Builder().cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(500)).build();
    }

    public static DisplayImageOptions getDefImageOptions() {
        if (defImgOptions == null) {
            synchronized (Tools.class) {
                if (defImgOptions == null) {
                    defImgOptions = new Builder().showImageForEmptyUri((int) C1680R.drawable.img_loading_failed).showImageOnLoading((int) C1680R.drawable.img_loading).showImageOnFail((int) C1680R.drawable.img_loading_failed).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).build();
                }
            }
        }
        return defImgOptions;
    }

    public static String UriToString(Uri uri) {
        return uri == null ? "" : uri.toString();
    }

    public static void displayImage(ImageView imageView, Uri uri, boolean showLoadding) {
        displayImage(imageView, UriToString(uri), showLoadding);
    }

    public static void displayImage(ImageView imageView, String uri, boolean showLoadding) {
        if (showLoadding) {
            ImageLoader.getInstance().displayImage(uri, imageView, getDefImageOptions());
        } else {
            ImageLoader.getInstance().displayImage(uri, imageView, getImageOptions());
        }
    }

    public static void displayImage(ImageView imageView, Uri uri) {
        displayImage(imageView, UriToString(uri), getDefImageOptions());
    }

    public static void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, getDefImageOptions());
    }

    public static void displayImage(ImageView imageView, Uri uri, DisplayImageOptions options) {
        displayImage(imageView, UriToString(uri), options);
    }

    public static void displayImage(ImageView imageView, String uri, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    public static void displayImage(final ImageView imageView, final DroiFile img, final int defRes) {
        if (img != null) {
            String uri = SPUtils.getFileUri(img.getObjectId());
            if (TextUtils.isEmpty(uri)) {
                img.getUriInBackground(new DroiCallback<Uri>() {
                    public void result(Uri uri, DroiError droiError) {
                        Log.i("zhuqichao", "uri=" + uri);
                        if (uri != null) {
                            SPUtils.setFileUri(img.getObjectId(), uri);
                            Tools.displayImage(imageView, uri, false);
                            return;
                        }
                        Log.i("zhuqichao", "error=" + droiError + ", code=" + droiError.getCode());
                        imageView.setImageResource(defRes);
                    }
                });
                return;
            } else {
                displayImage(imageView, uri, false);
                return;
            }
        }
        imageView.setImageResource(defRes);
    }

    public static void displayFace(ImageView imageView, DroiFile img) {
        displayImage(imageView, img, (int) C1680R.drawable.default_photo);
    }

    public static void displayBack(ImageView imageView, DroiFile img) {
        displayImage(imageView, img, (int) C1680R.color.background_color_main);
    }

    public static void makeToast(String msg) {
        makeToast(TheApp.getContext(), msg);
    }

    public static void makeToast(int resid) {
        makeToast(TheApp.getContext(), resid);
    }

    public static void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, 0).show();
    }

    public static void makeToast(Context context, int resid) {
        Toast.makeText(context, resid, 0).show();
    }

    public static String formatTime(long time) {
        return DateFormat.format(DEFAULT_FORMAT_TIME, time).toString();
    }

    public static String formatTime(long time, String format) {
        return DateFormat.format(format, time).toString();
    }

    public static String formatTime(long time, String format, int timeZone) {
        SimpleDateFormat mFormat = new SimpleDateFormat(format);
        mFormat.setTimeZone(TimeZone.getTimeZone("GMT+" + timeZone));
        return mFormat.format(Long.valueOf(time));
    }

    public static ProgressDialog getProgressDialog(Context context) {
        return new ProgressDialog(context);
    }

    public static void showProgressDialog(ProgressDialog dialog, String msg) {
        showProgressDialog(dialog, msg, false);
    }

    public static void showProgressDialog(ProgressDialog dialog, String msg, boolean cancelable) {
        if (dialog != null) {
            dialog.setMessage(msg);
            dialog.setCancelable(cancelable);
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    public static void hideProgressDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static String getToday() {
        return formatTime(System.currentTimeMillis(), DEFAULT_FORMAT_DATE) + "000000";
    }

    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -1);
        return formatTime(cal.getTime().getTime(), DEFAULT_FORMAT_DATE) + "000000";
    }

    public static String getTodayTime() {
        return formatTime(System.currentTimeMillis(), DEFAULT_FORMAT_TIME);
    }

    public static String getCurrentTime() {
        return formatTime(System.currentTimeMillis(), DEFAULT_FORMAT_TIME);
    }

    public static String getCurrentTimeByZeroZone() {
        return formatTime(System.currentTimeMillis(), DEFAULT_FORMAT_TIME, 0);
    }

    public static Calendar parseDefDate(String dateStr) {
        return parseDate(dateStr, DEFAULT_FORMAT_TIME);
    }

    public static Calendar parseDate(String dateStr, String srcFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(srcFormat, Locale.CHINA);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static String formatDefTime(String dataStr, String srcFormat) {
        return formatDate(dataStr, srcFormat, DEFAULT_FORMAT_TIME);
    }

    public static String formatDefDate(String dataStr, String srcFormat) {
        return formatDate(dataStr, srcFormat, DEFAULT_FORMAT_DATE) + "000000";
    }

    public static String formatDate(String dataStr, String srcFormat, String dstFormat) {
        return formatDate(parseDate(dataStr, srcFormat), dstFormat);
    }

    public static String formatDefDate(Calendar cal) {
        return formatDate(cal, DEFAULT_FORMAT_DATE) + "000000";
    }

    public static String formatDefTime(Calendar cal) {
        return formatDate(cal, DEFAULT_FORMAT_TIME);
    }

    public static String formatTimeByTimeZone(String date, int zone) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_TIME);
        df.setTimeZone(TimeZone.getTimeZone("GMT+" + zone));
        Date tmpDate = new Date();
        try {
            tmpDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tmpDate);
    }

    public static String formatDate(Calendar cal, String dstFormat) {
        return new SimpleDateFormat(dstFormat, Locale.CHINA).format(cal.getTime());
    }

    public static long getInterval(String date1, String date2) {
        return parseDefDate(date1).getTimeInMillis() - parseDefDate(date2).getTimeInMillis();
    }

    public static boolean judgeDate(String date) {
        String today = getTodayTime();
        if (today.compareTo(date) >= 0 && getInterval(today, date) < 2592000000L) {
            return true;
        }
        return false;
    }

    public static boolean cacheObject(Object obj, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(CACHE_PATH + fileName));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object loadObject(String fileName) {
        Exception e;
        File file = new File(CACHE_PATH + fileName);
        try {
            if (!file.exists()) {
                return null;
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            ois.close();
            fis.close();
            return obj;
        } catch (IOException e2) {
            e = e2;
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
    }

    public static void logout(final Context context) {
        final ProgressDialog dialog = getProgressDialog(context);
        showProgressDialog(dialog, TheApp.getContext().getString(C1680R.string.account_logouting));
        DroiUser.getCurrentUser().logoutInBackground(new DroiCallback<Boolean>() {
            public void result(Boolean aBoolean, DroiError droiError) {
                Tools.hideProgressDialog(dialog);
                if (droiError.isOk()) {
                    Tools.clearUserData();
                    Tools.loginAgain(context);
                    return;
                }
                Tools.makeToast((int) C1680R.string.account_logout_fail);
            }
        });
    }

    public static void loginAgain(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra("from_logout", true);
        context.startActivity(intent);
        EventBus.getDefault().post(new EventLogout());
    }

    public static boolean isQQLogin() {
        String id = DroiUser.getCurrentUser().getUserId();
        Log.i("chenxinX", "isQQLogin QQOpenId:" + SPUtils.getQQOpenId(id) + " QQAccessToken:" + SPUtils.getQQAccessToken(id) + " expiresDate:" + (SPUtils.getQQExpiresIn(id) - ((System.currentTimeMillis() - SPUtils.getQQExpiresDate(id)) / 1000)));
        return (TextUtils.isEmpty(SPUtils.getQQOpenId(id)) || TextUtils.isEmpty(SPUtils.getQQAccessToken(id)) || SPUtils.getQQExpiresIn(id) - ((System.currentTimeMillis() - SPUtils.getQQExpiresDate(id)) / 1000) <= 0) ? false : true;
    }

    public static void QQlogin(Activity activity, MyIUListener listener, Tencent mTencent) {
        mTencent.login(activity, "all", (IUiListener) listener);
    }

    public static void getDataAndGotoMain(final Activity activity, final ProgressDialog dialog) {
        showProgressDialog(dialog, activity.getString(C1680R.string.string_syncing_account));
        BaasHelper.getSportInBackground(new DroiQueryCallback<SportBean>() {

            class C19241 implements DroiQueryCallback<SleepBean> {
                C19241() {
                }

                public void result(List<SleepBean> list, DroiError droiError) {
                    Tools.hideProgressDialog(dialog);
                    if (droiError.isOk()) {
                        for (SleepBean bean : list) {
                            bean.setSync(1);
                            SleepHelper.getBeanDao().insertOrReplace(bean);
                        }
                        SPUtils.setSyncedData(true);
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.putExtras(activity.getIntent());
                        intent.setFlags(67108864);
                        activity.startActivity(intent);
                        activity.finish();
                        return;
                    }
                    Tools.showSyncFailDialog(activity);
                }
            }

            public void result(List<SportBean> list, DroiError droiError) {
                if (droiError.isOk()) {
                    for (SportBean bean : list) {
                        bean.setSync(1);
                        SportHelper.getBeanDao().insertOrReplace(bean);
                    }
                    BaasHelper.getSleepInBackground(new C19241());
                    return;
                }
                Tools.hideProgressDialog(dialog);
                Tools.showSyncFailDialog(activity);
            }
        });
    }

    private static void showSyncFailDialog(final Activity activity) {
        MyAlertDialog dialog = new MyAlertDialog(activity);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.string_sync_failed);
        dialog.setLeftButton((int) C1680R.string.string_cancel, new OnClickListener() {
            public void onClick(int witch) {
                SPUtils.setSyncedData(false);
                activity.finish();
            }
        });
        dialog.setRightButton((int) C1680R.string.string_retry, new OnClickListener() {
            public void onClick(int witch) {
                Tools.getDataAndGotoMain(activity, Tools.getProgressDialog(activity));
            }
        });
        dialog.show();
    }

    public static void clearUserData() {
        SportsReportActivity.reportBean = null;
        BtManagerService.deleteDevice();
        SPUtils.setSyncedData(false);
        SPUtils.clear(SPUtils.getUserShared());
        SPUtils.clear(SPUtils.getDeviceShared());
        SportHelper.getBeanDao().deleteAll();
        SleepHelper.getBeanDao().deleteAll();
        HeartHelper.getBeanDao().deleteAll();
        WeightHelper.getBeanDao().deleteAll();
    }

    public static String getDroiError(DroiError error) {
        int id;
        Log.i("zhuqichao", "error=" + error + ", code=" + error.getCode());
        switch (error.getCode()) {
            case DroiUser.f2749j /*1030305*/:
            case DroiUser.f2740a /*1040008*/:
            case 1040022:
            case DroiError.USER_ALREADY_EXISTS /*1070004*/:
                id = C1680R.string.droierror_user_exists;
                break;
            case DroiUser.f2741b /*1040009*/:
            case DroiError.USER_NOT_EXISTS /*1070002*/:
                id = C1680R.string.droierror_user_not_exists;
                break;
            case DroiUser.f2742c /*1040010*/:
            case DroiError.USER_PASSWORD_INCORRECT /*1070003*/:
                id = C1680R.string.droierror_password_error;
                break;
            case DroiUser.f2743d /*1040011*/:
            case DroiError.USER_DISABLE /*1070014*/:
                id = C1680R.string.droierror_user_disable;
                break;
            case DroiError.UNKNOWN_ERROR /*1070000*/:
            case DroiError.ERROR /*1070001*/:
                return TheApp.getContext().getString(C1680R.string.droierror_unknow, new Object[]{Integer.valueOf(error.getCode())});
            case DroiError.NETWORK_NOT_AVAILABLE /*1070005*/:
            case DroiError.SERVER_NOT_REACHABLE /*1070007*/:
            case DroiError.HTTP_SERVER_ERROR /*1070008*/:
            case DroiError.SERVICE_NOT_ALLOWED /*1070009*/:
            case DroiError.SERVICE_NOT_FOUND /*1070010*/:
            case DroiError.INTERNAL_SERVER_ERROR /*1070011*/:
                id = C1680R.string.droierror_net_error;
                break;
            case DroiError.USER_NOT_AUTHORIZED /*1070006*/:
                id = C1680R.string.droierror_user_not_login;
                break;
            case DroiError.INVALID_PARAMETER /*1070012*/:
                id = C1680R.string.droierror_error_param;
                break;
            case DroiError.NO_PERMISSION /*1070013*/:
                id = C1680R.string.droierror_no_permission;
                break;
            case DroiError.EXCEED_MAX_SIZE /*1070015*/:
                id = C1680R.string.droierror_exceed_size;
                break;
            case DroiError.FILE_NOT_READY /*1070016*/:
                id = C1680R.string.droierror_file_not_ready;
                break;
            case DroiError.CORE_NOT_INITIALIZED /*1070017*/:
                id = C1680R.string.droierror_not_init;
                break;
            case DroiError.USER_CANCELED /*1070018*/:
                id = C1680R.string.droierror_user_cancel;
                break;
            default:
                return TheApp.getContext().getString(C1680R.string.droierror_unknow, new Object[]{Integer.valueOf(error.getCode())});
        }
        return TheApp.getContext().getString(id);
    }

    public static float calculateBmi(int height, float weight) {
        return weight / ((((float) height) / 100.0f) * (((float) height) / 100.0f));
    }

    public static float calculateWeight(float bmi, int height) {
        return ((((float) height) / 100.0f) * (((float) height) / 100.0f)) * bmi;
    }

    public static String getVersionName() {
        PackageInfo pi = getPackageInfo();
        if (pi != null) {
            return pi.versionName;
        }
        return null;
    }

    public static int getVersionCode() {
        PackageInfo pi = getPackageInfo();
        if (pi != null) {
            return pi.versionCode;
        }
        return 0;
    }

    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            pi = TheApp.getInstance().getPackageManager().getPackageInfo(TheApp.getInstance().getPackageName(), 16384);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    public static void jumpToWinXin(Context context) {
        IWXAPI mApi = WXAPIFactory.createWXAPI(context, "wx4d148671075be61c", false);
        if (mApi.isWXAppInstalled() && mApi.isWXAppSupportAPI()) {
            Req req = new Req();
            req.toUserName = "gh_5fd67da1b3b7";
            req.profileType = 1;
            req.extMsg = "http://we.qq.com/d/AQDZxL24c6Og1v0vrWv6PN1KKxu57I_kcPJlxwnD#" + DroiUser.getCurrentUser().getUserId();
            mApi.sendReq(req);
        } else if (mApi.isWXAppInstalled() && mApi.isWXAppSupportAPI()) {
            makeToast((int) C1680R.string.wechart_user_before_login);
        } else {
            makeToast((int) C1680R.string.wechart_not_install);
        }
    }

    public static void sendHeightWeightToDevice() {
        User user = (User) DroiUser.getCurrentUser(User.class);
        BtDevice device = BtManagerService.getConnectDevice(null);
        if (device != null) {
            device.setSedentary("3|" + decimalFormat3(user.getHeight()) + "|" + decimalFormat4(Integer.valueOf(deleteWeightPoint(String.valueOf(WeightHelper.getNewestWeight().getWeight()))).intValue()) + "|||||");
            Log.i("hph", "sendHeightWeightToDevice=3|" + decimalFormat3(user.getHeight()) + "|" + decimalFormat4(Integer.valueOf(deleteWeightPoint(String.valueOf(WeightHelper.getNewestWeight().getWeight()))).intValue()) + "|||||");
        }
    }

    private static String decimalFormat3(int data) {
        return new DecimalFormat("#000").format((long) data);
    }

    private static String decimalFormat4(int data) {
        return new DecimalFormat("#0000").format((long) data);
    }

    private static String deleteWeightPoint(String weight) {
        return weight.replace(".", "");
    }

    public static boolean isAppInstalled(String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = TheApp.getContext().getPackageManager().getPackageInfo(packagename, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return true;
        }
        return false;
    }

    public static int dealQQjson(JSONObject obj) {
        int code = -1;
        try {
            code = obj.getInt("ret");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static int getHeartOxygen(int count) {
        if (count <= 70) {
            return 95;
        }
        if (count <= 110) {
            return 96;
        }
        if (count <= 150) {
            return 97;
        }
        return 95;
    }
}
