package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.account.IAccountListener;
import com.zhuoyi.account.ZyAccount;
import com.zhuoyou.plugin.action.ActionActivity;
import com.zhuoyou.plugin.action.ActionWelcomeInfo;
import com.zhuoyou.plugin.action.CacheTool;
import com.zhuoyou.plugin.cloud.AlarmUtils;
import com.zhuoyou.plugin.cloud.CloudSync;
import com.zhuoyou.plugin.cloud.GetDataFromNet;
import com.zhuoyou.plugin.cloud.NetMsgCode;
import com.zhuoyou.plugin.cloud.NetUtils;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.share.WeiboConstant;
import com.zhuoyou.plugin.weather.WeatherTools;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Welcome extends Activity implements OnClickListener {
    public static boolean isBack = false;
    public static boolean isentry = false;
    private ImageView ImageNetShow;
    private ImageView ImagePassButton;
    private String action_id = null;
    private RelativeLayout action_layout;
    private TextView actiong_query;
    private Bitmap bitmap = null;
    private Editor editor;
    private SimpleDateFormat formatter;
    private boolean isFirst;
    private boolean is_go_action = false;
    private boolean is_show_action = false;
    private RelativeLayout layout1;
    private LinearLayout layout2;
    private Button login;
    private Context mContext;
    private DataBaseUtil mData;
    private Handler mHandler;
    private ZyAccount mZyAccount;
    private CacheTool mcachetool = null;
    private ActionWelcomeInfo mwelcomedate = null;
    private SharedPreferences sharepreference;
    private Button skip;

    class C14262 implements Runnable {
        C14262() {
        }

        public void run() {
            String openId;
            HashMap<String, String> params = new HashMap();
            if (Tools.getLogin(Welcome.this.mContext)) {
                openId = Tools.getOpenId(Welcome.this.mContext);
            } else {
                openId = "0";
            }
            params.put("openId", openId);
            params.put("lcd", Welcome.this.GetLcdInfo());
            params.put("actIds", Welcome.this.CheckLocalActionId());
            new GetDataFromNet(NetMsgCode.APP_RUN_ACTION_INIT, ((RunningApp) Welcome.this.getApplication()).GetAppHandler(), params, Welcome.this.getApplication()).execute(new Object[]{NetMsgCode.URL});
        }
    }

    private class InitTask extends AsyncTask<String, Void, Boolean> {
        private InitTask() {
        }

        protected Boolean doInBackground(String... params) {
            WeatherTools.newInstance();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean result) {
            Welcome.this.gotoMainScreen();
        }
    }

    class C19021 implements IAccountListener {

        class C14251 implements Runnable {
            C14251() {
            }

            public void run() {
                AlarmUtils.setAutoSyncAlarm(Welcome.this.mContext);
                CloudSync.autoSyncType = 1;
                CloudSync.syncAfterLogin(1);
            }
        }

        C19021() {
        }

        public void onCancel() {
        }

        public void onSuccess(String userInfo) {
            Tools.saveInfoToSharePreference(Welcome.this.mContext, userInfo);
            Tools.setLogin(Welcome.this.mContext, true);
            Welcome.this.gotoMainScreen();
            Welcome.this.mHandler.postDelayed(new C14251(), 1000);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        this.mContext = this;
        this.mHandler = new Handler();
        this.isFirst = Tools.checkIsFirstEntry(this.mContext);
        this.mData = new DataBaseUtil(this.mContext);
        this.formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        initViews();
        AppActionNetInit();
        judgeGpsState();
    }

    private void initViews() {
        this.action_layout = (RelativeLayout) findViewById(R.id.ad_layout);
        this.ImageNetShow = (ImageView) findViewById(R.id.netshow);
        this.layout1 = (RelativeLayout) findViewById(R.id.layout1);
        this.layout2 = (LinearLayout) findViewById(R.id.layout2);
        this.login = (Button) findViewById(R.id.login);
        this.login.setOnClickListener(this);
        this.skip = (Button) findViewById(R.id.skip);
        this.skip.setOnClickListener(this);
        if (Locale.getDefault().getLanguage().equals("en")) {
            this.layout1.setBackgroundResource(R.drawable.logo_text_en);
        } else {
            this.layout1.setBackgroundResource(R.drawable.logo_text);
        }
        if (this.isFirst) {
            this.layout2.setVisibility(0);
            WeatherTools.newInstance();
            isBack = true;
        } else {
            this.layout2.setVisibility(8);
            new InitTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        }
        this.mcachetool = ((RunningApp) getApplication()).GetCacheTool();
        this.mwelcomedate = this.mcachetool.GetWelcomeDate();
        if (this.mwelcomedate != null) {
            String url = this.mwelcomedate.GetImgUrl();
            try {
                url = url.substring(0, url.lastIndexOf("/") + 1) + URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8").replace(SocializeConstants.OP_DIVIDER_PLUS, "%20");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String filePath = Tools.getSDPath() + "/Running/download/cache/" + GetFileName(url);
            if (new File(filePath).exists()) {
                this.action_layout.setVisibility(0);
                try {
                    this.bitmap = BitmapFactory.decodeFile(filePath);
                } catch (Exception e2) {
                }
                this.ImageNetShow.setImageBitmap(this.bitmap);
                return;
            }
            this.ImageNetShow.setImageResource(R.drawable.action_1);
            return;
        }
        this.action_layout.setVisibility(8);
    }

    public String GetFileName(String url) {
        String filename = "";
        if (url == null) {
            return filename;
        }
        String tmp = url;
        String file_tmp = url;
        for (int i = 0; i < 5; i++) {
            tmp = tmp.substring(0, tmp.lastIndexOf("/"));
        }
        for (String aa : file_tmp.substring(tmp.length() + 1).split("/")) {
            filename = filename + aa;
        }
        return filename.substring(0, filename.lastIndexOf("."));
    }

    private void gotoMainScreen() {
        if (!this.is_go_action) {
            isentry = true;
            startActivity(new Intent(this.mContext, Main.class));
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            finish();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.netshow:
            case R.id.button_query:
                this.is_go_action = true;
                Intent mquery_action = new Intent(this, ActionActivity.class);
                mquery_action.putExtra("is_from_welcome", true);
                startActivity(mquery_action);
                finish();
                return;
            case R.id.login:
                this.mZyAccount = new ZyAccount(getApplicationContext(), "1102927580", WeiboConstant.CONSUMER_KEY);
                this.mZyAccount.login(new C19021());
                return;
            case R.id.skip:
                isentry = true;
                Intent intent = new Intent(this.mContext, Main.class);
                intent.putExtra("config_dialog", true);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                finish();
                return;
            default:
                return;
        }
    }

    public String GetLcdInfo() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth + "x" + dm.heightPixels;
    }

    public String CheckLocalActionId() {
        return String.valueOf(0);
    }

    private void AppActionNetInit() {
        if (NetUtils.getAPNType(this) != -1) {
            new Thread(new C14262()).start();
        }
    }

    private void judgeGpsState() {
        this.sharepreference = getSharedPreferences("gaode_location_info", 0);
        if (this.sharepreference.getInt("map_activity_state", 0) > 1) {
            deletefromDatabase(this.mData.selectLastOperation(1));
        }
    }

    private void deletefromDatabase(long startTime) {
        int i;
        List<Integer> listGpsId = this.mData.selectPointID(startTime, conversTime(System.currentTimeMillis()), 0);
        List<Integer> listOperationId = this.mData.selectOperationId(startTime, conversTime(System.currentTimeMillis()), 0);
        if (listGpsId.size() > 0) {
            for (i = 0; i < listGpsId.size(); i++) {
                ContentValues runningItem = new ContentValues();
                runningItem.put(DataBaseContants.GPS_TABLE, DataBaseContants.TABLE_POINT_NAME);
                runningItem.put(DataBaseContants.GPS_DELETE, (Integer) listGpsId.get(i));
                getContentResolver().insert(DataBaseContants.CONTENT_URI_GPSSYNC, runningItem);
            }
        }
        if (listOperationId.size() > 0) {
            for (i = 0; i < listOperationId.size(); i++) {
                runningItem = new ContentValues();
                runningItem.put(DataBaseContants.GPS_TABLE, DataBaseContants.TABLE_OPERATION_NAME);
                runningItem.put(DataBaseContants.GPS_DELETE, (Integer) listOperationId.get(i));
                getContentResolver().insert(DataBaseContants.CONTENT_URI_GPSSYNC, runningItem);
            }
        }
        this.editor = this.sharepreference.edit();
        this.editor.putInt("map_activity_state", 0);
        if (this.sharepreference.contains("is_have_beginaddr")) {
            this.editor.remove("is_have_beginaddr");
        }
        if (this.sharepreference.contains("gps_sport_id")) {
            this.mData.deleteGpsFromID(this.sharepreference.getLong("gps_sport_id", -1));
            this.editor.remove("gps_sport_id");
        }
        this.editor.putInt("save_service_step", 0);
        this.editor.putInt("gps_singal_state", 0);
        this.editor.putFloat("save_service_distance", 0.0f);
        this.editor.putBoolean("is_begin_point", true);
        this.editor.commit();
        this.mData.deleteOperation(startTime, conversTime(System.currentTimeMillis()));
        this.mData.deletePoint(startTime, conversTime(System.currentTimeMillis()));
    }

    public long conversTime(long systemTime) {
        return Long.valueOf(Long.parseLong(this.formatter.format(new Date(systemTime)))).longValue();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.bitmap != null) {
            this.ImageNetShow.setImageBitmap(null);
            this.bitmap.recycle();
            this.bitmap = null;
        }
    }

    public void onBackPressed() {
        if (isBack) {
            super.onBackPressed();
            isBack = false;
        }
    }
}
