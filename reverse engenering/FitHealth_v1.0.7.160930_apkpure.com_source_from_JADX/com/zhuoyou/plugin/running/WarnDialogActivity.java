package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.amap.api.services.core.AMapException;
import com.fithealth.running.R;

public class WarnDialogActivity extends Activity {
    static MediaPlayer media = null;
    private static WakeLock timerWakeLock;
    private TextView NotifyTextView;
    private DisplayMetrics dm;
    int[] ids = new int[]{R.string.drink_notify_body1, R.string.drink_notify_body2, R.string.drink_notify_body3, R.string.drink_notify_body4, R.string.drink_notify_body5, R.string.drink_notify_body6, R.string.drink_notify_body7, R.string.drink_notify_body8, R.string.default_notify_body};
    Button mConfirm;
    KeyguardLock mkeyguardLock;
    KeyguardManager mkeyguardManager;

    class C14211 implements OnClickListener {
        C14211() {
        }

        public void onClick(View arg0) {
            WarnDialogActivity.this.mkeyguardLock.reenableKeyguard();
            if (WarnDialogActivity.media != null) {
                if (WarnDialogActivity.media.isPlaying()) {
                    WarnDialogActivity.media.stop();
                }
                WarnDialogActivity.media.release();
                WarnDialogActivity.media = null;
            }
            WarnDialogActivity.this.finish();
        }
    }

    static class mThread extends Thread {
        mThread() {
        }

        public void run() {
            if (!(WarnDialogActivity.media == null || WarnDialogActivity.media.isPlaying())) {
                WarnDialogActivity.media.start();
                WarnDialogActivity.media.setLooping(true);
            }
            try {
                sleep(80000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (WarnDialogActivity.media != null) {
                if (WarnDialogActivity.media.isPlaying()) {
                    WarnDialogActivity.media.stop();
                }
                WarnDialogActivity.media.release();
                WarnDialogActivity.media = null;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int srcId = getIntent().getIntExtra("key", 8);
        Window win = getWindow();
        win.addFlags(6815872);
        win.setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
        timerWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(268435482, "--");
        timerWakeLock.acquire();
        this.mkeyguardManager = (KeyguardManager) getSystemService("keyguard");
        this.mkeyguardLock = this.mkeyguardManager.newKeyguardLock("");
        this.mkeyguardLock.disableKeyguard();
        setContentView(R.layout.water_drink_dialog);
        this.NotifyTextView = (TextView) findViewById(R.id.notify_body);
        this.NotifyTextView.setText(getResources().getString(this.ids[srcId]));
        this.mConfirm = (Button) findViewById(R.id.confirm);
        this.mConfirm.setOnClickListener(new C14211());
    }

    protected void onResume() {
        super.onResume();
        media = MediaPlayer.create(this, R.raw.water_voice);
        new mThread().start();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (media != null) {
            if (media.isPlaying()) {
                media.stop();
            }
            media.release();
            media = null;
        }
        if (timerWakeLock != null) {
            timerWakeLock.release();
            timerWakeLock = null;
        }
        this.mkeyguardLock.reenableKeyguard();
    }

    protected void onPause() {
        super.onPause();
        if (media != null) {
            if (media.isPlaying()) {
                media.stop();
            }
            media.release();
            media = null;
        }
    }

    protected void onStop() {
        super.onStop();
        if (media != null) {
            if (media.isPlaying()) {
                media.stop();
            }
            media.release();
            media = null;
        }
        if (timerWakeLock != null) {
            timerWakeLock.release();
            timerWakeLock = null;
        }
        this.mkeyguardLock.reenableKeyguard();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (media != null) {
            if (media.isPlaying()) {
                media.stop();
            }
            media.release();
            media = null;
        }
    }
}
