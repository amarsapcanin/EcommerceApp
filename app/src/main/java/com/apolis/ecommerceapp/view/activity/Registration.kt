package com.apolis.ecommerceapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.ActivityRegistrationBinding
import com.apolis.ecommerceapp.view.fragment.registrationFragments.LoginFragment

class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.registration_container, LoginFragment())
            .addToBackStack(null)
            .commit()

    }
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}