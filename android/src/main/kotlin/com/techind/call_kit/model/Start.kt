package com.techind.call_kit.model


import com.google.gson.annotations.SerializedName

data class Start(
    @SerializedName("address")
    val address: String
)