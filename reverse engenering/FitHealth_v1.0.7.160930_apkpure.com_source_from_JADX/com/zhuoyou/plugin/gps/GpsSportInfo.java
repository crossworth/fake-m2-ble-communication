package com.zhuoyou.plugin.gps;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.internal.view.SupportMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMapScreenShotListener;
import com.amap.api.maps.AMap.OnMapTouchListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.fithealth.running.R;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import com.weibo.net.AsyncWeiboRunner.RequestListener;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.zhuoyou.plugin.album.BitmapUtils;
import com.zhuoyou.plugin.cloud.GPSDataSync;
import com.zhuoyou.plugin.cloud.NetMsgCode;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.running.SharePopupWindow;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.selfupdate.TerminalInfo;
import com.zhuoyou.plugin.share.AuthorizeActivity;
import com.zhuoyou.plugin.share.ShareTask;
import com.zhuoyou.plugin.share.ShareToWeixin;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.http.protocol.HTTP;

public class GpsSportInfo extends Activity implements OnMarkerClickListener, OnClickListener, OnMapLoadedListener, OnMapTouchListener, OnCameraChangeListener, OnMapScreenShotListener {
    public static final int BEGIN_MARK = 1;
    public static final int CANCEL_MARK = 3;
    public static final int CURRENT_MARK = 4;
    private static final int DELAY_MAP_CENTER = 1200;
    public static final int END_MARK = 2;
    private static final int SHARE_TO_MORE = 1204;
    private static final int SHARE_TO_QQ = 1203;
    private static final int SHARE_TO_QUAN = 1202;
    private static final int SHARE_TO_WEIXIN = 1201;
    private static final int TIMELINE_SUPPORTED_VERSION = 553779201;
    private static int select = 0;
    private ImageView BtnShare;
    private AMap aMap;
    private Marker beiginMark;
    private Marker cancelMark;
    private long clickStime = 0;
    float current_mzoom;
    private Marker endMark;
    private long endTime;
    private String end_address;
    private String fileName;
    private GuidePointModel firstPoint;
    private String gpsId;
    double[] gpspoints;
    private TextView heart_tvde_distance;
    private boolean isQQInstalled = false;
    private boolean isWBInstalled = false;
    private boolean isWXInstalled = false;
    private boolean isZoomall = true;
    private boolean is_china = true;
    private OnClickListener itemsOnClick = new C12774();
    private GuidePointModel lastPoint;
    private Dialog loadDialog;
    private ImageView mBtnZoom;
    private Handler mCloudHandler = new C12721();
    private Context mContext;
    private DataBaseUtil mDataBaseUtil;
    private Dialog mDialog;
    private GpsSportDataModel mGpsInfo;
    private Handler mHandler = new C12732();
    private Handler mHandler2 = new C12785();
    private LinearLayout mLayoutDetailData;
    private LinearLayout mLayoutGpsShare;
    private Marker mMark;
    private ImageView mMore;
    private SharePopupWindow mPopupWindow;
    private RequestListener mRequestListener = new C18813();
    private ImageView mShare_qq;
    private ImageView mShare_quan;
    private ImageView mShare_weixin;
    private TextView mTVDCalories;
    private TextView mTVDDisSpeed;
    private TextView mTVDDistance;
    private TextView mTVDStep;
    private TextView mTVDTimer;
    private UiSettings mUiSettings;
    private MapView mapView;
    private String mendAddress;
    private String mstartAddress;
    private View mview;
    private Polyline polyline;
    private Resources res;
    private long startTime;
    private String start_address;
    private Typeface tf;
    private Weibo weibo = Weibo.getInstance();

