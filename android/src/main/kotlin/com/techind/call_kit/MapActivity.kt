package com.techind.call_kit

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import com.google.gson.Gson
import com.techind.call_kit.databinding.ActivityMapBinding
import com.techind.call_kit.model.EventModel
import io.flutter.plugin.common.MethodChannel.Result

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private lateinit var fullscreenContent: TextView
    private lateinit var fullscreenContentControls: LinearLayout
    private val hideHandler = Handler()
    lateinit var eventModel:EventModel
    var durationInSec : Int = 5
    private var TAG = MapActivity::class.java.name
    companion object {
        @JvmStatic
        private lateinit var result: Result

        @JvmStatic
        fun getFlutterResult(): Result {
            return result
        }
        @JvmStatic
        fun setFlutterResult(@NonNull flutterResult: Result){
            result = flutterResult
        }
    }
    private val hideRunnable = Runnable {
        if(result!=null)
        {
            result.success(0)
        }
        finish()
    }

//    fun setFlutterResult(@NonNull flutterResult: Result){
//        result = flutterResult
//    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var payload = intent.getStringExtra("payload")
        Log.w(TAG,"payload ==>> $payload")
        eventModel = Gson().fromJson(payload, EventModel::class.java)
        durationInSec = intent.getIntExtra("duration",5)
        Log.w(TAG,"durationInSec ==>> $durationInSec")

        Log.w("MapActivity","==>> ${payload}")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        // Set up the user interaction to manually show or hide the system UI.
        fullscreenContent = binding.fullscreenContent
        fullscreenContent.text = "${eventModel.ride.rider.fullName} request for ride to "+eventModel.ride.endAddress
        fullscreenContentControls = binding.fullscreenContentControls

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.w(TAG,"onPostCreate durationInSec ==>> $durationInSec")
        delayedHide(durationInSec)
    }
    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delaySec: Int) {
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, (delaySec * 1000).toLong())
    }

}