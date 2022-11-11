package com.techind.call_kit

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.techind.call_kit.databinding.ActivityMapBinding
import com.techind.call_kit.fragment.PendingAcceptFragment
import com.techind.call_kit.map.MapManager
import com.techind.call_kit.model.EventModel
import com.techind.call_kit.my_interface.MapFragmentInterface
import io.flutter.plugin.common.MethodChannel.Result as FResult


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MapActivity : AppCompatActivity(), OnMapReadyCallback,
    MapFragmentInterface.MapDimensionsProvider , MapFragmentInterface.MapPaddingListener, MapFragmentInterface.OnButtonClickListener{

    private lateinit var binding: ActivityMapBinding

    private val hideHandler = Handler()
    lateinit var eventModel:EventModel
    var durationInSec : Int = 5
    private var TAG = MapActivity::class.java.name
    private var bottomPadding = 0
    private var topPadding = 0

    private var actionBarHeight = 0
    lateinit  var mapManager: MapManager

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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mapView.onCreate(savedInstanceState)

        var payload = intent.getStringExtra("payload")
        Log.w(TAG,"payload ==>> $payload")
        eventModel = Gson().fromJson(payload, EventModel::class.java)
        durationInSec = intent.getIntExtra("duration",5)
        Log.w(TAG,"durationInSec ==>> $durationInSec")

        Log.w("MapActivity","==>> ${payload}")


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = "New Ride Request"
        binding.toolbar.inflateMenu(R.menu.button_menu)
        setActionBarHeight()

        // Set up the user interaction to manually show or hide the system UI.
//        fullscreenContent.text = "${eventModel.ride.rider.fullName} request for ride to "+eventModel.ride.endAddress

    }

    private fun showPendingAcceptFragment(create: Boolean = true) {
        var fragment: PendingAcceptFragment? = null
        val f: Fragment? = supportFragmentManager.findFragmentById(R.id.bottomPanel)
        if (f is PendingAcceptFragment) {
            fragment = f
        } else if (create) {
            fragment = PendingAcceptFragment()
        }
        fragment?.setMapPaddingListener(this)
        fragment?.setButtonClickedListener(this)
        fragment?.setRide(eventModel.ride)
        fragment?.setDuration(durationInSec)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.bottomPanel, fragment!!)
        transaction.commit()
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
        delayedHide(durationInSec)
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
        /** [binding.mapView.post] will execute after build UI otherwise [binding.mapView.width] & [binding.mapView.height] will return 0 */
        binding.mapView.post {
            mapManager = MapManager(this, this)
            mapManager.setGoogleMap(googleMap)
            mapManager.showRide(
                null,
                LatLng(eventModel.ride.startLocationLat, eventModel.ride.startLocationLong),
                LatLng(eventModel.ride.endLocationLat, eventModel.ride.endLocationLong)
            )
            showPendingAcceptFragment(true)
        }

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

    override fun onTopPaddingUpdated(value: Int) {
        if (topPadding == value) {
            return;
        }
        topPadding = value;
        mapManager.updatePaddings()
    }

    override fun onBottomPaddingUpdated(value: Int) {
        if (bottomPadding == value) {
            return;
        }
        bottomPadding = value
//        var lp : RelativeLayout.LayoutParams = binding.
//         lp =(RelativeLayout.LayoutParams) binding . fabContainer . getLayoutParams ()
//        if (lp == null) {
//            lp =
//                new RelativeLayout . LayoutParams ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        }
//        lp.bottomMargin = Math.max(value, int ViewUtils . dpToPixels 15)
//        binding.fabContainer.setLayoutParams(lp)
        mapManager.updatePaddings()
    }

    override fun onClicked(action: Int) {
        Log.w(TAG,"onClicked ==>> $action")
        result.success(action)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.button_menu,menu)
        val menuItem = menu.findItem(R.id.menuAction)
        val button = menuItem.actionView!!.findViewById<View>(R.id.button) as Button
        button.text = "Decline"
        button.setOnClickListener {  onOptionsItemSelected(menuItem) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuAction -> {
                onClicked(2)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}