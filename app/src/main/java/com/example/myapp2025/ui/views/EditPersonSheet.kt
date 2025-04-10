package com.example.myapp2025.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapp2025.data.models.Person
import com.example.myapp2025.databinding.EditPersonSheetBinding
import com.example.myapp2025.ui.viewmodels.PersonViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditPersonSheet : BottomSheetDialogFragment() {

    private var _binding: EditPersonSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonViewModel by activityViewModels()
    private var person: Person? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditPersonSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Rellenar los campos con los datos actuales
        person?.let {
            binding.editTextName.setText(it.nombres)
            binding.editTextLastname.setText(it.apellidos)

        }

        // Guardar cambios
        binding.buttonUpdatePerson.setOnClickListener {
            val updatedName = binding.editTextName.text.toString()
            val updatedLastname = binding.editTextLastname.text.toString()

            if (updatedName.isBlank() || updatedLastname.isBlank()) {
                Toast.makeText(context, "Datos inválidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedPerson = person?.copy(
                nombres = updatedName,
                apellidos = updatedLastname
            )


            if (updatedPerson != null) {
                viewModel.updatePerson(updatedPerson)
                dismiss()
            }
        }
    }

    fun setPerson(person: Person) {
        this.person = person
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "EditPersonSheet"

        fun newInstance(person: Person): EditPersonSheet {
            val fragment = EditPersonSheet()
            fragment.setPerson(person)
            return fragment
        }
    }
}
