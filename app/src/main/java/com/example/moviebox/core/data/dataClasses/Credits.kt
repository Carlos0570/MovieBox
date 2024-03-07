package com.example.moviebox.core.data.dataClasses

import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("cast")
    var cast: ArrayList<Cast> = arrayListOf(),
    @SerializedName("crew")
    var crew: ArrayList<Crew> = arrayListOf()
)