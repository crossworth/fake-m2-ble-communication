package no.nordicsemi.android.error;

import android.support.v4.media.TransportMediator;
import com.droi.btlib.service.BtManagerService;

public class GattError {
    public static String parse(int error) {
        switch (error) {
            case 1:
                return "GATT INVALID HANDLE";
            case 2:
                return "GATT READ NOT PERMIT";
            case 3:
                return "GATT WRITE NOT PERMIT";
            case 4:
                return "GATT INVALID PDU";
            case 5:
                return "GATT INSUF AUTHENTICATION";
            case 6:
                return "GATT REQ NOT SUPPORTED";
            case 7:
                return "GATT INVALID OFFSET";
            case 8:
                return "GATT INSUF AUTHORIZATION";
            case 9:
                return "GATT PREPARE Q FULL";
            case 10:
                return "GATT NOT FOUND";
            case 11:
                return "GATT NOT LONG";
            case 12:
                return "GATT INSUF KEY SIZE";
            case 13:
                return "GATT INVALID ATTR LEN";
            case 14:
                return "GATT ERR UNLIKELY";
            case 15:
                return "GATT INSUF ENCRYPTION";
            case 16:
                return "GATT UNSUPPORT GRP TYPE";
            case 17:
                return "GATT INSUF RESOURCE";
            case 128:
                return "GATT NO RESOURCES";
            case BtManagerService.CLASSIC_SYNC_SLEEP_MSG /*129*/:
                return "GATT INTERNAL ERROR";
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                return "GATT WRONG STATE";
            case 131:
                return "GATT DB FULL";
            case 132:
                return "GATT BUSY";
            case 133:
                return "GATT ERROR";
            case 134:
                return "GATT CMD STARTED";
            case 135:
                return "GATT ILLEGAL PARAMETER";
            case 136:
                return "GATT PENDING";
            case 137:
                return "GATT AUTH FAIL";
            case 138:
                return "GATT MORE";
            case 139:
                return "GATT INVALID CFG";
            case 140:
                return "GATT SERVICE STARTED";
            case 141:
                return "GATT ENCRYPED NO MITM";
            case 142:
                return "GATT NOT ENCRYPTED";
            case 255:
                return "DFU SERVICE DISCOVERY NOT STARTED";
            case 257:
                return "TOO MANY OPEN CONNECTIONS";
            case 4096:
                return "DFU DEVICE DISCONNECTED";
            case 4097:
                return "DFU FILE NOT FOUND";
            case 4098:
                return "DFU FILE ERROR";
            case 4099:
                return "DFU NOT A VALID HEX FILE";
            case 4100:
                return "DFU IO EXCEPTION";
            case 4101:
                return "DFU ERROR WHILE SERVICE DISCOVERY";
            case 4102:
                return "DFU SERVICE NOT FOUND";
            case 4103:
                return "DFU CHARACTERISTICS NOT FOUND";
            case 4105:
                return "DFU FILE TYPE NOT SUPPORTED";
            default:
                if ((error & 8192) > 0) {
                    switch (error & -8193) {
                        case 2:
                            return "REMOTE DFU INVALID STATE";
                        case 3:
                            return "REMOTE DFU NOT SUPPORTED";
                        case 4:
                            return "REMOTE DFU DATA SIZE EXCEEDS LIMIT";
                        case 5:
                            return "REMOTE DFU INVALID CRC ERROR";
                        case 6:
                            return "REMOTE DFU OPERATION FAILED";
                    }
                }
                return "UNKNOWN (" + error + ")";
        }
    }
}
