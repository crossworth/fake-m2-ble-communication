package com.zhuoyou.plugin.running.tools;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.droi.btlib.service.BtDevice;
import com.droi.sdk.selfupdate.DroiInappUpdateListener;
import com.droi.sdk.selfupdate.DroiInappUpdateResponse;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.FirmwareUpdateActivity;
import com.zhuoyou.plugin.running.bean.EventFirmware;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog.OnClickListener;
import org.greenrobot.eventbus.EventBus;

public class FirmwareUtils {
    private static final String TAG = "zhuqichao";
    private static Handler mHandler = new Handler();

    private static String getFileName(BtDevice btDevice) {
        if (btDevice.getVersion().startsWith("M2_2_")) {
            return "M2_2.bin";
        }
        if (btDevice.getVersion().startsWith("M2_3_")) {
            return "M2_3.bin";
        }
        if (btDevice.getVersion().startsWith("M2_4_")) {
            return btDevice.getName() + ".bin";
        }
        if (btDevice.getVersion().startsWith("M2_")) {
            return "M2_1.bin";
        }
        return btDevice.getName() + ".bin";
    }

    private static int getFileVersion(BtDevice btDevice) {
        String[] versionStr = btDevice.getVersion().split("_");
        int verson = Integer.parseInt(versionStr[versionStr.length - 1]);
        Log.i("zhuqichao", "verson=" + verson);
        return verson;
    }

    public static void checkDeviceUpdate(BtDevice btDevice, Context context, DroiInappUpdateListener listener) {
        if (btDevice == null || !btDevice.supportFirmwareVersion() || TextUtils.isEmpty(btDevice.getVersion()) || TextUtils.isEmpty(btDevice.getName())) {
            listener.onUpdateReturned(0, null);
        } else {
            DroiUpdate.inappUpdate(context, getFileName(btDevice), getFileVersion(btDevice), listener);
        }
    }

    public static void checkDeviceUpdate(BtDevice btDevice, Context context, View statePoint, boolean showDialog, boolean idHand) {
        if (idHand) {
            Tools.makeToast((int) C1680R.string.firmware_update_checking);
        }
        final boolean z = idHand;
        final View view = statePoint;
        final boolean z2 = showDialog;
        final Context context2 = context;
        final BtDevice btDevice2 = btDevice;
        checkDeviceUpdate(btDevice, context, new DroiInappUpdateListener() {
            public void onUpdateReturned(final int i, final DroiInappUpdateResponse response) {
                FirmwareUtils.mHandler.post(new Runnable() {
                    public void run() {
                        switch (i) {
                            case 0:
                            case 2:
                            case 3:
                                Log.i("zhuqichao", "没有更新:" + i);
                                if (z) {
                                    Tools.makeToast((int) C1680R.string.firmware_no_update);
                                }
                                EventBus.getDefault().post(new EventFirmware(false));
                                if (view != null) {
                                    view.setVisibility(8);
                                    return;
                                }
                                return;
                            case 1:
                                Log.i("zhuqichao", "发现更新");
                                EventBus.getDefault().post(new EventFirmware(true));
                                if (view != null) {
                                    view.setVisibility(0);
                                }
                                if (z2) {
                                    FirmwareUtils.showUpdateDialog(context2, response, btDevice2);
                                    return;
                                }
                                return;
                            default:
                                return;
                        }
                    }
                });
            }
        });
    }

    private static void showUpdateDialog(final Context context, final DroiInappUpdateResponse response, final BtDevice btDevice) {
        MyAlertDialog dialog = new MyAlertDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle((int) C1680R.string.firmware_ble_send);
        dialog.setMessage((int) C1680R.string.firmware_have_update);
        dialog.setLeftButton((int) C1680R.string.firmware_upgrade_no, null);
        dialog.setRightButton((int) C1680R.string.firmware_upgrade_yes, new OnClickListener() {
            public void onClick(int witch) {
                Intent intent = new Intent(context, FirmwareUpdateActivity.class);
                intent.putExtra(FirmwareUpdateActivity.EXTRA_KEY_RESPONSE, response);
                if (btDevice.getVersion().startsWith("M2_2_")) {
                    intent.putExtra(FirmwareUpdateActivity.EXTRA_KEY_DEVICE_NAME, "M2_2");
                } else if (btDevice.getVersion().startsWith("M2_3_")) {
                    intent.putExtra(FirmwareUpdateActivity.EXTRA_KEY_DEVICE_NAME, "M2_3");
                } else if (btDevice.getVersion().startsWith("M2_4_")) {
                    intent.putExtra(FirmwareUpdateActivity.EXTRA_KEY_DEVICE_NAME, btDevice.getName());
                } else if (btDevice.getVersion().startsWith("M2_")) {
                    intent.putExtra(FirmwareUpdateActivity.EXTRA_KEY_DEVICE_NAME, "M2_1");
                } else {
                    intent.putExtra(FirmwareUpdateActivity.EXTRA_KEY_DEVICE_NAME, btDevice.getName());
                }
                intent.putExtra(FirmwareUpdateActivity.EXTRA_KEY_UPDATE_TYLE, btDevice.getUpdateType());
                intent.putExtra(FirmwareUpdateActivity.EXTRA_KEY_VERSION, FirmwareUtils.getFileVersion(btDevice));
                context.startActivity(intent);
            }
        });
        dialog.show();
    }
}
