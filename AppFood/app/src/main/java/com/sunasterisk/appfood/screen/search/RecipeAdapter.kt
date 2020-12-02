package com.sunasterisk.appfood.screen.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.data.model.Recipe
import com.sunasterisk.food_01.screen.search.SearchFragment
import com.sunasterisk.food_01.utils.OnItemRecyclerViewClickListenner
import kotlinx.android.synthetic.main.item_layout_food.view.*

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.ViewHolder?>() {
    private val listRecipe = mutableListOf<Recipe>()
    private var onItemClickListener: OnItemRecyclerViewClickListenner<Recipe>? = null
    private val listFavorites = mutableListOf<String>()

    fun updateData(recipes: MutableList<Recipe>?) {
        recipes?.let {
            this.listRecipe.clear()
            this.listRecipe.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun registerItemRecyclerViewClickListener(
        onItemRecyclerViewClickListener: OnItemRecyclerViewClickListenner<Recipe>?
    ) {
        onItemClickListener = onItemRecyclerViewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_food, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRecipe.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewData(listRecipe[position])
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),View.OnClickListener{

        private var recipe:Recipe? = null

        fun bindViewData(recipe: Recipe) {
            this.recipe = recipe
            itemView.run {
                foodNameView.text = recipe.name
                foodID.text = recipe.tag
                Picasso.get()
                    .load(recipe.urlImage)
                    .placeholder(R.drawable.ic_loop1)
                    .into(foodImageView)
            }
            if (listFavorites.any { it == recipe.idRecipe }) {
                itemView.favoriteDrawer.setBackgroundResource(R.drawable.ic_loved)
            } else {
                itemView.favoriteDrawer.setBackgroundResource(R.drawable.ic_favorite_24)
            }

            if(SearchFragment.isDark) {
                itemView.constrainLayoutItem.setBackgroundResource(R.drawable.custom_bg_while)
                itemView.idContainerLayout.setBackgroundResource(R.drawable.custom_bg_while)
            } else {
                itemView.constrainLayoutItem.setBackgroundResource(R.drawable.custom_bg_black)
                itemView.idContainerLayout.setBackgroundResource(R.drawable.custom_bg_black)
            }
        }

        init {
            itemView.constrainLayoutItem.animation = AnimationUtils.loadAnimation(itemView.context,R.anim.ani_out)
            itemView.foodImageView.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.ani_in)
            itemView.setOnClickListener(this)
            itemView.favoriteDrawer.apply {
                setOnClickListener {
//                    Toast.makeText(context,recipe?.idRecipe,Toast.LENGTH_SHORT).show()
                    if (listFavorites.any { it == recipe?.idRecipe }) {
                        listFavorites.remove(recipe?.idRecipe ?: return@setOnClickListener)
                    } else {
                        listFavorites.add(recipe?.idRecipe ?: return@setOnClickListener)
                    }
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        override fun onClick(v: View?) {
            onItemClickListener?.onItemClickListener(listRecipe[adapterPosition])
        }
    }
}
