package com.example.myfragments.ui.editar

import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfragments.R
import com.example.myfragments.data.RepositorioScps
import com.example.myfragments.databinding.ActivityEditarBinding
import com.example.myfragments.domain.modelo.Clase
import com.example.myfragments.domain.modelo.Classification
import com.example.myfragments.domain.modelo.Scp
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarBinding
    private val viewModel: EditarViewModel by viewModels()

    @Inject
    lateinit var repositorioScps: RepositorioScps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val extraId = intent.getIntExtra("EXTRA_SCP_ID", -1)
        val idToLoad = if (extraId != -1) {
            extraId
        } else {
            repositorioScps.getAll().firstOrNull()?.id ?: -1
        }

        if (idToLoad == -1) {
            // nothing to edit; show message and finish
            Snackbar.make(binding.root, "No hay SCP para editar", Snackbar.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.cargar(idToLoad)

        eventos()

        observacion()
    }

    private fun observacion() {
        viewModel.state.observe(this) { state ->
            val scp = state.scp

            Snackbar.make(binding.root, "Loaded scp id=${scp.id}", Snackbar.LENGTH_SHORT).show()

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
                Classification.UNRESTRICTED -> binding.radioGroupClassificationRestricted.check(R.id.radioButtonUnrestricted)
                Classification.RESTRICTED -> binding.radioGroupClassificationRestricted.check(R.id.radioButtonRestricted)
                Classification.CONFIDENTIAL -> binding.radioGroupClassificationRestricted.check(R.id.radioButtonConfidential)
                Classification.SECRET -> binding.radioGroupClassificationSecret.check(R.id.radioButtonSecret)
                Classification.TOP_SECRET -> binding.radioGroupClassificationSecret.check(R.id.radioButtonTopSecret)
            }


            state.mensaje?.let { error ->
                Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
                viewModel.limpiarMensaje()
            }

            if (state.cerrar) {
                finish()
            }
        }
    }



    private fun eventos() {

        binding.buttonActualizar.setOnClickListener {
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

            Snackbar.make(binding.root, "SCP guardado correctamente", Snackbar.LENGTH_SHORT).show()
        }

        binding.buttonBorrar.setOnClickListener {
            viewModel.borrar()
        }

        binding.buttonGoBack.setOnClickListener {
            finish()
        }

    }



}