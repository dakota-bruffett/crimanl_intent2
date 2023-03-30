package com.example.crimanlintent

import androidx.lifecycle.LiveData
import java.util.*

@Dao
interface CrimeDao{

    @Query("SELECT*FROM crime")
    fun getCrimes(): LiveData<List<Crime>>
    @Query("SELECT*FROM crime Where id=(id)")
    fun getCrime(id: UUID): LiveData<Crime?>
}