import { Characteristic, Descriptor, Peripheral, Service } from 'noble';
import GatInfo from './gat-info';

export default class SmartBand {
    protected _debug: boolean = false;
    protected peripheral: Peripheral;
    protected services: Array<Service>;

    constructor(peripheral: Peripheral) {
        this.services = [];
        this.peripheral = peripheral;

        peripheral.once('servicesDiscover', (services) => {
            for (let service of services) {
                console.log('discovered service', service.uuid);
            }
        });
    }

    protected log(...args) {
        if (this._debug) {
            console.log(args);
        }
    }

    set debug(debug: boolean) {
        this._debug = debug;
    }

    get debug() {
        return this._debug;
    }

    getPeripheralInfo() {
        return this.peripheral;
    }

    connect() {
        return new Promise((resolve, reject) => {
            this.peripheral.connect((error: any) => {
                if (error) {
                    this.log(`Error connecting ${this.peripheral.uuid}`);
                    this.log(error);
                    reject();
                    return;
                }

                this.log(`Connected to ${this.peripheral.uuid}`);
                this.discoveryServices().then(() => {
                    resolve();
                });
            });
        });
    }

    protected discoveryServices() {
        return new Promise((resolve, reject) => {
            this.peripheral.discoverServices([], async (error: any, services: Array<Service>) => {
                if (error) {
                    this.log(`Error discovering services from ${this.peripheral.uuid}`);
                    this.log(error);
                    reject();
                    return;
                }

                this.services = services;
                this.log(`${this.services.length} services discovered`);



                for (let service of this.services) {
                    // console.log(`Service ${service.uuid}`);
                    await this.discoverCharacteristics(service);

                    switch (GatInfo.Normalize(service.uuid)) {
                        case GatInfo.Normalize(GatInfo.ALARM_SERVICE):
                        case GatInfo.WithoutBase(GatInfo.ALARM_SERVICE):
                            this.ALARM_SERVICE = service;
                            this.SEDENTARY_SERVICE = service;
                            this.log('Found ALARM_SERVICE, SEDENTARY_SERVICE');
                            break;
                        case GatInfo.Normalize(GatInfo.BATTERY_SERVICE):
                        case GatInfo.WithoutBase(GatInfo.BATTERY_SERVICE):
                            this.BATTERY_SERVICE = service;
                            this.log('Found BATTERY_SERVICE');
                            break;
                        case GatInfo.Normalize(GatInfo.DEVICEINFO_SERVICE):
                        case GatInfo.WithoutBase(GatInfo.DEVICEINFO_SERVICE):
                            this.DEVICEINFO_SERVICE = service;
                            this.log('Found DEVICEINFO_SERVICE');
                            break;
                        case GatInfo.Normalize(GatInfo.DEVICE_NAME_SERVICE):
                        case GatInfo.WithoutBase(GatInfo.DEVICE_NAME_SERVICE):
                            this.DEVICE_NAME_SERVICE = service;
                            this.log('Found DEVICE_NAME_SERVICE');
                            break;
                        case GatInfo.Normalize(GatInfo.FIND_PHONE_SERVICE):
                        case GatInfo.WithoutBase(GatInfo.FIND_PHONE_SERVICE):
                            this.STEPS_SERVICE = service;
                            this.FIND_PHONE_SERVICE = service;
                            this.FIRMWARE_READY_SERVICE = service;
                            this.log('Found STEPS_SERVICE, FIND_PHONE_SERVICE, FIRMWARE_READY_SERVICE');
                            break;
                        case GatInfo.Normalize(GatInfo.GSENSOR_SERVICE):
                        case GatInfo.WithoutBase(GatInfo.GSENSOR_SERVICE):
                            this.GSENSOR_SERVICE = service;
                            this.log('Found GSENSOR_SERVICE');
                            break;
                        case GatInfo.Normalize(GatInfo.HEART_RATE_SERVICE):
                        case GatInfo.WithoutBase(GatInfo.HEART_RATE_SERVICE):
                            this.HEART_RATE_SERVICE = service;
                            this.log('Found HEART_RATE_SERVICE');
                            break;
                        case GatInfo.Normalize(GatInfo.OAD_SERVICE_UUID):
                        case GatInfo.WithoutBase(GatInfo.OAD_SERVICE_UUID):
                            this.OAD_SERVICE_UUID = service;
                            this.log('Found OAD_SERVICE_UUID');
                            break;
                        default:
                            this.log(`Service ${service.uuid} not mapped`);
                            break;
                    }
                }
                resolve();
            });
        });
    }

