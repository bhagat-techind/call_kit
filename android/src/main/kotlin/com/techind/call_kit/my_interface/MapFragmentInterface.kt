package com.techind.call_kit.my_interface

interface MapFragmentInterface {

    interface MapPaddingListener {
        fun onTopPaddingUpdated(value: Int)
        fun onBottomPaddingUpdated(value: Int)
    }

    interface OnButtonClickListener {
        fun onClicked(action:Int)
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