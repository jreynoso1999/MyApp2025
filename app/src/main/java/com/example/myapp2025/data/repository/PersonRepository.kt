package com.example.myapp2025.data.repository

import androidx.lifecycle.LiveData
import com.example.myapp2025.data.database.PersonDao
import com.example.myapp2025.data.models.Person

class PersonRepository(private val personDao: PersonDao) {

    fun getAllPersons(): LiveData<List<Person>> = personDao.getAllPersons()

    suspend fun insertPerson(person: Person) = personDao.insertPerson(person)

    suspend fun updatePerson(person: Person) {
        personDao.update(person)
    }

    suspend fun deletePerson(person: Person) = personDao.deletePerson(person)

    suspend fun insertAndReturnId(person: Person): Int {
        return personDao.insertAndReturnId(person).toInt()
    }

}
