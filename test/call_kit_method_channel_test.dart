import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:call_kit/call_kit_method_channel.dart';

void main() {
  MethodChannelCallKit platform = MethodChannelCallKit();
  const MethodChannel channel = MethodChannel('call_kit');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
