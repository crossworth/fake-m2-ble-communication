package com.zhuoyou.plugin.weather;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.amap.api.services.district.DistrictSearchQuery;
import com.baidu.location.BDGeofence;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fithealth.running.R;
import com.zhuoyi.account.constant.UrlConstant;
import com.zhuoyou.plugin.bluetooth.connection.BtProfileReceiver;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.cloud.NetUtils;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;
import p031u.aly.C1502d;

@SuppressLint({"SdCardPath"})
public class WeatherTools implements BDLocationListener {
    private static final long EXPIRESIN_TIME = 21600000;
    private static final String JSON_KEY_CITYNAME = "cityname";
    private static final String JSON_KEY_PM25 = "pm25";
    private static final String JSON_KEY_PM25_AQI = "aqi";
    private static final String JSON_KEY_RESULT = "result";
    private static final String JSON_KEY_TODAY = "todayWeather";
    private static final String JSON_KEY_TODAY_AIR = "air";
    private static final String JSON_KEY_TODAY_DETAIL = "detail";
    private static final String JSON_KEY_TODAY_TEMP = "temperature";
    private static final String JSON_KEY_TODAY_WEATHER = "weather";
    private static final String JSON_KEY_TODAY_WIND = "wind";
    private static WeatherTools mInstance = null;
    private String DATABASE_NAME = "city.db";
    private String DATABASE_PATH = (C1502d.f3811a + this.mCtx.getPackageName() + "/databases/");
    private Context mCtx = RunningApp.getInstance();
    private LocationClient mLocationClient = null;

    class C14441 extends Thread {
        C14441() {
        }

        public void run() {
            WeatherTools.this.requestLocation();
        }
    }

    class C14452 implements Runnable {
        C14452() {
        }

        public void run() {
            WeatherTools.this.postWeatherData();
        }
    }

    public static WeatherTools newInstance() {
        if (mInstance == null) {
            mInstance = new WeatherTools();
        }
        return mInstance;
    }

    public WeatherTools() {
        createCityDatabase(this.mCtx);
    }

    public void getCurrWeather() {
        if (NetUtils.getAPNType(this.mCtx) == -1) {
            Toast.makeText(this.mCtx, R.string.check_network, 0).show();
        } else if (isLocationVaild()) {
            getWeatherData();
        } else {
            if (this.mLocationClient != null) {
                this.mLocationClient.stop();
                this.mLocationClient.unRegisterLocationListener(this);
                this.mLocationClient = null;
            }
            this.mLocationClient = new LocationClient(this.mCtx);
            this.mLocationClient.registerLocationListener(this);
            LocationClientOption option = new LocationClientOption();
            option.setCoorType(BDGeofence.COORD_TYPE_BD09LL);
            option.setPriority(2);
            option.setProdName("LocationDemo");
            option.setAddrType("all");
            this.mLocationClient.setLocOption(option);
            new C14441().start();
        }
    }

    private boolean isLocationVaild() {
        String region = getRegion();
        long expiresin = getExpiresin();
        return !TextUtils.isEmpty(region) && (expiresin == 0 || System.currentTimeMillis() < expiresin);
    }

    private void requestLocation() {
        if (this.mLocationClient == null || !this.mLocationClient.isStarted()) {
            this.mLocationClient.start();
            this.mLocationClient.requestLocation();
            return;
        }
        this.mLocationClient.requestLocation();
    }

    private void setRegion(String region) {
        Editor mEditor = this.mCtx.getSharedPreferences("app_config", 0).edit();
        mEditor.putString("location_region", region);
        mEditor.commit();
    }

    public String getRegion() {
        return this.mCtx.getSharedPreferences("app_config", 0).getString("location_region", "");
    }

    private void setExpiresin(long time) {
        Editor mEditor = this.mCtx.getSharedPreferences("app_config", 0).edit();
        mEditor.putLong("location_expiresin", time);
        mEditor.commit();
    }

    private long getExpiresin() {
        return this.mCtx.getSharedPreferences("app_config", 0).getLong("location_expiresin", 0);
    }

