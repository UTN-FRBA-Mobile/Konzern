package utn.frba.mobile.konzern.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import utn.frba.mobile.konzern.MainActivity
import utn.frba.mobile.konzern.R

class SplashActivity : AppCompatActivity(), SplashFragment.SplashFragmentView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_layout)
        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.vActivityBaseContent, SplashFragment.newInstance())
                .commitNow()
        }
    }

    override fun onSplashEnded() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}