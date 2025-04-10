package com.example.myapp2025.ui.views

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapp2025.R
import com.example.myapp2025.data.database.AppDatabase
import com.example.myapp2025.data.models.Person
import com.example.myapp2025.data.models.User
import com.example.myapp2025.data.repository.PersonRepository
import com.example.myapp2025.data.repository.UserRepository
import com.example.myapp2025.ui.viewmodels.PersonViewModel
import com.example.myapp2025.ui.viewmodels.PersonViewModelFactory
import com.example.myapp2025.ui.viewmodels.UserViewModel
import com.example.myapp2025.ui.viewmodels.UserViewModelFactory
import kotlinx.coroutines.launch
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextIdentificacion: EditText
    private lateinit var editTextNombres: EditText
    private lateinit var editTextApellidos: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextFechaNacimiento: EditText
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button

    private lateinit var personViewModel: PersonViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar vistas
        editTextIdentificacion = findViewById(R.id.editTextIdentificacion)
        editTextNombres = findViewById(R.id.editTextNombres)
        editTextApellidos = findViewById(R.id.editTextApellidos)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento)
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

        editTextFechaNacimiento.setOnClickListener {
            showDatePicker()
        }

        // Inicializar ViewModels con Factory
        val database = AppDatabase.getDatabase(applicationContext)
        val personRepository = PersonRepository(database.personDao())
        val userRepository = UserRepository(database.userDao())

        personViewModel = ViewModelProvider(
            this,
            PersonViewModelFactory(personRepository)
        )[PersonViewModel::class.java]

        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        )[UserViewModel::class.java]

        buttonRegister.setOnClickListener {
            val identificacion = editTextIdentificacion.text.toString()
            val nombres = editTextNombres.text.toString()
            val apellidos = editTextApellidos.text.toString()
            val email = editTextEmail.text.toString()
            val telefono = editTextTelefono.text.toString()
            val direccion = editTextDireccion.text.toString()
            val fechaNacimiento = editTextFechaNacimiento.text.toString()
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (
                identificacion.isBlank() || nombres.isBlank() || apellidos.isBlank() ||
                email.isBlank() || telefono.isBlank() || direccion.isBlank() ||
                fechaNacimiento.isBlank() || username.isBlank() || password.isBlank()
            ) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val persona = Person(
                identificacion = identificacion,
                nombres = nombres,
                apellidos = apellidos,
                email = email,
                telefono = telefono,
                direccion = direccion,
                fechanacimiento = fechaNacimiento,
                avatar = null
            )

            lifecycleScope.launch {
                try {
                    Log.d(TAG, "Insertando persona: $persona")
                    val personaId = personViewModel.insertAndGetId(persona)
                    Log.d(TAG, "Persona ID insertado: $personaId")

                    val user = User(
                        username = username,
                        password = password

                    )


                    userViewModel.registerUser(user) { success ->
                        if (success) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registro exitoso",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Error al registrar usuario",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error en el registro: ${e.message}", e)
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error al registrar: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val fecha = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            editTextFechaNacimiento.setText(fecha)
        }, year, month, day)

        datePicker.show()
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}
