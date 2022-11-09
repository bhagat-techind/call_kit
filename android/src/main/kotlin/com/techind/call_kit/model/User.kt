package com.techind.call_kit.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("photoUrl")
    val photoUrl: String
)