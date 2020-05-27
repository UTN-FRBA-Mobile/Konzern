package utn.frba.mobile.konzern.posts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel

abstract class PostBaseFragment: Fragment() {
    protected lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run { ViewModelProvider(this).get(PostViewModel::class.java) } ?: throw Exception("Invalid Activity")
    }
}