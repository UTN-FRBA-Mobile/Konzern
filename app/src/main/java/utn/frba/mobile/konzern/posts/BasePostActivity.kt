package utn.frba.mobile.konzern.posts

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import utn.frba.mobile.konzern.BaseActivity
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.viewModel.BasePostViewModel
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel

abstract class BasePostActivity : BaseActivity(){
    protected lateinit var viewModel: BasePostViewModel
    protected abstract fun getCustomViewModel(): BasePostViewModel
    protected abstract fun goToDetail(savedInstanceState: Bundle?)

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

        if (intent.extras != null) {
            val value = getExtraVarKey(NAV_POST_ITEM_ID)

            if(value != null) {
                val vm = getCustomViewModel()
                vm.showDetailItem(value)
                goToDetail(savedInstanceState)
            }
        }
    }

    companion object{
        val NAV_POST_ITEM_ID = "NAV_POST_ITEM_ID"
    }

    private fun getExtraVarKey(key: String): String? {
        return if (intent.extras != null) {
            intent.extras!!.getString(key, "")
        } else ""
    }
}
