package com.techind.call_kit.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ride(
    @SerializedName("driverPayment")
    val driverPayment: String,
    @SerializedName("end")
    val end: End,
    @SerializedName("endAddress")
    val endAddress: String,
    @SerializedName("endLocationLat")
    val endLocationLat: Double,
    @SerializedName("endLocationLong")
    val endLocationLong: Double,
    @SerializedName("estimatedTimeArrive")
    val estimatedTimeArrive: Int,
    @SerializedName("freeCreditCharged")
    val freeCreditCharged: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mapUrl")
    val mapUrl: String,
    @SerializedName("parameters")
    val parameters: Any,
    @SerializedName("requestedCarType")
    val requestedCarType: RequestedCarType,
    @SerializedName("requestedDispatchType")
    val requestedDispatchType: String,
    @SerializedName("requestedDriverTypes")
    val requestedDriverTypes: List<Any>,
    @SerializedName("rider")
    val rider: Rider,
    @SerializedName("start")
    val start: Start,
    @SerializedName("startAddress")
    val startAddress: String,
    @SerializedName("startLocationLat")
    val startLocationLat: Double,
    @SerializedName("startLocationLong")
    val startLocationLong: Double,
    @SerializedName("status")
    val status: String,
    @SerializedName("surgeFactor")
    val surgeFactor: Double
) : Serializable
{
    fun isFemaleDriverRequest() : Boolean {
      return  false
    }

    fun isDirectConnectRequest() : Boolean {
      return  false
    }
}