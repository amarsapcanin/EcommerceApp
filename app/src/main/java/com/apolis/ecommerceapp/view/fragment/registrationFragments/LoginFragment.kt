package com.apolis.ecommerceapp.view.fragment.registrationFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.apolis.ecommerceapp.R
import com.apolis.ecommerceapp.databinding.FragmentLoginBinding
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse
import com.apolis.ecommerceapp.presenter.LoginContract
import com.apolis.ecommerceapp.presenter.LoginPresenter
import com.apolis.ecommerceapp.view.activity.MainActivity

class LoginFragment : Fragment(), LoginContract.LoginView {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginPresenter: LoginContract.LoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginPresenter = LoginPresenter(VolleyHandler(requireContext()),this)

        binding.txtNoAccount.setOnClickListener {
            loginPresenter.onNoAccountLinkClicked()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            loginPresenter.performLogin(email, password)
        }
    }

    override fun loginSuccess(loginResponse: LoginResponse) {
        Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    override fun loginFailure(message: String) {
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
        Log.i("TAG", message)
    }

    override fun navigateToRegistrationFragment() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.registration_container, RegisterFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