    protected discoverCharacteristics(service: Service) {
        return new Promise((resolve, reject) => {
            service.discoverCharacteristics([], async (error: any, characteristics: Array<Characteristic>) => {
                if (error) {
                    this.log(`Error discovering characteristics from service ${service.uuid}`);
                    this.log(error);
                    reject();
                    return;
                }
                this.log(`Discovered characteristics for service ${service.uuid}`);

                for (let characteristic of characteristics) {
                    // console.log(`Characteristic ${characteristic.uuid}`);
                    await this.discoverDescriptors(characteristic);

                    switch (GatInfo.Normalize(characteristic.uuid)) {
                        case GatInfo.Normalize(GatInfo.OTA):
                        case GatInfo.WithoutBase(GatInfo.OTA):
                            this.OTA = characteristic;
                            this.FIRMWARE_READY_MEASUMENT = characteristic;
                            this.log('Found OTA, FIRMWARE_READY_MEASUMENT');
                            break;
                        case GatInfo.Normalize(GatInfo.ALARM_MEASUREMENT):
                        case GatInfo.WithoutBase(GatInfo.ALARM_MEASUREMENT):
                            this.ALARM_MEASUREMENT = characteristic;
                            this.log('Found ALARM_MEASUREMENT');
                        break;
                        case GatInfo.Normalize(GatInfo.BATTERY_LEVEL):
                        case GatInfo.WithoutBase(GatInfo.BATTERY_LEVEL):
                            this.BATTERY_LEVEL = characteristic;
                            this.log('Found BATTERY_LEVEL');
                        break;
                        case GatInfo.Normalize(GatInfo.DEVICEINFO_HARDWARE):
                        case GatInfo.WithoutBase(GatInfo.DEVICEINFO_HARDWARE):
                            this.DEVICEINFO_HARDWARE = characteristic;
                            this.log('Found DEVICEINFO_HARDWARE');
                        break;
                        case GatInfo.Normalize(GatInfo.DEVICEINFO_MEASUMENT):
                        case GatInfo.WithoutBase(GatInfo.DEVICEINFO_MEASUMENT):
                            this.DEVICEINFO_MEASUMENT = characteristic;
                            this.log('Found DEVICEINFO_MEASUMENT');
                        break;
                        case GatInfo.Normalize(GatInfo.DEVICE_NAME):
                        case GatInfo.WithoutBase(GatInfo.DEVICE_NAME):
                            this.DEVICE_NAME = characteristic;
                            this.DEVICE_NAME_CHAR = characteristic;
                            this.OAD_ENABLE_UUID = characteristic;
                            this.STEPS_NOTICEFATION_ENABLE = characteristic;
                            this.log('Found DEVICE_NAME, DEVICE_NAME_CHAR, OAD_ENABLE_UUID, STEPS_NOTICEFATION_ENABLE');
                        break;
                        case GatInfo.Normalize(GatInfo.FIND_PHONE_MEASUREMENT):
                        case GatInfo.WithoutBase(GatInfo.FIND_PHONE_MEASUREMENT):
                            this.FIND_PHONE_MEASUREMENT = characteristic;
                            this.log('Found FIND_PHONE_MEASUREMENT');
                        break;
                        case GatInfo.Normalize(GatInfo.FIND_PHONE_NOTIFY_ENABLE):
                        case GatInfo.WithoutBase(GatInfo.FIND_PHONE_NOTIFY_ENABLE):
                            this.FIND_PHONE_NOTIFY_ENABLE = characteristic;
                            this.HEART_RATE_NOTICEFATION_ENABLE = characteristic;
                            this.log('Found FIND_PHONE_NOTIFY_ENABLE, HEART_RATE_NOTICEFATION_ENABLE');
                        break;
                        case GatInfo.Normalize(GatInfo.GSENSOR_DATA_MEASUREMENT):
                        case GatInfo.WithoutBase(GatInfo.GSENSOR_DATA_MEASUREMENT):
                            this.GSENSOR_DATA_MEASUREMENT = characteristic;
                            this.log('Found GSENSOR_DATA_MEASUREMENT');
                        break;
                        case GatInfo.Normalize(GatInfo.HEART_MEASUREMENT):
                        case GatInfo.WithoutBase(GatInfo.HEART_MEASUREMENT):
                            this.HEART_MEASUREMENT = characteristic;
                            this.log('Found HEART_MEASUREMENT');
                        break;
                        case GatInfo.Normalize(GatInfo.HEART_RATE_MEASUREMENT):
                        case GatInfo.WithoutBase(GatInfo.HEART_RATE_MEASUREMENT):
                            this.HEART_RATE_MEASUREMENT = characteristic;
                            this.GSENOR_MEASUREMENT = characteristic;
                            this.log('Found HEART_RATE_MEASUREMENT, GSENOR_MEASUREMENT');
                        break;
                        case GatInfo.Normalize(GatInfo.SEDENTARY_MEASUREMENT):
                        case GatInfo.WithoutBase(GatInfo.SEDENTARY_MEASUREMENT):
                            this.SEDENTARY_MEASUREMENT = characteristic;
                            this.log('Found SEDENTARY_MEASUREMENT');
                        break;
                        case GatInfo.Normalize(GatInfo.SEGMENT_STEPS_MEASUREMENT):
                        case GatInfo.WithoutBase(GatInfo.SEGMENT_STEPS_MEASUREMENT):
                            this.SEGMENT_STEPS_MEASUREMENT = characteristic;
                            this.log('Found SEGMENT_STEPS_MEASUREMENT');
                        break;
                        case GatInfo.Normalize(GatInfo.SLEEP_INFO_CHAR):
                        case GatInfo.WithoutBase(GatInfo.SLEEP_INFO_CHAR):
                            this.SLEEP_INFO_CHAR = characteristic;
                            this.log('Found SLEEP_INFO_CHAR');
                        break;
                        case GatInfo.Normalize(GatInfo.TAKE_PICTURE):
                        case GatInfo.WithoutBase(GatInfo.TAKE_PICTURE):
                            this.TAKE_PICTURE = characteristic;
                            this.log('Found TAKE_PICTURE');
                        break;
                        case GatInfo.Normalize(GatInfo.TIME_AND_ALARM_INFO):
                        case GatInfo.WithoutBase(GatInfo.TIME_AND_ALARM_INFO):
                            this.TIME_AND_ALARM_INFO = characteristic;
                            this.log('Found TIME_AND_ALARM_INFO');
                        break;
                        case GatInfo.Normalize(GatInfo.TIME_SYNC):
                        case GatInfo.WithoutBase(GatInfo.TIME_SYNC):
                            this.TIME_SYNC = characteristic;
                            this.log('Found TIME_SYNC');
                        break;
                        case GatInfo.Normalize(GatInfo.TOTAL_STEPS_MEASUREMENT):
                        case GatInfo.WithoutBase(GatInfo.TOTAL_STEPS_MEASUREMENT):
                            this.TOTAL_STEPS_MEASUREMENT = characteristic;
                            this.log('Found TOTAL_STEPS_MEASUREMENT');
                        break;
                        case GatInfo.Normalize(GatInfo.VIBRATION_REMIND):
                        case GatInfo.WithoutBase(GatInfo.VIBRATION_REMIND):
                            this.VIBRATION_REMIND = characteristic;
                            this.log('Found VIBRATION_REMIND');
                        break;
                        default:
                            this.log(`Characteristic ${characteristic.uuid} not mapped from service ${service.uuid}`);
                            break;
                    }
                }

                resolve();
            });
        });
    }

