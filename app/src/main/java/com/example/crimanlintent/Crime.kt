package com.example.crimanlintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import java.util.Date
import java.util.UUID

data class Crime(val id:UUID= UUID.randomUUID(),
                        var title: String= "",
                            var date: Date= Date(),
                            var isSolved:Boolean = false)//datee code
private lateinit var crime: Crime

private const val TAG = "CrimeListFragment"
class crimeListFragment: Fragment(){
    private lateinit var CrimeRecylerView: RecyclerView
    private var adaptor: CrimeAdaptor? = null
    private val CrimeViewModel:CrimeViewModel by lazy {
        ViewModelProvider.of(this).get(crimeListFragment::class.java)2
    }
    override fun onCreate(savedInstanceState: Bundle?){
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
        CrimeRecylerView.layoutManager= LinearLayoutManager(context)
        updateUI()
        return view
    }
    private fun updateUI(){
        val crimes = CrimeViewModel.crimes
        adaptor= CrimeAdaptor(crimes)
        CrimeRecylerView.adapter = adaptor
    }

    companion object{
        fun newInstance(): crimeListFragment{
            return crimeListFragment()
        }
        private inner class Crimeholder(view: View)
            :RecyclerView.ViewHolder(view),View.OnClickListener{

            private lateinit var crime: Crime
            val titleTextView: TextView = itemView.findViewById(R.id.CrimeTitle)
            val dateTextView: TextView = itemView.findViewById(R.id.crimeDate)
            init {
                itemView.setOnClickListener (this)

            }
            fun bind(crime: Crime){
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            }
            override fun onClick(v:View){
                Toast.makeText(context, "${crime.title}passed!", Toast.LENGTH_SHORT)
                    .show()
            }

        }
            }

        private inner class CrimeAdaptor(var crimes: List<Crime>)
            : RecyclerView.Adapter<Crimeholder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Crimeholder {
                : Crimeholder{
                    val view = layoutInflater.inflate(R.layout.activity_crimes,parent,false)
                    return Crimeholder(view)
                }

            }

            override fun getItemCount() = crimes.size
            override fun onbindViewholder(holder:Crimeholder,position: Int){
                val crime = crimes(position)
                holder.apply {
                holder.bind(crime)
            }

            }

    }
}
class CrimeFragment: Fragment() {

    private lateinit var TitleFiled: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        crime = Crime()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_crimes,container,false)
        TitleFiled= view.findViewById(R.id.CrimeTitle) as EditText
        dateButton = view.findViewById(R.id.crimeDate) as Button
        dateButton.apply {
            text = crime.date.toString()
            isEnabled = false
        }
        solvedCheckBox = view.findViewById(R.id.checkBox) as CheckBox
        return view
        // the create view code


    }
    override fun onStart(){
        super.onStart()
        val TitleWatcher = object : TextWatcher
        override fun beforeTextChanged(
            sequence: CharSequence?,
            start:Int,
            count:Int,
            after:Int
                ){

        }
        override fun onTextChanged(
            sequence: CharSequence?,
            start:Int,
            count:Int,
            after:Int
        ){
            crime.title= sequence.toString()
        }
        override fun afterTextChanged(sequence:Editable?){

        }

        TitleFiled.addTextChangedListener(TitleWatcher)
        solvedCheckBox.apply {
            setOnCheckedChangeListener{_, isChecked ->
                crime.isSolved = isChecked
            }
                //code for overrides and the amount of  for text changes
        }
    }



}