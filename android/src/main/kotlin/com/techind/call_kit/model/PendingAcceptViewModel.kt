package com.techind.call_kit.model

import android.graphics.drawable.Drawable
import com.techind.call_kit.MyApplication
import com.techind.call_kit.R
import com.techind.call_kit.utils.LocalizeUtils
import com.techind.call_kit.utils.UIUtils


class PendingAcceptViewModel {
    var avatar : Drawable? = null
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
    var isSurge : Boolean=false

    fun setRide(ride: Ride?) {
        if (ride == null) {
            // should never happen
            clear()
            return
        }
        isFemale=ride.isFemaleDriverRequest()
        isSurge = ride.surgeFactor > 1
        eta = LocalizeUtils.formatDriverEta(MyApplication.getInstance(), ride.estimatedTimeArrive.toLong())

        rating= UIUtils.formatRating(ride.rider.rating)
        name = ride.rider.firstname
        var categoryStr = ride.requestedCarType.title
        var fontSize = 20
        if (ride.isDirectConnectRequest()) {
            categoryIcon = R.drawable.nav_direct_connect
            if (isSurge) {
                categoryStr = MyApplication.getInstance().getString(R.string.direct_connect_request_car_type_w_surge, categoryStr)
                fontSize = 14
            } else {
                categoryStr = MyApplication.getInstance()
                    .getString(R.string.direct_connect_request_car_type, categoryStr)
                fontSize = 16
            }
        } else {
            categoryIcon = 0
        }
        categoryName = categoryStr
        surgeFactor = UIUtils.formatSurgeFactor(ride.surgeFactor)
        categoryFontSize = fontSize
        val url = ride.rider.user.photoUrl
//        untilDestroy(
//            RxImageLoader.execute(
//                Request(url)
//                    .progress(R.drawable.rotating_circle)
//                    .target(avatar)
//                    .error(R.drawable.ic_user_icon)
//                    .circular(true)
//            )
//        )
        if (isFemale) {
            buttonLeft = R.drawable.rounded_pink_button_left
            buttonRight = R.drawable.rounded_pink_button_right
        } else if (isSurge) {
            buttonLeft = R.drawable.rounded_blue_button_left
            buttonRight = R.drawable.rounded_blue_button_right
        } else {
            buttonLeft= R.drawable.rounded_green_button_left
            buttonRight = R.drawable.rounded_green_button_right
        }
    }

    fun clear() {
        avatar = null
        eta = "0"
        rating = "0"
        name = "---"
        categoryIcon = 0
        categoryName = "---"
        isFemale = false
        isSurge = false
    }
}