    protected discoverDescriptors(characteristic: Characteristic) {
        return new Promise((resolve, reject) => {
            characteristic.discoverDescriptors((error: any, descriptors: Array<Descriptor>) => {
                if (error) {
                    this.log(`Error discovering descriptors from characteristic ${characteristic.uuid}`);
                    this.log(error);
                    reject();
                    return;
                }
                this.log(`Discovered descriptors for characteristic ${characteristic.uuid}`);
                resolve();
            });
        });
    }

    getServices() {
        return this.services;
    }

    startHeartMeasurement() {
        this.sendCommandNumber(49);
    }

    /**
     * New SMS
     * or maybe set device not vibrate?
     */
    notifyNewSMS() {
        this.sendCommandNumber(2);
    }

    /**
     * Maybe work, maybe not
     */
    notifyReadSMS() {
        this.sendCommandNumber(8);
    }

    /**
     * Don't work on M2
     */
    notifyNewCall() {
        this.sendCommandNumber(5);
    }

    notifyNewCall2() {
        this.sendCommandNumber(40);
    }

    notifyNewCallWithString(string: string = '') {
        let cmd = new Buffer.allocUnsafe(1);
        cmd.writeUInt8(7, 0);

        let ending = new Buffer.allocUnsafe(1);
        ending.writeUInt8(-1, 0);

        // maybe use ascii?
        let stringArray = Buffer.from(string, 'utf8');

        const data = Buffer.concat([cmd, stringArray, ending], cmd.length + stringArray.length + ending.length);
        this.sendCommand(data);
    }

