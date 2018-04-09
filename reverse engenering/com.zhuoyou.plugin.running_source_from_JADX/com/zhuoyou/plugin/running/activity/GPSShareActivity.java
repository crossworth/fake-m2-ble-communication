package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds.Builder;
import com.droi.greendao.bean.GpsPointBean;
import com.droi.greendao.bean.GpsSportBean;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.database.GpsPointHelper;
import com.zhuoyou.plugin.running.database.GpsSportHelper;
import com.zhuoyou.plugin.running.tools.BMapUtils;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.TouchImageView;
import java.util.LinkedList;
import java.util.List;

public class GPSShareActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!GPSShareActivity.class.desiredAssertionStatus());
    public static final String KEY_SPORT_BEAN_ID = "key_sport_bean_id";
    private ShareAction action;
    private BaiduMap bMap = null;
    private GpsSportBean bean;
    private ImageView imgFacebook;
    private ImageView imgInstagram;
    private TouchImageView imgLocus;
    private ImageView imgMoments;
    private ImageView imgQQ;
    private ImageView imgQzone;
    private ImageView imgTwitter;
    private ImageView imgWechat;
    private ImageView imgWeibo;
    private ImageView imgWhatsApp;
    private boolean isMapLoaded;
    private LinearLayout layoutContent;
    private MapView mMapView = null;
    private String message;
    private OnMapLoadedCallback onMapLoadedCallback = new C17733();
    private UMShareListener shareListener = new C17722();
    private TextView tvDistance;
    private TextView tvDuration;

    class C17711 implements SnapshotReadyCallback {
        C17711() {
        }

        public void onSnapshotReady(Bitmap bitmap) {
            GPSShareActivity.this.action.withMedia(new UMImage(GPSShareActivity.this, GPSShareActivity.this.getSharePicture(bitmap)));
            GPSShareActivity.this.action.share();
        }
    }

    class C17722 implements UMShareListener {
        C17722() {
        }

        public void onResult(SHARE_MEDIA share_media) {
            Log.i("zhuqichao", "success:" + share_media);
            if (share_media == SHARE_MEDIA.TWITTER) {
                Tools.makeToast("Share success!");
            }
        }

        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.i("zhuqichao", "error:" + share_media);
            if (share_media == SHARE_MEDIA.TWITTER) {
                Tools.makeToast("Share fail!");
            }
        }

        public void onCancel(SHARE_MEDIA share_media) {
            Log.i("zhuqichao", "cancel:" + share_media);
            if (share_media == SHARE_MEDIA.TWITTER) {
                Tools.makeToast("Share cancel!");
            }
        }
    }

    class C17733 implements OnMapLoadedCallback {
        C17733() {
        }

        public void onMapLoaded() {
            GPSShareActivity.this.showSportLine();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_gps_share);
        this.bean = (GpsSportBean) GpsSportHelper.getBeanDao().load(getIntent().getStringExtra("key_sport_bean_id"));
        setUpMap();
        initView();
        initData();
    }

    private void initView() {
        this.imgQQ = (ImageView) findViewById(C1680R.id.share_qq);
        this.imgQzone = (ImageView) findViewById(C1680R.id.share_qzone);
        this.imgWechat = (ImageView) findViewById(C1680R.id.share_wechat);
        this.imgMoments = (ImageView) findViewById(C1680R.id.share_moments);
        this.imgWeibo = (ImageView) findViewById(C1680R.id.share_weibo);
        this.imgFacebook = (ImageView) findViewById(C1680R.id.share_facebook);
        this.imgTwitter = (ImageView) findViewById(C1680R.id.share_twitter);
        this.imgInstagram = (ImageView) findViewById(C1680R.id.share_instagram);
        this.imgWhatsApp = (ImageView) findViewById(C1680R.id.share_whatsapp);
        this.imgLocus = (TouchImageView) findViewById(C1680R.id.img_locus);
        this.tvDistance = (TextView) findViewById(C1680R.id.tv_distance);
        this.tvDuration = (TextView) findViewById(C1680R.id.tv_duration);
        initShareButton();
        this.layoutContent = (LinearLayout) findViewById(C1680R.id.layout_content);
        umengShareDialogTitle();
        this.layoutContent.setDrawingCacheEnabled(true);
        this.action = new ShareAction(this).setCallback(this.shareListener).withTitle(getString(C1680R.string.app_name)).withTargetUrl(Tools.DOWNLOAD_SITE);
    }

    private void initShareButton() {
        if (getResources().getConfiguration().locale.getCountry().equals("CN")) {
            this.imgFacebook.setVisibility(8);
            this.imgTwitter.setVisibility(8);
            this.imgInstagram.setVisibility(8);
            this.imgWhatsApp.setVisibility(8);
            return;
        }
        this.imgQQ.setVisibility(8);
        this.imgQzone.setVisibility(8);
        this.imgWechat.setVisibility(8);
        this.imgMoments.setVisibility(8);
    }

    private void initData() {
        Tools.displayImage(this.imgLocus, GpsUtils.getLocusUri(this.bean.getId()), true);
        this.tvDistance.setText(GpsUtils.getDisStrNoUnit(this.bean.getDistance()));
        this.tvDuration.setText(GpsUtils.getDuration(this.bean.getDuration()));
        this.message = getString(C1680R.string.share_gps_sport, new Object[]{this.tvDuration.getText(), this.tvDistance.getText()});
        this.action.withText(this.message + getString(C1680R.string.share_suffix));
    }

    private void setUpMap() {
        this.mMapView = (MapView) findViewById(C1680R.id.map);
        if ($assertionsDisabled || this.mMapView != null) {
            this.bMap = this.mMapView.getMap();
            this.bMap.setIndoorEnable(false);
            this.bMap.setOnMapLoadedCallback(this.onMapLoadedCallback);
            this.mMapView.showZoomControls(false);
            UiSettings uiSettings = this.bMap.getUiSettings();
            uiSettings.setCompassEnabled(false);
            uiSettings.setAllGesturesEnabled(false);
            return;
        }
        throw new AssertionError();
    }

    public void onClick(View v) {
        if (this.isMapLoaded) {
            switch (v.getId()) {
                case C1680R.id.share_facebook:
                    this.action.setPlatform(SHARE_MEDIA.FACEBOOK);
                    break;
                case C1680R.id.share_twitter:
                    this.action.setPlatform(SHARE_MEDIA.TWITTER);
                    this.action.withText(this.message + Tools.DOWNLOAD_SITE);
                    break;
                case C1680R.id.share_instagram:
                    this.action.setPlatform(SHARE_MEDIA.INSTAGRAM);
                    break;
                case C1680R.id.share_whatsapp:
                    this.action.setPlatform(SHARE_MEDIA.WHATSAPP);
                    break;
                case C1680R.id.share_qq:
                    this.action.setPlatform(SHARE_MEDIA.QQ);
                    break;
                case C1680R.id.share_qzone:
                    this.action.setPlatform(SHARE_MEDIA.QZONE);
                    break;
                case C1680R.id.share_wechat:
                    this.action.setPlatform(SHARE_MEDIA.WEIXIN);
                    break;
                case C1680R.id.share_moments:
                    this.action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                    break;
                case C1680R.id.share_weibo:
                    this.action.setPlatform(SHARE_MEDIA.SINA);
                    break;
            }
            getMapScreenShot();
            return;
        }
        Tools.makeToast((int) C1680R.string.share_wait_map);
    }

    private Bitmap getSharePicture(Bitmap bitmap) {
        Bitmap back = this.layoutContent.getDrawingCache();
        new Canvas(back).drawBitmap(bitmap, 0.0f, 0.0f, null);
        bitmap.recycle();
        return back;
    }

    private void getMapScreenShot() {
        if (this.mMapView.getVisibility() == 8) {
            this.action.withMedia(new UMImage((Context) this, this.layoutContent.getDrawingCache()));
            this.action.share();
            return;
        }
        this.bMap.snapshot(new C17711());
    }

    private void umengShareDialogTitle() {
        ProgressDialog dialog = Tools.getProgressDialog(this);
        dialog.setMessage(getString(C1680R.string.start_share));
        Config.dialog = dialog;
    }

    private void showSportLine() {
        List<GpsPointBean> src = GpsPointHelper.loadAscBySportId(this.bean.getId());
        LatLng[] bounds = GpsPointHelper.getBoundPoints((List) src);
        LinkedList<LatLng> data = new LinkedList();
        for (GpsPointBean bean : src) {
            data.add(new LatLng(bean.getLatitude(), bean.getLongitude()));
        }
        if (bounds != null) {
            Builder latLngBounds = new Builder();
            latLngBounds.include(bounds[0]).include(bounds[1]).include(bounds[2]).include(bounds[3]);
            changeCamera(MapStatusUpdateFactory.newLatLngBounds(latLngBounds.build(), this.mMapView.getWidth() - 50, this.mMapView.getHeight() - 50), false);
        }
        if (data.size() > 0) {
            this.bMap.clear();
            BMapUtils.addMakerToMap(this.bMap, C1680R.drawable.point_begin, (LatLng) data.getFirst());
            BMapUtils.addLineToMap(this.bMap, data, false);
            BMapUtils.addMakerToMap(this.bMap, C1680R.drawable.point_complete, (LatLng) data.getLast());
        } else {
            this.imgLocus.setVisibility(0);
            this.mMapView.setVisibility(8);
        }
        this.isMapLoaded = true;
    }

    private void changeCamera(MapStatusUpdate update, boolean animated) {
        if (animated) {
            this.bMap.animateMapStatus(update, 1000);
        } else {
            this.bMap.setMapStatus(update);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    protected void onPause() {
        super.onPause();
        this.mMapView.onPause();
    }

    protected void onResume() {
        super.onResume();
        this.mMapView.onResume();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mMapView.onSaveInstanceState(outState);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mMapView.onDestroy();
    }
}
