package utn.frba.mobile.konzern.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.vSignUpEmail
import utn.frba.mobile.konzern.R

class SignUpFragment : BaseSignUpFragment() {

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
        vLayoutSignUpMail.error = null
        return when {
            vSignUpEmail.text.isNullOrEmpty() -> {
                vLayoutSignUpMail.error = getString(R.string.sign_up_complete_field)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(vSignUpEmail.text.toString()).matches() -> {
                vLayoutSignUpMail.error = getString(R.string.sign_up_invalid_email)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun validatePasswords(): Boolean {
        vLayoutSignUpPassword.error = null
        vLayoutSignUpRepeatPassword.error = null

        return when {
            vSignUpPassword.text.toString().length < 6 -> {
                vLayoutSignUpPassword.error = getString(R.string.sign_up_password_longitude)
                false
            }
            vSignUpPassword.text.toString() != vSignUpRepeatPassword.text.toString() -> {
                vLayoutSignUpRepeatPassword.error = getString(R.string.sign_up_password_mismatch)
                false
            }
            else -> {

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
            showProgress(true)
            auth.createUserWithEmailAndPassword(email, vSignUpPassword.text.toString())
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        createUserData(email, auth.currentUser?.uid, vSignUpPhone, vSignUpInfo)
                        // val user = auth.currentUser TODO: Usar este user para manejar la persistencia
                    } else {
                        signUpView?.failedSignUpOrSignIn(task.exception?.message)
                        showProgress(false)
                    }
                }
        } else {
            // el formulario está mal
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}
