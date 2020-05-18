package utn.frba.mobile.konzern.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.login_fragment_layout.*
import utn.frba.mobile.konzern.R
import java.lang.RuntimeException
import kotlin.math.log

class LoginFragment : Fragment() {

    private var loginView: LoginFragmenView? = null
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vLoginGoogleSignInButton.setOnClickListener { loginView?.onGoogleSignInPressed(googleSignInClient, RC_SIGN_IN) }
        vLoginGoToMain.setOnClickListener { loginView?.onGoToMainButtonClicked() }
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
                    loginView?.successfulSignIn()
                } else {
                    Log.d("SignIn", "Failed Firebase Register")
                    loginView?.firebaseAuthProcessFailed()
                }
            }
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
    }
}