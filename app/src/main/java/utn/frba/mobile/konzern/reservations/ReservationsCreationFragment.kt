package utn.frba.mobile.konzern.reservations

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.reservations_creation_fragment.*
import utn.frba.mobile.konzern.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ReservationsCreationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reservations_creation_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        view.findViewById<Button>(R.id.vReservationsFormCreateBtn).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
*/

        vReservationsFormCreateBtn.setOnClickListener {
            //modificarViewModel()
            requireActivity().onBackPressed()
            //todo: agregar logica de guardar la reserva
        }


        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //Button to show DatePicker
        vReservationsForm2Bton.setOnClickListener {
            val dpd = DatePickerDialog(requireContext() , DatePickerDialog.OnDateSetListener { view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                vReservationsFormDate.setText("$mDay/$mMonth/$mYear")
            }, year, month, day)

            dpd.show()
        }

        //Button to show TimePicker
        vReservationsForm3Bton.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                //set Text
                vReservationsFormTime1.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }

            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }
}
