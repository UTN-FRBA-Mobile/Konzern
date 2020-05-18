package utn.frba.mobile.konzern.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import utn.frba.mobile.konzern.R
import java.lang.RuntimeException

class SplashFragment: Fragment() {

    private val splashTime = 2000L
    private var splashInterface: SplashFragmentView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SplashFragmentView) {
            splashInterface = context
        } else {
            throw RuntimeException("$context must be SplashFragmentView")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val splashHandler = Handler()
        splashHandler.postDelayed(
            { splashInterface?.onSplashEnded() },
            splashTime
        )
    }


    companion object {
        fun newInstance() = SplashFragment()
    }

    interface SplashFragmentView {
        fun onSplashEnded()
    }
}