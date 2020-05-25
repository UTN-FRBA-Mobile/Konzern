package utn.frba.mobile.konzern.contact

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.contact_fragment_layout.*
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
        setMockInfo()
    }

    //TODO: Remove this mocked info when get real from firebase
    private fun setMockInfo() {
        vContactName.text = "CONSORCIO_ABC"
        vContactPhone.text = "11-2345-6789"
        vContactAttentionTime.text = "L a V de 08:00 a 17:00"
        vContactEmail.text = "consorcio_abc@gmail.com"
        vContactAddress.text = "Av. Siempreviva 742"
        vContactExtraInfo.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Nunc nisl est, facilisis sagittis luctus eget, cursus sed quam. In tempus sed ligula at sagittis. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum turpis ipsum, vestibulum non velit et, " +
                "porta vestibulum erat. Quisque laoreet elit eget nulla ultricies, sed euismod diam tempor. " +
                "Duis vitae finibus ipsum. Nam accumsan elit a blandit efficitur. Proin dictum pellentesque commodo." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Nunc nisl est, facilisis sagittis luctus eget, cursus sed quam. In tempus sed ligula at sagittis. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum turpis ipsum, vestibulum non velit et, " +
                "porta vestibulum erat. Quisque laoreet elit eget nulla ultricies, sed euismod diam tempor. " +
                "Duis vitae finibus ipsum. Nam accumsan elit a blandit efficitur. Proin dictum pellentesque commodo."
    }

    companion object {
        fun newInstance() = ContactFragment()
    }

    interface ContactView
}