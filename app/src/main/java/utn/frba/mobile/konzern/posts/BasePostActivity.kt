package utn.frba.mobile.konzern.posts

import android.os.Bundle
import androidx.lifecycle.Observer
import utn.frba.mobile.konzern.BaseActivity
import utn.frba.mobile.konzern.utils.BaseViewModel

abstract class BasePostActivity : BaseActivity(){
    protected lateinit var viewModel: BaseViewModel
    protected abstract fun getCustomViewModel(): BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getCustomViewModel()
        viewModel.showProgressIndicator.observe(this, Observer {
            if(it!!){
                showProgress()
            }else{
                hideProgress()
            }
        })
    }
}
