package com.droi.sdk.core;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition.Type;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.priv.C0896c;
import com.droi.sdk.core.priv.C0907i;
import com.droi.sdk.core.priv.C0911l;
import com.droi.sdk.core.priv.C0939m;
import com.droi.sdk.core.priv.C0939m.C0918d;
import com.droi.sdk.core.priv.C0939m.C0930p;
import com.droi.sdk.core.priv.C0939m.C0931q;
import com.droi.sdk.core.priv.C0939m.C0932r;
import com.droi.sdk.core.priv.C0939m.C0935u;
import com.droi.sdk.core.priv.C0939m.C0936v;
import com.droi.sdk.core.priv.C0944p;
import com.droi.sdk.core.priv.C0947r;
import com.droi.sdk.core.priv.CorePriv;
import com.droi.sdk.core.priv.DroiStorageFinder;
import com.droi.sdk.core.priv.FileDescriptorHelper;
import com.droi.sdk.internal.DroiLog;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import no.nordicsemi.android.log.LogContract.Session;
import org.json.JSONObject;

@DroiObjectName("_User")
public class DroiUser extends DroiObject {
    private static final String ANONYMOUS_TAG = "anonymous";
    public static final Creator<DroiUser> CREATOR = new Creator<DroiUser>() {
        public DroiUser createFromParcel(Parcel parcel) {
            DroiObject droiObject = (DroiObject) DroiObject.CREATOR.createFromParcel(parcel);
            if (droiObject instanceof DroiUser) {
                DroiUser droiUser = (DroiUser) droiObject;
                droiUser.Password = parcel.readString();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                if (readString == null) {
                    return droiUser;
                }
                droiUser.session = new HashMap();
                droiUser.session.put("Token", readString);
                droiUser.session.put("ExpireAt", readString2);
                return droiUser;
            }
            DroiLog.m2870e(DroiUser.LOG_TAG, "Can not create DroiUser from parcel. Type mismatch.");
            return null;
        }

        public DroiUser[] newArray(int i) {
            return new DroiUser[i];
        }
    };
    private static final String DROI_USER_TASK_NAME = "duser_task";
    private static final byte[] KEY = new byte[]{(byte) 90, (byte) 90, (byte) 60, (byte) 126, (byte) 68, (byte) 56, (byte) 78, (byte) 90};
    private static final String LOG_TAG = "DroiUser";
    public static final int f2740a = 1040008;
    private static boolean autoAnonymousUser = true;
    public static final int f2741b = 1040009;
    public static final int f2742c = 1040010;
    private static DroiUser currentUser = null;
    public static final int f2743d = 1040011;
    public static final int f2744e = 1040015;
    public static final int f2745f = 1040016;
    public static final int f2746g = 1040017;
    public static final int f2747h = 1040018;
    public static final int f2748i = 1040019;
    public static final int f2749j = 1030305;
    @DroiExpose
    private Map<String, Object> AuthData;
    @DroiExpose
    private String Email;
    @DroiExpose
    private Boolean EmailVerified = Boolean.valueOf(false);
    @DroiExpose
    private Boolean Enabled = Boolean.valueOf(true);
    private String Password;
    @DroiExpose
    private String PhoneNum;
    @DroiExpose
    private Boolean PhoneNumVerified = Boolean.valueOf(false);
    @DroiExpose
    private String UserId;
    private Map<String, String> session;

