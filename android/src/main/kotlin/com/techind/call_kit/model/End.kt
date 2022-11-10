package com.techind.call_kit.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class End(
    @SerializedName("address")
    val address: String
) : Serializable