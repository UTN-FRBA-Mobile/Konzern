package utn.frba.mobile.konzern

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
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
    protected open val startsNewFlow: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setActionBar()
    }

    private fun setModuleContentView(){
        val vi = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = vi.inflate(getContentLayout(), null)
        v.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        vActivityBaseContent.removeAllViews()
        vActivityBaseContent.addView(v)
    }

    private fun setActionBar(){
        setModuleContentView()
        vTopAppBar.title = getViewTitle()
        setSupportActionBar(vTopAppBar)
        vNavigation.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.nav_home -> {
                    goToHome()
                    false
                }
                R.id.nav_profile -> {
                    goToProfile()
                    false
                }
                R.id.nav_expenses -> {
                    goToExpenses()
                    false
                }
                R.id.nav_booking -> {
                    goToBooking()
                    false
                }
                R.id.nav_contact -> {
                    goToContactInfo()
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

    fun changeModule(intent: Intent?){
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START)
        if(intent?.component?.className == this.javaClass.name) return
        if(intent != null) startActivity(intent)
        if(startsNewFlow) finish()
    }

    fun goToHome() {
        changeModule(null)
    }

    fun goToProfile() {
        changeModule(Intent(this, ProfileActivity::class.java))
    }

    fun goToBooking() {
        changeModule(Intent(this, ReservationsActivity::class.java))
    }

    fun goToContactInfo() {
        changeModule(Intent(this, ContactActivity::class.java))
    }

    fun goToExpenses() {
        changeModule(Intent(this, ExpensesActivity::class.java))
    }
}