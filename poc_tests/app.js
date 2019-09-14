const noble = require('noble');
const UUID = require('./GatInfo');


noble.on('stateChange', (state) => {
    if (state === 'poweredOn') {
        console.log('Scanning...');
        noble.startScanning(UUID.SERVICES_UUID, false);
    } else {
        noble.stopScanning();
    }
});

let upd = null;

function update(characteristic) {
    let data = new Buffer.allocUnsafe(15);
    data.writeUInt8(53, 0);
    data.writeUInt8(124, 1);
    data.writeUInt8(49, 2);
    data.writeUInt8(124, 3);
    data.writeUInt8(124, 4);
    data.writeUInt8(124, 5);
    data.writeUInt8(124, 6);
    data.writeUInt8(124, 7);
    data.writeUInt8(124, 8);
    data.writeUInt8(124, 9);
    data.writeUInt8(124, 10);
    data.writeUInt8(124, 11);
    data.writeUInt8(124, 12);
    data.writeUInt8(124, 13);
    data.writeUInt8(124, 14);

    characteristic.write(data, false, function(err) {
        if (err !== null) {
            console.log('Erro', err);
        } else {
            console.log('SEND');
        }
    });
}

let currentIndex = 0;
let totalNumber = 0;
let steps = 0;





let servicesList = [];

