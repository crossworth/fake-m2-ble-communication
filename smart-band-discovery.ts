import * as _ from 'lodash';
import { on, startScanning, stopScanning } from 'noble';


import GatInfo from './gat-info';
import SmartBand from './smart-band';

export default class SmartBandDiscovery {
    protected callbackOnDiscovery: any = false;
    protected _debug: boolean = false;

    constructor() {

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

    onNewBand(callback) {
        this.callbackOnDiscovery = callback;
    }

    start(stopWhenFound: boolean = false) {
        on('stateChange', (state: string) => {
            if (state === 'poweredOn') {
                startScanning();
            } else {
                stopScanning();
            }
        });

        on('discover', (peripheral) => {
            this.log('New BLE peripheral discovered: ', peripheral.advertisement);

            if (_.isEqual(peripheral.advertisement.serviceUuids.sort(), GatInfo.SERVICES_UUID.sort())) {
                this.log('Found SmartBand', peripheral.advertisement.localName);
                if (this.callbackOnDiscovery) {
                    this.callbackOnDiscovery(new SmartBand(peripheral));
                }

                if (stopWhenFound) {
                    stopScanning();
                }
            }
        });
    }

    stop() {
        stopScanning();
    }
}