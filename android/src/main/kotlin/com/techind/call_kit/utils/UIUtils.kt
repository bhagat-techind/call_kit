package com.techind.call_kit.utils

import java.text.DecimalFormat


class UIUtils {

    companion object {
        @JvmStatic
        private val DEFAULT_MONEY_FORMAT = "#.00"


        @JvmStatic
        fun formatRating(rating: Double): String {
            return if (rating > 0) DecimalFormat("#.00").format(rating) else ""
        }

        @JvmStatic
        fun formatSurgeFactor(surgeFactor: Double): String {
            return if (surgeFactor > 1.0f) DecimalFormat("#.00").format(surgeFactor) + "X" else ""
        }

        @JvmStatic
        fun convertMoneyToString(money: Double): String? {
            val df = DecimalFormat(DEFAULT_MONEY_FORMAT)
            return df.format(money)
        }
    }
}