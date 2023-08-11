package com.apolis.ecommerceapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.apolis.ecommerceapp.databinding.ActivityIntroBinding
import com.apolis.ecommerceapp.databinding.IndicatorDotsBinding
import com.apolis.ecommerceapp.view.adapter.ViewpagerAdapter
import com.apolis.ecommerceapp.view.fragment.introFragments.CartFragment
import com.apolis.ecommerceapp.view.fragment.introFragments.OrderFragment
import com.apolis.ecommerceapp.view.fragment.introFragments.ProductsFragment
import com.google.android.material.tabs.TabLayoutMediator

class Intro : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var viewpagerAdapter: ViewpagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val fragments = listOf(ProductsFragment(), CartFragment(), OrderFragment())
        viewpagerAdapter = ViewpagerAdapter(
            fragments,
            this@Intro
        )
        with(binding) {
            viewPagerIntro.adapter = viewpagerAdapter
            TabLayoutMediator(tabLayout, viewPagerIntro) { tab, _ ->
                tab.customView = createTabView()
            }.attach()

            viewPagerIntro.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    updateTabDots(position)
                }
            })
        }
    }

    private fun createTabView(): View {
        val tabBinding = IndicatorDotsBinding.inflate(layoutInflater)
        return tabBinding.root
    }

    private fun updateTabDots(selectedPosition: Int) {
        for (i in 0 until binding.tabLayout.tabCount) {
            val tabView = binding.tabLayout.getTabAt(i)?.customView
            val tabBinding = IndicatorDotsBinding.bind(tabView!!)

            if (i == selectedPosition) {
                tabBinding.imgDotSelected.visibility = View.VISIBLE
                tabBinding.imgDotUnselected.visibility = View.GONE
            } else {
                tabBinding.imgDotSelected.visibility = View.GONE
                tabBinding.imgDotUnselected.visibility = View.VISIBLE
            }
        }
    }
}