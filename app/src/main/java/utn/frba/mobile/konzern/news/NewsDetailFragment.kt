package utn.frba.mobile.konzern.news

import androidx.fragment.app.activityViewModels
import utn.frba.mobile.konzern.posts.ui.PostDetailFragment
import utn.frba.mobile.konzern.posts.viewModel.BasePostViewModel

class NewsDetailFragment : PostDetailFragment() {
    override fun getCustomViewModel(): BasePostViewModel {
        val vm: NewsViewModel by activityViewModels()
        return vm
    }
}
