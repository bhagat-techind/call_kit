package com.techind.call_kit.utils

import android.content.res.Resources
import android.util.TypedValue


class ViewUtils {

    companion object {
        /**
         * Converts DP to pixels
         * @see [on StackOverflow](http://stackoverflow.com/questions/4605527/converting-pixels-to-dp)
         *
         * @param dp
         * @return pixels value
         */
        fun dpToPixels(dp: Float): Float {
            val metrics = Resources.getSystem().displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
        }
    }
}