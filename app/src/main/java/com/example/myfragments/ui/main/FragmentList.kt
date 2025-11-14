package com.example.myfragments.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfragments.databinding.FragmentListBinding
import com.example.myfragments.ui.ScpAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ScpAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun configureRecyclerView() {
        adapter = ScpAdapter { scp ->
            // Navegar usando NavController en lugar de Intent
            val action = ListFragmentDirections
                .actionListFragmentToFragmentEditar(scpId = scp.id)
            findNavController().navigate(action)
        }

        binding.recyclerViewScps.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewScps.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadScps()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.scps)

            state.mensaje?.let { mensaje ->
                Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                viewModel.limpiarMensaje()
            }
        }
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            val action = ListFragmentDirections
                .actionListFragmentToFragmentAdd()

            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
