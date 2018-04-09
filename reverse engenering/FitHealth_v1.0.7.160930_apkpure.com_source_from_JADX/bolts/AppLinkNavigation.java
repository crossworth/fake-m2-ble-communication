package bolts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import bolts.AppLink.Target;
import com.facebook.GraphResponse;
import com.facebook.internal.AnalyticsEvents;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkNavigation {
    private static final String KEY_NAME_REFERER_APP_LINK = "referer_app_link";
    private static final String KEY_NAME_REFERER_APP_LINK_APP_NAME = "app_name";
    private static final String KEY_NAME_REFERER_APP_LINK_PACKAGE = "package";
    private static final String KEY_NAME_USER_AGENT = "user_agent";
    private static final String KEY_NAME_VERSION = "version";
    private static final String VERSION = "1.0";
    private static AppLinkResolver defaultResolver;
    private final AppLink appLink;
    private final Bundle appLinkData;
    private final Bundle extras;

    public enum NavigationResult {
        FAILED(Mailbox.FAILED, false),
        WEB(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_WEB, true),
        APP("app", true);
        
        private String code;
        private boolean succeeded;

        public String getCode() {
            return this.code;
        }

        public boolean isSucceeded() {
            return this.succeeded;
        }

        private NavigationResult(String code, boolean success) {
            this.code = code;
            this.succeeded = success;
        }
    }

    public AppLinkNavigation(AppLink appLink, Bundle extras, Bundle appLinkData) {
        if (appLink == null) {
            throw new IllegalArgumentException("appLink must not be null.");
        }
        if (extras == null) {
            extras = new Bundle();
        }
        if (appLinkData == null) {
            appLinkData = new Bundle();
        }
        this.appLink = appLink;
        this.extras = extras;
        this.appLinkData = appLinkData;
    }

    public AppLink getAppLink() {
        return this.appLink;
    }

    public Bundle getAppLinkData() {
        return this.appLinkData;
    }

    public Bundle getExtras() {
        return this.extras;
    }

    private Bundle buildAppLinkDataForNavigation(Context context) {
        Bundle data = new Bundle();
        Bundle refererAppLinkData = new Bundle();
        if (context != null) {
            String refererAppPackage = context.getPackageName();
            if (refererAppPackage != null) {
                refererAppLinkData.putString(KEY_NAME_REFERER_APP_LINK_PACKAGE, refererAppPackage);
            }
            ApplicationInfo appInfo = context.getApplicationInfo();
            if (appInfo != null) {
                String refererAppName = context.getString(appInfo.labelRes);
                if (refererAppName != null) {
                    refererAppLinkData.putString("app_name", refererAppName);
                }
            }
        }
        data.putAll(getAppLinkData());
        data.putString("target_url", getAppLink().getSourceUrl().toString());
        data.putString("version", "1.0");
        data.putString(KEY_NAME_USER_AGENT, "Bolts Android 1.1.4");
        data.putBundle(KEY_NAME_REFERER_APP_LINK, refererAppLinkData);
        data.putBundle("extras", getExtras());
        return data;
    }

    private Object getJSONValue(Object value) throws JSONException {
        int i = 0;
        if (value instanceof Bundle) {
            return getJSONForBundle((Bundle) value);
        }
        if (value instanceof CharSequence) {
            return value.toString();
        }
        Object array;
        if (value instanceof List) {
            array = new JSONArray();
            for (Object listValue : (List) value) {
                array.put(getJSONValue(listValue));
            }
            return array;
        } else if (value instanceof SparseArray) {
            array = new JSONArray();
            SparseArray<?> sparseValue = (SparseArray) value;
            for (int i2 = 0; i2 < sparseValue.size(); i2++) {
                array.put(sparseValue.keyAt(i2), getJSONValue(sparseValue.valueAt(i2)));
            }
            return array;
        } else if (value instanceof Character) {
            return value.toString();
        } else {
            if (value instanceof Boolean) {
                return value;
            }
            if (value instanceof Number) {
                if ((value instanceof Double) || (value instanceof Float)) {
                    return Double.valueOf(((Number) value).doubleValue());
                }
                return Long.valueOf(((Number) value).longValue());
            } else if (value instanceof boolean[]) {
                array = new JSONArray();
                boolean[] zArr = (boolean[]) value;
                r7 = zArr.length;
                while (i < r7) {
                    array.put(getJSONValue(Boolean.valueOf(zArr[i])));
                    i++;
                }
                return array;
            } else if (value instanceof char[]) {
                array = new JSONArray();
                char[] cArr = (char[]) value;
                r7 = cArr.length;
                while (i < r7) {
                    array.put(getJSONValue(Character.valueOf(cArr[i])));
                    i++;
                }
                return array;
            } else if (value instanceof CharSequence[]) {
                array = new JSONArray();
                CharSequence[] charSequenceArr = (CharSequence[]) value;
                r7 = charSequenceArr.length;
                while (i < r7) {
                    array.put(getJSONValue(charSequenceArr[i]));
                    i++;
                }
                return array;
            } else if (value instanceof double[]) {
                array = new JSONArray();
                double[] dArr = (double[]) value;
                r7 = dArr.length;
                while (i < r7) {
                    array.put(getJSONValue(Double.valueOf(dArr[i])));
                    i++;
                }
                return array;
            } else if (value instanceof float[]) {
                array = new JSONArray();
                float[] fArr = (float[]) value;
                r7 = fArr.length;
                while (i < r7) {
                    array.put(getJSONValue(Float.valueOf(fArr[i])));
                    i++;
                }
                return array;
            } else if (value instanceof int[]) {
                array = new JSONArray();
                int[] iArr = (int[]) value;
                r7 = iArr.length;
                while (i < r7) {
                    array.put(getJSONValue(Integer.valueOf(iArr[i])));
                    i++;
                }
                return array;
            } else if (value instanceof long[]) {
                array = new JSONArray();
                long[] jArr = (long[]) value;
                r7 = jArr.length;
                while (i < r7) {
                    array.put(getJSONValue(Long.valueOf(jArr[i])));
                    i++;
                }
                return array;
            } else if (value instanceof short[]) {
                array = new JSONArray();
                short[] sArr = (short[]) value;
                r7 = sArr.length;
                while (i < r7) {
                    array.put(getJSONValue(Short.valueOf(sArr[i])));
                    i++;
                }
                return array;
            } else if (!(value instanceof String[])) {
                return null;
            } else {
                array = new JSONArray();
                String[] strArr = (String[]) value;
                r7 = strArr.length;
                while (i < r7) {
                    array.put(getJSONValue(strArr[i]));
                    i++;
                }
                return array;
            }
        }
    }

    private JSONObject getJSONForBundle(Bundle bundle) throws JSONException {
        JSONObject root = new JSONObject();
        for (String key : bundle.keySet()) {
            root.put(key, getJSONValue(bundle.get(key)));
        }
        return root;
    }

    public NavigationResult navigate(Context context) {
        PackageManager pm = context.getPackageManager();
        Bundle finalAppLinkData = buildAppLinkDataForNavigation(context);
        Intent eligibleTargetIntent = null;
        for (Target target : getAppLink().getTargets()) {
            Intent targetIntent = new Intent("android.intent.action.VIEW");
            if (target.getUrl() != null) {
                targetIntent.setData(target.getUrl());
            } else {
                targetIntent.setData(this.appLink.getSourceUrl());
            }
            targetIntent.setPackage(target.getPackageName());
            if (target.getClassName() != null) {
                targetIntent.setClassName(target.getPackageName(), target.getClassName());
            }
            targetIntent.putExtra("al_applink_data", finalAppLinkData);
            if (pm.resolveActivity(targetIntent, 65536) != null) {
                eligibleTargetIntent = targetIntent;
                break;
            }
        }
        Intent outIntent = null;
        NavigationResult result = NavigationResult.FAILED;
        if (eligibleTargetIntent != null) {
            outIntent = eligibleTargetIntent;
            result = NavigationResult.APP;
        } else {
            Uri webUrl = getAppLink().getWebUrl();
            if (webUrl != null) {
                try {
                    outIntent = new Intent("android.intent.action.VIEW", webUrl.buildUpon().appendQueryParameter("al_applink_data", getJSONForBundle(finalAppLinkData).toString()).build());
                    result = NavigationResult.WEB;
                } catch (JSONException e) {
                    sendAppLinkNavigateEventBroadcast(context, eligibleTargetIntent, NavigationResult.FAILED, e);
                    throw new RuntimeException(e);
                }
            }
        }
        sendAppLinkNavigateEventBroadcast(context, outIntent, result, null);
        if (outIntent != null) {
            context.startActivity(outIntent);
        }
        return result;
    }

    private void sendAppLinkNavigateEventBroadcast(Context context, Intent intent, NavigationResult type, JSONException e) {
        Map<String, String> extraLoggingData = new HashMap();
        if (e != null) {
            extraLoggingData.put("error", e.getLocalizedMessage());
        }
        extraLoggingData.put(GraphResponse.SUCCESS_KEY, type.isSucceeded() ? "1" : "0");
        extraLoggingData.put("type", type.getCode());
        MeasurementEvent.sendBroadcastEvent(context, MeasurementEvent.APP_LINK_NAVIGATE_OUT_EVENT_NAME, intent, extraLoggingData);
    }

    public static void setDefaultResolver(AppLinkResolver resolver) {
        defaultResolver = resolver;
    }

    public static AppLinkResolver getDefaultResolver() {
        return defaultResolver;
    }

    private static AppLinkResolver getResolver(Context context) {
        if (getDefaultResolver() != null) {
            return getDefaultResolver();
        }
        return new WebViewAppLinkResolver(context);
    }

    public static NavigationResult navigate(Context context, AppLink appLink) {
        return new AppLinkNavigation(appLink, null, null).navigate(context);
    }

    public static Task<NavigationResult> navigateInBackground(final Context context, Uri destination, AppLinkResolver resolver) {
        return resolver.getAppLinkFromUrlInBackground(destination).onSuccess(new Continuation<AppLink, NavigationResult>() {
            public NavigationResult then(Task<AppLink> task) throws Exception {
                return AppLinkNavigation.navigate(context, (AppLink) task.getResult());
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL destination, AppLinkResolver resolver) {
        return navigateInBackground(context, Uri.parse(destination.toString()), resolver);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String destinationUrl, AppLinkResolver resolver) {
        return navigateInBackground(context, Uri.parse(destinationUrl), resolver);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, Uri destination) {
        return navigateInBackground(context, destination, getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL destination) {
        return navigateInBackground(context, destination, getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String destinationUrl) {
        return navigateInBackground(context, destinationUrl, getResolver(context));
    }
}
