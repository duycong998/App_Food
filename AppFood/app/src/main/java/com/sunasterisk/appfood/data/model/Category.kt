package com.sunasterisk.appfood.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("strCategory")
    @Expose
    var name: String,
    @SerializedName("strCategoryThumb")
    @Expose
    var image: String,
    @SerializedName("strCategoryDescription")
    @Expose
    var description: String
)