noble.on('discover', (peripheral) => {
    noble.stopScanning();
    console.log('Smartband found: ', peripheral.advertisement);

    peripheral.connect((error) => {
        console.log('Connected with: ' + peripheral.uuid);

        peripheral.discoverServices([], (err, services) => {
            services.forEach((service) => {
                let serviceUuid = service.uuid;
                let serviceName = serviceUuid;

                for (let key in UUID) {
                    if (UUID.hasOwnProperty(key) && typeof UUID[key] == "string") {
                        if (serviceUuid == UUID.fromString(UUID[key])) {
                            serviceName = key;
                        }
                    }
                }

                // console.log('Service found:', serviceName);

                servicesList[serviceName] = [];

                service.discoverCharacteristics([], (err, characteristics) => {
                    characteristics.forEach((characteristic) => {
                        let characteristicUuid = characteristic.uuid;

                        // console.log('Characteristic found:', serviceUuid, characteristicUuid);

                        let characteristicName = characteristicUuid;

                        for (let key in UUID) {
                            if (UUID.hasOwnProperty(key) && typeof UUID[key] == "string") {
                                if (characteristicUuid == UUID.fromString(UUID[key])) {
                                    characteristicName = key;
                                }
                            }
                        }

                        // console.log('Characteristic found:', serviceName + ' : ' + characteristicName + ' - ' + characteristic.properties);

                        servicesList[serviceName][characteristicName] = characteristic;

                        if (characteristicUuid == "2a5f") {
                            servicesDiscovered(servicesList);
                        }

                        return;


                        characteristic.discoverDescriptors((error, descriptors) => {

                            descriptors.forEach((descriptor) => {
                                console.log("Descriptor found:", descriptor.uuid, "Service uuid:", descriptor._serviceUuid, " Char uuid:", descriptor._characteristicUuid);

                                descriptor.readValue((error, data) => {
                                    let string = data.toString('ascii');
                                    let hex = data.toString('hex');

                                    console.log('ASCII: ', string, ' Hex: ', hex);
                                    console.log('\n');
                                });
                            });

                        });


                        return;


                        if (characteristicUuid == "6e400003b5a3f393e0a9e50e24dcca9e" && serviceUuid == "1820" ) {
                            upd = characteristic;
                            update(characteristic);

                            setInterval(function () {
                                update(upd);
                            }, 10000);
                        }

                        if (characteristicUuid == "6e400011b5a3f393e0a9e50e24dcca9e")  {

                            characteristic.read(function(err, data) {
                                console.log('READ 6e400011b5a3f393e0a9e50e24dcca9e');

                                let string = data.toString('ascii');
                                let hex = data.toString('hex');

                                console.log('ASCII: ', string, ' Hex: ', hex);
                                console.log('\n');
                            });

                            characteristic.on('data', function(data, isNotification) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');
                                console.log("6e400011b5a3f393e0a9e50e24dcca9e");
                                console.log('ASCII: ', string, ' Hex: ', hex, ' Dec:', parseInt(hex, 16));
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "6e400011b5a3f393e0a9e50e24dcca9e");
                            });

                            characteristic.subscribe(function(err) {});
                        }

                        if (characteristicUuid == UUID.fromString(UUID.TOTAL_STEPS_MEASUREMENT))  {

                            characteristic.on('data', function(data, isNotification) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');
                                console.log("TOTAL_STEPS_MEASUREMENT");
                                console.log('ASCII: ', string, ' Hex: ', hex, ' Dec:', parseInt(hex, 16));
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "TOTAL_STEPS_MEASUREMENT");
                            });

                            characteristic.subscribe(function(err) {});
                            characteristic.notify(true);
                        }

                        if (characteristicUuid == UUID.fromString(UUID.TOTAL_STEPS_MEASUREMENT)) {

                            characteristic.read(function(err, data) {
                                console.log('TOTAL_STEPS_MEASUREMENT');

                                let string = data.toString('ascii');
                                let hex = data.toString('hex');

                                console.log('ASCII: ', string, ' Hex: ', hex);
                                console.log('Bytes');
                                console.log('0: ', data[0], ' 1:', data[1], ' 2:', data[2], ' 3:', data[3], ' 4:', data[4], ' 5:', data[5], ' 6:', data[6], ' 7:', data[7], ' 8:', data[8], ' 9:', data[9]);
                                console.log('\n');

                                currentIndex = data[0] & 255;
                                totalNumber = data[1] & 255;
                                steps = ((((data[2] & 255) << 24) | ((data[3] & 255) << 16)) | ((data[4] & 255) << 8)) | (data[5] & 255);

                                console.log('Current index: ', currentIndex);
                                console.log('Total number: ', totalNumber);
                                console.log('Step: ', steps);

                                let dateTimestamp = (((((((data[6] & 255) << 24) | ((data[7] & 255) << 16)) | ((data[8] & 255) << 8)) | (data[9] & 255))) + 1262275200);
                                let date = new Date(dateTimestamp * 1000);

                                let day = date.getUTCDate();
                                let month = date.getUTCMonth() + 1;
                                let year = date.getUTCFullYear();
                                let hours = date.getHours();
                                let minutes = "0" + date.getMinutes();
                                let seconds = "0" + date.getSeconds();

                                let formattedTime = day + '/' + month + '/' + year + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);

                                console.log('Date: ', formattedTime);
                                console.log('\n');
                            });

                        }

                        return;

                        if (characteristicUuid == "6e400003b5a3f393e0a9e50e24dcca9e" &&
                            serviceUuid == "6e400004b5a3f393e0a9e50e24dcca9e" && false) {
                            console.log("OK");
                        }

                        if (characteristicUuid == "6e400003b5a3f393e0a9e50e24dcca9e" &&
                            serviceUuid == "6e400001b5a3f393e0a9e50e24dcca9e" && false) {

                            characteristic.read(function(err, data) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');

                                console.log("6e400001b5a3f393e0a9e50e24dcca9e");
                                console.log('ASCII: ', string, ' Hex: ', hex);
                            });

                            characteristic.on('data', function(data, isNotification) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');
                                console.log("6e400001b5a3f393e0a9e50e24dcca9e");
                                console.log('ASCII: ', string, ' Hex: ', hex, ' Dec:', parseInt(hex, 16));
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "6e400001b5a3f393e0a9e50e24dcca9e");
                            });

                            characteristic.subscribe(function(err) {});
                        }

                        if (characteristicUuid == UUID.fromString(UUID.TIME_SYNC) && false) {
                            console.log('TIME_SYNC');
                            // set 14:48 10/02
                            let data = new Buffer.allocUnsafe(4);
                            data.writeUInt8(90, 0);
                            data.writeUInt8(126, 1);
                            data.writeUInt8(149, 2);
                            data.writeUInt8(204, 3);

                            characteristic.write(data, false, function(err) {
                                if (err !== null) {
                                    console.log('Erro', err);
                                }
                            });

                        }

                        if (characteristicUuid == "6e400001b5a3f393e0a9e50e24dcca9e" && false)  {
                            characteristic.on('data', function(data, isNotification) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');
                                console.log("6e400001b5a3f393e0a9e50e24dcca9e");
                                console.log('ASCII: ', string, ' Hex: ', hex, ' Dec:', parseInt(hex, 16));
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "6e400001b5a3f393e0a9e50e24dcca9e");
                            });

                            characteristic.subscribe(function(err) {});
                        }

                        if (characteristicUuid == "6e400011b5a3f393e0a9e50e24dcca9e" && false)  {

                            characteristic.on('data', function(data, isNotification) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');
                                console.log("6e400011b5a3f393e0a9e50e24dcca9e");
                                console.log('ASCII: ', string, ' Hex: ', hex, ' Dec:', parseInt(hex, 16));
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "6e400011b5a3f393e0a9e50e24dcca9e");
                            });

                            characteristic.subscribe(function(err) {});
                        }

                        if (characteristicUuid == UUID.fromString(UUID.TIME_AND_ALARM_INFO) && false)  {

                            characteristic.on('data', function(data, isNotification) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');
                                console.log("TIME_AND_ALARM_INFO");
                                console.log('ASCII: ', string, ' Hex: ', hex, ' Dec:', parseInt(hex, 16));
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "TIME_AND_ALARM_INFO");
                            });

                            characteristic.subscribe(function(err) {});
                        }

                        if (characteristicUuid == UUID.fromString(UUID.SEGMENT_STEPS_MEASUREMENT) && false)  {

                            characteristic.on('data', function(data, isNotification) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');
                                console.log("SEGMENT_STEPS_MEASUREMENT");
                                console.log('ASCII: ', string, ' Hex: ', hex, ' Dec:', parseInt(hex, 16));
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "SEGMENT_STEPS_MEASUREMENT");
                            });

                            characteristic.subscribe(function(err) {});
                        }

                        if (characteristicUuid == UUID.fromString(UUID.TOTAL_STEPS_MEASUREMENT))  {

                            characteristic.on('data', function(data, isNotification) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');
                                console.log("TOTAL_STEPS_MEASUREMENT");
                                console.log('ASCII: ', string, ' Hex: ', hex, ' Dec:', parseInt(hex, 16));
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "TOTAL_STEPS_MEASUREMENT");
                            });

                            characteristic.subscribe(function(err) {});
                        }

                        if (characteristicUuid == UUID.fromString(UUID.FIND_PHONE_MEASUREMENT) && false)  {

                            characteristic.on('data', function(data, isNotification) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');
                                console.log("FIND_PHONE_MEASUREMENT");
                                console.log('ASCII: ', string, ' Hex: ', hex, ' Dec:', parseInt(hex, 16));
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "FIND_PHONE_MEASUREMENT");
                            });

                            characteristic.subscribe(function(err) {});
                        }

                        if (characteristicUuid == UUID.fromString(UUID.BATTERY_LEVEL) && false)  {

                            characteristic.on('data', function(data, isNotification) {
                                let hex = data.toString('hex');
                                console.log('Battery: ', parseInt(hex, 16), '%');
                            });

                            characteristic.once('notify', function(state) {
                                console.log("state change", state, "BATTERY_LEVEL");
                            });

                            characteristic.subscribe(function(err) {});

                        }

                        if (characteristicUuid == UUID.fromString(UUID.DEVICEINFO_MEASUMENT) && false)  {
                            console.log('DEVICEINFO_MEASUMENT');

                            characteristic.read(function(err, data) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');

                                console.log('ASCII: ', string, ' Hex: ', hex);

                            });
                        }

                        if (characteristicUuid == UUID.fromString(UUID.DEVICE_NAME_CHAR) && false)  {
                            console.log('DEVICE_NAME_CHAR');

                            characteristic.read(function(err, data) {
                                let string = data.toString('ascii');
                                let hex = data.toString('hex');

                                console.log('ASCII: ', string, ' Hex: ', hex);

                            });
                        }

                        if (characteristicUuid == UUID.fromString(UUID.FIRMWARE_READY_MEASUMENT) && false)  {
                            // SAME UUID AS OTA
                            console.log('FIRMWARE_READY_MEASUMENT');

                            let data = new Buffer(1);
                            data.writeUInt8(1, 0);
                            // 1 - update ready?
                            // 34 - nothing visible
                            // 35 - nothing visible


                            characteristic.write(data, false, function(err) {
                                if (err !== null) {
                                    console.log('Erro', err);
                                }
                            });

                        }

                        if (characteristicUuid == UUID.fromString(UUID.OTA) && false)  {
                            console.log('OTA');

                            let data = new Buffer.allocUnsafe(10);
                            data.writeUInt8(7, 0);
                            data.writeUInt8(57, 1);
                            data.writeUInt8(54, 2);
                            data.writeUInt8(51, 3);
                            data.writeUInt8(48, 4);
                            data.writeUInt8(57, 5);
                            data.writeUInt8(57, 6);
                            data.writeUInt8(53, 7);
                            data.writeUInt8(50, 8);
                            data.writeInt8(-1, 9);
                            // 1 - turn the heart light on, but there is no way to turn it off
                            // the device will eventually reboot it self
                            // 2 - vibrate two times (very fast and short) (set device no vibrate, maybe toggle)
                            // 3 - nothing visible
                            // 4 - vibrate, show message icon one time (message icon solid)
                            // 5 - nothing visible
                            // 6 - vibrate, show message icon one time (message icon with 3 dots inside)
                            // 7 - nothing visible
                            // 8 - nothing visible
                            // 9 - nothing visible
                            // 10 - nothing visible
                            // 17 - nothing visible
                            // 38 - vibrate until clicked (find device?)
                            // 39 - nothing visible, was expected to stop vibrating the device
                            // 40 - vibrate, show call icon until pressed
                            // 41 - vibrate, show icone new binding (?) when clicked show "right" icon
                            // 49 - turn on the heart light, and this time it turn off when clicked
                            // 50 - reboot the device

                            // Write a number on the screen
                            // let data = new Buffer.allocUnsafe(10);
                            // data.writeUInt8(7, 0); //
                            // data.writeUInt8(57, 1);
                            // data.writeUInt8(54, 2);
                            // data.writeUInt8(51, 3);
                            // data.writeUInt8(48, 4);
                            // data.writeUInt8(57, 5);
                            // data.writeUInt8(57, 6);
                            // data.writeUInt8(53, 7);
                            // data.writeUInt8(50, 8);
                            // data.writeInt8(-1, 9);


                            characteristic.write(data, false, function(err) {
                                if (err !== null) {
                                    console.log('Erro', err);
                                }
                            });


                        }

                        if (characteristicUuid == UUID.fromString(UUID.HEART_MEASUREMENT) && false)  {

                            characteristic.read(function(err, data) {
                                console.log('HEART_MEASUREMENT');


                                console.log('\n');
                            });

                        }

                        if (characteristicUuid == UUID.fromString(UUID.SEGMENT_STEPS_MEASUREMENT) && false) {

                            characteristic.read(function(err, data) {
                                console.log('SEGMENT_STEPS_MEASUREMENT');

                                let string = data.toString('ascii');
                                let hex = data.toString('hex');

                                console.log('ASCII: ', string, ' Hex: ', hex);

                                let isEmptyMsg = false;
                                if (data != null) {
                                    if (data[0] == -1 && data[13] == -1) {
                                        isEmptyMsg = true;
                                        for (let i = 0; i < 12; i++) {
                                            if (data[i + 1] != 0) {
                                                isEmptyMsg = false;
                                                break;
                                            }
                                        }

                                        if (isEmptyMsg) {
                                            console.log('Empty message (?!)');
                                        }
                                    }

                                    if (!isEmptyMsg) {
                                        currentIndex = data[0] & 255;
                                        totalNumber = data[1] & 255;
                                        steps = ((((data[2] & 255) << 24) | ((data[3] & 255) << 16)) | ((data[4] & 255) << 8)) | (data[5] & 255);
                                        let start = ((((((data[6] & 255) << 24) | ((data[7] & 255) << 16)) | ((data[8] & 255) << 8)) | (data[9] & 255))) + 1262275200;
                                        let end = ((((((data[10] & 255) << 24) | ((data[11] & 255) << 16)) | ((data[12] & 255) << 8)) | (data[13] & 255))) + 1262275200;

                                        console.log('CurrentIndex: ', currentIndex);
                                        console.log('totalNumber: ', totalNumber);
                                        console.log('steps: ', steps);
                                    }

                                }

                                console.log('\n');
                            });

                        }

                        if (characteristicUuid == UUID.fromString(UUID.FIND_PHONE_MEASUREMENT) && false) {

                            characteristic.read(function(err, data) {
                                console.log('FIND_PHONE_MEASUREMENT');

                                let string = data.toString('ascii');
                                let hex = data.toString('hex');

                                console.log('ASCII: ', string, ' Hex: ', hex);

                                let heartType = data[0] & 255;
                                console.log('Heart type: ', heartType);
                                console.log('\n');
                            });
                        }

                        if (characteristicUuid == UUID.fromString(UUID.TOTAL_STEPS_MEASUREMENT)) {

                            characteristic.read(function(err, data) {
                                console.log('TOTAL_STEPS_MEASUREMENT');

                                let string = data.toString('ascii');
                                let hex = data.toString('hex');

                                console.log('ASCII: ', string, ' Hex: ', hex);
                                console.log('Bytes');
                                console.log('0: ', data[0], ' 1:', data[1], ' 2:', data[2], ' 3:', data[3], ' 4:', data[4], ' 5:', data[5], ' 6:', data[6], ' 7:', data[7], ' 8:', data[8], ' 9:', data[9]);
                                console.log('\n');

                                currentIndex = data[0] & 255;
                                totalNumber = data[1] & 255;
                                steps = ((((data[2] & 255) << 24) | ((data[3] & 255) << 16)) | ((data[4] & 255) << 8)) | (data[5] & 255);

                                console.log('Current index: ', currentIndex);
                                console.log('Total number: ', totalNumber);
                                console.log('Step: ', steps);

                                let dateTimestamp = (((((((data[6] & 255) << 24) | ((data[7] & 255) << 16)) | ((data[8] & 255) << 8)) | (data[9] & 255))) + 1262275200);
                                let date = new Date(dateTimestamp * 1000);

                                let day = date.getUTCDate();
                                let month = date.getUTCMonth() + 1;
                                let year = date.getUTCFullYear();
                                let hours = date.getHours();
                                let minutes = "0" + date.getMinutes();
                                let seconds = "0" + date.getSeconds();

                                let formattedTime = day + '/' + month + '/' + year + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);

                                console.log('Date: ', formattedTime);
                                console.log('\n');
                            });

                        }
                    });
                });

            });
        });
    });


});


function servicesDiscovered(services) {
    console.log(services);
}
