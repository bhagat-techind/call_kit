package com.techind.call_kit.model

import android.util.Log
import android.view.View
import com.techind.call_kit.MyApplication
import com.techind.call_kit.R
import com.techind.call_kit.utils.LocalizeUtils
import com.techind.call_kit.utils.UIUtils


class PendingAcceptViewModel {
    var startAddress : String = "---"
    var eta : String = "---"
    var rating : String = "---"
    var name : String = "---"
    var categoryName : String = "---"
    var surgeFactor : String = "---"
    var categoryIcon : Int=0
    var categoryFontSize : Int=20
    var buttonLeft : Int=0
    var buttonRight : Int=0
    var isFemale : Boolean=false
    var isSurgeVisible : Int =View.GONE

    fun setRide(ride: Ride?) {

        if (ride == null) {
            // should never happen
            clear()
            return
        }
        isFemale=ride.isFemaleDriverRequest()
        isSurgeVisible = if (ride.surgeFactor.toInt() > 1 ) View.VISIBLE else View.GONE
        eta = LocalizeUtils.formatDriverEta(MyApplication.getInstance(), ride.estimatedTimeArrive.toLong())
        startAddress = ride.startAddress
        rating= UIUtils.formatRating(ride.rider.rating)
        name = ride.rider.firstname
        var categoryStr = ride.requestedCarType.title
        var fontSize = 20
        if (ride.isDirectConnectRequest()) {
            categoryIcon = R.drawable.nav_direct_connect
            if (isSurgeVisible == View.VISIBLE) {
                categoryStr = MyApplication.getInstance().getString(R.string.direct_connect_request_car_type_w_surge, categoryStr)
                fontSize = 14
            } else {
                categoryStr = MyApplication.getInstance()
                    .getString(R.string.direct_connect_request_car_type, categoryStr)
                fontSize = 16
            }
        } else {
            categoryIcon = R.drawable.nav_direct_connect
        }
        categoryName = categoryStr
        surgeFactor = UIUtils.formatSurgeFactor(ride.surgeFactor)
        categoryFontSize = fontSize
        if (isFemale) {
            buttonLeft = R.drawable.rounded_pink_button_left
            buttonRight = R.drawable.rounded_pink_button_right
        } else if (isSurgeVisible == View.VISIBLE) {
            buttonLeft = R.drawable.rounded_blue_button_left
            buttonRight = R.drawable.rounded_blue_button_right
        } else {
            buttonLeft= R.drawable.rounded_green_button_left
            buttonRight = R.drawable.rounded_green_button_right
        }
        Log.w(javaClass.name,"PendingAcceptViewModel ==>> $ride, $isSurgeVisible")
    }

    fun clear() {
        eta = "0"
        rating = "0"
        name = "---"
        categoryIcon = 0
        categoryName = "---"
        isFemale = false
        isSurgeVisible = View.GONE
    }
}