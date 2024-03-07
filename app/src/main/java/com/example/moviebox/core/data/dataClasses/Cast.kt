package com.example.moviebox.core.data.dataClasses

import com.google.gson.annotations.SerializedName

open class Cast(
    @SerializedName("adult")
    var adult: Boolean? = null,
    @SerializedName("gender")
    var gender: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("known_for_department")
    var knownForDepartment: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("original_name")
    var originalName: String? = null,
    @SerializedName("popularity")
    var popularity: Double? = null,
    @SerializedName("profile_path")
    var profilePath: String? = null,
    @SerializedName("cast_id")
    var castId: Int? = null,
    @SerializedName("character")
    var character: String? = null,
    @SerializedName("credit_id")
    var creditId: String? = null,
    @SerializedName("order")
    var order: Int? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("genre_ids")
    var genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("video")
    var video: Boolean? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null,
    @SerializedName("media_type")
    var mediaType: MediaType? = null
)
enum class MediaType(value : String){
    @SerializedName( "movie")
    MOVIE("movie"),
    @SerializedName( "tv")
    TV("tv")
}

