package com.techind.call_kit.model


import com.google.gson.annotations.SerializedName

data class Rider(
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("user")
    val user: User
)