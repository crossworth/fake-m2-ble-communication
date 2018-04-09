package com.droi.sdk.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.util.LongSparseArray;
import com.droi.sdk.internal.DroiLog;
import java.util.HashMap;

public class TaskDispatcher {
    public static final String BackgroundThreadName = "TaskDispatcher_BackgroundThreadName";
    public static final String MainThreadName = "MainThread";
    static final String f2769a = "TaskDispatcher_DroiBackgroundThread";
    private static final String f2770b = "TaskDispatcher";
    private static HashMap<String, TaskDispatcher> f2771c;
    private static LongSparseArray<TaskDispatcher> f2772d;
    private static Object f2773e = new Object();
    private static volatile boolean f2774f = false;
    private volatile boolean f2775g = false;
    private volatile String f2776h = null;
    private HandlerThread f2777i;
    private Handler f2778j;
    private String f2779k;
    private Object f2780l = new Object();
    private HashMap<String, Runnable> f2781m;
    private HashMap<String, Runnable> f2782n;

    private class C0869a implements Runnable {
        final /* synthetic */ TaskDispatcher f2761a;
        private String f2762b;
        private Runnable f2763c;
        private int f2764d;

        public C0869a(TaskDispatcher taskDispatcher, String str, Runnable runnable) {
            this.f2761a = taskDispatcher;
            this.f2762b = str;
            this.f2763c = runnable;
        }

        public void run() {
            if (!TaskDispatcher.f2774f && this.f2761a.f2781m != null) {
                if (this.f2762b != null) {
                    if (this.f2761a.f2781m.containsKey(this.f2762b) && ((Runnable) this.f2761a.f2781m.get(this.f2762b)).hashCode() == hashCode()) {
                        synchronized (this.f2761a.f2780l) {
                            this.f2761a.killTask(this.f2762b);
                            this.f2761a.f2776h = this.f2762b;
                        }
                    } else {
                        return;
                    }
                }
                synchronized (this.f2761a.f2780l) {
                    this.f2761a.f2775g = true;
                }
                try {
                    this.f2763c.run();
                } catch (Exception e) {
                    DroiLog.m2870e(TaskDispatcher.f2770b, "There is an exception. Exception is " + e.toString());
                }
                synchronized (this.f2761a.f2780l) {
                    this.f2761a.f2775g = false;
                    this.f2761a.f2776h = null;
                }
            }
        }
    }

    private class C0870b implements Runnable {
        final /* synthetic */ TaskDispatcher f2765a;
        private String f2766b;
        private Runnable f2767c;
        private int f2768d;

        public C0870b(TaskDispatcher taskDispatcher, String str, Runnable runnable, int i) {
            this.f2765a = taskDispatcher;
            this.f2767c = runnable;
            this.f2766b = str;
            this.f2768d = i;
        }

        public void run() {
            Object obj = null;
            if (!TaskDispatcher.f2774f && this.f2765a.f2782n != null && this.f2765a.f2781m != null && this.f2765a.f2781m.containsKey(this.f2766b) && ((Runnable) this.f2765a.f2781m.get(this.f2766b)).hashCode() == hashCode()) {
                synchronized (this.f2765a.f2780l) {
                    if (this.f2765a.f2782n.containsKey(this.f2766b)) {
                        this.f2765a.killTask(this.f2766b);
                        this.f2765a.f2776h = this.f2766b;
                        int i = 1;
                    }
                }
                synchronized (this.f2765a.f2780l) {
                    this.f2765a.f2775g = true;
                }
                try {
                    this.f2767c.run();
                } catch (Exception e) {
                    DroiLog.m2870e(TaskDispatcher.f2770b, "There is an exception. Exception is " + e.toString());
                }
                synchronized (this.f2765a.f2780l) {
                    this.f2765a.f2775g = false;
                    this.f2765a.f2776h = null;
                }
                if (obj == null) {
                    this.f2765a.f2778j.postDelayed(this, (long) this.f2768d);
                }
            }
        }
    }

