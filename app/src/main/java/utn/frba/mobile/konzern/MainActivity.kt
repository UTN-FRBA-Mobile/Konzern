package utn.frba.mobile.konzern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import utn.frba.mobile.konzern.expenses.ExpensesActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonsListeners()
    }

    private fun setButtonsListeners(){
        btn_home_expenses.setOnClickListener{
            onGotToPosts()
        }
    }

    private fun onGotToPosts(){
        val intent = Intent(this, ExpensesActivity::class.java)
        this.startActivity(intent)
    }
}
