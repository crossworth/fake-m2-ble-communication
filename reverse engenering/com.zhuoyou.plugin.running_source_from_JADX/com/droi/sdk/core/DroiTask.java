package com.droi.sdk.core;

import android.util.Pair;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class DroiTask {
    private String f2675a;
    private Object f2676b = new Object();
    private volatile boolean f2677c = false;
    private volatile boolean f2678d = false;
    private volatile boolean f2679e = false;
    private LinkedList f2680f = new LinkedList();
    private Object f2681g = null;
    private Pair<DroiRunnable, TaskDispatcher> f2682h = null;
    private String f2683i;
    private C0851a f2684j;

    private class C0851a implements Runnable {
        final /* synthetic */ DroiTask f2673a;
        private WeakReference<DroiTask> f2674b;

        public C0851a(DroiTask droiTask, DroiTask droiTask2) {
            this.f2673a = droiTask;
            this.f2674b = new WeakReference(droiTask2);
        }

        public void run() {
            DroiTask droiTask = (DroiTask) this.f2674b.get();
            if (droiTask != null) {
                droiTask.m2580b();
            }
        }
    }

    private DroiTask() {
    }

    private DroiTask(DroiRunnable droiRunnable, String str) {
        this.f2675a = str;
        if (this.f2675a == null) {
            this.f2675a = UUID.randomUUID().toString();
        }
        this.f2680f.add(droiRunnable);
    }

    private void m2578a() {
        boolean z = (this.f2677c || this.f2682h == null) ? false : true;
        this.f2677c = true;
        this.f2678d = false;
        this.f2681g = null;
        synchronized (this.f2676b) {
            this.f2676b.notifyAll();
        }
        if (z) {
            final DroiRunnable droiRunnable = (DroiRunnable) this.f2682h.first;
            ((TaskDispatcher) this.f2682h.second).enqueueTask(new Runnable(this) {
                final /* synthetic */ DroiTask f2672b;

                public void run() {
                    DroiTask taskObject = droiRunnable.getTaskObject();
                    droiRunnable.setTaskObject(this.f2672b);
                    droiRunnable.run();
                    droiRunnable.setTaskObject(taskObject);
                }
            });
        }
    }

    private void m2580b() {
        if (this.f2677c) {
            m2578a();
        } else if (this.f2679e) {
            this.f2681g = null;
            this.f2678d = false;
            m2578a();
        } else if (this.f2681g == null && m2581c() == null) {
            m2578a();
        } else {
            int i;
            if (this.f2681g instanceof AtomicInteger) {
                i = ((AtomicInteger) this.f2681g).get();
            } else {
                DroiRunnable droiRunnable = (DroiRunnable) this.f2681g;
                DroiTask taskObject = droiRunnable.getTaskObject();
                droiRunnable.setTaskObject(this);
                droiRunnable.run();
                droiRunnable.setTaskObject(taskObject);
                i = 0;
            }
            if (m2581c() == null) {
                m2578a();
                return;
            }
            if (this.f2681g instanceof AtomicInteger) {
                i += ((AtomicInteger) this.f2681g).get();
            }
            if (this.f2679e) {
                this.f2681g = null;
                this.f2678d = false;
                m2578a();
                return;
            }
            TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
            if (i > 0) {
                currentTaskDispatcher.enqueueTask(this.f2684j, i);
            } else {
                currentTaskDispatcher.enqueueTask(this.f2684j);
            }
        }
    }

    private Object m2581c() {
        if (this.f2681g == null) {
            this.f2681g = this.f2680f.getFirst();
        } else {
            int indexOf = this.f2680f.indexOf(this.f2681g) + 1;
            if (indexOf <= 0 || indexOf >= this.f2680f.size()) {
                return null;
            }
            this.f2681g = this.f2680f.get(indexOf);
        }
        return this.f2681g;
    }

    public static DroiTask create(DroiRunnable droiRunnable) {
        return new DroiTask(droiRunnable, null);
    }

    public DroiTask callback(DroiRunnable droiRunnable) {
        return callback(droiRunnable, TaskDispatcher.currentTaskDispatcher().name());
    }

    public DroiTask callback(DroiRunnable droiRunnable, String str) {
        if (this.f2682h != null) {
            throw new RuntimeException("There is only one callback within DroiTask");
        }
        this.f2682h = Pair.create(droiRunnable, TaskDispatcher.getDispatcher(str));
        return this;
    }

    public boolean cancel() {
        if (this.f2677c || !this.f2678d) {
            return false;
        }
        this.f2679e = true;
        return true;
    }

    public DroiTask delay(int i) {
        this.f2680f.add(new AtomicInteger(i));
        return this;
    }

    public boolean isCancelled() {
        return this.f2679e;
    }

    public boolean isCompleted() {
        return this.f2677c;
    }

    public boolean isRunning() {
        return this.f2678d;
    }

    public void resetState() {
        this.f2677c = false;
        this.f2678d = false;
        this.f2681g = null;
        this.f2679e = false;
    }

    public Boolean runAndWait(String str) {
        Boolean.valueOf(true);
        Boolean runInBackground = runInBackground(str);
        return !runInBackground.booleanValue() ? runInBackground : waitTask();
    }

    public Boolean runInBackground(String str) {
        if (this.f2677c || this.f2678d) {
            return Boolean.valueOf(false);
        }
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(str);
        if (this.f2684j == null) {
            this.f2684j = new C0851a(this, this);
        }
        this.f2678d = true;
        this.f2681g = null;
        return Boolean.valueOf(dispatcher.enqueueTask(this.f2684j));
    }

    public DroiTask then(DroiRunnable droiRunnable) {
        this.f2680f.add(droiRunnable);
        return this;
    }

    public Boolean waitTask() {
        Boolean valueOf = Boolean.valueOf(true);
        synchronized (this.f2676b) {
            if (this.f2677c || !this.f2678d) {
                valueOf = Boolean.valueOf(true);
            } else {
                try {
                    this.f2676b.wait();
                } catch (InterruptedException e) {
                    valueOf = Boolean.valueOf(false);
                }
            }
        }
        return valueOf;
    }
}
