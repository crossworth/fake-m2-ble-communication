package com.droi.sdk.core;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition.Type;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.priv.C0939m;
import com.droi.sdk.core.priv.C0939m.C0917c;
import com.droi.sdk.core.priv.DroiGroupRelation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;

@DroiObjectName("_Group")
public final class DroiGroup extends DroiObject {
    private static final String LOG_TAG = "DroiGroup";
    @DroiExpose
    private String Name;
    private boolean isReady;
    private Object locker;
    private List<C0821a> relationList;

    class C08182 extends DroiRunnable {
        final /* synthetic */ DroiGroup f2520a;

        C08182(DroiGroup droiGroup) {
            this.f2520a = droiGroup;
        }

        public void run() {
            this.f2520a.fetchRelation();
        }
    }

    private static class C0821a {
        public static final int f2528a = 0;
        public static final int f2529b = 1;
        public static final int f2530c = 2;
        public int f2531d;
        DroiGroupRelation f2532e;

        private C0821a() {
        }
    }

    public DroiGroup() {
        this.locker = new Object();
        this.Name = null;
    }

    public DroiGroup(String str) {
        this.locker = new Object();
        this.Name = str;
    }

    private static boolean m2498a(final int i, final String str, final DroiCallback<List<String>> droiCallback) {
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference(new DroiError(DroiError.UNKNOWN_ERROR, "May caused by Exception"));
        return DroiTask.create(new DroiRunnable() {
            public void run() {
                DroiError droiError = new DroiError();
                atomicReference.set(DroiGroup.m2500b(i, str, droiError));
                atomicReference2.set(droiError);
            }
        }).callback(new DroiRunnable() {
            public void run() {
                if (droiCallback != null) {
                    droiCallback.result(atomicReference.get(), (DroiError) atomicReference2.get());
                }
            }
        }, TaskDispatcher.currentTaskDispatcher().name()).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    private static List<String> m2500b(int i, String str, DroiError droiError) {
        if (droiError == null) {
            droiError = new DroiError();
        }
        DroiUser currentUser = DroiUser.getCurrentUser();
        if (currentUser == null) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("No loggin user.");
            return null;
        } else if (currentUser.isAuthorized()) {
            C0917c a = C0939m.m2753a(i, str, currentUser.getSessionToken(), droiError);
            if (!droiError.isOk()) {
                return null;
            }
            try {
                ArrayList arrayList = new ArrayList();
                int length = a.f2977c.length();
                for (int i2 = 0; i2 < length; i2++) {
                    arrayList.add(a.f2977c.getString(i2));
                }
                return arrayList;
            } catch (JSONException e) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("Json format error.");
                return null;
            }
        } else {
            droiError.setCode(DroiError.USER_NOT_AUTHORIZED);
            return null;
        }
    }

    public static List<String> getGroupIdsByGroupObjectId(String str, DroiError droiError) {
        return m2500b(1, str, droiError);
    }

    public static boolean getGroupIdsByGroupObjectIdInBackground(String str, DroiCallback<List<String>> droiCallback) {
        return m2498a(1, str, (DroiCallback) droiCallback);
    }

    public static List<String> getGroupIdsByUserObjectId(String str, DroiError droiError) {
        return m2500b(0, str, droiError);
    }

    public static boolean getGroupIdsByUserObjectIdInBackground(String str, DroiCallback<List<String>> droiCallback) {
        return m2498a(0, str, (DroiCallback) droiCallback);
    }

    public boolean addGroup(String str) {
        synchronized (this.locker) {
            if (this.relationList == null) {
                this.relationList = new ArrayList();
            }
            for (C0821a c0821a : this.relationList) {
                if (c0821a != null && c0821a.f2532e != null && str.equals(c0821a.f2532e.MemberGroupObjectId)) {
                    return false;
                }
            }
            C0821a c0821a2 = new C0821a();
            c0821a2.f2531d = 1;
            DroiGroupRelation droiGroupRelation = new DroiGroupRelation();
            droiGroupRelation.setPermission(getPermission());
            droiGroupRelation.GroupObjectId = getObjectId();
            droiGroupRelation.MemberGroupObjectId = str;
            c0821a2.f2532e = droiGroupRelation;
            this.relationList.add(c0821a2);
            return true;
        }
    }

    public boolean addUser(String str) {
        synchronized (this.locker) {
            if (this.relationList == null) {
                this.relationList = new ArrayList();
            }
            for (C0821a c0821a : this.relationList) {
                if (c0821a != null && c0821a.f2532e != null && str.equals(c0821a.f2532e.MemberUserObjectId)) {
                    return false;
                }
            }
            C0821a c0821a2 = new C0821a();
            c0821a2.f2531d = 1;
            DroiGroupRelation droiGroupRelation = new DroiGroupRelation();
            droiGroupRelation.setPermission(getPermission());
            droiGroupRelation.GroupObjectId = getObjectId();
            droiGroupRelation.MemberUserObjectId = str;
            c0821a2.f2532e = droiGroupRelation;
            this.relationList.add(c0821a2);
            return true;
        }
    }

    public DroiError fetchRelation() {
        DroiError droiError = new DroiError();
        List<DroiGroupRelation> runQuery = Builder.newBuilder().query(DroiGroupRelation.class).where("GroupObjectId", Type.EQ, getObjectId()).build().runQuery(droiError);
        if (!droiError.isOk()) {
            return droiError;
        }
        List arrayList = new ArrayList();
        for (DroiGroupRelation droiGroupRelation : runQuery) {
            C0821a c0821a = new C0821a();
            c0821a.f2531d = 0;
            c0821a.f2532e = droiGroupRelation;
            arrayList.add(c0821a);
        }
        synchronized (this.locker) {
            this.relationList = arrayList;
        }
        this.isReady = true;
        return droiError;
    }

    public void fetchRelationInBackground(final DroiCallback<Boolean> droiCallback) {
        final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        DroiTask.create(new C08182(this)).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiGroup f2519c;

            class C08161 implements Runnable {
                final /* synthetic */ C08171 f2516a;

                C08161(C08171 c08171) {
                    this.f2516a = c08171;
                }

                public void run() {
                    droiCallback.result(Boolean.valueOf(this.f2516a.f2519c.isReady), null);
                }
            }

            public void run() {
                if (droiCallback != null) {
                    currentTaskDispatcher.enqueueTask(new C08161(this));
                }
            }
        }).runInBackground("TaskDispatcher_DroiBackgroundThread");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] getGroupIdList() {
        /*
        r6 = this;
        r1 = 0;
        r2 = r6.locker;
        monitor-enter(r2);
        r0 = r6.relationList;	 Catch:{ all -> 0x0045 }
        if (r0 != 0) goto L_0x000b;
    L_0x0008:
        monitor-exit(r2);	 Catch:{ all -> 0x0045 }
        r0 = r1;
    L_0x000a:
        return r0;
    L_0x000b:
        r0 = r6.relationList;	 Catch:{ all -> 0x0045 }
        r0.size();	 Catch:{ all -> 0x0045 }
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x0045 }
        r3.<init>();	 Catch:{ all -> 0x0045 }
        r0 = r6.relationList;	 Catch:{ all -> 0x0045 }
        r4 = r0.iterator();	 Catch:{ all -> 0x0045 }
    L_0x001b:
        r0 = r4.hasNext();	 Catch:{ all -> 0x0045 }
        if (r0 == 0) goto L_0x0048;
    L_0x0021:
        r0 = r4.next();	 Catch:{ all -> 0x0045 }
        r0 = (com.droi.sdk.core.DroiGroup.C0821a) r0;	 Catch:{ all -> 0x0045 }
        if (r0 == 0) goto L_0x001b;
    L_0x0029:
        r5 = r0.f2532e;	 Catch:{ all -> 0x0045 }
        if (r5 == 0) goto L_0x001b;
    L_0x002d:
        r5 = r0.f2532e;	 Catch:{ all -> 0x0045 }
        r5 = r5.MemberGroupObjectId;	 Catch:{ all -> 0x0045 }
        if (r5 == 0) goto L_0x001b;
    L_0x0033:
        r5 = r0.f2532e;	 Catch:{ all -> 0x0045 }
        r5 = r5.MemberGroupObjectId;	 Catch:{ all -> 0x0045 }
        r5 = r5.isEmpty();	 Catch:{ all -> 0x0045 }
        if (r5 != 0) goto L_0x001b;
    L_0x003d:
        r0 = r0.f2532e;	 Catch:{ all -> 0x0045 }
        r0 = r0.MemberGroupObjectId;	 Catch:{ all -> 0x0045 }
        r3.add(r0);	 Catch:{ all -> 0x0045 }
        goto L_0x001b;
    L_0x0045:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0045 }
        throw r0;
    L_0x0048:
        monitor-exit(r2);	 Catch:{ all -> 0x0045 }
        if (r3 != 0) goto L_0x004d;
    L_0x004b:
        r0 = r1;
        goto L_0x000a;
    L_0x004d:
        r0 = r3.size();
        r0 = new java.lang.String[r0];
        r0 = r3.toArray(r0);
        r0 = (java.lang.String[]) r0;
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.DroiGroup.getGroupIdList():java.lang.String[]");
    }

    public String getName() {
        return this.Name;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] getUserIdList() {
        /*
        r6 = this;
        r1 = 0;
        r2 = r6.locker;
        monitor-enter(r2);
        r0 = r6.relationList;	 Catch:{ all -> 0x0045 }
        if (r0 != 0) goto L_0x000b;
    L_0x0008:
        monitor-exit(r2);	 Catch:{ all -> 0x0045 }
        r0 = r1;
    L_0x000a:
        return r0;
    L_0x000b:
        r0 = r6.relationList;	 Catch:{ all -> 0x0045 }
        r0.size();	 Catch:{ all -> 0x0045 }
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x0045 }
        r3.<init>();	 Catch:{ all -> 0x0045 }
        r0 = r6.relationList;	 Catch:{ all -> 0x0045 }
        r4 = r0.iterator();	 Catch:{ all -> 0x0045 }
    L_0x001b:
        r0 = r4.hasNext();	 Catch:{ all -> 0x0045 }
        if (r0 == 0) goto L_0x0048;
    L_0x0021:
        r0 = r4.next();	 Catch:{ all -> 0x0045 }
        r0 = (com.droi.sdk.core.DroiGroup.C0821a) r0;	 Catch:{ all -> 0x0045 }
        if (r0 == 0) goto L_0x001b;
    L_0x0029:
        r5 = r0.f2532e;	 Catch:{ all -> 0x0045 }
        if (r5 == 0) goto L_0x001b;
    L_0x002d:
        r5 = r0.f2532e;	 Catch:{ all -> 0x0045 }
        r5 = r5.MemberUserObjectId;	 Catch:{ all -> 0x0045 }
        if (r5 == 0) goto L_0x001b;
    L_0x0033:
        r5 = r0.f2532e;	 Catch:{ all -> 0x0045 }
        r5 = r5.MemberUserObjectId;	 Catch:{ all -> 0x0045 }
        r5 = r5.isEmpty();	 Catch:{ all -> 0x0045 }
        if (r5 != 0) goto L_0x001b;
    L_0x003d:
        r0 = r0.f2532e;	 Catch:{ all -> 0x0045 }
        r0 = r0.MemberUserObjectId;	 Catch:{ all -> 0x0045 }
        r3.add(r0);	 Catch:{ all -> 0x0045 }
        goto L_0x001b;
    L_0x0045:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0045 }
        throw r0;
    L_0x0048:
        monitor-exit(r2);	 Catch:{ all -> 0x0045 }
        if (r3 != 0) goto L_0x004d;
    L_0x004b:
        r0 = r1;
        goto L_0x000a;
    L_0x004d:
        r0 = r3.size();
        r0 = new java.lang.String[r0];
        r0 = r3.toArray(r0);
        r0 = (java.lang.String[]) r0;
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.DroiGroup.getUserIdList():java.lang.String[]");
    }

    public boolean isReady() {
        return this.isReady;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeGroup(java.lang.String r5) {
        /*
        r4 = this;
        r1 = r4.locker;
        monitor-enter(r1);
        r0 = r4.relationList;	 Catch:{ all -> 0x0030 }
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r1);	 Catch:{ all -> 0x0030 }
    L_0x0008:
        return;
    L_0x0009:
        r0 = r4.relationList;	 Catch:{ all -> 0x0030 }
        r2 = r0.iterator();	 Catch:{ all -> 0x0030 }
    L_0x000f:
        r0 = r2.hasNext();	 Catch:{ all -> 0x0030 }
        if (r0 == 0) goto L_0x002e;
    L_0x0015:
        r0 = r2.next();	 Catch:{ all -> 0x0030 }
        r0 = (com.droi.sdk.core.DroiGroup.C0821a) r0;	 Catch:{ all -> 0x0030 }
        if (r0 == 0) goto L_0x000f;
    L_0x001d:
        r3 = r0.f2532e;	 Catch:{ all -> 0x0030 }
        if (r3 == 0) goto L_0x000f;
    L_0x0021:
        r3 = r0.f2532e;	 Catch:{ all -> 0x0030 }
        r3 = r3.MemberGroupObjectId;	 Catch:{ all -> 0x0030 }
        r3 = r5.equals(r3);	 Catch:{ all -> 0x0030 }
        if (r3 == 0) goto L_0x000f;
    L_0x002b:
        r2 = 2;
        r0.f2531d = r2;	 Catch:{ all -> 0x0030 }
    L_0x002e:
        monitor-exit(r1);	 Catch:{ all -> 0x0030 }
        goto L_0x0008;
    L_0x0030:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0030 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.DroiGroup.removeGroup(java.lang.String):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeUser(java.lang.String r5) {
        /*
        r4 = this;
        r1 = r4.locker;
        monitor-enter(r1);
        r0 = r4.relationList;	 Catch:{ all -> 0x0030 }
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r1);	 Catch:{ all -> 0x0030 }
    L_0x0008:
        return;
    L_0x0009:
        r0 = r4.relationList;	 Catch:{ all -> 0x0030 }
        r2 = r0.iterator();	 Catch:{ all -> 0x0030 }
    L_0x000f:
        r0 = r2.hasNext();	 Catch:{ all -> 0x0030 }
        if (r0 == 0) goto L_0x002e;
    L_0x0015:
        r0 = r2.next();	 Catch:{ all -> 0x0030 }
        r0 = (com.droi.sdk.core.DroiGroup.C0821a) r0;	 Catch:{ all -> 0x0030 }
        if (r0 == 0) goto L_0x000f;
    L_0x001d:
        r3 = r0.f2532e;	 Catch:{ all -> 0x0030 }
        if (r3 == 0) goto L_0x000f;
    L_0x0021:
        r3 = r0.f2532e;	 Catch:{ all -> 0x0030 }
        r3 = r3.MemberUserObjectId;	 Catch:{ all -> 0x0030 }
        r3 = r5.equals(r3);	 Catch:{ all -> 0x0030 }
        if (r3 == 0) goto L_0x000f;
    L_0x002b:
        r2 = 2;
        r0.f2531d = r2;	 Catch:{ all -> 0x0030 }
    L_0x002e:
        monitor-exit(r1);	 Catch:{ all -> 0x0030 }
        goto L_0x0008;
    L_0x0030:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0030 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.DroiGroup.removeUser(java.lang.String):void");
    }

    public DroiError save() {
        DroiError save = super.save();
        if (!save.isOk()) {
            return save;
        }
        synchronized (this.locker) {
            List<C0821a> list = this.relationList != null ? (List) ((ArrayList) this.relationList).clone() : null;
        }
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (C0821a c0821a : list) {
                switch (c0821a.f2531d) {
                    case 1:
                        c0821a.f2532e.save();
                        break;
                    case 2:
                        if (!c0821a.f2532e.delete().isOk()) {
                            break;
                        }
                        arrayList.add(c0821a);
                        break;
                    default:
                        break;
                }
                c0821a.f2531d = 0;
            }
        }
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                list.remove((C0821a) it.next());
            }
            synchronized (this.locker) {
                this.relationList = list;
            }
        }
        return save;
    }

    public DroiError saveEventually() {
        throw new RuntimeException("Do not support saveEventually for DroiGroup");
    }

    public void setName(String str) {
        this.Name = str;
    }
}
