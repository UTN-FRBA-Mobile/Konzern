package utn.frba.mobile.konzern.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import utn.frba.mobile.konzern.R

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmenView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_layout)
        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.vActivityBaseContent, LoginFragment.newInstance())
                .commitNow()
        }
    }
}