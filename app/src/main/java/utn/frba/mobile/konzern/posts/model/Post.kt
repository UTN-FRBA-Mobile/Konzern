package utn.frba.mobile.konzern.posts.model

import java.io.Serializable
import kotlin.properties.Delegates

class Post(): Serializable{
    var id by Delegates.notNull<Int>()
    lateinit var summary: String
    var text: String? = null
    lateinit var date: String
    var image: String? = null

    constructor(id: Int, summary: String, text: String?, date: String, image: String?) : this() {
        this.id = id
        this.summary = summary
        this.text = text
        this.date = date
        this.image = image
    }
}