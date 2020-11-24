package com.sunasterisk.appfood.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("strMeal")
    @Expose
    val strMeal: String,
    @SerializedName("strMealThumb")
    @Expose
    val strMealThumb: String,
    @SerializedName("idMeal")
    @Expose
    val idMeal: String
)
