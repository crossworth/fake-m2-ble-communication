package com.umeng.socialize.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.KeyEvent;
import com.umeng.socialize.Config;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;

public class QueuedWork {
    private static Handler uiHandler = new Handler(Looper.getMainLooper());

    public static abstract class UMAsyncTask<Result> {
        protected Runnable thread;

        class C09501 implements Runnable {
            C09501() {
            }

            public void run() {
                final Object doInBackground = UMAsyncTask.this.doInBackground();
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        UMAsyncTask.this.onPostExecute(doInBackground);
                    }
                });
            }
        }

        class C09512 implements Runnable {
            C09512() {
            }

            public void run() {
                UMAsyncTask.this.onPreExecute();
            }
        }

        protected abstract Result doInBackground();

        protected void onPreExecute() {
        }

        protected void onPostExecute(Result result) {
        }

        public final UMAsyncTask<Result> execute() {
            this.thread = new C09501();
            QueuedWork.runInMain(new C09512());
            QueuedWork.runInBack(this.thread);
            return this;
        }

        public static void waitAsyncTask() {
        }
    }

    public static abstract class DialogThread<T> extends UMAsyncTask {
        Dialog dialog = null;

        class C09481 implements OnKeyListener {
            C09481() {
            }

            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == 4 && keyEvent.getRepeatCount() == 0) {
                    QueuedWork.removeInBack(DialogThread.this.thread);
                }
                return false;
            }
        }

        public DialogThread(Context context) {
            if ((context instanceof Activity) && Config.dialogSwitch) {
                if (Config.dialog != null) {
                    this.dialog = Config.dialog;
                } else {
                    this.dialog = new ProgressDialog(context);
                }
                this.dialog.setOwnerActivity((Activity) context);
                this.dialog.setOnKeyListener(new C09481());
            }
        }

        protected void onPostExecute(Object obj) {
            super.onPostExecute(obj);
            SocializeUtils.safeCloseDialog(this.dialog);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            SocializeUtils.safeShowDialog(this.dialog);
        }
    }

    public static void runInMain(Runnable runnable) {
        uiHandler.post(runnable);
    }

    public static void runInBack(Runnable runnable) {
        HandlerThread handlerThread = new HandlerThread(Log.TAG, 10);
        handlerThread.start();
        new Handler(handlerThread.getLooper()).post(runnable);
    }

    public static void removeInBack(Runnable runnable) {
    }

    public static void cancle(Runnable runnable) {
    }
}
