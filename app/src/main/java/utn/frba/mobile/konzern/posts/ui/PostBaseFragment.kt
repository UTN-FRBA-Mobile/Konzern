package utn.frba.mobile.konzern.posts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import utn.frba.mobile.konzern.posts.viewModel.BasePostViewModel

abstract class PostBaseFragment: Fragment() {
    protected lateinit var viewModel: BasePostViewModel

    abstract fun getCustomViewModel(): BasePostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getCustomViewModel()
    }
}