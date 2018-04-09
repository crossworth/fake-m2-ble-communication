package com.droi.sdk.core;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.C0782a;
import com.droi.sdk.core.priv.C0903g;
import com.droi.sdk.core.priv.CorePriv;
import com.droi.sdk.core.priv.TaskDispatcherPool;
import com.droi.sdk.internal.DroiLog;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DroiQuery {
    private static ConcurrentHashMap<Number, Number> f2665b = new ConcurrentHashMap();
    private static final String f2666d = "DroiQueryOPQueue";
    private static final String f2667e = "DroiQuery";
    protected Builder f2668a;
    private TaskDispatcherPool f2669c;

    public static class Builder {
        public static final String f2637b = "local_storage";
        public static final String f2638c = "cloud_storage";
        public static final String f2639d = "select";
        public static final String f2640e = "insert";
        protected static final String f2641f = "count";
        public static final String f2642g = "delete";
        public static final String f2643h = "update";
        public static final String f2644i = "updateData";
        public static final String f2645j = "where";
        public static final String f2646k = "cond";
        public static final String f2647l = "values";
        public static final String f2648m = "or";
        public static final String f2649n = "and";
        public static final String f2650o = "inc";
        public static final String f2651p = "dec";
        public static final String f2652q = "set";
        public static final String f2653r = "orderby";
        public static final String f2654s = "limit";
        public static final String f2655t = "offset";
        private static String[] f2656v = new String[]{f2639d, f2640e, "count", "delete", "update", f2644i};
        private static final String f2657w = "ASC";
        private static final String f2658x = "DESC";
        protected String f2659a;
        private C0903g<String, Object> f2660u = C0903g.m2668a();

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        <T extends DroiObject> Builder m2567a(T t, Class<T> cls) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(t);
            arrayList.add(cls.getSimpleName());
            this.f2660u.m2671a(f2640e, arrayList);
            return this;
        }

        <T extends DroiObject> Builder m2568a(T t, String str) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(t);
            arrayList.add(str);
            this.f2660u.m2671a(f2640e, arrayList);
            return this;
        }

        <T extends DroiObject> Builder m2569b(T t, Class<T> cls) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(t);
            arrayList.add(cls.getSimpleName());
            this.f2660u.m2671a("delete", arrayList);
            return this;
        }

        <T extends DroiObject> Builder m2570b(T t, String str) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(t);
            arrayList.add(str);
            this.f2660u.m2671a("delete", arrayList);
            return this;
        }

        public DroiQuery build() throws IllegalArgumentException {
            throwIfTheCommandInvalid();
            return new DroiQuery();
        }

        public Builder cloudStorage() {
            this.f2660u.m2671a(f2638c, f2638c);
            return this;
        }

        public Builder dec(String str) {
            this.f2660u.m2671a(f2651p, str);
            return this;
        }

        public <T extends DroiObject> Builder delete(Class<T> cls) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(cls.getSimpleName());
            this.f2660u.m2671a("delete", arrayList);
            return this;
        }

        public Builder inc(String str) {
            this.f2660u.m2671a(f2650o, str);
            return this;
        }

        public Builder limit(int i) {
            if (this.f2660u.m2673b(f2654s)) {
                throw new IllegalArgumentException("There is LIMIT condition object in command queue.");
            }
            this.f2660u.m2671a(f2654s, Integer.valueOf(Math.min(i, 1000)));
            return this;
        }

        public Builder localStorage() {
            this.f2660u.m2671a(f2637b, f2637b);
            return this;
        }

        public Builder offset(int i) {
            if (this.f2660u.m2673b("offset")) {
                throw new IllegalArgumentException("There is OFFSET condition object in command queue.");
            }
            this.f2660u.m2671a("offset", Integer.valueOf(Math.min(i, 1000)));
            return this;
        }

        public Builder orderBy(String str, Boolean bool) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            arrayList.add(bool.booleanValue() ? f2657w : f2658x);
            this.f2660u.m2671a(f2653r, arrayList);
            return this;
        }

        public <T extends DroiObject> Builder query(Class<T> cls) {
            this.f2660u.m2671a(f2639d, cls.getSimpleName());
            return this;
        }

        public Builder query(String str) {
            this.f2660u.m2671a(f2639d, str);
            return this;
        }

        public Builder set(String str, Object obj) {
            this.f2660u.m2671a(f2652q, new ArrayList(Arrays.asList(new Object[]{str, obj})));
            return this;
        }

        public void throwIfTheCommandInvalid() throws IllegalArgumentException {
            int i = 0;
            if (this.f2660u.m2673b(f2637b) && this.f2660u.m2673b(f2638c)) {
                throw new IllegalArgumentException("Query source should be one of local storage or cloud storage");
            }
            int i2 = (this.f2660u.m2673b(f2644i) ? 1 : 0) + ((((this.f2660u.m2673b(f2640e) ? 1 : 0) + (this.f2660u.m2673b(f2639d) ? 1 : 0)) + (this.f2660u.m2673b("delete") ? 1 : 0)) + (this.f2660u.m2673b("update") ? 1 : 0));
            if (i2 > 1) {
                throw new IllegalArgumentException("Your query needs one of SELECT, DELETE, INSERT or UPDATE.");
            }
            if (i2 == 0) {
                this.f2660u.m2671a(f2639d, "*");
            }
            for (String str : f2656v) {
                if (this.f2660u.m2673b(str)) {
                    this.f2659a = str;
                    break;
                }
            }
            if (this.f2660u.m2673b(f2640e) && (this.f2660u.m2673b(f2648m) || this.f2660u.m2673b(f2649n) || this.f2660u.m2673b(f2645j) || this.f2660u.m2673b(f2650o) || this.f2660u.m2673b(f2651p) || this.f2660u.m2673b(f2652q))) {
                throw new IllegalArgumentException("Insert command cannot combine with OR/AND/WHERE command");
            } else if ((this.f2660u.m2673b(f2648m) || this.f2660u.m2673b(f2649n)) && !this.f2660u.m2673b(f2645j)) {
                throw new IllegalArgumentException("Your query should one of WHERE statement for OR/AND statement");
            } else {
                i2 = (this.f2660u.m2673b(f2650o) ? 1 : 0) + (this.f2660u.m2673b(f2651p) ? 1 : 0);
                if (this.f2660u.m2673b(f2652q)) {
                    i = 1;
                }
                i2 += i;
                if (this.f2660u.m2673b(f2644i) && i2 != 1) {
                    throw new IllegalArgumentException("Your query must one updatint statement.");
                }
            }
        }

        public <T extends DroiObject> Builder update(T t, Class<T> cls) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(t);
            arrayList.add(cls.getSimpleName());
            this.f2660u.m2671a("update", arrayList);
            return this;
        }

        public <T extends DroiObject> Builder update(T t, String str) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(t);
            arrayList.add(str);
            this.f2660u.m2671a("update", arrayList);
            return this;
        }

        public <T extends DroiObject> Builder update(Class<T> cls) {
            this.f2660u.m2671a(f2644i, new ArrayList(Arrays.asList(new String[]{cls.getSimpleName()})));
            return this;
        }

        public Builder where(DroiCondition droiCondition) {
            if (this.f2660u.m2673b(f2645j)) {
                throw new IllegalArgumentException("There is WHERE condition object in command queue.");
            }
            this.f2660u.m2671a(f2645j, droiCondition);
            return this;
        }

        public Builder where(String str, String str2, Object obj) {
            if (this.f2660u.m2673b(f2645j)) {
                throw new IllegalArgumentException("There is WHERE condition object in command queue.");
            }
            this.f2660u.m2671a(f2645j, DroiCondition.cond(str, str2, obj));
            return this;
        }
    }

    private class C0849a implements Runnable {
        final /* synthetic */ DroiQuery f2661a;
        private C0782a f2662b;
        private List f2663c;
        private DroiError f2664d = new DroiError();

        public C0849a(DroiQuery droiQuery, Builder builder, C0782a c0782a) {
            this.f2661a = droiQuery;
            this.f2662b = c0782a;
        }

        public List m2571a() {
            return this.f2663c;
        }

        public DroiError m2572b() {
            return this.f2664d;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r3 = this;
            r0 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2668a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2659a;	 Catch:{ Exception -> 0x007d }
            r1 = "updateData";
            r0 = r0.equals(r1);	 Catch:{ Exception -> 0x007d }
            if (r0 == 0) goto L_0x0024;
        L_0x000e:
            r0 = r3.f2662b;	 Catch:{ Exception -> 0x007d }
            r1 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2668a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2660u;	 Catch:{ Exception -> 0x007d }
            r0 = r0.mo1886d(r1);	 Catch:{ Exception -> 0x007d }
            r3.f2664d = r0;	 Catch:{ Exception -> 0x007d }
        L_0x001e:
            monitor-enter(r3);
            r3.notify();	 Catch:{ all -> 0x0102 }
            monitor-exit(r3);	 Catch:{ all -> 0x0102 }
        L_0x0023:
            return;
        L_0x0024:
            r0 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2668a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2660u;	 Catch:{ Exception -> 0x007d }
            r1 = "inc";
            r0.m2670a(r1);	 Catch:{ Exception -> 0x007d }
            r0 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2668a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2660u;	 Catch:{ Exception -> 0x007d }
            r1 = "dec";
            r0.m2670a(r1);	 Catch:{ Exception -> 0x007d }
            r0 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2668a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2660u;	 Catch:{ Exception -> 0x007d }
            r1 = "set";
            r0.m2670a(r1);	 Catch:{ Exception -> 0x007d }
            r0 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2668a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2660u;	 Catch:{ Exception -> 0x007d }
            r1 = "updateData";
            r0.m2670a(r1);	 Catch:{ Exception -> 0x007d }
            r0 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r0 = r0.f2668a;	 Catch:{ Exception -> 0x007d }
            r1 = r0.f2659a;	 Catch:{ Exception -> 0x007d }
            r0 = -1;
            r2 = r1.hashCode();	 Catch:{ Exception -> 0x007d }
            switch(r2) {
                case -1335458389: goto L_0x00b1;
                case -1183792455: goto L_0x00a7;
                case -906021636: goto L_0x009d;
                case -838846263: goto L_0x00bb;
                default: goto L_0x0066;
            };	 Catch:{ Exception -> 0x007d }
        L_0x0066:
            switch(r0) {
                case 0: goto L_0x006a;
                case 1: goto L_0x00c5;
                case 2: goto L_0x00de;
                case 3: goto L_0x00f0;
                default: goto L_0x0069;
            };	 Catch:{ Exception -> 0x007d }
        L_0x0069:
            goto L_0x001e;
        L_0x006a:
            r0 = r3.f2662b;	 Catch:{ Exception -> 0x007d }
            r1 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2668a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2660u;	 Catch:{ Exception -> 0x007d }
            r2 = r3.f2664d;	 Catch:{ Exception -> 0x007d }
            r0 = r0.mo1883a(r1, r2);	 Catch:{ Exception -> 0x007d }
            r3.f2663c = r0;	 Catch:{ Exception -> 0x007d }
            goto L_0x001e;
        L_0x007d:
            r0 = move-exception;
            r1 = "DroiQuery";
            com.droi.sdk.internal.DroiLog.m2867d(r1, r0);	 Catch:{ all -> 0x00d7 }
            r1 = r3.f2664d;	 Catch:{ all -> 0x00d7 }
            r2 = 1070001; // 0x1053b1 float:1.499391E-39 double:5.286507E-318;
            r1.setCode(r2);	 Catch:{ all -> 0x00d7 }
            r1 = r3.f2664d;	 Catch:{ all -> 0x00d7 }
            r0 = r0.toString();	 Catch:{ all -> 0x00d7 }
            r1.setAppendedMessage(r0);	 Catch:{ all -> 0x00d7 }
            monitor-enter(r3);
            r3.notify();	 Catch:{ all -> 0x009a }
            monitor-exit(r3);	 Catch:{ all -> 0x009a }
            goto L_0x0023;
        L_0x009a:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x009a }
            throw r0;
        L_0x009d:
            r2 = "select";
            r1 = r1.equals(r2);	 Catch:{ Exception -> 0x007d }
            if (r1 == 0) goto L_0x0066;
        L_0x00a5:
            r0 = 0;
            goto L_0x0066;
        L_0x00a7:
            r2 = "insert";
            r1 = r1.equals(r2);	 Catch:{ Exception -> 0x007d }
            if (r1 == 0) goto L_0x0066;
        L_0x00af:
            r0 = 1;
            goto L_0x0066;
        L_0x00b1:
            r2 = "delete";
            r1 = r1.equals(r2);	 Catch:{ Exception -> 0x007d }
            if (r1 == 0) goto L_0x0066;
        L_0x00b9:
            r0 = 2;
            goto L_0x0066;
        L_0x00bb:
            r2 = "update";
            r1 = r1.equals(r2);	 Catch:{ Exception -> 0x007d }
            if (r1 == 0) goto L_0x0066;
        L_0x00c3:
            r0 = 3;
            goto L_0x0066;
        L_0x00c5:
            r0 = r3.f2662b;	 Catch:{ Exception -> 0x007d }
            r1 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2668a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2660u;	 Catch:{ Exception -> 0x007d }
            r0 = r0.mo1881a(r1);	 Catch:{ Exception -> 0x007d }
            r3.f2664d = r0;	 Catch:{ Exception -> 0x007d }
            goto L_0x001e;
        L_0x00d7:
            r0 = move-exception;
            monitor-enter(r3);
            r3.notify();	 Catch:{ all -> 0x0105 }
            monitor-exit(r3);	 Catch:{ all -> 0x0105 }
            throw r0;
        L_0x00de:
            r0 = r3.f2662b;	 Catch:{ Exception -> 0x007d }
            r1 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2668a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2660u;	 Catch:{ Exception -> 0x007d }
            r0 = r0.mo1885c(r1);	 Catch:{ Exception -> 0x007d }
            r3.f2664d = r0;	 Catch:{ Exception -> 0x007d }
            goto L_0x001e;
        L_0x00f0:
            r0 = r3.f2662b;	 Catch:{ Exception -> 0x007d }
            r1 = r3.f2661a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2668a;	 Catch:{ Exception -> 0x007d }
            r1 = r1.f2660u;	 Catch:{ Exception -> 0x007d }
            r0 = r0.mo1884b(r1);	 Catch:{ Exception -> 0x007d }
            r3.f2664d = r0;	 Catch:{ Exception -> 0x007d }
            goto L_0x001e;
        L_0x0102:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0102 }
            throw r0;
        L_0x0105:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0105 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.DroiQuery.a.run():void");
        }
    }

    private DroiQuery(Builder builder) {
        this.f2669c = new TaskDispatcherPool(0, 6);
        this.f2668a = builder;
    }

    private List m2573a(DroiError droiError) {
        List list = null;
        try {
            list = m2575a(this.f2668a.f2660u.m2673b(Builder.f2637b) ? C0883c.m2630a(CorePriv.getContext()) : CloudStorageDBHelper.m2432a(), droiError);
        } catch (Exception e) {
            if (droiError != null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
            }
        }
        return list == null ? new ArrayList() : list;
    }

    private List m2575a(C0782a c0782a, DroiError droiError) {
        List a;
        Runnable c0849a = new C0849a(this, this.f2668a, c0782a);
        synchronized (c0849a) {
            TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(f2666d);
            if (dispatcher == TaskDispatcher.currentTaskDispatcher()) {
                c0849a.run();
            } else {
                dispatcher.enqueueTask(c0849a);
                try {
                    c0849a.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            a = c0849a.m2571a();
            if (droiError != null) {
                droiError.copy(c0849a.m2572b());
            }
        }
        return a;
    }

    public static void cancelTask(int i) {
        synchronized (f2665b) {
            f2665b.remove(Integer.valueOf(i));
        }
    }

    DroiError m2577a() {
        DroiError droiError = new DroiError();
        runQuery(droiError);
        return droiError;
    }

    public int count(DroiError droiError) {
        int i = 0;
        if (this.f2668a.f2660u.m2673b(Builder.f2639d)) {
            this.f2668a.f2660u.m2671a(ParamKey.COUNT, Integer.valueOf(1));
            try {
                List runQuery = runQuery(droiError);
                if (droiError.isOk()) {
                    i = ((Integer) runQuery.get(0)).intValue();
                    this.f2668a.f2660u.m2670a(ParamKey.COUNT);
                }
            } finally {
                this.f2668a.f2660u.m2670a(ParamKey.COUNT);
            }
        } else {
            String str = "Only support query in builder.";
            DroiLog.m2870e(f2667e, str);
            if (droiError != null) {
                droiError.setCode(DroiError.INVALID_PARAMETER);
                droiError.setAppendedMessage(str);
            }
        }
        return i;
    }

    public int countInBackground(final DroiCallback<Integer> droiCallback) {
        final int hashCode = UUID.randomUUID().toString().hashCode();
        final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        synchronized (f2665b) {
            f2665b.put(Integer.valueOf(hashCode), Integer.valueOf(hashCode));
        }
        this.f2669c.enqueueTask(new Runnable(this) {
            final /* synthetic */ DroiQuery f2629d;

            public void run() {
                if (DroiQuery.f2665b.contains(Integer.valueOf(hashCode))) {
                    final DroiError droiError = new DroiError();
                    final int count = this.f2629d.count(droiError);
                    synchronized (DroiQuery.f2665b) {
                        DroiQuery.f2665b.remove(Integer.valueOf(hashCode));
                    }
                    if (droiCallback != null) {
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C08462 f2625c;

                            public void run() {
                                droiCallback.result(Integer.valueOf(count), droiError);
                            }
                        });
                    }
                }
            }
        });
        return hashCode;
    }

    public int runInBackground(final DroiCallback<Boolean> droiCallback) {
        final int hashCode = UUID.randomUUID().toString().hashCode();
        final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        synchronized (f2665b) {
            f2665b.put(Integer.valueOf(hashCode), Integer.valueOf(hashCode));
        }
        this.f2669c.enqueueTask(new Runnable(this) {
            final /* synthetic */ DroiQuery f2622d;

            public void run() {
                if (DroiQuery.f2665b.containsKey(Integer.valueOf(hashCode))) {
                    final DroiError droiError = new DroiError();
                    this.f2622d.runQuery(droiError);
                    synchronized (DroiQuery.f2665b) {
                        DroiQuery.f2665b.remove(Integer.valueOf(hashCode));
                    }
                    if (droiCallback != null) {
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C08441 f2618b;

                            public void run() {
                                droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                            }
                        });
                    }
                }
            }
        });
        return hashCode;
    }

    public <T extends DroiObject> List<T> runQuery(DroiError droiError) {
        return m2573a(droiError);
    }

    public <T extends DroiObject> int runQueryInBackground(final DroiQueryCallback<T> droiQueryCallback) {
        final int hashCode = UUID.randomUUID().toString().hashCode();
        final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        synchronized (f2665b) {
            f2665b.put(Integer.valueOf(hashCode), Integer.valueOf(hashCode));
        }
        this.f2669c.enqueueTask(new Runnable(this) {
            final /* synthetic */ DroiQuery f2636d;

            public void run() {
                if (DroiQuery.f2665b.containsKey(Integer.valueOf(hashCode))) {
                    final DroiError droiError = new DroiError();
                    List a = this.f2636d.m2573a(droiError);
                    synchronized (DroiQuery.f2665b) {
                        DroiQuery.f2665b.remove(Integer.valueOf(hashCode));
                    }
                    if (droiQueryCallback != null) {
                        if (a == null) {
                            a = new ArrayList();
                        }
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C08483 f2632c;

                            public void run() {
                                droiQueryCallback.result(a, droiError);
                            }
                        });
                    }
                }
            }
        });
        return hashCode;
    }
}
