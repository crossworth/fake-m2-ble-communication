package com.sina.weibo.sdk.statistic;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import com.sina.weibo.sdk.utils.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import twitter4j.HttpResponseCode;

class WBAgentHandler {
    private static int MAX_CACHE_SIZE = 5;
    private static List<PageLog> mActivePages;
    private static WBAgentHandler mInstance;
    private static Map<String, PageLog> mPages;
    private static Timer mTimer;

    public static synchronized WBAgentHandler getInstance() {
        WBAgentHandler wBAgentHandler;
        synchronized (WBAgentHandler.class) {
            if (mInstance == null) {
                mInstance = new WBAgentHandler();
            }
            wBAgentHandler = mInstance;
        }
        return wBAgentHandler;
    }

    private WBAgentHandler() {
        mActivePages = new ArrayList();
        mPages = new HashMap();
        LogUtil.m3309i(WBAgent.TAG, "init handler");
    }

    public void onPageStart(String pageName) {
        if (!StatisticConfig.ACTIVITY_DURATION_OPEN) {
            PageLog pageLog = new PageLog(pageName);
            pageLog.setType(LogType.FRAGMENT);
            synchronized (mPages) {
                mPages.put(pageName, pageLog);
            }
            LogUtil.m3307d(WBAgent.TAG, new StringBuilder(String.valueOf(pageName)).append(", ").append(pageLog.getStartTime() / 1000).toString());
        }
    }

    public void onPageEnd(String pageName) {
        if (!StatisticConfig.ACTIVITY_DURATION_OPEN) {
            if (mPages.containsKey(pageName)) {
                PageLog pageLog = (PageLog) mPages.get(pageName);
                pageLog.setDuration(System.currentTimeMillis() - pageLog.getStartTime());
                synchronized (mActivePages) {
                    mActivePages.add(pageLog);
                }
                synchronized (mPages) {
                    mPages.remove(pageName);
                }
                LogUtil.m3307d(WBAgent.TAG, new StringBuilder(String.valueOf(pageName)).append(", ").append(pageLog.getStartTime() / 1000).append(", ").append(pageLog.getDuration() / 1000).toString());
            } else {
                LogUtil.m3308e(WBAgent.TAG, "please call onPageStart before onPageEnd");
            }
            if (mActivePages.size() >= MAX_CACHE_SIZE) {
                saveActivePages(mActivePages);
                mActivePages.clear();
            }
        }
    }

    public void onResume(Context context) {
        if (LogReport.getPackageName() == null) {
            LogReport.setPackageName(context.getPackageName());
        }
        if (mTimer == null) {
            mTimer = timerTask(context, 500, StatisticConfig.getUploadInterval());
        }
        long curTime = System.currentTimeMillis();
        String pageName = context.getClass().getName();
        checkNewSession(context, curTime);
        if (StatisticConfig.ACTIVITY_DURATION_OPEN) {
            PageLog pageLog = new PageLog(pageName, curTime);
            pageLog.setType(LogType.ACTIVITY);
            synchronized (mPages) {
                mPages.put(pageName, pageLog);
            }
        }
        LogUtil.m3307d(WBAgent.TAG, new StringBuilder(String.valueOf(pageName)).append(", ").append(curTime / 1000).toString());
    }

    public void onPause(Context context) {
        long curTime = System.currentTimeMillis();
        String pageName = context.getClass().getName();
        LogUtil.m3309i(WBAgent.TAG, "update last page endtime:" + (curTime / 1000));
        PageLog.updateSession(context, null, Long.valueOf(0), Long.valueOf(curTime));
        if (StatisticConfig.ACTIVITY_DURATION_OPEN) {
            if (mPages.containsKey(pageName)) {
                PageLog pageLog = (PageLog) mPages.get(pageName);
                pageLog.setDuration(curTime - pageLog.getStartTime());
                synchronized (mActivePages) {
                    mActivePages.add(pageLog);
                }
                synchronized (mPages) {
                    mPages.remove(pageName);
                }
                LogUtil.m3307d(WBAgent.TAG, new StringBuilder(String.valueOf(pageName)).append(", ").append(pageLog.getStartTime() / 1000).append(", ").append(pageLog.getDuration() / 1000).toString());
            } else {
                LogUtil.m3308e(WBAgent.TAG, "please call onResume before onPause");
            }
            if (mActivePages.size() >= MAX_CACHE_SIZE) {
                saveActivePages(mActivePages);
                mActivePages.clear();
            }
        }
        checkAppStatus(context);
    }

