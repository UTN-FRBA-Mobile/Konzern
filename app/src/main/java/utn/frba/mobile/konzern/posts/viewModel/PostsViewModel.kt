package utn.frba.mobile.konzern.posts.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.repository.PostRepository


class PostsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository =
        PostRepository(application)

    val itemList = MutableLiveData<List<Post>>()
    val selectedItem = MutableLiveData<Post>()

    fun loadItemList(){
        itemList.postValue(repository.getItemList())
    }

    fun selectItem(id: Int){
        selectedItem.value = repository.getItem(id)
    }

    fun addItem(summary: String, description: String){
        repository.save(summary, description)
    }
}
