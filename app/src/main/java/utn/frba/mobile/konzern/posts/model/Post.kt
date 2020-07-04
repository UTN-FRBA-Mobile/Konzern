package utn.frba.mobile.konzern.posts.model

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Exclude
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

data class Post(
    @get:Exclude var id: String? = null,
    var summary: String = "",
    var description: String = "",
    var date: Date? = null,
    var images: List<Image> = ArrayList(),
    var active: Boolean = true,
    var userId: String? = null
): Serializable{

    data class Image(
        var url: String = "",
        var childPath: String = ""
    )

    @Exclude
    fun getMainImage(): Uri?{
        return if(this.hasImages())
            this.images[0].url.toUri()
        else
            null
    }

    @Exclude
    private fun hasImages(): Boolean{
        return this.images.count() > 0
    }

    @Exclude
    fun getFormattedDate(): String{
        return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("es", "AR")).format(date)
    }

    @Exclude
    fun isOwnedByUser(): Boolean{
        return this.userId == FirebaseAuth.getInstance().currentUser?.uid
    }
}