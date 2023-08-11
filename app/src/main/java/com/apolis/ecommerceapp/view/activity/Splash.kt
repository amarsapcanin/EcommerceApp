package com.apolis.ecommerceapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.ActivitySplashBinding
import com.apolis.ecommerceapp.model.preferences.SharedPreference
import com.apolis.ecommerceapp.presenter.SplashContract
import com.apolis.ecommerceapp.presenter.SplashPresenter

class Splash : AppCompatActivity(), SplashContract.SplashView {
    private lateinit var splashPresenter: SplashContract.SplashPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashPresenter = SplashPresenter(this, SharedPreference(applicationContext))
        splashPresenter.onViewCreated()
    }

    override fun navigateToRegistration() {
        startActivity(Intent(this, Registration::class.java))
        finish()
    }

    override fun navigateToIntro() {
        startActivity(Intent(this, Intro::class.java))
        finish()
    }
}