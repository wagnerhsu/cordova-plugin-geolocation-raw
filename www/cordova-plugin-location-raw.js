var exec = require('cordova/exec');

function isFunction(fn) {
    return Object.prototype.toString.call(fn)=== '[object Function]';
}

module.exports = {
    configLocation: function (param, success) {
        param = param || { };
        param.android = param.android || { };
        param.ios = param.ios || { };

        exec(success, null, "RawLocation", "configLocation", [param]);
    },
    getLocation: function(param, success, error) {
        if (isFunction(param)) {
            error = success;
            success = param;
            param = null;
        }
        param = param || { retGeo: false };
        exec(success, error, "RawLocation", "getLocation", [param]);
    }
};
