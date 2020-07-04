package utn.frba.mobile.konzern.news

import androidx.activity.viewModels
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.BasePostActivity
import utn.frba.mobile.konzern.utils.BaseViewModel

class NewsActivity: BasePostActivity() {
    override fun getViewTitle(): String? {
        return getString(R.string.title_news)
    }

    override fun getCustomViewModel(): BaseViewModel {
        val vm: NewsViewModel by viewModels()
        return vm
    }

    override fun getContentLayout(): Int {
        return R.layout.content_news
    }
}
