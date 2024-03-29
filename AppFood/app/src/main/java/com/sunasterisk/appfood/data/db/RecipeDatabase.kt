package com.sunasterisk.appfood.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sunasterisk.appfood.data.model.Recipe

@Database(entities = [Recipe::class], version = 2, exportSchema = false)
abstract class  RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao() : RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
