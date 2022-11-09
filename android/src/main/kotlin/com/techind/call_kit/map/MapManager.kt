package com.techind.call_kit.map

import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.techind.call_kit.R
import com.techind.call_kit.helper.ImageHelper


class MapManager(context: Context, provider: MapDimensionsProvider) {

    private var googleMap: GoogleMap? = null
    private var context: Context? = null
    private var provider: MapDimensionsProvider? = null
    private var routeStrokeWidth = 0
    private var routeStrokeColor = 0
    private var paddingForMarker = 0
    var DEFAULT_CAMERA_ZOOM : Float = 18.0f

    private var direction: Polyline? = null
    private var pickupMarker: Marker? = null
    private var destinationMarker: Marker? = null
    private var nextRideMarker: Marker? = null

    private var TAG = javaClass.name

    init {
        this.context = context
        this.provider = provider
        paddingForMarker = context.resources.getDimensionPixelSize(R.dimen.map_padding_for_marker)
        routeStrokeWidth = context.resources.getDimensionPixelSize(R.dimen.map_route_stroke_width)
        routeStrokeColor = ContextCompat.getColor(context, R.color.map_route_stroke_color)
    }

    fun setGoogleMap(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        updatePaddings()
    }

    private fun updatePaddings() {
        googleMap?.setPadding(0, provider!!.getMapTopPadding(), 0, provider!!.getMapBottomPadding())
    }

    /**
     * [moveCameraToLocation] will move map camera to given location [location]
     */
    private fun moveCameraToLocation(location: LatLng) {
        if (googleMap != null) {
            val width = provider!!.getMapWidth()
            val height = provider!!.getMapHeight()
            if (width > 0 && height > 0) {
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, DEFAULT_CAMERA_ZOOM)
                googleMap!!.animateCamera(cameraUpdate)
            }
        }
    }

    fun showRide(direction: List<LatLng>?, @NonNull pickup: LatLng, @Nullable destination: LatLng?) {
        hideRide()
        addOrUpdatePickupMarker(pickup)
        zoomToFit(pickup,destination)
        destination?.let { addOrUpdateDestinationMarker(it) }
        if(!direction.isNullOrEmpty())
            this.direction = drawDirectionOnMap(direction)
    }

    private fun hideRide() {
        removePickupMarker()
        removeDestinationMarker()
        if (direction != null) {
            direction!!.remove()
        }
    }

    private fun removePickupMarker() {
        if (pickupMarker != null) {
            pickupMarker!!.remove()
            pickupMarker = null
        }
    }

    private fun removeDestinationMarker() {
        if (destinationMarker != null) {
            destinationMarker!!.remove()
            destinationMarker = null
        }
    }

    private fun drawDirectionOnMap(direction: List<LatLng>): Polyline {
        val way = PolylineOptions().width(routeStrokeWidth.toFloat()).color(routeStrokeColor).geodesic(true)
        for (i in direction.indices) {
            val point = direction[i]
            way.add(point)
        }
        return googleMap!!.addPolyline(way)
    }


    private fun addOrUpdateDestinationMarker(location: LatLng) {
        destinationMarker = addOrUpdateMarker(destinationMarker, location, R.drawable.icon_red, R.string.destination_marker)
    }

    fun addOrUpdateNextRideMarker(location: LatLng) {
        nextRideMarker = addOrUpdateMarker(nextRideMarker, location, R.drawable.blue_pin, R.string.next_ride_marker)
    }

    private fun addOrUpdatePickupMarker(location: LatLng) {
        pickupMarker = addOrUpdateMarker(pickupMarker, location, R.drawable.icon_green, R.string.pickup_marker)
    }

    private fun addOrUpdateMarker( oldMarker: Marker?, location: LatLng, @DrawableRes resId: Int, @StringRes description: Int): Marker? {
        if (oldMarker != null) {
            // check position not changed
            if (oldMarker.position == location) {
                // return the same marker
                return oldMarker
            } else {
                // otherwise its better to recreate marker
                // since it could disappear during map animation
                oldMarker.remove()
            }
        }
        // create new marker
        return googleMap!!.addMarker(
            MarkerOptions()
                .title(context!!.getString(description))
                .position(location)
                .icon(BitmapDescriptorFactory.fromBitmap(ImageHelper.createBitmap(context, resId))))
    }

    private fun zoomToFit(latLng1: LatLng?, latLng2: LatLng?) {
        zoomOutCamera(latLng1, latLng2)
    }

    private fun zoomOutCamera(pickup: LatLng?, destination: LatLng?) {
        Log.w(TAG,"zoomOutCamera ==>> pickup : $pickup, destination : $destination")
        // if both points are null, nothing to do here
        if (pickup == null && destination == null) {
            return
        }

        val width = provider!!.getMapWidth()
        val height = provider!!.getMapHeight()
        Log.w(TAG,"zoomOutCamera ==>> width : $width, height : $height")

        if (width > 0 && height > 0) {
            val builder = LatLngBounds.Builder()
            if (pickup != null) {
                builder.include(pickup)
            }
            if (destination != null) {
                builder.include(destination)
            }
            val bounds = builder.build()

            // Add only left/right paddingForMarker
            // Bottom location marker requires no padding (car may trim but it's rare)
            // As for top space, seems google reserve some space internally for this purpose
            googleMap!!.setPadding(
                paddingForMarker,
                provider!!.getMapTopPadding(),
                paddingForMarker,
                paddingForMarker + provider!!.getMapBottomPadding()
            )
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0)
            googleMap!!.animateCamera(cameraUpdate)
            updatePaddings()
        }
    }


    /**
     * Provides map dimensions and paddings.
     * Used to calculate visible area for markers in order to prevent marker fall behind other ui items.
     */
    interface MapDimensionsProvider {
        fun getMapWidth(): Int
        fun getMapHeight(): Int
        fun getMapTopPadding(): Int
        fun getMapBottomPadding(): Int
    }
}