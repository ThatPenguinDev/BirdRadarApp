import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.SeekBar
import android.widget.TextView
import android.widget.ToggleButton
import com.example.loginpage.R

class FilterFragment : Fragment() {

    private lateinit var unitToggle: ToggleButton
    private lateinit var selectedRadiusText: TextView
    private lateinit var radiusSeekBar: SeekBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_filter, container, false)

        // Initialize views
        unitToggle = view.findViewById(R.id.unitToggle)
        selectedRadiusText = view.findViewById(R.id.selectedRadiusText)
        radiusSeekBar = view.findViewById(R.id.radiusSeekBar)

        // Set initial unit text
        updateRadiusText(0)

        // Set up SeekBar listener
        radiusSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateRadiusText(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set up ToggleButton listener
        unitToggle.setOnCheckedChangeListener { _, _ ->
            updateRadiusText(radiusSeekBar.progress)
        }

        return view
    }

    private fun updateRadiusText(progress: Int) {
        val unit = if (unitToggle.isChecked) "Miles" else "Km"
        selectedRadiusText.text = "Selected Radius: $progress $unit"
    }
}
