#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import <Cordova/CDVPlugin.h>

enum CDVLocationStatus {
    PERMISSIONDENIED = 1,
    POSITIONUNAVAILABLE,
    TIMEOUT
};
typedef NSUInteger CDVLocationStatus;

// simple object to keep track of location information
@interface CDVLocationData : NSObject {
    CDVLocationStatus locationStatus;
    NSMutableArray* locationCallbacks;
    NSMutableDictionary* watchCallbacks;
    CLLocation* locationInfo;
}

@property (nonatomic, assign) CDVLocationStatus locationStatus;
@property (nonatomic, strong) CLLocation* locationInfo;
@property (nonatomic, strong) NSMutableArray* locationCallbacks;
@property (nonatomic, strong) NSMutableDictionary* watchCallbacks;

@end

@interface CDVRawLocation : CDVPlugin <CLLocationManagerDelegate>{
    @private BOOL __locationStarted;
    @private BOOL __highAccuracyEnabled;
    CDVLocationData* locationData;
}

@property (nonatomic, strong) NSString *appName;
@property (nonatomic, strong) NSString *currentCallbackId;

- (void)configLocation:(CDVInvokedUrlCommand *)command;

@property (nonatomic, strong) CLLocationManager* locationManager;
@property (nonatomic, strong) CDVLocationData* locationData;

- (void)getLocation:(CDVInvokedUrlCommand*)command;
- (void)addWatch:(CDVInvokedUrlCommand*)command;
- (void)clearWatch:(CDVInvokedUrlCommand*)command;
- (void)returnLocationInfo:(NSString*)callbackId andKeepCallback:(BOOL)keepCallback;
- (void)returnLocationError:(NSUInteger)errorCode withMessage:(NSString*)message;
- (void)startLocation:(BOOL)enableHighAccuracy;

- (void)locationManager:(CLLocationManager*)manager
    didUpdateToLocation:(CLLocation*)newLocation
           fromLocation:(CLLocation*)oldLocation;

- (void)locationManager:(CLLocationManager*)manager
       didFailWithError:(NSError*)error;

- (BOOL)isLocationServicesEnabled;
@end
