package com.sunasterisk.appfood.screen.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.data.model.Recipe
import com.sunasterisk.appfood.screen.main.tabLayout.MainPageFragment
import com.sunasterisk.appfood.screen.splash.SplashFragment
import com.sunasterisk.food_01.utils.addFragment

class MainActivity : AppCompatActivity() {
    private val listRecipe = mutableListOf<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(SplashFragment.newInstance(), R.id.container)
        //addFragment(MainPageFragment.newInstance(), R.id.container)
        val a = getSharedPreferences("recipe", Context.MODE_PRIVATE).getString("recipe", "")
        if(a== null || a == "") return
        listRecipe.add(Gson().fromJson(a ?: "", Recipe::class.java))
        Picasso.with(this)
            .setIndicatorsEnabled(true)
    }

    fun addFavorite(recipe: Recipe) {
        val recipeString = Gson().toJson(recipe).toString()
        getSharedPreferences("recipe", Context.MODE_PRIVATE).edit().putString(
            "recipe",
            recipeString
        ).apply()
        listRecipe.add(recipe)
    }

    fun getRecipes() = listRecipe
}
