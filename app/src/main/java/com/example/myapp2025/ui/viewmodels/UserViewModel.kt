package com.example.myapp2025.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp2025.data.models.User
import com.example.myapp2025.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun registerUser(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.registerUser(user)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun checkUser(username: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.checkUser(username, password)
            onResult(user)
        }
    }

    suspend fun getAllUsers(): List<User> = repository.getAllUsers()

    suspend fun deleteUser(user: User) = repository.deleteUser(user)
}
