package utn.frba.mobile.konzern.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.customviews.ToolbarMenuInterface
import utn.frba.mobile.konzern.reservations.ReservationsActivity

class ProfileActivity : AppCompatActivity(), ToolbarMenuInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        vActivityProfileToolbar.setTitle(getString(R.string.profile_toolbar_title))
    }

    override fun onMenuMyProfileClicked() {}

    override fun onMenuSeeReservationsClicked() {
        val intent = Intent(this, ReservationsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onToolbarLogoClicked() {
        finish()
    }

}
