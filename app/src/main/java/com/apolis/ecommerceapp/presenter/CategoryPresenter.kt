package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.ResponseCallback
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.remote.dto.CategoryResponse
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse

class CategoryPresenter(private val volleyHandler: VolleyHandler,
                        private val categoryView: CategoryContract.CategoryView):
    CategoryContract.CategoryPresenter {
    override fun getCategories() {
        volleyHandler.getCategories(object : ResponseCallback{
            override fun successLogin(loginResponse: LoginResponse) {
                TODO("Not yet implemented")
            }

            override fun successCategory(categoryResponse: CategoryResponse) {
                categoryView.categoriesSuccess(categoryResponse)
            }

            override fun failure(error: String) {
                categoryView.categoriesError(error)
            }

        })
    }


}