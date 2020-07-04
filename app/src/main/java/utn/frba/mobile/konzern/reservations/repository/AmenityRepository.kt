package utn.frba.mobile.konzern.reservations.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import utn.frba.mobile.konzern.reservations.model.Amenity

class AmenityRepository {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbCollectionName = "amenities"

    suspend fun getItemList(): List<Amenity>{
        val items: ArrayList<Amenity> = ArrayList()

        try{
            val data =
                db.collection(dbCollectionName)
                    .get()
                    .await()

            for (document in data.documents) {
                val item = retrieveItem(document)
                if(item != null)
                    items.add(item)
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
        return items
    }

    private fun retrieveItem(document: DocumentSnapshot): Amenity?{
        return document.toObject(Amenity::class.java)
    }
}