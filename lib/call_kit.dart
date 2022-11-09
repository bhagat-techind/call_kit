
import 'call_kit_platform_interface.dart';

class CallKit {

  Future<String?> getPlatformVersion() {
    return CallKitPlatform.instance.getPlatformVersion();
  }

  Future<int?> onRideRequest({required Map<String, dynamic> params,int durationInSec = 10}) {
    return CallKitPlatform.instance.onRideRequest(params: params,durationInSec: durationInSec);
  }

}
