package com.example.myapp2025.ui.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp2025.data.models.Person
import com.example.myapp2025.databinding.ActivityMainBinding
import com.example.myapp2025.ui.adapters.PersonAdapter
import com.example.myapp2025.ui.viewmodels.PersonViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PersonViewModel by viewModels()
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Recibir userId desde LoginActivity
        val userId = intent.getIntExtra("userId", -1)
        if (userId == -1) {
            Toast.makeText(this, "Error: usuario no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        Toast.makeText(this, "Bienvenido usuario $userId", Toast.LENGTH_SHORT).show()

        // Configurar RecyclerView
        adapter = PersonAdapter(
            personList = emptyList(),
            onEdit = { person ->
                Toast.makeText(this, "Editar: ${person.nombres}", Toast.LENGTH_SHORT).show()
                val bottomSheet = EditPersonSheet.newInstance(person)
                bottomSheet.show(supportFragmentManager, EditPersonSheet.TAG)
            },
            onDelete = { person ->
                viewModel.delete(person)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.persons.observe(this) { persons ->
            adapter.updateList(persons)
        }

        binding.buttonAdd.setOnClickListener {
            val persona = Person(
                identificacion = "12345678",
                nombres = "Ejemplo",
                apellidos = "Prueba",
                email = "ejemplo@mail.com",
                telefono = "123456",
                direccion = "Calle falsa 123",
                fechanacimiento = "1990-01-01"
            )
            viewModel.insert(persona)
        }
    }
}
