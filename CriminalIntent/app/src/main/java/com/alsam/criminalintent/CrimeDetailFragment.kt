import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.alsam.criminalintent.Crime
import com.alsam.criminalintent.databinding.FragmentCrimeDetailBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

class CrimeDetailFragment : Fragment() {
    private lateinit var crime: Crime
    private var _binding: FragmentCrimeDetailBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a Date instance with the time set to midnight (00:00:00)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        crime = Crime(
            id = UUID.randomUUID(),
            title = "",
            date = calendar.time,
            isSolved = false,
            isSerious = false,
            requiresPolice = true
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                crime = crime.copy(title = text.toString())
            }

            // Format the date using SimpleDateFormat and display it without time zone information
            val dateWithoutTime = Calendar.getInstance()
            dateWithoutTime.time = crime.date
            dateWithoutTime.set(Calendar.HOUR_OF_DAY, 0)
            dateWithoutTime.set(Calendar.MINUTE, 0)
            dateWithoutTime.set(Calendar.SECOND, 0)

            // Set the SimpleDateFormat's time zone to UTC to remove time zone information
            val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")

            crimeDate.text = dateFormat.format(dateWithoutTime.time)

            crimeDate.isEnabled = false

            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crime = crime.copy(isSolved = isChecked)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
