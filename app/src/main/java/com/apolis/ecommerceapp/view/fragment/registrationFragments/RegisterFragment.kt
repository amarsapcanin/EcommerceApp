package com.apolis.ecommerceapp.view.fragment.registrationFragments

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
import com.apolis.ecommerceapp.databinding.FragmentRegisterBinding
import com.apolis.ecommerceapp.model.VolleyHandler
import com.apolis.ecommerceapp.model.remote.dto.LoginResponse
import com.apolis.ecommerceapp.presenter.RegistrationContract
import com.apolis.ecommerceapp.presenter.RegistrationPresenter

class RegisterFragment : Fragment(), RegistrationContract.RegistrationView {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerPresenter: RegistrationContract.RegistrationPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerPresenter = RegistrationPresenter(VolleyHandler(requireContext()), this)

        binding.txtYesAccount.setOnClickListener {
            registerPresenter.onYesAccountLinkClicked()
        }

        binding.btnRegister.setOnClickListener {
            val fullName = binding.edFullname.text.toString()
            val number = binding.edNumber.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            registerPresenter.performRegistration(fullName, number, email, password)

        }
    }
    override fun registrationSuccess(loginResponse: LoginResponse) {
        binding.apply {
            edFullname.text?.clear()
            edNumber.text?.clear()
            edEmail.text?.clear()
            edPassword.text?.clear()

        }

        Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show()

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.registration_container, LoginFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun registrationFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.i("TAG", message)
    }

    override fun navigateToLoginFragment() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.registration_container, LoginFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }


}