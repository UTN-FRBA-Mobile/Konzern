package utn.frba.mobile.konzern.contact.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class ContactRepository {

    fun getContactData(contactInterface: ContactRepositoryInterface) {
        val database = FirebaseFirestore.getInstance()
        database.collectionGroup(COLLECTION_ID)
            .get()
            .addOnCompleteListener {task ->
                contactInterface.onComplete(task.result?.documents?.get(0))
            }
            .addOnFailureListener {
                contactInterface.onFailure()
            }
    }

    companion object {
        const val COLLECTION_ID = "consorcio"
    }

    interface ContactRepositoryInterface {
        fun onComplete(consorcioInfo: DocumentSnapshot?)

        fun onFailure()
    }
}