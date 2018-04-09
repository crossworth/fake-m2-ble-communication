package com.droi.sdk.core.priv;

import com.droi.sdk.core.TaskDispatcher;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskDispatcherPool {
    private static final String f2854a = "TaskDispatcherPool";
    private static long f2855b = 20000;
    private ArrayList<TaskDispatcher> f2856c = new ArrayList();
    private LinkedList<C0894b> f2857d = new LinkedList();
    private HashSet<String> f2858e = new HashSet();
    private int f2859f;
    private int f2860g;
    private AtomicInteger f2861h = new AtomicInteger(0);

    private class C0893a implements Runnable {
        public volatile boolean f2847a = false;
        final /* synthetic */ TaskDispatcherPool f2848b;
        private TaskDispatcher f2849c;

        class C08921 implements Runnable {
            final /* synthetic */ C0893a f2846a;

            C08921(C0893a c0893a) {
                this.f2846a = c0893a;
            }

            public void run() {
                synchronized (this.f2846a.f2848b.f2856c) {
                    TaskDispatcher.stopTaskDispatcher(this.f2846a.f2849c.name());
                    this.f2846a.f2848b.f2856c.remove(this.f2846a.f2849c);
                }
            }
        }

        public C0893a(TaskDispatcherPool taskDispatcherPool, TaskDispatcher taskDispatcher) {
            this.f2848b = taskDispatcherPool;
            this.f2849c = taskDispatcher;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r4 = this;
        L_0x0000:
            r0 = r4.f2847a;
            if (r0 != 0) goto L_0x003b;
        L_0x0004:
            r0 = r4.f2848b;
            r1 = r0.f2857d;
            monitor-enter(r1);
            r0 = r4.f2848b;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.f2857d;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.isEmpty();	 Catch:{ InterruptedException -> 0x0046 }
            if (r0 == 0) goto L_0x0089;
        L_0x0017:
            r0 = r4.f2848b;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.f2856c;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.size();	 Catch:{ InterruptedException -> 0x0046 }
            r2 = r4.f2848b;	 Catch:{ InterruptedException -> 0x0046 }
            r2 = r2.f2859f;	 Catch:{ InterruptedException -> 0x0046 }
            if (r0 <= r2) goto L_0x003c;
        L_0x0029:
            r0 = r4.f2848b;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.f2857d;	 Catch:{ InterruptedException -> 0x0046 }
            r2 = com.droi.sdk.core.priv.TaskDispatcherPool.f2855b;	 Catch:{ InterruptedException -> 0x0046 }
            r0.wait(r2);	 Catch:{ InterruptedException -> 0x0046 }
        L_0x0036:
            r0 = r4.f2847a;	 Catch:{ InterruptedException -> 0x0046 }
            if (r0 == 0) goto L_0x0051;
        L_0x003a:
            monitor-exit(r1);	 Catch:{ all -> 0x004e }
        L_0x003b:
            return;
        L_0x003c:
            r0 = r4.f2848b;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.f2857d;	 Catch:{ InterruptedException -> 0x0046 }
            r0.wait();	 Catch:{ InterruptedException -> 0x0046 }
            goto L_0x0036;
        L_0x0046:
            r0 = move-exception;
            r2 = "TaskDispatcherPool";
            com.droi.sdk.internal.DroiLog.m2873w(r2, r0);	 Catch:{ all -> 0x004e }
            monitor-exit(r1);	 Catch:{ all -> 0x004e }
            goto L_0x003b;
        L_0x004e:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x004e }
            throw r0;
        L_0x0051:
            r0 = r4.f2848b;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.f2857d;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.isEmpty();	 Catch:{ InterruptedException -> 0x0046 }
            if (r0 == 0) goto L_0x0089;
        L_0x005d:
            r0 = r4.f2848b;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.f2856c;	 Catch:{ InterruptedException -> 0x0046 }
            r0 = r0.size();	 Catch:{ InterruptedException -> 0x0046 }
            r2 = r4.f2848b;	 Catch:{ InterruptedException -> 0x0046 }
            r2 = r2.f2859f;	 Catch:{ InterruptedException -> 0x0046 }
            if (r0 <= r2) goto L_0x0089;
        L_0x006f:
            r0 = "MainThread";
            r0 = com.droi.sdk.core.TaskDispatcher.getDispatcher(r0);	 Catch:{ InterruptedException -> 0x0046 }
            if (r0 != 0) goto L_0x0079;
        L_0x0077:
            monitor-exit(r1);	 Catch:{ all -> 0x004e }
            goto L_0x0000;
        L_0x0079:
            r0 = "MainThread";
            r0 = com.droi.sdk.core.TaskDispatcher.getDispatcher(r0);	 Catch:{ InterruptedException -> 0x0046 }
            r2 = new com.droi.sdk.core.priv.TaskDispatcherPool$a$1;	 Catch:{ InterruptedException -> 0x0046 }
            r2.<init>(r4);	 Catch:{ InterruptedException -> 0x0046 }
            r0.enqueueTask(r2);	 Catch:{ InterruptedException -> 0x0046 }
            monitor-exit(r1);	 Catch:{ all -> 0x004e }
            goto L_0x003b;
        L_0x0089:
            r0 = r4.f2847a;	 Catch:{ all -> 0x004e }
            if (r0 == 0) goto L_0x008f;
        L_0x008d:
            monitor-exit(r1);	 Catch:{ all -> 0x004e }
            goto L_0x003b;
        L_0x008f:
            r0 = r4.f2848b;	 Catch:{ all -> 0x004e }
            r0 = r0.f2857d;	 Catch:{ all -> 0x004e }
            r0 = r0.isEmpty();	 Catch:{ all -> 0x004e }
            if (r0 == 0) goto L_0x009e;
        L_0x009b:
            monitor-exit(r1);	 Catch:{ all -> 0x004e }
            goto L_0x0000;
        L_0x009e:
            r0 = r4.f2848b;	 Catch:{ all -> 0x004e }
            r0 = r0.f2857d;	 Catch:{ all -> 0x004e }
            r0 = r0.removeFirst();	 Catch:{ all -> 0x004e }
            r0 = (com.droi.sdk.core.priv.TaskDispatcherPool.C0894b) r0;	 Catch:{ all -> 0x004e }
            monitor-exit(r1);	 Catch:{ all -> 0x004e }
            if (r0 == 0) goto L_0x0000;
        L_0x00ad:
            r1 = r4.f2848b;	 Catch:{ Exception -> 0x00c9 }
            r1 = r1.f2861h;	 Catch:{ Exception -> 0x00c9 }
            r1.incrementAndGet();	 Catch:{ Exception -> 0x00c9 }
            r0.run();	 Catch:{ Exception -> 0x00c9 }
            r0 = r4.f2848b;	 Catch:{ Exception -> 0x00c9 }
            r0 = r0.f2861h;	 Catch:{ Exception -> 0x00c9 }
            r0.decrementAndGet();	 Catch:{ Exception -> 0x00c9 }
            r0 = 1;
            java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x00c9 }
            goto L_0x0000;
        L_0x00c9:
            r0 = move-exception;
            r1 = "TaskDispatcherPool";
            com.droi.sdk.internal.DroiLog.m2873w(r1, r0);
            goto L_0x0000;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.priv.TaskDispatcherPool.a.run():void");
        }
    }

    private class C0894b implements Runnable {
        public Runnable f2850a;
        public String f2851b;
        public String f2852c;
        final /* synthetic */ TaskDispatcherPool f2853d;

        private C0894b(TaskDispatcherPool taskDispatcherPool) {
            this.f2853d = taskDispatcherPool;
        }

        public void run() {
            synchronized (this.f2853d.f2858e) {
                boolean contains = this.f2853d.f2858e.contains(this.f2852c);
                this.f2853d.f2858e.remove(this.f2852c);
            }
            if (contains) {
                this.f2850a.run();
            }
        }
    }

    public TaskDispatcherPool(int i, int i2) {
        int i3 = 0;
        TaskDispatcher.initialize();
        this.f2859f = i;
        this.f2860g = i2;
        while (i3 < i) {
            m2658c();
            i3++;
        }
    }

    private void m2656b() {
        if (this.f2856c.size() < this.f2860g && this.f2861h.get() == this.f2856c.size() && this.f2857d.size() > 0) {
            m2658c();
        }
    }

    private void m2658c() {
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(UUID.randomUUID().toString());
        synchronized (this.f2856c) {
            this.f2856c.add(dispatcher);
            dispatcher.enqueueTask(new C0893a(this, dispatcher));
        }
    }

    public boolean cancelTask(String str) {
        synchronized (this.f2858e) {
            this.f2858e.remove(str);
        }
        return true;
    }

    public String enqueueTask(Runnable runnable) {
        return enqueueTask(runnable, UUID.randomUUID().toString());
    }

    public String enqueueTask(Runnable runnable, String str) {
        C0894b c0894b = new C0894b();
        c0894b.f2850a = runnable;
        c0894b.f2852c = str;
        synchronized (this.f2858e) {
            this.f2858e.add(c0894b.f2852c);
        }
        synchronized (this.f2857d) {
            this.f2857d.addLast(c0894b);
            this.f2857d.notify();
        }
        m2656b();
        return c0894b.f2852c;
    }

    public String enqueueTaskAtFrontOfQueue(Runnable runnable) {
        C0894b c0894b = new C0894b();
        c0894b.f2850a = runnable;
        c0894b.f2852c = UUID.randomUUID().toString();
        synchronized (this.f2858e) {
            this.f2858e.add(c0894b.f2852c);
        }
        synchronized (this.f2857d) {
            this.f2857d.addFirst(c0894b);
            this.f2857d.notify();
        }
        m2656b();
        return c0894b.f2852c;
    }

    public boolean isTaskCancelled(String str) {
        synchronized (this.f2858e) {
            if (this.f2858e.contains(str)) {
                return false;
            }
            return true;
        }
    }
}
