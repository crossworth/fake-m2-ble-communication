/**
 * Pedro Henrique - 2018
 * M2 - DroidHealth smartband
 * https://play.google.com/store/apps/details?id=com.zhuoyou.plugin.running
 */
export default class GatInfo {
    static readonly SERVICES_UUID = [ '1817', '180f', '1820'];
    /**
     * NOTE(Pedro): BASE_UUID is common for all BTLE devices.
     * @see http://stackoverflow.com/questions/18699251/finding-out-android-bluetooth-le-gatt-profiles
     */
    static readonly BASE_UUID = '0000%s-0000-1000-8000-00805f9b34fb';
    static readonly ALARM_MEASUREMENT = '6e400002-b5a3-f393-e0a9-e50e24dcca9e';
    static readonly ALARM_SERVICE = '00001820-0000-1000-8000-00805f9b34fb';
    static readonly BATTERY_LEVEL = '00002a19-0000-1000-8000-00805f9b34fb';
    static readonly BATTERY_SERVICE = '0000180f-0000-1000-8000-00805f9b34fb';
    static readonly DEVICEINFO_HARDWARE = '00002a27-0000-1000-8000-00805f9b34fb';
    static readonly DEVICEINFO_MEASUMENT = '00002a26-0000-1000-8000-00805f9b34fb';
    static readonly DEVICEINFO_SERVICE = '0000180a-0000-1000-8000-00805f9b34fb';
    static readonly DEVICE_NAME = '00002a00-0000-1000-8000-00805f9b34fb';
    static readonly DEVICE_NAME_CHAR = '00002a00-0000-1000-8000-00805f9b34fb';
    static readonly DEVICE_NAME_SERVICE = '00001800-0000-1000-8000-00805f9b34fb';
    static readonly FIND_PHONE_MEASUREMENT = '6e400007-b5a3-f393-e0a9-e50e24dcca9e';
    static readonly FIND_PHONE_NOTIFY_ENABLE = '00002902-0000-1000-8000-00805f9b34fb';
    static readonly FIND_PHONE_SERVICE = '00001817-0000-1000-8000-00805f9b34fb';
    static readonly FIRMWARE_READY_MEASUMENT = '00002a5f-0000-1000-8000-00805f9b34fb';
    static readonly FIRMWARE_READY_SERVICE = '00001817-0000-1000-8000-00805f9b34fb';
    static readonly GSENOR_MEASUREMENT = '6e400009-b5a3-f393-e0a9-e50e24dcca9e';
    static readonly GSENSOR_DATA_MEASUREMENT = '00005723-0000-1000-8000-00805f9b34fb';
    static readonly GSENSOR_SERVICE = '00005722-0000-1000-8000-00805f9b34fb';
    static readonly HEART_MEASUREMENT = '6e400009-b5a3-f393-e0a9-e50e24dcca9e';
    static readonly HEART_RATE_MEASUREMENT = '00002a37-0000-1000-8000-00805f9b34fb';
    static readonly HEART_RATE_NOTICEFATION_ENABLE = '00002902-0000-1000-8000-00805f9b34fb';
    static readonly HEART_RATE_SERVICE = '0000180d-0000-1000-8000-00805f9b34fb';
    static readonly OAD_ENABLE_UUID = '00002902-0000-1000-8000-00805f9b34fb';
    static readonly OAD_SERVICE_UUID = 'f000ffc0-0451-4000-b000-000000000000';
    static readonly OTA = '00002a5f-0000-1000-8000-00805f9b34fb';
    static readonly SEDENTARY_MEASUREMENT = '6e400003-b5a3-f393-e0a9-e50e24dcca9e';
    static readonly SEDENTARY_SERVICE = '00001820-0000-1000-8000-00805f9b34fb';
    static readonly SEGMENT_STEPS_MEASUREMENT = '6e400004-b5a3-f393-e0a9-e50e24dcca9e';
    static readonly SLEEP_INFO_CHAR = '6e400006-b5a3-f393-e0a9-e50e24dcca9e';
    static readonly STEPS_NOTICEFATION_ENABLE = '00002902-0000-1000-8000-00805f9b34fb';
    static readonly STEPS_SERVICE = '00001817-0000-1000-8000-00805f9b34fb';
    static readonly TAKE_PICTURE = '6e400008-b5a3-f393-e0a9-e50e24dcca9e';
    static readonly TIME_AND_ALARM_INFO = '00002a60-0000-1000-8000-00805f9b34fb';
    static readonly TIME_SYNC = '00002a61-0000-1000-8000-00805f9b34fb';
    static readonly TOTAL_STEPS_MEASUREMENT = '6e400005-b5a3-f393-e0a9-e50e24dcca9e';
    static readonly VIBRATION_REMIND = '0000ffa8-0000-1000-8000-00805f9b34fb';

    static Normalize(uuid: string) {
        return uuid.split('-').join('');
    }

    static WithoutBase(uuid: string) {
        let finalUuid = GatInfo.Normalize(uuid);

        if (finalUuid.indexOf('00001000800000805f9b34fb') !== -1) {
            finalUuid = finalUuid.replace('00001000800000805f9b34fb', '');
            finalUuid = finalUuid.substring(4);
        }

        return finalUuid;
    }
}