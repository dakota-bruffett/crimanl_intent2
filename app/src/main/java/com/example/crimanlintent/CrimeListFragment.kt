package com.example.crimanlintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val TAG = "CrimeListFragment"

class CrimeListFragment: Fragment() {
    private lateinit var CrimeRecylerView: RecyclerView
    private var adaptor: CrimeAdaptor? = null
    private val CrimeViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total Crimes: $ {CrimeViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        CrimeRecylerView =
            view.findViewById(R.id.Crime_recycle_view,) as RecyclerView
        CrimeRecylerView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return view
    }


    private fun updateUI() {

        val crimes = CrimeViewModel.crimes
        adaptor = CrimeAdaptor(crimes)
        CrimeRecylerView.adapter = adaptor
    }


    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }

    }
    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var crime: Crime
        val titleTextView: TextView = itemView.findViewById(R.id.CrimeTitle)
        val dateTextView: TextView = itemView.findViewById(R.id.crimeDate)

        init {
            itemView.setOnClickListener(this)

        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
        }

        override fun onClick(v: View) {
            Toast.makeText(context, "${crime.title}passed!", Toast.LENGTH_SHORT)
                .show()
        }


    }

        private inner class CrimeAdaptor(var crimes: List<Crime>) :
            RecyclerView.Adapter<CrimeHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
                val view = layoutInflater.inflate(R.layout.activity_crimes, parent, false)
                return CrimeHolder(view)
            }


            override fun getItemCount() = crimes.size
            override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
                val crime = crimes[position]
                holder.apply {
                    holder.bind(crime)
                }

            }

        }

}