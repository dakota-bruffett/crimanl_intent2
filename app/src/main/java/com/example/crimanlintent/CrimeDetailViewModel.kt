package com.example.crimanlintent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.UUID

class CrimeDetailViewModel(): ViewModel() {
    private val crimeIdLiveData = MutableLiveData<UUID>()
    private val crimeRespository = CrimeRespository.get()

    var crimeLiveData: LiveData<Crime?> =
    Transformations.switchMap(crimeIdLiveData){crimeId ->
    crimeRespository.getCrime(crimeId)
    }
    fun loadCrime(crimeId:UUID){
        crimeIdLiveData.value = crimeId
    }
    fun saveCrime(crime: Crime){
        crimeRespository.updateCrime(crime)
    }
}