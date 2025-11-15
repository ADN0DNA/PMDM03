package com.example.myfragments.ui.scps.editar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfragments.R
import com.example.myfragments.databinding.FragmentScpsEditarBinding
import com.example.myfragments.domain.modelo.*
import com.example.myfragments.ui.SiteSelectorAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentScpsEditar : Fragment() {

    private var _binding: FragmentScpsEditarBinding? = null
    private val binding get() = _binding!!

    private val args: FragmentScpsEditarArgs by navArgs()

    private val viewModel: EditarScpViewModel by viewModels()

    private lateinit var siteSelectorAdapter: SiteSelectorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScpsEditarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRadioGroups()
        configureSitesRecyclerView()
        setupObservers()
        setupListeners()

        // Cargar SCP con el ID recibido desde navegación
        // Si el ID es -1 no es válido
        if (args.scpId != -1) {
            viewModel.cargar(args.scpId)
        } else {
            // Navegar de vuelta si no hay un ID válido
            findNavController().navigateUp()
        }
    }

    private fun configureSitesRecyclerView() {
        siteSelectorAdapter = SiteSelectorAdapter { site, isSelected ->
            viewModel.toggleSiteAssignment(site, isSelected)
        }

        binding.recyclerViewSitesSelector.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = siteSelectorAdapter
        }
    }

    private fun setupRadioGroups() {
        val restrictedGroup = binding.radioGroupClassificationRestricted
        val secretGroup = binding.radioGroupClassificationSecret

        lateinit var restrictedListener: RadioGroup.OnCheckedChangeListener
        lateinit var secretListener: RadioGroup.OnCheckedChangeListener

        restrictedListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                secretGroup.setOnCheckedChangeListener(null)
                secretGroup.clearCheck()
                secretGroup.setOnCheckedChangeListener(secretListener)
            }
        }

        secretListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                restrictedGroup.setOnCheckedChangeListener(null)
                restrictedGroup.clearCheck()
                restrictedGroup.setOnCheckedChangeListener(restrictedListener)
            }
        }

        restrictedGroup.setOnCheckedChangeListener(restrictedListener)
        secretGroup.setOnCheckedChangeListener(secretListener)
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            updateUI(state)

            siteSelectorAdapter.submitList(state.availableSites)

            state.mensaje?.let { mensaje ->
                Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                viewModel.limpiarMensaje()
            }

            if (state.cerrar) {
                findNavController().navigateUp()
            }
        }
    }

    private fun updateUI(state: EditarScpState) {
        val scp = state.scp

        binding.editTextNumber.setText(scp.item.toString())
        binding.editTextAlias.setText(scp.nombre)
        binding.editTextDescription.setText(scp.description)


        when (scp.clase) {
            Clase.SAFE -> binding.radioGroupClass.check(R.id.radioButtonSafe)
            Clase.EUCLID -> binding.radioGroupClass.check(R.id.radioButtonEuclid)
            Clase.KETER -> binding.radioGroupClass.check(R.id.radioButtonKeter)
        }


        binding.switchFavorite.isChecked = scp.favorite


        when (scp.classification) {
            Classification.UNRESTRICTED ->
                binding.radioGroupClassificationRestricted.check(R.id.radioButtonUnrestricted)
            Classification.RESTRICTED ->
                binding.radioGroupClassificationRestricted.check(R.id.radioButtonRestricted)
            Classification.CONFIDENTIAL ->
                binding.radioGroupClassificationRestricted.check(R.id.radioButtonConfidential)
            Classification.SECRET ->
                binding.radioGroupClassificationSecret.check(R.id.radioButtonSecret)
            Classification.TOP_SECRET ->
                binding.radioGroupClassificationSecret.check(R.id.radioButtonTopSecret)
        }


    }

    private fun setupListeners() {
        binding.buttonActualizar.setOnClickListener {
            val scp = buildScpFromInputs()
            viewModel.guardar(scp)
            findNavController().navigateUp()
        }

        binding.buttonBorrar.setOnClickListener {
            viewModel.borrar()
            findNavController().navigateUp()
        }

        binding.buttonGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun buildScpFromInputs(): Scp {
        val item = binding.editTextNumber.text.toString().toIntOrNull() ?: 0
        val nombre = binding.editTextAlias.text.toString()
        val description = binding.editTextDescription.text.toString()

        val clase = when (binding.radioGroupClass.checkedRadioButtonId) {
            R.id.radioButtonSafe -> Clase.SAFE
            R.id.radioButtonEuclid -> Clase.EUCLID
            R.id.radioButtonKeter -> Clase.KETER
            else -> Clase.SAFE
        }

        val isFavorite = binding.switchFavorite.isChecked

        val classification = when (binding.radioGroupClassificationRestricted.checkedRadioButtonId) {
            R.id.radioButtonUnrestricted -> Classification.UNRESTRICTED
            R.id.radioButtonRestricted -> Classification.RESTRICTED
            R.id.radioButtonConfidential -> Classification.CONFIDENTIAL
            else -> when (binding.radioGroupClassificationSecret.checkedRadioButtonId) {
                R.id.radioButtonSecret -> Classification.SECRET
                R.id.radioButtonTopSecret -> Classification.TOP_SECRET
                else -> Classification.UNRESTRICTED
            }
        }

        return Scp(
            item = item,
            nombre = nombre,
            clase = clase,
            favorite = isFavorite,
            classification = classification,
            description = description,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
