package com.apolis.ecommerceapp.view.fragment.introFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.FragmentOrderBinding
import com.apolis.ecommerceapp.model.preferences.SharedPreference
import com.apolis.ecommerceapp.view.activity.MainActivity
import com.apolis.ecommerceapp.view.activity.Registration

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private lateinit var sharedPreference : SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = SharedPreference(requireContext())

        binding.btnLetsShop.setOnClickListener {
            sharedPreference.saveBoolean("isFirstTime", true)

            val intent = Intent(requireContext(), Registration::class.java)
            startActivity(intent)
        }

    }
}