    notifyCallEnd() {
        this.sendCommandNumber(17);
    }

    notifyMissCall() {
        this.sendCommandNumber(5);
    }

    notifyDoneFindPhone() {
        this.sendCommandNumber(19);
    }

    notifyStartFindDevice() {
        this.sendCommandNumber(38);
    }

    notifyStopFindDevice() {
        this.sendCommandNumber(39);
    }

    notifyBindingDevice() {
        this.sendCommandNumber(41);
    }

    notifyUnbindingDevice() {
        this.sendCommandNumber(48);
    }

    notifyNewWeChatMessage() {
        this.sendCommandNumber(6);
    }

    /**
     * TODO(Pedro): Test this
     *
     */
    setReadWeChatMessage() {
        this.sendCommandNumber(7);
    }

    openHeartService() {
        this.sendCommandNumber(34).then(() => {

        });
    }

    closeHeartService() {
        this.sendCommandNumber(35);
    }

    /**
     * Dont work
     * @param {string} message
     */
    setSedentaryReminder(message: string) {
        let stringArray = Buffer.from(message, 'utf8');

        this.SEDENTARY_MEASUREMENT.write(stringArray, (error: any, data: any) => {
            if (error) {
                console.log('SEDENTARY_MEASUREMENT.write Error');
                console.log(error);
                return;
            }

            if (data) {
                console.log('SEDENTARY_MEASUREMENT.write DATA');
                console.log(data);
            }
        });
    }

    /**
     * Get a service from a UUID
     * @param {string} uuid
     * @returns {any}
     */
    getService(uuid: string) {
        for (let service of this.services) {
            if (GatInfo.Normalize(service.uuid) === GatInfo.Normalize(uuid)) {
                return service;
            }
        }

        return null;
    }

    getCharacteristic(service: Service, uuid: string) {
        for (let characteristic of service.characteristics) {
            if (GatInfo.Normalize(characteristic.uuid) === uuid) {
                return characteristic;
            }
        }

        return null;
    }

    getCharacteristicName(Characteristic: Characteristic) {
        return this.getCharacteristicNameFromUUID(Characteristic.uuid);
    }

    getCharacteristicNameFromUUID(uuid: string) {
        for (let name in GatInfo) {
            let uuidCmp = GatInfo[name];
            if (GatInfo.Normalize(uuid) === GatInfo.Normalize(uuidCmp)) {
                return name;
            }
        }

        return uuid;
    }

