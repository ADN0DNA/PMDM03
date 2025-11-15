package com.example.myfragments.ui.sites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfragments.databinding.FragmentSitesListBinding
import com.example.myfragments.ui.SiteAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SitesListFragment : Fragment() {

    private var _binding: FragmentSitesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SiteAdapter

    private val viewModel: SitesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSitesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        setupObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadSites()
    }

    private fun configureRecyclerView() {
        adapter = SiteAdapter { site ->
            val action = SitesListFragmentDirections
                .actionSitesListFragmentToFragmentSitesEditar(siteId = site.id)
            findNavController().navigate(action)
        }

        binding.recyclerViewSites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSites.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.sites)

            state.mensaje?.let { mensaje ->
                Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                viewModel.limpiarMensaje()
            }
        }
    }

    private fun setupListeners() {
        binding.fabAddSite.setOnClickListener {
            val action = SitesListFragmentDirections
                .actionSitesListFragmentToFragmentSitesAdd()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
