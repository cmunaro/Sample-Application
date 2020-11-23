package com.cmunaro.surfingspot.data.retrofit.objects

import com.google.gson.annotations.SerializedName

data class ImageCharacteristics(
    @SerializedName("webformatURL") val imageUrl: String?,
    @SerializedName("webformatWidth") val width: Int = 0,
    @SerializedName("webformatHeight") val height: Int = 0,
)
