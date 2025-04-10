package com.example.myapp2025.ui.views

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp2025.R
import com.example.myapp2025.data.database.AppDatabase
import com.example.myapp2025.ui.viewmodels.UserViewModel
import kotlinx.coroutines.launch

class AdminActivity : AppCompatActivity() {

    private lateinit var listViewUsers: ListView
    private lateinit var buttonDeleteUser: Button
    private lateinit var editTextUsername: EditText

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        listViewUsers = findViewById(R.id.listViewUsers)
        buttonDeleteUser = findViewById(R.id.buttonDeleteUser)
        editTextUsername = findViewById(R.id.editTextUsername)

        loadUsers()

        buttonDeleteUser.setOnClickListener {
            val username = editTextUsername.text.toString()

            if (username.isEmpty()) {
                Toast.makeText(this, "Ingrese el usuario a eliminar", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val users = userViewModel.getAllUsers()
                    val userToDelete = users.find { it.username == username }

                    if (userToDelete != null) {
                        AppDatabase.getDatabase(applicationContext).userDao().deleteUser(userToDelete)
                        Toast.makeText(this@AdminActivity, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                        loadUsers()
                    } else {
                        Toast.makeText(this@AdminActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun loadUsers() {
        lifecycleScope.launch {
            val users = userViewModel.getAllUsers()
            val usernames = users.map { it.username }
            val adapter = ArrayAdapter(this@AdminActivity, android.R.layout.simple_list_item_1, usernames)
            listViewUsers.adapter = adapter
        }
    }
}
