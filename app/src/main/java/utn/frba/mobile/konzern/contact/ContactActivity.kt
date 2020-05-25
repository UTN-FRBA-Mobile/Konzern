package utn.frba.mobile.konzern.contact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import utn.frba.mobile.konzern.R

class ContactActivity : AppCompatActivity() , ContactFragment.ContactView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_layout)
        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.vActivityBaseContent, ContactFragment.newInstance())
                .commitNow()
        }
    }
}