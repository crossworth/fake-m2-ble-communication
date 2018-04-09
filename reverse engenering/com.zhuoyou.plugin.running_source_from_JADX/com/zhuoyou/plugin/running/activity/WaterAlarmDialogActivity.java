package com.zhuoyou.plugin.running.activity;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.receiver.AlarmReceiver;
import com.zhuoyou.plugin.running.view.MyAlertDialog;

public class WaterAlarmDialogActivity extends BaseActivity {
    public static int[] ids = new int[]{C1680R.string.drink_notify_body1, C1680R.string.drink_notify_body2, C1680R.string.drink_notify_body3, C1680R.string.drink_notify_body4, C1680R.string.drink_notify_body5, C1680R.string.drink_notify_body6, C1680R.string.drink_notify_body7, C1680R.string.drink_notify_body8, C1680R.string.drink_notify_body_default};
    private static MediaPlayer media = null;
    private static WakeLock timerWakeLock;
    private KeyguardLock mkeyguardLock;

    class C18451 implements OnDismissListener {
        C18451() {
        }

        public void onDismiss(DialogInterface dialog) {
            WaterAlarmDialogActivity.this.finish();
        }
    }

    private static class mThread extends Thread {
        private mThread() {
        }

        public void run() {
            if (!(WaterAlarmDialogActivity.media == null || WaterAlarmDialogActivity.media.isPlaying())) {
                WaterAlarmDialogActivity.media.start();
                WaterAlarmDialogActivity.media.setLooping(true);
            }
            try {
                sleep(80000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (WaterAlarmDialogActivity.media != null) {
                if (WaterAlarmDialogActivity.media.isPlaying()) {
                    WaterAlarmDialogActivity.media.stop();
                }
                WaterAlarmDialogActivity.media.release();
                WaterAlarmDialogActivity.media = null;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.addFlags(6815872);
        win.setType(2003);
        timerWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(268435482, "--");
        timerWakeLock.acquire();
        this.mkeyguardLock = ((KeyguardManager) getSystemService("keyguard")).newKeyguardLock("");
        this.mkeyguardLock.disableKeyguard();
        showAlarmDialog();
    }

    protected void onResume() {
        super.onResume();
        media = MediaPlayer.create(this, C1680R.raw.water_voice);
        new mThread().start();
    }

    private void showAlarmDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.mine_water_reminder);
        dialog.setCancelable(false);
        dialog.setMessage(ids[getIntent().getIntExtra(AlarmReceiver.KEY_WATER_ALARM_ID, 8)]);
        dialog.setLeftButton((int) C1680R.string.string_ok, null);
        dialog.getDialog().setOnDismissListener(new C18451());
        dialog.show();
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
