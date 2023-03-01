package com.example.crimanlintent

import androidx.lifecycle.ViewModel

class CrimeViewModel: ViewModel() {
    var crimes = mutableListOf<Crimes>()
    init {
        for(i in 0 until 100){
            val crime = Crime()
            crime.title= "Crime#$i"
            crime.isSolved= i % 2 == 0
            crimes += crime
        }
    }
}