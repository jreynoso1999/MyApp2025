package com.example.myapp2025.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapp2025.data.models.Person

@Dao
interface PersonDao {

    @Update
    suspend fun update(person: Person)

    @Query("SELECT * FROM personas")
    fun getAllPersons(): LiveData<List<Person>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

    @Insert
    suspend fun insertAndReturnId(person: Person): Long

    @Delete
    suspend fun deletePerson(person: Person)
}

