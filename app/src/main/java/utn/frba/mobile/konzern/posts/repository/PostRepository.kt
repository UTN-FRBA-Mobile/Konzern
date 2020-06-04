package utn.frba.mobile.konzern.posts.repository

import android.app.Application
import android.content.ContentResolver
import android.graphics.drawable.Drawable
import android.net.Uri
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.model.Post
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class PostRepository(var application: Application) {
    var items: ArrayList<Post> = arrayListOf(
        Post(
            1,
            "Resumen 1",
            "Descripción 1",
            "09/05/2020 18:10",
            getMockImages(1)
        ),
        Post(
            2,
            "Resumen 2",
            getAppString(R.string.lorem_ipsum),
            "09/05/2020 20:12",
            null
        ),
        Post(
            3,
            "Resumen 3",
            "Descripción 1",
            "09/05/2020 21:17",
            getMockImages(2)
        ),
        Post(
            4,
            "Resumen 4",
            getAppString(R.string.lorem_ipsum),
            "10/05/2020 03:09",
            getMockImages(1)
        ),
        Post(
            5,
            "Resumen 5",
            "Es una prueba 5",
            "10/05/2020 14:55",
            null
        )
    )

    fun getItemList(): List<Post>{
        return items
    }

    fun getItem(id: Int): Post?{
        return items.find { it.id == id }
    }

    fun save(item: Post){
        if(item.id != null){
            var oldItem = getItem(item.id!!)
            oldItem = item
        } else{
            item.id = items.count() + 1
            item.isOwnedByUser = true
            item.setDateString(LocalDateTime.now())
            items.add(0, item)
        }
    }

    fun delete(id: Int){
        items.remove(items.find { it.id == id })
    }

    private fun getAppString(stringId: Int): String {
        return application.resources.getString(stringId)
    }

    private fun getMockImages(count: Int): ArrayList<Uri> {
        val result = arrayListOf(getImageUri(R.drawable.mock_post_1))
        if(count > 1)
            result.add(getImageUri(R.drawable.mock_post_2))

        return result
    }

    private fun getImageUri(resourceId: Int): Uri{
        return (Uri.Builder())
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(application.resources.getResourcePackageName(resourceId))
            .appendPath(application.resources.getResourceTypeName(resourceId))
            .appendPath(application.resources.getResourceEntryName(resourceId))
            .build()
    }
}