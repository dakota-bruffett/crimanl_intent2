package com.example.crimanlintent

import android.app.Application

class CrimanlntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRespository.initialize(this)
    }
}