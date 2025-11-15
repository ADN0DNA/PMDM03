package com.example.myfragments.ui.scps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfragments.databinding.FragmentScpsListBinding
import com.example.myfragments.ui.ScpAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentScpsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ScpAdapter

    private val viewModel: ScpsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScpsListBinding.inflate(inflater, container, false)
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
        viewModel.loadScps()
    }

    private fun configureRecyclerView() {
        adapter = ScpAdapter { scpId ->
            val action = ListFragmentDirections
                .actionListFragmentToFragmentEditar(scpId = scpId)
            findNavController().navigate(action)
        }

        binding.recyclerViewScps.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewScps.adapter = adapter
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
