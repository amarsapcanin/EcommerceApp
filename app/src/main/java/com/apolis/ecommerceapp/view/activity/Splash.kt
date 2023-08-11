package com.apolis.ecommerceapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.ActivitySplashBinding
import com.apolis.ecommerceapp.model.preferences.SharedPreference

class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var sharedPreference : SharedPreference
    private var SPLASH_DELAY : Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = SharedPreference(applicationContext)

        val ifFirstTime = sharedPreference.getBoolean("isFirstTime", false)

        Handler().postDelayed({

            if(ifFirstTime){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, Intro::class.java)
                startActivity(intent)
            }

            finish()
        }, SPLASH_DELAY)
    }
}