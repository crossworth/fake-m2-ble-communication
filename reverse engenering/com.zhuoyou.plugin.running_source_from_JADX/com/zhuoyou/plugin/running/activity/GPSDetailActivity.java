package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds.Builder;
import com.droi.greendao.bean.GpsPointBean;
import com.droi.greendao.bean.GpsSportBean;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiObject;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.database.GpsPointHelper;
import com.zhuoyou.plugin.running.database.GpsSportHelper;
import com.zhuoyou.plugin.running.tools.BMapUtils;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyActionBar;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.TouchImageView;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class GPSDetailActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!GPSDetailActivity.class.desiredAssertionStatus());
    public static final String KEY_SPORT_BEAN_ID = "key_sport_bean_id";
    public static final int RESULT_CODE_DELETE = 257;
    private MyActionBar actionBar;
    private BaiduMap bMap = null;
    private GpsSportBean bean;
    private TouchImageView imgLocus;
    private ImageView imgZoom;
    private MapView mMapView = null;
    private OnMapLoadedCallback onMapLoadedCallback = new C17553();
    private ProgressDialog pdialog;
    private LinearLayout sportDataLayout;
    private TextView tvCal;
    private TextView tvDistance;
    private TextView tvDuration;
    private TextView tvHeart;
    private TextView tvSpeed;

    class C17521 implements OnClickListener {
        C17521() {
        }

        public void onClick(View v) {
            GPSDetailActivity.this.startActivity(new Intent(GPSDetailActivity.this, GPSShareActivity.class).putExtra("key_sport_bean_id", GPSDetailActivity.this.bean.getId()));
        }
    }

    class C17553 implements OnMapLoadedCallback {
        C17553() {
        }

        public void onMapLoaded() {
            GPSDetailActivity.this.showSportLine();
        }
    }

    class C17564 implements MyAlertDialog.OnClickListener {
        C17564() {
        }

        public void onClick(int witch) {
            Tools.makeToast("执行云端删除");
        }
    }

    class C17575 implements MyAlertDialog.OnClickListener {
        C17575() {
        }

        public void onClick(int witch) {
            GPSDetailActivity.this.deleteLocal();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_gps_detail);
        this.bean = (GpsSportBean) GpsSportHelper.getBeanDao().load(getIntent().getStringExtra("key_sport_bean_id"));
        this.pdialog = Tools.getProgressDialog(this);
        setUpMap();
        initView();
        initData();
    }

    private void initView() {
        this.actionBar = (MyActionBar) findViewById(C1680R.id.actionbar);
        this.imgLocus = (TouchImageView) findViewById(C1680R.id.img_locus);
        this.imgZoom = (ImageView) findViewById(C1680R.id.btn_zoom);
        this.sportDataLayout = (LinearLayout) findViewById(C1680R.id.sport_data_layout);
        if ($assertionsDisabled || this.sportDataLayout != null) {
            this.tvDistance = (TextView) findViewById(C1680R.id.tv_distance);
            this.tvDuration = (TextView) findViewById(C1680R.id.tv_duration);
            this.tvSpeed = (TextView) findViewById(C1680R.id.tv_speed);
            this.tvCal = (TextView) findViewById(C1680R.id.tv_cal);
            this.tvHeart = (TextView) findViewById(C1680R.id.tv_heart);
            return;
        }
        throw new AssertionError();
    }

    private void initData() {
        Tools.displayImage(this.imgLocus, GpsUtils.getLocusUri(this.bean.getId()), true);
        this.tvDistance.setText(GpsUtils.getDisStrNoUnit(this.bean.getDistance()));
        this.tvDuration.setText(GpsUtils.getDuration(this.bean.getDuration()));
        this.tvSpeed.setText(GpsUtils.getSpeed(this.bean.getDistance() / ((float) this.bean.getDuration())));
        this.tvCal.setText(GpsUtils.getCalStrNoUnit(this.bean.getCal()));
        this.actionBar = (MyActionBar) findViewById(C1680R.id.actionbar);
        this.actionBar.setOnRightButtonClickListener(new C17521());
    }

    private void setUpMap() {
        this.mMapView = (MapView) findViewById(C1680R.id.map);
        if ($assertionsDisabled || this.mMapView != null) {
            this.bMap = this.mMapView.getMap();
            this.bMap.setOnMapLoadedCallback(this.onMapLoadedCallback);
            this.bMap.setMyLocationEnabled(false);
            this.mMapView.showZoomControls(false);
            return;
        }
        throw new AssertionError();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_zoom:
                updateLayout();
                return;
            case C1680R.id.btn_delete:
                if (this.bean.getSync() == 1) {
                    showSyncDeleteDialog();
                    return;
                } else {
                    showLocalDeleteDialog();
                    return;
                }
            default:
                return;
        }
    }

    private void uploadData() {
        Tools.showProgressDialog(this.pdialog, "正在上传");
        final List<GpsPointBean> list = GpsPointHelper.getUploadList(this.bean.getId());
        DroiObject.saveAllInBackground(list, new DroiCallback<Boolean>() {

            class C17531 implements DroiCallback<Boolean> {
                C17531() {
                }

                public void result(Boolean aBoolean, DroiError droiError) {
                    Tools.hideProgressDialog(GPSDetailActivity.this.pdialog);
                    if (droiError.isOk()) {
                        GPSDetailActivity.this.bean.setSync(1);
                        GpsSportHelper.getBeanDao().insertOrReplace(GPSDetailActivity.this.bean);
                        Log.i("zhuqichao", "objectid=" + GPSDetailActivity.this.bean.getObjectId());
                        return;
                    }
                    Tools.makeToast(Tools.getDroiError(droiError));
                }
            }

            public void result(Boolean aBoolean, DroiError droiError) {
                if (droiError.isOk()) {
                    for (GpsPointBean bean : list) {
                        bean.setSync(1);
                        GpsPointHelper.getBeanDao().insertOrReplace(bean);
                    }
                    if (GPSDetailActivity.this.bean.getSync() == 0) {
                        GPSDetailActivity.this.bean.saveInBackground(new C17531());
                        return;
                    } else {
                        Tools.hideProgressDialog(GPSDetailActivity.this.pdialog);
                        return;
                    }
                }
                Tools.hideProgressDialog(GPSDetailActivity.this.pdialog);
                Tools.makeToast(Tools.getDroiError(droiError));
            }
        });
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
            return;
        }
        this.imgLocus.setVisibility(0);
        this.mMapView.setVisibility(8);
    }

    private void changeCamera(MapStatusUpdate update, boolean animated) {
        if (animated) {
            this.bMap.animateMapStatus(update, 1000);
        } else {
            this.bMap.setMapStatus(update);
        }
    }

    private void showSyncDeleteDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.string_delete_data_sync);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C17564());
        dialog.show();
    }

    private void showLocalDeleteDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.string_delete_data);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C17575());
        dialog.show();
    }

    private void deleteLocal() {
        GpsSportHelper.getBeanDao().deleteByKey(this.bean.getId());
        GpsPointHelper.deleteBySportId(this.bean.getId());
        new File(GpsUtils.getLocusUri(this.bean.getId()).getPath()).delete();
        setResult(257);
        Tools.makeToast((int) C1680R.string.string_delete_complete);
        finish();
    }

    private void updateLayout() {
        if (this.sportDataLayout.getVisibility() == 8) {
            Animation anim = AnimationUtils.loadAnimation(this, C1680R.anim.gps_layout_show);
            anim.setInterpolator(new OvershootInterpolator(1.2f));
            this.sportDataLayout.setAnimation(anim);
            this.imgZoom.setImageResource(C1680R.drawable.gps_zoomout);
            this.sportDataLayout.setVisibility(0);
            return;
        }
        anim = AnimationUtils.loadAnimation(this, C1680R.anim.gps_layout_dismiss);
        anim.setInterpolator(new OvershootInterpolator(1.2f));
        this.sportDataLayout.setAnimation(anim);
        this.imgZoom.setImageResource(C1680R.drawable.gps_zoomin);
        this.sportDataLayout.setVisibility(8);
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
