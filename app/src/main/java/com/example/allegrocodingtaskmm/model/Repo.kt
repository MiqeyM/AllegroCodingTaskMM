package com.example.allegrocodingtaskmm.model

import com.squareup.moshi.Json

data class Repo(
    val id: Long,
    val name: String,
    @Json(name = "full_name") val fullName: String,
    val description: String?,
    @Json(name = "stargazers_count") val stars: Int,
    val language: String?
)
