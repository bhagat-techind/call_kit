package com.techind.call_kit.model


import com.google.gson.annotations.SerializedName

data class End(
    @SerializedName("address")
    val address: String
)