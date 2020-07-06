package utn.frba.mobile.konzern.posts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.viewModel.BasePostViewModel
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel
import utn.frba.mobile.konzern.utils.BaseViewModel

class PostActivity : BasePostActivity() {
    override fun getViewTitle(): String? {
        return getString(R.string.title_posts)
    }

    override fun getCustomViewModel(): BasePostViewModel {
        val vm: PostViewModel by viewModels()
        return vm
    }

    override fun getContentLayout(): Int {
        return R.layout.content_post
    }

    override fun goToDetail(savedInstanceState: Bundle?) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.MainPostsFragment, true)
            .build()

        findNavController(R.id.nav_host_fragment).navigate(
            R.id.action_MainPostsFragment_to_ItemPostFragment,
            savedInstanceState,
            navOptions
        )
    }
}
