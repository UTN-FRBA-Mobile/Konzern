package utn.frba.mobile.konzern.news

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.BasePostActivity
import utn.frba.mobile.konzern.posts.viewModel.BasePostViewModel

class NewsActivity: BasePostActivity() {
    override fun getViewTitle(): String? {
        return getString(R.string.title_news)
    }

    override fun getCustomViewModel(): BasePostViewModel {
        val vm: NewsViewModel by viewModels()
        return vm
    }

    override fun goToDetail(savedInstanceState: Bundle?) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.NewsMainFragment, true)
            .build()

        findNavController(R.id.nav_host_fragment).navigate(
            R.id.action_newsMainFragment_to_newsDetailFragment,
            savedInstanceState,
            navOptions
        )
    }

    override fun getContentLayout(): Int {
        return R.layout.content_news
    }
}
