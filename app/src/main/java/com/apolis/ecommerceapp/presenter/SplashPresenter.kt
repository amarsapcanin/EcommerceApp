package com.apolis.ecommerceapp.presenter

import android.os.Handler
import com.apolis.ecommerceapp.model.preferences.SharedPreference

class SplashPresenter(
    private val view: SplashContract.SplashView,
    private val sharedPreference: SharedPreference
) : SplashContract.SplashPresenter {

    override fun onViewCreated() {
        val isFirstTime = sharedPreference.getBoolean("isFirstTime", false)
        Handler().postDelayed({
            if (isFirstTime) {
                view.navigateToRegistration()
            } else {
                view.navigateToIntro()
            }
        }, SPLASH_DELAY)
    }

    companion object {
        const val SPLASH_DELAY = 2500L
    }
}
