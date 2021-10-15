package com.bakribrahim.data.model

import com.google.gson.annotations.SerializedName



data class FileModel(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("name")
    val name: String,
)
