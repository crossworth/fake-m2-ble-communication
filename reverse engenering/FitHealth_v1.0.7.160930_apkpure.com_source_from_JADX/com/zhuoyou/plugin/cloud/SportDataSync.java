package com.zhuoyou.plugin.cloud;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.zhuoyou.plugin.running.HomePageFragment;
import com.zhuoyou.plugin.running.MainService;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.selfupdate.TerminalInfo;
import java.io.File;
import java.util.HashMap;

public class SportDataSync {
    private static final String TAG = "SportDataSync";
    static Context mContext;
    Runnable dowmRunnable = new C12232();
    private GPSDataSync gpsSync;
    TerminalInfo info;
    private boolean is_debug = true;
    private Handler mHandler;
    private Cvshelper mcvs;
    String openid = "";
    private String path = null;
    int result = 0;
    Runnable upRunnable = new C12221();

    class C12221 implements Runnable {
        C12221() {
        }

        public void run() {
            SportDataSync.this.path = SportDataSync.this.mcvs.GetDir();
            String up_zip = "Running_up.zip";
            StatFs sf = new StatFs(SportDataSync.this.mcvs.GetDir());
            if (((((long) sf.getAvailableBlocks()) * ((long) sf.getBlockSize())) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID >= 10) {
                SportDataSync.this.deleteSDCardFolder(new File(SportDataSync.this.mcvs.GetDir()));
                SportDataSync.this.mcvs.ExportDateCSVBYTYPE(0);
                SportDataSync.this.mcvs.ExportDateCSVBYTYPE(1);
                SportDataSync.this.mcvs.ExportDateCSVBYTYPE(2);
                SportDataSync.this.mcvs.ExportDateCSVBYTYPE(3);
                SportDataSync.this.gpsSync.createGSPFile(SportDataSync.this.mcvs.GetDir());
                try {
                    SportDataSync.this.mcvs.ExportCVSToZip(up_zip);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (HomePageFragment.mHandler != null) {
                    Message message = new Message();
                    message.what = 4;
                    message.obj = "30%";
                    HomePageFragment.mHandler.sendMessage(message);
                }
                SportDataSync.this.result = SportDataSync.this.update_file(SportDataSync.this.path, up_zip);
                Log.i("lsj", "upRunnable result =" + SportDataSync.this.result);
                if (SportDataSync.this.result == 1) {
                    Log.i("lsj", "update success");
                    Log.d(SportDataSync.TAG, "update success");
                    SportDataSync.this.mcvs.UpdataLocalDate();
                    SportDataSync.this.gpsSync.UpdataLocalDate();
                    Message msg = SportDataSync.this.mHandler.obtainMessage();
                    msg.what = 200;
                    msg.arg1 = NetMsgCode.postSportInfo;
                    SportDataSync.this.mHandler.sendMessage(msg);
                    return;
                }
                Log.i("lsj", "update failed");
                Log.d(SportDataSync.TAG, "update failed");
                SportDataSync.this.mHandler.sendEmptyMessage(110011);
            }
        }
    }

    class C12232 implements Runnable {
        C12232() {
        }

        public void run() {
            SportDataSync.this.path = SportDataSync.this.mcvs.GetDir();
            String down_zip = "Running_down.zip";
            StatFs sf = new StatFs(SportDataSync.this.mcvs.GetDir());
            if (((((long) sf.getAvailableBlocks()) * ((long) sf.getBlockSize())) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID >= 10) {
                SportDataSync.this.result = SportDataSync.this.download_file(SportDataSync.this.path, down_zip);
                Log.d(SportDataSync.TAG, "result:" + SportDataSync.this.result);
                if (SportDataSync.this.result == 3) {
                    Log.i("lsj", "download  success");
                    Log.d(SportDataSync.TAG, "download  success");
                    try {
                        SportDataSync.this.mcvs.CVSUnzipFile(SportDataSync.this.path + "/" + down_zip, SportDataSync.this.path);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(SportDataSync.TAG, "download failed");
                    }
                    if (HomePageFragment.mHandler != null) {
                        Message message = new Message();
                        message.what = 4;
                        message.obj = "100%";
                        HomePageFragment.mHandler.sendMessage(message);
                    }
                    SportDataSync.this.mcvs.ImportCSVFile(SportDataSync.mContext);
                    MainService.getInstance().checkDataBase();
                    Message msg = SportDataSync.this.mHandler.obtainMessage();
                    msg.what = 200;
                    msg.arg1 = NetMsgCode.getSportInfo;
                    SportDataSync.this.mHandler.sendMessage(msg);
                    return;
                }
                Log.i("lsj", "download failed");
                Log.d(SportDataSync.TAG, "download failed");
                SportDataSync.this.mHandler.sendEmptyMessage(110011);
            }
        }
    }

    public SportDataSync(Context context, int type) {
        mContext = context;
        this.openid = Tools.getOpenId(mContext);
        this.info = TerminalInfo.generateTerminalInfo(mContext);
        this.mcvs = new Cvshelper(mContext, type);
        this.gpsSync = new GPSDataSync(context, type);
    }

    public void postSportData(Handler handler) {
        this.mHandler = handler;
        new Thread(this.upRunnable).start();
        if (this.is_debug) {
            Log.d("txhlog", "NetMsgCode.postSportInfo");
        }
    }

    public void DeleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                DeleteFile(f);
            }
            file.delete();
        }
    }

    private void deleteSDCardFolder(File dir) {
        File to = new File(dir.getAbsolutePath() + System.currentTimeMillis());
        dir.renameTo(to);
        if (to.isDirectory()) {
            String[] children = to.list();
            for (String file : children) {
                File temp = new File(to, file);
                if (temp.isDirectory()) {
                    deleteSDCardFolder(temp);
                } else if (!temp.delete()) {
                    Log.d("deleteSDCardFolder", "DELETE FAIL");
                }
            }
            to.delete();
        }
    }

    private int update_file(String path, String filename) {
        HttpConnect httpCon = new HttpConnect();
        String filePath = path + "/" + filename;
        HashMap<String, String> params = new HashMap();
        params.put("account", this.openid);
        int result = httpCon.uploadFile(NetMsgCode.UP_URL, params, filePath);
        Log.i("lsj", "update_file result =" + result);
        return result;
    }

    private int download_file(String path, String filename) {
        HttpConnect httpCon = new HttpConnect();
        String filePath = path + "/" + filename;
        HashMap<String, String> params = new HashMap();
        params.put("account", this.openid);
        return httpCon.downloadFile(NetMsgCode.DOWN_URL, new HashMap(), params, filePath);
    }

    public void getSportFromCloud(Handler handler) {
        this.mHandler = handler;
        new Thread(this.dowmRunnable).start();
    }
}
