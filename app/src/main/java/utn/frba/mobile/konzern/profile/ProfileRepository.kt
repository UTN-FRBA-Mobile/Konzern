package utn.frba.mobile.konzern.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import utn.frba.mobile.konzern.contact.model.Contact

class ProfileRepository {

    fun getProfile(contactInterface: ProfileRepositoryInterface) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val query = db
            .collection("users_data")
            .whereEqualTo("uid", auth.currentUser?.uid)
            .get()
            .addOnCompleteListener { task ->
                lateinit var profile: Profile
                val documents = task.result?.documents
                profile = if (documents.isNullOrEmpty())
                            Profile()
                          else
                            documents[0]?.toObject(Profile::class.java)!!
                contactInterface.onComplete(profile)
            }
    }

    interface ProfileRepositoryInterface {
        fun onComplete(profile: Profile?)

    }
}