package com.example.crimanlintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.Date
import java.util.UUID

data class Crime(val id:UUID= UUID.randomUUID(),
                        var title: String= "",
                            var date: Date= Date(),
                            var isSolved:Boolean = false)//datee code
private lateinit var crime: Crime


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
        override fun afterTextChanged(sequence:Editable?)

        TitleFiled.addTextChangedListener(TitleWatcher)
        solvedCheckBox.apply {
            setOnCheckedChangeListener{_, isChecked ->
                crime.isSolved = isChecked
            }
                //code for overrides and the amount of  for text changes
        }
    }



}