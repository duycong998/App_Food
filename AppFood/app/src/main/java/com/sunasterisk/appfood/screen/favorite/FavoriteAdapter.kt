package com.sunasterisk.appfood.screen.favorite

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sunasterisk.appfood.R.*
import com.sunasterisk.appfood.data.model.Recipe
import com.sunasterisk.food_01.utils.OnItemRecyclerViewClickListenner
import com.sunasterisk.food_01.utils.SendDataFragment
import kotlinx.android.synthetic.main.item_layout_food.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder?>() {
    private val listRecipe = mutableListOf<Recipe>()
    private var onItemClickListener: OnItemRecyclerViewClickListenner<Recipe>? = null
    private var onlongLickItem: SendDataFragment<Recipe>? = null

    fun updateData(recipes: MutableList<Recipe>?) {
        recipes?.let {
            this.listRecipe.clear()
            this.listRecipe.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun updateDataFavorite(recipes: MutableList<Recipe>?) {
        recipes?.let {
            this.listRecipe.addAll(it.filterNot { a ->
                this.listRecipe.any { b -> b.idRecipe == a.idRecipe }
            })
            notifyDataSetChanged()
        }
    }

    fun deleteRecipe(recipes: Recipe) {
        recipes.let {
            this.listRecipe.remove(recipes)
            notifyDataSetChanged()
        }
    }

    fun registerItemLongLickRecyclerViewClickListener(
        onItemLongClickListener: SendDataFragment<Recipe>
    ) {
        onlongLickItem = onItemLongClickListener
    }

    fun registerItemRecyclerViewClickListener(
        onItemRecyclerViewClickListener: OnItemRecyclerViewClickListenner<Recipe>?
    ) {
        onItemClickListener = onItemRecyclerViewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout.item_layout_food, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRecipe.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewData(listRecipe[position])
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),View.OnClickListener, View.OnLongClickListener{

        fun bindViewData(recipe: Recipe) {
            itemView.run {
                favoriteDrawer.setBackgroundResource(drawable.ic_loved)
                foodNameView.text = recipe.name
                foodID.text = recipe.tag
                if(recipe.urlImage == ""){
                    val img = recipe.bitmap
                    val bitmap = BitmapFactory.decodeByteArray(img, 0, img!!.size)
                    foodImageView.setImageBitmap(bitmap)
                } else {
                    Picasso.get().load(recipe.urlImage).into(foodImageView)
                }
            }
        }

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener?.onItemClickListener(listRecipe[adapterPosition])
        }

        override fun onLongClick(p0: View?): Boolean {
           onlongLickItem?.onItemClick(listRecipe[adapterPosition])
            return true
        }
    }
}
