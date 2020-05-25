package utn.frba.mobile.konzern.contact

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import utn.frba.mobile.konzern.R
import java.lang.RuntimeException

class ContactFragment : Fragment() {

    private lateinit var contactView : ContactView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContactView) {
            contactView = context
        } else {
            throw RuntimeException("$context must be ContactView")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.contact_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = ContactFragment()
    }

    interface ContactView
}