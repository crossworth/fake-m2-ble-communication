package com.droi.sdk.core;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.C0899e;
import com.droi.sdk.core.priv.C0939m;
import com.droi.sdk.internal.DroiLog;
import org.json.JSONException;
import org.json.JSONObject;

public class DroiCloudCache {
    private static String f2453a = "DroiCloudCache";

    public static String get(String str, DroiError droiError) {
        if (str == null || str.isEmpty() || str.length() > 32) {
            if (droiError != null) {
                droiError.setCode(DroiError.INVALID_PARAMETER);
            }
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ops", 0);
            jSONObject.put("k", str);
        } catch (JSONException e) {
            DroiLog.m2870e(f2453a, "There is an exception. The exception is " + e.toString());
        }
        if (droiError != null) {
            droiError.setCode(0);
        }
        String b = C0939m.m2772b(C0899e.f2908m, jSONObject.toString(), droiError);
        if (droiError != null && !droiError.isOk()) {
            DroiLog.m2870e(f2453a, droiError.toString());
            return "";
        } else if (b == null) {
            DroiLog.m2870e(f2453a, "There is no return value");
            return "";
        } else {
            String str2 = "";
            try {
                JSONObject jSONObject2 = new JSONObject(b);
                int i = jSONObject2.getInt("Code");
                if (droiError != null) {
                    droiError.setCode(i);
                    if (i != 0) {
                        droiError.setAppendedMessage("Ticket:" + jSONObject2.getString("Ticket"));
                    }
                }
                return i == 0 ? jSONObject2.getString("Result") : str2;
            } catch (JSONException e2) {
                DroiLog.m2870e(f2453a, droiError.toString());
                return str2;
            }
        }
    }

