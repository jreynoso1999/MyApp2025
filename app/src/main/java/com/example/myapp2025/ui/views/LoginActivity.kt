package com.example.myapp2025.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapp2025.R
import com.example.myapp2025.data.database.AppDatabase
import com.example.myapp2025.data.repository.UserRepository
import com.example.myapp2025.ui.viewmodels.UserViewModel
import com.example.myapp2025.ui.viewmodels.UserViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUsername = findViewById<EditText>(R.id.editTextText)
        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonLogin2)

        val database = AppDatabase.getDatabase(applicationContext)
        val userRepository = UserRepository(database.userDao())
        val factory = UserViewModelFactory(userRepository)

        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim().lowercase()
            val password = editTextPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userViewModel.checkUser(username, password) { user ->
                if (user != null) {
                    Log.d("LoginActivity", "Inicio de sesión correcto para: $username con ID: ${user.id}")

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userId", user.id) // Aquí se envía el userId
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
        }

        buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
