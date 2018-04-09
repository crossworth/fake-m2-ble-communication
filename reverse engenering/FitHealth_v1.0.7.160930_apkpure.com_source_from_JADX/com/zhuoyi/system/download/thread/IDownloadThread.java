package com.zhuoyi.system.download.thread;

public interface IDownloadThread {
    void sendProgressMsg();

    void sendStopMsg(int i);
}
