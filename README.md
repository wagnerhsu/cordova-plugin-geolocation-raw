# cordova-plugin-geolocation-raw

Cordova定位插件，不使用第三方库，暂无逆地址

# Install

```bash
cordova plugin add cordova-plugin-geolocation-raw
```

## getLocation方法

### Success return data

- latitude：经度
- longitude：纬度

# Useage

```Javascript
	// locate
	RawLocation.getLocation({ }, function (locationInfo) {
		// do something
		console.log(JSON.stringify(locationInfo));
	}, function (err) {
		console.log(err);
	});
```
