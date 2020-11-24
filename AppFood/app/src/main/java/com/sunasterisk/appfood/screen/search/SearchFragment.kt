package com.sunasterisk.food_01.screen.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.data.model.Recipe
import com.sunasterisk.appfood.data.model.RecipeType
import com.sunasterisk.appfood.data.retrofit.APIUtil
import com.sunasterisk.appfood.screen.detail.DetailFragment
import com.sunasterisk.appfood.screen.search.RecipeAdapter
import com.sunasterisk.food_01.utils.OnItemRecyclerViewClickListenner
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(), OnItemRecyclerViewClickListenner<Recipe> {
    private val recipeAdapter: RecipeAdapter by lazy { RecipeAdapter() }
    private var listRecipe = mutableListOf<Recipe>()
    private var recipeType = RecipeType()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataSearch("chicken")
        initData()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.setSupportActionBar(view?.toolbarSearch)
        setHasOptionsMenu(true)
    }

    private fun initData() {
        searchFoodRecycler.adapter = recipeAdapter
        recipeAdapter.registerItemRecyclerViewClickListener(this)
    }

    private fun getDataSearchByID(strID : String) {
        val dataClient = APIUtil.getData()
        val callBack = dataClient?.getDataById(strID)
        callBack!!.enqueue(object : Callback<RecipeType> {

            override fun onResponse(
                call: Call<RecipeType>?,
                response: Response<RecipeType>?
            ) {
                recipeType = response!!.body()
                listRecipe.clear()
                recipeType.recipeRandomType?.let { listRecipe.addAll(it) }
                if (listRecipe.size > 0) {
                    recipeAdapter.updateData(listRecipe)
                    txtNoData.visibility = View.GONE
                    searchFoodRecycler.visibility = View.VISIBLE
                } else {
                    searchFoodRecycler.visibility = View.GONE
                    txtNoData.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<RecipeType>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }
        })
    }

    private fun getDataSearch(str: String) {
        val dataClient = APIUtil.getData()
        val callBack = dataClient?.getDataSearch(str)
        callBack!!.enqueue(object : Callback<RecipeType> {

            override fun onResponse(
                call: Call<RecipeType>?,
                response: Response<RecipeType>?
            ) {
                recipeType = response!!.body()
                listRecipe.clear()
                recipeType.recipeRandomType?.let { listRecipe.addAll(it) }
                if (listRecipe.size > 0) {
                    recipeAdapter.updateData(listRecipe)
                    txtNoData.visibility = View.GONE
                    searchFoodRecycler.visibility = View.VISIBLE
                } else {
                    searchFoodRecycler.visibility = View.GONE
                    txtNoData.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<RecipeType>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.menuSearch)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                try{
                    getDataSearchByID(newText.toInt().toString())
                }catch (_: Exception){
                    getDataSearch(newText)
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClickListener(item: Recipe?) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, DetailFragment.newInstance(item!!),"fragA")
        transaction.commit()
    }

    companion object {
        fun newInstance() = SearchFragment()
        const val TAG = "AAA"
    }
}
