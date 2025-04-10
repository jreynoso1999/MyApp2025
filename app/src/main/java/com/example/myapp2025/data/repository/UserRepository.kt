package com.example.myapp2025.data.repository

import com.example.myapp2025.data.database.UserDao
import com.example.myapp2025.data.models.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun checkUser(username: String, password: String): User? {
        return userDao.login(username, password)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUser()
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}
