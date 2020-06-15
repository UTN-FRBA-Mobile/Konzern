package utn.frba.mobile.konzern.expenses.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.expenses_fragment_layout.*
import kotlinx.android.synthetic.main.expenses_item.view.*
import org.json.JSONArray
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.model.Contact
import utn.frba.mobile.konzern.contact.repository.ContactRepository
import utn.frba.mobile.konzern.expenses.model.Expenses
import utn.frba.mobile.konzern.expenses.adapter.ExpensesAdapter
import utn.frba.mobile.konzern.expenses.adapter.ExpensesPdfAdapter
import utn.frba.mobile.konzern.expenses.repository.ExpensesRepository


class ExpensesFragment : Fragment() {

    private var expensesPdfAdapter: ExpensesPdfAdapter? = null
    private var expensesView: ExpensesFragmentView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ExpensesFragmentView) {
            expensesView = context
        } else {
            throw RuntimeException("$context must be ExpensesFragmentView")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.expenses_fragment_layout, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExpensesDataFromRepository()
    }

    private fun renderExpensesData(expensesList: List<Expenses>, consortium: Contact) {
        expensesPdfAdapter = ExpensesPdfAdapter()
        expensesPdfAdapter?.permitPDFFile(this.requireActivity())

        val sortedExpensesList = expensesList.sortedWith(compareByDescending<Expenses> { it.year.toInt() }.thenByDescending{ it.month.toInt() })

        vItemLastExpense.apply {
            this.vExpensesItemMonthValue.text = sortedExpensesList[0].monthLabel
            this.vExpensesItemYearValue.text = sortedExpensesList[0].year
            this.vExpensesItemAmountValue.text = sortedExpensesList[0].amount
            this.vExpensesItemExpirationDateValue.text = sortedExpensesList[0].expirationDate
            this.vExpensesItemDownloadButton.setOnClickListener { val path = expensesPdfAdapter?.createPDFFile (sortedExpensesList[0], consortium, context); expensesView?.downloadPDFSuccess(path.toString()) }
        }

        val viewManager = LinearLayoutManager(this.requireActivity())
        val adapter = ExpensesAdapter(sortedExpensesList.subList(1,sortedExpensesList.size), expensesView, expensesPdfAdapter, consortium, context)

        vExpensesRecyclerView.apply {
            this.layoutManager = viewManager
            this.adapter = adapter
        }

        vLabelPreviousExpenses.setOnTouchListener(OnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN && vLabelPreviousExpenses.compoundDrawables[2] != null && event.rawX >= vLabelPreviousExpenses.right - vLabelPreviousExpenses.compoundDrawables[2].bounds.width()) {
                vSearchPreviousExpenses.visibility = VISIBLE
                vLabelPreviousExpenses.setCompoundDrawables(null, null, null, null)
                true
            }
            false
        })

        vSearchPreviousExpenses.setOnTouchListener(OnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN && vSearchPreviousExpenses.compoundDrawables[2] != null && event.rawX >= vSearchPreviousExpenses.right - vSearchPreviousExpenses.compoundDrawables[2].bounds.width()) {
                vSearchPreviousExpenses.text.clear()
                adapter.filter.filter("")
                hideFilter()
                true
            }
            false
        })

        vSearchPreviousExpenses.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(vSearchPreviousExpenses.text.isEmpty()) {
                    hideFilter()
                }
                adapter.filter.filter(vSearchPreviousExpenses.text)
                val imm: InputMethodManager = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            }
            false
        })

        vExpensesProgressBar.visibility = GONE

    }

    private fun getExpensesDataFromRepository() {
        ExpensesRepository().getExpensesData(object : ExpensesRepository.ExpensesRepositoryInterface {
            override fun onComplete(expensesList: List<Expenses>) {
                getConsortiumDataFromRepository(expensesList)
            }

            override fun onFailure() {
                expensesView?.errorGettingExpensesInfo()
            }
        })
    }

    private fun getConsortiumDataFromRepository(expensesList: List<Expenses>) {
        ContactRepository().getContactData(object: ContactRepository.ContactRepositoryInterface {
            override fun onComplete(consortium: Contact?) {
                if (consortium != null) {
                    renderExpensesData(expensesList, consortium)
                } else {
                    renderExpensesData(expensesList, Contact())
                }
            }

            override fun onFailure() {
                expensesView?.errorGettingConsortiumInfo()
            }
        })
    }

    private fun hideFilter() {
        vSearchPreviousExpenses.visibility = GONE
        vLabelPreviousExpenses.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(R.drawable.ic_search), null)
    }

    companion object {
        fun newInstance() =
            ExpensesFragment()
    }

    interface ExpensesFragmentView {
        fun errorGettingExpensesInfo()

        fun errorGettingConsortiumInfo()

        fun downloadPDFSuccess(path: String)
    }


}