    private TaskDispatcher(Looper looper) {
        this.f2778j = new Handler(looper);
        this.f2779k = MainThreadName;
    }

    private TaskDispatcher(Looper looper, String str) {
        this.f2778j = new Handler(looper);
        this.f2779k = str;
    }

    private TaskDispatcher(final String str) {
        this.f2779k = str;
        this.f2777i = new HandlerThread(this, str) {
            final /* synthetic */ TaskDispatcher f2760b;

            public void run() {
                DroiLog.m2871i(TaskDispatcher.f2770b, "HandlerThread is running. Dispatch Name is " + str);
                try {
                    super.run();
                } catch (Exception e) {
                    DroiLog.m2870e(TaskDispatcher.f2770b, e.toString());
                }
                DroiLog.m2871i(TaskDispatcher.f2770b, "HandlerThread is stopping. Dispatch Name is " + str);
            }
        };
        this.f2777i.start();
        this.f2778j = new Handler(this.f2777i.getLooper());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean m2601a(java.lang.Runnable r5, int r6, java.lang.String r7, boolean r8) {
        /*
        r4 = this;
        r1 = r4.f2780l;
        monitor-enter(r1);
        r0 = r4.f2781m;	 Catch:{ all -> 0x005a }
        if (r0 != 0) goto L_0x000e;
    L_0x0007:
        r0 = new java.util.HashMap;	 Catch:{ all -> 0x005a }
        r0.<init>();	 Catch:{ all -> 0x005a }
        r4.f2781m = r0;	 Catch:{ all -> 0x005a }
    L_0x000e:
        r0 = r4.f2782n;	 Catch:{ all -> 0x005a }
        if (r0 != 0) goto L_0x0019;
    L_0x0012:
        r0 = new java.util.HashMap;	 Catch:{ all -> 0x005a }
        r0.<init>();	 Catch:{ all -> 0x005a }
        r4.f2782n = r0;	 Catch:{ all -> 0x005a }
    L_0x0019:
        r0 = r4.f2781m;	 Catch:{ all -> 0x005a }
        r0 = r0.containsKey(r7);	 Catch:{ all -> 0x005a }
        if (r0 == 0) goto L_0x003c;
    L_0x0021:
        r0 = "TaskDispatcher";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005a }
        r2.<init>();	 Catch:{ all -> 0x005a }
        r3 = "The task is in queue. TaskName is ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x005a }
        r2 = r2.append(r7);	 Catch:{ all -> 0x005a }
        r2 = r2.toString();	 Catch:{ all -> 0x005a }
        com.droi.sdk.internal.DroiLog.m2871i(r0, r2);	 Catch:{ all -> 0x005a }
        r0 = 1;
        monitor-exit(r1);	 Catch:{ all -> 0x005a }
    L_0x003b:
        return r0;
    L_0x003c:
        monitor-exit(r1);	 Catch:{ all -> 0x005a }
        r0 = new com.droi.sdk.core.TaskDispatcher$b;
        r0.<init>(r4, r7, r5, r6);
        r1 = r4.f2780l;
        monitor-enter(r1);
        if (r8 == 0) goto L_0x004c;
    L_0x0047:
        r2 = r4.f2782n;	 Catch:{ all -> 0x005d }
        r2.put(r7, r0);	 Catch:{ all -> 0x005d }
    L_0x004c:
        r2 = r4.f2781m;	 Catch:{ all -> 0x005d }
        r2.put(r7, r0);	 Catch:{ all -> 0x005d }
        monitor-exit(r1);	 Catch:{ all -> 0x005d }
        r1 = r4.f2778j;
        r2 = (long) r6;
        r0 = r1.postDelayed(r0, r2);
        goto L_0x003b;
    L_0x005a:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x005a }
        throw r0;
    L_0x005d:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x005d }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.TaskDispatcher.a(java.lang.Runnable, int, java.lang.String, boolean):boolean");
    }

    private void m2605c() {
        if (this.f2777i != null) {
            this.f2777i.quit();
            try {
                this.f2777i.join(2000);
            } catch (InterruptedException e) {
                DroiLog.m2870e(f2770b, "Stop dispatcher timeout.");
            }
        }
    }

    public static TaskDispatcher currentTaskDispatcher() {
        long id = Thread.currentThread().getId();
        if (f2772d != null) {
            TaskDispatcher taskDispatcher = (TaskDispatcher) f2772d.get(id);
            if (taskDispatcher != null) {
                return taskDispatcher;
            }
        }
        return getDispatcher(MainThreadName);
    }

    public static TaskDispatcher getDispatcher(String str) {
        if (f2771c == null) {
            throw new RuntimeException("TaskDispatcher doesn't initialize.");
        }
        TaskDispatcher taskDispatcher;
        synchronized (f2773e) {
            if (f2771c.containsKey(str)) {
                taskDispatcher = (TaskDispatcher) f2771c.get(str);
                if (!(taskDispatcher.f2777i == null || taskDispatcher.f2777i.isAlive())) {
                    f2771c.remove(str);
                    int indexOfValue = f2772d.indexOfValue(taskDispatcher);
                    if (indexOfValue >= 0) {
                        f2772d.removeAt(indexOfValue);
                    }
                    taskDispatcher = new TaskDispatcher(str);
                    f2771c.put(str, taskDispatcher);
                    f2772d.put(taskDispatcher.m2607a(), taskDispatcher);
                }
            } else {
                taskDispatcher = new TaskDispatcher(str);
                f2771c.put(str, taskDispatcher);
                f2772d.put(taskDispatcher.m2607a(), taskDispatcher);
            }
        }
        return taskDispatcher;
    }

    public static void initialize() {
        synchronized (f2773e) {
            if (f2771c == null) {
                f2771c = new HashMap();
                f2772d = new LongSparseArray();
                TaskDispatcher taskDispatcher = new TaskDispatcher(Looper.getMainLooper());
                if (Looper.getMainLooper() == null) {
                    throw new RuntimeException("Looper.getMainLooper() is null");
                }
                f2771c.put(MainThreadName, taskDispatcher);
                f2772d.put(Looper.getMainLooper().getThread().getId(), taskDispatcher);
            }
        }
    }

    public static void stopTaskDispatcher(String str) {
        if (f2771c != null) {
            synchronized (f2773e) {
                if (f2771c.containsKey(str)) {
                    TaskDispatcher taskDispatcher = (TaskDispatcher) f2771c.get(str);
                    taskDispatcher.m2605c();
                    f2771c.remove(str);
                    int indexOfValue = f2772d.indexOfValue(taskDispatcher);
                    if (indexOfValue >= 0) {
                        f2772d.removeAt(indexOfValue);
                    }
                    DroiLog.m2871i(f2770b, "Remove " + str + " from list");
                    return;
                }
            }
        }
    }

    public static void uninitialize() {
        f2774f = true;
        if (f2771c != null) {
            synchronized (f2773e) {
                for (TaskDispatcher taskDispatcher : f2771c.values()) {
                    if (taskDispatcher.f2777i != null) {
                        taskDispatcher.m2605c();
                    }
                }
                f2771c.clear();
                f2771c = null;
                f2772d.clear();
                f2772d = null;
            }
        }
    }

    long m2607a() {
        return this.f2777i.getId();
    }

    public boolean enqueueOnceTimerTask(Runnable runnable, int i, String str) {
        return m2601a(runnable, i, str, true);
    }

    public boolean enqueueTask(Runnable runnable) {
        return enqueueTask(runnable, null, 0);
    }

    public boolean enqueueTask(Runnable runnable, int i) {
        return enqueueTask(runnable, null, i);
    }

    public boolean enqueueTask(Runnable runnable, String str) {
        return enqueueTask(runnable, str, 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean enqueueTask(java.lang.Runnable r5, java.lang.String r6, int r7) {
        /*
        r4 = this;
        r0 = r4.f2778j;
        if (r0 != 0) goto L_0x0006;
    L_0x0004:
        r0 = 0;
    L_0x0005:
        return r0;
    L_0x0006:
        if (r6 != 0) goto L_0x0010;
    L_0x0008:
        r0 = java.util.UUID.randomUUID();
        r6 = r0.toString();
    L_0x0010:
        r1 = r4.f2780l;
        monitor-enter(r1);
        r0 = r4.f2781m;	 Catch:{ all -> 0x0041 }
        if (r0 != 0) goto L_0x001e;
    L_0x0017:
        r0 = new java.util.HashMap;	 Catch:{ all -> 0x0041 }
        r0.<init>();	 Catch:{ all -> 0x0041 }
        r4.f2781m = r0;	 Catch:{ all -> 0x0041 }
    L_0x001e:
        r0 = r4.f2781m;	 Catch:{ all -> 0x0041 }
        r0 = r0.containsKey(r6);	 Catch:{ all -> 0x0041 }
        if (r0 == 0) goto L_0x0044;
    L_0x0026:
        r0 = "TaskDispatcher";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0041 }
        r2.<init>();	 Catch:{ all -> 0x0041 }
        r3 = "The task is in queue. TaskName is ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x0041 }
        r2 = r2.append(r6);	 Catch:{ all -> 0x0041 }
        r2 = r2.toString();	 Catch:{ all -> 0x0041 }
        com.droi.sdk.internal.DroiLog.m2871i(r0, r2);	 Catch:{ all -> 0x0041 }
        r0 = 1;
        monitor-exit(r1);	 Catch:{ all -> 0x0041 }
        goto L_0x0005;
    L_0x0041:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0041 }
        throw r0;
    L_0x0044:
        monitor-exit(r1);	 Catch:{ all -> 0x0041 }
        r0 = new com.droi.sdk.core.TaskDispatcher$a;
        r0.<init>(r4, r6, r5);
        r1 = r4.f2780l;
        monitor-enter(r1);
        r2 = r4.f2781m;	 Catch:{ all -> 0x005d }
        r2.put(r6, r0);	 Catch:{ all -> 0x005d }
        monitor-exit(r1);	 Catch:{ all -> 0x005d }
        if (r7 <= 0) goto L_0x0060;
    L_0x0055:
        r1 = r4.f2778j;
        r2 = (long) r7;
        r0 = r1.postDelayed(r0, r2);
        goto L_0x0005;
    L_0x005d:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x005d }
        throw r0;
    L_0x0060:
        if (r7 != 0) goto L_0x0069;
    L_0x0062:
        r1 = r4.f2778j;
        r0 = r1.post(r0);
        goto L_0x0005;
    L_0x0069:
        r1 = r4.f2778j;
        r0 = r1.postAtFrontOfQueue(r0);
        goto L_0x0005;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.TaskDispatcher.enqueueTask(java.lang.Runnable, java.lang.String, int):boolean");
    }

    public boolean enqueueTaskAtFrontOfQueue(Runnable runnable) {
        return enqueueTask(runnable, null, -1);
    }

    public boolean enqueueTimerTask(Runnable runnable, int i, String str) {
        return m2601a(runnable, i, str, false);
    }

    public boolean isRunning() {
        return this.f2775g;
    }

    public boolean isTaskCancelled(String str) {
        synchronized (this.f2780l) {
            if (this.f2781m != null && this.f2781m.containsKey(str)) {
                return false;
            } else if (this.f2775g && this.f2776h != null && this.f2776h.hashCode() == str.hashCode()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean killTask(String str) {
        synchronized (this.f2780l) {
            if (this.f2781m != null && this.f2781m.containsKey(str)) {
                this.f2781m.remove(str);
            }
            if (this.f2782n != null && this.f2782n.containsKey(str)) {
                this.f2782n.remove(str);
            }
        }
        return true;
    }

    public String name() {
        return this.f2779k;
    }
}
