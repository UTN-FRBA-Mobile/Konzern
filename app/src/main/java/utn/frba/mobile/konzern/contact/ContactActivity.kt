package utn.frba.mobile.konzern.contact

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.ui.ContactFragment

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

    override fun errorGettinContactInfo() {
        Toast.makeText(this, R.string.contact_error_getting_info, Toast.LENGTH_SHORT).show()
    }
}