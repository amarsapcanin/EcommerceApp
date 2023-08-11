package com.apolis.ecommerceapp.presenter

interface SplashContract {
    interface SplashView {
        fun navigateToRegistration()
        fun navigateToIntro()
    }

    interface SplashPresenter {
        fun onViewCreated()
    }
}