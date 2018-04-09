package no.nordicsemi.android.dfu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.ble.GattInfo;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.bluetooth.data.Util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.UUID;
import no.nordicsemi.android.dfu.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.exception.DfuException;
import no.nordicsemi.android.dfu.exception.HexFileValidationException;
import no.nordicsemi.android.dfu.exception.UnknownResponseException;
import no.nordicsemi.android.dfu.exception.UploadAbortedException;
import no.nordicsemi.android.log.ILogSession;
import no.nordicsemi.android.log.Logger;
import p031u.aly.cv;

public abstract class DfuBaseService extends IntentService {
    public static final int ACTION_ABORT = 2;
    public static final int ACTION_PAUSE = 0;
    public static final int ACTION_RESUME = 1;
    public static final String BROADCAST_ACTION = "no.nordicsemi.android.dfu.broadcast.BROADCAST_ACTION";
    public static final String BROADCAST_ERROR = "no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR";
    public static final String BROADCAST_LOG = "no.nordicsemi.android.dfu.broadcast.BROADCAST_LOG";
    public static final String BROADCAST_PROGRESS = "no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS";
    private static final UUID CLIENT_CHARACTERISTIC_CONFIG = new UUID(45088566677504L, GattInfo.leastSigBits);
    private static final UUID DFU_CONTROL_POINT_UUID = new UUID(23300500811742L, 1523193452336828707L);
    private static final UUID DFU_PACKET_UUID = new UUID(23304795779038L, 1523193452336828707L);
    private static final UUID DFU_SERVICE_UUID = new UUID(23296205844446L, 1523193452336828707L);
    public static final int DFU_STATUS_CRC_ERROR = 5;
    public static final int DFU_STATUS_DATA_SIZE_EXCEEDS_LIMIT = 4;
    public static final int DFU_STATUS_INVALID_STATE = 2;
    public static final int DFU_STATUS_NOT_SUPPORTED = 3;
    public static final int DFU_STATUS_OPERATION_FAILED = 6;
    public static final int DFU_STATUS_SUCCESS = 1;
    private static final UUID DFU_VERSION = new UUID(23313385713630L, 1523193452336828707L);
    public static final int ERROR_CHARACTERISTICS_NOT_FOUND = 4103;
    public static final int ERROR_CONNECTION_MASK = 16384;
    public static final int ERROR_DEVICE_DISCONNECTED = 4096;
    public static final int ERROR_FILE_ERROR = 4098;
    public static final int ERROR_FILE_INVALID = 4099;
    public static final int ERROR_FILE_IO_EXCEPTION = 4100;
    public static final int ERROR_FILE_NOT_FOUND = 4097;
    public static final int ERROR_FILE_TYPE_UNSUPPORTED = 4105;
    public static final int ERROR_INVALID_RESPONSE = 4104;
    public static final int ERROR_MASK = 4096;
    public static final int ERROR_REMOTE_MASK = 8192;
    public static final int ERROR_SERVICE_DISCOVERY_NOT_STARTED = 4101;
    public static final int ERROR_SERVICE_NOT_FOUND = 4102;
    public static final String EXTRA_ACTION = "no.nordicsemi.android.dfu.extra.EXTRA_ACTION";
    public static final String EXTRA_AVG_SPEED_B_PER_MS = "no.nordicsemi.android.dfu.extra.EXTRA_AVG_SPEED_B_PER_MS";
    public static final String EXTRA_DATA = "no.nordicsemi.android.dfu.extra.EXTRA_DATA";
    public static final String EXTRA_DEVICE_ADDRESS = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS";
    public static final String EXTRA_DEVICE_NAME = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME";
    public static final String EXTRA_FILE_MIME_TYPE = "no.nordicsemi.android.dfu.extra.EXTRA_MIME_TYPE";
    public static final String EXTRA_FILE_PATH = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH";
    public static final String EXTRA_FILE_TYPE = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE";
    public static final String EXTRA_FILE_URI = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_URI";
    public static final String EXTRA_INIT_FILE_PATH = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_PATH";
    public static final String EXTRA_INIT_FILE_URI = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_URI";
    public static final String EXTRA_LOG_LEVEL = "no.nordicsemi.android.dfu.extra.EXTRA_LOG_LEVEL";
    public static final String EXTRA_LOG_MESSAGE = "no.nordicsemi.android.dfu.extra.EXTRA_LOG_INFO";
    public static final String EXTRA_LOG_URI = "no.nordicsemi.android.dfu.extra.EXTRA_LOG_URI";
    public static final String EXTRA_PARTS_TOTAL = "no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL";
    public static final String EXTRA_PART_CURRENT = "no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT";
    public static final String EXTRA_PROGRESS = "no.nordicsemi.android.dfu.extra.EXTRA_PROGRESS";
    public static final String EXTRA_RESTORE_BOND = "no.nordicsemi.android.dfu.extra.EXTRA_RESTORE_BOND";
    public static final String EXTRA_SPEED_B_PER_MS = "no.nordicsemi.android.dfu.extra.EXTRA_SPEED_B_PER_MS";
    private static final UUID GENERIC_ATTRIBUTE_SERVICE_UUID = new UUID(26392574038016L, GattInfo.leastSigBits);
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static final int INDICATIONS = 2;
    private static final int MAX_PACKET_SIZE = 20;
    public static final String MIME_TYPE_OCTET_STREAM = "application/octet-stream";
    public static final String MIME_TYPE_ZIP = "application/zip";
    private static final int NOTIFICATIONS = 1;
    public static final int NOTIFICATION_ID = 283;
    private static final byte[] OP_CODE_ACTIVATE_AND_RESET = new byte[]{(byte) 5};
    private static final int OP_CODE_ACTIVATE_AND_RESET_KEY = 5;
    private static final byte[] OP_CODE_INIT_DFU_PARAMS_COMPLETE = new byte[]{(byte) 2, (byte) 1};
    private static final int OP_CODE_INIT_DFU_PARAMS_KEY = 2;
    private static final byte[] OP_CODE_INIT_DFU_PARAMS_START = new byte[]{(byte) 2, (byte) 0};
    private static final int OP_CODE_PACKET_RECEIPT_NOTIF_KEY = 17;
    private static final byte[] OP_CODE_PACKET_RECEIPT_NOTIF_REQ = new byte[]{(byte) 8, (byte) 0, (byte) 0};
    private static final int OP_CODE_PACKET_RECEIPT_NOTIF_REQ_KEY = 8;
    private static final byte[] OP_CODE_RECEIVE_FIRMWARE_IMAGE = new byte[]{(byte) 3};
    private static final int OP_CODE_RECEIVE_FIRMWARE_IMAGE_KEY = 3;
    private static final byte[] OP_CODE_RESET = new byte[]{(byte) 6};
    private static final int OP_CODE_RESET_KEY = 6;
    private static final int OP_CODE_RESPONSE_CODE_KEY = 16;
    private static final byte[] OP_CODE_START_DFU = new byte[]{(byte) 1, (byte) 0};
    private static final int OP_CODE_START_DFU_KEY = 1;
    private static final byte[] OP_CODE_VALIDATE = new byte[]{(byte) 4};
    private static final int OP_CODE_VALIDATE_KEY = 4;
    public static final int PROGRESS_ABORTED = -7;
    public static final int PROGRESS_COMPLETED = -6;
    public static final int PROGRESS_CONNECTING = -1;
    public static final int PROGRESS_DISCONNECTING = -5;
    public static final int PROGRESS_ENABLING_DFU_MODE = -3;
    public static final int PROGRESS_STARTING = -2;
    public static final int PROGRESS_VALIDATING = -4;
    private static final UUID SERVICE_CHANGED_UUID = new UUID(46200963207168L, GattInfo.leastSigBits);
    private static final int STATE_CLOSED = -5;
    private static final int STATE_CONNECTED = -2;
    private static final int STATE_CONNECTED_AND_READY = -3;
    private static final int STATE_CONNECTING = -1;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_DISCONNECTING = -4;
    private static final String TAG = "DfuService";
    public static final int TYPE_APPLICATION = 4;
    public static final int TYPE_AUTO = 0;
    public static final int TYPE_BOOTLOADER = 2;
    public static final int TYPE_SOFT_DEVICE = 1;
    private boolean mAborted;
    private BluetoothAdapter mBluetoothAdapter;
    private final BroadcastReceiver mBondStateBroadcastReceiver = new C14483();
    private byte[] mBuffer = new byte[20];
    private int mBytesConfirmed;
    private int mBytesSent;
    private int mConnectionState;
    private final BroadcastReceiver mConnectionStateBroadcastReceiver = new C14472();
    private String mDeviceAddress;
    private String mDeviceName;
    private final BroadcastReceiver mDfuActionReceiver = new C14461();
    private int mError;
    private int mFileType;
    private final BluetoothGattCallback mGattCallback = new C14494();
    private int mImageSizeInBytes;
    private boolean mImageSizeSent;
    private boolean mInitPacketSent;
    private InputStream mInputStream;
    private int mLastBytesSent;
    private int mLastProgress = -1;
    private long mLastProgressTime;
    private final Object mLock = new Object();
    private ILogSession mLogSession;
    private boolean mNotificationsEnabled;
    private int mPacketsBeforeNotification = 10;
    private int mPacketsSentSinceNotification;
    private int mPartCurrent;
    private int mPartsTotal;
    private boolean mPaused;
    private byte[] mReceivedData = null;
    private boolean mRemoteErrorOccured;
    private boolean mRequestCompleted;
    private boolean mResetRequestSent;
    private boolean mServiceChangedIndicationsEnabled;
    private long mStartTime;

