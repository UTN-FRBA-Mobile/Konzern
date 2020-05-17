package utn.frba.mobile.konzern.posts.viewModel

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.model.Post

class PostsViewModel(application: Application) : AndroidViewModel(application) {
    val itemList = MutableLiveData<List<Post>>()
    val selectedItem = MutableLiveData<Post>()

    fun loadItemList(){
        itemList.postValue(getItemList())
    }

    private fun getAppString(stringId: Int): String {
        return getApplication<Application>().resources.getString(stringId)
    }

    private fun getMockImages(count: Int): ArrayList<Drawable>{
        val result = arrayListOf(getApplication<Application>().resources.getDrawable(R.drawable.mock_post_1))
        if(count > 1)
            result.add(getApplication<Application>().resources.getDrawable(R.drawable.mock_post_2))

        return result
    }

    private fun getItemList(): List<Post>{
        return arrayListOf(
            Post(1, "Resumen 1", "Descripción 1", "09/05/2020 18:10", getMockImages(1)),
            Post(2, "Resumen 2", getAppString(R.string.lorem_ipsum), "09/05/2020 20:12", null),
            Post(3, "Resumen 3", "Descripción 1", "09/05/2020 21:17", getMockImages(2)),
            Post(4, "Resumen 4", getAppString(R.string.lorem_ipsum),"10/05/2020 03:09", getMockImages(1)),
            Post(5, "Resumen 5", "Es una prueba 5", "10/05/2020 14:55", null)
        )
    }

    fun selectItem(index: Int){
        selectedItem.value = itemList.value?.get(index);
    }
}
