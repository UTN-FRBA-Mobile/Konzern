package utn.frba.mobile.konzern.contact

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_base_with_toolbar.*
import utn.frba.mobile.konzern.MainActivity
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.ui.ContactFragment
import utn.frba.mobile.konzern.customviews.ToolbarMenuInterface
import utn.frba.mobile.konzern.expenses.ExpensesActivity
import utn.frba.mobile.konzern.login.LoginActivity
import utn.frba.mobile.konzern.profile.ProfileActivity
import utn.frba.mobile.konzern.reservations.ReservationsActivity

class ContactActivity : AppCompatActivity() , ContactFragment.ContactView, ToolbarMenuInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_with_toolbar)
        vActivityBaseToolbar.setTitle(getString(R.string.contact_toolbar_title))
        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.vActivityContent, ContactFragment.newInstance())
                .commitNow()
        }
    }

    override fun errorGettinContactInfo() {
        Toast.makeText(this, R.string.contact_error_getting_info, Toast.LENGTH_SHORT).show()
    }

    override fun onMenuMyProfileClicked() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

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

    override fun onMenuContactInfoClicked() {}

    override fun onMenuLogoutClicked() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}