package com.example.myfragments.ui.add

import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfragments.R
import com.example.myfragments.databinding.ActivityAddBinding
import com.example.myfragments.domain.modelo.Clase
import com.example.myfragments.domain.modelo.Classification
import com.example.myfragments.domain.modelo.Scp
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {

    private val viewModel: AddViewModel by viewModels()

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val restrictedGroup = binding.radioGroupClassificationRestricted
        val secretGroup = binding.radioGroupClassificationSecret

        // Declarar los listeners primero
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.editTextNumber.text?.clear()
        binding.editTextAlias.text?.clear()
        binding.editTextDescription.text?.clear()
        binding.radioGroupClass.clearCheck()
        binding.switchFavorite.isChecked = false
        binding.radioGroupClassificationRestricted.clearCheck()
        binding.radioGroupClassificationSecret.clearCheck()

        eventos()
        observacion()
    }

    private fun observacion() {
        viewModel.state.observe(this) { state ->
            binding.editTextNumber.setText(if (state.number == 0) "" else state.number.toString())
            binding.editTextAlias.setText(state.alias)
            binding.editTextDescription.setText(state.description)

            state.mensaje?.let { msg ->
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                viewModel.limpiarMensaje()
            }
        }
    }

    private fun eventos() {
        binding.buttonGuardar.setOnClickListener {
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

            val scp = Scp(
                item = item,
                nombre = nombre,
                clase = clase,
                favorite = isFavorite,
                classification = classification,
                description = description,
            )

            viewModel.guardar(scp)
        }

        binding.buttonGoBack.setOnClickListener {
            finish()
        }
    }

}