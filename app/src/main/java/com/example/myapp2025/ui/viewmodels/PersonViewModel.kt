package com.example.myapp2025.ui.viewmodels

import androidx.lifecycle.*
import com.example.myapp2025.data.models.Person
import com.example.myapp2025.data.repository.PersonRepository
import kotlinx.coroutines.launch

class PersonViewModel(private val repository: PersonRepository) : ViewModel() {

    val persons: LiveData<List<Person>> = repository.getAllPersons()

    fun insert(person: Person) {
        viewModelScope.launch {
            repository.insertPerson(person)
        }
    }

    fun delete(person: Person) {
        viewModelScope.launch {
            repository.deletePerson(person)
        }
    }

    fun updatePerson(person: Person) {
        viewModelScope.launch {
            repository.updatePerson(person)
        }
    }


    // Metodo suspend actualizado correctamente
    suspend fun insertAndGetId(person: Person): Int {
        return repository.insertAndReturnId(person).toInt() // CONVERSIÓN DE Long A Int
    }
}
