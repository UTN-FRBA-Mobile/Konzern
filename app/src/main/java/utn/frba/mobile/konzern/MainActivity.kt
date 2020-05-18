package utn.frba.mobile.konzern

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import utn.frba.mobile.konzern.reservations.ReservationsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButtonsListeners()
    }

    private fun setButtonsListeners(){
        btn_home_reservations.setOnClickListener{
            onGotToPosts()
        }
    }

    private fun onGotToPosts(){
        val intent = Intent(this, ReservationsActivity::class.java)
        this.startActivity(intent)
    }
}
