package com.example.crimanlintent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class CrimeDetailViewModel(): ViewModel() {
    private val crimeLiveData= MutableLiveData<UUID>()
    private val crimeRespository = CrimeRespository.get()
}