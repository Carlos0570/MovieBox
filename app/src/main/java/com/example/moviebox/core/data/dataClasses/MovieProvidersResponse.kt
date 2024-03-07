package com.example.moviebox.core.data.dataClasses

import com.google.gson.annotations.SerializedName

data class MovieProvidersResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("results") var providersByCountry: ProvidersByCountry? = null
)

data class ProvidersByCountry(
    @SerializedName("CA") var CA: CA? = null,
    @SerializedName("US") var US: US? = null,
    @SerializedName("MX") var MX: MX? = null
)

data class Buy(
    @SerializedName("logo_path") var logoPath: String? = null,
    @SerializedName("provider_id") var providerId: Int? = null,
    @SerializedName("provider_name") var providerName: String? = null,
    @SerializedName("display_priority") var displayPriority: Int? = null
)

data class Rent(
    @SerializedName("logo_path") var logoPath: String? = null,
    @SerializedName("provider_id") var providerId: Int? = null,
    @SerializedName("provider_name") var providerName: String? = null,
    @SerializedName("display_priority") var displayPriority: Int? = null
)

data class Flatrate(
    @SerializedName("logo_path") var logoPath: String? = null,
    @SerializedName("provider_id") var providerId: Int? = null,
    @SerializedName("provider_name") var providerName: String? = null,
    @SerializedName("display_priority") var displayPriority: Int? = null
)


data class CA(
    @SerializedName("link") var link: String? = null,
    @SerializedName("buy") var buy: ArrayList<Buy> = arrayListOf(),
    @SerializedName("rent") var rent: ArrayList<Rent> = arrayListOf(),
    @SerializedName("flatrate") var flatrate: ArrayList<Flatrate> = arrayListOf()
)

data class US(
    @SerializedName("link") var link: String? = null,
    @SerializedName("buy") var buy: ArrayList<Buy> = arrayListOf(),
    @SerializedName("rent") var rent: ArrayList<Rent> = arrayListOf(),
    @SerializedName("flatrate") var flatrate: ArrayList<Flatrate> = arrayListOf()
)

data class MX(
    @SerializedName("link") var link: String? = null,
    @SerializedName("buy") var buy: ArrayList<Buy> = arrayListOf(),
    @SerializedName("rent") var rent: ArrayList<Rent> = arrayListOf(),
    @SerializedName("flatrate") var flatrate: ArrayList<Flatrate> = arrayListOf()
)