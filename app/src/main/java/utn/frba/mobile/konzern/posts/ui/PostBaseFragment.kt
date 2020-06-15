package utn.frba.mobile.konzern.posts.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel

abstract class PostBaseFragment: Fragment() {
    protected val viewModel: PostViewModel by activityViewModels()
}