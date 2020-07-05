package utn.frba.mobile.konzern.reservations.model

import com.google.firebase.firestore.Exclude
import java.sql.Timestamp

data class Reservation(@get:Exclude val id: String = "", val userId: String = "", val amenityId: String = "", val date: String = "", val hour: Int = 12)