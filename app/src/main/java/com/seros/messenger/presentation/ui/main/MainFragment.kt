package com.seros.messenger.presentation.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.seros.messenger.R
import com.seros.messenger.databinding.FragmentMainBinding
import com.seros.messenger.presentation.viewmodels.ContactsViewModel
import com.seros.messenger.presentation.viewmodels.MainFragmentViewModel
import com.seros.messenger.utils.loadContacts

private const val REQUEST_CONTACTS_PERMISSION = 1001

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainFragmentViewModel by viewModels()
    private val contactsViewModel: ContactsViewModel by viewModels()

    private lateinit var navController: NavController

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        bottomNav = binding.bottomNav
        toolbar = binding.materialToolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBottomNavigation()

        requestContactsPermission()

        contactsViewModel.contacts.observe(viewLifecycleOwner) { contactList ->
            Log.d("TAG", "onViewCreated: $contactList")
            contactList.forEach {
                Log.d("TAG", "onViewCreated: ${it.number}, ${it.name}")
            }
        }
    }

    private fun requestContactsPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CONTACTS_PERMISSION
            )
        } else {
            contactsViewModel.loadContactsFromDevice(requireContext())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CONTACTS_PERMISSION &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            contactsViewModel.loadContactsFromDevice(requireContext())
        } else {
            Log.e("MainFragment", "Permission denied. Cannot load contacts.")
        }
    }

    private fun setupBottomNavigation() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment_container_main)
                as? NavHostFragment ?: return
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.chatsFragment, R.id.usersFragment, R.id.profileFragment)
        )

        toolbar.setupWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)

        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.title = destination.label
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}