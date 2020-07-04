package utn.frba.mobile.konzern.posts.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import utn.frba.mobile.konzern.posts.model.Post
import java.util.*
import kotlin.collections.ArrayList

open class BasePostRepository{
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    open var dbCollectionName: String = ""

    suspend fun getItemList(): List<Post>{
        val items: ArrayList<Post> = ArrayList()

        try{
            val data =
                db.collection(dbCollectionName)
                    .orderBy("date", Query.Direction.DESCENDING)
                    .get()
                    .await()

            for (document in data.documents) {
                val item = retrieveItem(document)
                if(item != null && item.active)
                    items.add(item)
            }
        }catch (e : Exception){
            e.printStackTrace()
        }

        return items
    }

    private fun retrieveItem(document: DocumentSnapshot): Post?{
        val item = document.toObject(Post::class.java)
        if(item != null) {
            item.id = document.id
        }
        return item
    }

    suspend fun getItem(id: String): Post?{
        var item: Post? = null

        try{
            val data =
                db.collection(dbCollectionName)
                    .document(id)
                    .get()
                    .await()

            item = retrieveItem(data)
        }catch (e : Exception){
            e.printStackTrace()
        }

        return item
    }

    suspend fun delete(id: String){
        try{
            val item = getItem(id) ?: return
            item.active = false
            save(item, null)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    suspend fun save(item: Post, images: List<Uri>?){
        if(item.id == null){
            item.userId = FirebaseAuth.getInstance().currentUser?.uid
            item.date = Date()
        }

        item.images = saveImages(images)

        if(item.id == null)
            db.collection(dbCollectionName).document().set(item)
        else
            db.collection(dbCollectionName).document(item.id!!).set(item)
    }

    suspend fun saveImages(images: List<Uri>?): List<Post.Image>{
        val imageStoreList = ArrayList<Post.Image>()

        val ref = FirebaseStorage.getInstance().reference

        images?.forEach {
            val childPath = System.currentTimeMillis().toString() + ".jpg"
            val data = ref
                .child(childPath)
                .putFile(it)
                .await()

            val downloadUrl: Uri? = data.metadata?.reference?.downloadUrl?.await()
            if(downloadUrl != null)
                imageStoreList.add(Post.Image(downloadUrl.toString(), childPath))
        }

        return imageStoreList
    }

    suspend fun removeImages(images: List<Post.Image>){
        val ref = FirebaseStorage.getInstance().reference

        images.forEach {
            ref
                .child(it.childPath)
                .delete()
                .await()
        }
    }
}