package utn.frba.mobile.konzern.contact.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.contact_fragment_layout.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.repository.ContactRepository
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

        ContactRepository().getContactData(object : ContactRepository.ContactRepositoryInterface {
            override fun onComplete(consorcioInfo: DocumentSnapshot?) {
                setInfoFromDatabase(consorcioInfo)
            }

            override fun onFailure() {
                contactView.errorGettinContactInfo()
            }
        })
    }

    private fun setInfoFromDatabase(consorcioInfo: DocumentSnapshot?) {
        vContactProgressBar.visibility = View.GONE
        consorcioInfo?.let {info ->
            vContactName.text = info["name"] as CharSequence
            vContactPhone.text = info["phone"] as CharSequence
            vContactAttentionTime.text = info["attentionTime"] as CharSequence
            vContactEmail.text = info["email"] as CharSequence
            vContactAddress.text = info["address"]  as CharSequence
            vContactExtraInfo.text = info["extraInfo"] as CharSequence
        }
    }

    companion object {
        fun newInstance() = ContactFragment()
    }

    interface ContactView {
        fun errorGettinContactInfo()
    }
}