    static DroiError m2582a() {
        DroiError droiError = new DroiError();
        try {
            File file = new File(DroiStorageFinder.getPrivatePath(CorePriv.getContext()) + "/user");
            if (file.exists() && !file.delete()) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("Fail to delete");
                DroiLog.m2868d(LOG_TAG, "clearUp fail");
            }
            currentUser = null;
        } catch (Exception e) {
            DroiLog.m2869e(LOG_TAG, e);
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage(e.toString());
        }
        return droiError;
    }

    static synchronized DroiError m2583a(DroiUser droiUser) {
        DroiError droiError;
        Object obj = null;
        synchronized (DroiUser.class) {
            if (droiUser == null) {
                droiError = new DroiError(DroiError.INVALID_PARAMETER, null);
            } else {
                if (droiUser.getPermission() == null) {
                    DroiPermission defaultPermission = DroiPermission.getDefaultPermission();
                    if (defaultPermission == null) {
                        defaultPermission = new DroiPermission();
                        defaultPermission.setUserReadPermission(droiUser.getObjectId(), true);
                        defaultPermission.setUserWritePermission(droiUser.getObjectId(), true);
                    }
                    droiUser.setPermission(defaultPermission);
                }
                droiError = new DroiError();
                try {
                    String str = DroiStorageFinder.getPrivatePath(CorePriv.getContext()) + "/user";
                    currentUser = droiUser;
                    JSONObject toJson = droiUser.toJson(true, droiError);
                    if (droiError.isOk()) {
                        if (droiUser.session != null && droiUser.session.size() > 0) {
                            obj = new JSONObject(droiUser.session);
                        }
                        if (obj != null) {
                            toJson.put(Session.SESSION_CONTENT_DIRECTORY, obj);
                        }
                        String jSONObject = toJson.toString();
                        DroiLog.m2868d(LOG_TAG, "storeCurrentUser: " + jSONObject);
                        jSONObject = C0944p.m2791b(C0944p.m2789a(jSONObject.getBytes(), KEY));
                        Closeable fileOutputStream = new FileOutputStream(str);
                        fileOutputStream.write(jSONObject.getBytes());
                        FileDescriptorHelper.closeQuietly(fileOutputStream);
                    } else {
                        currentUser = null;
                    }
                } catch (Exception e) {
                    DroiLog.m2869e(LOG_TAG, e);
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                    currentUser = null;
                }
            }
        }
        return droiError;
    }

    private static <T extends DroiUser> DroiUser m2584a(File file, Class<T> cls) {
        Closeable fileInputStream;
        Exception e;
        if (!DroiObject.isExtendedClass((Class) cls)) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                Class cls2;
                byte[] bArr = new byte[fileInputStream.available()];
                fileInputStream.read(bArr);
                String str = new String(C0944p.m2794b(C0944p.m2793b(new String(bArr)), KEY));
                DroiLog.m2868d(LOG_TAG, "currentUser: " + str);
                JSONObject jSONObject = new JSONObject(str);
                FileDescriptorHelper.closeQuietly(fileInputStream);
                if (jSONObject.has(C0896c.f2866a) && cls == DroiUser.class) {
                    Class customClassWithClassName = DroiObject.getCustomClassWithClassName(jSONObject.getString(C0896c.f2866a));
                    if (cls != customClassWithClassName && C0911l.m2701a(customClassWithClassName, DroiUser.class)) {
                        cls2 = customClassWithClassName;
                    }
                }
                currentUser = (DroiUser) DroiObject.fromJson(jSONObject, cls2);
                if (currentUser == null) {
                    DroiLog.m2870e(LOG_TAG, "Load cache user fail.");
                    return null;
                }
                if (jSONObject.has(Session.SESSION_CONTENT_DIRECTORY)) {
                    currentUser.session = new HashMap();
                    jSONObject = jSONObject.getJSONObject(Session.SESSION_CONTENT_DIRECTORY);
                    Iterator keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        str = (String) keys.next();
                        currentUser.session.put(str, jSONObject.getString(str));
                    }
                }
                return currentUser;
            } catch (Exception e2) {
                e = e2;
                DroiLog.m2869e(LOG_TAG, e);
                FileDescriptorHelper.closeQuietly(fileInputStream);
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
            DroiLog.m2869e(LOG_TAG, e);
            FileDescriptorHelper.closeQuietly(fileInputStream);
            return null;
        }
    }

    private static <T extends DroiUser> DroiUser m2585a(Class<T> cls) {
        if (!DroiObject.isExtendedClass((Class) cls)) {
            return null;
        }
        String deviceId = CorePriv.getDeviceId();
        String installationId = CorePriv.getInstallationId();
        if (deviceId == null || installationId == null) {
            return null;
        }
        try {
            DroiUser droiUser = (DroiUser) cls.newInstance();
            droiUser.AuthData = new HashMap();
            droiUser.AuthData.put(ANONYMOUS_TAG, "1");
            droiUser.UserId = installationId + deviceId;
            currentUser = droiUser;
            return droiUser;
        } catch (Exception e) {
            DroiLog.m2869e(LOG_TAG, e);
            return null;
        }
    }

    private static <T extends DroiUser> DroiUser m2586a(String str, Map map, Class<T> cls) {
        if (!DroiObject.isExtendedClass((Class) cls)) {
            return null;
        }
        String deviceId = CorePriv.getDeviceId();
        String installationId = CorePriv.getInstallationId();
        if (deviceId == null || installationId == null) {
            return null;
        }
        try {
            DroiUser droiUser = (DroiUser) cls.newInstance();
            droiUser.AuthData = new HashMap();
            droiUser.AuthData.put(str, map);
            droiUser.UserId = installationId + deviceId;
            currentUser = droiUser;
            return droiUser;
        } catch (Exception e) {
            DroiLog.m2869e(LOG_TAG, e);
            return null;
        }
    }

    private DroiError m2589b() {
        DroiError droiError = new DroiError();
        String installationId = CorePriv.getInstallationId();
        String deviceId = CorePriv.getDeviceId();
        C0931q a = C0939m.m2762a(new C0930p(installationId), droiError);
        if (!droiError.isOk()) {
            return droiError;
        }
        if (a.f3028a == 0) {
            DroiUser droiUser = (DroiUser) DroiObject.fromJson(a.f3032e, DroiUser.class);
            if (droiUser == null) {
                return new DroiError(DroiError.ERROR, "No user result.");
            }
            copyFrom(droiUser);
            this.AuthData = droiUser.AuthData;
            this.UserId = droiUser.UserId;
            this.session = new HashMap();
            this.session.put("Token", a.f3030c);
            this.session.put("ExpireAt", String.valueOf(a.f3031d));
        } else {
            DroiObject droiUser2 = new DroiUser();
            droiUser2.AuthData = new HashMap();
            droiUser2.AuthData.put(ANONYMOUS_TAG, "1");
            droiUser2.UserId = installationId + deviceId;
            currentUser = droiUser2;
            C0935u c0935u = new C0935u(ANONYMOUS_TAG, droiUser2.getObjectId(), null, droiUser2.toJson(droiError));
            if (!droiError.isOk()) {
                return droiError;
            }
            C0936v a2 = C0939m.m2764a(c0935u, droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
            if (a2.f3043b != 0) {
                if (a2.f3043b == f2749j) {
                    droiError.setCode(DroiError.USER_ALREADY_EXISTS);
                } else {
                    droiError.setCode(a2.f3043b);
                }
                droiError.setAppendedMessage(null);
                return droiError;
            }
            copyFrom(droiUser2);
            this.AuthData = droiUser2.AuthData;
            this.UserId = droiUser2.UserId;
            this.session = new HashMap();
            this.session.put("Token", a2.f3045d);
            this.session.put("ExpireAt", String.valueOf(a2.f3046e));
        }
        return droiError;
    }

    public static DroiUser getCurrentUser() {
        if (currentUser != null) {
            return currentUser;
        }
        try {
            File file = new File(DroiStorageFinder.getPrivatePath(CorePriv.getContext()) + "/user");
            if (file.exists()) {
                currentUser = m2584a(file, DroiUser.class);
            } else if (autoAnonymousUser) {
                currentUser = m2585a(DroiUser.class);
            }
        } catch (Exception e) {
            DroiLog.m2869e(LOG_TAG, e);
        }
        return currentUser;
    }

    public static <T extends DroiUser> T getCurrentUser(Class<T> cls) {
        if (!DroiObject.isExtendedClass((Class) cls)) {
            DroiLog.m2870e(LOG_TAG, cls.getSimpleName() + " is not registered DroiObject extension.");
            return null;
        } else if (currentUser != null && currentUser.getClass().equals(cls)) {
            return currentUser;
        } else {
            try {
                File file = new File(DroiStorageFinder.getPrivatePath(CorePriv.getContext()) + "/user");
                if (file.exists()) {
                    currentUser = m2584a(file, (Class) cls);
                } else if (autoAnonymousUser) {
                    currentUser = m2585a((Class) cls);
                }
            } catch (Exception e) {
                DroiLog.m2869e(LOG_TAG, e);
            }
            return currentUser;
        }
    }

    public static boolean isAutoAnonymousUserEnabled() {
        return autoAnonymousUser;
    }

    public static DroiUser login(String str, String str2, DroiError droiError) {
        return login(str, str2, DroiUser.class, droiError);
    }

    public static <T extends DroiUser> T login(String str, String str2, Class<T> cls, DroiError droiError) {
        if (!DroiObject.isExtendedClass((Class) cls) && droiError != null) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage(cls.getSimpleName() + " is not registered DroiObject extension.");
            return null;
        } else if (str == null || str2 == null || str2.length() == 0) {
            if (droiError != null) {
                droiError.setCode(DroiError.INVALID_PARAMETER);
                droiError.setAppendedMessage("Empty UserId or Password");
            }
            return null;
        } else {
            DroiUser currentUser = getCurrentUser();
            if (currentUser == null || !currentUser.isAuthorized() || currentUser.isAnonymous()) {
                if (droiError == null) {
                    droiError = new DroiError();
                }
                C0931q a = C0939m.m2762a(new C0930p(str, C0944p.m2804f(str2), CorePriv.getInstallationId()), droiError);
                if (!droiError.isOk()) {
                    return null;
                }
                if (a.f3028a != 0 || a.f3030c == null) {
                    if (a.f3028a == f2741b) {
                        droiError.setCode(DroiError.USER_NOT_EXISTS);
                    } else if (a.f3028a == f2742c) {
                        droiError.setCode(DroiError.USER_PASSWORD_INCORRECT);
                    } else if (a.f3028a == f2743d) {
                        droiError.setCode(DroiError.USER_DISABLE);
                    } else {
                        droiError.setCode(a.f3028a);
                    }
                    droiError.setAppendedMessage("Ticket: " + a.f3029b);
                    return null;
                }
                currentUser = (DroiUser) DroiObject.fromJson(a.f3032e, cls);
                if (currentUser == null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage("Form user object fail. class: " + cls.getSimpleName());
                    return null;
                }
                currentUser.session = new HashMap();
                currentUser.session.put("Token", a.f3030c);
                currentUser.session.put("ExpireAt", String.valueOf(a.f3031d));
                droiError.copy(m2583a(currentUser));
                return currentUser;
            }
            if (droiError != null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("Another user had logged in. Please logout first.");
            }
            return null;
        }
    }

    public static boolean loginInBackground(String str, String str2, DroiCallback<DroiUser> droiCallback) {
        return loginInBackground(str, str2, DroiUser.class, droiCallback);
    }

    public static <T extends DroiUser> boolean loginInBackground(String str, String str2, Class<T> cls, final DroiCallback<DroiUser> droiCallback) {
        TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference(new DroiError(DroiError.UNKNOWN_ERROR, "May caused by Exception"));
        final String str3 = str;
        final String str4 = str2;
        final Class<T> cls2 = cls;
        return DroiTask.create(new DroiRunnable() {
            public void run() {
                DroiError droiError = new DroiError();
                atomicReference.set(DroiUser.login(str3, str4, cls2, droiError));
                atomicReference2.set(droiError);
            }
        }).callback(new DroiRunnable() {
            public void run() {
                if (droiCallback != null) {
                    droiCallback.result(atomicReference.get(), (DroiError) atomicReference2.get());
                }
            }
        }, currentTaskDispatcher.name()).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public static boolean loginOAuthAsync(Activity activity, OAuthProvider oAuthProvider, DroiCallback<DroiUser> droiCallback) {
        return loginOAuthAsync(activity, oAuthProvider, droiCallback, DroiUser.class);
    }

    public static <T extends DroiUser> boolean loginOAuthAsync(Activity activity, final OAuthProvider oAuthProvider, final DroiCallback<T> droiCallback, final Class<T> cls) {
        if (DroiObject.isExtendedClass((Class) cls) || droiCallback == null) {
            DroiUser currentUser = getCurrentUser();
            if (currentUser == null || !currentUser.isAuthorized() || currentUser.isAnonymous()) {
                if (!C0907i.m2679a().m2686c()) {
                    DroiLog.m2871i(LOG_TAG, "OAuth appid list is not ready. Fetching.");
                    C0907i.m2679a().m2685b();
                }
                DroiError requestToken = oAuthProvider.requestToken(activity, new DroiCallback<Boolean>() {
                    public void result(Boolean bool, DroiError droiError) {
                        try {
                            if (droiError.isOk() || droiCallback == null) {
                                DroiError droiError2 = new DroiError();
                                DroiUser currentUser = DroiUser.getCurrentUser();
                                if (currentUser == null || !currentUser.isAuthorized() || currentUser.isAnonymous()) {
                                    String str;
                                    String oAuthProviderName = oAuthProvider.getOAuthProviderName();
                                    String token = oAuthProvider.getToken();
                                    String id = oAuthProvider.getId();
                                    if (oAuthProviderName == null || !oAuthProviderName.equals(C0947r.f3088a)) {
                                        str = id;
                                        id = null;
                                    } else {
                                        str = null;
                                    }
                                    C0931q a = C0939m.m2762a(new C0930p(oAuthProviderName, str, id, token, CorePriv.getInstallationId()), droiError2);
                                    if (droiError2.isOk()) {
                                        if (a.m2737a()) {
                                            Object obj = (DroiUser) DroiObject.fromJson(a.f3032e, cls);
                                            if (obj != null) {
                                                obj.session = new HashMap();
                                                obj.session.put("Token", a.f3030c);
                                                obj.session.put("ExpireAt", String.valueOf(a.f3031d));
                                                DroiError a2 = DroiUser.m2583a((DroiUser) obj);
                                                if (droiCallback != null) {
                                                    DroiCallback droiCallback = droiCallback;
                                                    if (!a2.isOk()) {
                                                        obj = null;
                                                    }
                                                    droiCallback.result(obj, a2);
                                                    return;
                                                }
                                                return;
                                            } else if (droiCallback != null) {
                                                droiCallback.result(null, new DroiError(DroiError.ERROR, "Form user object fail. class: " + cls.getSimpleName()));
                                                return;
                                            } else {
                                                return;
                                            }
                                        } else if (droiCallback != null) {
                                            int i = a.f3028a;
                                            if (i == 0) {
                                                i = DroiError.ERROR;
                                            }
                                            droiCallback.result(null, new DroiError(i, "Login with oauth fail."));
                                            return;
                                        } else {
                                            return;
                                        }
                                    } else if (droiCallback != null) {
                                        droiCallback.result(null, droiError2);
                                        return;
                                    } else {
                                        return;
                                    }
                                } else if (droiCallback != null) {
                                    droiCallback.result(null, new DroiError(DroiError.ERROR, "Please logout current user first."));
                                    return;
                                } else {
                                    return;
                                }
                            }
                            droiCallback.result(null, droiError);
                        } catch (Exception e) {
                            if (droiCallback != null) {
                                droiCallback.result(null, new DroiError(DroiError.ERROR, e.toString()));
                            }
                        }
                    }
                });
                if (requestToken.isOk()) {
                    return true;
                }
                if (droiCallback == null) {
                    return false;
                }
                droiCallback.result(null, requestToken);
                return false;
            } else if (droiCallback == null) {
                return false;
            } else {
                droiCallback.result(null, new DroiError(DroiError.ERROR, "Another user had logged in. Please logout first."));
                return false;
            }
        }
        droiCallback.result(null, new DroiError(DroiError.ERROR, cls.getSimpleName() + " is not registered DroiObject extension."));
        return false;
    }

    public static DroiUser loginWithAnonymous(DroiError droiError) {
        DroiUser currentUser = getCurrentUser();
        if (currentUser == null || !currentUser.isAuthorized() || currentUser.isAnonymous()) {
            if (droiError == null) {
                droiError = new DroiError();
            }
            currentUser = getCurrentUser();
            if (currentUser == null || !currentUser.isAuthorized()) {
                String deviceId = CorePriv.getDeviceId();
                String installationId = CorePriv.getInstallationId();
                if (deviceId == null || installationId == null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage("NO ID");
                    return null;
                }
                if (currentUser == null) {
                    currentUser = new DroiUser();
                }
                droiError.copy(currentUser.m2589b());
                if (!droiError.isOk()) {
                    return null;
                }
                m2583a(currentUser);
                return currentUser;
            } else if (currentUser.isAnonymous()) {
                droiError.copy(new DroiError(0, null));
                return currentUser;
            } else {
                droiError.copy(new DroiError(DroiError.ERROR, "Please logout current user first."));
                return null;
            }
        }
        if (droiError != null) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("Another user had logged in. Please logout first.");
        }
        return null;
    }

    public static void setAutoAnonymousUser(boolean z) {
        autoAnonymousUser = z;
    }

    public boolean bindOAuth(Activity activity, final OAuthProvider oAuthProvider, final DroiCallback<Boolean> droiCallback) {
        DroiUser currentUser = getCurrentUser();
        if (currentUser == null || currentUser.isAuthorized() || currentUser.isAnonymous()) {
            if (!C0907i.m2679a().m2686c()) {
                DroiLog.m2871i(LOG_TAG, "OAuth appid list is not ready. Fetching.");
                C0907i.m2679a().m2685b();
            }
            DroiError requestToken = oAuthProvider.requestToken(activity, new DroiCallback<Boolean>(this) {
                final /* synthetic */ DroiUser f2733c;

                public void result(Boolean bool, DroiError droiError) {
                    Object obj = null;
                    if (droiError.isOk()) {
                        DroiObject currentUser = DroiUser.getCurrentUser();
                        if (currentUser == null || currentUser.isAnonymous() || currentUser.isAuthorized()) {
                            if (currentUser == null || (currentUser.isAnonymous() && !currentUser.isAuthorized())) {
                                if (DroiUser.isAutoAnonymousUserEnabled()) {
                                    DroiPermission permission = this.f2733c.getPermission();
                                    try {
                                        DroiError c = currentUser.m2589b();
                                        if (c.isOk()) {
                                            this.f2733c.copyFrom(currentUser);
                                            currentUser.setPermission(permission);
                                        } else {
                                            if (droiCallback != null) {
                                                droiCallback.result(Boolean.valueOf(false), c);
                                            }
                                            currentUser.setPermission(permission);
                                            return;
                                        }
                                    } catch (Throwable th) {
                                        currentUser.setPermission(permission);
                                    }
                                } else if (droiCallback != null) {
                                    droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.USER_NOT_AUTHORIZED, "No valid user to bind OAuth."));
                                    return;
                                } else {
                                    return;
                                }
                            }
                            JSONObject jSONObject = new JSONObject();
                            try {
                                Object a;
                                Object e;
                                Object obj2;
                                Object obj3;
                                String userId = oAuthProvider.getUserId();
                                String password = oAuthProvider.getPassword();
                                String oAuthProviderName = oAuthProvider.getOAuthProviderName();
                                if (this.f2733c.isAnonymous()) {
                                    byte[] bArr = new byte[8];
                                    new SecureRandom().nextBytes(bArr);
                                    a = C0944p.m2786a(bArr);
                                    password = null;
                                } else {
                                    String str = userId;
                                }
                                if (password != null) {
                                    e = C0944p.m2801e(password);
                                } else {
                                    String str2 = password;
                                }
                                boolean z = true;
                                switch (oAuthProviderName.hashCode()) {
                                    case -1707708798:
                                        if (oAuthProviderName.equals(C0947r.f3088a)) {
                                            z = false;
                                            break;
                                        }
                                        break;
                                }
                                switch (z) {
                                    case false:
                                        userId = oAuthProvider.getId();
                                        obj2 = null;
                                        break;
                                    default:
                                        userId = oAuthProvider.getId();
                                        obj2 = userId;
                                        obj3 = null;
                                        obj = oAuthProvider.getToken();
                                        break;
                                }
                                if (a != null) {
                                    jSONObject.put("UserId", a);
                                }
                                if (e != null) {
                                    jSONObject.put("Password", e);
                                }
                                if (obj3 != null) {
                                    jSONObject.put("Code", obj3);
                                }
                                if (obj2 != null) {
                                    jSONObject.put("OpenId", obj2);
                                }
                                if (obj != null) {
                                    jSONObject.put("AccessToken", obj);
                                }
                                jSONObject.put("InstallationId", CorePriv.getInstallationId());
                                jSONObject.put("Token", this.f2733c.getSessionToken());
                                jSONObject.put("AuthProvider", oAuthProviderName);
                                DroiError droiError2 = new DroiError();
                                C0918d a2 = C0939m.m2757a(jSONObject, droiError2);
                                if (droiError2.isOk()) {
                                    if (a2.f2978a != 0) {
                                        droiError2.setCode(a2.f2978a);
                                        droiError2.setAppendedMessage("Ticket: " + a2.f2979b);
                                        if (droiCallback != null) {
                                            droiCallback.result(Boolean.valueOf(false), droiError2);
                                            return;
                                        }
                                        return;
                                    }
                                    DroiUser droiUser = (DroiUser) DroiObject.fromJson(a2.f2980c, DroiUser.class);
                                    this.f2733c.UserId = droiUser.UserId;
                                    this.f2733c.AuthData = droiUser.AuthData;
                                    if (DroiUser.getCurrentUser() == this.f2733c) {
                                        DroiUser.m2583a(this.f2733c);
                                    }
                                    if (droiCallback != null) {
                                        droiCallback.result(Boolean.valueOf(true), new DroiError());
                                    }
                                } else if (droiCallback != null) {
                                    droiCallback.result(Boolean.valueOf(false), droiError2);
                                }
                            } catch (Exception e2) {
                                if (droiCallback != null) {
                                    droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, e2.toString()));
                                }
                            }
                        } else if (droiCallback != null) {
                            droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, "Current user is not authorized."));
                        }
                    } else if (droiCallback != null) {
                        droiCallback.result(Boolean.valueOf(false), droiError);
                    }
                }
            });
            if (requestToken.isOk()) {
                return true;
            }
            if (droiCallback == null) {
                return false;
            }
            droiCallback.result(Boolean.valueOf(false), requestToken);
            return false;
        } else if (droiCallback == null) {
            return false;
        } else {
            droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, "The user is not authorized."));
            return false;
        }
    }

    public void cancelUserBackgroundTask(String str) {
        DroiObject.cancelBackgroundTask(str);
        TaskDispatcher.getDispatcher(DROI_USER_TASK_NAME).killTask(str);
    }

    public DroiError changePassword(String str, String str2) {
        if (this.AuthData != null && this.AuthData.size() > 0) {
            return new DroiError(DroiError.ERROR, "Only normal user can change password.");
        }
        if (!isAuthorized()) {
            return new DroiError(DroiError.ERROR, "User is not login.");
        }
        DroiError droiError = new DroiError();
        C0918d a = C0939m.m2756a(str, str2, getSessionToken(), droiError);
        if (!droiError.isOk() || a.f2978a == 0) {
            return droiError;
        }
        droiError.setCode(a.f2978a);
        droiError.setAppendedMessage("Ticket: " + a.f2979b);
        return droiError;
    }

    public void changePasswordInBackground(final String str, final String str2, final DroiCallback<Boolean> droiCallback) {
        String name = TaskDispatcher.currentTaskDispatcher().name();
        final AtomicReference atomicReference = new AtomicReference(new DroiError(DroiError.UNKNOWN_ERROR, "May caused by Exception"));
        DroiTask.create(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2725d;

            public void run() {
                atomicReference.set(this.f2725d.changePassword(str, str2));
            }
        }).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2721c;

            public void run() {
                if (droiCallback != null) {
                    DroiError droiError = (DroiError) atomicReference.get();
                    droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                }
            }
        }, name).runInBackground("TaskDispatcher_DroiBackgroundThread");
    }

    public DroiError confirmPhoneNumberPinCode(String str) {
        if (!isAuthorized() || isAnonymous()) {
            return new DroiError(DroiError.ERROR, "User is not authorized or anonymous.");
        }
        if (this.PhoneNumVerified.booleanValue()) {
            return new DroiError(DroiError.ERROR, "Had verified.");
        }
        if (this.PhoneNum == null || this.PhoneNum.isEmpty()) {
            return new DroiError(DroiError.ERROR, "Phone number is empty.");
        }
        DroiError droiError = new DroiError();
        C0918d a = C0939m.m2755a(getSessionToken(), str, droiError);
        if (!droiError.isOk()) {
            return droiError;
        }
        if (a.f2978a != 0) {
            droiError.setCode(a.f2978a);
            return droiError;
        }
        droiError.setCode(a.f2978a);
        if (a.f2979b != null) {
            droiError.setAppendedMessage("Ticket: " + a.f2979b);
        }
        this.PhoneNumVerified = Boolean.valueOf(true);
        if (this != currentUser) {
            return droiError;
        }
        m2583a(this);
        return droiError;
    }

    public boolean confirmPhoneNumberPinCodeInBackground(final String str, final DroiCallback<Boolean> droiCallback) {
        TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        final DroiError droiError = new DroiError();
        return DroiTask.create(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2702c;

            public void run() {
                droiError.copy(this.f2702c.confirmPhoneNumberPinCode(str));
            }
        }).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2699c;

            public void run() {
                if (droiCallback != null) {
                    droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                }
            }
        }, currentTaskDispatcher.name()).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    protected void copyFrom(DroiObject droiObject) {
        super.copyFrom(droiObject);
        if (droiObject instanceof DroiUser) {
            this.Enabled = ((DroiUser) droiObject).Enabled;
        }
    }

    public String getEmail() {
        return this.Email;
    }

    public String getPassword() {
        return this.Password;
    }

    public String getPhoneNumber() {
        return this.PhoneNum;
    }

    public String getSessionToken() {
        return this.session == null ? null : (String) this.session.get("Token");
    }

    public String getUserId() {
        return this.UserId;
    }

    public boolean isAnonymous() {
        return this.AuthData != null && this.AuthData.containsKey(ANONYMOUS_TAG);
    }

    public boolean isAuthorized() {
        if (this.session == null) {
            return false;
        }
        long time = new Date().getTime();
        String str = (String) this.session.get("Token");
        boolean z = (Long.parseLong((String) this.session.get("ExpireAt")) <= time || str == null || str.isEmpty()) ? false : true;
        return z;
    }

    public boolean isEmailVerified() {
        return this.EmailVerified.booleanValue();
    }

    public boolean isLoggedIn() {
        if (this.session == null) {
            return false;
        }
        long time = new Date().getTime();
        String str = (String) this.session.get("Token");
        boolean z = (Long.parseLong((String) this.session.get("ExpireAt")) <= time || str == null || str.isEmpty()) ? false : true;
        return z;
    }

    public boolean isPhoneNumVerified() {
        return this.PhoneNumVerified.booleanValue();
    }

    public DroiError logout() {
        DroiError droiError = new DroiError();
        if (this.session != null) {
            C0939m.m2763a(new C0932r(getSessionToken(), getObjectId()), droiError);
            if (!droiError.isOk()) {
                DroiLog.m2874w(LOG_TAG, droiError.toString());
            }
        }
        return m2582a();
    }

    public boolean logoutInBackground(final DroiCallback<Boolean> droiCallback) {
        TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        final AtomicReference atomicReference = new AtomicReference(new DroiError(DroiError.UNKNOWN_ERROR, "May caused by Exception"));
        return DroiTask.create(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2686b;

            public void run() {
                atomicReference.set(this.f2686b.logout());
            }
        }).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2739c;

            public void run() {
                if (droiCallback != null) {
                    DroiError droiError = (DroiError) atomicReference.get();
                    if (droiError == null) {
                        droiError = new DroiError(DroiError.UNKNOWN_ERROR, null);
                    }
                    droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                }
            }
        }, currentTaskDispatcher.name()).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public boolean refreshValidationStatus() {
        DroiError droiError = new DroiError();
        List runQuery = Builder.newBuilder().query(DroiUser.class).cloudStorage().where(C0896c.f2868c, Type.EQ, getObjectId()).build().runQuery(droiError);
        if (!droiError.isOk()) {
            DroiLog.m2874w(LOG_TAG, droiError.toString());
            return false;
        } else if (runQuery == null || runQuery.size() != 1) {
            DroiLog.m2874w(LOG_TAG, "User error. look up fail.");
            return false;
        } else {
            DroiUser droiUser = (DroiUser) runQuery.get(0);
            this.EmailVerified = droiUser.EmailVerified;
            this.PhoneNumVerified = droiUser.PhoneNumVerified;
            return true;
        }
    }

    public boolean refreshValidationStatusInBackground(final DroiCallback<Boolean> droiCallback) {
        String name = TaskDispatcher.currentTaskDispatcher().name();
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        return DroiTask.create(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2707b;

            public void run() {
                atomicInteger.set(this.f2707b.refreshValidationStatus() ? 1 : 0);
            }
        }).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2705c;

            public void run() {
                boolean z = true;
                int i = 0;
                if (atomicInteger.get() != 1) {
                    z = false;
                }
                if (droiCallback != null) {
                    DroiCallback droiCallback = droiCallback;
                    Boolean valueOf = Boolean.valueOf(z);
                    if (!z) {
                        i = DroiError.ERROR;
                    }
                    droiCallback.result(valueOf, new DroiError(i, null));
                }
            }
        }, name).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public DroiError save() {
        if (!DroiObject.isExtendedClass(getClass())) {
            return new DroiError(DroiError.ERROR, getClass().getSimpleName() + " is not registered DroiObject extension.");
        }
        DroiError droiError;
        if (this.Password != null && isLocalStorage()) {
            droiError = new DroiError();
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("Not allowed to save password in local storage.");
            return droiError;
        } else if (this.Password != null && this.AuthData != null) {
            return new DroiError(DroiError.INVALID_PARAMETER, "Only normal user can change password.");
        } else {
            droiError = super.save();
            return (droiError.isOk() && currentUser == this) ? m2583a(this) : droiError;
        }
    }

    public DroiError saveEventually() {
        if (this.Password == null) {
            return super.saveEventually();
        }
        DroiError droiError = new DroiError();
        droiError.setCode(DroiError.ERROR);
        droiError.setAppendedMessage("Not allowed change password for saveEventually.");
        return droiError;
    }

    public String saveInBackground(DroiCallback<Boolean> droiCallback) {
        if (!isAuthorized()) {
            return null;
        }
        if (this.Password != null && isLocalStorage()) {
            return null;
        }
        currentUser = this;
        return super.saveInBackground(droiCallback);
    }

    public void setEmail(String str) {
        if (str == null || !str.equals(this.Email)) {
            this.EmailVerified = Boolean.valueOf(false);
        }
        this.Email = str;
    }

    public void setPassword(String str) {
        this.Password = str;
    }

    public void setPhoneNumber(String str) {
        if (str == null || !str.equals(this.PhoneNum)) {
            this.PhoneNumVerified = Boolean.valueOf(false);
        }
        this.PhoneNum = str;
    }

    public void setUserId(String str) {
        this.UserId = str;
    }

    public DroiError signUp() {
        String str;
        DroiPermission permission;
        if (!DroiObject.isExtendedClass(getClass())) {
            return new DroiError(DroiError.ERROR, getClass().getSimpleName() + " is not registered DroiObject extension.");
        }
        if (this.UserId == null || this.Password == null || this.Password.length() == 0) {
            return new DroiError(DroiError.INVALID_PARAMETER, "Empty UserId or Password");
        }
        try {
            DroiUser currentUser = getCurrentUser();
            if (currentUser != null && currentUser.isAuthorized() && !currentUser.isAnonymous()) {
                return new DroiError(DroiError.ERROR, "Please logout current user first.");
            }
            if (isAuthorized() && !isAnonymous()) {
                return new DroiError(DroiError.ERROR, "Please logout current user first.");
            }
            DroiError b;
            str = this.UserId;
            permission = getPermission();
            if (!isAuthorized()) {
                b = m2589b();
                if (!b.isOk()) {
                    this.AuthData = null;
                    this.UserId = str;
                    setPermission(permission);
                    return b;
                }
            }
            this.AuthData = null;
            this.UserId = str;
            setPermission(permission);
            currentUser = this;
            b = save();
            if (b.isOk()) {
                b = changePassword("", this.Password);
                if (b.isOk()) {
                    return m2583a(this);
                }
                logout();
                return b;
            }
            logout();
            return b;
        } catch (Exception e) {
            return new DroiError(DroiError.ERROR, e.toString());
        } catch (Throwable th) {
            this.AuthData = null;
            this.UserId = str;
            setPermission(permission);
        }
    }

    public String signUpInBackground(final DroiCallback<Boolean> droiCallback) {
        String uuid = UUID.randomUUID().toString();
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread");
        final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        return dispatcher.enqueueTask(new Runnable(this) {
            final /* synthetic */ DroiUser f2730c;

            public void run() {
                final DroiError signUp = this.f2730c.signUp();
                if (droiCallback != null) {
                    currentTaskDispatcher.enqueueTask(new Runnable(this) {
                        final /* synthetic */ C08586 f2727b;

                        public void run() {
                            droiCallback.result(Boolean.valueOf(signUp.isOk()), signUp);
                        }
                    });
                }
            }
        }, uuid) ? uuid : null;
    }

    public boolean unbindOAuth(Activity activity, final OAuthProvider oAuthProvider, final DroiCallback<Boolean> droiCallback) {
        DroiUser currentUser = getCurrentUser();
        if (currentUser == null || currentUser.isAuthorized()) {
            if (!C0907i.m2679a().m2686c()) {
                DroiLog.m2871i(LOG_TAG, "OAuth appid list is not ready. Fetching.");
                C0907i.m2679a().m2685b();
            }
            DroiError requestToken = oAuthProvider.requestToken(activity, new DroiCallback<Boolean>(this) {
                final /* synthetic */ DroiUser f2736c;

                public void result(Boolean bool, DroiError droiError) {
                    Object obj = null;
                    if (droiError.isOk()) {
                        DroiObject currentUser = DroiUser.getCurrentUser();
                        if (currentUser == null || currentUser.isAuthorized()) {
                            if (currentUser == null || (currentUser.isAnonymous() && !currentUser.isAuthorized())) {
                                DroiPermission permission = this.f2736c.getPermission();
                                try {
                                    DroiError c = currentUser.m2589b();
                                    if (c.isOk()) {
                                        this.f2736c.copyFrom(currentUser);
                                        this.f2736c.setPermission(permission);
                                    } else {
                                        if (droiCallback != null) {
                                            droiCallback.result(Boolean.valueOf(false), c);
                                        }
                                        this.f2736c.setPermission(permission);
                                        return;
                                    }
                                } catch (Throwable th) {
                                    this.f2736c.setPermission(permission);
                                }
                            }
                            JSONObject jSONObject = new JSONObject();
                            try {
                                Object a;
                                Object f;
                                Object obj2;
                                Object obj3;
                                String userId = oAuthProvider.getUserId();
                                String password = oAuthProvider.getPassword();
                                String oAuthProviderName = oAuthProvider.getOAuthProviderName();
                                if (this.f2736c.isAnonymous()) {
                                    byte[] bArr = new byte[8];
                                    new SecureRandom().nextBytes(bArr);
                                    a = C0944p.m2786a(bArr);
                                    password = null;
                                } else {
                                    String str = userId;
                                }
                                if (password != null) {
                                    f = C0944p.m2804f(password);
                                } else {
                                    String str2 = password;
                                }
                                boolean z = true;
                                switch (oAuthProviderName.hashCode()) {
                                    case -1707708798:
                                        if (oAuthProviderName.equals(C0947r.f3088a)) {
                                            z = false;
                                            break;
                                        }
                                        break;
                                }
                                switch (z) {
                                    case false:
                                        userId = oAuthProvider.getId();
                                        obj2 = null;
                                        break;
                                    default:
                                        userId = oAuthProvider.getId();
                                        obj2 = userId;
                                        obj3 = null;
                                        obj = oAuthProvider.getToken();
                                        break;
                                }
                                if (a != null) {
                                    jSONObject.put("UserId", a);
                                }
                                if (f != null) {
                                    jSONObject.put("Password", f);
                                }
                                if (obj3 != null) {
                                    jSONObject.put("Code", obj3);
                                }
                                if (obj2 != null) {
                                    jSONObject.put("OpenId", obj2);
                                }
                                if (obj != null) {
                                    jSONObject.put("AccessToken", obj);
                                }
                                jSONObject.put("InstallationId", CorePriv.getInstallationId());
                                jSONObject.put("Token", this.f2736c.getSessionToken());
                                jSONObject.put("AuthProvider", oAuthProviderName);
                                DroiError droiError2 = new DroiError();
                                C0918d b = C0939m.m2771b(jSONObject, droiError2);
                                if (droiError2.isOk()) {
                                    if (b.f2978a != 0) {
                                        droiError2.setCode(b.f2978a);
                                        droiError2.setAppendedMessage("Ticket: " + b.f2979b);
                                        if (droiCallback != null) {
                                            droiCallback.result(Boolean.valueOf(false), droiError2);
                                            return;
                                        }
                                        return;
                                    }
                                    DroiUser droiUser = (DroiUser) DroiObject.fromJson(b.f2980c, DroiUser.class);
                                    this.f2736c.UserId = droiUser.UserId;
                                    this.f2736c.AuthData = droiUser.AuthData;
                                    if (DroiUser.getCurrentUser() == this.f2736c) {
                                        DroiUser.m2583a(this.f2736c);
                                    }
                                    if (droiCallback != null) {
                                        droiCallback.result(Boolean.valueOf(true), new DroiError());
                                    }
                                } else if (droiCallback != null) {
                                    droiCallback.result(Boolean.valueOf(false), droiError2);
                                }
                            } catch (Exception e) {
                                if (droiCallback != null) {
                                    droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, e.toString()));
                                }
                            }
                        } else if (droiCallback != null) {
                            droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, "Current user is not authorized."));
                        }
                    } else if (droiCallback != null) {
                        droiCallback.result(Boolean.valueOf(false), droiError);
                    }
                }
            });
            if (requestToken.isOk()) {
                return true;
            }
            if (droiCallback == null) {
                return false;
            }
            droiCallback.result(Boolean.valueOf(false), requestToken);
            return false;
        } else if (droiCallback == null) {
            return false;
        } else {
            droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, "The user is not authorized."));
            return false;
        }
    }

    public DroiError validateEmail() {
        if (!isAuthorized() || isAnonymous()) {
            return new DroiError(DroiError.ERROR, "User is not authorized or anonymous.");
        }
        if (this.EmailVerified.booleanValue()) {
            return new DroiError(DroiError.ERROR, "The email had verified.");
        }
        if (this.Email == null || this.Email.isEmpty()) {
            return new DroiError(DroiError.ERROR, "Email is empty.");
        }
        DroiError droiError = new DroiError();
        C0918d a = C0939m.m2754a(getSessionToken(), droiError);
        if (!droiError.isOk()) {
            return droiError;
        }
        droiError.setCode(a.f2978a);
        String str = null;
        if (a.f2979b != null) {
            str = "Ticket: " + a.f2979b;
        }
        droiError.setAppendedMessage(str);
        return droiError;
    }

    public boolean validateEmailInBackground(final DroiCallback<Boolean> droiCallback) {
        TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        final DroiError droiError = new DroiError();
        return DroiTask.create(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2691b;

            public void run() {
                droiError.copy(this.f2691b.validateEmail());
            }
        }).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2689c;

            public void run() {
                if (droiCallback != null) {
                    droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                }
            }
        }, currentTaskDispatcher.name()).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public DroiError validatePhoneNumber() {
        if (!isAuthorized() || isAnonymous()) {
            return new DroiError(DroiError.ERROR, "User is not authorized or anonymous.");
        }
        if (this.PhoneNumVerified.booleanValue()) {
            return new DroiError(DroiError.ERROR, "Had verified.");
        }
        if (this.PhoneNum == null || this.PhoneNum.isEmpty()) {
            return new DroiError(DroiError.ERROR, "Phone number is empty.");
        }
        DroiError droiError = new DroiError();
        C0918d b = C0939m.m2770b(getSessionToken(), droiError);
        if (!droiError.isOk()) {
            return droiError;
        }
        droiError.setCode(b.f2978a);
        if (b.f2979b == null) {
            return droiError;
        }
        droiError.setAppendedMessage("Ticket: " + b.f2979b);
        return droiError;
    }

    public boolean validatePhoneNumberInBackground(final DroiCallback<Boolean> droiCallback) {
        TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        final DroiError droiError = new DroiError();
        return DroiTask.create(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2696b;

            public void run() {
                droiError.copy(this.f2696b.validatePhoneNumber());
            }
        }).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiUser f2694c;

            public void run() {
                if (droiCallback != null) {
                    droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                }
            }
        }, currentTaskDispatcher.name()).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.Password);
        parcel.writeString(this.session != null ? (String) this.session.get("Token") : null);
        parcel.writeString(this.session != null ? (String) this.session.get("ExpireAt") : null);
    }
}