    private String startConnectUrl(String url) {
        IOException e;
        Throwable th;
        String result = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        ByteArrayBuffer baf = null;
        try {
            URLConnection ucon = new URL(url).openConnection();
            ucon.setConnectTimeout(UrlConstant.SEDN_MMS_DELAY);
            ucon.setReadTimeout(30000);
            is = ucon.getInputStream();
            BufferedInputStream bis2 = new BufferedInputStream(is);
            try {
                ByteArrayBuffer baf2 = new ByteArrayBuffer(1024);
                while (true) {
                    try {
                        int current = bis2.read();
                        if (current == -1) {
                            break;
                        }
                        baf2.append((byte) current);
                    } catch (IOException e2) {
                        e = e2;
                        baf = baf2;
                        bis = bis2;
                    } catch (Throwable th2) {
                        th = th2;
                        baf = baf2;
                        bis = bis2;
                    }
                }
                result = EncodingUtils.getString(baf2.toByteArray(), "UTF-8");
                if (result != null && result.equals("zero")) {
                    result = null;
                }
                if (baf2 != null) {
                    baf2.clear();
                }
                if (bis2 != null) {
                    try {
                        bis2.close();
                    } catch (IOException e3) {
                        e = e3;
                        bis = bis2;
                        e.printStackTrace();
                        return result;
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e4) {
                        e = e4;
                        e.printStackTrace();
                        return result;
                    }
                }
            } catch (IOException e5) {
                e = e5;
                bis = bis2;
                try {
                    e.printStackTrace();
                    if (baf != null) {
                        baf.clear();
                    }
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (is != null) {
                        is.close();
                    }
                    return result;
                } catch (Throwable th3) {
                    th = th3;
                    if (baf != null) {
                        baf.clear();
                    }
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e62) {
                            e62.printStackTrace();
                            throw th;
                        }
                    }
                    if (is != null) {
                        is.close();
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                bis = bis2;
                if (baf != null) {
                    baf.clear();
                }
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
                }
                throw th;
            }
        } catch (IOException e7) {
            e62 = e7;
            e62.printStackTrace();
            if (baf != null) {
                baf.clear();
            }
            if (bis != null) {
                bis.close();
            }
            if (is != null) {
                is.close();
            }
            return result;
        }
        return result;
    }

    private void getCurrAqiEtc(String result) {
        int aqi = -1;
        Log.i("gchk", "" + result);
        try {
            JSONObject allWeather = new JSONObject(result);
            String cityname = allWeather.getString(JSON_KEY_CITYNAME);
            if (allWeather.getInt(JSON_KEY_RESULT) == 0) {
                JSONObject todayWeather = allWeather.getJSONObject(JSON_KEY_TODAY);
                String detail = todayWeather.getString(JSON_KEY_TODAY_DETAIL);
                String air = todayWeather.getString(JSON_KEY_TODAY_AIR);
                String temperature = todayWeather.getString(JSON_KEY_TODAY_TEMP);
                String weather = todayWeather.getString("weather");
                String wind = todayWeather.getString(JSON_KEY_TODAY_WIND);
                JSONObject pm25 = allWeather.getJSONObject("pm25");
                aqi = pm25.getInt(JSON_KEY_PM25_AQI);
                String pm_quality = pm25.getString("pm_quality");
                StringBuilder comment = new StringBuilder();
                String[] details = detail.split("\\：|\\；");
                String[] airs = air.split("\\：|\\；");
                String[] temperatures = temperature.split("/");
                String[] weathers = weather.split("\\ ");
                comment.append(cityname + "|");
                if (details.length > 2) {
                    comment.append(details[2] + "|");
                } else {
                    comment.append("无|");
                }
                if (details.length > 6) {
                    comment.append(details[6] + "|");
                } else {
                    comment.append("无|");
                }
                if (airs.length > 3) {
                    comment.append(airs[3] + "|");
                }
                comment.append(temperatures[0] + "|");
                comment.append(temperatures[1] + "|");
                comment.append(weathers[1] + "|");
                comment.append(wind + "|");
                comment.append(aqi + "|");
                comment.append(pm_quality + "|");
                BluetoothDevice currRemoteDevice = BtProfileReceiver.getRemoteDevice();
                if (currRemoteDevice != null && BluetoothService.getInstance().isConnected() && Util.getProductName(currRemoteDevice.getName()).equals("TJ01")) {
                    Intent intent = new Intent("com.tyd.plugin.receiver.sendmsg");
                    intent.putExtra("plugin_cmd", 4);
                    intent.putExtra("plugin_content", comment.toString());
                    this.mCtx.sendBroadcast(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("gchk", "get aqi error =" + e.getMessage());
        }
        Log.i("gchk", "aqi = " + aqi);
        if (aqi > 0) {
            Tools.updatePm25(Tools.getDate(0), aqi);
        }
    }

    private boolean createCityDatabase(Context context) {
        boolean ret;
        FileNotFoundException e;
        Throwable th;
        IOException e2;
        String outFileName = this.DATABASE_PATH + this.DATABASE_NAME;
        if (new File(outFileName).exists()) {
            return true;
        }
        File dir = new File(this.DATABASE_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        OutputStream output = null;
        InputStream input = context.getResources().openRawResource(R.raw.city);
        try {
            OutputStream output2 = new FileOutputStream(outFileName);
            try {
                byte[] buffer = new byte[2048];
                while (true) {
                    int length = input.read(buffer);
                    if (length <= 0) {
                        break;
                    }
                    output2.write(buffer, 0, length);
                }
                ret = true;
                try {
                    output2.flush();
                    output2.close();
                } catch (IOException e3) {
                }
                try {
                    input.close();
                    output = output2;
                } catch (IOException e4) {
                    output = output2;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                output = output2;
                try {
                    e.printStackTrace();
                    ret = false;
                    try {
                        output.flush();
                        output.close();
                    } catch (IOException e6) {
                    }
                    try {
                        input.close();
                    } catch (IOException e7) {
                    }
                    return ret;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        output.flush();
                        output.close();
                    } catch (IOException e8) {
                    }
                    try {
                        input.close();
                    } catch (IOException e9) {
                    }
                    throw th;
                }
            } catch (IOException e10) {
                e2 = e10;
                output = output2;
                e2.printStackTrace();
                ret = false;
                try {
                    output.flush();
                    output.close();
                } catch (IOException e11) {
                }
                try {
                    input.close();
                } catch (IOException e12) {
                }
                return ret;
            } catch (Throwable th3) {
                th = th3;
                output = output2;
                output.flush();
                output.close();
                input.close();
                throw th;
            }
        } catch (FileNotFoundException e13) {
            e = e13;
            e.printStackTrace();
            ret = false;
            output.flush();
            output.close();
            input.close();
            return ret;
        } catch (IOException e14) {
            e2 = e14;
            e2.printStackTrace();
            ret = false;
            output.flush();
            output.close();
            input.close();
            return ret;
        }
        return ret;
    }

    public void onReceiveLocation(BDLocation location) {
        if (this.mLocationClient != null) {
            this.mLocationClient.stop();
            this.mLocationClient.unRegisterLocationListener(this);
            this.mLocationClient = null;
        }
        Log.e("gchk", "onReceiveLocation");
        if (location != null && location.getLocType() == BDLocation.TypeNetWorkLocation) {
            String region = location.getCity();
            if (region == null || region.equals("")) {
                Tools.updatePm25(Tools.getDate(0), -1);
                return;
            }
            Log.e("gchk", region);
            setRegion(region);
            setExpiresin(System.currentTimeMillis() + 21600000);
            getWeatherData();
        }
    }

    public void onReceivePoi(BDLocation arg0) {
    }

    private void getWeatherData() {
        new Thread(new C14452()).start();
    }

    private int postWeatherData() {
        if (!isLocationVaild()) {
            return -1;
        }
        String city = getRegion();
        city = city.substring(0, city.length() - 1);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(this.DATABASE_PATH + this.DATABASE_NAME, null, 1);
        Cursor cursor = db.rawQuery("select code from city where city = '" + city + "'", null);
        if (cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            Log.i("gchk", "未获得城市 code ");
        } else {
            String result = startConnectUrl("http://newweather.oo523.com:8080/=/pm2_5?cityid=" + cursor.getInt(cursor.getColumnIndex("code")) + "&mode=new" + "&format=json");
            if (result != null) {
                try {
                    getCurrAqiEtc(result);
                } catch (Exception e) {
                    Log.i("gchk", "result has error");
                }
            }
        }
        cursor.close();
        db.close();
        return 1;
    }

    public String[] selectProvice() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(this.DATABASE_PATH + this.DATABASE_NAME, null, 1);
        Cursor cursor = db.rawQuery("select distinct provice from city ", null);
        String[] provice = new String[cursor.getCount()];
        if (cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            Log.i("txhlog", "未获得provice  ");
        } else {
            for (int i = 0; i < cursor.getCount(); i++) {
                provice[i] = cursor.getString(cursor.getColumnIndex("provice"));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return provice;
    }

    public String[] selectCity(Context con, String provice) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(this.DATABASE_PATH + this.DATABASE_NAME, null, 1);
        Cursor cursor = db.rawQuery("select city from city where provice = '" + provice + "'", null);
        String[] city = new String[cursor.getCount()];
        if (cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            Log.i("txhlog", "未获得city ");
        } else {
            for (int i = 0; i < cursor.getCount(); i++) {
                String temp = cursor.getString(cursor.getColumnIndex(DistrictSearchQuery.KEYWORDS_CITY));
                if (temp.equals(con.getResources().getString(R.string.counties))) {
                    temp = con.getResources().getString(R.string.city);
                }
                city[i] = temp;
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return city;
    }
}
