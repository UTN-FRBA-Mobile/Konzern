package utn.frba.mobile.konzern.reservations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.reservations_activity.*

import utn.frba.mobile.konzern.R

class ReservationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservations_activity)
        setSupportActionBar(toolbar)
    }

}
