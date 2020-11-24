package com.sunasterisk.appfood.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "recipe_table")
@Parcelize
data class Recipe (
    @SerializedName("idMeal")
    @Expose
    @PrimaryKey
    var idRecipe: String = "",
    @SerializedName("strMeal")
    @Expose
    val name: String = "",
    @SerializedName("strCategory")
    @Expose
    val category: String = "",
    @SerializedName("strArea")
    @Expose
    val area: String = "",
    @SerializedName("strInstructions")
    @Expose
    val instructions: String = "",
    @SerializedName("strTags")
    @Expose
    val tag: String = "",
    @SerializedName("strMealThumb")
    @Expose
    val urlImage: String = "",
    @SerializedName("strYoutube")
    @Expose
    val urlVideo: String = "",
    @SerializedName("strIngredient1")
    @Expose
    val ingre1: String = "",
    @SerializedName("strIngredient2")
    @Expose
    val ingre2: String = "",
    @SerializedName("strIngredient3")
    @Expose
    val ingre3: String = "",
    @SerializedName("strIngredient4")
    @Expose
    val ingre4: String = "",
    @SerializedName("strIngredient5")
    @Expose
    val ingre5: String = "",
    @SerializedName("strIngredient6")
    @Expose
    val ingre6: String = "",
    @SerializedName("strIngredient7")
    @Expose
    val ingre7: String = "",
    @SerializedName("strIngredient8")
    @Expose
    val ingre8: String = "",
    @SerializedName("strIngredient9")
    @Expose
    val ingre9: String = "",
    @SerializedName("strIngredient10")
    @Expose
    val ingre10: String = "",
    @SerializedName("strMeasure1")
    @Expose
    val measure1: String = "",
    @SerializedName("strMeasure2")
    @Expose
    val measure2: String = "",
    @SerializedName("strMeasure3")
    @Expose
    val measure3: String = "",
    @SerializedName("strMeasure4")
    @Expose
    val measure4: String = "",
    @SerializedName("strMeasure5")
    @Expose
    val measure5: String = "",
    @SerializedName("strMeasure6")
    @Expose
    val measure6: String = "",
    @SerializedName("strMeasure7")
    @Expose
    val measure7: String = "",
    @SerializedName("strMeasure8")
    @Expose
    val measure8: String = "",
    @SerializedName("strMeasure9")
    @Expose
    val measure9: String = "",
    @SerializedName("strMeasure10")
    @Expose
    val measure10: String = "",
    val bitmap : ByteArray? = null
) : Parcelable
