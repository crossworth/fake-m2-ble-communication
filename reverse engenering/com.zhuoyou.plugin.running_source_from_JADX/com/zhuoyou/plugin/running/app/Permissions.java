package com.zhuoyou.plugin.running.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog.OnClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@TargetApi(20)
public class Permissions {
    private static final String PACKAGE_URL_SCHEME = "package:";
    public static final HashMap<String, String> PERMISSIONS = new HashMap();
    public static final String PERMISSION_CALENDAR = "android.permission.READ_CALENDAR";
    public static final String PERMISSION_CALL_PHONE = "android.permission.MODIFY_PHONE_STATE";
    public static final String PERMISSION_CAMERA = "android.permission.CAMERA";
    public static final String PERMISSION_CONTACTS = "android.permission.READ_CONTACTS";
    public static final String PERMISSION_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    public static final String PERMISSION_MICROPHONE = "android.permission.RECORD_AUDIO";
    public static final String PERMISSION_PHONE = "android.permission.READ_PHONE_STATE";
    public static final String PERMISSION_READ_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String PERMISSION_SENSORS = "android.permission.BODY_SENSORS";
    public static final String PERMISSION_SMS = "android.permission.READ_SMS";
    public static final String PERMISSION_WRITE_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    public static final int REQUEST_CALENDAR = 3;
    public static final int REQUEST_CAMERA = 4;
    public static final int REQUEST_CONTACTS = 5;
    public static final int REQUEST_LOCATION = 2;
    public static final int REQUEST_MICROPHONE = 6;
    public static final int REQUEST_MULTIPLE = 0;
    public static final int REQUEST_PERMISSION_SETTING = 10;
    public static final int REQUEST_PHONE = 7;
    public static final int REQUEST_SENSORS = 8;
    public static final int REQUEST_SMS = 9;
    public static final int REQUEST_STORAGE = 1;

    static {
        PERMISSIONS.put(PERMISSION_READ_STORAGE, "读取存储空间");
        PERMISSIONS.put(PERMISSION_WRITE_STORAGE, "写入存储空间");
        PERMISSIONS.put(PERMISSION_LOCATION, "获取位置信息");
        PERMISSIONS.put(PERMISSION_CALENDAR, "读取日历信息");
        PERMISSIONS.put(PERMISSION_CAMERA, "使用摄像头");
        PERMISSIONS.put(PERMISSION_CONTACTS, "读取联系人");
        PERMISSIONS.put(PERMISSION_MICROPHONE, "使用麦克风");
        PERMISSIONS.put(PERMISSION_PHONE, "获取电话状态");
        PERMISSIONS.put(PERMISSION_SENSORS, "使用传感器");
        PERMISSIONS.put(PERMISSION_SMS, "读取短信");
        PERMISSIONS.put(PERMISSION_CALL_PHONE, "挂断电话");
    }

    public static List<String> getPermissionList(Activity activity) {
        ArrayList<String> list = new ArrayList();
        addPermissionToList(activity, list, PERMISSION_READ_STORAGE);
        addPermissionToList(activity, list, PERMISSION_LOCATION);
        addPermissionToList(activity, list, PERMISSION_PHONE);
        addPermissionToList(activity, list, PERMISSION_CAMERA);
        addPermissionToList(activity, list, PERMISSION_SMS);
        addPermissionToList(activity, list, PERMISSION_CONTACTS);
        addPermissionToList(activity, list, PERMISSION_MICROPHONE);
        return list;
    }

    public static boolean checkPermissions(Context mContext, String... permission) {
        for (String item : permission) {
            if (ContextCompat.checkSelfPermission(mContext, item) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPermissions(Context mContext, List<String> permission) {
        for (String item : permission) {
            if (ContextCompat.checkSelfPermission(mContext, item) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPermission(Context mContext, String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == 0;
    }

    public static boolean shouldShowRationale(Context mContext, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permission);
    }

    public static boolean addPermissionToList(Context mContext, List<String> list, String permission) {
        if (ContextCompat.checkSelfPermission(mContext, permission) != 0) {
            list.add(permission);
            if (!shouldShowRationale(mContext, permission)) {
                return false;
            }
        }
        return true;
    }

    public static String getPermissionsString(List<String> list, String title) {
        StringBuilder msg = new StringBuilder();
        msg.append(title).append("\n");
        for (int i = 0; i < list.size(); i++) {
            msg.append(i + 1).append("、").append((String) PERMISSIONS.get(list.get(i)));
            if (i != list.size() - 1) {
                msg.append("\n");
            }
        }
        msg.append("\n");
        return msg.toString();
    }

    public static void showRequestDialog(final Context context, final List<String> permissions) {
        MyAlertDialog dialog = new MyAlertDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("开启卓易健康");
        dialog.setMessage(getPermissionsString(permissions, "为了您能更好的使用卓易健康，我们需要申请以下权限，请选择允许：\n"));
        dialog.setLeftButton("下一步", new OnClickListener() {
            public void onClick(int witch) {
                ActivityCompat.requestPermissions((Activity) context, (String[]) permissions.toArray(new String[permissions.size()]), 0);
            }
        });
        dialog.getDialog().setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                ((Activity) context).setResult(0);
                ((Activity) context).finish();
            }
        });
        dialog.show();
    }

    public static void showRequestFailDialog(final Context context, final List<String> permissions) {
        MyAlertDialog dialog = new MyAlertDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("权限未被允许");
        dialog.setMessage(getPermissionsString(permissions, "请允许以下权限的申请，否则卓易健康将无法正常运行：\n"));
        dialog.setRightButton("确定", new OnClickListener() {
            public void onClick(int witch) {
                ActivityCompat.requestPermissions((Activity) context, (String[]) permissions.toArray(new String[permissions.size()]), 0);
            }
        });
        dialog.setLeftButton("取消", new OnClickListener() {
            public void onClick(int witch) {
                ((Activity) context).setResult(0);
                ((Activity) context).finish();
            }
        });
        dialog.show();
    }

    public static void showRequestRationaleDialog(final Context context, String permission) {
        ArrayList<String> list = new ArrayList();
        list.add(permission);
        MyAlertDialog dialog = new MyAlertDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("缺少必要权限");
        dialog.setMessage(getPermissionsString(list, "我们需要以下权限，否则卓易健康将无法正常运行，请开启权限后在使用卓易健康。\n\n设置路径：系统设置->应用->卓易健康->权限\n"));
        dialog.setRightButton("去设置", new OnClickListener() {
            public void onClick(int witch) {
                Intent intent = new Intent();
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.parse(Permissions.PACKAGE_URL_SCHEME + context.getPackageName()));
                ((Activity) context).startActivityForResult(intent, 10);
            }
        });
        dialog.setLeftButton("取消", new OnClickListener() {
            public void onClick(int witch) {
                ((Activity) context).setResult(0);
                ((Activity) context).finish();
            }
        });
        dialog.show();
    }

    public static ArrayList<String> checkPermissionResult(Context mContext, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ArrayList<String> list = new ArrayList();
        int i = 0;
        while (i < permissions.length) {
            if (grantResults[i] != 0 && ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permissions[i])) {
                list.add(permissions[i]);
            }
            i++;
        }
        return list;
    }
}
