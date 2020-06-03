package utn.frba.mobile.konzern.expenses

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import utn.frba.mobile.konzern.R


class ExpensesActivity : AppCompatActivity(), ExpensesFragment.ExpensesFragmentView {

    private lateinit var expensesFragment: ExpensesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)
        if(savedInstanceState == null) {
            expensesFragment = ExpensesFragment.newInstance()
        }
    }

    override fun downloadPDFSuccess() {
        Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
    }


}