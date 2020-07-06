package utn.frba.mobile.konzern.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import utn.frba.mobile.konzern.MainActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.login_fragment_layout.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.login.ForgotPasswordFragment

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmenView, ForgotPasswordFragment.OnFragmentInteractionListener, BaseSignUpFragment.SignUpFragmentView {

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
        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
    }

    override fun successfulSignUp() {
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
        successfulSignIn()
    }

    override fun failedSignUpOrSignIn(message: String?) {
        Toast.makeText(this, message,
            Toast.LENGTH_SHORT).show()
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

    override fun signInWithGoogleFailed() {
        Toast.makeText(this, "Sign In with Google Failed.", Toast.LENGTH_SHORT).show()
    }

    override fun firebaseAuthProcessFailed() {
        Toast.makeText(this, "Firebase Auth Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginFragment.onGoogleSignInResult(requestCode, data)
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vActivityBaseContent, fragment)
            .addToBackStack("name")
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed();
        } else {
            supportFragmentManager.popBackStack();
        }
    }

    override fun completeSignUp() {
        showFragment(CompleteSignUpFragment())
    }
}