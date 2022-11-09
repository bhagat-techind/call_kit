import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:call_kit/call_kit.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _callKitPlugin = CallKit();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion =
          await _callKitPlugin.getPlatformVersion() ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: InkWell(
          onTap: (){
            var payload = {
              "id": 70126,
              "eventType": "REQUESTED",
              "title": null,
              "message": null,
              "parameters": "{\"acceptanceExpiration\":1667982635086,\"acknowledgeExpiration\":1667982630086,\"eta\":365,\"stacked\":false}",
              "ride": {
                "id": 2247,
                "status": "REQUESTED",
                "rider": {
                  "id": 7,
                  "firstname": "Bhagat",
                  "lastname": "Rider",
                  "phoneNumber": "+917060825354",
                  "email": "bhagatsingh9084@gmail.com",
                  "rating": 4.68,
                  "user": {
                    "photoUrl": "https://ride-vegas.s3.amazonaws.com/user-photos/6bb43366-cb58-4908-bf16-18dbfa11f3d9.png"
                  },
                  "fullName": "Bhagat Rider"
                },
                "estimatedTimeArrive": 365,
                "startLocationLat": 36.17827415860478,
                "startLocationLong": -115.2433479577303,
                "endLocationLat": 36.15431436476919,
                "endLocationLong": -115.17451442778112,
                "startAddress": "500 North Rainbow Boulevard",
                "endAddress": "2411 Llewellyn Drive",
                "start": {
                  "address": "500 North Rainbow Boulevard"
                },
                "end": {
                  "address": "2411 Llewellyn Drive"
                },
                "surgeFactor": 1.0,
                "requestedCarType": {
                  "title": "STANDARD",
                  "carCategory": "REGULAR",
                  "plainIconUrl": "https://media.ride-vegas.com/regular.png",
                  "configuration": "{\"skipRideAuthorization\": false}"
                },
                "requestedDriverTypes": [],
                "requestedDispatchType": "REGULAR",
                "parameters": {
                  "acceptanceExpiration": 1667982635086,
                  "acknowledgeExpiration": 1667982630086,
                  "eta": 365,
                  "stacked": false
                },
                "driverPayment": null,
                "freeCreditCharged": null,
                "mapUrl": null
              }
            };
            _callKitPlugin.onRideRequest(params: payload,durationInSec: 10 );
          },
          child: Center(
            child: Text('Running on: $_platformVersion\n'),
          ),
        ),
      ),
    );
  }
}
