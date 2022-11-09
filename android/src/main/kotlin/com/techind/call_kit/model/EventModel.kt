package com.techind.call_kit.model


import com.google.gson.annotations.SerializedName

data class EventModel(
    @SerializedName("eventType")
    val eventType: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: Any,
    @SerializedName("parameters")
    val parameters: Any?,
    @SerializedName("ride")
    val ride: Ride,
    @SerializedName("title")
    val title: Any?
)