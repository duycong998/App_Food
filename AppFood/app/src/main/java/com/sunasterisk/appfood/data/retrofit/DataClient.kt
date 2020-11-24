package com.sunasterisk.appfood.data.retrofit

import com.sunasterisk.appfood.data.model.Categories
import com.sunasterisk.appfood.data.model.MealType
import com.sunasterisk.appfood.data.model.RecipeType
import retrofit2.Call
import retrofit2.http.*

interface DataClient {
    @GET("categories.php")
    fun getDataCategory(): Call<Categories>

    @GET("random.php")
    fun getDataRandom(): Call<RecipeType>

    @GET("filter.php")
    fun getDataMeal(
        @Query("c") strMeal: String?
    ): Call<MealType>

    @GET("search.php")
    fun getDataSearch(
        @Query("s") strMeal: String?
    ): Call<RecipeType>

    @GET("lookup.php")
    fun getDataById(
        @Query("i") strId: String?
    ): Call<RecipeType>
}
