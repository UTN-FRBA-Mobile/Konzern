package utn.frba.mobile.konzern.posts.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.repository.PostRepository


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository =
        PostRepository(application)

    val itemList = MutableLiveData<List<Post>>()
    val selectedItem = MutableLiveData<Post>()
    val images = MutableLiveData<List<Uri>>()

    fun loadItemList(){
        itemList.postValue(repository.getItemList())
    }

    fun selectItem(id: Int?){
        if(id == null)
            selectNewItem()
        else
            selectedItem.value = repository.getItem(id)
    }

    private fun selectNewItem(){
        this.selectedItem.postValue(Post())
        this.images.postValue(arrayListOf())
    }

    fun addItem(summary: String, description: String){
        repository.save(summary, description, images.value)
    }

    fun addImage(imageList: List<Uri>){
        var value = ArrayList<Uri>()

        if(images.value != null)
            value = images.value as ArrayList

        value.addAll(imageList)
        images.postValue(value)
    }


}
