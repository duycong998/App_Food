package com.sunasterisk.appfood.screen.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.data.db.RecipeDao
import com.sunasterisk.appfood.data.db.RecipeDatabase
import com.sunasterisk.appfood.data.model.Meal
import com.sunasterisk.food_01.utils.OnItemRecyclerViewClickListenner
import kotlinx.android.synthetic.main.item_layout_food.view.*
import java.util.concurrent.Executors

class MealAdapter : RecyclerView.Adapter<MealAdapter.RecipeViewHolder>() {
    private val meal = mutableListOf<Meal>()
    private var onItemClickListener: OnItemRecyclerViewClickListenner<Meal>? = null
    private val listFavorites = mutableListOf<String>()
    private lateinit var recipeDao: RecipeDao
    private lateinit var recipeDatabase: RecipeDatabase

    fun updateDataRecipe(meal: MutableList<Meal>?) {
        meal?.let {
            this.meal.clear()
            this.meal.addAll(meal)
            notifyDataSetChanged()
        }
    }

    fun registerItemRecyclerViewClickListener(
        onItemRecyclerViewClickListener: OnItemRecyclerViewClickListenner<Meal>?
    ) {
        onItemClickListener = onItemRecyclerViewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecipeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout_food, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.binData(meal[position])
    }

    override fun getItemCount() = meal.size

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var meal: Meal? = null

        fun binData(meal: Meal) {
            this.meal = meal
            itemView.constrainLayoutItem.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.ani_in)
            itemView.run {
                foodNameView.text = meal.strMeal
                foodID.text = meal.idMeal
                Picasso.get()
                    .load(meal.strMealThumb)
                    .placeholder(R.drawable.ic_loop1)
                    .into(foodImageView)
                itemView.favoriteDrawer.visibility = View.GONE
            }
            if (listFavorites.any { it == meal.idMeal }) {
                itemView.favoriteDrawer.setBackgroundResource(R.drawable.ic_loved)
//                Executors.newSingleThreadExecutor().execute {
////                    recipeDao.insert()
//                }
            } else {
                itemView.favoriteDrawer.setBackgroundResource(R.drawable.ic_favorite_24)
            }
        }

        init {
            itemView.setOnClickListener(this)
            itemView.favoriteDrawer.apply {
                setOnClickListener {
                    if (listFavorites.any { it == meal?.idMeal }) {
                        listFavorites.remove(meal?.idMeal ?: return@setOnClickListener)
                    } else {
                        listFavorites.add(meal?.idMeal ?: return@setOnClickListener)
                    }
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        override fun onClick(p0: View?) {
            onItemClickListener?.onItemClickListener(meal)
        }
    }
}
