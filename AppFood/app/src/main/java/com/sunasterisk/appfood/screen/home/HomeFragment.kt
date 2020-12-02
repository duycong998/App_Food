package com.sunasterisk.appfood.screen.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.TransitionInflater
import com.squareup.picasso.Picasso
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.data.db.RecipeDao
import com.sunasterisk.appfood.data.db.RecipeDatabase
import com.sunasterisk.appfood.data.model.*
import com.sunasterisk.appfood.data.retrofit.APIUtil
import com.sunasterisk.appfood.screen.detail.DetailFragment
import com.sunasterisk.appfood.screen.home.adapter.CategoryAdapter
import com.sunasterisk.appfood.screen.home.adapter.MealAdapter
import com.sunasterisk.food_01.utils.OnItemRecyclerViewClickListenner
import com.sunasterisk.food_01.utils.SendDataFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors


class HomeFragment : Fragment(), OnItemRecyclerViewClickListenner<Category>,
    SendDataFragment<Recipe> {
    private val onItemMealClickByID = object : OnItemRecyclerViewClickListenner<Meal> {
        override fun onItemClickListener(item: Meal?) {
            checkFavorite = false
            val transaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, DetailFragment.newInstance(item!!.idMeal), "fragA")
            transaction.commit()
        }
    }
    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter() }
    private var listCatory = mutableListOf<Category>()
    private var categories = Categories()
    private var listRecipe = mutableListOf<Recipe>()
    private var recipeRandomType = RecipeType()
    private val mealAdapter: MealAdapter by lazy { MealAdapter() }
    private var listMeal = mutableListOf<Meal>()
    private var mealType = MealType()
    private lateinit var recipeDao: RecipeDao
    private lateinit var recipeDatabase: RecipeDatabase
    var topAnime: Animation? = null
    var botAnime: Animation? = null
    var _checkFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        topAnime = AnimationUtils.loadAnimation(requireContext(), R.anim.top_anim)
        botAnime = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_anim)

        imageMealThumbRecipe.animation = topAnime
        textMealRecipe.animation = botAnime
    }

    override fun onStart() {
        super.onStart()
        getDataCatogy()
        getDataRandom()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_image)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_image)
    }

    private fun getDataMeal(strMeal: String?) {
        val dataClient = APIUtil.getData()
        val callBack = dataClient?.getDataMeal(strMeal)
        callBack!!.enqueue(object : Callback<MealType> {
            override fun onResponse(p0: Call<MealType>?, response: Response<MealType>?) {
                if (response?.body() != null) {
                    mealType = response.body()!!
                    listMeal.clear()
                    listMeal.addAll(mealType.listMealType!!)
                    mealAdapter.updateDataRecipe(listMeal)
                    recyclerviewRecipe.adapter = mealAdapter
                }
            }

            override fun onFailure(p0: Call<MealType>?, p1: Throwable?) {
                Log.d(TAG, p1.toString())
            }
        })
    }

    private fun getDataRandom() {
        val dataClient = APIUtil.getData()
        val callBack = dataClient?.getDataRandom()
        callBack!!.enqueue(object : Callback<RecipeType> {
            override fun onResponse(p0: Call<RecipeType>?, p1: Response<RecipeType>?) {
                recipeRandomType = p1?.body()!!
                listRecipe.addAll(recipeRandomType.recipeRandomType!!)
                textMealRecipe.text = listRecipe.first().name
                textTagRecipe.text = listRecipe.first().tag
                Picasso.get().load(listRecipe.first().urlImage).resize(120, 120)
                    .into(imageMealThumbRecipe)
                cardviewHome.setOnClickListener {
                    onItemClick(listRecipe.first())
                }

                favoriteDrawer.setOnClickListener {
                    Executors.newSingleThreadExecutor().execute {
                        val list = recipeDao.getDataAll()
                        if (list.any {
                                it.idRecipe == listRecipe.first().idRecipe
                            }) {
                            favoriteDrawer.post {
                                favoriteDrawer.setBackgroundResource(R.drawable.ic_favorite_24)
                                _checkFavorite = false
                                Toast.makeText(context, "Bạn Đã Bỏ Thích", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            recipeDao.deleteUsers(listRecipe.first())
                        } else {
                            favoriteDrawer.post {
                                favoriteDrawer.setBackgroundResource(R.drawable.ic_loved)
                                _checkFavorite = true
                                Toast.makeText(context, "Bạn Đã Thích", Toast.LENGTH_SHORT).show()
                            }
                            val a = listRecipe.first()
                            if (a.tag.isNullOrEmpty()) {
                                a.tag = ""
                            }
                            recipeDao.insert(a)
                        }
                    }
                }
            }

            override fun onFailure(p0: Call<RecipeType>?, p1: Throwable?) {
                Log.d(TAG, p1.toString())
            }
        })
    }

    private fun getDataCatogy() {
        val dataClient = APIUtil.getData()
        val callBack = dataClient?.getDataCategory()
        callBack!!.enqueue(object : Callback<Categories> {
            override fun onResponse(p0: Call<Categories>?, response: Response<Categories>?) {
                categories = response!!.body()!!
                listCatory.addAll(categories.categories!!)
                categoryAdapter.updateData(listCatory)
            }

            override fun onFailure(p0: Call<Categories>?, p1: Throwable?) {
                Log.d(TAG, p1.toString())
            }
        })
    }

    override fun onItemClickListener(item: Category?) {
        getDataMeal(item?.name ?: "")
    }

    private fun initView() {
        getDataMeal("Chicken")
        recyclerCategories.adapter = categoryAdapter
        categoryAdapter.registerItemRecyclerViewClickListener(this)

        recyclerviewRecipe.adapter = mealAdapter
        mealAdapter.registerItemRecyclerViewClickListener(onItemMealClickByID)

        recipeDatabase = RecipeDatabase.getDatabase(requireContext())
        recipeDao = recipeDatabase.recipeDao()
    }

    override fun onItemClick(item: Recipe?) {
        checkFavorite = _checkFavorite
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, DetailFragment.newInstance(item!!), "fragA")
//        transaction.replace(R.id.container, DetailFragment.newInstance(item!!), "fragA")
//            .addSharedElement(imageMealThumbRecipe, imageMealThumbRecipe.transitionName)
//            .addSharedElement(textMealRecipe, textMealRecipe.transitionName)
        transaction.commit()
    }

    companion object {
        fun newInstance() = HomeFragment()
        const val TAG = "AAA"
        var checkFavorite = false
    }
}
