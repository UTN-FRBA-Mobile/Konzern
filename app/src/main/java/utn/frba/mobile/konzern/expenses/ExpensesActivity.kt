package utn.frba.mobile.konzern.expenses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_expenses.*
import org.json.JSONArray
import org.json.JSONObject
import utn.frba.mobile.konzern.R

class ExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses);

        var expenses = JSONArray("""[{"amount": "1582.20", "month":"Abril", "exp_date":"12/05/2020", "apartment":"1 C"},
                                {"amount": "1350.20", "month":"Marzo", "exp_date":"16/04/2020", "apartment":"1 C"},
                                {"amount": "1350.20", "month":"Febrero", "exp_date":"15/03/2020", "apartment":"1 C"}]""");
        var i = 0;
        var coin = resources.getString(R.string.expenses_coin);

        while (i < expenses.length()) {
            var expense = expenses.getJSONObject(i);

            if (i == 0) {
                vTextLastExpenseMonth.append(expense.getString("month"));
                vTextLastExpenseAmount.append(" " + coin + " " + expense.getString("amount"));
                vTextLastExpenseExpirationDate.append(" " + expense.getString("exp_date"));
            } else {
                vTextPreviousExpensesMonth.append(expense.getString("month"));
                vTextPreviousExpensesAmount.append(" " + coin + " " + expense.getString("amount"));
                vTextPreviousExpensesExpirationDate.append(" " + expense.getString("exp_date"));
            }
            i++;
        }
    }
}