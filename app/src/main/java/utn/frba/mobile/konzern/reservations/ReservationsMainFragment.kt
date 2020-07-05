package utn.frba.mobile.konzern.reservations

import android.hardware.TriggerEventListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_post_main.*
import kotlinx.android.synthetic.main.reservations_main_fragment.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.adapter.ItemPostAdapter
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.reservations.model.Reservation
import androidx.lifecycle.Observer
import utn.frba.mobile.konzern.reservations.adapter.ReservationAdapter
import java.util.*

class ReservationsMainFragment : Fragment() {

    private val viewModel: ReservationViewModel = ReservationViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reservations_main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initAmenities()

        viewModel.dayReservations.observe(viewLifecycleOwner, Observer<List<Reservation>> {
            rvReservationsCards.adapter = ReservationAdapter(it, viewModel)
        })

        vReservationsCalendar.setOnDateChangeListener { _, year, month, day -> fetchDayReservations(year, month, day) }
        view.findViewById<Button>(R.id.vReservationsMainToCreation).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        initDayReservations()
    }

    private fun initDayReservations(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        fetchDayReservations(year, month, day)
    }

    private fun fetchDayReservations(year: Int, month: Int, day: Int) {
        var date = "$day/$month/$year"
        viewModel.initDayReservations(date)
    }
}
