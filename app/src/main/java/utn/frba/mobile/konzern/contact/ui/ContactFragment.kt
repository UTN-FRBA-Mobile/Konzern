package utn.frba.mobile.konzern.contact.ui

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.contact_fragment_layout.*
import utn.frba.mobile.konzern.R
import java.lang.RuntimeException

class ContactFragment : Fragment() {

    private lateinit var contactView : ContactView
    private lateinit var database: FirebaseFirestore

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContactView) {
            contactView = context
        } else {
            throw RuntimeException("$context must be ContactView")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.contact_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getContactDataFromFirebase(database)
    }

    private fun getContactDataFromFirebase(database: FirebaseFirestore) {
        database.collectionGroup("consorcio")
            .get()
            .addOnCompleteListener {task ->
                val consorcioInfo = task.result?.documents?.get(0)
                setInfoFromDatabase(consorcioInfo)
            }
            .addOnFailureListener {
                contactView.errorGettinContactInfo()
            }
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