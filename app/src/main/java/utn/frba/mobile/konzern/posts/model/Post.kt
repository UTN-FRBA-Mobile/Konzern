package utn.frba.mobile.konzern.posts.model

import android.graphics.drawable.Drawable
import java.io.Serializable
import kotlin.properties.Delegates

class Post(): Serializable{
    var id by Delegates.notNull<Int>()
    lateinit var summary: String
    lateinit var text: String
    lateinit var date: String
    var images: List<Drawable> = ArrayList()

    constructor(id: Int, summary: String, text: String, date: String, images: List<Drawable>?) : this() {
        this.id = id
        this.summary = summary
        this.text = text
        this.date = date
        if(images != null)
            this.images = images
    }

    constructor(id: Int, summary: String, text: String, date: String, image: Drawable) :
            this(id, summary, text, date, arrayListOf(image))

    fun getMainImage(): Drawable?{
        return if(this.hasImages())
            this.images[0]
        else
            null
    }

    fun hasImages(): Boolean{
        return this.images.count() > 0
    }
}