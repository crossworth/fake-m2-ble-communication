import SmartBand from './smart-band';
import SmartBandDiscovery from './smart-band-discovery';
import { currentId } from 'async_hooks';



let bandDiscovery = new SmartBandDiscovery();
bandDiscovery.debug = true;

bandDiscovery.onNewBand(async (band: SmartBand) => {
    console.log(band.getPeripheralInfo().advertisement.localName);
    await band.connect();
    // console.log(band.HEART_MEASUREMENT);
    band.openHeartService();


    // band.BATTERY_LEVEL.notify(true);
    band.FIND_PHONE_MEASUREMENT.notify(true);
    // band.TOTAL_STEPS_MEASUREMENT.notify(true);
    // // band.HEART_MEASUREMENT.notify(true);
    // // band.GSENOR_MEASUREMENT.notify(true);
    // band.SLEEP_INFO_CHAR.notify(true);
    // band.SEGMENT_STEPS_MEASUREMENT.notify(true);
    //
    //
    // band.BATTERY_LEVEL.on('data', (data, isNotification) => {
    //     let hex = data.toString('hex');
    //     console.log(`Battery value: ${parseInt(data, 10)}`);
    //     console.log(`Battery: ${parseInt(hex, 16)}%`);
    //     console.log(`isNotification ${isNotification}`);
    // });
    //
    band.FIND_PHONE_MEASUREMENT.on('data', (data, isNotification) => {
        let type = data[0] & 255;
        console.log(`FIND_PHONE_MEASUREMENT ${data}`);
        console.log(`type ${type}`);
    });
    //
    // band.SLEEP_INFO_CHAR.on('data', (data, isNotification) => {
    //     console.log(`SLEEP_INFO_CHAR data.length = ${data.length}`);
    // });
    //
    // band.TOTAL_STEPS_MEASUREMENT.on('data', (data, isNotification) => {
    //     let steps = band.parseTotalSteps(data);
    //     console.log('TOTAL_STEPS_MEASUREMENT');
    //     console.log(steps);
    // });
    //
    // band.SEGMENT_STEPS_MEASUREMENT.on('data', (data, isNotification) => {
    //     console.log('SEGMENT_STEPS_MEASUREMENT');
    // });



    // console.log(band.FIRMWARE_READY_SERVICE);
    // console.log('Open Heart service');
    //
    // if (band.FIRMWARE_READY_MEASUMENT) {
    //     let data = new Buffer.allocUnsafe(1);
    //     data.writeUInt8(34, 0);
    //
    //     band.FIRMWARE_READY_MEASUMENT.write(data, (error: any, data: any) => {
    //         if (error) {
    //             console.log('FIRMWARE_READY_MEASUMENT.write Error');
    //             console.log(error);
    //             return;
    //         }
    //
    //         if (data) {
    //             console.log('FIRMWARE_READY_MEASUMENT.write DATA');
    //             console.log(data);
    //         }
    //     });
    // } else {
    //     console.log('false FIRMWARE_READY_MEASUMENT');
    // }
    //
    // setTimeout(() => {
    //
    //
    // }, 5 * 1000);

    // band.HEART_RATE_SERVICE.on('data', (data, isNotification) => {
    //     console.log('HEART_RATE_SERVICE');
    // });

    // band.HEART_MEASUREMENT.on('data', (data, isNotification) => {
    //     let type = data[0] & 255;
    //
    //     let ppgData = new Buffer.allocUnsafe(1);
    //
    //     for (let i = 0; i < data.length - 1; i++) {
    //         ppgData.writeUInt8(data[i + 1], i);
    //     }
    //
    //     console.log('HEART_MEASUREMENT');
    //     console.log(`Type ${type}`);
    //     console.log(`Byte0 ${data[0]}`);
    //
    //     console.log(`heart_data=[${(data[1] & 255)}, ${(data[2] & 255)}, ${(data[3] & 255)}, ${(data[4] & 255)}, ${(data[5] & 255)}, ${(data[6] & 255)}, ${(data[7] & 255)}, ${(data[8] & 255)}, ${(data[9] & 255)}, ${(data[10] & 255)}, ${(data[11] & 255)}, ${(data[12] & 255)}, ${(data[13] & 255)}]`);
    //     console.log(`data.length=${data.length}`);
    // });

    // band.BATTERY_LEVEL.subscribe(function(err) {
    //
    // });


    // setInterval(() => {
    //     band.test1();
    //
    //
    //
    //
    //     // band.test2();
    //     // band.getBatteryLevel().then((result) => {
    //     //     console.log(`${result}% get from fn`);
    //     // });
    // }, 1000);

    // band.TOTAL_STEPS_MEASUREMENT.notify(true);
    // band.TOTAL_STEPS_MEASUREMENT.on('notify', (state) => {
    //     console.log('notify', state);
    // });
    //
    // band.TOTAL_STEPS_MEASUREMENT.on('data', (data, isNotification) => {
    //     console.log('on data');
    //     console.log('is notification', isNotification);
    // });
    //
    // band.TOTAL_STEPS_MEASUREMENT.subscribe(function(err) {});
    // setInterval(() => {
    //     band.getTotalSteps().then((result) => {
    //        console.log(result);
    //     });
    // }, 1000);

    //
    // band.TOTAL_STEPS_MEASUREMENT.once('notify', function(state) {
    //     console.log("state change", state, "TOTAL_STEPS_MEASUREMENT");
    // });
    //
    // band.TOTAL_STEPS_MEASUREMENT.subscribe(function(err) {});

    // band.disconnect();
});

bandDiscovery.start(true);


