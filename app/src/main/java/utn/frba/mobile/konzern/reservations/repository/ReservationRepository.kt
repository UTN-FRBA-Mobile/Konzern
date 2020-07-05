package utn.frba.mobile.konzern.reservations.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import utn.frba.mobile.konzern.reservations.model.Amenity
import utn.frba.mobile.konzern.reservations.model.Reservation

class ReservationRepository {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbCollectionName = "reservations"
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun getItemList(): List<Reservation>{
        lateinit var data: QuerySnapshot
        try{
            data =
                db.collection(dbCollectionName)
                    .get()
                    .await()
        }catch (e : Exception){
            e.printStackTrace()
        }
        return parseItems(data)
    }

    suspend fun save(amenityId: String, day: String, hour: Int) {
        var reservation = Reservation(
            "",
            firebaseAuth.currentUser!!.uid,
            amenityId,
            day,
            hour
        )
        db.collection(dbCollectionName).document().set(reservation)
    }

    suspend fun exists(amenityId: String, day: String, hour: Int): Boolean{
        val data = db.collection(dbCollectionName)
            .whereEqualTo("date", day)
            .whereEqualTo("hour", hour)
            .get()
            .await()
        return !parseItems(data).isNullOrEmpty()
    }

    suspend fun fetchByDate(date: String): List<Reservation> {
        val data = db.collection(dbCollectionName)
                        .whereEqualTo("date", date)
                        .get()
                        .await()
        return parseItems(data)
    }

    private fun parseItems(data: QuerySnapshot): List<Reservation> {
        val items: ArrayList<Reservation> = ArrayList()
        for (document in data.documents) {
            val item = retrieveItem(document)
            if(item != null)
                items.add(item)
        }
        return items
    }

    private fun retrieveItem(document: DocumentSnapshot): Reservation?{
        var item = document.toObject(Reservation::class.java)
        //item!!.id = document.id
        return item
    }
}