    public void onEvent(String pageName, String eventId, Map<String, String> extend) {
        EventLog eventLog = new EventLog(pageName, eventId, extend);
        eventLog.setType(LogType.EVENT);
        synchronized (mActivePages) {
            mActivePages.add(eventLog);
        }
        if (extend == null) {
            LogUtil.m3307d(WBAgent.TAG, "event--- page:" + pageName + " ,event name:" + eventId);
        } else {
            LogUtil.m3307d(WBAgent.TAG, "event--- page:" + pageName + " ,event name:" + eventId + " ,extend:" + extend.toString());
        }
        if (mActivePages.size() >= MAX_CACHE_SIZE) {
            saveActivePages(mActivePages);
            mActivePages.clear();
        }
    }

    public void uploadAppLogs(final Context context) {
        long duration = System.currentTimeMillis() - LogReport.getTime(context);
        if (LogReport.getTime(context) <= 0 || duration >= StatisticConfig.MIN_UPLOAD_INTERVAL) {
            WBAgentExecutor.execute(new Runnable() {
                public void run() {
                    LogReport.uploadAppLogs(context, WBAgentHandler.this.getLogsInMemory());
                }
            });
            return;
        }
        timerTask(context, StatisticConfig.MIN_UPLOAD_INTERVAL - duration, 0);
    }

    public void onStop(Context context) {
        checkAppStatus(context);
    }

    private void checkAppStatus(Context context) {
        if (isBackground(context)) {
            saveActivePages(mActivePages);
            mActivePages.clear();
        }
    }

    private boolean isBackground(Context context) {
        for (RunningAppProcessInfo appProcess : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == HttpResponseCode.BAD_REQUEST) {
                    LogUtil.m3309i(WBAgent.TAG, "后台:" + appProcess.processName);
                    return true;
                }
                LogUtil.m3309i(WBAgent.TAG, "前台:" + appProcess.processName);
                return false;
            }
        }
        return false;
    }

    public void onKillProcess() {
        LogUtil.m3309i(WBAgent.TAG, "save applogs and close timer and shutdown thread executor");
        saveActivePages(mActivePages);
        mInstance = null;
        closeTimer();
        WBAgentExecutor.shutDownExecutor();
    }

    private void checkNewSession(Context context, long curTime) {
        if (PageLog.isNewSession(context, curTime)) {
            PageLog old_session = new PageLog(context);
            old_session.setType(LogType.SESSION_END);
            PageLog new_session = new PageLog(context, curTime);
            new_session.setType(LogType.SESSION_START);
            synchronized (mActivePages) {
                if (old_session.getEndTime() > 0) {
                    mActivePages.add(old_session);
                } else {
                    LogUtil.m3307d(WBAgent.TAG, "is a new install");
                }
                mActivePages.add(new_session);
            }
            LogUtil.m3307d(WBAgent.TAG, "last session--- starttime:" + old_session.getStartTime() + " ,endtime:" + old_session.getEndTime());
            LogUtil.m3307d(WBAgent.TAG, "is a new session--- starttime:" + new_session.getStartTime());
            return;
        }
        LogUtil.m3309i(WBAgent.TAG, "is not a new session");
    }

    private synchronized void saveActivePages(List<PageLog> pages) {
        final String content = LogBuilder.getPageLogs(pages);
        WBAgentExecutor.execute(new Runnable() {
            public void run() {
                LogFileUtil.writeToFile(LogFileUtil.getAppLogPath(LogFileUtil.ANALYTICS_FILE_NAME), content, true);
            }
        });
    }

    private synchronized String getLogsInMemory() {
        String memorylogs;
        memorylogs = "";
        if (mActivePages.size() > 0) {
            memorylogs = LogBuilder.getPageLogs(mActivePages);
            mActivePages.clear();
        }
        return memorylogs;
    }

    private Timer timerTask(final Context context, long delay, long peirod) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                LogReport.uploadAppLogs(context, WBAgentHandler.this.getLogsInMemory());
            }
        };
        if (peirod == 0) {
            timer.schedule(task, delay);
        } else {
            timer.schedule(task, delay, peirod);
        }
        return timer;
    }

    private void closeTimer() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
