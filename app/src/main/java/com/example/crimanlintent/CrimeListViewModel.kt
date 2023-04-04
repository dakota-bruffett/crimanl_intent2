package com.example.crimanlintent

import androidx.lifecycle.ViewModel

class CrimeListViewModel: ViewModel() {

    private val crimeRespository = CrimeRespository.get()
    val crimeListLiveData = crimeRespository.getCrimes()
}