package com.sunasterisk.appfood.screen.main.tabLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sunasterisk.appfood.R
import com.sunasterisk.appfood.screen.favorite.FavoriteFragment
import com.sunasterisk.appfood.screen.home.HomeFragment
import com.sunasterisk.food_01.screen.mainPage.MainPagerAdapter
import com.sunasterisk.food_01.screen.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_tab.*

class MainPageFragment : Fragment() {
    private val homeFragment = HomeFragment.newInstance()
    private val searchFragment = SearchFragment.newInstance()
    private val favoriteFragment = FavoriteFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
    }

    private fun setupTabLayout() {
        setupViewPager()
        tabLayout.setupWithViewPager(viewPager)
        setupTabIcons()
    }

    private fun setupTabIcons() {
        tabLayout.getTabAt(HOME_PAGE)?.setIcon(R.drawable.ic_home)
        tabLayout.getTabAt(SEARCH_PAGE)?.setIcon(R.drawable.ic_search)
        tabLayout.getTabAt(FAVORITE_PAGE)?.setIcon(R.drawable.ic_heart)
    }

    private fun setupViewPager() {
        viewPager.adapter = activity?.supportFragmentManager?.let {
            MainPagerAdapter(it).apply {
                addFragment(homeFragment, resources.getString(R.string.home))
                addFragment(searchFragment, resources.getString(R.string.search))
                addFragment(favoriteFragment, resources.getString(R.string.favorite))
            }
        }
        viewPager.offscreenPageLimit = 3
    }

    companion object {
        private const val HOME_PAGE = 0
        private const val SEARCH_PAGE = 1
        private const val FAVORITE_PAGE = 2
    }
}
