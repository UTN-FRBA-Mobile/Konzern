package utn.frba.mobile.konzern.reservations.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Exclude

data class Reservation(
    @get:Exclude var id: String? = null,
    val userId: String = "",
    val amenityId: String = "",
    val date: String = "",
    val hour: Int = 12
) {
    fun isOwnedByUser(): Boolean{
        return this.userId == FirebaseAuth.getInstance().currentUser?.uid
    }
}