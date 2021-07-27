package io.hankers.plugin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class RawLocation extends CordovaPlugin {

    public static String TAG = "RawLocation";
    LocationManager mLocationManager;

    // JS回掉接口对象
    public static CallbackContext mCallback = null;
    // 权限申请码
    private static final int PERMISSION_REQUEST_CODE = 500;

    @Override
    protected void pluginInitialize() {
        checkPermissions();
    }

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        mCallback = callbackContext;
        if (!checkPermissions()) {
            return false;
        }
        if (action.equals("getLocation")) {
            Location location = getLastKnownLocation();
            try {
                JSONObject obj = new JSONObject();
                if (location.getLatitude() > 0 && location.getLongitude() > 0) {
                    obj.put("latitude", location.getLatitude());
                    obj.put("longitude", location.getLongitude());
                    //obj.put("address", location.getAddress());
                } else {
                    obj.put("code", -1);
                    obj.put("msg", "unknown error");
                }
                mCallback.success(obj);
            } catch (Exception ex) {
                mCallback.error(ex.getMessage());
            }
            return true;
        } else if (action.equals("configLocation")) {
            didConfig();
            return true;
        }

        return false;
    }

    public void didConfig() {
        if (mCallback != null) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("code", 0);
                obj.put("msg", "初始化成功");
                mCallback.success(obj);
            } catch (Exception ex) {
                mCallback.error(ex.getMessage());
            }
        }
    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) this.cordova.getActivity().getApplicationContext().getSystemService(LOCATION_SERVICE);
        }
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    /**
     * 权限检测回调
     */
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            didConfig();
        }
    }

    private boolean checkPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (!this.cordova.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ||
                !this.cordova.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            this.cordova.requestPermissions(this, PERMISSION_REQUEST_CODE, permissions);
            return false;
        } else if (!this.cordova.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            this.cordova.requestPermission(this, PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_COARSE_LOCATION);
            return false;
        } else if (!this.cordova.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            this.cordova.requestPermission(this, PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION);
            return false;
        }
        return true;
    }

}
