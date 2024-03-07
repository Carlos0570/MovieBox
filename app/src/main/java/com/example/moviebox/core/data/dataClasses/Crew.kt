package com.example.moviebox.core.data.dataClasses

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("department") var department: String? = null,
    @SerializedName("job") var job: String? = null
) : Cast()