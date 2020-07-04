package utn.frba.mobile.konzern.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_post.vActivityPostToolbar
import utn.frba.mobile.konzern.contact.ContactActivity
import utn.frba.mobile.konzern.customviews.ToolbarMenuInterface
import utn.frba.mobile.konzern.expenses.ExpensesActivity
import utn.frba.mobile.konzern.profile.ProfileActivity
import utn.frba.mobile.konzern.reservations.ReservationsActivity
import utn.frba.mobile.konzern.utils.BaseViewModel

abstract class BaseActivity : AppCompatActivity(), ToolbarMenuInterface {
    protected lateinit var viewModel: BaseViewModel
    protected abstract fun getViewTitle(): String?
    protected abstract fun getCustomViewModel(): BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentLayout())

        getViewTitle()?.let { vActivityPostToolbar.setTitle(it) }
        viewModel = getCustomViewModel()
        viewModel.showProgressIndicator.observe(this, Observer {
            if(it!!){
                showProgress()
            }else{
                hideProgress()
            }
        })
    }

    abstract fun getContentLayout(): Int

    abstract fun showProgress()

    abstract fun hideProgress()

    override fun onMenuMyProfileClicked() {
        val intent = Intent(this, ProfileActivity::class.java)
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

    override fun onMenuExpensesClicked() {
        val intent = Intent(this, ExpensesActivity::class.java)
        startActivity(intent)
        finish()
    }
}
