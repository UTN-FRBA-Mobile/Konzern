package utn.frba.mobile.konzern

import androidx.activity.viewModels
import utn.frba.mobile.konzern.news.NewsViewModel
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel

class MainActivity : BaseActivity() {
    override val startsNewFlow: Boolean = false

    override fun getContentLayout(): Int {
        return R.layout.content_home
    }

    override fun getViewTitle(): String? {
        return getString(R.string.app_name)
    }
}
