package com.seros.messenger.presentation.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.seros.messenger.R
import com.seros.messenger.databinding.FragmentFirstBinding
import com.seros.messenger.presentation.viewmodels.FirstFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private val viewModel: FirstFragmentViewModel by viewModels()

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener {
            if (viewModel.isAuthenticated()){
                findNavController().navigate(R.id.action_firstFragment_to_mainFragment)
            }else {
                findNavController().navigate(R.id.action_firstFragment_to_loginFragment)
            }
        }
    }
}