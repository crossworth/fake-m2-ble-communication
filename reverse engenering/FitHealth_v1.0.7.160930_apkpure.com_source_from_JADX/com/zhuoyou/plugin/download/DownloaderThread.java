package com.zhuoyou.plugin.download;

import android.content.Context;
import android.os.Message;

public class DownloaderThread extends Thread {
    private Context context;
    private Downloader downloader;

    public DownloaderThread(Downloader downloader, Context context) {
        this.downloader = downloader;
        this.context = context;
    }

    public void run() {
        if (this.downloader.init()) {
            Message msg = new Message();
            msg.what = 1;
            msg.arg1 = this.downloader.getNotification_flag();
            this.downloader.downlaod();
            return;
        }
        this.downloader.getmHandler().sendEmptyMessage(4);
    }
}
