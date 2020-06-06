package utn.frba.mobile.konzern.expenses

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.expenses_fragment_layout.*
import kotlinx.android.synthetic.main.expenses_item.view.*
import org.json.JSONArray
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.expenses.adapter.ExpensesAdapter
import utn.frba.mobile.konzern.expenses.adapter.ExpensesPdfAdapter

class ExpensesFragment : Fragment() {

    private var expensesPdfAdapter: ExpensesPdfAdapter? = null
    private var expensesView: ExpensesFragmentView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ExpensesFragmentView) {
            expensesView = context
        } else {
            throw RuntimeException("$context must be LoginFragmentView")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.expenses_fragment_layout, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: Este JSONArray debe ser la respuesta del servicio que traiga de la base las expensas
        var expensesJson = JSONArray("""[{"amount": "1644.20", "month":"Mayo", "exp_date":"11/06/2020", "apartment":"1 C"},
                                {"amount": "1582.20", "month":"Abril", "exp_date":"12/05/2020", "apartment":"1 C"},
                                {"amount": "1350.20", "month":"Marzo", "exp_date":"16/04/2020", "apartment":"1 C"},
                                {"amount": "1350.20", "month":"Febrero", "exp_date":"15/03/2020", "apartment":"1 C"},
                                {"amount": "1032.20", "month":"Enero", "exp_date":"10/02/2020", "apartment":"1 C"}]""")
        val expensesList = parseExpenseList(expensesJson)

        expensesPdfAdapter = ExpensesPdfAdapter()
        expensesPdfAdapter?.permitPDFFile(this.requireActivity())

        val viewManager = LinearLayoutManager(this.requireActivity())
        val adapter = ExpensesAdapter(expensesList.subList(1,expensesList.size), expensesView, expensesPdfAdapter, context)

        vExpensesRecyclerView.apply {
            this.layoutManager = viewManager
            this.adapter = adapter
        }

        vItemLastExpense.apply {
            this.vExpensesItemMonthValue.text = expensesList[0].month
            this.vExpensesItemAmountValue.text = expensesList[0].amount
            this.vExpensesItemExpirationDateValue.text = expensesList[0].expirationDate
            this.vExpensesItemDownloadButton.setOnClickListener { expensesPdfAdapter?.createPDFFile (expensesList[0], context); expensesView?.downloadPDFSuccess() }
        }
    }

    private fun parseExpenseList(expensesJson: JSONArray) : List<Expenses> {
        val expensesList = mutableListOf<Expenses>()

        for (i in 0 until expensesJson.length()) {
            expensesList.add(
                Expenses(
                    expensesJson.getJSONObject(i).getString("month"),
                    expensesJson.getJSONObject(i).getString("amount"),
                    expensesJson.getJSONObject(i).getString("exp_date"),
                    expensesJson.getJSONObject(i).getString("apartment")
                ))
        }
        return expensesList.toList()
    }

    companion object {
        fun newInstance() = ExpensesFragment()
    }

    interface ExpensesFragmentView {
        fun downloadPDFSuccess()
    }


}