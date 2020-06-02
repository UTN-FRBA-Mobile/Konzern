package utn.frba.mobile.konzern.reservations

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.reservations_activity.*

import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.customviews.ToolbarMenuInterface
import utn.frba.mobile.konzern.profile.ProfileActivity

class ReservationsActivity : AppCompatActivity(), ToolbarMenuInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservations_activity)
        vActivityReservationsToolbar.setTitle(getString(R.string.reservations_title_toolbar))
    }

    override fun onMenuMyProfileClicked() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onMenuSeeReservationsClicked() {}

    override fun onToolbarLogoClicked() {
        finish()
    }

}
