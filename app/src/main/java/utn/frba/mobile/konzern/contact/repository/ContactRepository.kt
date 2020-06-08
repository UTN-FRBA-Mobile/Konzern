package utn.frba.mobile.konzern.contact.repository

import com.google.firebase.firestore.FirebaseFirestore
import utn.frba.mobile.konzern.contact.model.Contact

class ContactRepository {

    fun getContactData(contactInterface: ContactRepositoryInterface) {
        val database = FirebaseFirestore.getInstance()
        database.collectionGroup(COLLECTION_ID)
            .get()
            .addOnCompleteListener { task ->
                val consorcioInfo = task.result?.documents?.get(0)?.toObject(Contact::class.java)
                contactInterface.onComplete(consorcioInfo)
            }
            .addOnFailureListener {
                contactInterface.onFailure()
            }
    }

    companion object {
        const val COLLECTION_ID = "consorcio"
    }

    interface ContactRepositoryInterface {
        fun onComplete(consorcioInfo: Contact?)

        fun onFailure()
    }
}