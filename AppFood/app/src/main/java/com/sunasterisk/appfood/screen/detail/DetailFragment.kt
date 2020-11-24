package com.sunasterisk.appfood.screen.detail

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.squareup.picasso.Picasso
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.data.model.Recipe
import com.sunasterisk.appfood.data.model.RecipeType
import com.sunasterisk.appfood.data.retrofit.APIUtil
import com.sunasterisk.appfood.screen.main.PlayYoutubeActivity
import kotlinx.android.synthetic.main.fragment_detail_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {
    private var recipe: Recipe? = null
    private var listRecipe = mutableListOf<Recipe>()
    private var recipeRandomType = RecipeType()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        recipe = arguments?.getParcelable("aaa")
        textNameDetail.text = recipe?.name
        textAreaDetail.text = recipe?.area
        textInstructionDetail.text = recipe?.instructions
        textIngredientDetail.text = recipe?.ingre1
        textIngredientDetail2.text = recipe?.ingre2
        textIngredientDetail3.text = recipe?.ingre3
        textIngredientDetail4.text = recipe?.ingre4
        textIngredientDetail5.text = recipe?.ingre5
        textMeasure1.text = recipe?.measure1
        textMeasure2.text = recipe?.measure2
        textMeasure3.text = recipe?.measure3
        textMeasure4.text = recipe?.measure4
        textMeasure5.text = recipe?.measure5
        if (recipe?.urlImage == "") {
            val img = recipe?.bitmap
            val bitmap = BitmapFactory.decodeByteArray(img, 0, img!!.size)
            videoPlayYoutube.setImageBitmap(bitmap)
        } else {
            Picasso.with(context).load(recipe?.urlImage).into(videoPlayYoutube)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun init() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbarDetail)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarDetail.setNavigationOnClickListener {
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            val detailFragment = requireActivity().supportFragmentManager.findFragmentByTag("fragA")
            transaction.remove(detailFragment!!)
            transaction.commit()
        }

        ImageButtonPlay.setOnClickListener {
            val intent = Intent(context, PlayYoutubeActivity::class.java)
            intent.putExtra("you", recipe?.urlVideo)
            Log.d("aaa", "" + recipe?.urlVideo)
            startActivity(intent)
        }
    }

    private fun getData(id: String) {
        val dataClient = APIUtil.getData()
        val callBack = dataClient?.getDataById(id)
        callBack!!.enqueue(object : Callback<RecipeType> {
            override fun onResponse(p0: Call<RecipeType>?, p1: Response<RecipeType>?) {
                if (p1?.body() != null) {
                    recipeRandomType = p1.body()
                    listRecipe.clear()
                    listRecipe.addAll(recipeRandomType.recipeRandomType!!)
                    textNameDetail.text = listRecipe.first().name
                    textAreaDetail.text = listRecipe.first().area
                    textInstructionDetail.text = listRecipe.first().instructions
                    textIngredientDetail.text = listRecipe.first().ingre1
                    textIngredientDetail2.text = listRecipe.first().ingre2
                    textIngredientDetail3.text = listRecipe.first().ingre3
                    textIngredientDetail4.text = listRecipe.first().ingre4
                    textIngredientDetail5.text = listRecipe.first().ingre5
                    textMeasure1.text = listRecipe.first().ingre1
                    textMeasure2.text = listRecipe.first().ingre2
                    textMeasure3.text = listRecipe.first().ingre3
                    textMeasure4.text = listRecipe.first().ingre4
                    textMeasure5.text = listRecipe.first().ingre5
                    Picasso.with(context).load(listRecipe.first().urlImage).into(videoPlayYoutube)
                    ImageButtonPlay.setOnClickListener {
                        val intent = Intent(context, PlayYoutubeActivity::class.java)
                        intent.putExtra("you", listRecipe.first().urlVideo)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(p0: Call<RecipeType>?, p1: Throwable?) {
                Log.d("aa", "aa" + p1.toString())
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.menuSearch)
        val item2 = menu.findItem(R.id.menuAdd)
        if (item != null) {
            item.isVisible = false
        } else if (item2 != null) {
            item2.isVisible = false
        }
        super.onPrepareOptionsMenu(menu)
    }

    companion object {

        fun newInstance(item: Recipe) = DetailFragment().apply {
            arguments = bundleOf("aaa" to item)
        }

        fun newInstance(id: String) = DetailFragment().apply {
            getData(id)
        }
    }
}
