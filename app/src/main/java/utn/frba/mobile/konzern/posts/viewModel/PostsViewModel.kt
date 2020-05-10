package utn.frba.mobile.konzern.posts.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import utn.frba.mobile.konzern.posts.model.Post

class PostsViewModel : ViewModel() {
    val itemList = MutableLiveData<List<Post>>()
    val selectedItem = MutableLiveData<Post>()

    fun loadItemList(){
        itemList.postValue(getItemList())
    }

    private fun getItemList(): List<Post>{
        return arrayListOf(
            Post(1, "Prueba", "Es una prueba", "09/05/2020 18:10", "Prueba.jpg"),
            Post(2, "Prueba 2", null,"09/05/2020 20:12",null),
            Post(3, "Prueba 3", null, "09/05/2020 21:17", "Prueba.jpg"),
            Post(4, "Prueba 4", "Es una prueba 4","10/05/2020 03:09", null),
            Post(5, "Prueba 5", "Es una prueba 5", "10/05/2020 14:55", "Prueba.jpg")
        )
    }

    fun selectItem(index: Int){
        selectedItem.value = itemList.value?.get(index);
    }
}
