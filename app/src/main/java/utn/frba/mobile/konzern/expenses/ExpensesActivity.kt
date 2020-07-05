package utn.frba.mobile.konzern.expenses

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.FileProvider
import utn.frba.mobile.konzern.BaseActivity
import utn.frba.mobile.konzern.BuildConfig
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.expenses.ui.ExpensesFragment
import java.io.File


class ExpensesActivity : BaseActivity(), ExpensesFragment.ExpensesFragmentView{
    private lateinit var expensesFragment: ExpensesFragment

    override fun getContentLayout(): Int {
        return R.layout.content_expenses
    }

    override fun getViewTitle(): String? {
        return getString(R.string.home_expenses)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            expensesFragment = ExpensesFragment.newInstance()
        }
    }

    override fun errorGettingExpensesInfo() {
        Toast.makeText(this, R.string.expenses_error_getting_info, Toast.LENGTH_SHORT).show()
    }

    override fun errorGettingConsortiumInfo() {
        Toast.makeText(this, R.string.contact_error_getting_info, Toast.LENGTH_SHORT).show()
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
}