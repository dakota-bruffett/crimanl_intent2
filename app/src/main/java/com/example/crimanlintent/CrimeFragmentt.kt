import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.*
import android.text.format.DateFormat
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crimanlintent.*
import java.net.URI
import java.util.*

private lateinit var crime: Crime

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE="Dialog_Date"
private const val REQUEST_DATE = 0
private const val REQUEST_CONTACT = 1
private const val DATE_FORMAT = "EEE, MM, dd"
class CrimeFragment: Fragment(),DatePickerFragment.Callbacks {

    private lateinit var TitleFiled: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private lateinit var reportButton: Button
    private lateinit var suspectButton: Button
    private val crimeDetailViewModel:CrimeDetailViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeDetailViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        crime = Crime()
        val crimeID: UUID = arguments?.getSerializable(ARG_CRIME_ID)as UUID
        Log.d(TAG, "args bundle crime ID:$crimeID")
        //crime from data base
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_crimes,container,false)
        TitleFiled= view.findViewById(R.id.CrimeTitle) as EditText
        dateButton = view.findViewById(R.id.crimeDate) as Button
        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(crime.date).apply{
                setTargetFragment(this@CrimeFragment,REQUEST_DATE)
                show(this@CrimeFragment.requireFragmentManager(),DIALOG_DATE)
            }
            reportButton = view.findViewById(R.id.crime_Report)
            reportButton.setOnClickListener { Intent(Intent.ACTION_SEND).apply {
                type = "text/Plain"
                putExtra(Intent.EXTRA_TEXT,getCrimeReport())
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    getString(R.string.Crime_subject)
                )
            } .also{intent ->
                val chooserIntent =
                    Intent.createChooser(intent,getString(R.string.Send_Report))
                startActivity(chooserIntent)
            }
            }
            suspectButton =  view.findViewById(R.id.Crime_Suspect)
            suspectButton.apply {
                val pickContactIntent=
                    Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI)
                setOnClickListener {
                    startActivityForResult(pickContactIntent, REQUEST_CONTACT)
                    val packageManager: PackageManager = requireActivity().packageManager
                    val resolvedActivity: ResolveInfo? =
                        packageManager.resolveActivity(pickContactIntent,
                            PackageManager.MATCH_DEFAULT_ONLY)
                    if (resolvedActivity == null){
                        isEnabled = false
                    }
                }
            }
        }
        solvedCheckBox = view.findViewById(R.id.checkBox) as CheckBox
        return view
        // the create view code
        // update here is our buttons and text codes are listed above

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { crime->
                crime?.let {
                    this.crime=crime
                    updateUI()
                }
            }// the on view lifecycle crime tracker model
        )
    }override fun onStop(){
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }

    override fun onDateSelectedDates(date: Date) {
        crime.date = date
        updateUI()
    }

    private fun updateUI() {
        TitleFiled.setText(crime.title)
        dateButton.text= crime.date.toString()
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }
        if(crime.suspect.isNotEmpty()){
            suspectButton.text= crime.suspect
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return
            requestCode == REQUEST_CONTACT && data !=null ->{
                val contactUri: Uri? = data.data
                val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
                val cursor = contactUri?.let {
                    requireActivity().contentResolver
                        .query(it, queryFields, null,null,null)
                }
                cursor?.use {
                    if(it.count == 0){
                        return
                    }
                    val suspect = it.getString(0)
                    crime.suspect = suspect
                    crimeDetailViewModel.saveCrime(crime)
                    suspectButton.text = suspect
                }// to make one our intent code work for the the application while using Uri
            }
        }

    }

    private fun getCrimeReport(): String {
        val solvedString = if (crime.isSolved) {
            getString(R.string.Crime_report_solved)
        } else {
            getString(R.string.Crime_report_unsolved)
        }
        val dateString = DateFormat.format(DATE_FORMAT,crime.date).toString()
        var suspect = if (crime.suspect.isBlank()){
            getString(R.string.Crime_no_suspect)
        }else{
            getString(R.string.Crime_Suspect,crime.suspect)
        }
        return getString(R.string.Crime_report,crime.title,dateString,solvedString,suspect)
    }// the format of getting a crime report



    override fun onStart(){
        super.onStart()
        val TitleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }


            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                crime.title = sequence.toString()
            }


            override fun afterTextChanged(sequence: Editable?) {

            }
        }
        TitleFiled.addTextChangedListener(TitleWatcher)
        solvedCheckBox.apply {
            setOnCheckedChangeListener{_, isChecked ->
                crime.isSolved = isChecked
            }
            //code for overrides and the amount of  for text changes
        }
    }
    companion object
    fun newInstance (crimeId:UUID): CrimeFragment{
        val args = Bundle().apply {
            putSerializable(ARG_CRIME_ID,crimeId)
        }
        return CrimeFragment().apply {
            arguments = args
        }
    }

}