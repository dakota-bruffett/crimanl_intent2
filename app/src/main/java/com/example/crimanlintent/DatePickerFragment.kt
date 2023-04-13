package com.example.crimanlintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*
import kotlin.time.Duration.Companion.days

private const val ARG_DATE = "date"
class DatePickerFragment: DialogFragment() {
    interface Callbacks{
        fun onDateSelectedDates(date: Date)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dateListner = DatePickerDialog.OnDateSetListener{
            _:DatePicker,year:Int,month:Int, day :Int ->
            val resultDate : Date = GregorianCalendar(year,month,day).time
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onDateSelectedDates(resultDate)
            }
        }
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar= Calendar.getInstance()
        calendar.time= date
        val initialYear= calendar.get(Calendar.YEAR)
        val initalMonth = calendar.get(Calendar.MONTH)
        val initalDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            dateListner,
            initialYear,
            initalMonth,
            initalDay
        )
    }
    companion object{
        fun newInstance(date: Date):DatePickerFragment{
            val args= Bundle().apply {
                putSerializable(ARG_DATE,date)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }
}