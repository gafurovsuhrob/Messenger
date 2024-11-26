package com.seros.messenger.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.seros.messenger.R
import com.seros.messenger.databinding.FragmentProfileBinding
import com.seros.messenger.presentation.viewmodels.AuthViewModel
import com.seros.messenger.presentation.viewmodels.ProfileFragmentViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileFragmentViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menu.clear()
            menuInflater.inflate(R.menu.exit_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.item_exit -> {
                    exit()
                    true
                }

                else -> false
            }
        }
    }

    private fun exit() {
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.setTitle("Предупреждение")
            .setMessage("Вы действителько хотите выйти?")
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Выйти") { dialog, _ ->
                authViewModel.logout()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}