    class C14461 extends BroadcastReceiver {
        C14461() {
        }

        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra(DfuBaseService.EXTRA_ACTION, 0)) {
                case 0:
                    DfuBaseService.this.mPaused = true;
                    return;
                case 1:
                    DfuBaseService.this.mPaused = false;
                    synchronized (DfuBaseService.this.mLock) {
                        DfuBaseService.this.mLock.notifyAll();
                    }
                    return;
                case 2:
                    DfuBaseService.this.mPaused = false;
                    DfuBaseService.this.mAborted = true;
                    synchronized (DfuBaseService.this.mLock) {
                        DfuBaseService.this.mLock.notifyAll();
                    }
                    return;
                default:
                    return;
            }
        }
    }

    class C14472 extends BroadcastReceiver {
        C14472() {
        }

        public void onReceive(Context context, Intent intent) {
            if (((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")).getAddress().equals(DfuBaseService.this.mDeviceAddress)) {
                DfuBaseService.this.logi("Action received: " + intent.getAction());
                DfuBaseService.this.mConnectionState = 0;
                synchronized (DfuBaseService.this.mLock) {
                    DfuBaseService.this.mLock.notifyAll();
                }
            }
        }
    }

    class C14483 extends BroadcastReceiver {
        C14483() {
        }

        public void onReceive(Context context, Intent intent) {
            if (((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")).getAddress().equals(DfuBaseService.this.mDeviceAddress) && intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1) != 11) {
                DfuBaseService.this.mRequestCompleted = true;
                synchronized (DfuBaseService.this.mLock) {
                    DfuBaseService.this.mLock.notifyAll();
                }
            }
        }
    }

    class C14494 extends BluetoothGattCallback {
        C14494() {
        }

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (status != 0) {
                DfuBaseService.this.loge("Connection state change error: " + status + " newState: " + newState);
                DfuBaseService.this.mPaused = false;
                DfuBaseService.this.mError = status | 16384;
            } else if (newState == 2) {
                DfuBaseService.this.logi("Connected to GATT server");
                DfuBaseService.this.mConnectionState = -2;
                if (gatt.getDevice().getBondState() == 12) {
                    try {
                        synchronized (this) {
                            DfuBaseService.this.logd("Waiting 1600 ms for a possible Service Changed indication...");
                            wait(1600);
                        }
                    } catch (InterruptedException e) {
                    }
                }
                boolean success = gatt.discoverServices();
                DfuBaseService.this.logi("Attempting to start service discovery... " + (success ? "succeed" : Mailbox.FAILED));
                if (!success) {
                    DfuBaseService.this.mError = DfuBaseService.ERROR_SERVICE_DISCOVERY_NOT_STARTED;
                } else {
                    return;
                }
            } else if (newState == 0) {
                DfuBaseService.this.logi("Disconnected from GATT server");
                DfuBaseService.this.mPaused = false;
                DfuBaseService.this.mConnectionState = 0;
            }
            synchronized (DfuBaseService.this.mLock) {
                DfuBaseService.this.mLock.notifyAll();
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == 0) {
                DfuBaseService.this.logi("Services discovered");
                DfuBaseService.this.mConnectionState = -3;
            } else {
                DfuBaseService.this.loge("Service discovery error: " + status);
                DfuBaseService.this.mError = status | 16384;
            }
            synchronized (DfuBaseService.this.mLock) {
                DfuBaseService.this.mLock.notifyAll();
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            boolean z = true;
            if (status != 0) {
                DfuBaseService.this.loge("Descriptor write error: " + status);
                DfuBaseService.this.mError = status | 16384;
            } else if (DfuBaseService.CLIENT_CHARACTERISTIC_CONFIG.equals(descriptor.getUuid())) {
                DfuBaseService dfuBaseService;
                if (DfuBaseService.SERVICE_CHANGED_UUID.equals(descriptor.getCharacteristic().getUuid())) {
                    dfuBaseService = DfuBaseService.this;
                    if (descriptor.getValue()[0] != (byte) 2) {
                        z = false;
                    }
                    dfuBaseService.mServiceChangedIndicationsEnabled = z;
                } else {
                    dfuBaseService = DfuBaseService.this;
                    if (descriptor.getValue()[0] != (byte) 1) {
                        z = false;
                    }
                    dfuBaseService.mNotificationsEnabled = z;
                }
            }
            synchronized (DfuBaseService.this.mLock) {
                DfuBaseService.this.mLock.notifyAll();
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
                if (!DfuBaseService.DFU_PACKET_UUID.equals(characteristic.getUuid())) {
                    DfuBaseService.this.sendLogBroadcast(5, "Data written to " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
                    DfuBaseService.this.mRequestCompleted = true;
                } else if (DfuBaseService.this.mImageSizeSent && DfuBaseService.this.mInitPacketSent) {
                    DfuBaseService.this.mBytesSent = DfuBaseService.this.mBytesSent + characteristic.getValue().length;
                    DfuBaseService.this.mPacketsSentSinceNotification = DfuBaseService.this.mPacketsSentSinceNotification + 1;
                    boolean notificationExpected = DfuBaseService.this.mPacketsBeforeNotification > 0 && DfuBaseService.this.mPacketsSentSinceNotification == DfuBaseService.this.mPacketsBeforeNotification;
                    boolean lastPacketTransfered;
                    if (DfuBaseService.this.mBytesSent == DfuBaseService.this.mImageSizeInBytes) {
                        lastPacketTransfered = true;
                    } else {
                        lastPacketTransfered = false;
                    }
                    if (!notificationExpected && !lastPacketTransfered) {
                        try {
                            DfuBaseService.this.waitIfPaused();
                            if (DfuBaseService.this.mAborted || DfuBaseService.this.mError != 0 || DfuBaseService.this.mRemoteErrorOccured || DfuBaseService.this.mResetRequestSent) {
                                synchronized (DfuBaseService.this.mLock) {
                                    DfuBaseService.this.sendLogBroadcast(15, "Upload terminated");
                                    DfuBaseService.this.mLock.notifyAll();
                                }
                                return;
                            }
                            byte[] buffer = DfuBaseService.this.mBuffer;
                            DfuBaseService.this.writePacket(gatt, characteristic, buffer, DfuBaseService.this.mInputStream.read(buffer));
                            DfuBaseService.this.updateProgressNotification();
                            return;
                        } catch (HexFileValidationException e) {
                            DfuBaseService.this.loge("Invalid HEX file");
                            DfuBaseService.this.mError = 4099;
                        } catch (IOException e2) {
                            DfuBaseService.this.loge("Error while reading the input stream", e2);
                            DfuBaseService.this.mError = DfuBaseService.ERROR_FILE_IO_EXCEPTION;
                        }
                    } else {
                        return;
                    }
                } else if (!DfuBaseService.this.mImageSizeSent) {
                    DfuBaseService.this.sendLogBroadcast(5, "Data written to " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
                    DfuBaseService.this.mImageSizeSent = true;
                } else if (!DfuBaseService.this.mInitPacketSent) {
                    DfuBaseService.this.sendLogBroadcast(5, "Data written to " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
                    DfuBaseService.this.mInitPacketSent = true;
                }
            } else if (DfuBaseService.this.mResetRequestSent) {
                DfuBaseService.this.mRequestCompleted = true;
            } else {
                DfuBaseService.this.loge("Characteristic write error: " + status);
                DfuBaseService.this.mError = status | 16384;
            }
            synchronized (DfuBaseService.this.mLock) {
                DfuBaseService.this.mLock.notifyAll();
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
                DfuBaseService.this.sendLogBroadcast(5, "Read Response received from " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
                DfuBaseService.this.mReceivedData = characteristic.getValue();
                DfuBaseService.this.mRequestCompleted = true;
            } else {
                DfuBaseService.this.loge("Characteristic read error: " + status);
                DfuBaseService.this.mError = status | 16384;
            }
            synchronized (DfuBaseService.this.mLock) {
                DfuBaseService.this.mLock.notifyAll();
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            switch (characteristic.getIntValue(17, 0).intValue()) {
                case 17:
                    BluetoothGattCharacteristic packetCharacteristic = gatt.getService(DfuBaseService.DFU_SERVICE_UUID).getCharacteristic(DfuBaseService.DFU_PACKET_UUID);
                    try {
                        DfuBaseService.this.mBytesConfirmed = characteristic.getIntValue(20, 1).intValue();
                        DfuBaseService.this.mPacketsSentSinceNotification = 0;
                        DfuBaseService.this.waitIfPaused();
                        if (DfuBaseService.this.mAborted || DfuBaseService.this.mError != 0 || DfuBaseService.this.mRemoteErrorOccured || DfuBaseService.this.mResetRequestSent) {
                            DfuBaseService.this.sendLogBroadcast(15, "Upload terminated");
                            break;
                        }
                        byte[] buffer = DfuBaseService.this.mBuffer;
                        DfuBaseService.this.writePacket(gatt, packetCharacteristic, buffer, DfuBaseService.this.mInputStream.read(buffer));
                        DfuBaseService.this.updateProgressNotification();
                        return;
                    } catch (HexFileValidationException e) {
                        DfuBaseService.this.loge("Invalid HEX file");
                        DfuBaseService.this.mError = 4099;
                        break;
                    } catch (IOException e2) {
                        DfuBaseService.this.loge("Error while reading the input stream", e2);
                        DfuBaseService.this.mError = DfuBaseService.ERROR_FILE_IO_EXCEPTION;
                        break;
                    }
                    break;
                default:
                    if (!DfuBaseService.this.mRemoteErrorOccured) {
                        if (characteristic.getIntValue(17, 2).intValue() != 1) {
                            DfuBaseService.this.mRemoteErrorOccured = true;
                        }
                        DfuBaseService.this.sendLogBroadcast(5, "Notification received from " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
                        DfuBaseService.this.mReceivedData = characteristic.getValue();
                        break;
                    }
                    break;
            }
            synchronized (DfuBaseService.this.mLock) {
                DfuBaseService.this.mLock.notifyAll();
            }
        }

        public String parse(BluetoothGattCharacteristic characteristic) {
            byte[] data = characteristic.getValue();
            if (data == null) {
                return "";
            }
            int length = data.length;
            if (length == 0) {
                return "";
            }
            char[] out = new char[((length * 3) - 1)];
            for (int j = 0; j < length; j++) {
                int v = data[j] & 255;
                out[j * 3] = DfuBaseService.HEX_ARRAY[v >>> 4];
                out[(j * 3) + 1] = DfuBaseService.HEX_ARRAY[v & 15];
                if (j != length - 1) {
                    out[(j * 3) + 2] = '-';
                }
            }
            return new String(out);
        }
    }

    protected abstract Class<? extends Activity> getNotificationTarget();

    public DfuBaseService() {
        super(TAG);
    }

    public void onCreate() {
        super.onCreate();
        initialize();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        IntentFilter actionFilter = makeDfuActionIntentFilter();
        manager.registerReceiver(this.mDfuActionReceiver, actionFilter);
        registerReceiver(this.mDfuActionReceiver, actionFilter);
        registerReceiver(this.mConnectionStateBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED"));
        registerReceiver(this.mBondStateBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
    }

    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mDfuActionReceiver);
        unregisterReceiver(this.mDfuActionReceiver);
        unregisterReceiver(this.mConnectionStateBroadcastReceiver);
        unregisterReceiver(this.mBondStateBroadcastReceiver);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onHandleIntent(android.content.Intent r62) {
        /*
        r61 = this;
        r44 = android.preference.PreferenceManager.getDefaultSharedPreferences(r61);
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS";
        r0 = r62;
        r16 = r0.getStringExtra(r6);
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME";
        r0 = r62;
        r17 = r0.getStringExtra(r6);
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH";
        r0 = r62;
        r24 = r0.getStringExtra(r6);
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_URI";
        r0 = r62;
        r26 = r0.getParcelableExtra(r6);
        r26 = (android.net.Uri) r26;
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_PATH";
        r0 = r62;
        r29 = r0.getStringExtra(r6);
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_URI";
        r0 = r62;
        r30 = r0.getParcelableExtra(r6);
        r30 = (android.net.Uri) r30;
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_LOG_URI";
        r0 = r62;
        r34 = r0.getParcelableExtra(r6);
        r34 = (android.net.Uri) r34;
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE";
        r57 = 0;
        r0 = r62;
        r1 = r57;
        r25 = r0.getIntExtra(r6, r1);
        if (r24 == 0) goto L_0x0066;
    L_0x0050:
        if (r25 != 0) goto L_0x0066;
    L_0x0052:
        r6 = java.util.Locale.US;
        r0 = r24;
        r6 = r0.toLowerCase(r6);
        r57 = "zip";
        r0 = r57;
        r6 = r6.endsWith(r0);
        if (r6 == 0) goto L_0x01dc;
    L_0x0064:
        r25 = 0;
    L_0x0066:
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_MIME_TYPE";
        r0 = r62;
        r38 = r0.getStringExtra(r6);
        if (r38 == 0) goto L_0x01e0;
    L_0x0070:
        r6 = "hello";
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r58 = "deviceAddress:";
        r57 = r57.append(r58);
        r0 = r57;
        r1 = r16;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r57;
        android.util.Log.i(r6, r0);
        r6 = "hello";
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r58 = "deviceName:";
        r57 = r57.append(r58);
        r0 = r57;
        r1 = r17;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r57;
        android.util.Log.i(r6, r0);
        r6 = "hello";
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r58 = "filePath:";
        r57 = r57.append(r58);
        r0 = r57;
        r1 = r24;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r57;
        android.util.Log.i(r6, r0);
        r6 = "hello";
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r58 = "fileUri:";
        r57 = r57.append(r58);
        r0 = r57;
        r1 = r26;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r57;
        android.util.Log.i(r6, r0);
        r6 = "hello";
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r58 = "initFilePath:";
        r57 = r57.append(r58);
        r0 = r57;
        r1 = r29;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r57;
        android.util.Log.i(r6, r0);
        r6 = "hello";
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r58 = "initFileUri:";
        r57 = r57.append(r58);
        r0 = r57;
        r1 = r30;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r57;
        android.util.Log.i(r6, r0);
        r6 = "hello";
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r58 = "logUri:";
        r57 = r57.append(r58);
        r0 = r57;
        r1 = r34;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r57;
        android.util.Log.i(r6, r0);
        r6 = "hello";
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r58 = "fileType:";
        r57 = r57.append(r58);
        r0 = r57;
        r1 = r25;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r57;
        android.util.Log.i(r6, r0);
        r6 = "hello";
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r58 = "mimeType:";
        r57 = r57.append(r58);
        r0 = r57;
        r1 = r38;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r57;
        android.util.Log.i(r6, r0);
        r0 = r61;
        r1 = r34;
        r6 = no.nordicsemi.android.log.Logger.openSession(r0, r1);
        r0 = r61;
        r0.mLogSession = r6;
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT";
        r57 = 1;
        r0 = r62;
        r1 = r57;
        r6 = r0.getIntExtra(r6, r1);
        r0 = r61;
        r0.mPartCurrent = r6;
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL";
        r57 = 1;
        r0 = r62;
        r1 = r57;
        r6 = r0.getIntExtra(r6, r1);
        r0 = r61;
        r0.mPartsTotal = r6;
        r6 = r25 & -8;
        if (r6 > 0) goto L_0x01c2;
    L_0x01ae:
        r6 = "application/zip";
        r0 = r38;
        r6 = r6.equals(r0);
        if (r6 != 0) goto L_0x01ea;
    L_0x01b8:
        r6 = "application/octet-stream";
        r0 = r38;
        r6 = r6.equals(r0);
        if (r6 != 0) goto L_0x01ea;
    L_0x01c2:
        r6 = "File type or file mime-type not supported";
        r0 = r61;
        r0.logw(r6);
        r6 = 15;
        r57 = "File type or file mime-type not supported";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);
        r6 = 4105; // 0x1009 float:5.752E-42 double:2.028E-320;
        r0 = r61;
        r0.sendErrorBroadcast(r6);
    L_0x01db:
        return;
    L_0x01dc:
        r25 = 4;
        goto L_0x0066;
    L_0x01e0:
        if (r25 != 0) goto L_0x01e6;
    L_0x01e2:
        r38 = "application/zip";
        goto L_0x0070;
    L_0x01e6:
        r38 = "application/octet-stream";
        goto L_0x0070;
    L_0x01ea:
        r6 = "application/octet-stream";
        r0 = r38;
        r6 = r6.equals(r0);
        if (r6 == 0) goto L_0x021d;
    L_0x01f4:
        r6 = 1;
        r0 = r25;
        if (r0 == r6) goto L_0x021d;
    L_0x01f9:
        r6 = 2;
        r0 = r25;
        if (r0 == r6) goto L_0x021d;
    L_0x01fe:
        r6 = 4;
        r0 = r25;
        if (r0 == r6) goto L_0x021d;
    L_0x0203:
        r6 = "Unable to determine file type";
        r0 = r61;
        r0.logw(r6);
        r6 = 15;
        r57 = "Unable to determine file type";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);
        r6 = 4105; // 0x1009 float:5.752E-42 double:2.028E-320;
        r0 = r61;
        r0.sendErrorBroadcast(r6);
        goto L_0x01db;
    L_0x021d:
        r0 = r16;
        r1 = r61;
        r1.mDeviceAddress = r0;
        r0 = r17;
        r1 = r61;
        r1.mDeviceName = r0;
        r6 = 0;
        r0 = r61;
        r0.mConnectionState = r6;
        r6 = 0;
        r0 = r61;
        r0.mBytesSent = r6;
        r6 = 0;
        r0 = r61;
        r0.mBytesConfirmed = r6;
        r6 = 0;
        r0 = r61;
        r0.mPacketsSentSinceNotification = r6;
        r6 = 0;
        r0 = r61;
        r0.mError = r6;
        r58 = 0;
        r0 = r58;
        r2 = r61;
        r2.mLastProgressTime = r0;
        r6 = 0;
        r0 = r61;
        r0.mAborted = r6;
        r6 = 0;
        r0 = r61;
        r0.mPaused = r6;
        r6 = 0;
        r0 = r61;
        r0.mNotificationsEnabled = r6;
        r6 = 0;
        r0 = r61;
        r0.mResetRequestSent = r6;
        r6 = 0;
        r0 = r61;
        r0.mRequestCompleted = r6;
        r6 = 0;
        r0 = r61;
        r0.mImageSizeSent = r6;
        r6 = 0;
        r0 = r61;
        r0.mRemoteErrorOccured = r6;
        r6 = "settings_packet_receipt_notification_enabled";
        r57 = 1;
        r0 = r44;
        r1 = r57;
        r43 = r0.getBoolean(r6, r1);
        r6 = "settings_number_of_packets";
        r57 = 10;
        r57 = java.lang.String.valueOf(r57);
        r0 = r44;
        r1 = r57;
        r53 = r0.getString(r6, r1);
        r41 = 10;
        r41 = java.lang.Integer.parseInt(r53);	 Catch:{ NumberFormatException -> 0x040b }
        if (r41 < 0) goto L_0x0298;
    L_0x0291:
        r6 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r0 = r41;
        if (r0 <= r6) goto L_0x029a;
    L_0x0298:
        r41 = 10;
    L_0x029a:
        if (r43 != 0) goto L_0x029e;
    L_0x029c:
        r41 = 0;
    L_0x029e:
        r0 = r41;
        r1 = r61;
        r1.mPacketsBeforeNotification = r0;
        r6 = "settings_mbr_size";
        r57 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r57 = java.lang.String.valueOf(r57);
        r0 = r44;
        r1 = r57;
        r53 = r0.getString(r6, r1);
        r36 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r36 = java.lang.Integer.parseInt(r53);	 Catch:{ NumberFormatException -> 0x0410 }
        if (r36 >= 0) goto L_0x02be;
    L_0x02bc:
        r36 = 0;
    L_0x02be:
        r6 = 1;
        r57 = "Starting DFU service";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);
        r33 = 0;
        r31 = 0;
        r6 = 1;
        r57 = "Opening file...";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        if (r26 == 0) goto L_0x0415;
    L_0x02d8:
        r0 = r61;
        r1 = r26;
        r2 = r38;
        r3 = r36;
        r4 = r25;
        r33 = r0.openInputStream(r1, r2, r3, r4);	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
    L_0x02e6:
        if (r30 == 0) goto L_0x0425;
    L_0x02e8:
        r6 = r61.getContentResolver();	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r0 = r30;
        r31 = r6.openInputStream(r0);	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r32 = r31;
    L_0x02f4:
        r0 = r33;
        r1 = r61;
        r1.mInputStream = r0;	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        r28 = r33.available();	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        r0 = r28;
        r1 = r61;
        r1.mImageSizeInBytes = r0;	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        if (r25 != 0) goto L_0x031a;
    L_0x0306:
        r6 = "application/zip";
        r0 = r38;
        r6 = r6.equals(r0);	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        if (r6 == 0) goto L_0x031a;
    L_0x0310:
        r0 = r33;
        r0 = (no.nordicsemi.android.dfu.ZipHexInputStream) r0;	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        r56 = r0;
        r25 = r56.getContentType();	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
    L_0x031a:
        r0 = r25;
        r1 = r61;
        r1.mFileType = r0;	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        r6 = "application/zip";
        r0 = r38;
        r6 = r6.equals(r0);	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        if (r6 == 0) goto L_0x10a3;
    L_0x032a:
        r0 = r33;
        r0 = (no.nordicsemi.android.dfu.ZipHexInputStream) r0;	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        r56 = r0;
        r6 = 4;
        r0 = r25;
        if (r0 != r6) goto L_0x0432;
    L_0x0335:
        r6 = r56.getApplicationInit();	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        if (r6 == 0) goto L_0x10a3;
    L_0x033b:
        r31 = new java.io.ByteArrayInputStream;	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        r6 = r56.getApplicationInit();	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        r0 = r31;
        r0.<init>(r6);	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
    L_0x0346:
        r6 = 5;
        r57 = new java.lang.StringBuilder;	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r57.<init>();	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r58 = "Image file opened (";
        r57 = r57.append(r58);	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r0 = r61;
        r0 = r0.mImageSizeInBytes;	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r58 = r0;
        r57 = r57.append(r58);	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r58 = " bytes in total)";
        r57 = r57.append(r58);	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r57 = r57.toString();	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r6 = 1;
        r57 = "Connecting to DFU target...";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = -1;
        r0 = r61;
        r0.updateProgressNotification(r6);	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r1 = r16;
        r7 = r0.connect(r1);	 Catch:{ all -> 0x04c9 }
        r13 = android.bluetooth.BluetoothGatt.class;
        r6 = "refresh";
        r57 = 0;
        r0 = r57;
        r0 = new java.lang.Class[r0];	 Catch:{ Exception -> 0x04a8 }
        r57 = r0;
        r0 = r57;
        r37 = r13.getMethod(r6, r0);	 Catch:{ Exception -> 0x04a8 }
        r6 = 1;
        r0 = r37;
        r0.setAccessible(r6);	 Catch:{ Exception -> 0x04a8 }
        r6 = 0;
        r0 = r37;
        r0.invoke(r7, r6);	 Catch:{ Exception -> 0x04a8 }
    L_0x03a3:
        r0 = r61;
        r6 = r0.mError;	 Catch:{ all -> 0x04c9 }
        if (r6 <= 0) goto L_0x04da;
    L_0x03a9:
        r0 = r61;
        r6 = r0.mError;	 Catch:{ all -> 0x04c9 }
        r0 = r6 & -16385;
        r21 = r0;
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x04c9 }
        r6.<init>();	 Catch:{ all -> 0x04c9 }
        r57 = "An error occurred while connecting to the device:";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ all -> 0x04c9 }
        r0 = r21;
        r6 = r6.append(r0);	 Catch:{ all -> 0x04c9 }
        r6 = r6.toString();	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r0.loge(r6);	 Catch:{ all -> 0x04c9 }
        r6 = 20;
        r57 = "Connection failed (0x%02X): %s";
        r58 = 2;
        r0 = r58;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x04c9 }
        r58 = r0;
        r59 = 0;
        r60 = java.lang.Integer.valueOf(r21);	 Catch:{ all -> 0x04c9 }
        r58[r59] = r60;	 Catch:{ all -> 0x04c9 }
        r59 = 1;
        r60 = no.nordicsemi.android.error.GattError.parse(r21);	 Catch:{ all -> 0x04c9 }
        r58[r59] = r60;	 Catch:{ all -> 0x04c9 }
        r57 = java.lang.String.format(r57, r58);	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r6 = r0.mError;	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r0.terminateConnection(r7, r6);	 Catch:{ all -> 0x04c9 }
        r6 = 0;
        r0 = r61;
        r0.mInputStream = r6;	 Catch:{ IOException -> 0x1088 }
        if (r33 == 0) goto L_0x0407;
    L_0x0404:
        r33.close();	 Catch:{ IOException -> 0x1088 }
    L_0x0407:
        r33 = 0;
        goto L_0x01db;
    L_0x040b:
        r19 = move-exception;
        r41 = 10;
        goto L_0x029a;
    L_0x0410:
        r19 = move-exception;
        r36 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        goto L_0x02be;
    L_0x0415:
        r0 = r61;
        r1 = r24;
        r2 = r38;
        r3 = r36;
        r4 = r25;
        r33 = r0.openInputStream(r1, r2, r3, r4);	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        goto L_0x02e6;
    L_0x0425:
        if (r29 == 0) goto L_0x10a7;
    L_0x0427:
        r32 = new java.io.FileInputStream;	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        r0 = r32;
        r1 = r29;
        r0.<init>(r1);	 Catch:{ SecurityException -> 0x0445, FileNotFoundException -> 0x0466, IOException -> 0x0487 }
        goto L_0x02f4;
    L_0x0432:
        r6 = r56.getSystemInit();	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        if (r6 == 0) goto L_0x10a3;
    L_0x0438:
        r31 = new java.io.ByteArrayInputStream;	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        r6 = r56.getSystemInit();	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        r0 = r31;
        r0.<init>(r6);	 Catch:{ SecurityException -> 0x109e, FileNotFoundException -> 0x1096, IOException -> 0x108e, all -> 0x106b }
        goto L_0x0346;
    L_0x0445:
        r19 = move-exception;
    L_0x0446:
        r31 = 0;
        r6 = "A security exception occured while opening file";
        r0 = r61;
        r1 = r19;
        r0.loge(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = 4097; // 0x1001 float:5.741E-42 double:2.024E-320;
        r0 = r61;
        r0.updateProgressNotification(r6);	 Catch:{ all -> 0x04c9 }
        r6 = 0;
        r0 = r61;
        r0.mInputStream = r6;	 Catch:{ IOException -> 0x109b }
        if (r33 == 0) goto L_0x0462;
    L_0x045f:
        r33.close();	 Catch:{ IOException -> 0x109b }
    L_0x0462:
        r33 = 0;
        goto L_0x01db;
    L_0x0466:
        r19 = move-exception;
    L_0x0467:
        r31 = 0;
        r6 = "An exception occured while opening file";
        r0 = r61;
        r1 = r19;
        r0.loge(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = 4097; // 0x1001 float:5.741E-42 double:2.024E-320;
        r0 = r61;
        r0.updateProgressNotification(r6);	 Catch:{ all -> 0x04c9 }
        r6 = 0;
        r0 = r61;
        r0.mInputStream = r6;	 Catch:{ IOException -> 0x1093 }
        if (r33 == 0) goto L_0x0483;
    L_0x0480:
        r33.close();	 Catch:{ IOException -> 0x1093 }
    L_0x0483:
        r33 = 0;
        goto L_0x01db;
    L_0x0487:
        r19 = move-exception;
    L_0x0488:
        r31 = 0;
        r6 = "An exception occured while calculating file size";
        r0 = r61;
        r1 = r19;
        r0.loge(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = 4098; // 0x1002 float:5.743E-42 double:2.0247E-320;
        r0 = r61;
        r0.updateProgressNotification(r6);	 Catch:{ all -> 0x04c9 }
        r6 = 0;
        r0 = r61;
        r0.mInputStream = r6;	 Catch:{ IOException -> 0x108b }
        if (r33 == 0) goto L_0x04a4;
    L_0x04a1:
        r33.close();	 Catch:{ IOException -> 0x108b }
    L_0x04a4:
        r33 = 0;
        goto L_0x01db;
    L_0x04a8:
        r19 = move-exception;
        r6 = "chenxin";
        r57 = new java.lang.StringBuilder;	 Catch:{ all -> 0x04c9 }
        r57.<init>();	 Catch:{ all -> 0x04c9 }
        r58 = "Exception:";
        r57 = r57.append(r58);	 Catch:{ all -> 0x04c9 }
        r58 = r19.getStackTrace();	 Catch:{ all -> 0x04c9 }
        r57 = r57.append(r58);	 Catch:{ all -> 0x04c9 }
        r57 = r57.toString();	 Catch:{ all -> 0x04c9 }
        r0 = r57;
        android.util.Log.i(r6, r0);	 Catch:{ all -> 0x04c9 }
        goto L_0x03a3;
    L_0x04c9:
        r6 = move-exception;
    L_0x04ca:
        r57 = 0;
        r0 = r57;
        r1 = r61;
        r1.mInputStream = r0;	 Catch:{ IOException -> 0x1068 }
        if (r33 == 0) goto L_0x04d7;
    L_0x04d4:
        r33.close();	 Catch:{ IOException -> 0x1068 }
    L_0x04d7:
        r33 = 0;
    L_0x04d9:
        throw r6;
    L_0x04da:
        r0 = r61;
        r6 = r0.mAborted;	 Catch:{ all -> 0x04c9 }
        if (r6 == 0) goto L_0x0506;
    L_0x04e0:
        r6 = "Upload aborted";
        r0 = r61;
        r0.logi(r6);	 Catch:{ all -> 0x04c9 }
        r6 = 15;
        r57 = "Upload aborted";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = -7;
        r0 = r61;
        r0.terminateConnection(r7, r6);	 Catch:{ all -> 0x04c9 }
        r6 = 0;
        r0 = r61;
        r0.mInputStream = r6;	 Catch:{ IOException -> 0x1085 }
        if (r33 == 0) goto L_0x0502;
    L_0x04ff:
        r33.close();	 Catch:{ IOException -> 0x1085 }
    L_0x0502:
        r33 = 0;
        goto L_0x01db;
    L_0x0506:
        r6 = DFU_SERVICE_UUID;	 Catch:{ all -> 0x04c9 }
        r18 = r7.getService(r6);	 Catch:{ all -> 0x04c9 }
        r12 = r7.getServices();	 Catch:{ all -> 0x04c9 }
        r12 = (java.util.ArrayList) r12;	 Catch:{ all -> 0x04c9 }
        r6 = r12.iterator();	 Catch:{ all -> 0x04c9 }
    L_0x0516:
        r57 = r6.hasNext();	 Catch:{ all -> 0x04c9 }
        if (r57 == 0) goto L_0x0543;
    L_0x051c:
        r52 = r6.next();	 Catch:{ all -> 0x04c9 }
        r52 = (android.bluetooth.BluetoothGattService) r52;	 Catch:{ all -> 0x04c9 }
        r57 = "chenxin";
        r58 = new java.lang.StringBuilder;	 Catch:{ all -> 0x04c9 }
        r58.<init>();	 Catch:{ all -> 0x04c9 }
        r59 = "service:";
        r58 = r58.append(r59);	 Catch:{ all -> 0x04c9 }
        r59 = r52.getUuid();	 Catch:{ all -> 0x04c9 }
        r59 = r59.toString();	 Catch:{ all -> 0x04c9 }
        r58 = r58.append(r59);	 Catch:{ all -> 0x04c9 }
        r58 = r58.toString();	 Catch:{ all -> 0x04c9 }
        android.util.Log.i(r57, r58);	 Catch:{ all -> 0x04c9 }
        goto L_0x0516;
    L_0x0543:
        if (r18 != 0) goto L_0x056c;
    L_0x0545:
        r6 = "DFU service does not exists on the device";
        r0 = r61;
        r0.loge(r6);	 Catch:{ all -> 0x04c9 }
        r6 = 15;
        r57 = "Connected. DFU Service not found";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = 4102; // 0x1006 float:5.748E-42 double:2.0267E-320;
        r0 = r61;
        r0.terminateConnection(r7, r6);	 Catch:{ all -> 0x04c9 }
        r6 = 0;
        r0 = r61;
        r0.mInputStream = r6;	 Catch:{ IOException -> 0x1082 }
        if (r33 == 0) goto L_0x0568;
    L_0x0565:
        r33.close();	 Catch:{ IOException -> 0x1082 }
    L_0x0568:
        r33 = 0;
        goto L_0x01db;
    L_0x056c:
        r6 = DFU_CONTROL_POINT_UUID;	 Catch:{ all -> 0x04c9 }
        r0 = r18;
        r14 = r0.getCharacteristic(r6);	 Catch:{ all -> 0x04c9 }
        r6 = DFU_PACKET_UUID;	 Catch:{ all -> 0x04c9 }
        r0 = r18;
        r8 = r0.getCharacteristic(r6);	 Catch:{ all -> 0x04c9 }
        if (r14 == 0) goto L_0x0580;
    L_0x057e:
        if (r8 != 0) goto L_0x05a7;
    L_0x0580:
        r6 = "DFU characteristics not found in the DFU service";
        r0 = r61;
        r0.loge(r6);	 Catch:{ all -> 0x04c9 }
        r6 = 15;
        r57 = "Connected. DFU Characteristics not found";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = 4103; // 0x1007 float:5.75E-42 double:2.027E-320;
        r0 = r61;
        r0.terminateConnection(r7, r6);	 Catch:{ all -> 0x04c9 }
        r6 = 0;
        r0 = r61;
        r0.mInputStream = r6;	 Catch:{ IOException -> 0x107f }
        if (r33 == 0) goto L_0x05a3;
    L_0x05a0:
        r33.close();	 Catch:{ IOException -> 0x107f }
    L_0x05a3:
        r33 = 0;
        goto L_0x01db;
    L_0x05a7:
        r6 = DFU_VERSION;	 Catch:{ all -> 0x04c9 }
        r0 = r18;
        r55 = r0.getCharacteristic(r6);	 Catch:{ all -> 0x04c9 }
        r6 = 5;
        r57 = "Connected. Services discovered";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = -2;
        r0 = r61;
        r0.updateProgressNotification(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r54 = 0;
        if (r55 == 0) goto L_0x0627;
    L_0x05c3:
        r0 = r61;
        r1 = r55;
        r54 = r0.readVersion(r7, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r39 = r54 & 15;
        r35 = r54 >> 8;
        r6 = new java.lang.StringBuilder;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6.<init>();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r57 = "Version number read: ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r35;
        r6 = r6.append(r0);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r57 = ".";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r39;
        r6 = r6.append(r0);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = r6.toString();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r0.logi(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r57.<init>();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r58 = "Version number read: ";
        r57 = r57.append(r58);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r57;
        r1 = r35;
        r57 = r0.append(r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r58 = ".";
        r57 = r57.append(r58);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r57;
        r1 = r39;
        r57 = r0.append(r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r57 = r57.toString();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
    L_0x0627:
        r6 = 1;
        r0 = r54;
        if (r0 == r6) goto L_0x063c;
    L_0x062c:
        if (r54 != 0) goto L_0x0709;
    L_0x062e:
        r6 = r7.getServices();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = r6.size();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r57 = 3;
        r0 = r57;
        if (r6 <= r0) goto L_0x0709;
    L_0x063c:
        r6 = "Application with buttonless update found";
        r0 = r61;
        r0.logw(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 15;
        r57 = "Application with buttonless update found";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = r7.getDevice();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = r6.getBondState();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r57 = 12;
        r0 = r57;
        if (r6 != r0) goto L_0x0681;
    L_0x065c:
        r6 = GENERIC_ATTRIBUTE_SERVICE_UUID;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r27 = r7.getService(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        if (r27 == 0) goto L_0x0681;
    L_0x0664:
        r6 = SERVICE_CHANGED_UUID;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r27;
        r47 = r0.getCharacteristic(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        if (r47 == 0) goto L_0x0681;
    L_0x066e:
        r6 = 2;
        r0 = r61;
        r1 = r47;
        r0.enableCCCD(r7, r1, r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 10;
        r57 = "Service Changed indications enabled";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
    L_0x0681:
        r6 = 1;
        r57 = "Jumping to the DFU Bootloader...";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 1;
        r0 = r61;
        r0.enableCCCD(r7, r14, r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 10;
        r57 = "Notifications enabled";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = -3;
        r0 = r61;
        r0.updateProgressNotification(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = OP_CODE_START_DFU;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r57 = 1;
        r58 = 4;
        r6[r57] = r58;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = "Sending Start DFU command (Op Code = 1, Upload Mode = 4)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = OP_CODE_START_DFU;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r57 = 1;
        r0 = r61;
        r1 = r57;
        r0.writeOpCode(r7, r14, r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 10;
        r57 = "Jump to bootloader sent (Op Code = 1, Upload Mode = 4)";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r61.waitUntilDisconnected();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 5;
        r57 = "Disconnected by the remote device";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 0;
        r0 = r61;
        r0.refreshDeviceCache(r7, r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r0.close(r7);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = "Starting service that will connect to the DFU bootloader";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r40 = new android.content.Intent;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r40.<init>();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 24;
        r0 = r40;
        r1 = r62;
        r0.fillIn(r1, r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r1 = r40;
        r0.startService(r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 0;
        r0 = r61;
        r0.mInputStream = r6;	 Catch:{ IOException -> 0x107c }
        if (r33 == 0) goto L_0x0705;
    L_0x0702:
        r33.close();	 Catch:{ IOException -> 0x107c }
    L_0x0705:
        r33 = 0;
        goto L_0x01db;
    L_0x0709:
        r6 = 1;
        r0 = r61;
        r0.enableCCCD(r7, r14, r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 10;
        r57 = "Notifications enabled";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r45 = 0;
        r49 = 0;
        r6 = r25 & 1;
        if (r6 <= 0) goto L_0x08d4;
    L_0x0722:
        r9 = r28;
    L_0x0724:
        r6 = r25 & 2;
        if (r6 <= 0) goto L_0x08d7;
    L_0x0728:
        r10 = r28;
    L_0x072a:
        r6 = r25 & 4;
        if (r6 <= 0) goto L_0x08da;
    L_0x072e:
        r11 = r28;
    L_0x0730:
        r6 = "application/zip";
        r0 = r38;
        r6 = r6.equals(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        if (r6 == 0) goto L_0x074c;
    L_0x073a:
        r0 = r33;
        r0 = (no.nordicsemi.android.dfu.ZipHexInputStream) r0;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r56 = r0;
        r9 = r56.softDeviceImageSize();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r10 = r56.bootloaderImageSize();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r11 = r56.applicationImageSize();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x074c:
        r6 = OP_CODE_START_DFU;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = 1;
        r0 = r25;
        r0 = (byte) r0;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = r0;
        r6[r57] = r58;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6.<init>();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = "Sending Start DFU command (Op Code = 1, Upload Mode = ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r25;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = ")";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = r6.toString();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r61;
        r0.logi(r6);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = OP_CODE_START_DFU;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57.<init>();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = "DFU Start sent (Op Code = 1, Upload Mode = ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r57;
        r1 = r25;
        r57 = r0.append(r1);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = ")";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = r57.toString();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6.<init>();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = "Sending image size array to DFU Packet (";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = r6.append(r9);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = "b, ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = r6.append(r10);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = "b, ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = r6.append(r11);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = "b)";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = r6.toString();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r61;
        r0.logi(r6);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = r61;
        r6.writeImageSize(r7, r8, r9, r10, r11);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57.<init>();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = "Firmware image size sent (";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r57;
        r57 = r0.append(r9);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = "b, ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r57;
        r57 = r0.append(r10);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = "b, ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r57;
        r57 = r0.append(r11);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = "b)";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = r57.toString();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r45 = r61.readNotificationResponse();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = 1;
        r0 = r61;
        r1 = r45;
        r49 = r0.getStatusCode(r1, r6);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57.<init>();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = "Responce received (Op Code = ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = 1;
        r58 = r45[r58];	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = " Status = ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r57;
        r1 = r49;
        r57 = r0.append(r1);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r58 = ")";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = r57.toString();	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r6 = 1;
        r0 = r49;
        if (r0 == r6) goto L_0x0c62;
    L_0x0869:
        r6 = new no.nordicsemi.android.dfu.exception.RemoteDfuException;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        r57 = "Starting DFU failed";
        r0 = r57;
        r1 = r49;
        r6.<init>(r0, r1);	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
        throw r6;	 Catch:{ RemoteDfuException -> 0x0875, UnknownResponseException -> 0x088d }
    L_0x0875:
        r19 = move-exception;
        r6 = r19.getErrorNumber();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = 3;
        r0 = r57;
        if (r6 == r0) goto L_0x08dd;
    L_0x0880:
        throw r19;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
    L_0x0881:
        r20 = move-exception;
        r6 = r20.getErrorNumber();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = 3;
        r0 = r57;
        if (r6 == r0) goto L_0x0ac3;
    L_0x088c:
        throw r20;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x088d:
        r19 = move-exception;
        r21 = 4104; // 0x1008 float:5.751E-42 double:2.0276E-320;
        r6 = r19.getMessage();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r0.loge(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 20;
        r57 = r19.getMessage();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = "Sending Reset command (Op Code = 6)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = OP_CODE_RESET;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 10;
        r57 = "Reset request sent";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 4104; // 0x1008 float:5.751E-42 double:2.0276E-320;
        r0 = r61;
        r0.terminateConnection(r7, r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
    L_0x08c6:
        r6 = 0;
        r0 = r61;
        r0.mInputStream = r6;	 Catch:{ IOException -> 0x1070 }
        if (r33 == 0) goto L_0x08d0;
    L_0x08cd:
        r33.close();	 Catch:{ IOException -> 0x1070 }
    L_0x08d0:
        r33 = 0;
        goto L_0x01db;
    L_0x08d4:
        r9 = 0;
        goto L_0x0724;
    L_0x08d7:
        r10 = 0;
        goto L_0x072a;
    L_0x08da:
        r11 = 0;
        goto L_0x0730;
    L_0x08dd:
        r6 = r25 & 4;
        if (r6 <= 0) goto L_0x0a95;
    L_0x08e1:
        r6 = r25 & 3;
        if (r6 <= 0) goto L_0x0a95;
    L_0x08e5:
        r6 = 0;
        r0 = r61;
        r0.mRemoteErrorOccured = r6;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = "DFU target does not support (SD/BL)+App update";
        r0 = r61;
        r0.logw(r6);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = 15;
        r57 = "DFU target does not support (SD/BL)+App update";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r25 = r25 & -5;
        r0 = r25;
        r1 = r61;
        r1.mFileType = r0;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = OP_CODE_START_DFU;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = 1;
        r0 = r25;
        r0 = (byte) r0;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = r0;
        r6[r57] = r58;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = 2;
        r0 = r61;
        r0.mPartsTotal = r6;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r33;
        r0 = (no.nordicsemi.android.dfu.ZipHexInputStream) r0;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r56 = r0;
        r0 = r56;
        r1 = r25;
        r0.setContentType(r1);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r11 = 0;
        r6 = r33.available();	 Catch:{ IOException -> 0x1079 }
        r0 = r61;
        r0.mImageSizeInBytes = r6;	 Catch:{ IOException -> 0x1079 }
    L_0x092a:
        r6 = 1;
        r57 = "Sending only SD/BL";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6.<init>();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = "Resending Start DFU command (Op Code = 1, Upload Mode = ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r25;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = ")";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = r6.toString();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r61;
        r0.logi(r6);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = OP_CODE_START_DFU;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57.<init>();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = "DFU Start sent (Op Code = 1, Upload Mode = ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r57;
        r1 = r25;
        r57 = r0.append(r1);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = ")";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = r57.toString();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6.<init>();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = "Sending image size array to DFU Packet: [";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = r6.append(r9);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = "b, ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = r6.append(r10);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = "b, ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = r6.append(r11);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = "b]";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = r6.toString();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r61;
        r0.logi(r6);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = r61;
        r6.writeImageSize(r7, r8, r9, r10, r11);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57.<init>();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = "Firmware image size sent [";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r57;
        r57 = r0.append(r9);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = "b, ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r57;
        r57 = r0.append(r10);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = "b, ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r57;
        r57 = r0.append(r11);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = "b]";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = r57.toString();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r45 = r61.readNotificationResponse();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = 1;
        r0 = r61;
        r1 = r45;
        r49 = r0.getStatusCode(r1, r6);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57.<init>();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = "Responce received (Op Code = ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = 1;
        r58 = r45[r58];	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = " Status = ";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r57;
        r1 = r49;
        r57 = r0.append(r1);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r58 = ")";
        r57 = r57.append(r58);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = r57.toString();	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r6 = 1;
        r0 = r49;
        if (r0 == r6) goto L_0x0c62;
    L_0x0a46:
        r6 = new no.nordicsemi.android.dfu.exception.RemoteDfuException;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        r57 = "Starting DFU failed";
        r0 = r57;
        r1 = r49;
        r6.<init>(r0, r1);	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
        throw r6;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
    L_0x0a52:
        r19 = move-exception;
        r6 = "Upload aborted";
        r0 = r61;
        r0.logi(r6);	 Catch:{ all -> 0x04c9 }
        r6 = 15;
        r57 = "Upload aborted";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r6 = r0.mConnectionState;	 Catch:{ all -> 0x04c9 }
        r57 = -3;
        r0 = r57;
        if (r6 != r0) goto L_0x0a8d;
    L_0x0a6f:
        r6 = 0;
        r0 = r61;
        r0.mAborted = r6;	 Catch:{ Exception -> 0x1076 }
        r6 = "Sending Reset command (Op Code = 6)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ Exception -> 0x1076 }
        r6 = OP_CODE_RESET;	 Catch:{ Exception -> 0x1076 }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ Exception -> 0x1076 }
        r6 = 10;
        r57 = "Reset request sent";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ Exception -> 0x1076 }
    L_0x0a8d:
        r6 = -7;
        r0 = r61;
        r0.terminateConnection(r7, r6);	 Catch:{ all -> 0x04c9 }
        goto L_0x08c6;
    L_0x0a95:
        throw r19;	 Catch:{ RemoteDfuException -> 0x0881, UnknownResponseException -> 0x088d }
    L_0x0a96:
        r19 = move-exception;
        r6 = 20;
        r57 = "Device has disconneted";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = r19.getMessage();	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r0.loge(r6);	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r6 = r0.mNotificationsEnabled;	 Catch:{ all -> 0x04c9 }
        if (r6 == 0) goto L_0x0ab5;
    L_0x0ab1:
        r6 = 0;
        r7.setCharacteristicNotification(r14, r6);	 Catch:{ all -> 0x04c9 }
    L_0x0ab5:
        r0 = r61;
        r0.close(r7);	 Catch:{ all -> 0x04c9 }
        r6 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r0 = r61;
        r0.updateProgressNotification(r6);	 Catch:{ all -> 0x04c9 }
        goto L_0x08c6;
    L_0x0ac3:
        r6 = 4;
        r0 = r25;
        if (r0 != r6) goto L_0x0c61;
    L_0x0ac8:
        r6 = 0;
        r0 = r61;
        r0.mRemoteErrorOccured = r6;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "DFU target does not support DFU v.2";
        r0 = r61;
        r0.logw(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 15;
        r57 = "DFU target does not support DFU v.2";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 1;
        r57 = "Switching to DFU v.1";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "Resending Start DFU command (Op Code = 1)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = OP_CODE_START_DFU;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = "DFU Start sent (Op Code = 1)";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Sending application image size to DFU Packet: ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r28;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = " bytes";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = r6.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r6 = r0.mImageSizeInBytes;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.writeImageSize(r7, r8, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = "Firmware image size sent (";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r1 = r28;
        r57 = r0.append(r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = " bytes)";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r45 = r61.readNotificationResponse();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 1;
        r0 = r61;
        r1 = r45;
        r49 = r0.getStatusCode(r1, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = "Responce received (Op Code = ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = 1;
        r58 = r45[r58];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = ", Status = ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r1 = r49;
        r57 = r0.append(r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = ")";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 1;
        r0 = r49;
        if (r0 == r6) goto L_0x0c62;
    L_0x0b9b:
        r6 = new no.nordicsemi.android.dfu.exception.RemoteDfuException;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Starting DFU failed";
        r0 = r57;
        r1 = r49;
        r6.<init>(r0, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        throw r6;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0ba7:
        r19 = move-exception;
        r6 = r19.getErrorNumber();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r6 | 8192;
        r21 = r0;
        r6 = r19.getMessage();	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r0.loge(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 20;
        r57 = "Remote DFU error: %s";
        r58 = 1;
        r0 = r58;
        r0 = new java.lang.Object[r0];	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r58 = r0;
        r59 = 0;
        r60 = no.nordicsemi.android.error.GattError.parse(r21);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r58[r59] = r60;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r57 = java.lang.String.format(r57, r58);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = "Sending Reset command (Op Code = 6)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = OP_CODE_RESET;	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r6 = 10;
        r57 = "Reset request sent";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        r0 = r61;
        r1 = r21;
        r0.terminateConnection(r7, r1);	 Catch:{ UploadAbortedException -> 0x0a52, DeviceDisconnectedException -> 0x0a96, DfuException -> 0x0bfa }
        goto L_0x08c6;
    L_0x0bfa:
        r19 = move-exception;
        r6 = r19.getErrorNumber();	 Catch:{ all -> 0x04c9 }
        r0 = r6 & -16385;
        r21 = r0;
        r6 = 20;
        r57 = "Error (0x%02X): %s";
        r58 = 2;
        r0 = r58;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x04c9 }
        r58 = r0;
        r59 = 0;
        r60 = java.lang.Integer.valueOf(r21);	 Catch:{ all -> 0x04c9 }
        r58[r59] = r60;	 Catch:{ all -> 0x04c9 }
        r59 = 1;
        r60 = no.nordicsemi.android.error.GattError.parse(r21);	 Catch:{ all -> 0x04c9 }
        r58[r59] = r60;	 Catch:{ all -> 0x04c9 }
        r57 = java.lang.String.format(r57, r58);	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ all -> 0x04c9 }
        r6 = r19.getMessage();	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r0.loge(r6);	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r6 = r0.mConnectionState;	 Catch:{ all -> 0x04c9 }
        r57 = -3;
        r0 = r57;
        if (r6 != r0) goto L_0x0c56;
    L_0x0c3d:
        r6 = "Sending Reset command (Op Code = 6)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ Exception -> 0x1073 }
        r6 = OP_CODE_RESET;	 Catch:{ Exception -> 0x1073 }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ Exception -> 0x1073 }
        r6 = 10;
        r57 = "Reset request sent";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ Exception -> 0x1073 }
    L_0x0c56:
        r6 = r19.getErrorNumber();	 Catch:{ all -> 0x04c9 }
        r0 = r61;
        r0.terminateConnection(r7, r6);	 Catch:{ all -> 0x04c9 }
        goto L_0x08c6;
    L_0x0c61:
        throw r20;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0c62:
        if (r31 == 0) goto L_0x0d1d;
    L_0x0c64:
        r6 = 10;
        r57 = "Writing Initialize DFU Parameters...";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "Sending the Initialize DFU Parameters START (Op Code = 2, Value = 0)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = OP_CODE_INIT_DFU_PARAMS_START;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 20;
        r15 = new byte[r6];	 Catch:{ IOException -> 0x0c9c }
        r48 = 0;
    L_0x0c83:
        r6 = 0;
        r0 = r15.length;	 Catch:{ IOException -> 0x0c9c }
        r57 = r0;
        r0 = r31;
        r1 = r57;
        r48 = r0.read(r15, r6, r1);	 Catch:{ IOException -> 0x0c9c }
        r6 = -1;
        r0 = r48;
        if (r0 == r6) goto L_0x0cb2;
    L_0x0c94:
        r0 = r61;
        r1 = r48;
        r0.writeInitPacket(r7, r8, r15, r1);	 Catch:{ IOException -> 0x0c9c }
        goto L_0x0c83;
    L_0x0c9c:
        r19 = move-exception;
        r6 = "Error while reading Init packet file";
        r0 = r61;
        r0.loge(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = new no.nordicsemi.android.dfu.exception.DfuException;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Error while reading Init packet file";
        r58 = 4098; // 0x1002 float:5.743E-42 double:2.0247E-320;
        r0 = r57;
        r1 = r58;
        r6.<init>(r0, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        throw r6;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0cb2:
        r6 = "Sending the Initialize DFU Parameters COMPLETE (Op Code = 2, Value = 1)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = OP_CODE_INIT_DFU_PARAMS_COMPLETE;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = "Initialize DFU Parameters completed";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r45 = r61.readNotificationResponse();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 2;
        r0 = r61;
        r1 = r45;
        r49 = r0.getStatusCode(r1, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = "Responce received (Op Code = ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = 1;
        r58 = r45[r58];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = ", Status = ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r1 = r49;
        r57 = r0.append(r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = ")";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 1;
        r0 = r49;
        if (r0 == r6) goto L_0x0d22;
    L_0x0d11:
        r6 = new no.nordicsemi.android.dfu.exception.RemoteDfuException;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Device returned error after sending init packet";
        r0 = r57;
        r1 = r49;
        r6.<init>(r0, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        throw r6;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0d1d:
        r6 = 1;
        r0 = r61;
        r0.mInitPacketSent = r6;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0d22:
        r0 = r61;
        r0 = r0.mPacketsBeforeNotification;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r42 = r0;
        if (r42 <= 0) goto L_0x0d84;
    L_0x0d2a:
        r6 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Sending the number of packets before notifications (Op Code = 8, Value = ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r42;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = ")";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = r6.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = OP_CODE_PACKET_RECEIPT_NOTIF_REQ;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r1 = r42;
        r0.setNumberOfPackets(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = OP_CODE_PACKET_RECEIPT_NOTIF_REQ;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = "Packet Receipt Notif Req (Op Code = 8) sent (Value = ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r1 = r42;
        r57 = r0.append(r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = ")";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0d84:
        r6 = "Sending Receive Firmware Image request (Op Code = 3)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = OP_CODE_RECEIVE_FIRMWARE_IMAGE;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = "Receive Firmware Image request sent";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r50 = android.os.SystemClock.elapsedRealtime();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r50;
        r2 = r61;
        r2.mStartTime = r0;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r50;
        r2 = r61;
        r2.mLastProgressTime = r0;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r61.updateProgressNotification();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "Starting upload...";
        r0 = r61;
        r0.logi(r6);	 Catch:{ DeviceDisconnectedException -> 0x0e60, UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7, UploadAbortedException -> 0x0a52, DfuException -> 0x0bfa }
        r6 = 10;
        r57 = "Starting upload...";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ DeviceDisconnectedException -> 0x0e60, UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7, UploadAbortedException -> 0x0a52, DfuException -> 0x0bfa }
        r0 = r61;
        r1 = r33;
        r45 = r0.uploadFirmwareImage(r7, r8, r1);	 Catch:{ DeviceDisconnectedException -> 0x0e60, UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7, UploadAbortedException -> 0x0a52, DfuException -> 0x0bfa }
        r22 = android.os.SystemClock.elapsedRealtime();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 3;
        r0 = r61;
        r1 = r45;
        r49 = r0.getStatusCode(r1, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Response received. Op Code: ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = 0;
        r57 = r45[r57];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = " Req Op Code = ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = 1;
        r57 = r45[r57];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = ", Status = ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = 2;
        r57 = r45[r57];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = r6.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = "Responce received (Op Code = ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = 1;
        r58 = r45[r58];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = ", Status = ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r1 = r49;
        r57 = r0.append(r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = ")";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 1;
        r0 = r49;
        if (r0 == r6) goto L_0x0e69;
    L_0x0e54:
        r6 = new no.nordicsemi.android.dfu.exception.RemoteDfuException;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Device returned error after sending file";
        r0 = r57;
        r1 = r49;
        r6.<init>(r0, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        throw r6;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0e60:
        r19 = move-exception;
        r6 = "Disconnected while sending data";
        r0 = r61;
        r0.loge(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        throw r19;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0e69:
        r6 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Transfer of ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0 = r0.mBytesSent;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r0;
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = " bytes has taken ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = r22 - r50;
        r0 = r58;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = " ms";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = r6.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = "Upload completed in ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = r22 - r50;
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = " ms";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "Sending Validate request (Op Code = 4)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = OP_CODE_VALIDATE;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = "Validate request sent";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r45 = r61.readNotificationResponse();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 4;
        r0 = r61;
        r1 = r45;
        r49 = r0.getStatusCode(r1, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Response received. Op Code: ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = 0;
        r57 = r45[r57];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = " Req Op Code = ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = 1;
        r57 = r45[r57];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = ", Status = ";
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = 2;
        r57 = r45[r57];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r6 = r6.append(r0);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = r6.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = new java.lang.StringBuilder;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = "Responce received (Op Code = ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = 1;
        r58 = r45[r58];	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = ", Status = ";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r57;
        r1 = r49;
        r57 = r0.append(r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = ")";
        r57 = r57.append(r58);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r57.toString();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 1;
        r0 = r49;
        if (r0 == r6) goto L_0x0f76;
    L_0x0f6a:
        r6 = new no.nordicsemi.android.dfu.exception.RemoteDfuException;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = "Device returned validation error";
        r0 = r57;
        r1 = r49;
        r6.<init>(r0, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        throw r6;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0f76:
        r6 = -5;
        r0 = r61;
        r0.updateProgressNotification(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 0;
        r7.setCharacteristicNotification(r14, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "Sending Activate and Reset request (Op Code = 5)";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = OP_CODE_ACTIVATE_AND_RESET;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.writeOpCode(r7, r14, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 10;
        r57 = "Activate and Reset request sent";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r61.waitUntilDisconnected();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 5;
        r57 = "Disconnected by the remote device";
        r0 = r61;
        r1 = r57;
        r0.sendLogBroadcast(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 6;
        r0 = r54;
        if (r0 >= r6) goto L_0x0fb1;
    L_0x0fab:
        r6 = 1;
        r0 = r61;
        r0.refreshDeviceCache(r7, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0fb1:
        r0 = r61;
        r0.close(r7);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = r7.getDevice();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = r6.getBondState();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = 12;
        r0 = r57;
        if (r6 != r0) goto L_0x0ff9;
    L_0x0fc4:
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_RESTORE_BOND";
        r57 = 0;
        r0 = r62;
        r1 = r57;
        r46 = r0.getBooleanExtra(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        if (r46 != 0) goto L_0x0fd6;
    L_0x0fd2:
        r6 = r25 & 3;
        if (r6 <= 0) goto L_0x0fea;
    L_0x0fd6:
        r6 = r7.getDevice();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.removeBond(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        monitor-enter(r61);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r58 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
        r0 = r61;
        r1 = r58;
        r0.wait(r1);	 Catch:{ InterruptedException -> 0x1066 }
    L_0x0fe9:
        monitor-exit(r61);	 Catch:{ all -> 0x100f }
    L_0x0fea:
        if (r46 == 0) goto L_0x0ff9;
    L_0x0fec:
        r6 = r25 & 4;
        if (r6 <= 0) goto L_0x0ff9;
    L_0x0ff0:
        r6 = r7.getDevice();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0.createBond(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x0ff9:
        r0 = r61;
        r6 = r0.mPartCurrent;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r0 = r0.mPartsTotal;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r0;
        r0 = r57;
        if (r6 != r0) goto L_0x1012;
    L_0x1007:
        r6 = -6;
        r0 = r61;
        r0.updateProgressNotification(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        goto L_0x08c6;
    L_0x100f:
        r6 = move-exception;
        monitor-exit(r61);	 Catch:{ all -> 0x100f }
        throw r6;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
    L_0x1012:
        r6 = "Starting service that will upload application";
        r0 = r61;
        r0.logi(r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r40 = new android.content.Intent;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r40.<init>();	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = 24;
        r0 = r40;
        r1 = r62;
        r0.fillIn(r1, r6);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_MIME_TYPE";
        r57 = "application/zip";
        r0 = r40;
        r1 = r57;
        r0.putExtra(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE";
        r57 = 4;
        r0 = r40;
        r1 = r57;
        r0.putExtra(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT";
        r0 = r61;
        r0 = r0.mPartCurrent;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r0;
        r57 = r57 + 1;
        r0 = r40;
        r1 = r57;
        r0.putExtra(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r6 = "no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL";
        r0 = r61;
        r0 = r0.mPartsTotal;	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r57 = r0;
        r0 = r40;
        r1 = r57;
        r0.putExtra(r6, r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        r0 = r61;
        r1 = r40;
        r0.startService(r1);	 Catch:{ UnknownResponseException -> 0x088d, RemoteDfuException -> 0x0ba7 }
        goto L_0x08c6;
    L_0x1066:
        r6 = move-exception;
        goto L_0x0fe9;
    L_0x1068:
        r57 = move-exception;
        goto L_0x04d9;
    L_0x106b:
        r6 = move-exception;
        r31 = r32;
        goto L_0x04ca;
    L_0x1070:
        r6 = move-exception;
        goto L_0x01db;
    L_0x1073:
        r6 = move-exception;
        goto L_0x0c56;
    L_0x1076:
        r6 = move-exception;
        goto L_0x0a8d;
    L_0x1079:
        r6 = move-exception;
        goto L_0x092a;
    L_0x107c:
        r6 = move-exception;
        goto L_0x01db;
    L_0x107f:
        r6 = move-exception;
        goto L_0x01db;
    L_0x1082:
        r6 = move-exception;
        goto L_0x01db;
    L_0x1085:
        r6 = move-exception;
        goto L_0x01db;
    L_0x1088:
        r6 = move-exception;
        goto L_0x01db;
    L_0x108b:
        r6 = move-exception;
        goto L_0x01db;
    L_0x108e:
        r19 = move-exception;
        r31 = r32;
        goto L_0x0488;
    L_0x1093:
        r6 = move-exception;
        goto L_0x01db;
    L_0x1096:
        r19 = move-exception;
        r31 = r32;
        goto L_0x0467;
    L_0x109b:
        r6 = move-exception;
        goto L_0x01db;
    L_0x109e:
        r19 = move-exception;
        r31 = r32;
        goto L_0x0446;
    L_0x10a3:
        r31 = r32;
        goto L_0x0346;
    L_0x10a7:
        r32 = r31;
        goto L_0x02f4;
        */
        throw new UnsupportedOperationException("Method not decompiled: no.nordicsemi.android.dfu.DfuBaseService.onHandleIntent(android.content.Intent):void");
    }

    private void setNumberOfPackets(byte[] data, int value) {
        data[1] = (byte) (value & 255);
        data[2] = (byte) ((value >> 8) & 255);
    }

    private InputStream openInputStream(String filePath, String mimeType, int mbrSize, int types) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(filePath);
        if (MIME_TYPE_ZIP.equals(mimeType)) {
            return new ZipHexInputStream(is, mbrSize, types);
        }
        if (filePath.toString().toLowerCase(Locale.US).endsWith("hex")) {
            return new HexInputStream(is, mbrSize);
        }
        return is;
    }

    private InputStream openInputStream(Uri stream, String mimeType, int mbrSize, int types) throws FileNotFoundException, IOException {
        InputStream is = getContentResolver().openInputStream(stream);
        if (MIME_TYPE_ZIP.equals(mimeType)) {
            return new ZipHexInputStream(is, mbrSize, types);
        }
        if (stream.toString().toLowerCase(Locale.US).endsWith("hex")) {
            return new HexInputStream(is, mbrSize);
        }
        return is;
    }

    private BluetoothGatt connect(String address) {
        this.mConnectionState = -1;
        logi("Connecting to the device...");
        BluetoothGatt gatt = this.mBluetoothAdapter.getRemoteDevice(address).connectGatt(this, false, this.mGattCallback);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if (((this.mConnectionState != -1 && this.mConnectionState != -2) || this.mError != 0 || this.mAborted) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        return gatt;
    }

    private void terminateConnection(BluetoothGatt gatt, int error) {
        if (this.mConnectionState != 0) {
            updateProgressNotification(-5);
            disconnect(gatt);
            sendLogBroadcast(5, "Disconnected");
        }
        refreshDeviceCache(gatt, false);
        close(gatt);
        updateProgressNotification(error);
    }

    private void disconnect(BluetoothGatt gatt) {
        if (this.mConnectionState != 0) {
            this.mConnectionState = -4;
            logi("Disconnecting from the device...");
            gatt.disconnect();
            waitUntilDisconnected();
        }
    }

    private void waitUntilDisconnected() {
        try {
            synchronized (this.mLock) {
                while (this.mConnectionState != 0 && this.mError == 0) {
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
    }

    private void close(BluetoothGatt gatt) {
        logi("Cleaning up...");
        sendLogBroadcast(0, "gatt.close()");
        gatt.close();
        this.mConnectionState = -5;
    }

    private void refreshDeviceCache(BluetoothGatt gatt, boolean force) {
        if (force || gatt.getDevice().getBondState() == 10) {
            sendLogBroadcast(0, "gatt.refresh()");
            try {
                Method refresh = gatt.getClass().getMethod("refresh", new Class[0]);
                if (refresh != null) {
                    logi("Refreshing result: " + ((Boolean) refresh.invoke(gatt, new Object[0])).booleanValue());
                }
            } catch (Exception e) {
                loge("An exception occured while refreshing device", e);
                sendLogBroadcast(15, "Refreshing failed");
            }
        }
    }

    private int getStatusCode(byte[] response, int request) throws UnknownResponseException {
        if (response != null && response.length == 3 && response[0] == cv.f3784n && response[1] == request && response[2] >= (byte) 1 && response[2] <= (byte) 6) {
            return response[2];
        }
        throw new UnknownResponseException("Invalid response received", response, request);
    }

    private int readVersion(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        if (this.mConnectionState != -3) {
            throw new DeviceDisconnectedException("Unable to read version number", this.mConnectionState);
        } else if (characteristic == null) {
            return 0;
        } else {
            this.mReceivedData = null;
            this.mError = 0;
            logi("Reading DFU version number...");
            sendLogBroadcast(1, "Reading DFU version number...");
            gatt.readCharacteristic(characteristic);
            try {
                synchronized (this.mLock) {
                    while (true) {
                        if ((this.mRequestCompleted || this.mConnectionState != -3 || this.mError != 0 || this.mAborted) && !this.mPaused) {
                            break;
                        }
                        this.mLock.wait();
                    }
                }
            } catch (InterruptedException e) {
                loge("Sleeping interrupted", e);
            }
            if (this.mAborted) {
                throw new UploadAbortedException();
            } else if (this.mError != 0) {
                throw new DfuException("Unable to read version number", this.mError);
            } else if (this.mConnectionState == -3) {
                return characteristic.getIntValue(18, 0).intValue();
            } else {
                throw new DeviceDisconnectedException("Unable to read version number", this.mConnectionState);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void enableCCCD(android.bluetooth.BluetoothGatt r10, android.bluetooth.BluetoothGattCharacteristic r11, int r12) throws no.nordicsemi.android.dfu.exception.DeviceDisconnectedException, no.nordicsemi.android.dfu.exception.DfuException, no.nordicsemi.android.dfu.exception.UploadAbortedException {
        /*
        r9 = this;
        r8 = 2;
        r7 = 0;
        r6 = -3;
        r5 = 1;
        if (r12 != r5) goto L_0x002d;
    L_0x0006:
        r0 = "notifications";
    L_0x0008:
        r3 = r9.mConnectionState;
        if (r3 == r6) goto L_0x0030;
    L_0x000c:
        r3 = new no.nordicsemi.android.dfu.exception.DeviceDisconnectedException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Unable to set ";
        r4 = r4.append(r5);
        r4 = r4.append(r0);
        r5 = " state";
        r4 = r4.append(r5);
        r4 = r4.toString();
        r5 = r9.mConnectionState;
        r3.<init>(r4, r5);
        throw r3;
    L_0x002d:
        r0 = "indications";
        goto L_0x0008;
    L_0x0030:
        r3 = 0;
        r9.mReceivedData = r3;
        r9.mError = r7;
        if (r12 != r5) goto L_0x003b;
    L_0x0037:
        r3 = r9.mNotificationsEnabled;
        if (r3 != 0) goto L_0x0041;
    L_0x003b:
        if (r12 != r8) goto L_0x0042;
    L_0x003d:
        r3 = r9.mServiceChangedIndicationsEnabled;
        if (r3 == 0) goto L_0x0042;
    L_0x0041:
        return;
    L_0x0042:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Enabling ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r4 = "...";
        r3 = r3.append(r4);
        r3 = r3.toString();
        r9.logi(r3);
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Enabling ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r4 = " for ";
        r3 = r3.append(r4);
        r4 = r11.getUuid();
        r3 = r3.append(r4);
        r3 = r3.toString();
        r9.sendLogBroadcast(r5, r3);
        r10.setCharacteristicNotification(r11, r5);
        r3 = CLIENT_CHARACTERISTIC_CONFIG;
        r1 = r11.getDescriptor(r3);
        if (r12 != r5) goto L_0x00ef;
    L_0x008d:
        r3 = android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
    L_0x008f:
        r1.setValue(r3);
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "gatt.writeDescriptor(";
        r3 = r3.append(r4);
        r4 = r1.getUuid();
        r4 = r3.append(r4);
        if (r12 != r5) goto L_0x00f2;
    L_0x00a7:
        r3 = ", value=0x01-00)";
    L_0x00a9:
        r3 = r4.append(r3);
        r3 = r3.toString();
        r9.sendLogBroadcast(r7, r3);
        r10.writeDescriptor(r1);
        r4 = r9.mLock;	 Catch:{ InterruptedException -> 0x00df }
        monitor-enter(r4);	 Catch:{ InterruptedException -> 0x00df }
    L_0x00ba:
        if (r12 != r5) goto L_0x00c0;
    L_0x00bc:
        r3 = r9.mNotificationsEnabled;	 Catch:{ all -> 0x00dc }
        if (r3 == 0) goto L_0x00c6;
    L_0x00c0:
        if (r12 != r8) goto L_0x00d2;
    L_0x00c2:
        r3 = r9.mServiceChangedIndicationsEnabled;	 Catch:{ all -> 0x00dc }
        if (r3 != 0) goto L_0x00d2;
    L_0x00c6:
        r3 = r9.mConnectionState;	 Catch:{ all -> 0x00dc }
        if (r3 != r6) goto L_0x00d2;
    L_0x00ca:
        r3 = r9.mError;	 Catch:{ all -> 0x00dc }
        if (r3 != 0) goto L_0x00d2;
    L_0x00ce:
        r3 = r9.mAborted;	 Catch:{ all -> 0x00dc }
        if (r3 == 0) goto L_0x00d6;
    L_0x00d2:
        r3 = r9.mPaused;	 Catch:{ all -> 0x00dc }
        if (r3 == 0) goto L_0x00f5;
    L_0x00d6:
        r3 = r9.mLock;	 Catch:{ all -> 0x00dc }
        r3.wait();	 Catch:{ all -> 0x00dc }
        goto L_0x00ba;
    L_0x00dc:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x00dc }
        throw r3;	 Catch:{ InterruptedException -> 0x00df }
    L_0x00df:
        r2 = move-exception;
        r3 = "Sleeping interrupted";
        r9.loge(r3, r2);
    L_0x00e5:
        r3 = r9.mAborted;
        if (r3 == 0) goto L_0x00f7;
    L_0x00e9:
        r3 = new no.nordicsemi.android.dfu.exception.UploadAbortedException;
        r3.<init>();
        throw r3;
    L_0x00ef:
        r3 = android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
        goto L_0x008f;
    L_0x00f2:
        r3 = ", value=0x02-00)";
        goto L_0x00a9;
    L_0x00f5:
        monitor-exit(r4);	 Catch:{ all -> 0x00dc }
        goto L_0x00e5;
    L_0x00f7:
        r3 = r9.mError;
        if (r3 == 0) goto L_0x011c;
    L_0x00fb:
        r3 = new no.nordicsemi.android.dfu.exception.DfuException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Unable to set ";
        r4 = r4.append(r5);
        r4 = r4.append(r0);
        r5 = " state";
        r4 = r4.append(r5);
        r4 = r4.toString();
        r5 = r9.mError;
        r3.<init>(r4, r5);
        throw r3;
    L_0x011c:
        r3 = r9.mConnectionState;
        if (r3 == r6) goto L_0x0041;
    L_0x0120:
        r3 = new no.nordicsemi.android.dfu.exception.DeviceDisconnectedException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Unable to set ";
        r4 = r4.append(r5);
        r4 = r4.append(r0);
        r5 = " state";
        r4 = r4.append(r5);
        r4 = r4.toString();
        r5 = r9.mConnectionState;
        r3.<init>(r4, r5);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: no.nordicsemi.android.dfu.DfuBaseService.enableCCCD(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int):void");
    }

    private void writeOpCode(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] value) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        boolean reset = false;
        if (value[0] == (byte) 6 || value[0] == (byte) 5) {
            reset = true;
        }
        writeOpCode(gatt, characteristic, value, reset);
    }

    private void writeOpCode(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] value, boolean reset) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        this.mReceivedData = null;
        this.mError = 0;
        this.mRequestCompleted = false;
        this.mResetRequestSent = reset;
        characteristic.setValue(value);
        sendLogBroadcast(1, "Writing to characteristic " + characteristic.getUuid());
        sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + SocializeConstants.OP_CLOSE_PAREN);
        gatt.writeCharacteristic(characteristic);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((this.mRequestCompleted || this.mConnectionState != -3 || this.mError != 0 || this.mAborted) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        if (this.mAborted) {
            throw new UploadAbortedException();
        } else if (!this.mResetRequestSent && this.mError != 0) {
            throw new DfuException("Unable to write Op Code " + value[0], this.mError);
        } else if (!this.mResetRequestSent && this.mConnectionState != -3) {
            throw new DeviceDisconnectedException("Unable to write Op Code " + value[0], this.mConnectionState);
        }
    }

    private void writeImageSize(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int imageSize) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        this.mReceivedData = null;
        this.mError = 0;
        this.mImageSizeSent = false;
        characteristic.setWriteType(1);
        characteristic.setValue(new byte[4]);
        characteristic.setValue(imageSize, 20, 0);
        sendLogBroadcast(1, "Writing to characteristic " + characteristic.getUuid());
        sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + SocializeConstants.OP_CLOSE_PAREN);
        gatt.writeCharacteristic(characteristic);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((this.mImageSizeSent || this.mConnectionState != -3 || this.mError != 0 || this.mAborted) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        if (this.mAborted) {
            throw new UploadAbortedException();
        } else if (this.mError != 0) {
            throw new DfuException("Unable to write Image Size", this.mError);
        } else if (this.mConnectionState != -3) {
            throw new DeviceDisconnectedException("Unable to write Image Size", this.mConnectionState);
        }
    }

    private void writeImageSize(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int softDeviceImageSize, int bootloaderImageSize, int appImageSize) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        this.mReceivedData = null;
        this.mError = 0;
        this.mImageSizeSent = false;
        characteristic.setWriteType(1);
        characteristic.setValue(new byte[12]);
        characteristic.setValue(softDeviceImageSize, 20, 0);
        characteristic.setValue(bootloaderImageSize, 20, 4);
        characteristic.setValue(appImageSize, 20, 8);
        sendLogBroadcast(1, "Writing to characteristic " + characteristic.getUuid());
        sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + SocializeConstants.OP_CLOSE_PAREN);
        gatt.writeCharacteristic(characteristic);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((this.mImageSizeSent || this.mConnectionState != -3 || this.mError != 0 || this.mAborted) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        if (this.mAborted) {
            throw new UploadAbortedException();
        } else if (this.mError != 0) {
            throw new DfuException("Unable to write Image Sizes", this.mError);
        } else if (this.mConnectionState != -3) {
            throw new DeviceDisconnectedException("Unable to write Image Sizes", this.mConnectionState);
        }
    }

    private void writeInitPacket(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] buffer, int size) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        byte[] locBuffer = buffer;
        if (buffer.length != size) {
            locBuffer = new byte[size];
            System.arraycopy(buffer, 0, locBuffer, 0, size);
        }
        this.mReceivedData = null;
        this.mError = 0;
        this.mInitPacketSent = false;
        characteristic.setWriteType(1);
        characteristic.setValue(locBuffer);
        logi("Sending init packet (Value = " + parse(locBuffer) + SocializeConstants.OP_CLOSE_PAREN);
        sendLogBroadcast(1, "Writing to characteristic " + characteristic.getUuid());
        sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + SocializeConstants.OP_CLOSE_PAREN);
        gatt.writeCharacteristic(characteristic);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((this.mInitPacketSent || this.mConnectionState != -3 || this.mError != 0 || this.mAborted) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        if (this.mAborted) {
            throw new UploadAbortedException();
        } else if (this.mError != 0) {
            throw new DfuException("Unable to write Init DFU Parameters", this.mError);
        } else if (this.mConnectionState != -3) {
            throw new DeviceDisconnectedException("Unable to write Init DFU Parameters", this.mConnectionState);
        }
    }

    private byte[] uploadFirmwareImage(BluetoothGatt gatt, BluetoothGattCharacteristic packetCharacteristic, InputStream inputStream) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        this.mReceivedData = null;
        this.mError = 0;
        byte[] buffer = this.mBuffer;
        try {
            int size = inputStream.read(buffer);
            sendLogBroadcast(1, "Sending firmware to characteristic " + packetCharacteristic.getUuid() + Util.TEXT_POSTFIX);
            writePacket(gatt, packetCharacteristic, buffer, size);
            try {
                synchronized (this.mLock) {
                    while (true) {
                        if ((this.mReceivedData != null || this.mConnectionState != -3 || this.mError != 0 || this.mAborted) && !this.mPaused) {
                            break;
                        }
                        this.mLock.wait();
                    }
                }
            } catch (InterruptedException e) {
                loge("Sleeping interrupted", e);
            }
            if (this.mAborted) {
                throw new UploadAbortedException();
            } else if (this.mError != 0) {
                throw new DfuException("Uploading Fimrware Image failed", this.mError);
            } else if (this.mConnectionState == -3) {
                return this.mReceivedData;
            } else {
                throw new DeviceDisconnectedException("Uploading Fimrware Image failed: device disconnected", this.mConnectionState);
            }
        } catch (HexFileValidationException e2) {
            throw new DfuException("HEX file not valid", 4099);
        } catch (IOException e3) {
            throw new DfuException("Error while reading file", ERROR_FILE_IO_EXCEPTION);
        }
    }

    private void writePacket(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] buffer, int size) {
        byte[] locBuffer = buffer;
        if (buffer.length != size) {
            locBuffer = new byte[size];
            System.arraycopy(buffer, 0, locBuffer, 0, size);
        }
        characteristic.setValue(locBuffer);
        gatt.writeCharacteristic(characteristic);
    }

    private void waitIfPaused() {
        synchronized (this.mLock) {
            while (this.mPaused) {
                try {
                    this.mLock.wait();
                } catch (InterruptedException e) {
                    loge("Sleeping interrupted", e);
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    public boolean createBond(BluetoothDevice device) {
        boolean result;
        if (device.getBondState() == 12) {
            return true;
        }
        this.mRequestCompleted = false;
        sendLogBroadcast(1, "Starting pairing...");
        if (VERSION.SDK_INT >= 19) {
            sendLogBroadcast(0, "gatt.getDevice().createBond()");
            result = device.createBond();
        } else {
            result = createBondApi18(device);
        }
        try {
            synchronized (this.mLock) {
                while (!this.mRequestCompleted && !this.mAborted) {
                    this.mLock.wait();
                }
            }
            return result;
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
            return result;
        }
    }

    public boolean createBondApi18(BluetoothDevice device) {
        try {
            Method createBond = device.getClass().getMethod("createBond", new Class[0]);
            if (createBond != null) {
                sendLogBroadcast(0, "gatt.getDevice().createBond() (hidden)");
                return ((Boolean) createBond.invoke(device, new Object[0])).booleanValue();
            }
        } catch (Exception e) {
            Log.w(TAG, "An exception occurred while creating bond", e);
        }
        return false;
    }

    public boolean removeBond(BluetoothDevice device) {
        if (device.getBondState() == 10) {
            return true;
        }
        sendLogBroadcast(1, "Removing bond information...");
        boolean result = false;
        try {
            Method removeBond = device.getClass().getMethod("removeBond", new Class[0]);
            if (removeBond != null) {
                this.mRequestCompleted = false;
                sendLogBroadcast(0, "gatt.getDevice().removeBond() (hidden)");
                result = ((Boolean) removeBond.invoke(device, new Object[0])).booleanValue();
                try {
                    synchronized (this.mLock) {
                        while (!this.mRequestCompleted && !this.mAborted) {
                            this.mLock.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    loge("Sleeping interrupted", e);
                }
            }
            return true;
        } catch (Exception e2) {
            Log.w(TAG, "An exception occurred while removing bond information", e2);
            return result;
        }
    }

    private byte[] readNotificationResponse() throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        this.mError = 0;
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((this.mReceivedData != null || this.mConnectionState != -3 || this.mError != 0 || this.mAborted) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        if (this.mAborted) {
            throw new UploadAbortedException();
        } else if (this.mError != 0) {
            throw new DfuException("Unable to write Op Code", this.mError);
        } else if (this.mConnectionState == -3) {
            return this.mReceivedData;
        } else {
            throw new DeviceDisconnectedException("Unable to write Op Code", this.mConnectionState);
        }
    }

    private void updateProgressNotification() {
        int progress = (int) ((100.0f * ((float) this.mBytesSent)) / ((float) this.mImageSizeInBytes));
        if (this.mLastProgress != progress) {
            this.mLastProgress = progress;
            updateProgressNotification(progress);
        }
    }

    public void updateProgressNotification(int progress) {
        Log.i("DfuBaseService", "progress:" + progress);
        String deviceAddress = this.mDeviceAddress;
        String deviceName = this.mDeviceName != null ? this.mDeviceName : getString(C1450R.string.dfu_unknown_name);
        Builder builder = new Builder(this).setSmallIcon(17301640).setOnlyAlertOnce(true);
        builder.setColor(-7829368);
        switch (progress) {
            case -7:
                builder.setOngoing(false).setContentTitle(getString(C1450R.string.dfu_status_aborted)).setSmallIcon(17301641).setContentText(getString(C1450R.string.dfu_status_aborted_msg)).setAutoCancel(true);
                break;
            case -6:
                builder.setOngoing(false).setContentTitle(getString(C1450R.string.dfu_status_completed)).setSmallIcon(17301641).setContentText(getString(C1450R.string.dfu_status_completed_msg)).setAutoCancel(true).setColor(-16730086);
                break;
            case -5:
                builder.setOngoing(true).setContentTitle(getString(C1450R.string.dfu_status_disconnecting)).setContentText(getString(C1450R.string.dfu_status_disconnecting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            case -4:
                builder.setOngoing(true).setContentTitle(getString(C1450R.string.dfu_status_validating)).setContentText(getString(C1450R.string.dfu_status_validating_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            case -3:
                builder.setOngoing(true).setContentTitle(getString(C1450R.string.dfu_status_switching_to_dfu)).setContentText(getString(C1450R.string.dfu_status_switching_to_dfu_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            case -2:
                builder.setOngoing(true).setContentTitle(getString(C1450R.string.dfu_status_starting)).setContentText(getString(C1450R.string.dfu_status_starting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            case -1:
                builder.setOngoing(true).setContentTitle(getString(C1450R.string.dfu_status_connecting)).setContentText(getString(C1450R.string.dfu_status_connecting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            default:
                if (progress < 4096) {
                    builder.setOngoing(true).setContentTitle(this.mPartsTotal == 1 ? getString(C1450R.string.dfu_status_uploading) : getString(C1450R.string.dfu_status_uploading_part, new Object[]{Integer.valueOf(this.mPartCurrent), Integer.valueOf(this.mPartsTotal)})).setContentText((this.mFileType & 4) > 0 ? getString(C1450R.string.dfu_status_uploading_msg, new Object[]{deviceName}) : getString(C1450R.string.dfu_status_uploading_components_msg, new Object[]{deviceName})).setProgress(100, progress, false);
                    break;
                }
                builder.setOngoing(false).setContentTitle(getString(C1450R.string.dfu_status_error)).setSmallIcon(17301641).setContentText(getString(C1450R.string.dfu_status_error_msg)).setAutoCancel(true).setColor(SupportMenu.CATEGORY_MASK);
                break;
        }
        if (progress < 4096) {
            sendProgressBroadcast(progress);
        } else {
            sendErrorBroadcast(progress);
        }
        Intent intent = new Intent(this, getNotificationTarget());
        intent.addFlags(268435456);
        intent.putExtra(EXTRA_DEVICE_ADDRESS, deviceAddress);
        intent.putExtra(EXTRA_DEVICE_NAME, deviceName);
        intent.putExtra(EXTRA_PROGRESS, progress);
        if (this.mLogSession != null) {
            intent.putExtra(EXTRA_LOG_URI, this.mLogSession.getSessionUri());
        }
        builder.setContentIntent(PendingIntent.getActivity(this, 0, intent, 134217728));
        if (!(progress == -7 || progress == -6 || progress >= 4096)) {
            Intent abortIntent = new Intent(BROADCAST_ACTION);
            abortIntent.putExtra(EXTRA_ACTION, 2);
            builder.addAction(C1450R.drawable.ic_action_notify_cancel, getString(C1450R.string.dfu_action_abort), PendingIntent.getBroadcast(this, 1, abortIntent, 134217728));
        }
        ((NotificationManager) getSystemService(MessageObj.CATEGORY_NOTI)).notify(NOTIFICATION_ID, builder.build());
    }

    private void sendProgressBroadcast(int progress) {
        float speed;
        float avgSpeed;
        long now = SystemClock.elapsedRealtime();
        if (now - this.mLastProgressTime != 0) {
            speed = ((float) (this.mBytesSent - this.mLastBytesSent)) / ((float) (now - this.mLastProgressTime));
        } else {
            speed = 0.0f;
        }
        if (now - this.mStartTime != 0) {
            avgSpeed = ((float) this.mBytesSent) / ((float) (now - this.mStartTime));
        } else {
            avgSpeed = 0.0f;
        }
        this.mLastProgressTime = now;
        this.mLastBytesSent = this.mBytesSent;
        Intent broadcast = new Intent(BROADCAST_PROGRESS);
        broadcast.putExtra(EXTRA_DATA, progress);
        broadcast.putExtra(EXTRA_DEVICE_ADDRESS, this.mDeviceAddress);
        broadcast.putExtra(EXTRA_PART_CURRENT, this.mPartCurrent);
        broadcast.putExtra(EXTRA_PARTS_TOTAL, this.mPartsTotal);
        broadcast.putExtra(EXTRA_SPEED_B_PER_MS, speed);
        broadcast.putExtra(EXTRA_AVG_SPEED_B_PER_MS, avgSpeed);
        if (this.mLogSession != null) {
            broadcast.putExtra(EXTRA_LOG_URI, this.mLogSession.getSessionUri());
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
    }

    private void sendErrorBroadcast(int error) {
        Intent broadcast = new Intent(BROADCAST_ERROR);
        broadcast.putExtra(EXTRA_DATA, error & -16385);
        broadcast.putExtra(EXTRA_DEVICE_ADDRESS, this.mDeviceAddress);
        if (this.mLogSession != null) {
            broadcast.putExtra(EXTRA_LOG_URI, this.mLogSession.getSessionUri());
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
    }

    private void sendLogBroadcast(int level, String message) {
        ILogSession session = this.mLogSession;
        String fullMessage = "[DFU] " + message;
        if (session == null) {
            Intent broadcast = new Intent(BROADCAST_LOG);
            broadcast.putExtra(EXTRA_LOG_MESSAGE, fullMessage);
            broadcast.putExtra(EXTRA_LOG_LEVEL, level);
            broadcast.putExtra(EXTRA_DEVICE_ADDRESS, this.mDeviceAddress);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
            return;
        }
        Logger.log(session, level, fullMessage);
    }

    private boolean initialize() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService("bluetooth");
        if (bluetoothManager == null) {
            loge("Unable to initialize BluetoothManager.");
            return false;
        }
        this.mBluetoothAdapter = bluetoothManager.getAdapter();
        if (this.mBluetoothAdapter != null) {
            return true;
        }
        loge("Unable to obtain a BluetoothAdapter.");
        return false;
    }

    private static IntentFilter makeDfuActionIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION);
        return intentFilter;
    }

    private void loge(String message) {
        Log.e(TAG, message);
    }

    private void loge(String message, Throwable e) {
        Log.e(TAG, message, e);
    }

    private void logw(String message) {
        Log.w(TAG, message);
    }

    private void logi(String message) {
        Log.i(TAG, message);
    }

    private void logd(String message) {
        Log.d(TAG, message);
    }

    public String parse(byte[] data) {
        if (data == null) {
            return "";
        }
        int length = data.length;
        if (length == 0) {
            return "";
        }
        char[] out = new char[((length * 3) - 1)];
        for (int j = 0; j < length; j++) {
            int v = data[j] & 255;
            out[j * 3] = HEX_ARRAY[v >>> 4];
            out[(j * 3) + 1] = HEX_ARRAY[v & 15];
            if (j != length - 1) {
                out[(j * 3) + 2] = '-';
            }
        }
        return new String(out);
    }
}
