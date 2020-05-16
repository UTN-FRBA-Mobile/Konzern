package utn.frba.mobile.konzern.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import utn.frba.mobile.konzern.R
import java.lang.RuntimeException

class LoginFragment : Fragment() {

    private var loginView: LoginFragmenView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginFragmenView) {
            loginView = context
        } else {
            throw RuntimeException("$context must be LoginFragmentView")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment_layout, container, false)
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

    interface LoginFragmenView
}