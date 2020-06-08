package utn.frba.mobile.konzern.posts.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.repository.PostRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private var coRoutinesJob = Job()
    private val coRoutinesScope = CoroutineScope(Dispatchers.Main + this.coRoutinesJob)
    private val _failure = SingleLiveEvent<Failure>()
    val failure: LiveData<Failure> get() = _failure

    protected val showProgress = MutableLiveData<Boolean>()
    val showProgressIndicator: LiveData<Boolean> get() = showProgress

    private val repository: PostRepository = PostRepository()

    val itemList = MutableLiveData<List<Post>>()
    val selectedItem = MutableLiveData<Post>()
    val images = MutableLiveData<List<Uri>>()

    fun loadItemList(){
        this.launchControlledInBg(mainOperation = {
            itemList.postValue(repository.getItemList())
        })
    }

    fun selectItem(id: String?){
        if(id == null)
            selectNewItem()
        else
            this.launchControlledInBg(mainOperation = {
                selectedItem.postValue(repository.getItem(id))
            })
    }

    fun deleteItem(id: String){
        this.launchControlledInBg(mainOperation = {
            repository.delete(id)
            loadItemList()
        })
    }

    private fun selectNewItem(){
        this.selectedItem.postValue(Post())
        this.images.postValue(arrayListOf())
    }

    fun saveItem(summary: String, description: String){
        val item = this.selectedItem.value!!

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

    fun launchControlledInBg(mainOperation: suspend AndroidViewModel.() -> Unit,
                             continuation: () -> Any = {}) {
        this.coRoutinesScope.launch {
            try {
                mainOperation()
                continuation()
            } catch (t: ControlledException) {
                handleControlledException(t, continuation)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }
    private fun handleException(e: Exception) {
        showProgress.value = false
        val failure = Failure(intStringIdTitle = R.string.error)
        failure.failureType = FailureType.UNKNOWN
        this._failure.value = failure

        e.fillInStackTrace()
    }

    private fun handleControlledException(exception: ControlledException, continuation: () -> Any) {
        showProgress.value = false

        val failure = exception.toFailure()
        if(exception.isWarning()) {
            failure.failureContinuation = continuation
        }
        this._failure.value = failure
    }
}
