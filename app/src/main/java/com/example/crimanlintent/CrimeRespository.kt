package com.example.crimanlintent

import android.content.Context
import android.content.LocusId
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.io.File
import java.util.UUID
import java.util.concurrent.Executors

private const val DATABASE_NAME="crime-database"

class CrimeRespository private constructor(context: Context){

    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration_1_2)
        .build()
    private val crimeDao = database.crimeDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val filesDir = context.applicationContext.filesDir

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()
    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    fun updateCrime(crime: Crime){
        executor.execute{
            crimeDao.addCrime(crime)
        }

    }
    fun addCrime(crime: Crime){
        executor.execute{
            crimeDao.addCrime(crime)
        }
    }
    fun getPhotoFilename(crime: Crime): File= File(filesDir,crime.photoFileName)

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