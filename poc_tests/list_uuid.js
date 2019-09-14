const UUID = require('./GatInfo');



for (let key in UUID) {
    if (UUID.hasOwnProperty(key) && typeof UUID[key] == "string") {
        console.log(key + " -> " + UUID[key]);
    }
}