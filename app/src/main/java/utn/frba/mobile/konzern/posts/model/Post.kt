package utn.frba.mobile.konzern.posts.model

import android.graphics.drawable.Drawable
import android.net.Uri
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class Post(): Serializable{
    var id: Int? = null
    var summary: String = ""
    var text: String = ""
    lateinit var date: String
    var images: List<Uri> = ArrayList()
    var isOwnedByUser: Boolean = false

    constructor(id: Int, summary: String, text: String, date: String, images: List<Uri>?) : this() {
        this.id = id
        this.summary = summary
        this.text = text
        this.date = date
        addImages(images)
    }

    fun setDateString(date: LocalDateTime?){
        if(date != null)
            this.date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("es", "AR")).format(Date())
    }

    fun addImages(images: List<Uri>?){
        if(images != null)
            this.images = images
    }

    fun getMainImage(): Uri?{
        return if(this.hasImages())
            this.images[0]
        else
            null
    }

    private fun hasImages(): Boolean{
        return this.images.count() > 0
    }
}