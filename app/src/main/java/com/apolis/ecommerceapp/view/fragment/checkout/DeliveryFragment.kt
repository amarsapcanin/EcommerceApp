package com.apolis.ecommerceapp.view.fragment.checkout

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.AddAddresDialogBinding
import com.apolis.ecommerceapp.databinding.FragmentDeliveryBinding
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.preferences.SharedPreference
import com.apolis.ecommerceapp.model.remote.dto.Address
import com.apolis.ecommerceapp.model.remote.dto.AddressResponse
import com.apolis.ecommerceapp.view.adapter.AddressAdapter
import com.google.gson.Gson
import com.apolis.ecommerceapp.model.local.DbHandler
import com.apolis.ecommerceapp.model.local.dao.InfoDao
import com.apolis.ecommerceapp.model.local.entity.InfoLocal
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse
import org.json.JSONObject

class DeliveryFragment : Fragment(), AddressAdapter.ItemClickRadioListener {

    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var adapter: AddressAdapter
    private lateinit var sharedPreference : SharedPreference
    private lateinit var dbHandler: DbHandler
    private lateinit var infoDao: InfoDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initDao()
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = SharedPreference(requireContext())
        val userId = sharedPreference.getId("userId")

         getAddresses(userId.toString())

        binding.btnNext.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager2)
            val currentItem = viewPager.currentItem
            viewPager.setCurrentItem(currentItem + 1, true)
        }

        binding.btnAddAddress.setOnClickListener {
            addAddressDialog()
        }
    }

    override fun onResume() {
        super.onResume()

        sharedPreference = SharedPreference(requireContext())
        val userId = sharedPreference.getId("userId")

        getAddresses(userId.toString())
    }

    private fun getAddresses(userId: String){
        val request = JsonObjectRequest(
            Request.Method.GET, URL_ADDRESS + userId, null,
            { response ->
                val addressResponse = Gson().fromJson(response.toString(), AddressResponse::class.java)

                if (addressResponse.status == 0) {
                    val address = addressResponse.addresses
                    displayAddresses(address)
                } else {
                    val message = addressResponse.message
                    Log.i("tag", message)
                }
            },
            { error ->
                Log.i("tag", error.toString())
            })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    private fun displayAddresses(address: List<Address>) {

        adapter = AddressAdapter(address, this)
        val layoutManager = LinearLayoutManager(requireContext())

        binding.apply {
            rcAddresses.adapter = adapter
            rcAddresses.layoutManager = layoutManager
        }
    }

    private fun addAddressDialog() {
        val addAddressDialogBinding = AddAddresDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireContext()).apply {
            setView(addAddressDialogBinding.root)
            setCancelable(false)
        }

        val dialog = builder.create()
        dialog.window?.setGravity(Gravity.CENTER)

        addAddressDialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        addAddressDialogBinding.btnSave.setOnClickListener {
            addAddressDialogBinding.apply {
                val address = edAddress.text.toString()
                val addressTittle = edAddressTitle.text.toString()

                val userId = sharedPreference.getId("userId").toString()
                addAddress(userId, addressTittle, address )

                edAddress.text?.clear()
                edAddressTitle.text?.clear()

                adapter.notifyDataSetChanged()

                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onItemClick(address: Address) {
        val newInfo =
            address.title?.let { address.address?.let { it1 -> InfoLocal(infoId = 0, addressTitle = it, address = it1, payment = " " ) } }
        if (newInfo != null) {
            infoDao.addAddress(newInfo)
        }
    }

    fun addAddress(userId: String,addressTittle: String, address: String) {

        val jsonObject = JSONObject()
        jsonObject.put("user_id", userId)
        jsonObject.put("title", address)
        jsonObject.put("address", addressTittle)

        val request = JsonObjectRequest(
            Request.Method.POST, URL_ADD_ADDRESS, jsonObject,
            { response ->
                val addressAddResponse = Gson().fromJson(response.toString(),
                    AddressResponse::class.java)

                if (addressAddResponse.status == 0) {
                    Toast.makeText(requireContext(), "Address added!", Toast.LENGTH_SHORT).show()
                } else {
                    val message = addressAddResponse.message
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->

            })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    private fun initDao() {
        dbHandler = DbHandler(requireContext())
        infoDao = InfoDao(dbHandler)
    }

    companion object {
        const val URL_ADDRESS = "http://10.0.2.2/myshop/index.php/User/addresses/"
        const val URL_ADD_ADDRESS = "http://10.0.2.2/myshop/index.php/User/address"
    }
}