    public parseTotalSteps(data: any) {
        let currentIndex = 0;
        let totalNumber = 0;
        let steps = 0;

        let string = data.toString('ascii');
        let hex = data.toString('hex');

        currentIndex = data[0] & 255;
        totalNumber = data[1] & 255;
        steps = ((((data[2] & 255) << 24) | ((data[3] & 255) << 16)) | ((data[4] & 255) << 8)) | (data[5] & 255);

        let dateTimestamp = (((((((data[6] & 255) << 24) | ((data[7] & 255) << 16)) | ((data[8] & 255) << 8)) | (data[9] & 255))) + 1262275200);
        let date = new Date(dateTimestamp * 1000);

        let day = date.getUTCDate();
        let month = date.getUTCMonth() + 1;
        let year = date.getUTCFullYear();
        let hours = date.getHours();
        let minutes = "0" + date.getMinutes();
        let seconds = "0" + date.getSeconds();

        let formattedTime = day + '/' + month + '/' + year + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);


        return {
            steps: steps,
            currentIndex: currentIndex,
            totalNumber: totalNumber,
            date: formattedTime

        };
    }

    getBatteryLevel() {
        return new Promise((resolve, reject) => {
            this.BATTERY_LEVEL.read((error: any, data: any) => {
                if (error) {

                }
                resolve(parseInt(data.toString('hex'), 16));
            });
        });
    }


    getTotalSteps() {
        return new Promise((resolve, reject) => {
            this.TOTAL_STEPS_MEASUREMENT.read((error: any, data: any) => {
                if (error) {
                    console.log('Error getting the total Steps');
                    reject();
                    return;
                }
                resolve(this.parseTotalSteps(data));
            });
        });
    }

    protected sendCommandNumber(num: number) {
        let data = new Buffer.allocUnsafe(1);
        data.writeUInt8(num, 0);
        return this.sendCommand(data);
    }

    protected sendCommand(data: any) {
        return new Promise((resolve, reject) => {
            this.OTA.write(data, true, (error: any) => {
                if (error) {
                    console.log('OTA.write Error');
                    console.log(error);
                    reject();
                    return;
                }

                resolve();
            });
        });
    }

    public ALARM_MEASUREMENT: any = false;
    public ALARM_SERVICE: any = false;
    public BATTERY_LEVEL: any = false;
    public BATTERY_SERVICE: any = false;
    public DEVICEINFO_HARDWARE: any = false;
    public DEVICEINFO_MEASUMENT: any = false;
    public DEVICEINFO_SERVICE: any = false;
    public DEVICE_NAME: any = false;
    public DEVICE_NAME_CHAR: any = false;
    public DEVICE_NAME_SERVICE: any = false;
    public FIND_PHONE_MEASUREMENT: any = false;
    public FIND_PHONE_NOTIFY_ENABLE: any = false;
    public FIND_PHONE_SERVICE: any = false;
    public FIRMWARE_READY_MEASUMENT: any = false;
    public FIRMWARE_READY_SERVICE: any = false;
    public GSENOR_MEASUREMENT: any = false;
    public GSENSOR_DATA_MEASUREMENT: any = false;
    public GSENSOR_SERVICE: any = false;
    public HEART_MEASUREMENT: any = false;
    public HEART_RATE_MEASUREMENT: any = false;
    public HEART_RATE_NOTICEFATION_ENABLE: any = false;
    public HEART_RATE_SERVICE: any = false;
    public OAD_ENABLE_UUID: any = false;
    public OAD_SERVICE_UUID: any = false;
    public OTA: any = false;
    public SEDENTARY_MEASUREMENT: any = false;
    public SEDENTARY_SERVICE: any = false;
    public SEGMENT_STEPS_MEASUREMENT: any = false;
    public SLEEP_INFO_CHAR: any = false;
    public STEPS_NOTICEFATION_ENABLE: any = false;
    public STEPS_SERVICE: any = false;
    public TAKE_PICTURE: any = false;
    public TIME_AND_ALARM_INFO: any = false;
    public TIME_SYNC: any = false;
    public TOTAL_STEPS_MEASUREMENT: any = false;
    public VIBRATION_REMIND: any = false;

    disconnect() {
        this.peripheral.disconnect();
    }
}