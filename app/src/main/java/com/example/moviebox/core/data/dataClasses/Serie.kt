package com.example.moviebox.core.data.dataClasses

import com.google.gson.annotations.SerializedName

data class Serie(
    @SerializedName("origin_country")
    var originCountry: ArrayList<String> = arrayListOf(),
    @SerializedName("first_air_date")
    var firstAirDate: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("original_name")
    var originalName: String? = null
) : MediaItem()

