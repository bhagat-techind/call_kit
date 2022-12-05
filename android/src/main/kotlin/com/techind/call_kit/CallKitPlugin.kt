package com.techind.call_kit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.annotation.NonNull
import com.google.gson.Gson
import com.techind.call_kit.model.EventModel
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodChannel.MethodCallHandler


/** CallKitPlugin */
class CallKitPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var context: Context
  private var activity: Activity? = null
  private var TAG : String = "CallKitPlugin"

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "call_kit")
    context = flutterPluginBinding.applicationContext
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    try {
      Log.w(TAG, "onMethodCall ==>> ${call.method}")
      when (call.method) {
        "getPlatformVersion" -> {
          result.success("Android ${android.os.Build.VERSION.RELEASE}")
        }
        "onRideRequest" -> {
          Log.w(TAG, "onRideRequest ==>> $activity ${MyApplication.getInstance().applicationContext}")
          val data = (call.arguments() ?: HashMap<String, Any?>())
          onRide(data,result)
        }
        else -> {
          result.notImplemented()
        }
      }
    }catch (error: Exception) {
      result.error("error", error.message, "")
    }
  }

  private fun onRide(data:HashMap<String,Any?>,@NonNull result: Result) {

    val payloadData : String = Gson().toJson(data["payload"])
    val durationInSec : Int = (data["duration"]?:10) as Int
    MapActivity.setFlutterResult(result)
    Log.w(TAG, "onRide ==>> $payloadData")
    val intent = Intent(MyApplication.getInstance().applicationContext, MapActivity::class.java)
    intent.putExtra("payload", payloadData)
    intent.putExtra("duration", durationInSec)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    MyApplication.getInstance().applicationContext!!.startActivity(intent)
//    val uri = Uri.Builder().scheme("rating").authority("call").build()
//    val i = Intent(Intent.ACTION_VIEW)
//    i.data = uri
//    i.putExtra("payload", payload)
//    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//    MyApplication.getInstance().applicationContext.startActivity(i)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
  override fun onDetachedFromActivity() {
    activity = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    activity = binding.activity;
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity;
  }

  override fun onDetachedFromActivityForConfigChanges() {
    activity = null
  }
}
