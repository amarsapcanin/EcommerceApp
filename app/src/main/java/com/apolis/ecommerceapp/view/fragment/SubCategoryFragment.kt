package com.apolis.ecommerceapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apolis.ecommerceapp.databinding.CustomTabLayoutBinding
import com.apolis.ecommerceapp.databinding.FragmentSubCategoryBinding
import com.apolis.ecommerceapp.model.remote.dto.Category
import com.apolis.ecommerceapp.model.remote.dto.SubCategoryResponse
import com.apolis.ecommerceapp.model.remote.dto.Subcategory
import com.apolis.ecommerceapp.view.adapter.SubCategoryAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

@Suppress("DEPRECATION")
class SubCategoryFragment : Fragment() {

    private lateinit var binding: FragmentSubCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickedCategory = arguments?.getParcelable<Category>("clickedCategory")
        if(clickedCategory != null){
            clickedCategory.category_id ?.let { fetchSubcategories(it) }
        }
    }

    private fun fetchSubcategories(categoryId: String?) {
        val request = JsonObjectRequest(
            Request.Method.GET, URL_SUB_CATEGORY+categoryId, null,
            { response ->
                val categoriesResponse = Gson().fromJson(response.toString(), SubCategoryResponse::class.java)

                if (categoriesResponse.status == 0) {

                    val subcategories = categoriesResponse.subcategories
                    displaySubcategories(subcategories)

                } else {
                    //error
                }
            },
            { _ ->
            })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    private fun displaySubcategories(subcategories: List<Subcategory>) {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager2

        val tabTitles = subcategories.map { it.subcategory_name }
        val subCategoryPagerAdapter = SubCategoryAdapter(this, subcategories)

        viewPager.adapter = subCategoryPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabBinding = CustomTabLayoutBinding.inflate(
                layoutInflater, tabLayout, false)

            tabBinding.txtSubcategory.text = tabTitles[position]

            tab.customView = tabBinding.root
        }.attach()
    }


    companion object {
        const val URL_SUB_CATEGORY = "http://10.0.2.2/myshop/index.php/SubCategory?category_id="
    }
}