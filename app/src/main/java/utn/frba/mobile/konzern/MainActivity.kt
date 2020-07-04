package utn.frba.mobile.konzern

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import utn.frba.mobile.konzern.customviews.ToolbarMenuInterface
import utn.frba.mobile.konzern.contact.ContactActivity
import utn.frba.mobile.konzern.profile.ProfileActivity
import utn.frba.mobile.konzern.reservations.ReservationsActivity
import utn.frba.mobile.konzern.expenses.ExpensesActivity


class MainActivity : AppCompatActivity(), ToolbarMenuInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vMainActivityToolbar.setTitle(getString(R.string.app_name))
    }

    override fun onMenuMyProfileClicked() {
        onGotToProfile()
    }

    override fun onMenuExpensesClicked() {
        onGotToExpenses()
    }

    override fun onMenuSeeReservationsClicked() {
        onGotToReservations()
    }

    override fun onToolbarLogoClicked() {}

    override fun onMenuContactInfoClicked() {
        onGoToContact()
    }

    private fun onGotToExpenses(){
        val intent = Intent(this, ExpensesActivity::class.java)
        this.startActivity(intent)
    }

    private fun onGoToContact() {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
    }

    private fun onGotToProfile(){
        val intent = Intent(this, ProfileActivity::class.java)
        this.startActivity(intent)
    }

    private fun onGotToReservations(){
        val intent = Intent(this, ReservationsActivity::class.java)
        this.startActivity(intent)
    }
}
