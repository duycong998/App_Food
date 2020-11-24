package com.sunasterisk.appfood.screen.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.data.model.Category
import com.sunasterisk.food_01.utils.OnItemRecyclerViewClickListenner
import kotlinx.android.synthetic.main.row_category_layout.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val category = mutableListOf<Category>()
    private var onItemClickListener: OnItemRecyclerViewClickListenner<Category>? = null

    fun registerItemRecyclerViewClickListener(
        onItemRecyclerViewClickListener: OnItemRecyclerViewClickListenner<Category>?
    ) {
        onItemClickListener = onItemRecyclerViewClickListener
    }

    fun updateData(category: MutableList<Category>?) {
        category?.let {
            this.category.clear()
            this.category.addAll(category)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CategoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_category_layout, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindData(category[position])
    }

    override fun getItemCount() = category.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        fun bindData(category: Category) {
            itemView.run {
                textNameCategory.text = category.name
                Picasso.with(context)
                    .load(category.image)
                    .placeholder(R.drawable.ic_loop)
                    .into(imageCategory)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemClickListener?.onItemClickListener(category[adapterPosition])
        }
    }
}