    class C12721 extends Handler {
        C12721() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NetMsgCode.getGPSInfo /*10501*/:
                    GpsSportInfo.this.mGpsInfo = GpsSportInfo.this.mDataBaseUtil.selectGpsInfoForID(Long.parseLong(GpsSportInfo.this.gpsId));
                    GpsSportInfo.this.loadingMapData();
                    return;
                case 110011:
                    if (GpsSportInfo.this.loadDialog.isShowing()) {
                        GpsSportInfo.this.loadDialog.dismiss();
                    }
                    Toast.makeText(GpsSportInfo.this, R.string.update_failed, 0).show();
                    return;
                default:
                    return;
            }
        }
    }

    class C12732 extends Handler {
        C12732() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1200:
                    GpsSportInfo.this.aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng((GpsSportInfo.this.gpspoints[0] + GpsSportInfo.this.gpspoints[2]) / 2.0d, (GpsSportInfo.this.gpspoints[1] + GpsSportInfo.this.gpspoints[3]) / 2.0d), GpsSportInfo.this.current_mzoom));
                    break;
                case 1201:
                    if (GpsSportInfo.this.isWXInstalled) {
                        if (!ShareToWeixin.api.isWXAppSupportAPI()) {
                            Toast.makeText(GpsSportInfo.this.mContext, R.string.weixin_no_support, 0).show();
                            break;
                        }
                        ShareToWeixin.SharetoWX(GpsSportInfo.this.mContext, false, GpsSportInfo.this.fileName);
                        GpsSportInfo.this.mLayoutGpsShare.setVisibility(8);
                        break;
                    }
                    Toast.makeText(GpsSportInfo.this.mContext, R.string.install_weixin, 0).show();
                    break;
                case 1202:
                    if (GpsSportInfo.this.isWXInstalled) {
                        if (ShareToWeixin.api.getWXAppSupportAPI() < 553779201) {
                            Toast.makeText(GpsSportInfo.this.mContext, R.string.weixin_no_support_quan, 0).show();
                            break;
                        }
                        ShareToWeixin.SharetoWX(GpsSportInfo.this.mContext, true, GpsSportInfo.this.fileName);
                        GpsSportInfo.this.mLayoutGpsShare.setVisibility(8);
                        break;
                    }
                    Toast.makeText(GpsSportInfo.this.mContext, R.string.install_weixin, 0).show();
                    break;
                case 1203:
                    if (!GpsSportInfo.this.isQQInstalled) {
                        Toast.makeText(GpsSportInfo.this.mContext, R.string.install_qq, 0).show();
                        break;
                    }
                    GpsSportInfo.this.shareToQQ(GpsSportInfo.this.fileName);
                    GpsSportInfo.this.mLayoutGpsShare.setVisibility(8);
                    break;
                case GpsSportInfo.SHARE_TO_MORE /*1204*/:
                    if (System.currentTimeMillis() - GpsSportInfo.this.clickStime > 1000) {
                        GpsSportInfo.this.clickStime = System.currentTimeMillis();
                        GpsSportInfo.this.mPopupWindow = new SharePopupWindow(GpsSportInfo.this, GpsSportInfo.this.itemsOnClick, GpsSportInfo.this.fileName);
                        GpsSportInfo.this.mPopupWindow.setInputMethodMode(1);
                        GpsSportInfo.this.mPopupWindow.setSoftInputMode(16);
                        GpsSportInfo.this.mPopupWindow.showAtLocation(GpsSportInfo.this.findViewById(R.id.gps_sport_info), 81, 0, 0);
                        GpsSportInfo.this.mLayoutGpsShare.setVisibility(8);
                        break;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

    class C12774 implements OnClickListener {
        C12774() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.share_weibo:
                    if (GpsSportInfo.this.weibo.isSessionValid()) {
                        GpsSportInfo.select = 1;
                        SharePopupWindow.mInstance.getWeiboView().setImageResource(R.drawable.share_wb_select);
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClass(GpsSportInfo.this, AuthorizeActivity.class);
                    GpsSportInfo.this.startActivity(intent);
                    return;
                case R.id.share:
                    if (GpsSportInfo.select <= 0) {
                        Toast.makeText(GpsSportInfo.this.mContext, R.string.select_platform, 0).show();
                        return;
                    } else if (SharePopupWindow.mInstance != null) {
                        GpsSportInfo.this.share2weibo(SharePopupWindow.mInstance.getShareContent(), Tools.getScreenShot(SharePopupWindow.mInstance.getShareFileName()));
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    class C12785 extends Handler {
        C12785() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    GpsSportInfo.select = 1;
                    return;
                default:
                    return;
            }
        }
    }

    private class InitDataTask extends AsyncTask<String, Integer, String> {
        private InitDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            GpsSportInfo.this.loadDialog.show();
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            GpsSportInfo.this.loadDialog.dismiss();
        }

        protected String doInBackground(String... params) {
            GpsSportInfo.this.initDatabaseline();
            return null;
        }
    }

    class C18813 implements RequestListener {

        class C12741 implements Runnable {
            C12741() {
            }

            public void run() {
                Toast.makeText(GpsSportInfo.this, R.string.share_success, 0).show();
            }
        }

        C18813() {
        }

        public void onComplete(String response) {
            GpsSportInfo.this.runOnUiThread(new C12741());
        }

        public void onError(final WeiboException e) {
            GpsSportInfo.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(GpsSportInfo.this, e.getStatusMessage(), 0).show();
                }
            });
        }

        public void onIOException(final IOException e) {
            GpsSportInfo.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(GpsSportInfo.this, e.getMessage(), 0).show();
                }
            });
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpssport_info);
        this.tf = Typeface.createFromAsset(getAssets(), "font/cmtattoodragon.ttf");
        this.res = getResources();
        this.mapView = (MapView) findViewById(R.id.gpssport_map);
        this.mapView.onCreate(savedInstanceState);
        this.start_address = this.res.getString(R.string.gps_startAddress);
        this.end_address = this.res.getString(R.string.gps_endAddress);
        if (this.aMap == null) {
            this.aMap = this.mapView.getMap();
            this.mUiSettings = this.aMap.getUiSettings();
            this.mUiSettings.setZoomControlsEnabled(false);
            this.aMap.setOnMarkerClickListener(this);
            this.aMap.setOnMapLoadedListener(this);
            this.aMap.setOnMapTouchListener(this);
            this.aMap.setOnCameraChangeListener(this);
        }
        this.mContext = this;
        getShareAppStatus();
        getLanguageEnv();
        this.mview = (FrameLayout) findViewById(R.id.gps_record_info);
        this.mTVDStep = (TextView) findViewById(R.id.gps_tvde_step);
        this.mTVDStep.setTypeface(this.tf);
        this.mTVDTimer = (TextView) findViewById(R.id.gps_tvde_timer);
        this.mTVDDistance = (TextView) findViewById(R.id.gps_tvde_distance);
        this.mTVDDistance.setTypeface(this.tf);
        this.mTVDDisSpeed = (TextView) findViewById(R.id.gps_tvde_dipspeed);
        this.mTVDDisSpeed.setTypeface(this.tf);
        this.mTVDCalories = (TextView) findViewById(R.id.gps_tvde_calorie );
        this.mTVDCalories.setTypeface(this.tf);
        this.heart_tvde_distance = (TextView) findViewById(R.id.heart_tvde_distance);
        this.heart_tvde_distance.setTypeface(this.tf);
        this.mShare_weixin = (ImageView) findViewById(R.id.share_weixin);
        this.mShare_weixin.setOnClickListener(this);
        this.mShare_quan = (ImageView) findViewById(R.id.share_quan);
        this.mShare_quan.setOnClickListener(this);
        this.mShare_qq = (ImageView) findViewById(R.id.share_qq);
        this.mShare_qq.setOnClickListener(this);
        this.mMore = (ImageView) findViewById(R.id.share_more);
        this.mMore.setOnClickListener(this);
        this.mBtnZoom = (ImageView) findViewById(R.id.gps_igbtn_zoom);
        this.BtnShare = (ImageView) findViewById(R.id.gps_share);
        this.mLayoutDetailData = (LinearLayout) findViewById(R.id.gps_layout_detaildata);
        this.mLayoutGpsShare = (LinearLayout) findViewById(R.id.gps_share_choice);
        this.mBtnZoom.setOnClickListener(this);
        this.BtnShare.setOnClickListener(this);
        this.mDataBaseUtil = new DataBaseUtil(getApplicationContext());
        this.loadDialog = new Builder(this).setTitle(this.res.getString(R.string.gps_loadingmMap)).setMessage(this.res.getString(R.string.gps_isLoadingMap)).create();
        this.gpsId = getIntent().getStringExtra("gpsid");
        Log.i("lsj", "gpsId = " + this.gpsId);
        if (TextUtils.isEmpty(this.gpsId)) {
            Log.i("lsj", "fail");
            if (this.loadDialog.isShowing()) {
                this.loadDialog.dismiss();
            }
            Toast.makeText(this, R.string.update_failed, 1).show();
            return;
        }
        this.mGpsInfo = this.mDataBaseUtil.selectGpsInfoForID(Long.parseLong(this.gpsId));
        if (this.mGpsInfo.getStarttime() != 0) {
            loadingMapData();
        } else {
            downCloudGPSData(Long.valueOf(this.gpsId).longValue());
        }
    }

    private void loadingMapData() {
        this.startTime = this.mGpsInfo.getStarttime();
        this.endTime = this.mGpsInfo.getEndtime();
        this.mstartAddress = this.mGpsInfo.getStartAddress();
        this.mendAddress = this.mGpsInfo.getEndAddress();
        initTextValue();
        if (this.startTime != 0 && this.endTime != 0) {
            this.gpspoints = this.mDataBaseUtil.findGpsBound(this.startTime, this.endTime);
            this.firstPoint = this.mDataBaseUtil.selectFirstPoint(this.startTime, this.endTime);
            this.lastPoint = this.mDataBaseUtil.selectLasttPoint(this.startTime, this.startTime);
            new InitDataTask().execute(new String[0]);
        }
    }

    private void downCloudGPSData(long gpsId) {
        if (TerminalInfo.generateTerminalInfo(this).getNetworkType() == (byte) 0) {
            Toast.makeText(this, R.string.open_net, 0).show();
        } else {
            new GPSDataSync(this, 1).getGPSFromCloud(this.mCloudHandler, gpsId);
        }
    }

    private void initTextValue() {
        Format decFormat = new DecimalFormat("#0.00");
        Format intFormat = new DecimalFormat("00");
        this.mTVDStep.setText(String.valueOf(this.mGpsInfo.getSteps()));
        this.mTVDTimer.setText(formatTimer(this.mGpsInfo.getDurationtime()));
        this.mTVDDistance.setText(decFormat.format(Double.valueOf(this.mGpsInfo.getTotalDistance() / 1000.0d)));
        this.mTVDDisSpeed.setText(decFormat.format(Double.valueOf(this.mGpsInfo.getAvespeed() * 3.6d)));
        this.mTVDCalories.setText("" + Double.valueOf(this.mGpsInfo.getCalorie()).intValue());
        Log.i("lsj", "mGpsInfo.getHeartCount()");
        if (this.mGpsInfo.getHeartCount() == null) {
            this.heart_tvde_distance.setText("0");
        } else {
            this.heart_tvde_distance.setText(this.mGpsInfo.getHeartCount() + "");
        }
    }

    private void getLanguageEnv() {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            this.is_china = true;
        } else {
            this.is_china = false;
        }
    }

    public static String formatTimer(long timer) {
        Format format = new DecimalFormat("00");
        String hour = format.format(Long.valueOf((timer % 86400) / 3600));
        String minute = format.format(Long.valueOf((timer % 3600) / 60));
        return hour + ":" + minute + ":" + format.format(Long.valueOf(timer % 60));
    }

    public void initDatabaseline() {
        List<GuidePointModel> smallistPoint = new ArrayList();
        List<GuidePointModel> smallistPoint2 = new ArrayList();
        if (this.startTime != 0 && this.firstPoint.getLongitude() != 0.0d) {
            List<GuidePointModel> sublistPoint = this.mDataBaseUtil.selectPoint(this.firstPoint.getTime(), this.endTime, 0);
            if (sublistPoint.size() <= 0) {
                return;
            }
            List<GuidePointModel> handlistPoint;
            GuidePointModel beginPoint;
            GuidePointModel endPoint;
            if (sublistPoint.size() == 1) {
                smallistPoint = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(0)).getTime(), this.endTime);
                handlistPoint = GpsUtil.handlePoint(smallistPoint);
                beginPoint = (GuidePointModel) handlistPoint.get(0);
                beginPoint.setTime(((GuidePointModel) smallistPoint.get(0)).getTime());
                hanlderMarker(beginPoint, 1);
                endPoint = (GuidePointModel) handlistPoint.get(handlistPoint.size() - 1);
                endPoint.setTime(((GuidePointModel) smallistPoint.get(smallistPoint.size() - 1)).getTime());
                hanlderMarker(endPoint, 2);
                initLine(handlistPoint, false);
                return;
            }
            int i = 0;
            while (i < sublistPoint.size()) {
                if (i > 0 && (((GuidePointModel) sublistPoint.get(i)).getPointState() == 3 || ((GuidePointModel) sublistPoint.get(i)).getPointState() == 5 || ((GuidePointModel) sublistPoint.get(i)).getPointState() == 6)) {
                    smallistPoint = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i - 1)).getTime(), ((GuidePointModel) sublistPoint.get(i)).getTime());
                    List<GuidePointModel> currentlistPoint;
                    if (smallistPoint.size() == 2) {
                        initLine(smallistPoint, false);
                        if (i == sublistPoint.size() - 1) {
                            smallistPoint2 = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i)).getTime(), this.endTime);
                        } else {
                            smallistPoint2 = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i)).getTime(), ((GuidePointModel) sublistPoint.get(i + 1)).getTime() - 1);
                        }
                        currentlistPoint = new ArrayList();
                        currentlistPoint.add(smallistPoint.get(smallistPoint.size() - 1));
                        currentlistPoint.add(GpsUtil.handlePoint(smallistPoint2).get(0));
                        initLine(currentlistPoint, false);
                    } else if (smallistPoint.size() > 2) {
                        handlistPoint = GpsUtil.handlePoint(this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i - 1)).getTime(), ((GuidePointModel) sublistPoint.get(i)).getTime() - 1));
                        initLine(handlistPoint, false);
                        if (i == sublistPoint.size() - 1) {
                            smallistPoint2 = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i)).getTime(), this.endTime);
                        } else {
                            smallistPoint2 = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i)).getTime(), ((GuidePointModel) sublistPoint.get(i + 1)).getTime() - 1);
                        }
                        currentlistPoint = new ArrayList();
                        currentlistPoint.add(handlistPoint.get(handlistPoint.size() - 1));
                        currentlistPoint.add(GpsUtil.handlePoint(smallistPoint2).get(0));
                        initLine(currentlistPoint, false);
                    }
                }
                if (i == 1) {
                    smallistPoint = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(0)).getTime(), ((GuidePointModel) sublistPoint.get(1)).getTime());
                    beginPoint = (GuidePointModel) GpsUtil.handlePoint(smallistPoint).get(0);
                    beginPoint.setTime(((GuidePointModel) smallistPoint.get(0)).getTime());
                    hanlderMarker(beginPoint, 1);
                }
                if (i == sublistPoint.size() - 1) {
                    smallistPoint = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i)).getTime(), this.endTime);
                    handlistPoint = GpsUtil.handlePoint(smallistPoint);
                    endPoint = (GuidePointModel) handlistPoint.get(handlistPoint.size() - 1);
                    endPoint.setTime(((GuidePointModel) smallistPoint.get(smallistPoint.size() - 1)).getTime());
                    hanlderMarker(endPoint, 2);
                    if (handlistPoint.size() > 1) {
                        initLine(handlistPoint, false);
                    }
                }
                i++;
            }
        }
    }

    public void initLine(List<GuidePointModel> listPoint, boolean isVirtual) {
        int i;
        List<LatLng> initList = new ArrayList();
        List<LatLng> tempList = new ArrayList();
        for (i = 0; i < listPoint.size(); i++) {
            initList.add(new LatLng(((GuidePointModel) listPoint.get(i)).getLatitude(), ((GuidePointModel) listPoint.get(i)).getLongitude()));
        }
        if (initList.size() > 1 && initList.size() < 10000) {
            this.polyline = this.aMap.addPolyline(new PolylineOptions().addAll(initList).width(14.0f).setDottedLine(isVirtual).color(SupportMenu.CATEGORY_MASK));
        } else if (initList.size() > 10000) {
            int mp = initList.size() / 10000;
            int mod = initList.size() % 10000;
            int j = 0;
            for (i = 0; i < initList.size(); i++) {
                tempList.add(initList.get(i));
                if (j < mp) {
                    if (tempList.size() == 10000) {
                        this.polyline = this.aMap.addPolyline(new PolylineOptions().addAll(tempList).width(14.0f).setDottedLine(isVirtual).color(SupportMenu.CATEGORY_MASK));
                        tempList.clear();
                        tempList.add(initList.get(i));
                        j++;
                    }
                } else if (mod + mp < 10000) {
                    if (initList.size() == mod + mp) {
                        this.polyline = this.aMap.addPolyline(new PolylineOptions().addAll(tempList).width(14.0f).setDottedLine(isVirtual).color(SupportMenu.CATEGORY_MASK));
                        initList.clear();
                    }
                } else if (tempList.size() == 10000) {
                    this.polyline = this.aMap.addPolyline(new PolylineOptions().addAll(tempList).width(14.0f).setDottedLine(isVirtual).color(SupportMenu.CATEGORY_MASK));
                    tempList.clear();
                }
            }
        }
    }

    public void hanlderMarker(GuidePointModel mGuidePoint, int marker) {
        LatLng latlng = new LatLng(mGuidePoint.getLatitude(), mGuidePoint.getLongitude());
        switch (marker) {
            case 1:
                MarkerOptions markerOption1;
                if (this.is_china) {
                    markerOption1 = new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.start_address).snippet(this.mstartAddress).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_begin)).perspective(true).draggable(true);
                } else {
                    markerOption1 = new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.start_address).snippet(this.mstartAddress).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_begin_en)).perspective(true).draggable(true);
                }
                this.beiginMark = this.aMap.addMarker(markerOption1);
                return;
            case 2:
                MarkerOptions markerOption2;
                if (this.is_china) {
                    markerOption2 = new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.end_address).snippet(this.mendAddress).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_complete)).perspective(true).draggable(true);
                } else {
                    markerOption2 = new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.end_address).snippet(this.mendAddress).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_complete_en)).perspective(true).draggable(true);
                }
                this.endMark = this.aMap.addMarker(markerOption2);
                return;
            case 3:
                Marker cancelMark = this.aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_cancel)).perspective(true).draggable(true));
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        this.mapView.onResume();
    }

    protected void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mapView.onSaveInstanceState(outState);
    }

    public boolean onMarkerClick(Marker marker) {
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        return false;
    }

    public void onClick(View v) {
        this.fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        switch (v.getId()) {
            case R.id.back:
                finish();
                return;
            case R.id.gps_share:
                this.mLayoutGpsShare.setVisibility(0);
                return;
            case R.id.share_weixin:
                this.mLayoutGpsShare.setVisibility(8);
                getMapScreenShot();
                this.mHandler.sendEmptyMessageDelayed(1201, 1000);
                return;
            case R.id.share_quan:
                this.mLayoutGpsShare.setVisibility(8);
                getMapScreenShot();
                this.mHandler.sendEmptyMessageDelayed(1202, 1000);
                return;
            case R.id.share_qq:
                this.mLayoutGpsShare.setVisibility(8);
                getMapScreenShot();
                this.mHandler.sendEmptyMessageDelayed(1203, 1000);
                return;
            case R.id.share_more:
                this.mLayoutGpsShare.setVisibility(8);
                getMapScreenShot();
                this.mHandler.sendEmptyMessageDelayed(SHARE_TO_MORE, 1000);
                return;
            case R.id.gps_igbtn_zoom:
                zoomSwitch();
                return;
            case R.id.img_back:
                finish();
                return;
            default:
                return;
        }
    }

    public void getMapScreenShot() {
        this.aMap.getMapScreenShot(this);
    }

    public void onMapScreenShot(Bitmap bit1) {
        Bitmap bitmap;
        if (this.isZoomall) {
            Bitmap bit2 = getScreenShot(this.mview);
            if (bit1 != null) {
                bitmap = Bitmap.createBitmap(bit1.getWidth(), bit1.getHeight() + bit2.getHeight(), Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawBitmap(bit1, 0.0f, 0.0f, null);
                canvas.drawBitmap(bit2, 0.0f, (float) bit1.getHeight(), null);
            } else {
                return;
            }
        }
        bitmap = bit1;
        Tools.saveBitmapToFile(BitmapUtils.compressImage(bitmap, 100, ""), this.fileName);
    }

    public void onMapScreenShot(Bitmap bitmap, int i) {
    }

    private Bitmap getScreenShot(View mScreenshot) {
        Bitmap bmp = Bitmap.createBitmap(mScreenshot.getWidth(), mScreenshot.getHeight(), Config.ARGB_8888);
        mScreenshot.draw(new Canvas(bmp));
        return bmp;
    }

    private void zoomSwitch() {
        if (this.isZoomall) {
            this.mLayoutDetailData.setVisibility(8);
            this.mBtnZoom.setImageResource(R.drawable.gps_zoomin);
            this.isZoomall = false;
            return;
        }
        this.mLayoutDetailData.setVisibility(0);
        this.mBtnZoom.setImageResource(R.drawable.gps_zoomout);
        this.isZoomall = true;
    }

    public void onMapLoaded() {
        if (this.gpspoints != null) {
            LatLng startGuide = new LatLng(this.gpspoints[0], this.gpspoints[1]);
            this.aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder().include(startGuide).include(new LatLng(this.gpspoints[2], this.gpspoints[3])).build(), 50));
        }
    }

    public void onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                if (this.mHandler.hasMessages(1200)) {
                    this.mHandler.removeMessages(1200);
                    return;
                }
                return;
            case 1:
                if (this.gpspoints != null) {
                    this.mHandler.sendEmptyMessageDelayed(1200, 3000);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onCameraChange(CameraPosition arg0) {
    }

    public void onCameraChangeFinish(CameraPosition arg0) {
        this.current_mzoom = this.aMap.getCameraPosition().zoom;
    }

    private void getShareAppStatus() {
        PackageManager pm = getPackageManager();
        Intent filterIntent = new Intent("android.intent.action.SEND", null);
        filterIntent.addCategory("android.intent.category.DEFAULT");
        filterIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        List<ResolveInfo> resolveInfos = new ArrayList();
        resolveInfos.addAll(pm.queryIntentActivities(filterIntent, 0));
        for (int i = 0; i < resolveInfos.size(); i++) {
            String mPackageName = ((ResolveInfo) resolveInfos.get(i)).activityInfo.packageName;
            if (mPackageName.equals(WXApp.WXAPP_PACKAGE_NAME)) {
                this.isWXInstalled = true;
            }
            if (mPackageName.equals("com.sina.weibo")) {
                this.isWBInstalled = true;
            }
            if (mPackageName.equals(Constants.MOBILEQQ_PACKAGE_NAME)) {
                this.isQQInstalled = true;
            }
        }
    }

    private void shareToQQ(String fileName) {
        ComponentName cp = new ComponentName(Constants.MOBILEQQ_PACKAGE_NAME, "com.tencent.mobileqq.activity.JumpActivity");
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setComponent(cp);
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(Tools.getScreenShot(fileName))));
        startActivity(intent);
    }

    private void share2weibo(String content, String picpath) {
        new Thread(new ShareTask(this, picpath, content, this.mRequestListener)).start();
        if (this.mPopupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
    }
}
