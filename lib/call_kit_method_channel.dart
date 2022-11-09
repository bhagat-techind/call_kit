import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'call_kit_platform_interface.dart';

/// An implementation of [CallKitPlatform] that uses method channels.
class MethodChannelCallKit extends CallKitPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('call_kit');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<int?> onRideRequest({required Map<String, dynamic> params,int durationInSec = 10}) async {
    var payload = {
      "payload":params,
      "duration":durationInSec
    };
    return await methodChannel.invokeMethod<int>("onRideRequest", payload);
  }

}
