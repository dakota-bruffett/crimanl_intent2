package com.example.crimanlintent

import android.content.Context
import android.content.LocusId
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.UUID

private const val DATABASE_NAME="crime-database"

class CrimeRespository private constructor(context: Context){

    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val crimeDao = database.crimeDao()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()
    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    companion object{
        private var INSTANCE:CrimeRespository? = null
            fun initialize(context: Context){
                if (INSTANCE== null){
                    INSTANCE= CrimeRespository(context)
                }
            }
        fun get(): CrimeRespository{
            return INSTANCE?:
            throw IllegalStateException("CrimeRepository must be initialized ")
        }


    }

}