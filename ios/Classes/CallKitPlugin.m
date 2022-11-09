#import "CallKitPlugin.h"
#if __has_include(<call_kit/call_kit-Swift.h>)
#import <call_kit/call_kit-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "call_kit-Swift.h"
#endif

@implementation CallKitPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftCallKitPlugin registerWithRegistrar:registrar];
}
@end
