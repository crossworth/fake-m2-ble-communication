package com.droi.btlib.plugin;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;
import com.droi.btlib.C0687R;
import com.droi.btlib.service.BluetoothManager;
import com.droi.btlib.service.BtManagerService;
import com.tencent.open.SocialConstants;
import com.umeng.facebook.internal.FacebookRequestErrorClassification;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RemoteCamera extends AppCompatActivity {
    private static final int AUTO_EXIT_CAMERA = 1;
    public static final String mActionCapture = "com.zhoyou.plugin.autocamera.capture";
    public static final String mActionExit = "com.zhoyou.plugin.autocamera.exit.RemoteCamera";
    private static final String mCapturePath = "/DCIM/autocamera/";
    private static RemoteCamera mInstance;
    private ImageView camera_change;
    private boolean isBack = false;
    private boolean isCmd;
    private boolean isExit = false;
    private boolean isOpen = false;
    private boolean isPreview = false;
    private ImageView light;
    private BluetoothManager mBluetoothManager;
    private Camera mCamera;
    private Handler mHandler = new C06997();
    private BroadcastReceiver mReceive = new C06954();
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    public PictureCallback myjpegCallback = new C06975();
    private String picPath = "";
    private Size pictureSize;
    private Size previewSize;

    class C06911 implements OnClickListener {
        C06911() {
        }

        public void onClick(View v) {
            RemoteCamera.this.isBack = !RemoteCamera.this.isBack;
            RemoteCamera.this.releaseCamera();
            RemoteCamera.this.initCamera();
            CameraUtil2.initParams(RemoteCamera.this.mCamera, RemoteCamera.this);
        }
    }

    class C06922 implements OnClickListener {
        C06922() {
        }

        public void onClick(View v) {
            if (RemoteCamera.this.isBack) {
                RemoteCamera.this.isOpen = !RemoteCamera.this.isOpen;
                RemoteCamera.this.updateLightStatus();
            }
        }
    }

    class C06943 implements Callback {

        class C06931 implements AutoFocusCallback {
            C06931() {
            }

            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    RemoteCamera.this.mCamera.cancelAutoFocus();
                }
            }
        }

        C06943() {
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (RemoteCamera.this.mCamera != null) {
                RemoteCamera.this.mCamera.autoFocus(new C06931());
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            RemoteCamera.this.initCamera();
            if (RemoteCamera.this.mCamera != null) {
                CameraUtil2.initParams(RemoteCamera.this.mCamera, RemoteCamera.this);
                LayoutParams laParams = (LayoutParams) RemoteCamera.this.mSurfaceView.getLayoutParams();
                int width = RemoteCamera.this.getScreenWH().widthPixels;
                int height = RemoteCamera.this.getScreenWH().heightPixels;
                Log.i("zhuqichao", "width = " + width + " height = " + height);
                int previewWidth = width;
                int previewHeight = height;
                RemoteCamera.this.previewSize = RemoteCamera.this.mCamera.getParameters().getPreviewSize();
                RemoteCamera.this.pictureSize = RemoteCamera.this.mCamera.getParameters().getPictureSize();
                if (RemoteCamera.this.previewSize != null) {
                    previewWidth = RemoteCamera.this.previewSize.height;
                    previewHeight = RemoteCamera.this.previewSize.width;
                }
                Log.i("zhuqichao", "previewWidth = " + previewWidth + " previewHeight = " + previewHeight);
                if (width * previewHeight > height * previewWidth) {
                    int scaledChildWidth = (previewWidth * height) / previewHeight;
                    Log.i("caixinxin", "scaledChildWidth : " + scaledChildWidth);
                    laParams.width = scaledChildWidth;
                    laParams.height = height;
                    RemoteCamera.this.mSurfaceView.setLayoutParams(laParams);
                    RemoteCamera.this.mSurfaceView.layout((width - scaledChildWidth) / 2, 0, (width + scaledChildWidth) / 2, height);
                    return;
                }
                int scaledChildHeight = (previewHeight * width) / previewWidth;
                Log.i("caixinxin", "scaledChildHeight : " + scaledChildHeight);
                laParams.width = width;
                laParams.height = scaledChildHeight;
                RemoteCamera.this.mSurfaceView.setLayoutParams(laParams);
                RemoteCamera.this.mSurfaceView.layout(0, (height - scaledChildHeight) / 2, width, (height + scaledChildHeight) / 2);
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            RemoteCamera.this.releaseCamera();
        }
    }

    class C06954 extends BroadcastReceiver {
        C06954() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RemoteCamera.mActionCapture)) {
                RemoteCamera.this._doCapture();
            } else if (intent.getAction().equals(RemoteCamera.mActionExit)) {
                Log.e("gchk", "exit");
                RemoteCamera.this.isExit = intent.getBooleanExtra(SocialConstants.TYPE_REACTIVE, false);
                RemoteCamera.this.isPreview = false;
                RemoteCamera.this.finish();
            }
        }
    }

    class C06975 implements PictureCallback {

        class C06961 implements OnScanCompletedListener {
            C06961() {
            }

            public void onScanCompleted(String path, Uri uri) {
                RemoteCamera.this.sendBroadcast(new Intent("android.hardware.action.NEW_PICTURE", uri));
                RemoteCamera.this.sendBroadcast(new Intent("com.android.camera.NEW_PICTURE", uri));
            }
        }

        C06975() {
        }

        @SuppressLint({"SimpleDateFormat"})
        public void onPictureTaken(byte[] data, Camera camera) {
            IOException e;
            Log.e("gchk", "take picture success");
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (RemoteCamera.ExistSDCard()) {
                String sd = RemoteCamera.getSDPath();
                Log.v("gchk", "sd() = " + sd);
                RemoteCamera.newFolder(sd + RemoteCamera.mCapturePath);
                RemoteCamera.this.picPath = sd + RemoteCamera.mCapturePath + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
                Log.e("gchk", "picPath = " + RemoteCamera.this.picPath);
                try {
                    FileOutputStream outStream = new FileOutputStream(new File(RemoteCamera.this.picPath));
                    FileOutputStream fileOutputStream;
                    try {
                        Log.e("gchk", "save pic");
                        Matrix matrix = new Matrix();
                        if (!RemoteCamera.this.isBack) {
                            matrix.postRotate(270.0f);
                        }
                        Bitmap bm = Bitmap.createBitmap(1200, (bitmap.getHeight() * 1200) / bitmap.getWidth(), Config.ARGB_8888);
                        matrix.setScale(((float) bm.getWidth()) / ((float) bitmap.getWidth()), ((float) bm.getHeight()) / ((float) bitmap.getHeight()));
                        new Canvas(bm).drawBitmap(bitmap, matrix, null);
                        bm.compress(CompressFormat.JPEG, 40, outStream);
                        outStream.close();
                        Toast.makeText(RemoteCamera.this, RemoteCamera.this.picPath, 0).show();
                        ExifInterface exifInterface = new ExifInterface(RemoteCamera.this.picPath);
                        exifInterface.setAttribute("Orientation", String.valueOf(6));
                        exifInterface.saveAttributes();
                        if (RemoteCamera.hasKitkat()) {
                            MediaScannerConnection.scanFile(RemoteCamera.mInstance, new String[]{sd}, new String[]{"image/*"}, new C06961());
                            RemoteCamera.scanPhotos(RemoteCamera.this.picPath, RemoteCamera.mInstance);
                        } else {
                            RemoteCamera.this.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                        }
                        fileOutputStream = outStream;
                    } catch (IOException e2) {
                        e = e2;
                        fileOutputStream = outStream;
                        e.printStackTrace();
                        RemoteCamera.this.mBluetoothManager.sendCustomCmd(81, "capture", RemoteCamera.getTag());
                        RemoteCamera.this.mBluetoothManager.sendCustomCmd(81, "capture");
                        camera.startPreview();
                        RemoteCamera.this.resetTime();
                    }
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                    RemoteCamera.this.mBluetoothManager.sendCustomCmd(81, "capture", RemoteCamera.getTag());
                    RemoteCamera.this.mBluetoothManager.sendCustomCmd(81, "capture");
                    camera.startPreview();
                    RemoteCamera.this.resetTime();
                }
                RemoteCamera.this.mBluetoothManager.sendCustomCmd(81, "capture");
            } else {
                RemoteCamera.this.mBluetoothManager.sendCustomCmd(81, "capture", RemoteCamera.getTag());
            }
            camera.startPreview();
            RemoteCamera.this.resetTime();
        }
    }

    class C06986 implements AutoFocusCallback {
        C06986() {
        }

        public void onAutoFocus(boolean success, Camera camera) {
            Log.e("gchk", "__________15  ");
            RemoteCamera.this.mCamera.takePicture(null, null, RemoteCamera.this.myjpegCallback);
        }
    }

    class C06997 extends Handler {
        C06997() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                RemoteCamera.this.isPreview = false;
                RemoteCamera.this.finish();
            }
        }
    }

    public static RemoteCamera getInstance() {
        return mInstance;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().addFlags(1024);
        setContentView(C0687R.layout.preview);
        this.mBluetoothManager = BtManagerService.mBluetoothManager;
        mInstance = this;
        Log.i("gchk", "RemoteCamera screen createed mInstance = " + mInstance);
        Intent intent = getIntent();
        if (intent != null) {
            this.isCmd = intent.getBooleanExtra("isCmd", false);
        }
        registerBc();
        if (CameraPlugTools.isScreenLocked(this)) {
            finish();
        }
    }

    private void initCameraFirst() {
        this.mSurfaceView = (SurfaceView) findViewById(C0687R.id.sView);
        this.mSurfaceHolder = this.mSurfaceView.getHolder();
        this.camera_change = (ImageView) findViewById(C0687R.id.camera_change);
        this.light = (ImageView) findViewById(C0687R.id.light);
        this.camera_change.setOnClickListener(new C06911());
        this.light.setOnClickListener(new C06922());
        this.mSurfaceHolder.addCallback(new C06943());
        this.mSurfaceHolder.setType(3);
    }

    private void releaseCamera() {
        if (this.mCamera != null) {
            if (this.isPreview) {
                this.mCamera.stopPreview();
                this.isPreview = false;
            }
            Log.e("gchk", "release camera");
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    private void registerBc() {
        IntentFilter intentF = new IntentFilter();
        intentF.addAction(mActionCapture);
        intentF.addAction(mActionExit);
        registerReceiver(this.mReceive, intentF);
    }

    private void unRegisterBc() {
        unregisterReceiver(this.mReceive);
    }

    public void onPause() {
        super.onPause();
        Log.e("gchk", "mInstance is null");
        finish();
    }

    public void onResume() {
        super.onResume();
        Log.e("gchk", "mInstance is create");
        CameraPlugTools.saveDataString(this, "screen", "RemoteCamera");
        initCameraFirst();
    }

    public void onDestroy() {
        releaseCamera();
        unRegisterBc();
        CameraPlugTools.saveDataString(this, "screen", FacebookRequestErrorClassification.KEY_OTHER);
        if (!this.isExit) {
            this.mBluetoothManager.sendCustomCmd(84, "exit");
        }
        super.onDestroy();
    }

    private void initCamera() {
        int i = 0;
        if (!this.isPreview) {
            CameraInfo cameraInfo = new CameraInfo();
            int cameraCount = Camera.getNumberOfCameras();
            Log.e("gchk", "cameraCount =" + cameraCount);
            if (cameraCount <= 0) {
                Log.e("gchk", "did not find pre camera");
            } else if (cameraCount > 1 || this.isBack) {
                Log.e("gchk", "__________4  ");
                Log.e("gchk", "__________5  cameraInfo.facing = " + cameraInfo.facing);
                Log.e("gchk", "__________6  ");
                try {
                    if (this.isBack) {
                        if (!this.isBack) {
                            i = 1;
                        }
                        this.mCamera = Camera.open(i);
                        this.mCamera.setDisplayOrientation(90);
                        updateLightStatus();
                    } else {
                        this.light.setImageResource(C0687R.drawable.camera_light_off);
                        if (!this.isBack) {
                            i = 1;
                        }
                        this.mCamera = Camera.open(i);
                        this.mCamera.setDisplayOrientation(90);
                        Parameters par = this.mCamera.getParameters();
                        par.setRotation(180);
                        par.set("rotation", 180);
                        this.mCamera.setParameters(par);
                    }
                    Log.e("gchk", "__________7  ");
                } catch (RuntimeException e) {
                    Log.e("gchk", "__________8  ");
                    e.printStackTrace();
                }
            } else {
                Log.e("gchk", "no front");
            }
        }
        if (this.mCamera == null || this.isPreview) {
            if (this.isCmd) {
                Log.e("gchk", "preview failed");
                this.mBluetoothManager.sendCustomCmd(83, "entryPreview", getTag());
            }
            finish();
            return;
        }
        Log.e("gchk", "__________9  ");
        try {
            this.mCamera.setPreviewDisplay(this.mSurfaceHolder);
        } catch (IOException e2) {
            Log.e("gchk", "__________10  ");
            e2.printStackTrace();
        }
        this.mCamera.startPreview();
        Log.e("gchk", "__________11  ");
        this.isPreview = true;
        Log.e("gchk", "__________12  ");
        if (this.isCmd) {
            Log.e("gchk", "preview succssed");
            this.mBluetoothManager.sendCustomCmd(83, "entryPreview");
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), StatisticConfig.MIN_UPLOAD_INTERVAL);
    }

    public static boolean hasKitkat() {
        return VERSION.SDK_INT >= 19;
    }

    public static void scanPhotos(String filePath, Context context) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(intent);
    }

    public void _doCapture() {
        if (this.mCamera != null) {
            Log.i("gchk", "camera auto camera");
            this.mCamera.autoFocus(new C06986());
            return;
        }
        Log.i("gchk", "camera is null");
        this.mBluetoothManager.sendCustomCmd(81, "capture", getTag());
    }

    public static void doCapture() {
        Log.i("gchk", "capture doCapture");
        if (mInstance != null) {
            Log.i("gchk", "capture __doCapture");
            mInstance._doCapture();
            return;
        }
        Log.i("gchk", "RemoteCamera is null");
    }

    public static boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static String getSDPath() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    public static void newFolder(String folderPath) {
        Log.v("gchk", "newFolder() = " + folderPath);
        if (folderPath != null && !folderPath.equals("")) {
            try {
                File myFilePath = new File(folderPath);
                if (myFilePath.exists()) {
                    Log.v("gchk", "folderPath is exists " + folderPath);
                    return;
                }
                boolean flag = myFilePath.mkdirs();
                Log.v("gchk", "newFolder() = RET" + flag);
                if (!flag) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static char[] getTag() {
        return new char[]{'!', 'ÿ', 'ÿ', 'ÿ'};
    }

    private void resetTime() {
        this.mHandler.removeMessages(1);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), StatisticConfig.MIN_UPLOAD_INTERVAL);
    }

    private DisplayMetrics getScreenWH() {
        return getResources().getDisplayMetrics();
    }

    private void updateLightStatus() {
        Parameters parameters = this.mCamera.getParameters();
        if (this.isOpen && this.isBack) {
            this.light.setImageResource(C0687R.drawable.camera_light_on);
            parameters.setFlashMode("on");
            this.isOpen = true;
        } else {
            this.light.setImageResource(C0687R.drawable.camera_light_off);
            parameters.setFlashMode("off");
            this.isOpen = false;
        }
        this.mCamera.setParameters(parameters);
    }
}
