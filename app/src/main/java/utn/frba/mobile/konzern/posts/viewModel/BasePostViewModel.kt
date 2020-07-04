package utn.frba.mobile.konzern.posts.viewModel

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.repository.BasePostRepository
import utn.frba.mobile.konzern.posts.repository.PostRepository
import utn.frba.mobile.konzern.utils.BaseViewModel
import utn.frba.mobile.konzern.utils.serviceManager.SingleLiveEvent
import kotlin.collections.ArrayList


abstract class BasePostViewModel : BaseViewModel() {
    abstract val repository: BasePostRepository
    abstract val canEdit: Boolean

    val itemList = MutableLiveData<List<Post>>()
    val images = MutableLiveData<List<Uri>>()

    var selectedItem: Post? = null
    private val _editEvent =
        SingleLiveEvent<Any>()
    val editEvent: LiveData<Any> get() = _editEvent

    private val _showDetailEvent =
        SingleLiveEvent<Any>()
    val showDetailEvent: LiveData<Any> get() = _showDetailEvent

    fun initItemList(){
        if(itemList.value == null){
            loadItemList()
        }
    }

    fun editItem(id: String?){
        selectItem(id) {
            this.images.postValue(selectedItem?.images?.map { it.url.toUri() })
            _editEvent.call()
        }
    }

    fun showDetailItem(id: String){
        selectItem(id) {
            _showDetailEvent.call()
        }
    }

    fun saveItem(summary: String, description: String){
        val item = this.selectedItem!!

        item.summary = summary
        item.description = description

        this.launchControlledInBg(mainOperation = {
            repository.save(item, images.value)
            loadItemList()
        })
    }

    fun addImage(imageList: List<Uri>){
        var value = ArrayList<Uri>()

        if(images.value != null)
            value = images.value as ArrayList

        value.addAll(imageList)
        images.postValue(value)
    }

    fun deleteItem(id: String){
        this.launchControlledInBg(
            mainOperation = {
                repository.delete(id)
                loadItemList()
            },
            continuation = { hideProgressBar() },
            customHandlingProgress = true
        )
    }

    private fun loadItemList(){
        this.launchControlledInBg(mainOperation = {
            itemList.postValue(repository.getItemList())
        })
    }

    private fun selectItem(id: String?, continuation: () -> Unit = {}){
        if(id == null)
            selectNewItem()
        else
            this.launchControlledInBg(mainOperation = {
                selectedItem = repository.getItem(id)
            }, continuation = continuation)
    }

    private fun selectNewItem(){
        this.selectedItem = Post()
        this.images.postValue(arrayListOf())
    }
}
