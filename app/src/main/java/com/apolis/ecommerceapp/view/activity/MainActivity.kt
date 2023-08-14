package com.apolis.ecommerceapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.ActivityMainBinding
import com.apolis.ecommerceapp.model.remote.dto.Category
import com.apolis.ecommerceapp.view.fragment.CartFragment
import com.apolis.ecommerceapp.view.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedMenuItem: MenuItem
    private var isSearchVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                // R.id.logout ->
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
}