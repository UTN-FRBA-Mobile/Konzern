package utn.frba.mobile.konzern.posts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_post.*
import utn.frba.mobile.konzern.MainActivity
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.ContactActivity
import utn.frba.mobile.konzern.customviews.ToolbarMenuInterface
import utn.frba.mobile.konzern.expenses.ExpensesActivity
import utn.frba.mobile.konzern.profile.ProfileActivity
import utn.frba.mobile.konzern.reservations.ReservationsActivity


class PostActivity : AppCompatActivity(), ToolbarMenuInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        vActivityPostToolbar.setTitle(getString(R.string.posts_toolbar_title))
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

    override fun onMenuContactInfoClicked() {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
        finish()
    }
}
