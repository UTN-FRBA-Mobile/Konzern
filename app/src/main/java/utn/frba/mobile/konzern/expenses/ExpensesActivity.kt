package utn.frba.mobile.konzern.expenses

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import utn.frba.mobile.konzern.BuildConfig
import utn.frba.mobile.konzern.R
import java.io.File


class ExpensesActivity : AppCompatActivity(), ExpensesFragment.ExpensesFragmentView {

    private lateinit var expensesFragment: ExpensesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)
        if(savedInstanceState == null) {
            expensesFragment = ExpensesFragment.newInstance()
        }
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
                // Instruct the user to install a PDF reader here, or something
            }
        }
    }


}