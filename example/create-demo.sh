cordova create test_loc io.hankers.test_loc TEST_LOC
cd test_loc
cordova platform add ios android
cordova plugin add cordova-plugin-device --searchpath ~/cordova/plugins
cordova plugin add cordova-plugin-ionic-webview --searchpath ~/cordova/plugins
cordova plugin add cordova-plugin-geolocation-raw --searchpath ../../..
cp -r ../index.html ./www/
cp ../config.xml .
cordova prepare
