package utn.frba.mobile.konzern.expenses.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_expenses_item.view.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.expenses.Expenses

class ExpensesAdapter(private val expensesList: ArrayList<Expenses>) : RecyclerView.Adapter<ExpensesAdapter.ExpensesItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesItem {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.custom_expenses_item, parent, false)
        return ExpensesItem(view)
    }

    override fun getItemCount(): Int = expensesList.size

    override fun onBindViewHolder(expense: ExpensesItem, position: Int) {
        expense.run {
            month.text = expensesList[position].month
            amount.text = expensesList[position].amount
            expireDate.text = expensesList[position].expireDate
        }
    }

    class ExpensesItem(view: View) : RecyclerView.ViewHolder(view) {
        var month = view.vExpensesItemMonth
        var amount = view.vExpensesItemAmountNumber
        var expireDate = view.vExpensesItemExpireDate
    }
}