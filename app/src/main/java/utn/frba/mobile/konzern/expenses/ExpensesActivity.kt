package utn.frba.mobile.konzern.expenses

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import utn.frba.mobile.konzern.BuildConfig
import utn.frba.mobile.konzern.R
import kotlinx.android.synthetic.main.activity_expenses.*
import utn.frba.mobile.konzern.contact.ContactActivity
import utn.frba.mobile.konzern.customviews.ToolbarMenuInterface
import utn.frba.mobile.konzern.expenses.ui.ExpensesFragment
import utn.frba.mobile.konzern.profile.ProfileActivity
import utn.frba.mobile.konzern.reservations.ReservationsActivity
import java.io.File


class ExpensesActivity : AppCompatActivity(), ExpensesFragment.ExpensesFragmentView, ToolbarMenuInterface {

    private lateinit var expensesFragment: ExpensesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)
        vActivityExpensesToolbar.setTitle(getString(R.string.home_expenses))
        if(savedInstanceState == null) {
            expensesFragment = ExpensesFragment.newInstance()
        }
    }

    override fun errorGettingExpensesInfo() {
        Toast.makeText(this, R.string.expenses_error_getting_info, Toast.LENGTH_SHORT).show()
    }

    override fun downloadPDFSuccess(path: String) {
        if(!path.isEmpty()) {
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()

            val target = Intent(Intent.ACTION_VIEW)
            target.type = "application/pdf"
            target.data = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", File(path))
            target.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val intent = Intent.createChooser(target, "Open File")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
            }
        }
    }

    override fun onMenuMyProfileClicked() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onMenuExpensesClicked() {}

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