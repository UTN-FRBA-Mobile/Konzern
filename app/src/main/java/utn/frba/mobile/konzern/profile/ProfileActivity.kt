package utn.frba.mobile.konzern.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_profile.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.ContactActivity
import utn.frba.mobile.konzern.customviews.ToolbarMenuInterface
import utn.frba.mobile.konzern.expenses.ExpensesActivity
import utn.frba.mobile.konzern.profile.ui.profile.ProfileFragment
import utn.frba.mobile.konzern.reservations.ReservationsActivity

class ProfileActivity : AppCompatActivity(), ProfileFragment.ProfileFragmentView, ToolbarMenuInterface {

    private lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        vActivityProfileToolbar.setTitle(getString(R.string.profile_toolbar_title))
        showProgress()
        if(savedInstanceState == null) {
            profileFragment = ProfileFragment.newInstance()
        }
    }

    override fun onMenuMyProfileClicked() {}

    override fun onMenuExpensesClicked() {
        val intent = Intent(this, ExpensesActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onMenuSeeReservationsClicked() {
        val intent = Intent(this, ReservationsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onToolbarLogoClicked() {
        finish()
    }

    override fun onMenuContactInfoClicked() {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showProgress(){
        vActivityContentProfile.visibility = View.GONE
        vProfileProgressBarLayout.visibility = View.VISIBLE
    }

    override fun hideProgress(){
        vProfileProgressBarLayout.visibility = View.GONE
        vActivityContentProfile.visibility = View.VISIBLE
    }

}
