package utn.frba.mobile.konzern.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.login_fragment_layout.*
import utn.frba.mobile.konzern.R
import java.lang.RuntimeException
import kotlin.math.log
import kotlinx.android.synthetic.main.login_fragment_layout.*
import kotlinx.android.synthetic.main.login_fragment_layout.view.*
import utn.frba.mobile.konzern.MainActivity
import utn.frba.mobile.konzern.profile.Profile
import utn.frba.mobile.konzern.profile.ProfileRepository

class LoginFragment : Fragment()  {

    private var loginView: LoginFragmenView? = null
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private val profileRepository = ProfileRepository()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginFragmenView) {
            loginView = context
        } else {
            throw RuntimeException("$context must be LoginFragmentView")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_key))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            return goToLoginOrCompleteSignUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment_layout, container, false)
    }

    private fun emailLogin() {
        val email = vLoginEmailInput.text.toString()
        val pass = vLoginPassword.text.toString()
        if (email.isEmpty() || pass.isEmpty())
            return
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // val user = auth.currentUser TODO: Usar este user para manejar la persistencia
                    firebaseAuth.currentUser?.uid
                    loginView?.successfulSignIn()
                } else {
                    loginView?.failedSignUpOrSignIn(task.exception?.message)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vLoginGoogleSignInButton.setOnClickListener {
            showProgress(true)
            loginView?.onGoogleSignInPressed(googleSignInClient, RC_SIGN_IN)
        }
        /*vLoginForgotMyPassword.setOnClickListener{
            loginView?.showFragment(ForgotPasswordFragment())
        }*/
        vLoginSignUpButton.setOnClickListener{
            loginView?.showFragment(SignUpFragment())
        }
        vLoginButton.setOnClickListener {
            emailLogin()
        }
    }

    fun showProgress(show: Boolean){
        if(show){
            vProgressBarLayout.visibility = VISIBLE
            loginFragmentLayout.visibility = GONE
        } else{
            vProgressBarLayout.visibility = GONE
            loginFragmentLayout.visibility = VISIBLE
        }
    }

    fun onGoogleSignInResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                loginView?.showSuccessSignInToast()
                Log.d("SignIn", "Sign in with google Success")
                firebaseAuthProcess(account!!)
            } catch (e: ApiException) {
                Log.d("SignIn", "Sign in with google Failed")
                loginView?.signInWithGoogleFailed()
                showProgress(false)
            }
        } else {
            Log.d("SignIn", "RequestCode for SignIn: $requestCode")
        }
    }

    private fun firebaseAuthProcess(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)

        firebaseAuth.signInWithCredential(credentials)
            .addOnCompleteListener(requireActivity()){ task ->
                if (task.isSuccessful) {
                    Log.d("SignIn", "Success Firebase Register")
                    goToLoginOrCompleteSignUp()
                } else {
                    Log.d("SignIn", "Failed Firebase Register")
                    loginView?.firebaseAuthProcessFailed()
                    showProgress(false)
                }
            }
    }

    private fun goToLoginOrCompleteSignUp(){
        showProgress(true)
        profileRepository.getProfile(object : ProfileRepository.ProfileRepositoryInterface {
            override fun onComplete(profile: Profile?) {
                if (profile?.email.isNullOrEmpty())
                    loginView?.completeSignUp()
                else
                    loginView?.successfulSignIn()
            }
        })
    }
    override fun onDetach() {
        super.onDetach()
        loginView = null
    }

    companion object {
        fun newInstance() = LoginFragment()
        private const val RC_SIGN_IN = 9001
    }

    interface LoginFragmenView {
        fun showSuccessSignInToast()

        fun successfulSignIn()

        fun onGoogleSignInPressed(googleSignInClient: GoogleSignInClient, resultCode: Int)

        fun onGoToMainButtonClicked()

        fun signInWithGoogleFailed()

        fun firebaseAuthProcessFailed()

        fun showFragment(fragment: Fragment)

        fun failedSignUpOrSignIn(message: String?)

        fun completeSignUp()
    }
}