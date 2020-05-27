package utn.frba.mobile.konzern

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

import utn.frba.mobile.konzern.posts.PostActivity
import utn.frba.mobile.konzern.profile.ProfileActivity
import utn.frba.mobile.konzern.reservations.ReservationsActivity
import utn.frba.mobile.konzern.expenses.ExpensesActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButtonsListeners()
    }

    private fun setButtonsListeners(){
        btn_home_posts.setOnClickListener{
            onGotToPosts()
        }
        btn_home_profile.setOnClickListener{
            onGotToProfile()
        }
        btn_home_reservations.setOnClickListener{
            onGotToReservations()
        }
        btn_home_expenses.setOnClickListener{
            onGotToExpenses()
        }
    }

    private fun onGotToPosts(){
        val intent = Intent(this, PostActivity::class.java)
        this.startActivity(intent)
    }

    private fun onGotToProfile(){
        val intent = Intent(this, ProfileActivity::class.java)
        this.startActivity(intent)
    }

    private fun onGotToReservations(){
        val intent = Intent(this, ReservationsActivity::class.java)
        this.startActivity(intent)
    }

    private fun onGotToExpenses(){
        val intent = Intent(this, ExpensesActivity::class.java)
        this.startActivity(intent)
    }
}
