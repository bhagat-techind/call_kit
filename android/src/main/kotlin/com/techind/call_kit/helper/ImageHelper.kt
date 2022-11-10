package com.techind.call_kit.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ViewTarget
import com.techind.call_kit.R


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

        fun loadImageIntoView(imageView: ImageView, imageUrl: String?): ViewTarget<ImageView, Bitmap> {
            return Glide.with(imageView.context)
                .asBitmap()
                .load(imageUrl)
                .error(R.drawable.ic_user_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        }
    }
}