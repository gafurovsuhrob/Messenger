package com.seros.messenger.presentation.ui.auth

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.seros.messenger.R
import com.seros.messenger.databinding.FragmentRegistrationBinding
import com.seros.messenger.presentation.viewmodels.AuthViewModel
import com.seros.messenger.presentation.viewmodels.RegistrationFragmentViewModel
import com.seros.messenger.utils.setupClearErrorOnTouch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationFragmentViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private var selectedImageUri = MutableLiveData<Uri?>()

    private var fullName: String = ""
    private var phoneNumber: String = ""

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                selectedImageUri.value = uri
                binding.tvInitials.visibility = INVISIBLE
                binding.ivProfilePicture.setImageURI(uri)
            } else {
                selectedImageUri.value = null
                Toast.makeText(requireContext(), "Вы не выбрали фото", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()

    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        selectedImageUri.observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                binding.ivProfilePicture.setImageURI(uri)
                binding.tvInitials.visibility = INVISIBLE
                binding.fabClearImage.visibility = VISIBLE
            } else {
                binding.ivProfilePicture.setImageURI(null)
                binding.tvInitials.visibility = VISIBLE
                binding.fabClearImage.visibility = INVISIBLE
            }
        }

        binding.inputFullName.addTextChangedListener { text ->
            val filteredText = text.toString().filter { it.isLetter() || it.isWhitespace() }

            if (text.toString() != filteredText) {
                Toast.makeText(requireContext(), "Введите только буквы", Toast.LENGTH_SHORT).show()
                binding.inputFullName.setText(filteredText)
                binding.inputFullName.setSelection(filteredText.length)
                fullName = filteredText
            } else {
                if (text.toString().isNotEmpty()) {
                    fullName = text.toString()
                    val words = filteredText.split("\\s+".toRegex()).filter { it.isNotBlank() }
                    val initials = words.take(2)
                        .joinToString("") { it.firstOrNull()?.uppercaseChar()?.toString() ?: "" }

                    binding.tvFullName.text = text.toString()
                    binding.tvFullName.visibility = VISIBLE
                    binding.tvInitials.text = initials
                } else {
                    binding.tvInitials.text = "+"
                    binding.tvFullName.visibility = INVISIBLE
                    fullName = ""
                }
            }
        }

        binding.inputPhoneNumber.addTextChangedListener { text ->
            if (text.toString().isNotEmpty()) {
                phoneNumber = text.toString()
                binding.tvNumberPhone.visibility = VISIBLE
                binding.tvNumberPhone.text = "+992 ${text.toString()}"
            } else {
                binding.tvNumberPhone.visibility = INVISIBLE
                phoneNumber = ""
            }
        }

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
            Log.d("TAG", "setupObservers: $user")
            if (user != null) {
                navigateToMainScreen()
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            tvGoToLogin.setOnClickListener {
                fieldsCheckGoBack()
            }
            fabGoToBack.setOnClickListener {
                fieldsCheckGoBack()
            }
            fabClearImage.setOnClickListener {
                selectedImageUri.value = null
            }
            ivProfilePicture.setOnClickListener {
                pickImageLauncher.launch("image/*")
            }
            tvInitials.setOnClickListener {
                pickImageLauncher.launch("image/*")
            }
            btnRegistration.setOnClickListener {
                val fullName = binding.inputFullName.text.toString()
                val phoneNumber = binding.inputPhoneNumber.text.toString()
                val password = binding.inputPassword.text.toString()

                if (validateRegisterFields(fullName, phoneNumber, password)) {
                    authViewModel.register(fullName, phoneNumber, password, selectedImageUri.value)
                }
            }

            setupClearErrorOnTouch(
                listOf(inputLayoutFullName, inputLayoutPassword, inputLayoutPhoneNumber),
                listOf(binding.root)
            )
        }
    }

    private fun fieldsCheckGoBack() {
        if (!checkFields()) {
            findNavController().popBackStack()
        } else {
            val dialog = MaterialAlertDialogBuilder(requireContext())
            dialog.setTitle("Предупреждение")
                .setMessage("Заполнены несколь полей или добавлено фото, вы действителько хотите выйти?")
                .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Выйти") { dialog, _ ->
                    findNavController().popBackStack()
                }
                .show()
        }
    }

    private fun checkFields(): Boolean {
        return fullName.isNotEmpty()
        return phoneNumber.isNotEmpty()
        return selectedImageUri.value != null
    }

    private fun validateRegisterFields(
        fullName: String,
        phoneNumber: String,
        password: String
    ): Boolean {
        if (fullName.isBlank()) {
            binding.inputLayoutFullName.error = "Введите полное имя"
            return false
        }
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
        findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}