package utn.frba.mobile.konzern.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_complete_sign_up.*
import kotlinx.android.synthetic.main.fragment_complete_sign_up.view.*
import kotlinx.android.synthetic.main.fragment_complete_sign_up.view.vCompleteSignUpPhone

import utn.frba.mobile.konzern.R

class CompleteSignUpFragment : BaseSignUpFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complete_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser
        val email: String = user?.email as String
        view.vCompleteSingUpButton.setOnClickListener {
            createUserData(email, user?.uid, vCompleteSignUpPhone, vCompleteSignUpInfo)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = CompleteSignUpFragment()
    }

}
