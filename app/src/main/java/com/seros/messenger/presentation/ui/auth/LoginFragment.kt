package com.seros.messenger.presentation.ui.auth

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.seros.messenger.R
import com.seros.messenger.databinding.FragmentLoginBinding
import com.seros.messenger.presentation.viewmodels.AuthViewModel
import com.seros.messenger.presentation.viewmodels.LoginFragmentViewModel
import com.seros.messenger.utils.setupClearErrorOnTouch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginFragmentViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }


    private fun setupObservers() {
        authViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        authViewModel.errorState.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                authViewModel.clearError()
            }
        }

        authViewModel.authState.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                navigateToMainScreen()
            }
        }
    }
    private fun setupListeners() {
        binding.apply {
            tvGoToRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }

            btnContinue.setOnClickListener {
                val phoneNumber = binding.inputPhoneNumber.text.toString()
                val password = binding.inputPassword.text.toString()

                if (validateLoginFields(phoneNumber, password)) {
                    authViewModel.login(phoneNumber, password)
                }
            }

            setupClearErrorOnTouch(
                listOf(inputLayoutPassword, inputLayoutPhoneNumber),
                listOf(binding.root)
            )
        }
    }

    private fun validateLoginFields(phoneNumber: String, password: String): Boolean {
        if (phoneNumber.isBlank()) {
            binding.inputLayoutPhoneNumber.error = "Введите номер телефона"
            return false
        }
        if (password.isBlank()) {
            binding.inputLayoutPassword.error = "Введите пароль"
            return false
        }
        return true
    }

    private fun navigateToMainScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}