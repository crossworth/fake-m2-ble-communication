package com.droi.btlib.plugin;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.droi.btlib.C0687R;

public class FindPhoneDialog extends Activity implements OnClickListener {
    public static String ACTION_FIND_PHONE = "droi_find_phone";
    private static Activity Instance = null;
    private MediaPlayer media = null;

    public class MusicThread extends Thread {
        public void run() {
            if (FindPhoneDialog.this.media == null) {
                FindPhoneDialog.this.media = MediaPlayer.create(FindPhoneDialog.this, C0687R.raw.find_phone);
            }
            if (FindPhoneDialog.this.media != null && !FindPhoneDialog.this.media.isPlaying()) {
                FindPhoneDialog.this.media.start();
                FindPhoneDialog.this.media.setLooping(true);
            }
        }
    }

    public static boolean ifDialogShow() {
        return Instance != null;
    }

    public static void clostSysDialog() {
        if (Instance != null) {
            Instance.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0687R.layout.find_phone_dialog);
        Instance = this;
        findViewById(C0687R.id.btn_find).setOnClickListener(this);
        setFinishOnTouchOutside(false);
        new MusicThread().start();
        setParams();
        ((TextView) findViewById(C0687R.id.sys_tv)).setText(getIntent().getStringExtra("phone_lost_message"));
    }

    private void setParams() {
        Window window = getWindow();
        LayoutParams lp = window.getAttributes();
        lp.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(lp);
    }

    public void onClick(View v) {
        finish();
    }

    protected void onDestroy() {
        sendBroadcast(new Intent(ACTION_FIND_PHONE));
        super.onDestroy();
        Instance = null;
        stopMusic();
    }

    public void stopMusic() {
        if (this.media != null) {
            if (this.media.isPlaying()) {
                this.media.stop();
            }
            this.media.release();
            this.media = null;
        }
    }
}
