package utn.frba.mobile.konzern.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import utn.frba.mobile.konzern.MainActivity
import utn.frba.mobile.konzern.R

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmenView {

    private lateinit var loginFragment: LoginFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_layout)
        if(savedInstanceState == null) {
            loginFragment = LoginFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.vActivityBaseContent, loginFragment)
                .commitNow()
        }
    }

    override fun showSuccessSignInToast() {
        Toast.makeText(this, "Sign In Successful", Toast.LENGTH_SHORT).show()
    }

    override fun finishSignInActivity() {
        finish()
    }

    override fun successfulSignIn() {
        val goToMainView = Intent(applicationContext, MainActivity::class.java)
        startActivity(goToMainView)
        finish()
    }

    override fun onGoogleSignInPressed(googleSignInClient: GoogleSignInClient, requestCode: Int) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, requestCode)
    }

    override fun onGoToMainButtonClicked() {
        val goToMainView = Intent(applicationContext, MainActivity::class.java)
        startActivity(goToMainView)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginFragment.onGoogleSignInResult(requestCode, data)
    }
}