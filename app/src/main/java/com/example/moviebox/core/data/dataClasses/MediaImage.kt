package com.example.moviebox.core.data.dataClasses

import com.google.gson.annotations.SerializedName


data class MediaImage(
    @SerializedName("backdrops")
    var backdrops: ArrayList<Image> = arrayListOf(),
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("logos")
    var logos: ArrayList<Image> = arrayListOf(),
    @SerializedName("posters")
    var posters: ArrayList<Image> = arrayListOf()
)

data class Image(
    @SerializedName("aspect_ratio")
    var aspectRatio: Double? = null,
    @SerializedName("height")
    var height: Int? = null,
    @SerializedName("iso_639_1")
    var iso6391: String? = null,
    @SerializedName("file_path")
    var filePath: String? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null,
    @SerializedName("width")
    var width: Int? = null

)