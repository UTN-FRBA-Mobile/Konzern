package utn.frba.mobile.konzern.contact.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.contact_fragment_layout.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.model.Contact
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
        getContactDataFormRepository()
    }

    private fun getContactDataFormRepository() {
        ContactRepository().getContactData(object : ContactRepository.ContactRepositoryInterface {
            override fun onComplete(consorcioInfo: Contact?) {
                setInfoFromDatabase(consorcioInfo)
            }

            override fun onFailure() {
                contactView.errorGettinContactInfo()
            }
        })
    }

    private fun setInfoFromDatabase(consorcioInfo: Contact?) {
        vContactProgressBar.visibility = View.GONE
        consorcioInfo?.let {
            vContactName.text = it.name
            Glide.with(this).load(it.imageUrl).error(R.drawable.consorcio_placeholder).into(vContactImage)
            vContactPhone.text = it.phone
            vContactAttentionTime.text = it.attentionTime
            vContactEmail.text = it.email
            vContactAddress.text = it.address
            vContactExtraInfo.text = it.extraInfo
        }
    }

    companion object {
        fun newInstance() = ContactFragment()
    }

    interface ContactView {
        fun errorGettinContactInfo()
    }
}