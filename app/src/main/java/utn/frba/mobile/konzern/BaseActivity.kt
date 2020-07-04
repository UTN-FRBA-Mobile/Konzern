package utn.frba.mobile.konzern

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_base.*
import utn.frba.mobile.konzern.contact.ContactActivity
import utn.frba.mobile.konzern.expenses.ExpensesActivity
import utn.frba.mobile.konzern.profile.ProfileActivity
import utn.frba.mobile.konzern.reservations.ReservationsActivity

abstract class BaseActivity: AppCompatActivity() {
    abstract fun getContentLayout(): Int
    protected abstract fun getViewTitle(): String?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        setModuleContentView()
        vTopAppBar.title = getViewTitle()
        setSupportActionBar(vTopAppBar)
        vNavigation.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.nav_profile -> {
                    goToProfile()
                    false
                }
                R.id.nav_expenses -> {
                    goToExpensesClicked()
                    false
                }
                R.id.nav_booking -> {
                    goToBooking()
                    false
                }
                R.id.nav_contact -> {
                    ogoToContactInfo()
                    false
                }
                R.id.nav_logout -> {
                    Toast.makeText(this, getString(R.string.custom_toolbar_logout_item), Toast.LENGTH_SHORT).show()
                    false
                }
                else -> false
            }
        }
    }

    @SuppressLint("InflateParams")
    fun setModuleContentView(){
        val vi = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = vi.inflate(getContentLayout(), null)
        v.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        vActivityBaseContent.removeAllViews()
        vActivityBaseContent.addView(v)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun showProgress(){
        vActivityBaseContent.visibility = View.GONE
        vProgressBarLayout.visibility = View.VISIBLE
    }

    fun hideProgress(){
        vProgressBarLayout.visibility = View.GONE
        vActivityBaseContent.visibility = View.VISIBLE
    }

    fun goToProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goToBooking() {
        val intent = Intent(this, ReservationsActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun ogoToContactInfo() {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goToExpensesClicked() {
        val intent = Intent(this, ExpensesActivity::class.java)
        startActivity(intent)
        finish()
    }
}