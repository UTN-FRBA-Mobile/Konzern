package utn.frba.mobile.konzern.expenses.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.expenses_item.view.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.expenses.Expenses
import utn.frba.mobile.konzern.expenses.ExpensesFragment.*

class ExpensesAdapter(private val expensesList: List<Expenses>, private val expensesView: ExpensesFragmentView?, private val expensesPdfAdapter: ExpensesPdfAdapter?, private val context: Context?) : RecyclerView.Adapter<ExpensesAdapter.ExpensesItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesItem {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.expenses_item, parent, false)
        return ExpensesItem(view)
    }

    override fun getItemCount(): Int = expensesList.size

    override fun onBindViewHolder(expense: ExpensesItem, position: Int) {
        expense.run {
            month.text = expensesList[position].month
            amount.text = expensesList[position].amount
            expirationDate.text = expensesList[position].expirationDate
            downloadButton.setOnClickListener { expensesPdfAdapter?.createPDFFile (expensesList[position], context); expensesView?.downloadPDFSuccess() }
        }
    }

    class ExpensesItem(view: View) : RecyclerView.ViewHolder(view) {
        var month = view.vExpensesItemMonthValue
        var amount = view.vExpensesItemAmountValue
        var expirationDate = view.vExpensesItemExpirationDateValue
        var downloadButton = view.vExpensesItemDownloadButton
    }
}