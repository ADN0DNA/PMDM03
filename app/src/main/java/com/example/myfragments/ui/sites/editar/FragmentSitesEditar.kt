package com.example.myfragments.ui.sites.editar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myfragments.databinding.FragmentSitesEditarBinding
import com.example.myfragments.domain.modelo.Site
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSitesEditar : Fragment() {

    private var _binding: FragmentSitesEditarBinding? = null
    private val binding get() = _binding!!

    private val args: FragmentSitesEditarArgs by navArgs()

    private val viewModel: EditarSiteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSitesEditarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()

        if (args.siteId != -1) {
            viewModel.cargar(args.siteId)
        } else {
            findNavController().navigateUp()
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            updateUI(state.site)

            state.mensaje?.let { mensaje ->
                Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                viewModel.limpiarMensaje()
            }

            if (state.cerrar) {
                findNavController().navigateUp()
            }
        }
    }

    private fun updateUI(site: Site) {
        binding.editTextCode.setText(site.code.toString())
        binding.editTextLocation.setText(site.location)
        binding.editTextSecurityLevel.setText(site.securityLevel)
        binding.editTextDirector.setText(site.director)
    }

    private fun setupListeners() {
        binding.buttonActualizar.setOnClickListener {
            val site = buildSiteFromInputs()
            viewModel.guardar(site)
        }

        binding.buttonBorrar.setOnClickListener {
            viewModel.borrar()
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

