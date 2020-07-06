package utn.frba.mobile.konzern.reservations

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.reservations_creation_fragment.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.reservations.model.Amenity
import utn.frba.mobile.konzern.reservations.repository.AmenityRepository
import java.text.SimpleDateFormat
import java.util.*
import android.R as rAndroid;
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import utn.frba.mobile.konzern.login.BaseSignUpFragment
import utn.frba.mobile.konzern.reservations.repository.ReservationRepository
import java.lang.RuntimeException

class ReservationsCreationFragment : Fragment() {

    private val viewModel: ReservationViewModel = ReservationViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reservations_creation_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vReservationsFormCreateBtn.setOnClickListener {
            createReservation()
        }

        viewModel.amenities.observe(viewLifecycleOwner, Observer<List<Amenity>> { it ->
            val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, it.map { it.name})
            vReservationsSelect.adapter = adapter
        })

        viewModel.saveEvent.observe(viewLifecycleOwner, Observer {
            if (viewModel.amenityReserved) {
                Toast.makeText(this.requireActivity(), "El amenity ya está reservado", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.saveReservation(
                    vReservationsSelect.selectedItem.toString(),
                    vReservationsDay.text.toString(),
                    vReservationsHour.selectedItem.toString()
                )
                findNavController().navigateUp()
            }
        })

        viewModel.initAmenities()
        initTimeOptions()

        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //Button to show DatePicker
        vReservationsForm2Bton.setOnClickListener {
            val dpd = DatePickerDialog(requireContext() , DatePickerDialog.OnDateSetListener { view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                vReservationsDay.setText("$mDay/$mMonth/$mYear")
            }, year, month, day)

            dpd.show()
        }

        //Button to show TimePicker
    }

    private fun createReservation() {
        if (validateForm()) {
             viewModel.amenityReserved(
                vReservationsSelect.selectedItem.toString(),
                vReservationsDay.text.toString(),
                vReservationsHour.selectedItem.toString()
            )
        }
        else
            Toast.makeText(this.requireActivity(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
    }

    private fun validateForm(): Boolean {
        return (!(vReservationsDay.text.isNullOrEmpty()))
    }

    private fun initTimeOptions(){
        val times = ArrayList<String>()
        repeat(24) {
            times.add(it.toString())
        }
        val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, times)
        vReservationsHour.adapter = adapter
    }

}
