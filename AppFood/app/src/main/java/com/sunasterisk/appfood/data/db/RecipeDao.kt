package com.sunasterisk.appfood.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sunasterisk.appfood.data.model.Recipe

@Dao
interface RecipeDao {
    @Insert(entity = Recipe::class, onConflict = REPLACE)
    fun insert(recipe: Recipe)

    @Query("SELECT * FROM recipe_table")
    fun getDataAll() : List<Recipe>

    @Delete
    fun deleteRecipe(recipe: Recipe)
}
