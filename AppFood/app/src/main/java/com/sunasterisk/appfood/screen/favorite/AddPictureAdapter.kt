package com.sunasterisk.appfood.screen.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.data.model.AddPicture

class AddPictureAdapter(private val context: Context,private val arrayAdd: MutableList<AddPicture>) : BaseAdapter() {

    class ViewHolder(row: View) {
            var imgPicture: ImageView = row.findViewById(R.id.imgFood)
            var txtname: TextView = row.findViewById(R.id.description)
    }

    override fun getCount()= arrayAdd.size

    override fun getItem(position: Int): Any {
        return arrayAdd[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if(convertView == null) {
            val layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_add_picture,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }
        val food: AddPicture = getItem(position) as AddPicture
        viewHolder.imgPicture.setImageResource(food.picture)
        viewHolder.txtname.text = food.name
        return view as View
    }
}
