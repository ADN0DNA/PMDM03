package com.example.myfragments.ui.scps.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myfragments.R
import com.example.myfragments.databinding.FragmentScpsAddBinding
import com.example.myfragments.domain.modelo.Clase
import com.example.myfragments.domain.modelo.Classification
import com.example.myfragments.domain.modelo.Scp
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentScpsAdd : Fragment() {

    private var _binding: FragmentScpsAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddScpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScpsAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRadioGroups()
        clearFields()
        setupObservers()
        setupListeners()
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

    private fun clearFields() {
        binding.editTextNumber.text?.clear()
        binding.editTextAlias.text?.clear()
        binding.editTextDescription.text?.clear()
        binding.radioGroupClass.clearCheck()
        binding.switchFavorite.isChecked = false
        binding.radioGroupClassificationRestricted.clearCheck()
        binding.radioGroupClassificationSecret.clearCheck()
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.editTextNumber.setText(
                if (state.number == 0) "" else state.number.toString()
            )
            binding.editTextAlias.setText(state.alias)
            binding.editTextDescription.setText(state.description)

            state.mensaje?.let { mensaje ->
                Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                viewModel.limpiarMensaje()
            }
        }
    }

    private fun setupListeners() {
        binding.buttonGuardar.setOnClickListener {
            val scp = buildScpFromInputs()
            viewModel.guardar(scp)
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
