package utn.frba.mobile.konzern.posts.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import utn.frba.mobile.konzern.posts.model.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostsViewModel : ViewModel() {
    val itemList = MutableLiveData<List<Post>>()
    val selectedItem = MutableLiveData<Post>()

    fun loadItemList(){
        itemList.postValue(getItemList())
    }

    private fun getItemList(): List<Post>{
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        return arrayListOf(
            Post(1, "Prueba", "Es una prueba", LocalDateTime.parse("09/05/2020 18:10", formatter), "Prueba.jpg"),
            Post(2, "Prueba 2", null, LocalDateTime.parse("09/05/2020 20:12", formatter), null),
            Post(3, "Prueba 3", "Es una prueba 3", LocalDateTime.parse("09/05/2020 21:17", formatter), "Prueba.jpg"),
            Post(4, "Prueba 4", null, LocalDateTime.parse("10/05/2020 03:09", formatter), null),
            Post(5, "Prueba 5", "Es una prueba 5", LocalDateTime.parse("10/05/2020 14:55", formatter), "Prueba.jpg")
        )
    }

    fun selectItem(index: Int){
        selectedItem.value = itemList.value?.get(index);
    }
}
