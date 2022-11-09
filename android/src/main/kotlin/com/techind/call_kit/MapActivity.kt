package com.techind.call_kit

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.techind.call_kit.databinding.ActivityMapBinding
import com.techind.call_kit.map.MapManager
import com.techind.call_kit.model.EventModel
import io.flutter.plugin.common.MethodChannel.Result as FResult


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MapActivity : AppCompatActivity(), OnMapReadyCallback,MapManager.MapDimensionsProvider {

    private lateinit var binding: ActivityMapBinding

    private val hideHandler = Handler()
    lateinit var eventModel:EventModel
    var durationInSec : Int = 5
    private var TAG = MapActivity::class.java.name
    private val bottomPadding = 0
    private var actionBarHeight = 0
    lateinit  var mapManager:MapManager

    companion object {
        @JvmStatic
        private lateinit var result: FResult

        @JvmStatic
        fun getFlutterResult(): FResult {
            return result
        }
        @JvmStatic
        fun setFlutterResult(@NonNull flutterResult: FResult){
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
        mapManager = MapManager(this,this)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mapView.onCreate(savedInstanceState)

        var payload = intent.getStringExtra("payload")
        Log.w(TAG,"payload ==>> $payload")
        eventModel = Gson().fromJson(payload, EventModel::class.java)
        durationInSec = intent.getIntExtra("duration",5)
        Log.w(TAG,"durationInSec ==>> $durationInSec")

        Log.w("MapActivity","==>> ${payload}")
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setActionBarHeight()

        // Set up the user interaction to manually show or hide the system UI.
//        fullscreenContent.text = "${eventModel.ride.rider.fullName} request for ride to "+eventModel.ride.endAddress

    }

    private fun setActionBarHeight(){
        val typedValue = TypedValue()
        if (theme.resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.w(TAG,"onPostCreate durationInSec ==>> $durationInSec")
        /// FIXME Uncomment this line
//        delayedHide(durationInSec)
    }
    /**
     * Schedules a call to hide() in [delaySec], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delaySec: Int) {
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, (delaySec * 1000).toLong())
    }

    override fun onResume() {
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)
        super.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        hideHandler.removeCallbacks(hideRunnable)
        binding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mapManager.setGoogleMap(googleMap)
        mapManager.showRide(null, LatLng(eventModel.ride.startLocationLat,eventModel.ride.startLocationLong),LatLng(eventModel.ride.endLocationLat,eventModel.ride.endLocationLong))
        googleMap.setOnMarkerClickListener { true }

        googleMap.setContentDescription("Description")
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.uiSettings.isMapToolbarEnabled = false
        googleMap.uiSettings.isTiltGesturesEnabled = false

    }

    override fun getMapWidth(): Int {
        return binding.mapView.width
    }

    override fun getMapHeight(): Int {
        return binding.mapView.height
    }

    override fun getMapTopPadding(): Int {
        var topPadding: Int = actionBarHeight
        topPadding += binding.topPanel.height
        return topPadding
    }

    override fun getMapBottomPadding(): Int {
        return bottomPadding
    }


}