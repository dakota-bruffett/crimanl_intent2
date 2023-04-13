package com.example.crimanlintent

import CrimeFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

private const val TAG ="MainActivity"
class MainActivity : AppCompatActivity(),
    CrimeListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_tag)
        if (currentFragment == null) {
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_view_tag,fragment)
                .commit()
            //fragment code
        }
    }

    override fun onCrimeSelected(crimeId: UUID){
        val fragment = CrimeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view_tag,fragment)
            .addToBackStack(null)
            .commit()
        // updated main activity and our support fragments are made
    }
}