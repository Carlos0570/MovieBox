package com.example.moviebox.core.data.dataClasses

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("video")
    var video: Boolean? = null,
    @SerializedName("media_type")
    var mediaType: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("budget")
    var budget: Int? = null,
    @SerializedName("genres")
    var genres: ArrayList<Genres> = arrayListOf(),
    @SerializedName("homepage")
    var homepage: String? = null,
    @SerializedName("production_companies")
    var productionCompanies: ArrayList<ProductionCompanies> = arrayListOf(),
    @SerializedName("production_countries")
     var productionCountries: ArrayList<ProductionCountries> = arrayListOf(),
    @SerializedName("revenue")
    var revenue: Int? = null,
    @SerializedName("runtime")
    var runtime: Int? = null,
    @SerializedName("spoken_languages")
    var spokenLanguages: ArrayList<SpokenLanguages> = arrayListOf(),
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tagline")
    var tagline: String? = null
) : MediaItem()

data class Genres(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null
)

data class ProductionCompanies(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("logo_path")
    var logoPath: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("origin_country")
    var originCountry: String? = null
)

data class ProductionCountries(
    @SerializedName("iso_3166_1")
    var iso31661: String? = null,
    @SerializedName("name")
    var name: String? = null
)

data class SpokenLanguages(
    @SerializedName("english_name")
    var englishName: String? = null,
    @SerializedName("iso_639_1")
    var iso6391: String? = null,
    @SerializedName("name")
    var name: String? = null
)
