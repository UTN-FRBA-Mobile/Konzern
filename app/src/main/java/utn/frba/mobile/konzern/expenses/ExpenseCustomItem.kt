package utn.frba.mobile.konzern.expenses

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.custom_expenses_item.view.*
import utn.frba.mobile.konzern.R

class ExpenseCustomItem (context: Context) : ConstraintLayout(context) {

    init {
        View.inflate(context, R.layout.custom_expenses_item, this)
        vExpensesItemDownloadButton.setOnClickListener { onDownloadClick() }
    }

    fun loadActualExpense(expense: Expenses) {
        vExpensesItemMonth.text = expense.month
        vExpensesItemAmountNumber.text = expense.amount
        vExpensesItemExpireDate.text = expense.expireDate
    }

    private fun onDownloadClick(){
        Toast.makeText(context, "Botón de descarga presionado", Toast.LENGTH_SHORT).show()
    }
}