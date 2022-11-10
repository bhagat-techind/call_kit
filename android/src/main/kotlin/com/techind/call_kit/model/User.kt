package com.techind.call_kit.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("photoUrl")
    val photoUrl: String
): Serializable