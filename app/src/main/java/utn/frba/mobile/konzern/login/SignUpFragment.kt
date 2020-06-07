package utn.frba.mobile.konzern.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.vSignUpEmail
import kotlinx.android.synthetic.main.login_fragment_layout.*
import utn.frba.mobile.konzern.MainActivity

import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.profile.Profile
import java.lang.RuntimeException
import kotlin.math.sign


class SignUpFragment : Fragment() {

    private var signUpView: SignUpFragmentView? = null
    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignUpFragmentView) {
            signUpView = context
        } else {
            throw RuntimeException("$context must be OnFragmentInteractionListener")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sign_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.vSignUpEmail.doAfterTextChanged { validateEmail() }
        view.vSignUpPassword.doAfterTextChanged { validatePasswords() }
        view.vSignUpRepeatPassword.doAfterTextChanged { validatePasswords() }
        view.vSingUpButton.setOnClickListener{ createAccount() }
    }


    private fun validateEmail(): Boolean {
        return when {
            vSignUpEmail.text.isEmpty() -> {
                vSignUpEmail.error = getString(R.string.sign_up_button)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(vSignUpEmail.text).matches() -> {
                vSignUpEmail.error = getString(R.string.sign_up_invalid_email)
                false
            }
            else -> {
                vSignUpEmail.error = null
                true
            }
        }
    }

    private fun validatePasswords(): Boolean {
        return when {
            vSignUpPassword.text.toString() != vSignUpRepeatPassword.text.toString() -> {
                vSignUpPassword.error = getString(R.string.sign_up_password_mismatch)
                vSignUpRepeatPassword.error = getString(R.string.sign_up_password_mismatch)
                false
            }
            vSignUpPassword.text.toString().length < 6 -> {
                vSignUpPassword.error = getString(R.string.sign_up_password_longitude)
                false
            }
            else -> {
                vSignUpPassword.error = null
                vSignUpRepeatPassword.error = null
                true
            }
        }
    }

    private fun validateForm(): Boolean {
        return validateEmail() && validatePasswords()
    }

    private fun createAccount() {
        var email = vSignUpEmail.text.toString()
        if (validateForm()) {
            auth.createUserWithEmailAndPassword(email, vSignUpPassword.text.toString())
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        createUserData(email)
                        // val user = auth.currentUser TODO: Usar este user para manejar la persistencia
                    } else {
                        signUpView?.failedSignUpOrSignIn(task.exception?.message)
                    }
                }
        } else {
            // el formulario está mal
        }
    }

    private fun createUserData(email: String) {
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "email" to email,
            "phone" to vSignUpPhone.text.toString(),
            "info" to vSignUpInfo.text.toString()
        )

        db.collection("users_data")
            .add(user)
            .addOnSuccessListener {
                signUpView?.successfulSignUp()
            }
            .addOnFailureListener {
                signUpView?.failedSignUpOrSignIn("Error en la creación del usuario")
            }
    }

    interface SignUpFragmentView {
        fun successfulSignUp()
        fun failedSignUpOrSignIn(message: String?)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}
