package bolts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class MeasurementEvent {
    public static final String APP_LINK_NAVIGATE_IN_EVENT_NAME = "al_nav_in";
    public static final String APP_LINK_NAVIGATE_OUT_EVENT_NAME = "al_nav_out";
    public static final String MEASUREMENT_EVENT_ARGS_KEY = "event_args";
    public static final String MEASUREMENT_EVENT_NAME_KEY = "event_name";
    public static final String MEASUREMENT_EVENT_NOTIFICATION_NAME = "com.parse.bolts.measurement_event";
    private Context appContext;
    private Bundle args;
    private String name;

    static void sendBroadcastEvent(Context context, String name, Intent intent, Map<String, String> extraLoggingData) {
        Bundle logData = new Bundle();
        if (intent != null) {
            Bundle event = AppLinks.getAppLinkData(intent);
            if (event != null) {
                logData = getApplinkLogData(context, name, event, intent);
            } else {
                Uri key = intent.getData();
                if (key != null) {
                    logData.putString("intentData", key.toString());
                }
                Bundle intentExtras = intent.getExtras();
                if (intentExtras != null) {
                    for (String key1 : intentExtras.keySet()) {
                        logData.putString(key1, objectToJSONString(intentExtras.get(key1)));
                    }
                }
            }
        }
        if (extraLoggingData != null) {
            for (String key2 : extraLoggingData.keySet()) {
                logData.putString(key2, (String) extraLoggingData.get(key2));
            }
        }
        new MeasurementEvent(context, name, logData).sendBroadcast();
    }

    private MeasurementEvent(Context context, String eventName, Bundle eventArgs) {
        this.appContext = context.getApplicationContext();
        this.name = eventName;
        this.args = eventArgs;
    }

    private void sendBroadcast() {
        if (this.name == null) {
            Log.d(getClass().getName(), "Event name is required");
        }
        try {
            Class e = Class.forName("android.support.v4.content.LocalBroadcastManager");
            Method methodGetInstance = e.getMethod("getInstance", new Class[]{Context.class});
            Method methodSendBroadcast = e.getMethod("sendBroadcast", new Class[]{Intent.class});
            Object localBroadcastManager = methodGetInstance.invoke(null, new Object[]{this.appContext});
            Intent event = new Intent(MEASUREMENT_EVENT_NOTIFICATION_NAME);
            event.putExtra(MEASUREMENT_EVENT_NAME_KEY, this.name);
            event.putExtra(MEASUREMENT_EVENT_ARGS_KEY, this.args);
            methodSendBroadcast.invoke(localBroadcastManager, new Object[]{event});
        } catch (Exception e2) {
            Log.d(getClass().getName(), "LocalBroadcastManager in android support library is required to raise bolts event.");
        }
    }

    private static Bundle getApplinkLogData(Context context, String eventName, Bundle appLinkData, Intent applinkIntent) {
        Bundle logData = new Bundle();
        ComponentName resolvedActivity = applinkIntent.resolveActivity(context.getPackageManager());
        if (resolvedActivity != null) {
            logData.putString("class", resolvedActivity.getShortClassName());
        }
        if (APP_LINK_NAVIGATE_OUT_EVENT_NAME.equals(eventName)) {
            if (resolvedActivity != null) {
                logData.putString("package", resolvedActivity.getPackageName());
            }
            if (applinkIntent.getData() != null) {
                logData.putString("outputURL", applinkIntent.getData().toString());
            }
            if (applinkIntent.getScheme() != null) {
                logData.putString("outputURLScheme", applinkIntent.getScheme());
            }
        } else if (APP_LINK_NAVIGATE_IN_EVENT_NAME.equals(eventName)) {
            if (applinkIntent.getData() != null) {
                logData.putString("inputURL", applinkIntent.getData().toString());
            }
            if (applinkIntent.getScheme() != null) {
                logData.putString("inputURLScheme", applinkIntent.getScheme());
            }
        }
        for (String key : appLinkData.keySet()) {
            Object o = appLinkData.get(key);
            if (o instanceof Bundle) {
                for (String targetURI1 : ((Bundle) o).keySet()) {
                    String logValue1 = objectToJSONString(((Bundle) o).get(targetURI1));
                    if (key.equals("referer_app_link")) {
                        if (targetURI1.equalsIgnoreCase("url")) {
                            logData.putString("refererURL", logValue1);
                        } else if (targetURI1.equalsIgnoreCase("app_name")) {
                            logData.putString("refererAppName", logValue1);
                        } else if (targetURI1.equalsIgnoreCase("package")) {
                            logData.putString("sourceApplication", logValue1);
                        }
                    }
                    logData.putString(key + "/" + targetURI1, logValue1);
                }
            } else {
                String logValue = objectToJSONString(o);
                if (key.equals("target_url")) {
                    Uri targetURI = Uri.parse(logValue);
                    logData.putString("targetURL", targetURI.toString());
                    logData.putString("targetURLHost", targetURI.getHost());
                } else {
                    logData.putString(key, logValue);
                }
            }
        }
        return logData;
    }

    private static String objectToJSONString(Object o) {
        if (o == null) {
            return null;
        }
        if ((o instanceof JSONArray) || (o instanceof JSONObject)) {
            return o.toString();
        }
        try {
            if (o instanceof Collection) {
                return new JSONArray((Collection) o).toString();
            }
            return o instanceof Map ? new JSONObject((Map) o).toString() : o.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
