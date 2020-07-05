package utn.frba.mobile.konzern.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.login.LoginActivity

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

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Splash Activity", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                var token = ""
                // Get new Instance ID token
                if (task.result != null && task.result?.token != null) {
                    token = task.result!!.token
                }

                // Log and toast
                Log.d("Splash Activity", token)
            })
    }

    override fun onSplashEnded() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}