package com.techind.call_kit.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RequestedCarType(
    @SerializedName("carCategory")
    val carCategory: String,
    @SerializedName("configuration")
    val configuration: String,
    @SerializedName("plainIconUrl")
    val plainIconUrl: String,
    @SerializedName("title")
    val title: String
) : Serializable