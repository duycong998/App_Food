package com.sunasterisk.appfood.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import com.sunasterisk.appfood.R

class DiaLogShow(context : Context) {
    init {
        val screenSize = Point()
        val diaLog = Dialog(context)
        diaLog!!.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.item_add_recipe)
            window?.apply {
                setLayout(screenSize.x * 14 / 15, screenSize.y * 14 / 15)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.gravity = Gravity.CENTER
            }
        }
    }
}