    public static DroiError getInBackground(String str, final DroiCallback<String> droiCallback) {
        DroiError droiError = new DroiError(0, "");
        if (str == null || str.isEmpty() || str.length() > 32) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
        } else {
            final JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ops", 0);
                jSONObject.put("k", str);
            } catch (JSONException e) {
                DroiLog.m2870e(f2453a, "There is an exception. The exception is " + e.toString());
            }
            final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
            TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread").enqueueTask(new Runnable() {
                public void run() {
                    final DroiError droiError = new DroiError(0, "");
                    String b = C0939m.m2772b(C0899e.f2908m, jSONObject.toString(), droiError);
                    String str = "";
                    if (droiError.isOk()) {
                        try {
                            JSONObject jSONObject = new JSONObject(b);
                            int i = jSONObject.getInt("Code");
                            if (droiError != null) {
                                droiError.setCode(i);
                                if (i != 0) {
                                    droiError.setAppendedMessage("Ticket:" + jSONObject.getString("Ticket"));
                                }
                            }
                            if (i == 0) {
                                str = jSONObject.getString("Result");
                            }
                        } catch (JSONException e) {
                            DroiLog.m2870e(DroiCloudCache.f2453a, droiError.toString());
                        }
                    }
                    if (droiCallback != null) {
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C07961 f2439c;

                            public void run() {
                                droiCallback.result(str, droiError);
                            }
                        });
                    }
                }
            });
        }
        return droiError;
    }

    public static DroiError remove(String str) {
        DroiError droiError = new DroiError();
        droiError.setCode(0);
        if (str == null || str.isEmpty() || str.length() > 32) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
        } else {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ops", 2);
                jSONObject.put("k", str);
            } catch (JSONException e) {
                DroiLog.m2870e(f2453a, "There is an exception. The exception is " + e.toString());
            }
            String b = C0939m.m2772b(C0899e.f2908m, jSONObject.toString(), droiError);
            if (droiError != null && !droiError.isOk()) {
                DroiLog.m2870e(f2453a, droiError.toString());
            } else if (b == null) {
                DroiLog.m2870e(f2453a, "There is no return value");
            } else {
                try {
                    jSONObject = new JSONObject(b);
                    int i = jSONObject.getInt("Code");
                    droiError.setCode(i);
                    if (i != 0) {
                        droiError.setAppendedMessage("Ticket:" + jSONObject.getString("Ticket"));
                    }
                } catch (JSONException e2) {
                    DroiLog.m2870e(f2453a, droiError.toString());
                    droiError.setCode(DroiError.INVALID_PARAMETER);
                }
            }
        }
        return droiError;
    }

    public static DroiError removeInBackground(String str, final DroiCallback<Boolean> droiCallback) {
        DroiError droiError = new DroiError(0, "");
        if (str == null || str.isEmpty() || str.length() > 32) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
        } else {
            final JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ops", 2);
                jSONObject.put("k", str);
            } catch (JSONException e) {
                DroiLog.m2870e(f2453a, "There is an exception. The exception is " + e.toString());
            }
            final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
            TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread").enqueueTask(new Runnable() {
                public void run() {
                    final DroiError droiError = new DroiError(0, "");
                    try {
                        JSONObject jSONObject = new JSONObject(C0939m.m2772b(C0899e.f2908m, jSONObject.toString(), droiError));
                        int i = jSONObject.getInt("Code");
                        droiError.setCode(i);
                        if (i != 0) {
                            droiError.setAppendedMessage("Ticket:" + jSONObject.getString("Ticket"));
                        }
                    } catch (JSONException e) {
                        DroiLog.m2870e(DroiCloudCache.f2453a, droiError.toString());
                        droiError.setCode(DroiError.INVALID_PARAMETER);
                    }
                    if (droiCallback != null) {
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C08003 f2449b;

                            public void run() {
                                droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                            }
                        });
                    }
                }
            });
        }
        return droiError;
    }

    public static DroiError set(String str, String str2) {
        DroiError droiError = new DroiError();
        droiError.setCode(0);
        if (str == null || str.isEmpty() || str.length() > 32) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
        } else if (str2 == null || str2.length() > 1024) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
        } else {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ops", 1);
                jSONObject.put("k", str);
                jSONObject.put("v", str2);
            } catch (JSONException e) {
                DroiLog.m2870e(f2453a, "There is an exception. The exception is " + e.toString());
            }
            String b = C0939m.m2772b(C0899e.f2908m, jSONObject.toString(), droiError);
            if (droiError != null && !droiError.isOk()) {
                DroiLog.m2870e(f2453a, droiError.toString());
            } else if (b == null) {
                DroiLog.m2870e(f2453a, "There is no return value");
            } else {
                try {
                    jSONObject = new JSONObject(b);
                    int i = jSONObject.getInt("Code");
                    droiError.setCode(i);
                    if (i != 0) {
                        droiError.setAppendedMessage("Ticket:" + jSONObject.getString("Ticket"));
                    }
                } catch (JSONException e2) {
                    DroiLog.m2870e(f2453a, droiError.toString());
                }
            }
        }
        return droiError;
    }

    public static DroiError setInBackground(String str, String str2, final DroiCallback<Boolean> droiCallback) {
        DroiError droiError = new DroiError(0, "");
        if (str == null || str.isEmpty() || str.length() > 32) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
        } else if (str2 == null || str2.length() > 1024) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
        } else {
            final JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ops", 1);
                jSONObject.put("k", str);
                jSONObject.put("v", str2);
            } catch (JSONException e) {
                DroiLog.m2870e(f2453a, "There is an exception. The exception is " + e.toString());
            }
            final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
            TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread").enqueueTask(new Runnable() {
                public void run() {
                    final DroiError droiError = new DroiError(0, "");
                    try {
                        JSONObject jSONObject = new JSONObject(C0939m.m2772b(C0899e.f2908m, jSONObject.toString(), droiError));
                        int i = jSONObject.getInt("Code");
                        droiError.setCode(i);
                        if (i != 0) {
                            droiError.setAppendedMessage("Ticket:" + jSONObject.getString("Ticket"));
                        }
                    } catch (JSONException e) {
                        DroiLog.m2870e(DroiCloudCache.f2453a, droiError.toString());
                    }
                    if (droiCallback != null) {
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C07982 f2444b;

                            public void run() {
                                droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                            }
                        });
                    }
                }
            });
        }
        return droiError;
    }
}
