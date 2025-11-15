package com.example.myfragments.ui.sites.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myfragments.databinding.FragmentSitesAddBinding
import com.example.myfragments.domain.modelo.Site
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSitesAdd : Fragment() {

    private var _binding: FragmentSitesAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddSiteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSitesAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearFields()
        setupObservers()
        setupListeners()
    }

    private fun clearFields() {
        binding.editTextCode.text?.clear()
        binding.editTextLocation.text?.clear()
        binding.editTextSecurityLevel.text?.clear()
        binding.editTextDirector.text?.clear()
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.editTextCode.setText(
                if (state.code == 0) "" else state.code.toString()
            )
            binding.editTextLocation.setText(state.location)
            binding.editTextSecurityLevel.setText(state.securityLevel)
            binding.editTextDirector.setText(state.director)

            state.mensaje?.let { mensaje ->
                Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                viewModel.limpiarMensaje()
            }
        }
    }

    private fun setupListeners() {
        binding.buttonGuardar.setOnClickListener {
            val site = buildSiteFromInputs()
            viewModel.guardar(site)
            findNavController().navigateUp()
        }

        binding.buttonGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun buildSiteFromInputs(): Site {
        val code = binding.editTextCode.text.toString().toIntOrNull() ?: 0
        val location = binding.editTextLocation.text.toString()
        val securityLevel = binding.editTextSecurityLevel.text.toString()
        val director = binding.editTextDirector.text.toString()

        return Site(
            id = 0,
            code = code,
            location = location,
            securityLevel = securityLevel,
            director = director
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

