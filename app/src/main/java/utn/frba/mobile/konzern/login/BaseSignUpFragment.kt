package utn.frba.mobile.konzern.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_complete_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.vFragmentSignUp
import kotlinx.android.synthetic.main.fragment_sign_up.vProgressBarLayout
import utn.frba.mobile.konzern.R
import java.lang.RuntimeException


abstract class BaseSignUpFragment : Fragment() {

    var signUpView: SignUpFragmentView? = null
    lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignUpFragmentView) {
            signUpView = context
        } else {
            throw RuntimeException("$context must be OnFragmentInteractionListener")
        }
    }


    fun createUserData(email: String, uid: String?, phone: TextInputEditText, info: TextInputEditText) {
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "email" to email,
            "phone" to phone.text.toString(),
            "info" to info.text.toString(),
            "uid" to uid
        )
        db.collection("users_data")
            .add(user)
            .addOnSuccessListener {
                signUpView?.successfulSignUp()
            }
            .addOnFailureListener {
                showProgress(false)
                signUpView?.failedSignUpOrSignIn("Error en la creación del usuario")
            }
    }

    fun showProgress(show: Boolean){
        if(show){
            vProgressBarLayout.visibility = View.VISIBLE
            vFragmentSignUp.visibility = View.GONE
        } else{
            vProgressBarLayout.visibility = View.GONE
            vFragmentSignUp.visibility = View.VISIBLE
        }
    }

    interface SignUpFragmentView {
        fun successfulSignUp()
        fun failedSignUpOrSignIn(message: String?)
    }

}
