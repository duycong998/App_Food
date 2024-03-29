package com.sunasterisk.appfood.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeType (
    @SerializedName("meals")
    @Expose
    val recipeRandomType: List<Recipe>? = null
)
