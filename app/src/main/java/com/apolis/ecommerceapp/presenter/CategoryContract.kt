package com.apolis.ecommerceapp.presenter

import com.apolis.ecommerceapp.model.remote.dto.CategoryResponse

interface CategoryContract {

    interface CategoryView {
        fun categoriesSuccess(categoryResponse: CategoryResponse)
        fun categoriesError(message: String)
    }

    interface CategoryPresenter {
        fun getCategories()
    }
}
