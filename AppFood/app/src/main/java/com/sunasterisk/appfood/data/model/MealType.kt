package com.sunasterisk.appfood.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MealType(
    @SerializedName("meals")
    @Expose
    val listMealType: List<Meal>? = null
)
