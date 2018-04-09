package no.nordicsemi.android.dfu;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import com.droi.btlib.C0687R;
import com.droi.btlib.connection.MessageObj;
import com.droi.btlib.device.GattInfo;
import com.droi.btlib.service.Util;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;
import no.nordicsemi.android.dfu.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.exception.DfuException;
import no.nordicsemi.android.dfu.exception.HexFileValidationException;
import no.nordicsemi.android.dfu.exception.RemoteDfuException;
import no.nordicsemi.android.dfu.exception.UnknownResponseException;
import no.nordicsemi.android.dfu.exception.UploadAbortedException;
import no.nordicsemi.android.error.GattError;
import no.nordicsemi.android.log.ILogSession;
import no.nordicsemi.android.log.Logger;

public class DfuBaseService extends IntentService {
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
    private final BroadcastReceiver mBondStateBroadcastReceiver = new C19833();
    private byte[] mBuffer = new byte[20];
    private int mBytesConfirmed;
    private int mBytesSent;
    private int mConnectionState;
    private final BroadcastReceiver mConnectionStateBroadcastReceiver = new C19822();
    private String mDeviceAddress;
    private String mDeviceName;
    private final BroadcastReceiver mDfuActionReceiver = new C19811();
    private int mError;
    private int mFileType;
    private final BluetoothGattCallback mGattCallback = new C19844();
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

