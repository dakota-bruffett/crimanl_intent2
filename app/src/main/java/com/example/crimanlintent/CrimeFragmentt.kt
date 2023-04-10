import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crimanlintent.*
import java.util.*

private lateinit var crime: Crime

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "crime_id"
class CrimeFragment: Fragment() {

    private lateinit var TitleFiled: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
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
    fun newInstance(crimeId:UUID): CrimeFragment{
        val args = Bundle().apply {
            putSerializable(ARG_CRIME_ID,crimeId)
        }
        return CrimeFragment().apply {
            arguments = args
        }
    }

}