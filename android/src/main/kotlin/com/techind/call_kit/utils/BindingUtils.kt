package com.techind.call_kit.utils


//@JvmStatic @BindingAdapter("backgroundDrawableRes")
//fun setBackgroundDrawableRes(view: View, @DrawableRes resource: Int) {
//    view.setBackgroundResource(resource)
//}

class BindingUtils {
//    companion object {
//        @BindingConversion
//        fun convertBooleanToVisibility(visible: Boolean): Int {
//            return if (visible) View.VISIBLE else View.GONE
//        }
//
//        @BindingAdapter("android:src")
//        fun setImageResource(imageView: ImageView, resource: Int) {
//            imageView.setImageResource(resource)
//        }
//
//        @BindingAdapter("android:drawableLeft")
//        fun setDrawableLeft(textView: TextView, resource: Int) {
//            val left = if (resource > 0) ContextCompat.getDrawable(textView.context, resource) else null
//            textView.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null)
//        }
//
//        @JvmStatic @BindingAdapter("app:backgroundColorRes")
//        fun setBackgroundColorRes(view: View, @ColorRes resource: Int) {
//            view.setBackgroundColor(ContextCompat.getColor(view.context, resource))
//        }
//
//        @BindingAdapter("backgroundColor")
//        fun setBackgroundColor(view: View, @ColorInt color: Int) {
//            view.setBackgroundColor(color)
//        }
//
//        @JvmStatic @BindingAdapter("app:backgroundDrawableRes")
//        fun setBackgroundDrawableRes(view: View, @DrawableRes resource: Int) {
//            view.setBackgroundResource(resource)
//        }
//
//        @BindingAdapter("imageUrl")
//        fun loadImage(view: ImageView, imageUrl: String?) {
//            ImageHelper.loadImageIntoView(view, imageUrl)
//        }
//
//        @BindingAdapter("htmlText")
//        fun setHtmlText(view: TextView, htmlString: String?) {
//            setHtmlText(view, htmlString, false)
//        }
//
//        @BindingAdapter("htmlTextInteractive")
//        fun setHtmlTextInteractive(view: TextView, htmlString: String?) {
//            setHtmlText(view, htmlString, true)
//        }
//
//        private fun setHtmlText(view: TextView, htmlString: String?, interactive: Boolean) {
//            if (htmlString != null) {
//                val result: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
//                } else {
//                    Html.fromHtml(htmlString)
//                }
//                view.setText(result, TextView.BufferType.SPANNABLE)
//                if (interactive) {
//                    view.movementMethod = LinkMovementMethod.getInstance()
//                }
//            }
//        }
//
//        @BindingAdapter("fontSize")
//        fun setFontSize(textView: TextView, fontSize: Int) {
//            textView.textSize = fontSize.toFloat()
//        }
//    }
}