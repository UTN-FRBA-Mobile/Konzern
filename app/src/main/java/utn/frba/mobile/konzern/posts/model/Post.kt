package utn.frba.mobile.konzern.posts.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.properties.Delegates

class Post(): Serializable{
    var id by Delegates.notNull<Int>()
    lateinit var summary: String
    var text: String? = null
    lateinit var date: LocalDateTime

    constructor(id: Int, summary: String, text: String?, date: LocalDateTime) : this() {
        this.id = id
        this.summary = summary
        this.text = text
        this.date = date
    }
}