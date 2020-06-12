package utn.frba.mobile.konzern.expenses.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.expenses_item.view.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.expenses.Expenses
import utn.frba.mobile.konzern.expenses.ExpensesFragment.ExpensesFragmentView


class ExpensesAdapter(private var expensesList: List<Expenses>,
                      private val expensesView: ExpensesFragmentView?,
                      private val expensesPdfAdapter: ExpensesPdfAdapter?,
                      private val context: Context?
                    ) : RecyclerView.Adapter<ExpensesAdapter.ExpensesItem>(), Filterable {

    private var expensesFullList = mutableListOf<Expenses>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesItem {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.expenses_item, parent, false)
        if(expensesFullList.isEmpty())
            expensesFullList.addAll(0, expensesList)
        return ExpensesItem(view)
    }

    override fun getItemCount(): Int = expensesList.size

    override fun onBindViewHolder(expense: ExpensesItem, position: Int) {
        expense.run {
            month.text = expensesList[position].month
            amount.text = expensesList[position].amount
            expirationDate.text = expensesList[position].expirationDate
            downloadButton.setOnClickListener { val path = expensesPdfAdapter?.createPDFFile (expensesList[position], context); expensesView?.downloadPDFSuccess(path.toString()) }
        }
    }

    class ExpensesItem(view: View) : RecyclerView.ViewHolder(view) {
        var month = view.vExpensesItemMonthValue
        var amount = view.vExpensesItemAmountValue
        var expirationDate = view.vExpensesItemExpirationDateValue
        var downloadButton = view.vExpensesItemDownloadButton
    }

    override fun getFilter(): Filter {
        return expensesFilter
    }

    private val expensesFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var expensesFilteredList = mutableListOf<Expenses>()
            var filterResults = FilterResults()

            if (constraint == null || constraint.isEmpty()) {
                expensesFilteredList.addAll(0, expensesFullList)
            } else {
                var filterPattern = constraint.toString().toUpperCase().trim()

                for ( i : Expenses in expensesFullList) {
                    if (i.month.toUpperCase().trim().contains(filterPattern)) {
                        expensesFilteredList.add(i)
                    }
                }
            }

            filterResults.values = expensesFilteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            expensesList = results?.values as List<Expenses>
            notifyDataSetChanged()
        }


    }

}