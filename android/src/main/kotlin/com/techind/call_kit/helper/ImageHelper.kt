package com.techind.call_kit.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat


class ImageHelper {

    companion object {

        /**
         * Method to get bitmap from VectorDrawable
         * @param drawableID
         * @return
         */
        fun createBitmap(context: Context?, drawableID: Int): Bitmap {
            val drawable = ContextCompat.getDrawable(context!!, drawableID)
            val bmp = Bitmap.createBitmap(
                drawable!!.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bmp)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bmp
        }
    }
}