    class C19811 extends BroadcastReceiver {
        C19811() {
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

    class C19822 extends BroadcastReceiver {
        C19822() {
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

    class C19833 extends BroadcastReceiver {
        C19833() {
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

    class C19844 extends BluetoothGattCallback {
        C19844() {
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
                    DfuBaseService.this.mError = 4101;
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
                            DfuBaseService.this.mError = 4100;
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
                        DfuBaseService.this.mError = 4100;
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

    public DfuBaseService() {
        super(TAG);
    }

    public void onCreate() {
        Log.i(TAG, "onCreate");
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

    protected void onHandleIntent(Intent intent) {
        InputStream initIs;
        BluetoothGattCharacteristic packetCharacteristic;
        byte[] response;
        Throwable e;
        Throwable th;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String deviceAddress = intent.getStringExtra(EXTRA_DEVICE_ADDRESS);
        String deviceName = intent.getStringExtra(EXTRA_DEVICE_NAME);
        String filePath = intent.getStringExtra(EXTRA_FILE_PATH);
        Uri fileUri = (Uri) intent.getParcelableExtra(EXTRA_FILE_URI);
        String initFilePath = intent.getStringExtra(EXTRA_INIT_FILE_PATH);
        Uri initFileUri = (Uri) intent.getParcelableExtra(EXTRA_INIT_FILE_URI);
        Uri logUri = (Uri) intent.getParcelableExtra(EXTRA_LOG_URI);
        int fileType = intent.getIntExtra(EXTRA_FILE_TYPE, 0);
        if (filePath != null && fileType == 0) {
            fileType = filePath.toLowerCase(Locale.US).endsWith("zip") ? 0 : 4;
        }
        String mimeType = intent.getStringExtra(EXTRA_FILE_MIME_TYPE);
        if (mimeType == null) {
            mimeType = fileType == 0 ? MIME_TYPE_ZIP : MIME_TYPE_OCTET_STREAM;
        }
        Log.i("hello", "deviceAddress:" + deviceAddress);
        Log.i("hello", "deviceName:" + deviceName);
        Log.i("hello", "filePath:" + filePath);
        Log.i("hello", "fileUri:" + fileUri);
        Log.i("hello", "initFilePath:" + initFilePath);
        Log.i("hello", "initFileUri:" + initFileUri);
        Log.i("hello", "logUri:" + logUri);
        Log.i("hello", "fileType:" + fileType);
        Log.i("hello", "mimeType:" + mimeType);
        this.mLogSession = Logger.openSession(this, logUri);
        this.mPartCurrent = intent.getIntExtra(EXTRA_PART_CURRENT, 1);
        this.mPartsTotal = intent.getIntExtra(EXTRA_PARTS_TOTAL, 1);
        if ((fileType & -8) > 0 || !(MIME_TYPE_ZIP.equals(mimeType) || MIME_TYPE_OCTET_STREAM.equals(mimeType))) {
            logw("File type or file mime-type not supported");
            sendLogBroadcast(15, "File type or file mime-type not supported");
            sendErrorBroadcast(4105);
        } else if (!MIME_TYPE_OCTET_STREAM.equals(mimeType) || fileType == 1 || fileType == 2 || fileType == 4) {
            int numberOfPackets;
            int mbrSize;
            this.mDeviceAddress = deviceAddress;
            this.mDeviceName = deviceName;
            this.mConnectionState = 0;
            this.mBytesSent = 0;
            this.mBytesConfirmed = 0;
            this.mPacketsSentSinceNotification = 0;
            this.mError = 0;
            this.mLastProgressTime = 0;
            this.mAborted = false;
            this.mPaused = false;
            this.mNotificationsEnabled = false;
            this.mResetRequestSent = false;
            this.mRequestCompleted = false;
            this.mImageSizeSent = false;
            this.mRemoteErrorOccured = false;
            boolean packetReceiptNotificationEnabled = preferences.getBoolean(DfuSettingsConstants.SETTINGS_PACKET_RECEIPT_NOTIFICATION_ENABLED, true);
            try {
                numberOfPackets = Integer.parseInt(preferences.getString(DfuSettingsConstants.SETTINGS_NUMBER_OF_PACKETS, String.valueOf(10)));
                if (numberOfPackets < 0 || numberOfPackets > 65535) {
                    numberOfPackets = 10;
                }
            } catch (NumberFormatException e2) {
                numberOfPackets = 10;
            }
            if (!packetReceiptNotificationEnabled) {
                numberOfPackets = 0;
            }
            this.mPacketsBeforeNotification = numberOfPackets;
            try {
                mbrSize = Integer.parseInt(preferences.getString(DfuSettingsConstants.SETTINGS_MBR_SIZE, String.valueOf(4096)));
                if (mbrSize < 0) {
                    mbrSize = 0;
                }
            } catch (NumberFormatException e3) {
                mbrSize = 4096;
            }
            sendLogBroadcast(1, "Starting DFU service");
            InputStream inputStream = null;
            try {
                InputStream fileInputStream;
                BluetoothGatt gatt;
                int error;
                BluetoothGattService dfuService;
                Iterator it;
                BluetoothGattCharacteristic controlPointCharacteristic;
                sendLogBroadcast(1, "Opening file...");
                if (fileUri != null) {
                    inputStream = openInputStream(fileUri, mimeType, mbrSize, fileType);
                } else {
                    inputStream = openInputStream(filePath, mimeType, mbrSize, fileType);
                }
                if (initFileUri != null) {
                    initIs = getContentResolver().openInputStream(initFileUri);
                } else if (initFilePath != null) {
                    fileInputStream = new FileInputStream(initFilePath);
                } else {
                    initIs = null;
                }
                InputStream initIs2;
                try {
                    this.mInputStream = inputStream;
                    int imageSizeInBytes = inputStream.available();
                    this.mImageSizeInBytes = imageSizeInBytes;
                    if (fileType == 0 && MIME_TYPE_ZIP.equals(mimeType)) {
                        fileType = ((ZipHexInputStream) inputStream).getContentType();
                    }
                    this.mFileType = fileType;
                    if (MIME_TYPE_ZIP.equals(mimeType)) {
                        ZipHexInputStream zhis = (ZipHexInputStream) inputStream;
                        if (fileType == 4) {
                            if (zhis.getApplicationInit() != null) {
                                fileInputStream = new ByteArrayInputStream(zhis.getApplicationInit());
                                sendLogBroadcast(5, "Image file opened (" + this.mImageSizeInBytes + " bytes in total)");
                                sendLogBroadcast(1, "Connecting to DFU target...");
                                updateProgressNotification(-1);
                                gatt = connect(deviceAddress);
                                refreshDeviceCache(gatt, true);
                                if (this.mError <= 0) {
                                    loge("An error occurred while connecting to the device:" + (this.mError & -16385));
                                    sendLogBroadcast(20, String.format("Connection failed (0x%02X): %s", new Object[]{Integer.valueOf(error), GattError.parse(error)}));
                                    terminateConnection(gatt, this.mError);
                                    try {
                                        this.mInputStream = null;
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                    } catch (IOException e4) {
                                        return;
                                    }
                                } else if (this.mAborted) {
                                    logi("Upload aborted");
                                    sendLogBroadcast(15, "Upload aborted");
                                    terminateConnection(gatt, -7);
                                    try {
                                        this.mInputStream = null;
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                    } catch (IOException e5) {
                                        return;
                                    }
                                } else {
                                    dfuService = gatt.getService(DFU_SERVICE_UUID);
                                    Log.i("chenxin", "want service:" + DFU_SERVICE_UUID.toString());
                                    it = ((ArrayList) gatt.getServices()).iterator();
                                    while (it.hasNext()) {
                                        Log.i("chenxin", "service:" + ((BluetoothGattService) it.next()).getUuid().toString());
                                    }
                                    if (dfuService == null) {
                                        loge("DFU service does not exists on the device");
                                        sendLogBroadcast(15, "Connected. DFU Service not found");
                                        terminateConnection(gatt, 4102);
                                        try {
                                            this.mInputStream = null;
                                            if (inputStream != null) {
                                                inputStream.close();
                                            }
                                            return;
                                        } catch (IOException e6) {
                                            return;
                                        }
                                    }
                                    controlPointCharacteristic = dfuService.getCharacteristic(DFU_CONTROL_POINT_UUID);
                                    packetCharacteristic = dfuService.getCharacteristic(DFU_PACKET_UUID);
                                    if (controlPointCharacteristic != null || packetCharacteristic == null) {
                                        loge("DFU characteristics not found in the DFU service");
                                        sendLogBroadcast(15, "Connected. DFU Characteristics not found");
                                        terminateConnection(gatt, 4103);
                                        try {
                                            this.mInputStream = null;
                                            if (inputStream != null) {
                                                inputStream.close();
                                            }
                                        } catch (IOException e7) {
                                            return;
                                        }
                                    }
                                    BluetoothGattCharacteristic versionCharacteristic = dfuService.getCharacteristic(DFU_VERSION);
                                    sendLogBroadcast(5, "Connected. Services discovered");
                                    try {
                                        updateProgressNotification(-2);
                                        int version = 0;
                                        if (versionCharacteristic != null) {
                                            version = readVersion(gatt, versionCharacteristic);
                                            int minor = version & 15;
                                            int major = version >> 8;
                                            logi("Version number read: " + major + "." + minor);
                                            sendLogBroadcast(10, "Version number read: " + major + "." + minor);
                                        }
                                        Intent newIntent;
                                        if (version == 1 || (version == 0 && gatt.getServices().size() > 3)) {
                                            logw("Application with buttonless update found");
                                            sendLogBroadcast(15, "Application with buttonless update found");
                                            if (gatt.getDevice().getBondState() == 12) {
                                                BluetoothGattService genericAttributeService = gatt.getService(GENERIC_ATTRIBUTE_SERVICE_UUID);
                                                if (genericAttributeService != null) {
                                                    BluetoothGattCharacteristic serviceChangedCharacteristic = genericAttributeService.getCharacteristic(SERVICE_CHANGED_UUID);
                                                    if (serviceChangedCharacteristic != null) {
                                                        enableCCCD(gatt, serviceChangedCharacteristic, 2);
                                                        sendLogBroadcast(10, "Service Changed indications enabled");
                                                    }
                                                }
                                            }
                                            sendLogBroadcast(1, "Jumping to the DFU Bootloader...");
                                            enableCCCD(gatt, controlPointCharacteristic, 1);
                                            sendLogBroadcast(10, "Notifications enabled");
                                            updateProgressNotification(-3);
                                            OP_CODE_START_DFU[1] = (byte) 4;
                                            logi("Sending Start DFU command (Op Code = 1, Upload Mode = 4)");
                                            writeOpCode(gatt, controlPointCharacteristic, OP_CODE_START_DFU, true);
                                            sendLogBroadcast(10, "Jump to bootloader sent (Op Code = 1, Upload Mode = 4)");
                                            waitUntilDisconnected();
                                            sendLogBroadcast(5, "Disconnected by the remote device");
                                            refreshDeviceCache(gatt, false);
                                            close(gatt);
                                            logi("Starting service that will connect to the DFU bootloader");
                                            newIntent = new Intent();
                                            newIntent.fillIn(intent, 24);
                                            startService(newIntent);
                                            try {
                                                this.mInputStream = null;
                                                if (inputStream != null) {
                                                    inputStream.close();
                                                }
                                                return;
                                            } catch (IOException e8) {
                                                return;
                                            }
                                        }
                                        int status;
                                        enableCCCD(gatt, controlPointCharacteristic, 1);
                                        sendLogBroadcast(10, "Notifications enabled");
                                        int softDeviceImageSize = (fileType & 1) > 0 ? imageSizeInBytes : 0;
                                        int bootloaderImageSize = (fileType & 2) > 0 ? imageSizeInBytes : 0;
                                        int appImageSize = (fileType & 4) > 0 ? imageSizeInBytes : 0;
                                        try {
                                            if (MIME_TYPE_ZIP.equals(mimeType)) {
                                                zhis = (ZipHexInputStream) inputStream;
                                                softDeviceImageSize = zhis.softDeviceImageSize();
                                                bootloaderImageSize = zhis.bootloaderImageSize();
                                                appImageSize = zhis.applicationImageSize();
                                            }
                                            OP_CODE_START_DFU[1] = (byte) fileType;
                                            logi("Sending Start DFU command (Op Code = 1, Upload Mode = " + fileType + ")");
                                            writeOpCode(gatt, controlPointCharacteristic, OP_CODE_START_DFU);
                                            sendLogBroadcast(10, "DFU Start sent (Op Code = 1, Upload Mode = " + fileType + ")");
                                            logi("Sending image size array to DFU Packet (" + softDeviceImageSize + "b, " + bootloaderImageSize + "b, " + appImageSize + "b)");
                                            writeImageSize(gatt, packetCharacteristic, softDeviceImageSize, bootloaderImageSize, appImageSize);
                                            sendLogBroadcast(10, "Firmware image size sent (" + softDeviceImageSize + "b, " + bootloaderImageSize + "b, " + appImageSize + "b)");
                                            response = readNotificationResponse();
                                            status = getStatusCode(response, 1);
                                            sendLogBroadcast(10, "Responce received (Op Code = " + response[1] + " Status = " + status + ")");
                                            if (status != 1) {
                                                throw new RemoteDfuException("Starting DFU failed", status);
                                            }
                                        } catch (RemoteDfuException e9) {
                                            if (e9.getErrorNumber() != 3) {
                                                throw e9;
                                            } else if ((fileType & 4) <= 0 || (fileType & 3) <= 0) {
                                                throw e9;
                                            } else {
                                                this.mRemoteErrorOccured = false;
                                                logw("DFU target does not support (SD/BL)+App update");
                                                sendLogBroadcast(15, "DFU target does not support (SD/BL)+App update");
                                                fileType &= -5;
                                                this.mFileType = fileType;
                                                OP_CODE_START_DFU[1] = (byte) fileType;
                                                this.mPartsTotal = 2;
                                                ((ZipHexInputStream) inputStream).setContentType(fileType);
                                                try {
                                                    this.mImageSizeInBytes = inputStream.available();
                                                } catch (IOException e10) {
                                                }
                                                sendLogBroadcast(1, "Sending only SD/BL");
                                                logi("Resending Start DFU command (Op Code = 1, Upload Mode = " + fileType + ")");
                                                writeOpCode(gatt, controlPointCharacteristic, OP_CODE_START_DFU);
                                                sendLogBroadcast(10, "DFU Start sent (Op Code = 1, Upload Mode = " + fileType + ")");
                                                logi("Sending image size array to DFU Packet: [" + softDeviceImageSize + "b, " + bootloaderImageSize + "b, " + 0 + "b]");
                                                writeImageSize(gatt, packetCharacteristic, softDeviceImageSize, bootloaderImageSize, 0);
                                                sendLogBroadcast(10, "Firmware image size sent [" + softDeviceImageSize + "b, " + bootloaderImageSize + "b, " + 0 + "b]");
                                                response = readNotificationResponse();
                                                status = getStatusCode(response, 1);
                                                sendLogBroadcast(10, "Responce received (Op Code = " + response[1] + " Status = " + status + ")");
                                                if (status != 1) {
                                                    throw new RemoteDfuException("Starting DFU failed", status);
                                                }
                                            }
                                        } catch (RemoteDfuException e92) {
                                            loge(e92.getMessage());
                                            sendLogBroadcast(20, e92.getMessage());
                                            logi("Sending Reset command (Op Code = 6)");
                                            writeOpCode(gatt, controlPointCharacteristic, OP_CODE_RESET);
                                            sendLogBroadcast(10, "Reset request sent");
                                            terminateConnection(gatt, 4104);
                                        } catch (RemoteDfuException e1) {
                                            if (e1.getErrorNumber() != 3) {
                                                throw e1;
                                            } else if (fileType == 4) {
                                                this.mRemoteErrorOccured = false;
                                                logw("DFU target does not support DFU v.2");
                                                sendLogBroadcast(15, "DFU target does not support DFU v.2");
                                                sendLogBroadcast(1, "Switching to DFU v.1");
                                                logi("Resending Start DFU command (Op Code = 1)");
                                                writeOpCode(gatt, controlPointCharacteristic, OP_CODE_START_DFU);
                                                sendLogBroadcast(10, "DFU Start sent (Op Code = 1)");
                                                logi("Sending application image size to DFU Packet: " + imageSizeInBytes + " bytes");
                                                writeImageSize(gatt, packetCharacteristic, this.mImageSizeInBytes);
                                                sendLogBroadcast(10, "Firmware image size sent (" + imageSizeInBytes + " bytes)");
                                                response = readNotificationResponse();
                                                status = getStatusCode(response, 1);
                                                sendLogBroadcast(10, "Responce received (Op Code = " + response[1] + ", Status = " + status + ")");
                                                if (status != 1) {
                                                    throw new RemoteDfuException("Starting DFU failed", status);
                                                }
                                            } else {
                                                throw e1;
                                            }
                                        }
                                        if (initIs2 != null) {
                                            sendLogBroadcast(10, "Writing Initialize DFU Parameters...");
                                            logi("Sending the Initialize DFU Parameters START (Op Code = 2, Value = 0)");
                                            writeOpCode(gatt, controlPointCharacteristic, OP_CODE_INIT_DFU_PARAMS_START);
                                            byte[] data = new byte[20];
                                            while (true) {
                                                int size = initIs2.read(data, 0, data.length);
                                                if (size == -1) {
                                                    break;
                                                }
                                                writeInitPacket(gatt, packetCharacteristic, data, size);
                                            }
                                            logi("Sending the Initialize DFU Parameters COMPLETE (Op Code = 2, Value = 1)");
                                            writeOpCode(gatt, controlPointCharacteristic, OP_CODE_INIT_DFU_PARAMS_COMPLETE);
                                            sendLogBroadcast(10, "Initialize DFU Parameters completed");
                                            response = readNotificationResponse();
                                            status = getStatusCode(response, 2);
                                            sendLogBroadcast(10, "Responce received (Op Code = " + response[1] + ", Status = " + status + ")");
                                            if (status != 1) {
                                                throw new RemoteDfuException("Device returned error after sending init packet", status);
                                            }
                                        }
                                        this.mInitPacketSent = true;
                                        int numberOfPacketsBeforeNotification = this.mPacketsBeforeNotification;
                                        if (numberOfPacketsBeforeNotification > 0) {
                                            logi("Sending the number of packets before notifications (Op Code = 8, Value = " + numberOfPacketsBeforeNotification + ")");
                                            setNumberOfPackets(OP_CODE_PACKET_RECEIPT_NOTIF_REQ, numberOfPacketsBeforeNotification);
                                            writeOpCode(gatt, controlPointCharacteristic, OP_CODE_PACKET_RECEIPT_NOTIF_REQ);
                                            sendLogBroadcast(10, "Packet Receipt Notif Req (Op Code = 8) sent (Value = " + numberOfPacketsBeforeNotification + ")");
                                        }
                                        logi("Sending Receive Firmware Image request (Op Code = 3)");
                                        writeOpCode(gatt, controlPointCharacteristic, OP_CODE_RECEIVE_FIRMWARE_IMAGE);
                                        sendLogBroadcast(10, "Receive Firmware Image request sent");
                                        long startTime = SystemClock.elapsedRealtime();
                                        this.mStartTime = startTime;
                                        this.mLastProgressTime = startTime;
                                        updateProgressNotification();
                                        logi("Starting upload...");
                                        sendLogBroadcast(10, "Starting upload...");
                                        response = uploadFirmwareImage(gatt, packetCharacteristic, inputStream);
                                        long endTime = SystemClock.elapsedRealtime();
                                        status = getStatusCode(response, 3);
                                        logi("Response received. Op Code: " + response[0] + " Req Op Code = " + response[1] + ", Status = " + response[2]);
                                        sendLogBroadcast(10, "Responce received (Op Code = " + response[1] + ", Status = " + status + ")");
                                        if (status != 1) {
                                            throw new RemoteDfuException("Device returned error after sending file", status);
                                        }
                                        logi("Transfer of " + this.mBytesSent + " bytes has taken " + (endTime - startTime) + " ms");
                                        sendLogBroadcast(10, "Upload completed in " + (endTime - startTime) + " ms");
                                        logi("Sending Validate request (Op Code = 4)");
                                        writeOpCode(gatt, controlPointCharacteristic, OP_CODE_VALIDATE);
                                        sendLogBroadcast(10, "Validate request sent");
                                        response = readNotificationResponse();
                                        status = getStatusCode(response, 4);
                                        logi("Response received. Op Code: " + response[0] + " Req Op Code = " + response[1] + ", Status = " + response[2]);
                                        sendLogBroadcast(10, "Responce received (Op Code = " + response[1] + ", Status = " + status + ")");
                                        if (status != 1) {
                                            throw new RemoteDfuException("Device returned validation error", status);
                                        }
                                        updateProgressNotification(-5);
                                        gatt.setCharacteristicNotification(controlPointCharacteristic, false);
                                        logi("Sending Activate and Reset request (Op Code = 5)");
                                        writeOpCode(gatt, controlPointCharacteristic, OP_CODE_ACTIVATE_AND_RESET);
                                        sendLogBroadcast(10, "Activate and Reset request sent");
                                        waitUntilDisconnected();
                                        sendLogBroadcast(5, "Disconnected by the remote device");
                                        if (version < 6) {
                                            refreshDeviceCache(gatt, true);
                                        }
                                        close(gatt);
                                        if (gatt.getDevice().getBondState() == 12) {
                                            boolean restoreBond = intent.getBooleanExtra(EXTRA_RESTORE_BOND, false);
                                            if (restoreBond || (fileType & 3) > 0) {
                                                removeBond(gatt.getDevice());
                                                synchronized (this) {
                                                    try {
                                                        wait(2000);
                                                    } catch (InterruptedException e11) {
                                                    }
                                                }
                                            }
                                            if (restoreBond && (fileType & 4) > 0) {
                                                createBond(gatt.getDevice());
                                            }
                                        }
                                        if (this.mPartCurrent == this.mPartsTotal) {
                                            updateProgressNotification(-6);
                                        } else {
                                            logi("Starting service that will upload application");
                                            newIntent = new Intent();
                                            newIntent.fillIn(intent, 24);
                                            newIntent.putExtra(EXTRA_FILE_MIME_TYPE, MIME_TYPE_ZIP);
                                            newIntent.putExtra(EXTRA_FILE_TYPE, 4);
                                            newIntent.putExtra(EXTRA_PART_CURRENT, this.mPartCurrent + 1);
                                            newIntent.putExtra(EXTRA_PARTS_TOTAL, this.mPartsTotal);
                                            startService(newIntent);
                                        }
                                        try {
                                            this.mInputStream = null;
                                            if (inputStream != null) {
                                                inputStream.close();
                                            }
                                            return;
                                        } catch (IOException e12) {
                                            return;
                                        }
                                    } catch (UploadAbortedException e13) {
                                        logi("Upload aborted");
                                        sendLogBroadcast(15, "Upload aborted");
                                        if (this.mConnectionState == -3) {
                                            try {
                                                this.mAborted = false;
                                                logi("Sending Reset command (Op Code = 6)");
                                                writeOpCode(gatt, controlPointCharacteristic, OP_CODE_RESET);
                                                sendLogBroadcast(10, "Reset request sent");
                                            } catch (Exception e14) {
                                            }
                                        }
                                        terminateConnection(gatt, -7);
                                    } catch (RemoteDfuException e922) {
                                        sendLogBroadcast(20, "Device has disconneted");
                                        loge(e922.getMessage());
                                        if (this.mNotificationsEnabled) {
                                            gatt.setCharacteristicNotification(controlPointCharacteristic, false);
                                        }
                                        close(gatt);
                                        updateProgressNotification(4096);
                                    } catch (RemoteDfuException e9222) {
                                        error = e9222.getErrorNumber() & -16385;
                                        sendLogBroadcast(20, String.format("Error (0x%02X): %s", new Object[]{Integer.valueOf(error), GattError.parse(error)}));
                                        loge(e9222.getMessage());
                                        if (this.mConnectionState == -3) {
                                            try {
                                                logi("Sending Reset command (Op Code = 6)");
                                                writeOpCode(gatt, controlPointCharacteristic, OP_CODE_RESET);
                                                sendLogBroadcast(10, "Reset request sent");
                                            } catch (Exception e15) {
                                            }
                                        }
                                        terminateConnection(gatt, e9222.getErrorNumber());
                                    }
                                }
                            }
                        } else if (zhis.getSystemInit() != null) {
                            fileInputStream = new ByteArrayInputStream(zhis.getSystemInit());
                            sendLogBroadcast(5, "Image file opened (" + this.mImageSizeInBytes + " bytes in total)");
                            sendLogBroadcast(1, "Connecting to DFU target...");
                            updateProgressNotification(-1);
                            gatt = connect(deviceAddress);
                            refreshDeviceCache(gatt, true);
                            if (this.mError <= 0) {
                                loge("An error occurred while connecting to the device:" + (this.mError & -16385));
                                sendLogBroadcast(20, String.format("Connection failed (0x%02X): %s", new Object[]{Integer.valueOf(error), GattError.parse(error)}));
                                terminateConnection(gatt, this.mError);
                                this.mInputStream = null;
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                            } else if (this.mAborted) {
                                dfuService = gatt.getService(DFU_SERVICE_UUID);
                                Log.i("chenxin", "want service:" + DFU_SERVICE_UUID.toString());
                                it = ((ArrayList) gatt.getServices()).iterator();
                                while (it.hasNext()) {
                                    Log.i("chenxin", "service:" + ((BluetoothGattService) it.next()).getUuid().toString());
                                }
                                if (dfuService == null) {
                                    controlPointCharacteristic = dfuService.getCharacteristic(DFU_CONTROL_POINT_UUID);
                                    packetCharacteristic = dfuService.getCharacteristic(DFU_PACKET_UUID);
                                    if (controlPointCharacteristic != null) {
                                    }
                                    loge("DFU characteristics not found in the DFU service");
                                    sendLogBroadcast(15, "Connected. DFU Characteristics not found");
                                    terminateConnection(gatt, 4103);
                                    this.mInputStream = null;
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                }
                                loge("DFU service does not exists on the device");
                                sendLogBroadcast(15, "Connected. DFU Service not found");
                                terminateConnection(gatt, 4102);
                                this.mInputStream = null;
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                return;
                            } else {
                                logi("Upload aborted");
                                sendLogBroadcast(15, "Upload aborted");
                                terminateConnection(gatt, -7);
                                this.mInputStream = null;
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                            }
                        }
                    }
                    initIs2 = initIs;
                    sendLogBroadcast(5, "Image file opened (" + this.mImageSizeInBytes + " bytes in total)");
                } catch (SecurityException e16) {
                    e = e16;
                    initIs2 = initIs;
                    loge("A security exception occured while opening file", e);
                    updateProgressNotification(4097);
                    try {
                        this.mInputStream = null;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e17) {
                        return;
                    }
                } catch (FileNotFoundException e18) {
                    e = e18;
                    initIs2 = initIs;
                    loge("An exception occured while opening file", e);
                    updateProgressNotification(4097);
                    try {
                        this.mInputStream = null;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e19) {
                        return;
                    }
                } catch (IOException e20) {
                    e = e20;
                    initIs2 = initIs;
                    loge("An exception occured while calculating file size", e);
                    updateProgressNotification(4098);
                    try {
                        this.mInputStream = null;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e21) {
                        return;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    initIs2 = initIs;
                    try {
                        this.mInputStream = null;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e22) {
                    }
                    throw th;
                }
                try {
                    sendLogBroadcast(1, "Connecting to DFU target...");
                    updateProgressNotification(-1);
                    gatt = connect(deviceAddress);
                    refreshDeviceCache(gatt, true);
                    if (this.mError <= 0) {
                        loge("An error occurred while connecting to the device:" + (this.mError & -16385));
                        sendLogBroadcast(20, String.format("Connection failed (0x%02X): %s", new Object[]{Integer.valueOf(error), GattError.parse(error)}));
                        terminateConnection(gatt, this.mError);
                        this.mInputStream = null;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } else if (this.mAborted) {
                        logi("Upload aborted");
                        sendLogBroadcast(15, "Upload aborted");
                        terminateConnection(gatt, -7);
                        this.mInputStream = null;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } else {
                        dfuService = gatt.getService(DFU_SERVICE_UUID);
                        Log.i("chenxin", "want service:" + DFU_SERVICE_UUID.toString());
                        it = ((ArrayList) gatt.getServices()).iterator();
                        while (it.hasNext()) {
                            Log.i("chenxin", "service:" + ((BluetoothGattService) it.next()).getUuid().toString());
                        }
                        if (dfuService == null) {
                            loge("DFU service does not exists on the device");
                            sendLogBroadcast(15, "Connected. DFU Service not found");
                            terminateConnection(gatt, 4102);
                            this.mInputStream = null;
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            return;
                        }
                        controlPointCharacteristic = dfuService.getCharacteristic(DFU_CONTROL_POINT_UUID);
                        packetCharacteristic = dfuService.getCharacteristic(DFU_PACKET_UUID);
                        if (controlPointCharacteristic != null) {
                        }
                        loge("DFU characteristics not found in the DFU service");
                        sendLogBroadcast(15, "Connected. DFU Characteristics not found");
                        terminateConnection(gatt, 4103);
                        this.mInputStream = null;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                } catch (IOException e23) {
                    loge("Disconnected while sending data");
                    throw e23;
                } catch (RemoteDfuException e92222) {
                    loge(e92222.getMessage());
                    sendLogBroadcast(20, e92222.getMessage());
                    logi("Sending Reset command (Op Code = 6)");
                    writeOpCode(gatt, controlPointCharacteristic, OP_CODE_RESET);
                    sendLogBroadcast(10, "Reset request sent");
                    terminateConnection(gatt, 4104);
                } catch (RemoteDfuException e922222) {
                    error = e922222.getErrorNumber() | 8192;
                    loge(e922222.getMessage());
                    sendLogBroadcast(20, String.format("Remote DFU error: %s", new Object[]{GattError.parse(error)}));
                    logi("Sending Reset command (Op Code = 6)");
                    writeOpCode(gatt, controlPointCharacteristic, OP_CODE_RESET);
                    sendLogBroadcast(10, "Reset request sent");
                    terminateConnection(gatt, error);
                } catch (UploadAbortedException e132) {
                    logi("Upload aborted");
                    sendLogBroadcast(15, "Upload aborted");
                    if (this.mConnectionState == -3) {
                        try {
                            this.mAborted = false;
                            logi("Sending Reset command (Op Code = 6)");
                            writeOpCode(gatt, controlPointCharacteristic, OP_CODE_RESET);
                            sendLogBroadcast(10, "Reset request sent");
                        } catch (Exception e142) {
                        }
                    }
                    terminateConnection(gatt, -7);
                } catch (RemoteDfuException e9222222) {
                    error = e9222222.getErrorNumber() & -16385;
                    sendLogBroadcast(20, String.format("Error (0x%02X): %s", new Object[]{Integer.valueOf(error), GattError.parse(error)}));
                    loge(e9222222.getMessage());
                    if (this.mConnectionState == -3) {
                        try {
                            logi("Sending Reset command (Op Code = 6)");
                            writeOpCode(gatt, controlPointCharacteristic, OP_CODE_RESET);
                            sendLogBroadcast(10, "Reset request sent");
                        } catch (Exception e152) {
                        }
                    }
                    terminateConnection(gatt, e9222222.getErrorNumber());
                } catch (IOException e24) {
                    loge("Error while reading Init packet file");
                    throw new DfuException("Error while reading Init packet file", 4098);
                } catch (Throwable th3) {
                    th = th3;
                    this.mInputStream = null;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    throw th;
                }
            } catch (SecurityException e25) {
                e = e25;
                loge("A security exception occured while opening file", e);
                updateProgressNotification(4097);
                this.mInputStream = null;
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (FileNotFoundException e26) {
                e = e26;
                loge("An exception occured while opening file", e);
                updateProgressNotification(4097);
                this.mInputStream = null;
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e27) {
                e = e27;
                loge("An exception occured while calculating file size", e);
                updateProgressNotification(4098);
                this.mInputStream = null;
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } else {
            logw("Unable to determine file type");
            sendLogBroadcast(15, "Unable to determine file type");
            sendErrorBroadcast(4105);
        }
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
        if (response != null && response.length == 3 && response[0] == (byte) 16 && response[1] == request && response[2] >= (byte) 1 && response[2] <= (byte) 6) {
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
        sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + ")");
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
        sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + ")");
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
        sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + ")");
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
        sendLogBroadcast(1, "Writing to characteristic " + characteristic.getUuid());
        sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + ")");
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
            throw new DfuException("Error while reading file", 4100);
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
        if (device.getBondState() == 12) {
            return true;
        }
        boolean result;
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
        String deviceName = this.mDeviceName != null ? this.mDeviceName : getString(C0687R.string.dfu_unknown_name);
        Builder builder = new Builder(this).setSmallIcon(17301640).setOnlyAlertOnce(true);
        builder.setColor(-7829368);
        switch (progress) {
            case -7:
                builder.setOngoing(false).setContentTitle(getString(C0687R.string.dfu_status_aborted)).setSmallIcon(17301641).setContentText(getString(C0687R.string.dfu_status_aborted_msg)).setAutoCancel(true);
                break;
            case -6:
                builder.setOngoing(false).setContentTitle(getString(C0687R.string.dfu_status_completed)).setSmallIcon(17301641).setContentText(getString(C0687R.string.dfu_status_completed_msg)).setAutoCancel(true).setColor(-16730086);
                break;
            case -5:
                builder.setOngoing(true).setContentTitle(getString(C0687R.string.dfu_status_disconnecting)).setContentText(getString(C0687R.string.dfu_status_disconnecting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            case -4:
                builder.setOngoing(true).setContentTitle(getString(C0687R.string.dfu_status_validating)).setContentText(getString(C0687R.string.dfu_status_validating_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            case -3:
                builder.setOngoing(true).setContentTitle(getString(C0687R.string.dfu_status_switching_to_dfu)).setContentText(getString(C0687R.string.dfu_status_switching_to_dfu_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            case -2:
                builder.setOngoing(true).setContentTitle(getString(C0687R.string.dfu_status_starting)).setContentText(getString(C0687R.string.dfu_status_starting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            case -1:
                builder.setOngoing(true).setContentTitle(getString(C0687R.string.dfu_status_connecting)).setContentText(getString(C0687R.string.dfu_status_connecting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                break;
            default:
                if (progress < 4096) {
                    builder.setOngoing(true).setContentTitle(this.mPartsTotal == 1 ? getString(C0687R.string.dfu_status_uploading) : getString(C0687R.string.dfu_status_uploading_part, new Object[]{Integer.valueOf(this.mPartCurrent), Integer.valueOf(this.mPartsTotal)})).setContentText((this.mFileType & 4) > 0 ? getString(C0687R.string.dfu_status_uploading_msg, new Object[]{deviceName}) : getString(C0687R.string.dfu_status_uploading_components_msg, new Object[]{deviceName})).setProgress(100, progress, false);
                    break;
                }
                builder.setOngoing(false).setContentTitle(getString(C0687R.string.dfu_status_error)).setSmallIcon(17301641).setContentText(getString(C0687R.string.dfu_status_error_msg)).setAutoCancel(true).setColor(SupportMenu.CATEGORY_MASK);
                break;
        }
        if (progress < 4096) {
            sendProgressBroadcast(progress);
        } else {
            sendErrorBroadcast(progress);
        }
        if (!(progress == -7 || progress == -6 || progress >= 4096)) {
            Intent abortIntent = new Intent(BROADCAST_ACTION);
            abortIntent.putExtra(EXTRA_ACTION, 2);
            builder.addAction(C0687R.drawable.ic_action_notify_cancel, getString(C0687R.string.dfu_action_abort), PendingIntent.getBroadcast(this, 1, abortIntent, 134217728));
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
        Log.i("chenxin", "sendProgressBroadcast:" + progress);
        getApplication().sendBroadcast(broadcast);
    }

    private void sendErrorBroadcast(int error) {
        Intent broadcast = new Intent(BROADCAST_ERROR);
        broadcast.putExtra(EXTRA_DATA, error & -16385);
        broadcast.putExtra(EXTRA_DEVICE_ADDRESS, this.mDeviceAddress);
        if (this.mLogSession != null) {
            broadcast.putExtra(EXTRA_LOG_URI, this.mLogSession.getSessionUri());
        }
        getApplication().sendBroadcast(broadcast);
    }

    private void sendLogBroadcast(int level, String message) {
        ILogSession session = this.mLogSession;
        String fullMessage = "[DFU] " + message;
        if (session == null) {
            Intent broadcast = new Intent(BROADCAST_LOG);
            broadcast.putExtra(EXTRA_LOG_MESSAGE, fullMessage);
            broadcast.putExtra(EXTRA_LOG_LEVEL, level);
            broadcast.putExtra(EXTRA_DEVICE_ADDRESS, this.mDeviceAddress);
            getApplication().sendBroadcast(broadcast);
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
