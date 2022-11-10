package com.techind.call_kit.utils


import android.content.Context
import com.techind.call_kit.R

class LocalizeUtils {

    companion object {
        /**
         * Returns driver arrival time representation in minutes
         * @param context context to use for localization
         * @param seconds amount of seconds
         * @return time in minutes representation string
         */
        @JvmStatic
        fun formatDriverEta(context: Context, seconds: Long?): String {
            if (seconds == null) {
                return context.getString(R.string.no_mins)
            }
            val mins = (seconds / 60).toInt()
            return if (mins <= 0) {
                context.getString(R.string.zero_mins)
            } else context.resources.getQuantityString(R.plurals.minutes_plural, mins, mins)
        }
    }
}