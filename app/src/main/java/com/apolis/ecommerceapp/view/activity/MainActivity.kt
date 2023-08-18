package com.apolis.ecommerceapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.ActivityMainBinding
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.preferences.SharedPreference
import com.apolis.ecommerceapp.model.remote.dto.LogoutResponse
import com.apolis.ecommerceapp.presenter.LogoutContract
import com.apolis.ecommerceapp.presenter.LogoutPresenter
import com.apolis.ecommerceapp.view.fragment.CartFragment
import com.apolis.ecommerceapp.view.fragment.HomeFragment

class MainActivity : AppCompatActivity(), LogoutContract.LogoutView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedMenuItem: MenuItem
    private lateinit var logoutPresenter: LogoutContract.LogoutPresenter
    private lateinit var sharedPreference : SharedPreference
    private var isSearchVisible = false
    private var isDarkTheme = false

    fun updateToolbar(title: String) {
        supportActionBar?.apply {
            binding.txtTitle.text = title
            binding.imgSearch.visibility = View.GONE
            binding.imgBack.visibility = View.VISIBLE
            setHomeAsUpIndicator(R.drawable.baseline_arrow_drop_down_24)

            binding.imgBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    fun originalToolbar(){
        supportActionBar?.apply {
            binding.txtTitle.text = "Super Cart"
            binding.imgSearch.visibility = View.VISIBLE
            binding.imgBack.visibility = View.GONE
            setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val headerView = binding.navViews.getHeaderView(0)

        val nameOfUser = headerView.findViewById<TextView>(R.id.txt_nameOfUser)
        val emailOfUser = headerView.findViewById<TextView>(R.id.txt_emailOfUser)
        val numberOfUser = headerView.findViewById<TextView>(R.id.txt_numberOfUser)

        sharedPreference = SharedPreference(this)
        nameOfUser.text = sharedPreference.getName("userName")
        emailOfUser.text = sharedPreference.getEmail("email")
        numberOfUser.text = sharedPreference.getNumber("userNumber")

        val themeSwitch = headerView.findViewById<SwitchCompat>(R.id.switchTheme)

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }

        logoutPresenter = LogoutPresenter(VolleyHandler(this), this)

        supportFragmentManager.beginTransaction().add(R.id.main_container, HomeFragment()).commit()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        }

        val defaultSelectedItem = binding.navViews.menu.findItem(R.id.home)
        defaultSelectedItem.isChecked = true
        selectedMenuItem = defaultSelectedItem

        binding.navViews.setNavigationItemSelectedListener {menuItems ->

            menuItems.isChecked = true
            selectedMenuItem.isChecked = false
            selectedMenuItem = menuItems

            when(menuItems.itemId){
                R.id.home -> {
                    handleMenuEvent(HomeFragment())
                    binding.txtTitle.text = "SUPER CART"
                    binding.imgSearch.visibility = View.VISIBLE
                }
                R.id.cart -> {
                    handleMenuEvent(CartFragment())
                    binding.txtTitle.text = "CART"
                    binding.imgSearch.visibility = View.GONE
                }
                R.id.logout -> {
                    sharedPreference = SharedPreference(this)
                    val email = sharedPreference.getEmail("email")
                    if (email != null) {
                        logoutPresenter.performLogout(email)
                    }
                    sharedPreference.clearEmail()
                    sharedPreference.clearName()
                    sharedPreference.clearNumber()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.imgSearch.setOnClickListener {
            isSearchVisible = !isSearchVisible

            if (isSearchVisible) {
                binding.edSearch.visibility = View.VISIBLE
            } else {
                binding.edSearch.visibility = View.GONE
            }
        }

    }

    private fun toggleTheme() {
        if (isDarkTheme) {
            setTheme(R.style.ThemeLight)
        } else {
            setTheme(R.style.ThemeDark)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleMenuEvent(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
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

    override fun logoutSuccess(logoutResponse: LogoutResponse) {
        Toast.makeText(
            this, "Logout successful!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, Splash::class.java))
    }

    override fun logoutFailure(error: String) {
        Toast.makeText(
            this, "Logout unsuccessful!", Toast.LENGTH_SHORT).show()
    }
}